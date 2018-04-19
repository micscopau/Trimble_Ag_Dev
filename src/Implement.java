
import static java.lang.Math.*;

public class Implement implements TractorPositionListener {

    private double width;
    private double distanceToTractor;
    private double currHeading;
    private boolean isSpraying;
    private EnuPosition currPos;
    private EnuPosition currLPos;
    private EnuPosition currRPos;
    private AppliedArea appliedArea;

    private int nozzleCount = 12;
    private boolean nozzleCountEven = (nozzleCount & 1) == 0;
    private boolean[] nozzles = new boolean[(nozzleCountEven) ? nozzleCount + 1 : nozzleCount]; //add 1 to nozzle array if odd NozzleCount

    public Implement(double width, double distanceToTractor) {
        this.width = width;
        this.distanceToTractor = distanceToTractor;
        currHeading = 0.0;
        isSpraying = false;
    }

    public void handleNewTractorPosition(final EnuPosition tractorPos, double heading) {
        updateAppliedArea(generateImplementPos(tractorPos, heading), heading);
    }

    public void updateAppliedArea(final EnuPosition newImplementPos, double heading) {
        EnuPosition newLPos = getLeftPos(width / 2);
        EnuPosition newRPos = getRightPos(width / 2);
        AgPolygon newPoly = generatePolygon(currLPos, currRPos, newLPos, newRPos);

        // Turn the nozzles off if the spray polygon overlaps where we've already sprayed
        boolean turnAllNozzlesOn = !appliedArea.checkOverlapAllBool(newPoly);

        //int olPolyInd = appliedArea.checkOverlapAllInt(newPoly);
        //if (olPolyInd >= 0){

        int lNozInd = 0, rNozInd = nozzles.length -1 ;
        EnuPosition lNozPos = new EnuPosition();
        EnuPosition rNozPos = new EnuPosition();

        if (!turnAllNozzlesOn) {
            //determine what is largest possible rectangle of nozzles spraying that does not overlap

            for (int i = 0; i < nozzles.length; i++) {
                lNozPos = calculateNozzlePosition(i);
                lNozInd = i;

                //Generate new polygon that is only single nozzle and verify if overlap exists
                //TODO: test overlap check code for scenario of multiple "corners" having same coordinates
                //TODO: replace this with check of specific polygon that caused overlap conflict for full width sprayer
                if (appliedArea.checkOverlapAllBool( generatePolygon(currLPos, currRPos, lNozPos, lNozPos))) {
                   break;
                }else{
                    lNozPos = null;
                }
            }

            if (null == lNozPos){
                //iterated entire nozzle list and no single nozzle being on passes overlap check
                setAllNozzles(false);
            } else{ //wait to check for right side edge once we've found left side
                for (int j = nozzles.length - 1 ; j >= lNozInd ; j--) {
                    rNozPos = calculateNozzlePosition(j);
                    rNozInd = j;

                    //Generate new polygon that is only single nozzle and verify if overlap exists
                    //TODO: same as above TODO's
                    if (appliedArea.checkOverlapAllBool(generatePolygon(currLPos, currRPos, rNozPos, rNozPos))) {
                        break;
                    } else {
                        rNozPos = null;
                    }
                }

                if(null == rNozPos){
                    //probably did something wrong, as very least result could be: right==left
                    setAllNozzles(false);
                }else {
                    newPoly = generatePolygon(currLPos, currRPos, lNozPos, rNozPos);

                    if(isSpraying){
                        appliedArea.addPolygon(newPoly);
                    }

                    setZoneNozzles(true, lNozInd, rNozInd);
                    isSpraying = true;
                }
            }

        } else {
            //no overlap, entire spray bar can be used
            if (isSpraying) {
                //update appliedArea with newPoly
                appliedArea.addPolygon(newPoly);
            }

            setAllNozzles(true);
            isSpraying = true;
        }


        currPos = newImplementPos;
        currHeading = heading;
        currRPos = newRPos;
        currLPos = newLPos;
    }

