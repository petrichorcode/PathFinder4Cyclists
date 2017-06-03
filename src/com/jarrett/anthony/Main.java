package com.jarrett.anthony;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jarrett.anthony.API_Pollution.SensorsAPI;
import com.jarrett.anthony.Components.Element;
import com.jarrett.anthony.Mainframe.ParserHandler;
import com.jarrett.anthony.Mainframe.RoadNetwork;
import org.xml.sax.SAXException;
import static com.jarrett.anthony.Mainframe.SearchMethods.awaitingQuery;

/**
 * This class parses OSM XML
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("/home/flower/Documents/PF/src/com/jarrett/anthony/northLondon.xml");
        Map<String,Element> result;
        //Check input
        if(!input.exists() || !input.isFile() || !input.canRead()) {
            System.out.println("Can't read input OSM XML.\nCheck if file exists and is readable.");
        }
        else {
            //Convert data
            //Initialisation of the parser
            ParserHandler parser = new ParserHandler();
            RoadNetwork adj = new RoadNetwork();
            System.out.println("\u001b[1m\u001b[32m"+"Processing...");
            try{
                SensorsAPI.parseSensors();
                //Parse OSM data
                result = parser.parse(input);
                //PollutionHandler.printStatistics();
                ParserHandler.printStatistics(result);
                ConcurrentHashMap<String, Integer> nodeL = parser.printNodeL();
                adj.storeNodes(nodeL);
                adj.storeWays(result);
                adj.populateAdjList();
                adj.populateAdj(result);
                adj.printM();
                awaitingQuery();

            } catch (IOException | SAXException e){
                e.printStackTrace();
            }
        }
    }
}