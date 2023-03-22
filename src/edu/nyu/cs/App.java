package edu.nyu.cs;

// some basic java imports
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.commons.lang3.SystemUtils;

import java.util.regex.Matcher;

// some imports used by the UnfoldingMap library
import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.providers.OpenStreetMap.*;
import de.fhpotsdam.unfolding.providers.MapBox;
import de.fhpotsdam.unfolding.providers.Google.*;
import de.fhpotsdam.unfolding.providers.Microsoft;
// import de.fhpotsdam.unfolding.utils.ScreenPosition;


/**
 * A program that pops open an interactive map.
 */
public class App extends PApplet {

	/****************************************************************/
	/*                  BEGIN - DON'T MODIFY THIS CODE              */
	/****************************************************************/
	UnfoldingMap map; // will be a reference to the actual map
	String mapTitle = ""; // will hold the title of the map
	final float SCALE_FACTOR = 0.0002f; // a factor used to scale pedestrian counts to calculate a reasonable radius for a bubble marker on the map
	final int DEFAULT_ZOOM_LEVEL = 11;
	final Location DEFAULT_LOCATION = new Location(40.7286683f, -73.997895f); // a hard-coded NYC location to start with
	String[][] data; // will hold data extracted from the CSV data file
	/****************************************************************/
	/*                    END - DON'T MODIFY THIS CODE              */
	/****************************************************************/

	/**
	 * This method is automatically called every time the user presses a key while viewing the map.
	 * The `key` variable (type char) is automatically is assigned the value of the key that was pressed.
	 * Complete the functions called from here, such that:
	 * 	- when the user presses the `1` key, the code calls the showMay2021MorningCounts method to show the morning counts in May 2021, with blue bubble markers on the map.
	 * 	- when the user presses the `2` key, the code calls the showMay2021EveningCounts method to show the evening counts in May 2021, with blue bubble markers on the map.
	 * 	- when the user presses the `3` key, the code calls the showMay2021EveningMorningCountsDifferencemethod to show the difference between the evening and morning counts in May 2021.  If the evening count is greater, the marker should be a green bubble, otherwise, the marker should be a red bubble.
	 * 	- when the user presses the `4` key, the code calls the showMay2021VersusMay2019Counts method to show the difference between the average of the evening and morning counts in May 2021 and the average of the evening and morning counts in May 2019.  If the counts for 2021 are greater, the marker should be a green bubble, otherwise, the marker should be a red bubble.
	 * 	- when the user presses the `5` key, the code calls the customVisualization1 method to show data of your choosing, visualized with marker types of your choosing.
	 * 	- when the user presses the `6` key, the code calls the customVisualization2 method to show data of your choosing, visualized with marker types of your choosing.
	 */
	public void keyPressed() {
		System.out.println("Key pressed: " + key);
		// complete this method
		switch(key){
			case '1':
				showMay2021MorningCounts(data);
				break;
			case '2':
				showMay2021EveningCounts(data);
				break;
			case '3':
				showMay2021EveningMorningCountsDifference(data);
				break;
			case '4':
				showMay2021VersusMay2019Counts(data);
				break;
			case '5':
				customVisualization1(data);
				break;
			case '6':
				customVisualization2(data);
				break;

		}

	}

