package com.jarrett.anthony.API_Pollution;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SensorsAPI {

    /** Kings College Environmental Research Groups API
     * This HTTP Request returns all Pollution sensor sites in London along with their Readings and locations **/
    private static final String URL_SOURCE = "http://api.erg.kcl.ac.uk/AirQuality/Daily/MonitoringIndex/Latest/GroupName=London";

    public static void parseSensors(){

        /* Make the request */
        try{
            //Turn the HTTP Query into a URL object
            URL urlObject = new URL(URL_SOURCE);
            //Open the stream (returns an InputStream):
            InputStream in = urlObject.openStream();

            /* Now parse the data that we receive */
            // Create an XML reader
            XMLReader xr = XMLReaderFactory.createXMLReader();

            //Tell the XML reader to use PollutionHandler
            PollutionHandler handler =  new PollutionHandler();
            xr.setContentHandler(handler);

            //Wrap it in InputSource, SAX parser prefers this way
            InputSource inSource = new InputSource(in);

            //Parse it
            xr.parse(inSource);
            PollutionHandler.printStatistics();

        }

        catch(IOException | SAXException ioe)
        {
            ioe.printStackTrace();
        }
    }


}
