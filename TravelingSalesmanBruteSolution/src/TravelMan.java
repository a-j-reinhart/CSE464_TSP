import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.*;

/**
 * @author Ardin Reinhart
 * @Date 31 Oct. 2017
 * Purpose: create a brute-force solution to the Traveling Salesman Problem
 */

public class TravelMan {

	private static RandomAccessFile raf;
	// save best solution
	private static ArrayList<Point2D> bestPath = new ArrayList<Point2D>();
	private static double dim = 0, bestDist = 0;
	private static String fileName;
	private static int count = 0;
	
	// ================================================================================================== Methods
	public static void main(String[] args) {
		
		// read file
		ArrayList<Point2D> original = readDataFile();
		System.out.println("Dimension:");
		System.out.println(dim);
		
		// original path
		System.out.println("Original Path's Distance:");
		bestDist = getPathDist(original);
		System.out.println(bestDist);
		
		// check all paths
		checkAllPaths(original, 0);
		System.out.println("Best Path's Distance:");
		System.out.println(bestDist);
		//System.out.println("Best Path:");
		//System.out.println(java.util.Arrays.toString(bestPath.toArray()));
	}
	
	private static double getPathDist(ArrayList<Point2D> path) {
		double dist = 0;
		for (int i = 0; i < dim-1; i++) 
			dist += getDist(path.get(i), path.get(i+1));
		return dist;
	}
	
	private static void checkAllPaths(ArrayList<Point2D> input, int k) {
		for(int i = k; i < input.size(); i++){
            java.util.Collections.swap(input, i, k);
            checkAllPaths(input, k+1);
            java.util.Collections.swap(input, k, i);
        }
        if (k == input.size()-1){
        	count += 1;
        	//System.out.println(count);
        	double d = getPathDist(input);
        	if (d < bestDist) {
        		bestDist = d;
        		bestPath = input;
        		//System.out.println(java.util.Arrays.toString(bestPath.toArray()));
        	}
        }
	}
	
	private static ArrayList<Point2D> readDataFile() {
		ArrayList<Point2D> points = new ArrayList<>();
		int node = 0;
    	String[] line = new String[10];
    	String fullLine = null;
       	try {
       		Scanner reader = new Scanner(System.in);
    		System.out.println("Name of file: ");
    		setFileName(reader.nextLine());
    		raf = new RandomAccessFile(fileName, "r");
    		try {
    			raf.readLine(); // skip name, comment, & type
    			raf.readLine();
    			raf.readLine(); 
    			dim = Integer.parseInt(raf.readLine().substring(11));
    			//System.out.println(dim);
    			raf.readLine(); // skip edge weight type & node coord lines
    			raf.readLine(); 
    			for (int i = 0; i < dim; i++) {
    				fullLine = raf.readLine();
    				line = (fullLine).split("\\s+");
    				//System.out.println(line[1]);
    				if (fullLine.charAt(0)==' '){
	    				Point2D p = new Point2D.Double(Double.parseDouble(line[2]), Double.parseDouble(line[3]));
	    				System.out.println(p.toString());
	    				points.add(p);
	    				//node = Integer.parseInt(line[1]);
    				} else {
    					Point p = new Point(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
	    				System.out.println(p.toString());
	    				points.add(p);
    				}
    			}
    			raf.close();
    		} catch(Exception e) {
				System.out.println("Error:");
				System.out.println(e.getMessage());
			}
    	} 
       	catch(FileNotFoundException ex) {
    		System.err.println( "Unable to open file '" +  fileName + "'");
    		System.exit(1);
    	}
    	catch(IOException ex) {
    		System.err.println("Error reading file '" + fileName + "'");
    		System.exit(1);
    	}
       	return points;
	}
	
	private static double getDist(Point2D p1, Point2D p2) {
	    return (Math.sqrt((p2.getX()-p1.getX())*(p2.getX()-p1.getX()) +
	    		(p2.getY()-p1.getY())*(p2.getY()-p1.getY())));
	}
	
	// ================================================================================================== Getters & Setters
	public static String getFileName() 				{		return fileName;		}
	public static void setFileName(String name) {
		if (name == null) 
			System.out.println("Error: No file given.");
		else 
			fileName = name;
	}
	
}
