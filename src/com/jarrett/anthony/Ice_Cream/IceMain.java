package com.jarrett.anthony.Ice_Cream;

/* When running from Intellij, program configurations need to be changed
 for the main to reassigned */
/** This Particular package tests against the Ice-Cream problem
 * - which as of current has been disproven to exist **/


import org.xml.sax.SAXException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;


public class IceMain {

    public static void main(String []args) throws FileNotFoundException{
        File input = new File("/home/flower/Documents/PF/src/com/jarrett/anthony/northLondon.xml");
        Map<String, Integer> result;

        //Check input
        if (!input.exists() || !input.isFile() || !input.canRead()) {
            System.out.println("Can't read input OSM XML.\nCheck if file exists and is readable.");

        } else {
            //Convert data
            //Initialisation of the parser
            IceParserHandler parser = new IceParserHandler();
            System.out.println("\u001b[1m\u001b[32m" + "Processing...");
            System.out.println("Within Ice_Main");
            try {

                result = parser.parse(input);

            } catch (IOException | SAXException e) {
                e.printStackTrace();
            }
        }
    }
}
