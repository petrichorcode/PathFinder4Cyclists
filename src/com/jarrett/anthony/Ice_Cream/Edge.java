package com.jarrett.anthony.Ice_Cream;

import java.util.ArrayList;
import java.util.List;

/**
 * Edge class Object created for the purpose of testing Ice-Cream problem, not practically applicable
 * outside this scope.
 */
public class Edge {

    //ATTRIBUTES
    private int wayID;
    private String startID;
    private int endID;
    /** The list of adjacent nds **/
    private List<String> nodes;

    //CONSTRUCTOR
    public Edge(int id) {
        this.wayID = id;
        this.nodes = new ArrayList<String>();


    }

    public Edge(int id, List<String> nodes){
        this.wayID = id;
        this.nodes = nodes;
    }

    public Edge(String startID, int endID, int wayID){
        this.wayID = wayID;
        this.endID = endID;
        this.startID = startID;
    }

    //SETTERS
    public void setWayId(int wayID){
        this.wayID = wayID;
    }

    public void setStartID(String startID){
        this.startID = startID;
    }

    public void setEndID(int endID){
        this.endID = endID;
    }

    //GETTERS
    public int getWayID(){
        return wayID;
    }

    public String getStartID(){
        return startID;
    }

    public int getEndId(){
        return endID;
    }

    public void addNodes(String nd){
        nodes.add(nd);
    }

    /**
     * @return The list of nodes of the way
     */
    public List<String> getNodes(){

        return nodes;
    }
}
