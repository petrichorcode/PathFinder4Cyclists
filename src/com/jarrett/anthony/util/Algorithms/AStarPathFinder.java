package com.jarrett.anthony.util.Algorithms;

import com.jarrett.anthony.Components.Neighbour;
import com.jarrett.anthony.Components.Node;
import com.jarrett.anthony.Mainframe.ParserHandler;
import com.jarrett.anthony.Mainframe.RoadNetwork;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * A* algorithm with a BinaryHeap datastructure implementation.
 * Implementation credit goes to: https://github.com/kevinwang1975/PathFinder
 */
public class AStarPathFinder {
    private static Node[] adjacencies = RoadNetwork.getAdjLists();
    private static ArrayList<Node> stack = new ArrayList();
    private int counter = 0; //for debugging purposes
    private static double cost =0;
    private static final BinaryHeap<Node> binaryHeap = new BinaryHeap<>(new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return (int) (o1.getCost()  - (o2.getCost()));
        }
    });


    public static void searchPath(String start, String end){
        try{
            //maybe do something with the return value if it's false/true?
            search(start, end);
        }
        finally{
            binaryHeap.clear();
        }
    }
    /**
     * @param start
     * @param end
     * @return true if a path is found, false otherwise
     */
    private static boolean search(String start, String end){
        System.out.println(start);
        Node nodeS = (Node) ParserHandler.getMap().get(start);
        Node endMod = (Node) ParserHandler.getMap().get(end);
        nodeS.setVisited(true);
        nodeS.setCost(0);

        binaryHeap.add(nodeS);
        while(binaryHeap.size() > 0){
            //take the node with the lowest cost and check all outgoing connections
            Node node = binaryHeap.remove();
            node.setOpen(false);
            String st = String.valueOf(node.getId());
            //Need to get the neighbours of Node (start)
            int i = RoadNetwork.indexForName(st); //changed from start to st
            // makes more logical sense, may have been bug previously - st is now the last popped value,
            // which has all its neighbours searched for
            for (Neighbour nbr = adjacencies[i].adjList; nbr != null; nbr = nbr.next) {
                String candidate1 = adjacencies[nbr.vNum].idNum;
                Node candidate = (Node) ParserHandler.getMap().get(candidate1);
                if(!candidate.isOpen()){
                    continue;
                }
                // get the cost of travel to this node
                //System.out.println("Test 2 "+ adjacencies[nbr.vNum].adjList.vNum);
                try {
                    cost = adjacencies[nbr.vNum].adjList.getWeight();
                    //System.out.println("Vnum "+adjacencies[nbr.vNum].adjList.vNum); //UC
                    //counter++; // UC
                    //System.out.println(counter); //UC
                    //System.out.println(cost); //UC
                } catch (NullPointerException e) {
                    System.out.print("Caught the NullPointerException");
                }

                 //error here is due to an input not having a weight
                // verify weights, but first address multiple entries within adjlist which may address issue.
                //this is what's breaking the program
                //int cost = 1;
                if(!candidate.isVisited()){
                    candidate.setVisited(true);
                    candidate.setCost(cost);
                    candidate.setPredecessor(node); //this allows us to make a trace back
                    binaryHeap.add(candidate);
                    // if it has been visited before, in which case it will have a cost
                } else if(cost < candidate.getCost()){
                    candidate.setCost(cost);
                    candidate.setPredecessor(node);
                    //cost has been changed so heap needs to be rearranged
                    binaryHeap.remove(candidate);
                    binaryHeap.add(candidate);
                }
            }
            // reached destination
            if(binaryHeap.contains(endMod)){
                markPath(nodeS,endMod);
                return true;
            }
        }
        return false; //path not found
    }
    /**
     * Mark all nodes which form the path using the predecessors
     * @param start
     * @param end
     */
    private static void markPath(Node start, Node end){
        Node node = end;

        while(node != start){
            stack.add(node);
            node = node.getPredecessor();
        }
        stack.add(start);
        returnPath();
    }

    private static void returnPath() {
        for (int p = stack.size()-1; p >= 0; p--){
            System.out.println("The path follows: " + stack.get(p).getId());
            stack.get(p).reset();
        }
    }
}
