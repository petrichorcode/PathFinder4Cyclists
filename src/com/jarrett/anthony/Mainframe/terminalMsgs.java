package com.jarrett.anthony.Mainframe;

/**
 * This interface serves the purpose of enabling code re-use
 * and clears up the space taken up by large Strings.
 */
public interface terminalMsgs {

    //Welcome String
    String welcome = "\u001b[1m\u001b[34m"+"Welcome to the Path Finder from the Terminal..\n" +
            "Please choose to enter a Node \"N\", Geolocation \"G\" or PostCode/Address \"P\"";

    //Case NodeSearch String
    String okNode = "Now enter your start and end points, with a space separator...";

    //Case GeoSearch String
    String okGeo = "Now enter your start & finish lat/lon points with single space separators on one line...";

    //Case PostalSearch String
    String okPostal = "Now enter your start and end address on two seperate lines...";

    //Case AnotherSearch String
    String anotherSearch = "Would you like to make another search?\n" +
            "If Yes, Type \"N\" for Node | \"G\" for Geolocation or " +
            "| \"P\" for PostCode/Address\nElse, please type E to exit";

    //Case Exit String
    String thankYouQuote = "\u001b[1m\u001b[32m"+"Thank you for using the Path Finder.\n" +
            "Brought to you by, Anthony Jarrett.\n"+
            "\033[1m" +
            "\"The people should have a choice for the air that they breathe\"\033[0m";

}
