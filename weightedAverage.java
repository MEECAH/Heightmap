import java.util.ArrayList;
import java.util.List;

public class weightedAverage {

    ArrayList<Integer> heights;
    ArrayList<Double> distances;

    public weightedAverage(){
    }


    public int calculateWA(Point<Integer>[] array){
        double WA = 0;

        for (Point<Integer> p: array
        ) {
            heights.add(p.getValue());
            distances.add(p.getDistance());
        }

        for(int i=0; i<=heights.size(); i++){
            WA += ((double)heights.get(i))/(distances.get(i));
        }

        return (int)WA;
    }

}
