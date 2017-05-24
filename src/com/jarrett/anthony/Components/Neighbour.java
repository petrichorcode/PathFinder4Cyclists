package com.jarrett.anthony.Components;

/**
 * Created by antho on 14/04/2017.
 */
public class Neighbour {

    //ATTRIBUTES
    public int vNum;
    public Neighbour next;
    private int weight;


    //CONSTRUCTORS
    /**
     *	@param vNum The object ID
     *	@param nbr The neighbour
     *	@param weight The weight between object and its neighbour
     */
    public Neighbour(int vNum, Neighbour nbr, int weight){
        this.weight = weight;
        this.vNum = vNum;
        next = nbr;
    }
    /**
     *	@param vNum The object ID
     *	@param weight The weight between object and its neighbour
     */
    public Neighbour(int vNum, int weight) {
        this.weight = weight;
        this.vNum = vNum;
    }

    //GETTERS
    /**
     *	@return  weight the weight of an object and its respective neighbour
     */
    public int getWeight() {
        return weight;
    }


}
