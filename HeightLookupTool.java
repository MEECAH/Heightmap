import java.io.*;
import java.util.*;

public class HeightLookupTool {

    //these final variables are the coordinates which define the pre-determined bounds of the quadtree
    //these numbers taken from approx 1000m points to the bottom left and top right corners
    //of a rectangle surrounding the test flight on google maps
    private final double minX = 32.62507;
    private final double minY = 35.0368;
    private final double maxX = 32.64108;
    private final double maxY = 35.05792;

    private QuadTree<Integer> q = new QuadTree<>(minX,minY,maxX,maxY);

    private Point<Integer>[] foundPoints; // this is for storing the results of Quadtree's searchWithin method

    Scanner sc;

    String s;

    double x, y;
    int z;

    //constructor
    public HeightLookupTool(Scanner sc){
        this.sc = sc;
        buildQuadTree();
    }

    private void buildQuadTree(){

        while (sc.hasNextLine()) {
            s = sc.nextLine();

            String[] splitStr = s.trim().split("\\s+");

            x = Double.parseDouble(splitStr[0]);
            y = Double.parseDouble(splitStr[1]);
            z = Integer.parseInt(splitStr[2]);

            //Point<Integer> p = new Point(x,y,z);

            q.set(x,y,z);

        }
    }

    private void findNeighbors(double sampleX, double sampleY){
        double[] boxCoordinates = q.generateBoundaryBox(sampleX,sampleY);
        foundPoints = q.searchWithin(boxCoordinates[0],boxCoordinates[1],boxCoordinates[2],boxCoordinates[3]);

        for (Point<Integer> p : foundPoints){
            p.distance(sampleX,p.getX(),sampleY,p.getY());
        }
    }

    public int weightedAverage(double xcoord,double ycoord){
        findNeighbors(xcoord,ycoord);
        WeightedAverageCalculator wa = new WeightedAverageCalculator(foundPoints);
        int wAvg = wa.calculateWA();
        return wAvg;
    }

    //old code used to generated restrictedHeightMap.txt
    //-------------------------------------------------------------------------------------------------------------

        /*FileWriter fileWriter = new FileWriter("restrictedHeightMap.txt");

        while (sc.hasNextLine()){
            s = sc.nextLine();

            String[] splitStr = s.trim().split("\\s+");

            //assigning y and x in opposite order because .txt file stores the coordinates as longitude and latitude and
            //I'm using the following lines to reverse them for ease when comparing with google maps coordinates
            //IMPORTANT COMMENT ^^^^

            y = Double.parseDouble(splitStr[0]);
            x = Double.parseDouble(splitStr[1]);
            z = Integer.parseInt(splitStr[2]);

            System.out.println(x+" "+y+" "+z+" ");

            //restrict coordinates to smaller area of interest
            //approx 1000 meters around area of test flight
            if((x>32.62507 && x<32.64108) && (y>35.0368 && y<35.05792)){

                String s2 = x+" "+y+" "+z+"\n";

                fileWriter.write(s2);
            }
        }

        fileWriter.close();*/


}
