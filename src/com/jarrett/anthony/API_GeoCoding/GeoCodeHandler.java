package com.jarrett.anthony.API_GeoCoding;

import com.jarrett.anthony.Components.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The GeoCodeHandler parses the information received from the GoogleMaps API
 * https://developers.google.com/maps/documentation/geocoding/intro
 */

public class GeoCodeHandler extends DefaultHandler {

    //ATTRIBUTES
    private Node tempN;
    private double tempVal;
    private boolean lat = false;
    private boolean lng = false;
    private double latVal = 0;
    private double lngVal = 0;
    /** Where the coordinates of the parsed address will be stored **/
    private static Node cords;

    //CONSTRUCTOR
    public GeoCodeHandler() {
        super();
    }


    //OTHER METHODS
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        //System.out.println("test");
        if (localName.equals("location")) {
            Node tempN = new Node();
        }
        if(localName.equals("lat")){
            lat = true;
        }
        if(localName.equals("lng")){
            lng = true;
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equals("location")) {
            Node result = new Node(latVal,lngVal);
            System.out.println("Location done " + result.getLat() +" "+ result.getLon());
            cords = result;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (lat) {
            if(latVal == 0){
                String latValStr = new String(ch, start, length);
                latVal = Double.parseDouble(latValStr);
                System.out.println("lat: " + new String(ch, start, length));
                lat = false;
            }

        } else if (lng) {
            if(lngVal == 0){
                String lngValStr = new String(ch, start, length);
                lngVal = Double.parseDouble(lngValStr);
                System.out.println("lng: " + new String(ch, start, length));
                lng = false;
            }
        }
    }

    //GETTERS
    static Node getCords(){
        return cords;
    }
}