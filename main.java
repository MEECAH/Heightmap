import java.io.*;
import java.util.*;

public class main {

    public static void main(String[] args) throws Exception {

        File file = new File("restrictedHeightMap.txt"); //originally read from N032E035_converted.txt

        Scanner sc = new Scanner(file);

        HeightLookupTool h = new HeightLookupTool(sc);

        int testing = h.weightedAverage(32.63133,35.04763);

        System.out.println(testing);

    }
}