	/**
	 * Adds markers to the map for the morning pedestrian counts in May 2021.
	 * These counts are in the second-to-last field in the CSV data file.  So we look at the second-to-last array element in our data array for these values.
	 * 
	 * @param data A two-dimensional String array, containing the data returned by the getDataFromLines method.
	 */
	public void showMay2021MorningCounts(String[][] data) {
		clearMap(); // clear any markers previously placed on the map
		mapTitle = "May 2021 Morning Pedestrian Counts";

		for (String[] dataString : data){
			float lat = Float.parseFloat(dataString[0]);
			float lng = Float.parseFloat(dataString[1]);
			Location markerLocation = new Location(lat,lng);
			int pedestrianCount = Integer.parseInt(dataString[dataString.length-3]);
			float markerRadius = pedestrianCount * SCALE_FACTOR;
			float[] markerColour = new float[]{255, 0, 0, 127};
			MarkerBubble marker = new MarkerBubble(this, markerLocation, markerRadius, markerColour);
			map.addMarker(marker);
		}

		/* // remove the code below and replace with your own code that solves the problem indicated in the comments
		// example of how to create a marker at a specific location and place it on the map
		float lat = 40.737375365084105f; // latitude of a location of interest
		float lng = -74.00101207586745f; // longitude of a location of interest
		Location markerLocation = new Location(lat, lng); // create a Location object
		int pedestrianCount = 11024; // an example pedestrian count (in reality, you will get these from a file)
		float markerRadius = pedestrianCount * SCALE_FACTOR; // scale down the marker radius to look better on the map
		float[] markerColor = {255, 0, 0, 127}; // a color, specified as a combinatino of red, green, blue, and alpha (i.e. transparency), each represented as numbers between 0 and 255.
		MarkerBubble marker = new MarkerBubble(this, markerLocation, markerRadius, markerColor); // don't worry about the `this` keyword for now... just make sure it's there.
		map.addMarker(marker);*/
	}

	/**
	 * Adds markers to the map for the evening pedestrian counts in May 2021.
	 * These counts are in the second-to-last field in the CSV data file.  So we look at the second-to-last array element in our data array for these values.
	 * 
	 * @param data A two-dimensional String array, containing the data returned by the getDataFromLines method.
	 */
	public void showMay2021EveningCounts(String[][] data) {
		clearMap(); // clear any markers previously placed on the map
		mapTitle = "May 2021 Evening Pedestrian Counts";
		// complete this method

		for (String[] dataString : data){
			float lat = Float.parseFloat(dataString[0]);
			float lng = Float.parseFloat(dataString[1]);
			Location markerLocation = new Location(lat,lng);
			int pedestrianCount = Integer.parseInt(dataString[dataString.length-2]);
			float markerRadius = pedestrianCount * SCALE_FACTOR;
			float[] markerColour = new float[]{255, 0, 0, 127};
			MarkerBubble marker = new MarkerBubble(this, markerLocation, markerRadius, markerColour);
			map.addMarker(marker);
		}
	}

	/**
	 * Adds markers to the map for the difference between evening and morning pedestrian counts in May 2021.
	 * 
	 * @param data A two-dimensional String array, containing the data returned by the getDataFromLines method.
	 */
	public void showMay2021EveningMorningCountsDifference(String[][] data) {
		clearMap(); // clear any markers previously placed on the map
		mapTitle = "Difference Between May 2021 Evening and Morning Pedestrian Counts";
		// complete this method

		for (String[] dataString : data){
			float lat = Float.parseFloat(dataString[0]);
			float lng = Float.parseFloat(dataString[1]);
			Location markerLocation = new Location(lat,lng);
			int eveningPedestrianCount = Integer.parseInt(dataString[dataString.length-2]);
			int morningPedestrianCount = Integer.parseInt(dataString[dataString.length-3]);
			int pedestrianCountDifference = eveningPedestrianCount-morningPedestrianCount;
			float markerRadius = Math.abs(pedestrianCountDifference) * SCALE_FACTOR;
			float[] markerColour = new float[]{255, 0, 0, 127};
			if(pedestrianCountDifference>=0){
				markerColour[0] = 0f;
				markerColour[2] = 255f;
			}
			MarkerBubble marker = new MarkerBubble(this, markerLocation, markerRadius, markerColour);
			map.addMarker(marker);
		}
	}

