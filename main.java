import java.io.*;
import java.util.*;

public class main {

    public static void main(String[] args) throws Exception {

        //these final variables are the coordinates which define the pre-determined bounds of the quadtree
        //these numbers taken from approx 1000m points to the bottom left and top right corners
        //of a rectangle surrounding the test flight on google maps
        final double minX = 32.62507;
        final double minY = 35.0368;
        final double maxX = 32.64108;
        final double maxY = 35.05792;

        //coordinates for the sample point taken from drone flight path
        final double sampleX = 32.63133;
        final double sampleY = 35.04763;

        Point<Integer>[] foundPoints; // this is for storing the results of Quadtree's searchWithin method

        //PriorityQueue<Point<Integer>> nearestPoints = new PriorityQueue<Point<Integer>>(4, Comparator.comparingDouble(Point::getDistance)); //this is for keeping the four closest points found

        File file = new File("restrictedHeightMap.txt"); //originally read from N032E035_converted.txt

        Scanner sc = new Scanner(file);

        String s;

        double x;
        double y;
        int z;

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

        //-------------------------------------------------------------------------------------------------------------
        //begin code to build quad tree from height map text file data

        QuadTree<Integer> q = new QuadTree<>(minX,minY,maxX,maxY);

        while (sc.hasNextLine()) {
            s = sc.nextLine();

            String[] splitStr = s.trim().split("\\s+");

            x = Double.parseDouble(splitStr[0]);
            y = Double.parseDouble(splitStr[1]);
            z = Integer.parseInt(splitStr[2]);

            //Point<Integer> p = new Point(x,y,z);

            q.set(x,y,z);

        }

        //old code used for testing quadtree construction
        //---------------------------------------------------------------------------------------------

        System.out.println(q.getCount());
        System.out.println();
        System.out.println(q.getValues());
        System.out.println();
        System.out.println("testing, the next line should print 204");
        System.out.println(q.get(32.62513888888892,35.057916666666635,null));
        System.out.println();

        double[] boxCoordinates = q.generateBoundaryBox(sampleX,sampleY);
        foundPoints = q.searchWithin(boxCoordinates[0],boxCoordinates[1],boxCoordinates[2],boxCoordinates[3]);

        for (Point<Integer> p : foundPoints){
            p.distance(sampleX,p.getX(),sampleY,p.getY());
        }

        weightedAverage wa = new weightedAverage();
        int WA = wa.calculateWA(foundPoints);
        System.out.print(WA);
    }

}
