import java.util.Collection;

public class Tractor {

	  private double speed;
	  private double heading;
	  private EnuPosition pos;

	  private Collection<TractorPositionListener> listeners;


	  public Tractor(){

	  }

	  // Retrieves the current speed of the tractor (in m/s)
	  public double getSpeed() { return speed; }

	  // Get heading of tractor (in radians)
	  public double getHeading() { return heading; }

	  // Retrieves the position of the tractor
	  public EnuPosition getPosition() { return pos; }

	  // Register/unregister listener for the tractor position.
	  public void registerListener(TractorPositionListener listener){}
	  public void unregisterListener(TractorPositionListener listener){}

	  // Must be called at regular interval
	  public void periodicUpdate(){
		  
	  }

	  public void notifyListeners(EnuPosition tractorPos, double heading){
		  
	  }

}