	/**
	 * Adds markers to the map for the difference between the average pedestrian count in May 2021 and the average pedestrian count in May 2019.
	 * Note that some pedestrian counts were not done in May 2019, and the data is blank.  
	 * Do not put a marker at those locations with missing data.
	 * 
	 * @param data A two-dimensional String array, containing the data returned by the getDataFromLines method.
	 */
	public void showMay2021VersusMay2019Counts(String[][] data) {
		clearMap(); // clear any markers previously placed on the map
		mapTitle = "Difference Between May 2021 and May 2019 Pedestrian Counts";

		// complete this method
		for (String[] dataString: data){
			if(dataString[dataString.length-7].equals("")){
				continue;
			}
			float lat = Float.parseFloat(dataString[0]);
			float lng = Float.parseFloat(dataString[1]);
			Location markerLocation = new Location(lat,lng);
			float May2019AverageCount = calculateAverage(dataString, 9);
			float May2021AverageCount = calculateAverage(dataString, 3);
			int pedestrianCountDifference = (int) (May2021AverageCount-May2019AverageCount);
			float markerRadius = Math.abs(pedestrianCountDifference) * SCALE_FACTOR;
			float[] markerColour = new float[]{255, 0, 0, 127};
			if(pedestrianCountDifference>=0){
				markerColour[0] = 0f;
				markerColour[2] = 255f;
			}
			MarkerBubble marker = new MarkerBubble(this, markerLocation, markerRadius, markerColour);
			map.addMarker(marker);

		}
	}

	/**
	 * @param dataString the data
	 * @param offset the position of the Morning count
	 * @return Average count of pedestrians for that count, 
	 */
	public float calculateAverage(String[] dataString,int offset){
		int MorningCount = Integer.parseInt(dataString[dataString.length-offset]);
		int EveningCount = Integer.parseInt(dataString[dataString.length-offset+1]);
		int WeekendCount = Integer.parseInt(dataString[dataString.length-offset+2]);
		float AverageCount = ((MorningCount+EveningCount)*5+(WeekendCount)*4)/14;
		return AverageCount;
	}

	/**
	 * A data visualization of your own choosing.  
	 * Do some data analysis and map the results using markers.
	 * 
	 * @param data
	 */
	public void customVisualization1(String[][] data) {
		clearMap(); // clear any markers previously placed on the map
		mapTitle = "Average May 2021 Pedestrian Counts";
		// complete this method		
		for (String[] dataString : data){
			float lat = Float.parseFloat(dataString[0]);
			float lng = Float.parseFloat(dataString[1]);
			Location markerLocation = new Location(lat,lng);
			int pedestrianCount = (int) calculateAverage(dataString, 3);
			float markerRadius = pedestrianCount * SCALE_FACTOR;
			float[] markerColour = new float[]{0, 255, 0, 127};
			MarkerBubble marker = new MarkerBubble(this, markerLocation, markerRadius, markerColour);
			map.addMarker(marker);
		}
	}

	/**
	 * A data visualization of your own choosing.  
	 * Do some data analysis and map the results using markers.
	 * 
	 * @param data
	 */
	public void customVisualization2(String[][] data) {
		clearMap(); // clear any markers previously placed on the map
		mapTitle = "Average May 2019 Pedestrian Counts";
		// complete this method	
		for (String[] dataString : data){
			if(dataString[dataString.length-7].equals("")){
				continue;
			}
			float lat = Float.parseFloat(dataString[0]);
			float lng = Float.parseFloat(dataString[1]);
			Location markerLocation = new Location(lat,lng);
			int pedestrianCount = (int) calculateAverage(dataString, 9);
			float markerRadius = pedestrianCount * SCALE_FACTOR;
			float[] markerColour = new float[]{0, 255, 0, 127};
			MarkerBubble marker = new MarkerBubble(this, markerLocation, markerRadius, markerColour);
			map.addMarker(marker);
		}
	}

