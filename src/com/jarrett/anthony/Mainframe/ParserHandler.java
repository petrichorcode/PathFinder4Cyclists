package com.jarrett.anthony.Mainframe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jarrett.anthony.Components.Element;
import com.jarrett.anthony.Components.Node;
import com.jarrett.anthony.Components.Way;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * OSMParser parses XML file and creates corresponding Java objects.
 */
public class ParserHandler extends DefaultHandler {

    //ATTRIBUTES
    /** The parsed OSM elements **/
    private static Map<String,Element> elements;
    /** A List of Nodes within Ways with along with the number of occurances **/
    static private ConcurrentHashMap<String, Integer> nodeL = new ConcurrentHashMap();

    /** The current read element **/
    private Element current;

    //CONSTRUCTOR
    public ParserHandler() {
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
    public Map<String,Element> parse(File f) throws IOException, SAXException {
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
    private Map<String,Element> parse(InputSource input) throws SAXException, IOException {
        //Init elements set
        elements = new HashMap<>();

        // Start parsing
        XMLReader xr = XMLReaderFactory.createXMLReader();
        xr.setContentHandler(this);
        xr.setErrorHandler(this);
        xr.parse(input);

        return elements;
    }

    /**
     * Get an object ID, in this format: X000000, where X is the object type (N for nodes, W for ways).
     * @param type The object type (node or way)
     * @param ref The object ID in a given type
     * @return The object ID, unique for all types
     */
    private String getId(String type, String ref) {
        String result = null;
        switch(type) {
            case "node":
                result="N"+ref;
                break;
            case "way":
                result="W"+ref;
                break;
            default:
                throw new RuntimeException("Unknown element type: "+type);
        }

        return result;
    }

    @Override
    public void endElement(String  uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if(localName.equals("node") || localName.equals("way")) {
            //Add element to list, and delete current
            if(current != null) {
                if( (localName.equals("way") && ((Way) current).getNodes().size() >= 2)
                        || localName.equals("node")) {
                    elements.put(current.getId(), current);
                }
                current = null;
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        //Case of node
        if(localName.equals("node")) {
            Node n = new Node(
                    Long.parseLong(attributes.getValue("id")),
                    Double.parseDouble(attributes.getValue("lat")),
                    Double.parseDouble(attributes.getValue("lon"))
                    );
            current = n;
            nodeL.put(String.valueOf(attributes.getValue("id")),0);
        }
        //Case of way
        else if(localName.equals("way")) {
            Way w = new Way(Long.parseLong(attributes.getValue("id")));
            current = w;
        }
        //Case of way node
        else if(localName.equals("nd")) {
            ((Way) current).addNode((Node) elements.get("N"+attributes.getValue("ref")));

            //Case of Node within way, make a count
            if (localName.equals("nd") && nodeL.containsKey(attributes.getValue("ref"))){
                nodeL.put((attributes.getValue("ref")),nodeL.get(attributes.getValue("ref")) + 1);
            }
        }
        //Case of tag
        else if(localName.equals("tag")) {
            if(current != null) {
                current.addTag(attributes.getValue("k"), attributes.getValue("v"));
            }
        }
    }

    /**
     * Displays some statistics about given elements
     * @param elements The elements
     */
    public static void printStatistics(Map<String,Element> elements) {
        int nbNodes = 0, nbWays = 0;

        for(Element e : elements.values()) {
            if(e instanceof Node) { nbNodes++; }
            else if(e instanceof Way) { nbWays++; }
        }

        System.out.println("= Elements statistics =");
        System.out.println("* Nodes:\t"+nbNodes);
        System.out.println("* Ways:\t\t"+nbWays);
    }

    /** Check the count for all th Nodes that occur in Ways
     * and only keep those that occur twice or more, thus representing an intersection **/
    public ConcurrentHashMap<String, Integer> printNodeL(){
        for (Map.Entry entry : nodeL.entrySet()) {
            Integer bob = (Integer) entry.getValue();
            if(bob < 2){
                nodeL.remove(entry.getKey());            }
        }

        return nodeL;
    }

    //GETTERS
    public static Map<String,Element> getMap() {
        return elements;
    }

    public static ConcurrentHashMap<String, Integer> getNodeL() {
        return nodeL;
    }
}
