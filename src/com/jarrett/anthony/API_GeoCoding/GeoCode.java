package com.jarrett.anthony.API_GeoCoding;

import com.jarrett.anthony.Components.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Google states that geocoding is a "time and resource intensive task". Therefore, whenever possible,
 * pre-geocode known addresses should stored in a temporary cache.
 */

/**
 * HTTP VS HTTPS
 * An important security consideration when tracking the users location, as opposed to taking input
 * requires different procedures...(fill)
 */
public class GeoCode {
    //Unique API key with capped use, provided by Google.
    static final String apiKey = "AIzaSyBRtGhmwcSQ7PpqUql4r2IywNDieytjZkw";

    /**
     * @param address is the location made by the user query
     */
    public static Node addrSearch(String address){
        /** This HTTP request takes an address location
         * E.g: Goldsmiths University of London and returns its co-ordinates **/
        String URL_SOURCE = "https://maps.googleapis.com/maps/api/geocode/xml?address="+address+"&key="+apiKey;

        // Create the URL:
        String query = URL_SOURCE;

        /* Make the request */
        try{
            //Turn the string into a URL object
            URL urlObject = new URL(query);
            //Open the stream (returns an InputStream):
            InputStream in = urlObject.openStream();

            /* Now parse the data that we receive */
            // Create an XML reader
            XMLReader xr = XMLReaderFactory.createXMLReader();

            //Tell the XML reader to use GeoCodeHandler
            GeoCodeHandler handler =  new GeoCodeHandler();
            xr.setContentHandler(handler);

            //Wrap it in InputSource, SAX parser prefers this way
            InputSource inSource = new InputSource(in);

            //Parse it
            xr.parse(inSource);
        }

        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

        catch (SAXException se)
        {
            se.printStackTrace();
        }
        return GeoCodeHandler.getCords();
    }
}
