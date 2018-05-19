
/**
 * Assignment 1
 * 
 * DBSCAN which uses the RangeQuery interface.
 * An (inefficient) implementation of a RangeQuery is given. 
 * 
 * DBSCAN will find core points in a dataset and cluster the data based on these
 * core points. A core point is a point with at least minPts nodes within a 
 * distance epsilon.
 * 
 */

import java.awt.List;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class assignment1 {

    int n, d, minPts;
    int epsilon;
    ClusterPoint[] points;
    double mX, mY;
    

    /**
     * Set to true before submitting. Don't forget to save.
     */
    static boolean submitting = false;

    public static void main(String[] args) throws IOException {
        int i = 2;
        long t1 = System.currentTimeMillis();
        String path_in = String.format("/home/jh0iku/Documents/q4/JBIO40/w1/prgA/input/%02d.in", i);
        String path_out = String.format("/home/jh0iku/Documents/q4/JBIO40/w1/prgA/output/%02d.out", i);
        long t2 = System.currentTimeMillis();
        //performance measure
        System.out.println("Simulation time: " + (t2 - t1) / 1000 + " seconds.");
        (new assignment1()).run(submitting, path_in, path_out);
    }

    public void run(boolean submitting, String path_in, String path_out) throws IOException {

        try {
            readInput(submitting, path_in);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBSCAN.class.getName()).log(Level.SEVERE, null, ex);
        }

       DBSCAN dbscan = new DBSCAN(new LinearQuery());
        // DBSCAN dbscan = new DBSCAN(new QuadtreeQuery(mX,mY));

        int cluster_counter = dbscan.findClustering(n, minPts, epsilon, points);

        if (submitting) {
            printOutput(cluster_counter);
        } else {
            writeOutput(cluster_counter, path_out);
        }
        System.err.println(cluster_counter);
    }

    /**
     * 
     * @param stdIO if true, use System.in (USE FOR SUBMITTING)
     * @param path  if !stdIO read from this file (USE FOR TESTING)
     * @throws FileNotFoundException
     */
    private void readInput(boolean stdIO, String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner sc;
        if (stdIO) {
            sc = new Scanner(System.in);
        } else {
            sc = new Scanner(file).useLocale(Locale.US);
        }

        n = sc.nextInt();
        d = sc.nextInt();
        epsilon = sc.nextInt();
        minPts = sc.nextInt();

        points = new ClusterPoint[n];
        ArrayList<ClusterPoint> arrPoints = new ArrayList<>();

        // Read n points and append points to the ClusterPoint array list
        for (int i = 0; i < n; i++) {
            double[] coords = new double[d];

            // Read d coords
            for (int j = 0; j < d; j++) {
                coords[j] = sc.nextInt();
            }

            // Add point to array
            ClusterPoint p = new ClusterPoint(d, coords);
            points[i] = p;
            arrPoints.add(p);
            
            if (i == 1) {
                mX = Math.max(points[i - 1].getCoords()[0], points[i].getCoords()[0]);
                mY = Math.max(points[i - 1].getCoords()[1], points[i].getCoords()[1]);

            } else if (i > 1) {
                mX = Math.max(mX, points[i].getCoords()[0]);
                mY = Math.max(mY, points[i].getCoords()[1]);
            }

            
        }



        // create root
        // build quad tree here
        // rough test implementation here
        QuadtreeNode boundary = new QuadtreeNode(mX/2, mY/2, mX/2, mY/2);
        Quadtree qt = new Quadtree(boundary, 1);
        qt.setCount(0);
        for(int i=0; i<arrPoints.size(); i++){
            qt.insert( arrPoints.get(i) );
        }
        //seraching points in given area efficiently
        QuadtreeNode range = new QuadtreeNode(mX/4, mY/4, mX/4, mY/4);
        System.out.println(qt.query(range).size());
        System.out.println(n);
        System.out.println("count: "+ qt.getCount());
        
        
       
    }

    private void printOutput(int c) {
        // Assignment number
        System.out.format("%d\n", 1);
        System.out.format("%d %d %d\n", n, d, c);
        for (int i = 0; i < n; i++) {
            ClusterPoint point = points[i];
            System.out.print(point);
        }
    }

    private void writeOutput(int c, String path_out) throws UnsupportedEncodingException, IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path_out), "utf-8"))) {
            writer.write(String.format("%d\n", 2));
            writer.write(String.format("%d %d %d\n", n, d, c));
            for (int i = 0; i < n; i++) {
                ClusterPoint point = points[i];
                writer.write(point.toString());
            }
        }
    }
}
