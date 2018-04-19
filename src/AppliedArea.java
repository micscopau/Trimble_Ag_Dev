
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
	    boolean overlap = true;
        //EnuPosition pAi, pAj;
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

            //this only handles rectangles oriented parallel to x axis
            /*
            if(pAIX > pBJ.getEast() || pBI.getEast() > pAJX
                    || pAIY < pBJ.getNorth() || pBI.getNorth() < pAJY){
                    */
            //2.x > 1.x
            if(alfx > brbx){
                overlap = false;
            }
            if(blfx > arbx){
                overlap = false;
            }
            if(alby > brfy){
                overlap = false;
            }
            if(blby > arfy){
                overlap = false;
            }



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

	
}
