package com.jarrett.anthony.Mainframe;


import com.jarrett.anthony.API_Pollution.PollutionHandler;
import com.jarrett.anthony.API_Pollution.Sensor;
import com.jarrett.anthony.API_Pollution.SensorsAPI;
import com.jarrett.anthony.Components.Element;
import com.jarrett.anthony.Components.Node;

import java.util.HashMap;
import java.util.Map;

public class WeightCalcs {

    //ATTRIBUTES
    private static final double R = 6372.8; // In kms - need to convert this to miles as speed is provided in MPH
    private static double lat1, lat2, dLat, dLon, lon1, lat3, lon3;
    private static double minDist;
    private static String minSite;
    private static HashMap<String, Sensor> Sensors;

    /** This method will be called from within the package and will operate all actions on this class.
    /* @param n1 the star
    /* @param n2
    /* @param highway
    **/
    /*Takes two Nodes (intersections) and find the weight for the intersection which will join them
     */

    //Will need to do processing of Node Name and return Lon/Lat
    /* returns from global Euc/Nearest sen/highway type calced here
     * This will be a figure which will go into populateAdj list*/
    protected static double weight(Node n1, Node n2, String highway) {
        //Container the nodes, get their lon/lats
        Node cont1 = new Node(n1.getLat(), n1.getLon());
        Node cont2 = new Node(n2.getLat(), n2.getLon());

        //Haversine
        double havDist = haversineDist(cont1, cont2);
        double highWeight = highwayTypeWeighting(highway);

        //Nearest Sensor
        Node midPoint = findMid(cont1, cont2); //AFTER Having calc'ed mid point
        Sensor sen = nearestSen(midPoint);
        double avgPolVal = getPollutionVal(sen);

        // Final calculations will be made here,
        // this will be an overall calculation of all the weights we've collected
        return havDist + highWeight *avgPolVal;
    }

    /*Haversine formula (great circle distance) -
    For those who are in the earth is flat category, feel free to swap this out for EuclideanDist!(Joke) */
    protected static double haversineDist(Node cont1, Node cont2) {
        dLat = Math.toRadians(cont1.getLat() - cont2.getLat());
        dLon = Math.toRadians(cont1.getLon() - cont2.getLon());
        lat1 = Math.toRadians(cont2.getLat());
        lat2 = Math.toRadians(cont1.getLat());
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return 1000 * R * c; //converted from meters to KM
    }

    /* Method will find nearest sensor and then get the pollution value at that sensor */
    private static Sensor nearestSen(Node node) {
        //Will need to make calls to Sensors Map in order find which is the nearest
        /* calc Haversine distance from midPoint to sensors and keep track of lowest one */
        minDist = 1000000;
        Sensors = PollutionHandler.getPolluMap();
        for (Sensor value : Sensors.values()) {
            Node n1 = new Node(value.getLat(), value.getLon());
            // n1 is our sensor location, Node is midpoint for our 2 intersections
            double container1 = haversineDist(n1, node);
            if (container1 < minDist) {
                minSite = value.getSiteCode();
                minDist = container1;
            }
        }
        return Sensors.get(minSite);
    }

    /** Will return the pollution value for a given site **/
    private static double getPollutionVal(Sensor siteName) {
        // method .getPolVals() takes the average of the pollutionIndex
        // values at a sensor and returns a double
        return Sensors.get(siteName.getSiteCode()).getPolVals();
    }

    /* Weightings are experimental and a work in progress, due to the complex nature of path finding with additional weights*/
    private static double highwayTypeWeighting(String highway) {

        //Given two intersections, they will only meet on one way (Unless ice-cream problem is True)
        //In which case, depending on which Highway type we have, an additional weight for that will be returned
        if (highway == "null")
            return 100; //be on the safe side
        if (highway == "motorway" || highway == "trunk")
            return 110;
        if (highway == "motorway_link" || highway == "trunk_link")
            return 60;
        if (highway == "primary" || highway == "primary_link")
            return 50;
        if (highway == "secondary" || highway == "secondary_link")
            return 40;
        if (highway == "tertiary" || highway == "tertiary_link")
            return 35;
        if (highway == "road" || highway == "unclassified")
            return 30;
        if (highway == "residential")
            return 25;
        if (highway == "living_street")
            return 20;
        if (highway == "pedestrian")
            return 15;
        if (highway == "cycleway")
            return 10;
        if (highway == "path")
            return 10;
        if (highway == "service")
            return 5;
        else
            return 100; //be on the safe side
    }

    private static Node findMid(Node cont1, Node cont2) {
        dLon = Math.toRadians(cont2.getLon() - cont1.getLon());

        //convert to radians
        lat1 = Math.toRadians(cont1.getLat());
        lat2 = Math.toRadians(cont2.getLat());
        lon1 = Math.toRadians(cont1.getLon());

        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
        lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);
        return new Node(Math.toDegrees(lat3), Math.toDegrees(lon3));
    }

    private double getTraffic(Node cont1, Node cont2) {
        //This method would in theory find the mid point between an intersection
        //Then be able to retrieve the traffic at that local
        //In theory this method was going to work in conjunction with pollution data
        //However the data is not appropriately suited for my needs (consistency/fluency issues).
        //Perhaps a future solution will be found
        return 1;
    }

    //The weighting computation takes measurement values from differing contexts..
    //therefore, normalising is required in order to provide a balanced influence
    private double normalise(double base, double val) {
        /* normalising deferred due to no min/max value and dynamic nature of data
            - through trialnError an optimal weighting that blends well with distance
             parameter can be reached
         */
        return 1;
    }


}