	/**
	 * Opens a file and returns an array of the lines within the file, as Strings with their line breaks removed.
	 * 
	 * @param filepath The filepath to open
	 * @return A String array, where each String contains the text of a line of the file, with its line break removed.
	 * @throws FileNotFoundException
	 */
	public String[] getLinesFromFile(String filepath) {
	
    // modified from Text Analysis Exercise!

    ArrayList<String> fullTextSplit = new ArrayList<String>();
    // opening up a file may fail if the file is not there, so use try/catch to protect against such errors
    try {
      // try to open the file and extract its contents
      Scanner scn = new Scanner(new File(filepath));
      while (scn.hasNextLine()) {
        String line = scn.nextLine();
        fullTextSplit.add(line);
      }
      scn.close(); // be nice and close the Scanner
    }
    catch (FileNotFoundException e) {
      // in case we fail to open the file, output a friendly message.
      System.out.println("Oh no... can't find the file!");
    }
	String[] fullTextSplitStringArr = new String[fullTextSplit.size()];
	for (int i = 0; i<fullTextSplit.size();i++){
		fullTextSplitStringArr[i] = fullTextSplit.get(i);
	}
    return fullTextSplitStringArr; // return the full text
  }
	/**
	 * Takes an array of lines of text in comma-separated values (CSV) format and splits each line into a sub-array of data fields.
	 * For example, an argument such as {"1,2,3", "100,200,300"} could result in a returned array { {"1", "2", "3"}, {"100", "200", "300"} }
	 * This method must skip any lines that don't contain mappable data (i.e. don't have any geospatial data in them) 
	 * and do any other cleanup of the data necessary for it to be easily mapped by other code in the program.
	 *
	 * @param lines A String array of lines of text, where each line is in comma-separated values (CSV) format.
	 * @return A two-dimensional String array, where each inner array contains the data from one of the lines, split by commas.
	 */
	public String[][] getDataFromLines(String[] lines) {
		ArrayList<String[]> allLinesArrayList = new ArrayList<String[]>();
		boolean skipFirstLine = true;
		// complete this method
		for (String line : lines){
			if (skipFirstLine){
				skipFirstLine = false;
				continue;
			}
			String[] currentLine = line.split(",");
			String[] currentLineWithLatLong = new String[currentLine.length+1];
			for (int i = 2; i<currentLineWithLatLong.length;i++){
				currentLineWithLatLong[i] = currentLine[i-1];
			}
			String[] temporaryLatLong = parseLatLong(currentLine[0]); //makes a String[] with lat and long as separate strings
			currentLineWithLatLong[0] = temporaryLatLong[0];
			currentLineWithLatLong[1] = temporaryLatLong[1];
			allLinesArrayList.add(currentLineWithLatLong);
		}
	
		String[][] allLines = new String[allLinesArrayList.size()][];
		for (int i = 0; i<allLinesArrayList.size();i++){
			allLines[i] = allLinesArrayList.get(i);
		}
		return allLines;

	}


	/**
	 * 
	 * 
	 * @param dataValue the field from column "the_geom"
	 */
	public String[] parseLatLong(String dataValue){
		String[] LatAndLong = new String[2];
		String[] StringGeomLocation = dataValue.split(" ",0);
		LatAndLong[0] = removeChars(StringGeomLocation[1],'(');
		LatAndLong[1] = removeChars(StringGeomLocation[2],')');
		return LatAndLong;
	}
	/**
	 * 
	 * @param beforeEditString unedited string
	 * @param filter character to be removed
	 * @return edited string
	 */
	public String removeChars(String beforeEditString, char filter){
		String afterEditString = "";
		for (int i = 0; i<beforeEditString.length();i++){
			if (beforeEditString.charAt(i) == filter){
				continue;
			}
			else {
				afterEditString += beforeEditString.charAt(i);
			}
		}
		return afterEditString;
	}










	/****************************************************************/
	/**** YOU WILL MOST LIKELY NOT NEED TO EDIT ANYTHING BELOW HERE */
	/****               Proceed at your own peril!                  */
	/****************************************************************/

