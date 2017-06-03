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
        private static Node[] adjLists= new Node[intersections.size()];
        private Set<Map.Entry<String, Element>> waySet;



        /* Cycle through Map to find elements which are Nodes */
        public void storeNodes(Map<String, Integer> inter) {
            for (String key : inter.keySet()) {
                  key = "N" + key;
                intersections.add(key);
                }
            //Number of intersections on my map
            adjLists = new Node[intersections.size()];

            System.out.println("Number of intersections: " +intersections.size());
        }

        /* A bit of Java 8 Action using the Lambda Expression
         *  I copy the elements Map into a new Map and then filter
         *  out everything that isn't a Way */
        public void storeWays(Map<String, Element> result) {
            wayList = new HashMap<>(result);
            wayList.keySet().removeIf(key -> !key.startsWith("W"));
            waySet = wayList.entrySet();

        }


        //could later remain to populateAdj
        public void populateAdjList(){
            for (int i = 0; i < intersections.size(); i++) {
                String bob = String.valueOf(intersections.get(i));
                adjLists[i] = new Node(bob, null);
            }
        }

        private void populateAdj2(){

        }
        /* Population of adjacency list,
      prior to neighbour computation */
        public void populateAdj(Map<String, Element> result) {
            /* this process could probably be made more efficient
            * Currently at O(n), perhaps O(1) is possible */
            for (int i = 0; i < intersections.size(); i++) {
                //tempList gets reset
                tempList.clear();
                for (Map.Entry wayEntry : waySet) {
                    Way w1 = (Way) wayList.get(wayEntry.getKey());
                    //Checking for nodes within Ways which match our intersection(i)
                    for (Node n : w1.getNodes()) {
                        if(n !=null)
                            container2 = n.getId();
                            //if we've come across container2 b4, don't chase it up again, so increment the n??
                            //have a list for them, will be of length intersections

                        //Do any of the ways in this given way (w1) equal our intersection(i)
                        if (container2.contains(intersections.get(i))) {
                            //Yes to above, it looks like we have found a way with an intersection
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
            for (Node adjList : adjLists) {
                System.out.print("Head " + adjList);
                //System.out.println("he?"+adjLists[v].adjList+"he?");
                for (Neighbour nbr = adjList.adjList; nbr != null; nbr = nbr.next) {
                    System.out.print(" --> " + adjLists[nbr.vNum]);
                }
                System.out.println();
            }
        }

        public static Node[] getAdjLists() {
            return adjLists;
        }

        static ArrayList<String> getIntersections() {
            return intersections;
        }
        }


