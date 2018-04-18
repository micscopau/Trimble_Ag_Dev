import java.awt.*;

public class Implement implements TractorPositionListener {

  private double width;
  private double distanceToTractor;
  private double currHeading;
  private boolean isSpraying;
  private EnuPosition currPos;
  private EnuPosition currLPos;
  private EnuPosition currRPos;
  private AppliedArea appliedArea;

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
    Polygon newPoly = generatePolygon(currLPos, currRPos, newLPos, newRPos);
  
    // Turn the nozzles off if the spray polygon overlaps where we've already
    // sprayed
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
	  return null;
  }

  // Gets a position to the left of the implement center
  public EnuPosition getLeftPos(double distanceFromCenter) {
	  return currLPos; 
  }

  // Gets a position to the right of the implement center
  public EnuPosition getRightPos(double distanceFromCenter) {
	  return currRPos; 
  }

  // Generates a polygon given the where the implement ends were and are now
  public Polygon generatePolygon(EnuPosition backLeft, EnuPosition backRight, EnuPosition frontLeft, EnuPosition frontRight){
	  return null;
  }

  // Turns all the nozzles on or off so they activly start or stop spraying
  public void setAllNozzles(boolean on){

  }



} 

