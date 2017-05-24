package com.jarrett.anthony.API_Pollution;

import java.util.ArrayList;
import java.util.List;

public class Sensor{

    //ATTRIBUTES
    private double lat;
    private double lon;
    private String siteCode;
    private List polVals = new ArrayList<>();
    private double container;
    //CONSTRUCTOR
    /**
     *	@param siteCode The sensor PostCode
     *	@param lat The latitude
     *	@param lon The longitude
     */
    public Sensor(String siteCode, double lat, double lon) {
        this.siteCode = siteCode;
        this.lat = lat;
        this.lon = lon;
        this.polVals = new ArrayList<Integer>();

    }

    //MODIFIERS
    /**
     * @param polVal add a pollution value
     */
    public void addVal(Integer polVal){
        polVals.add(polVal);
    }

    //ACCESSORS
    /**
     * @return the average pollution value for the site
     */
    public double getPolVals() {
        int counter = polVals.size();
        for (Object polVal : polVals) {
            int bob = (int) polVal;
            container += bob;
        }
        container = container/ counter;
        return container;
    }

    /**
     * @return the array of Pollution for site
     */
    public List getPolArr(){
        return polVals;
    }

    /**
     * @return the site code
     */
    public String getSiteCode(){
        return siteCode;
    }


    /**
     * @return the latitude
     */
    public double getLat(){
        return lat;
    }


    /**
     * @return the longitude
     */
    public double getLon(){
        return lon;
    }
}
