import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class WeightedAverageCalculator {

    ArrayList<Integer> heights = new ArrayList<>();
    ArrayList<Double> distances = new ArrayList<>();

    public WeightedAverageCalculator(Point<Integer>[] array){
        for (Point<Integer> p: array
        ) {
            heights.add(p.getValue());
            distances.add(p.getDistance());
        }
    }

    //calculate weighted average with weights assigned based on distance from sample point
    public int calculateWA(){
        double returnWA = 0;
        double weight = 0;
        double sumOfWeights = 0;

        for(int i=0; i<heights.size(); i++){
            weight = 1/(distances.get(i));
            sumOfWeights += 1/distances.get(i);
            returnWA += (weight)*(heights.get(i));
        }

        returnWA = returnWA/sumOfWeights;

        return (int)(returnWA+.5);
    }

}
