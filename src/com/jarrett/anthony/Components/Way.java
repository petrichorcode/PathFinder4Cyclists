package com.jarrett.anthony.Components;

/**
 * Created by User on 29/03/2017.
 */

import com.jarrett.anthony.Components.Element;
import com.jarrett.anthony.Components.Node;

import java.util.ArrayList;
import java.util.List;


/**
 * A way is an OSM element which connects to nodes to create a path.
 */

public class Way extends Element {

    //ATTRIBUTES
    /** The list of adjacent nodes **/
    private List<Node> nodes;

    //CONSTRUCTOR
    /**
     * Default constructors
     * @oaram id the object ID
     * @param nodes Its nodes
     */
    public Way(long id, List<Node> nodes){
        super(id);

        //Conditions on nodes
        if(nodes == null){
            throw new NullPointerException("Nodes list can't be null");
        }
        if(nodes.size() < 2){
            throw new RuntimeException("A way should have at least two nodes");
        }

        this.nodes = nodes;
    }


    /**
     * Constructor without nodes
     * Should be used only if at least two nodes will be added
     * 	 * @param id The object ID
     */
    public Way(long id){
        super(id);
        this.nodes = new ArrayList<Node>();
    }

    //ACCESSORS
    @Override
    public String getId(){

        return "W"+id;
    }

    /**
     * @return The list of nodes of the way
     */
    public List<Node> getNodes(){

        return nodes;
    }

    //MODIFIERS
    /**
     * @param n The node to add at the end of the way
     */
    public void addNode(Node n){

        nodes.add(n);
    }

    /**
     * @param index The index of the node to remove
     */
    public void removeNode(int index){
        if(nodes.size() == 2){
            throw new RuntimeException("Can't remove node, only two remaining");
        }
        nodes.remove(index);
    }
}