    public EnuPosition findLeftNozzle() {
      //find leftmost nozzle acceptable

      EnuPosition nozzPos = new EnuPosition();

      for (int i = 0; i < nozzles.length; i++) {
         nozzPos = calculateNozzlePosition(i);

          //Generate new polygon that is only single nozzle and verify if overlap exists
          //TODO: test overlap check code for scenario of multiple "corners" having same coordinates
          //TODO: replace this with check of specific polygon that caused overlap conflict for full width sprayer
          if (appliedArea.checkOverlapAllBool( generatePolygon(currLPos, currRPos, nozzPos, nozzPos))) {
              return nozzPos;
          } // else //iterate to next i and check next nozzle inward from the left
      }
      return null;
    }


    public EnuPosition calculateNozzlePosition(int i){

        //We are calculating distance, therefore we need to know count of spaces between nozzles
        int justifiedCount = (nozzleCountEven) ? nozzleCount  : nozzleCount -1;
        //we subtract 1 from nC to account for zero indexing,
        // and that when nCEven is true, we add one to nozzleArray length

        if ((justifiedCount / 2) - i > 0) {
            //left side
            return getLeftPos(calculateNozzleDistance(i));

        } else if ((justifiedCount / 2) - i < 0) {
            //right side
            return getRightPos(calculateNozzleDistance(i));
        } else {
            //center
            return currPos;
        }

    }


  public double getWidth() {
    return width; 
  }

  public double getDistanceToTractor() {
    return distanceToTractor; 
  }

  // Retrieves the current position of the implement (center)
  public EnuPosition getPosition() {
    return currPos;
  }

  // Retrieve the area where spray has been applied
  public AppliedArea getAppliedArea() {
    return appliedArea;
  }

  // Indicates whether or not the implement is currently spraying
  public boolean isSpraying() {
    return isSpraying; 
  }

  // Generates the center position of the implement given the tractor pos
  public EnuPosition generateImplementPos(final EnuPosition tractorPos, double heading) {

    //adding PI to heading to account that implement is off rear of tractor
    return calculatePosition(distanceToTractor, (heading+PI), tractorPos);
  }

  // Gets a position to the left of the implement center
  public EnuPosition getLeftPos(double distanceFromCenter) {
    return calculatePosition(distanceFromCenter, (currHeading + (3*PI/2)), currPos);
  }

  // Gets a position to the right of the implement center
  public EnuPosition getRightPos(double distanceFromCenter) {
    return calculatePosition(distanceFromCenter, (currHeading + (PI/2)), currPos);
  }

  public EnuPosition calculatePosition(double r, double theta, EnuPosition origin){
    double xHead = cos(theta);
    double yHead = sin(theta);

    double xRelPos = (r * xHead);
    double yRelPos = (r * yHead);

    double xPos = origin.getEast() + xRelPos;
    double yPos = origin.getNorth() + yRelPos;

    return new EnuPosition(xPos, yPos, origin.getUp());
  }

  // Generates a polygon given where the active nozzle ends were and are now
  //public AgPolygon generatePolygon(EnuPosition backLeft, EnuPosition backRight, EnuPosition frontLeft, EnuPosition frontRight){
  public AgPolygon generatePolygon(EnuPosition backLeft, EnuPosition backRight, EnuPosition frontLeft, EnuPosition frontRight){
	  return new AgPolygon(backLeft, backRight, frontLeft, frontRight);
  }

  // Turns all the nozzles on or off so they actively start or stop spraying
  public void setAllNozzles(boolean on){
      for (int i = 0 ; i < nozzles.length ; i++){
      nozzles[i] = on;
      }
  }

  public double calculateNozzleDistance(int i){
      double dist = 0.0;
      double nozzleSpacing = getWidth()/(nozzleCount - 1);
      int countFromCenter = (abs(nozzleCount/2)- i);

      if( ((nozzleCount & 1) == 0) && (countFromCenter > 0) ){
          dist = ((nozzleSpacing/2)+((countFromCenter - 1)*nozzleSpacing));
      }else if((nozzleCount & 1) == 1){
          dist = countFromCenter*nozzleSpacing;
      } else{
          dist = 0.0;
      }

      return dist;
  }

  public void setZoneNozzles(boolean on, int a, int b){
      //set nozzles from index range a to b inclusive
      for(int i = a; i <= b ; i ++){
          nozzles[i] = on;
      }
  }

} 

