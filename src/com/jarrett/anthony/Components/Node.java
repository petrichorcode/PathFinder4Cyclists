package com.jarrett.anthony.Components;

/**
 * Created by User on 29/03/2017.
 */

/*
 * A node is an OSM element with a unique ID and coordinates.
 */

public class Node extends Element {
    //ATTRIBUTES
    private double lat;
    private double lon;
    private boolean open =  true;
    private boolean visited = false;
    private Node predecessor;
    private double cost = 0;
    public Neighbour adjList;
    public String idNum;



//CONSTRUCTOR

    //Alt
    public Node() {

    }

    /**
     *	@param id The object ID
     *	@param lat The latitude
     *	@param lon The longitude
     */

    public Node(long id, double lat, double lon){
        super(id);
        this.lat = lat;
        this.lon = lon;
    }

    /**
     *	@param lat The latitude
     *	@param lon The longitude
     */
    public Node(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * @param idNum of this Node
     * @param neighbours the ID of the next Node(Outgoing)
     */
    public Node(String idNum, Neighbour neighbours){
        this.idNum = idNum;
        this.adjList = neighbours;
    }

    //ACCESSORS
    @Override
    public String getId(){
        return "N"+id;
    }

    /**
     * @return the latitude
     */
    public double getLat(){
        return lat;
    }

    /**
     * @return the longitude
     */
    public double getLon(){
        return lon;
    }

    /**
     * {@inheritDoc}
     */
    public Node getPredecessor() {
        return predecessor;
    }

    /**
     * {@inheritDoc}
     */
    public double getCost() {
        return this.cost;
    }

    public String getIdNum(){
        return idNum;
    }


//MODIFIERS
    /**
     * @param lat the new latitude
     */
    public void setLat(double lat){
        this.lat = lat;
    }

    /**
     * @param lon the new longitude
     */
    public void setLon(double lon){
        this.lon = lon;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * {@inheritDoc}
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * {@inheritDoc}
     */
    public void setPredecessor(Node node) {
        this.predecessor = node;
    }

    /**
     * {@inheritDoc}
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * {@inheritDoc}
     * @param cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * reset to the original values.
     */
    public void reset() {
        predecessor = null;
        cost = 0;
        open = true;
        visited = false;
    }

    @Override
    public String toString(){
        return getIdNum();
    }

}

