    package com.jarrett.anthony.Mainframe;

    import com.jarrett.anthony.Components.Element;
    import com.jarrett.anthony.Components.Neighbour;
    import com.jarrett.anthony.Components.Node;
    import com.jarrett.anthony.Components.Way;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.Set;


    /**
     * Road Network constructs the adjacency list using the Node and Way elements
     */

    public class RoadNetwork {
        private Map<String, Element> wayList;
        private static ArrayList<String> intersections = new ArrayList<>();
        private String container2 = "";
        private String container3;
        private ArrayList<String> tempList = new ArrayList<>();
        private int counter = 0;
        private static Node[] adjLists;



        /* Cycle through Map to find elements which are Nodes */
        public void storeNodes(Map<String, Integer> inter) {
            for (String key : inter.keySet()) {
                  key = "N" + key;
                intersections.add(key);
                }
            //Number of intersections on my map
            System.out.println("Number of intersections: " +intersections.size());
        }

        /* A bit of Java 8 Action using the Lambda Expression
         *  I copy the elements Map into a new Map and then filter
         *  out everything that isn't a Way */
        public void storeWays(Map<String, Element> result) {
            wayList = new HashMap<>(result);
            wayList.keySet().removeIf(key -> !key.startsWith("W"));
        }

        /* Population of adjacency list,
      prior to neighbour computation */
        public void populateAdj(Map<String, Element> result) {
            adjLists = new Node[intersections.size()];
            /* this process could probably be made more efficient
            * Currently at O(n), perhaps O(1) is possible */
            for (int i = 0; i < intersections.size(); i++) {
                String bob = String.valueOf(intersections.get(i));
                adjLists[i] = new Node(bob, null);
            }
            for (int i = 0; i < intersections.size(); i++) {
                //tempList gets reset
                tempList.clear();
                Set<Map.Entry<String, Element>> entrySet = wayList.entrySet();
                for (Map.Entry entry : entrySet) {
                    Way w1 = (Way) wayList.get(entry.getKey());
                    //Checking for nodes within Ways which match our intersection(i)
                    for (Node n : w1.getNodes()) {
                        if(n !=null) {
                            container2 = n.getId();
                            //if we've come across container2 b4, don't chase it up again, so increment the n??
                            //have a list for them, will be of length intersections
                        }
                        //Do any of the ways in this given way (w1) equal our intersection(i)
                        if (container2.contains(intersections.get(i))) {
                            //Yes to above, it looks like we have found a way which our intersection exists in
                            //SO, lets find the other intersection which belongs on that Way
                            //This means, searching through same way using our intersections list
                            for(Node n2 : w1.getNodes()) {
                                if(n2 !=null) {
                                    container3 = n2.getId();
                                }
                                if(intersections.contains(container3)&& !container3.equals(container2)){
                                    //put item into tempList tempList
                                    tempList.add(container3);
                                    //System.out.println("TAG PRINT "+w1.getTags().get("highway")+w1.getTags().get("maxspeed"));
                                    //check we haven't picked up a dupe
                                    int count = 0;
                                    for(String temp : tempList){
                                        if(temp.equals(container3))
                                            count++;
                                    }
                                    if (count >= 2)
                                        break;

                                    int v1 = indexForName(container2);
                                    int v2 = indexForName(container3);

                                    Node n3 = (Node) result.get(container2);
                                    Node n4 = (Node) result.get(container3);
                                    int meterDist = (int) WeightCalcs.weight(n3,n4, w1.getTags().get("highway"));//this is in meters
                                   //if a neighbour exists, don't insert again
                                    adjLists[v1].adjList = new Neighbour(v2, adjLists[v1].adjList, meterDist);
                                    counter++;
                                        /* Debugging purposes, checking for correct adjacencies amongst nodes */
                                    //System.out.println("Container3: "+container3);
                                    //System.out.println("Container2: "+container2);
                                    //System.out.println("Way: " +w1);
                                    //System.out.println(container3+"Is adjacent to " + container2+" node");
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(counter);
        }

        public static int indexForName(String name){
            for(int v=0; v < adjLists.length; v++){
                String bob1 = String.valueOf(adjLists[v]);
                if(name.contains(bob1)){
                    return v;
                }
            }
            return -1;
        }

        public void printM() {
            System.out.println();
            for (int v=0; v < adjLists.length; v++) {
                System.out.print("Head " +adjLists[v]);
                //System.out.println("he?"+adjLists[v].adjList+"he?");
                for (Neighbour nbr = adjLists[v].adjList; nbr != null; nbr = nbr.next) {
                    System.out.print(" --> " + adjLists[nbr.vNum]);
                }
                System.out.println();
            }
        }

        public static Node[] getAdjLists() {
            return adjLists;
        }

        public static ArrayList<String> getIntersections() {
            return intersections;
        }

        //This method will put together the adjacent neighbours
    //Requirements for an intersection/convergence of ways is that a node be represented within two separate ways.
        //Rule: In order for it to be an intersection, Node must be present within a minimum of two ways, else void it
        /* The way this search will work
        * 1) Take each Node element one by one within list of Nodes
        * 2) Search through all ways for this Node and store the Ways which it is present in.
        * 3) If it belongs in more than two ways, then its an intersection
        * 4) Then the next adjacent intersection along the ways must be found
        * 5) This requires a similar fashioned search
        * 5) Once found, those can be stored as the Neighbouring intersections for given Node.
        * 6) This will also require doing cost calculation (distance) when inputting nodes adjList
        * 7) Then repeat for next nodes...
        * */


     /*   public String findIntersections(String id) {
            Set<Map.Entry<String, Element>> entrySet = wayList.entrySet();
            for (Map.Entry entry : entrySet) {
                w1 = (Way) wayList.get(entry.getKey());
                for (Node n : w1.getNodes()) {
                    container2 = String.valueOf(n);
                    if (container2.contains(id)) {
                        count++;
                    }
                    if (count == 2) {
                        inter = id;
                        count = 0;
                    }
                }
            }
            if (inter != (null))
                return inter;
            else
                return null;
        }
        */


        /*public void findAdjacents() {
            for (int i = 0; i < nodeList.size(); i++) {
                count = 0;
                inter = null;
                container = findIntersections(nodeList.get(i));

                if (container != null) {
                    intersections.add(container);
                }
            }
            }*/
        }


