package com.jarrett.anthony.Mainframe;

import com.jarrett.anthony.API_GeoCoding.GeoCode;
import com.jarrett.anthony.Components.Element;
import com.jarrett.anthony.Components.Node;
import com.jarrett.anthony.util.Algorithms.AStarPathFinder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * This class fulfills the varying search parameters that may come up.
 * These include Node search, Geolocation search and Address/POI search
 * It also covers the shortfalls of the Adjacency list by implementing a "findNearestNode".
 */
public class SearchMethods implements terminalMsgs {

    //ATTRIBUTES
    private static Scanner scan = new Scanner(System.in);
    private static boolean waiting = true;
    private static String str = "";
    private static Map<String,Element> elements = ParserHandler.getMap();
    private static double minValueStart = 9999999;
    private static double minValueEnd = 9999999;
    private static String minInterStart = "";
    private static String minInterEnd = "";
    private static String[] container = new String[2];

    //METHODS
    /** A continual while loop that allows to make inifnite searches
     * with varying search Types - with the option to exit **/
    public static void awaitingQuery(){
        System.out.println(welcome);
        while(waiting) {
            //Wait for user input
            str = scan.nextLine();

            //Case for NodeSearch
            if (str.toUpperCase().replaceAll("\\s+","").equals("N")) {
                nodeSearch();
                System.out.println(anotherSearch);
            }

            //Case for GeoSearch
            if(str.toUpperCase().replaceAll("\\s+","").equals("G")){
                geoSearch();
                System.out.println(anotherSearch);
            }

            //Case for PostalSearch
            if(str.toUpperCase().replaceAll("\\s+","").equals("P")){
                postalSearch();
                System.out.println(anotherSearch);
            }
            //Case for Exit
            if(str.toUpperCase().replaceAll("\\s+","").equals("E")){
                System.out.println(thankYouQuote);
                waiting = false;
            }
        }
    }


    /* A NodeSearch is a search to and from another Node. Node is a term used for a uniquely identified location
         NodeSearch is the simplest search, but not practical in the real world
         - due to the abstraction of Node IDs */
    private static void nodeSearch(){
        System.out.println(okNode);
        str = scan.nextLine().toUpperCase();
        String[] toFrom = str.split(" ");
        Node n1 = (Node) ParserHandler.getMap().get(toFrom[0]);
        Node n2 = (Node) ParserHandler.getMap().get(toFrom[1]);
        AStarPathFinder.searchPath(n1.getId(), n2.getId());
        /*Needs exception catching incase nullpointer is thrown due to a node that does not exist*/
    }

    /* The GeoSearch is a search that is made from two pairs of longitude and latitude points
     * It is a very precise search, but difficult to fully implement.
     * At this stage, the geoSearch will result in finding the nearest intersection(Node) and conduct
       the search from that point. The same applies to the destination location.*/
    private static void geoSearch(){
        System.out.println(okGeo);
        str = scan.nextLine();
        String[] toFrom = str.split(" ");
        //String[] toFrom = str2.split(" ");
        findNearestNode(toFrom[0],toFrom[1],toFrom[2],toFrom[3]);
        AStarPathFinder.searchPath(container[0], container[1]);
    }

    /* A PostalSearch is useful for those who may not be familiar with or have access to Geolocation info.
      It is also the most common way of searching. An address or POI are other considerations, but these are things
       which fall under with the same paradigm. Methodologies used here are easily transferrable. This search
       will result in a similar type of 'finding the nearest intersection (Node)' search as conducted above.
     */
    private static void postalSearch(){
        System.out.println(okPostal);
        str = scan.nextLine().replaceAll(" ", "");
        String str2 = scan.nextLine().replaceAll(" ", "");
        Node n3 = GeoCode.addrSearch(str);
        Node n4 = GeoCode.addrSearch(str2);
        findNearestNode(Double.toString(n3.getLat()),Double.toString(n3.getLon()),
                Double.toString(n4.getLat()),Double.toString(n4.getLon()));
        AStarPathFinder.searchPath(container[0], container[1]);
    }

    /* Due to the programs current limitations, it is not possible to find a path directly from any given
    location (that isn't in the adjacency list). The adjacency list only supports intersections on a map.
    Therefore, for a given location, the nearest intersection is found and returned by the following method.
     */
    /**
     * @param lat1 the lat of the start location
     * @param lon1 the lon of the start location
     * @param lat2 the lat of the end location
     * @param lon2 the lon of the end location
     */
    private static void findNearestNode(String lat1, String lon1, String lat2, String lon2 ){
        Node start = new Node(Double.parseDouble(lat1), Double.parseDouble(lon1));
        Node finish = new Node(Double.parseDouble(lat2), Double.parseDouble(lon2));

        for(int i = 0; i < RoadNetwork.getIntersections().size(); i++){
          Node n5 = (Node) elements.get(RoadNetwork.getIntersections().get(i));
            double temp1 = WeightCalcs.haversineDist(start, n5);
          if(temp1 < minValueStart){
              minValueStart = temp1;
              minInterStart = RoadNetwork.getIntersections().get(i);
          }

            double temp2 = WeightCalcs.haversineDist(finish, n5);
          if(temp2 < minValueEnd){
              minValueEnd = temp2;
              minInterEnd = RoadNetwork.getIntersections().get(i);
          }
        }
        container[0] = minInterStart;
        container[1] = minInterEnd;
    }
}
