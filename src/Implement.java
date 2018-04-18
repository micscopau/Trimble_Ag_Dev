
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
  private boolean[] nozzles = new boolean[((nozzleCount & 1) == 0) ? nozzleCount+1 : nozzleCount]; //add 1 to nozzle array if odd NozzleCount

  public Implement(double width, double distanceToTractor){
    this.width = width;
    this.distanceToTractor = distanceToTractor;
    currHeading = 0.0;
    isSpraying = false;
  }

  public void handleNewTractorPosition(final EnuPosition tractorPos, double heading) {
    updateAppliedArea(generateImplementPos(tractorPos, heading), heading);
  }

  public void updateAppliedArea(final EnuPosition newImplementPos, double heading) {
    EnuPosition newLPos = getLeftPos(width/2);
    EnuPosition newRPos = getRightPos(width/2);
    AgPolygon newPoly = generatePolygon(currLPos, currRPos, newLPos, newRPos);
  
    // Turn the nozzles off if the spray polygon overlaps where we've already sprayed
    boolean turnNozzlesOn = !appliedArea.checkOverlap(newPoly);

    if (isSpraying)
    {
      // The spray we've got overlaps - turn off the nozzles
      appliedArea.addPolygon(newPoly);
    }

    setAllNozzles(turnNozzlesOn);
  
    isSpraying = turnNozzlesOn;
    currPos = newImplementPos;
    currHeading = heading;
    currRPos = newRPos;
    currLPos = newLPos;
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

  // Generates a polygon given where the implement ends were and are now
  public AgPolygon generatePolygon(EnuPosition backLeft, EnuPosition backRight, EnuPosition frontLeft, EnuPosition frontRight){
	  return null;
  }

  // Turns all the nozzles on or off so they actively start or stop spraying
  public void setAllNozzles(boolean on){
    for (int i = 0 ; i < nozzles.length ; i++){
      nozzles[i] = on;
    }
  }

} 

