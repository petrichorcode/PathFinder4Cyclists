package com.jarrett.anthony.API_Pollution;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.HashMap;

/**
 * The PollutionHandller parses the information received from the Pollution Sensors for London API,
 * hosted by Kings College's Environmental Group
 * http://api.erg.kcl.ac.uk/airquality/help
 */

public class PollutionHandler extends DefaultHandler {

/** HashMap to store Sensor information, SiteCode is set as Key. **/
private static HashMap<String, Sensor> Sensors = new HashMap<String, Sensor>();

//ATTRIBUTES
 /** The current read Sensor **/
 private Sensor current;
 private int i = 0;

 //CONSTRUCTOR
    public PollutionHandler(){
        super();
    }

    //OTHER METHODS
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        if(localName.equals("Site")){
            current = new Sensor(
                    attributes.getValue("SiteCode"),
                    Double.parseDouble(attributes.getValue("Latitude")),
                    Double.parseDouble(attributes.getValue("Longitude"))
            );
        }
        else if(localName.equals("Species")){
            current.addVal(Integer.parseInt(attributes.getValue("AirQualityIndex")));

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equals("Site")) {
            if (current != null) {
                Sensors.put(current.getSiteCode(),current);
            }
            current = null;
        }
    }

    /** Displays some statistics about given sensors **/
    protected static void printStatistics(){
        for(Sensor value : Sensors.values()){
            System.out.println("Site Name: " + value.getSiteCode() +"| " +"Lat: " + value.getLat()
                    + "| "+"Lon: "+ value.getLon()+"| PolVals: "+ value.getPolArr());
        }
    }

    //GETTERS
    public static HashMap<String,Sensor> getPolluMap(){
        return Sensors;
    }
}
