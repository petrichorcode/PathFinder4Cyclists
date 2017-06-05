package com.jarrett.anthony.Ice_Cream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by flower on 04/06/17.
 */
public class IceParserHandler extends DefaultHandler{

    //ATTRIBUTES
    /** The parsed OSM elements **/
    private static Map<String,Integer> elements;

    /** The current read element **/
    private Edge current;

    //CONSTRUCTOR
    public IceParserHandler() {
        super();
    }

    //OTHER METHODS
    /**
     * Parses a XML file and creates OSM Java objects
     * @param f The OSM database extract, in XML format, as a file
     * @return The corresponding OSM objects as a Map. Keys are elements ID, and values are OSM elements objects.
     * @throws IOException If an error occurs during file reading
     * @throws SAXException If an error occurs during parsing
     */
    public Map<String, Integer> parse(File f) throws IOException, SAXException {
        //File check
        if (!f.exists() || !f.isFile()) {
            throw new FileNotFoundException();
        }

        if (!f.canRead()) {
            throw new IOException("Can't read file");
        }

        return parse(new InputSource(new FileReader(f)));
    }

    /**
     * Parses a XML input and creates OSM Java objects
     * @param input The OSM database extract, in XML format, as an InputSource
     * @return The corresponding OSM objects as a Map. Keys are elements ID, and values are OSM elements objects.
     * @throws IOException If an error occurs during reading
     * @throws SAXException If an error occurs during parsing
     */
    private Map<String, Integer> parse(InputSource input) throws SAXException, IOException {
        //Init elements set
        elements = new HashMap<>();

        // Start parsing
        XMLReader xr = XMLReaderFactory.createXMLReader();
        xr.setContentHandler(this);
        xr.setErrorHandler(this);
        xr.parse(input);

        return elements;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws org.xml.sax.SAXException {
        super.startElement(uri, localName, qName, attributes);

        //Case of way
        if (localName.equals("way")) {
            Edge e = new Edge(Integer.parseInt(attributes.getValue("id")));
            current = e;
        }
        //Case of way node
        else if (localName.equals("nd")) {
            current.addNodes(attributes.getValue("ref"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws org.xml.sax.SAXException {
        super.endElement(uri, localName, qName);

        if(localName.equals("way")) {
            //Add element to list, and delete current
            if(current != null) {
                if( (localName.equals("way")) && current.getNodes() != null) {
                    //change this so that the first parameter is a concatenation of the first and last NodeId
                    if(!(elements.containsKey(current.getNodes().get(0)+
                            current.getNodes().get(current.getNodes().size()-1)))) {
                        elements.put(current.getNodes().get(0) + current.getNodes().get(current.getNodes().size()-1),
                                current.getWayID());
                        //System.out.println("Not dupe");
                    }
                }else{
                    System.out.println("Found dupe");
                }
                current = null;
            }
        }
    }
}