	/**
	 * This method will be automatically called when the program runs
	 * Put any initial setup of the window, the map, and markers here.
	 */
	public void setup() {
		size(1200, 800, P2D); // set the map window size, using the OpenGL 2D rendering engine
		// size(1200, 800); // set the map window size, using Java's default rendering engine (try this if the OpenGL doesn't work for you)
		map = getMap(); // create the map and store it in the global-ish map variable

		// load the data from the file... you will have to complete the functions called to make sure this works
		try {
			String cwd = Paths.get("").toAbsolutePath().toString(); // the current working directory as an absolute path
			String path = Paths.get(cwd, "data", "PedCountLocationsMay2015.csv").toString(); // e.g "data/PedCountLocationsMay2015.csv" on Mac/Unix vs. "data\PedCountLocationsMay2015.csv" on Windows
			String[] lines = getLinesFromFile(path); // get an array of the lines from the file.
			data = getDataFromLines(lines); // get a two-dimensional array of the data in these lines; complete the getDataFromLines method so the data from the file is returned appropriately
			// System.out.println(Arrays.deepToString(data)); // for debugging
			// automatically zoom and pan into the New York City location
			map.zoomAndPanTo(DEFAULT_ZOOM_LEVEL, DEFAULT_LOCATION);

			// by default, show markers for the morning counts in May 2021 (the third-to-last field in the CSV file)
			showMay2021MorningCounts(data);
			// various ways to zoom in / out
			// map.zoomLevelOut();
			// map.zoomLevelIn();
			// map.zoomIn();
			// map.zoomOut();

			// it's possible to pan to a location or a position on the screen
			// map.panTo(nycLocation); // pan to NYC
			// ScreenPosition screenPosition = new ScreenPosition(100, 100);
			// map.panTo(screenPosition); // pan to a position on the screen

			// zoom and pan into a location
			// int zoomLevel = 10;
			// map.zoomAndPanTo(zoomLevel, nycLocation);

			// it is possible to restrict zooming and panning
			// float maxPanningDistance = 30; // in km
			// map.setPanningRestriction(nycLocation, maxPanningDistance);
			// map.setZoomRange(5, 22);


		}
		catch (Exception e) {
			System.out.println("Error: could not load data from file: " + e);
		}

	} // setup

	/**
	 * Create a map using a publicly-available map provider.
	 * There will usually not be a need to modify this method. However, in some cases, you may need to adjust the assignment of the `map` variable.
	 * If there are error messages related to the Map Provider or with loading the map tile image files, try all of the other commented-out map providers to see if one works.
	 * 
	 * @return A map object.
	 */
	private UnfoldingMap getMap() {
		// not all map providers work on all computers.
		// if you have trouble with the one selected, try the others one-by-one to see which one works for you.
		map = new UnfoldingMap(this, new Microsoft.RoadProvider());
		// map = new UnfoldingMap(this, new Microsoft.AerialProvider());
		// map = new UnfoldingMap(this, new GoogleMapProvider());
		// map = new UnfoldingMap(this);
		// map = new UnfoldingMap(this, new OpenStreetMapProvider());

		// enable some interactive behaviors
		MapUtils.createDefaultEventDispatcher(this, map);
		map.setTweening(true);
		map.zoomToLevel(DEFAULT_ZOOM_LEVEL);

		return map;
	}
	
	/**
	 * This method is called automatically to draw the map.
	 * This method is given to you.
	 * There is no need to modify this method.
	 */
	public void draw() {
		background(0);
		map.draw();
		drawTitle();
	}

	/**
	 * Clear the map of all markers.
	 * This method is given to you.
	 * There is no need to modify this method.
	 */
	public void clearMap() {
		map.getMarkers().clear();
	}

	/**
	 * Draw the title of the map at the bottom of the screen
	 */
	public void drawTitle() {
		fill(0);
		noStroke();
		rect(0, height-40, width, height-40); // black rectangle
		textAlign(CENTER);
		fill(255);
		text(mapTitle, width/2, height-15); // white centered text
	}

	/**
	 * The main method is automatically called when the program runs.
	 * This method is given to you.
	 * There is no need to modify this method.
	 * @param args A String array of command-line arguments.
	 */
	public static void main(String[] args) {
		System.out.printf("\n###  JDK IN USE ###\n- Version: %s\n- Location: %s\n### ^JDK IN USE ###\n\n", SystemUtils.JAVA_VERSION, SystemUtils.getJavaHome());
		boolean isGoodJDK = SystemUtils.IS_JAVA_1_8;
		if (!isGoodJDK) {
			System.out.printf("Fatal Error: YOU MUST USE JAVA 1.8, not %s!!!\n", SystemUtils.JAVA_VERSION);
		}
		else {
			PApplet.main("edu.nyu.cs.App"); // do not modify this!
		}
	}

}
