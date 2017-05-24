package com.jarrett.anthony.Components;

/**
 * An abstract class for a generic OSM object
 */
import java.util.HashMap;
import java.util.Map;

public abstract class Element{

//ATTRIBUTES
    /** The object ID, unique per object type **/
    protected long id;
    /** The objects tags, which describe it **/
    protected Map<String,String> tags;

//CONSTRUCTOR
    // Alt
    public Element(){
    }

    /**
     *Default constructor, initialises ID
     * @param id the element ID
     */
    public Element(long id){
        this.id = id;
        tags = new HashMap<String,String>();
    }


//ACCESSORS
    /**
     * @return the ID, format: X00000, where X is the object type (N: node, W: way, R: relation)
     */
    public abstract String getId();

    /**
     * @return the tags
     */
    public Map<String,String> getTags(){
        return tags;
    }

    @Override
    public String toString(){
        return "Element "+getId()+" ("+getTags()+")";
        //return getId();
    }

//MODIFIERS
    /**
     * Add a tag
     * @param key The tag key
     * @param value the tag value
     */
    public void addTag(String key, String value){
        tags.put(key, value);
    }

    /**
     * Remove the given tag
     * @param key The tag key
     */
    public void deleteTag(String key){
        tags.remove(key);
    }

}