
import java.util.ArrayList;

public class AppliedArea {

	private ArrayList<AgPolygon> polygons;

	public AppliedArea(){
		polygons = new ArrayList<AgPolygon>();
	}	

	public void addPolygon(AgPolygon poly){
        polygons.add(poly);
	}

	public boolean checkOverlapAllBool(AgPolygon poly){
	    boolean overlap = false;

        double alfx = poly.getlF().getEast();
        double alfy = poly.getlF().getNorth();

        double arbx = poly.getrB().getEast();
        double arby = poly.getrB().getNorth();

	    for (int i = 0 ; i < polygons.size() ; i++){
	        AgPolygon thisPoly = polygons.get(i);

            double blfx = thisPoly.getlF().getEast();
            double blfy = thisPoly.getlF().getNorth();

            double brbx = thisPoly.getrB().getEast();
            double brby = thisPoly.getrB().getNorth();

            //Best method that I've found so far would most likely be point inclusion in Polygons:
            // https://wrf.ecse.rpi.edu//Research/Short_Notes/pnpoly.html
            // https://stackoverflow.com/questions/8721406/how-to-determine-if-a-point-is-inside-a-2d-convex-polygon'

            //This might be easily extended to checking a line, or just checking both end points of a line might be
            // nearly as effective


            //this method only handles right angle rectangles oriented parallel to each other
            /*
            if(pAIX > pBJ.getEast() || pBI.getEast() > pAJX
                    || pAIY < pBJ.getNorth() || pBI.getNorth() < pAJY){
            }
             */


            //We can always brute force check every combination of points being outside of the other polygon,
            //but it is very clear that this is a prime example of brute force being the wrong idea.
            /*
            if((alfx > brbx && alfy > brby )|| ...){
            }
            */

        }

	    return overlap;
	}


    public int checkOverlapAllInt(AgPolygon poly){
        //perform similar overlap check to the boolean,
        //except return the integer location of the polygon in the AppliedArea list
        //This would allow more pinpointed checking of overlap on iterations of nozzle zone polygons
        int overlap = -1;

        return overlap;
    }

    public boolean checkOverlapOneBool(int i, AgPolygon poly){
        //again a similar overlap check except
        // this allows us to check overlap on just a single AppliedArea polygon at a time

	    boolean overlap = false;

        return overlap;
    }

    public ArrayList<AgPolygon> sortAppliedArea(ArrayList<AgPolygon> polys){
	    ArrayList<AgPolygon> sorted = new ArrayList<AgPolygon>();

	    //Sort coordinates by distance from a given point:
	    //https://stackoverflow.com/questions/30636014/how-to-order-a-list-of-points-by-distance-to-a-given-point

        //If the unsorted list is short enough, it might be acceptable to call this sort based on current tractor/implement location
        //and only need to check the first so many entries for overlap based on implement width.

        //we could also just always sort based on origin, and once we have the polygon index that caused the overlap violation,
        //we check the polygons in the list near the conflicting polygon.
        // I foresee issues (if the polygons have not been consolidated) with nearby points in an arbitrary direction being far away in the
        // sorted list though.

	    return sorted;
    }

    public ArrayList<AgPolygon> consolidateAppliedArea(ArrayList<AgPolygon> polys){
	    ArrayList<AgPolygon> shrunk = new ArrayList<AgPolygon>();

	    //First thoughts are to check if 2 neighboring polygons (in sorted list) share 2 points in common.
        // If so, we can definitely merge them.
        //Foresee issues with gaps on the order of 1 inch (resolution of instrumentation), so perhaps if neighboring polygons
        // are within a margin of error range that is <= width of 1 sprayer

	    return shrunk;
    }

	
}
