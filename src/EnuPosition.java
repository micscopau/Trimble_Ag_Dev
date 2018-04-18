public class EnuPosition {
	
	private double east;
	private double north;
	private double up;
	
	public EnuPosition(){
		east = 0.0;
		north = 0.0;
		up = 0.0;
	}
	
	public EnuPosition(double east, double north, double up) {
		this.east = east;
		this.north = north;
		this.up = up;
	}

	public double getEast() {
		return east; 
	}
	
	public double getNorth() {
		return north; 
	}
	
	public double getUp() {
		return up;
	}

	public double get(int i) {
		switch(i)
		{
		case 0: return east;
		case 1: return north;
		case 2: return up;
		default: return Double.NaN;
		}
	}

	//Latitude, Longitude, Altitude coordinates
	//void setLLA(LlaPos llaPos) {	}

	//void setXYZ(XyzPos xyzPos) {	}


}
