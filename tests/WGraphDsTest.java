package ex1.tests;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class WGraphDsTest {
    private int node,numberOfNodes,numberOfEdges;
    private double weight = 5.5;
    private weighted_graph testWGraphDS;
    private int seed = 2;
    private Random random = new Random(seed);

    void initGraph(weighted_graph g){
        testWGraphDS = g;
    }

    @BeforeEach
    void init(){
        testWGraphDS = new WGraph_DS();
        initGraph(testWGraphDS);
    }
    @Test
    void getNode() {
        testWGraphDS.addNode(6);
        testWGraphDS.addNode(7);
        node_info testingNode = testWGraphDS.getNode(4);
        Assertions.assertNull(testingNode, "The node is not in the graph, required to be null");
        testingNode = testWGraphDS.getNode(6);
        Assertions.assertNotNull(testingNode, "Shouldn't be null");
        Assertions.assertEquals(6, testingNode.getKey(), "Required to be 6");
        testingNode = testWGraphDS.getNode(7);
        Assertions.assertEquals(7, testingNode.getKey(), "Required to be 7");
        testWGraphDS.addNode(14);
        Assertions.assertNotNull(testWGraphDS.getNode(14),"Shouldn't be null");
        testWGraphDS.removeNode(14);
        Assertions.assertNull(testWGraphDS.getNode(14),"Required to be null");
    }
    @Test
    void hasEdge() {
        testWGraphDS.addNode(3);
        testWGraphDS.connect(1, 3, 8.2);
        Assertions.assertFalse(testWGraphDS.hasEdge(1, 3), "There is no such node in the graph, should be false");
        testWGraphDS.addNode(0);
        testWGraphDS.addNode(1);
        testWGraphDS.connect(1, 0, 4.3);
        Assertions.assertFalse(testWGraphDS.hasEdge(1, 2), "Required to be false");
        testWGraphDS.connect(0, 3, 8.2);
        Assertions.assertTrue(testWGraphDS.hasEdge(0, 3), "Required to be true");
        Assertions.assertTrue(testWGraphDS.hasEdge(3, 0), "Required to be true");
        Assertions.assertFalse(testWGraphDS.hasEdge(1, 3), "Required to be false");
        testWGraphDS.removeEdge(0, 3);
        Assertions.assertFalse(testWGraphDS.hasEdge(0, 3), "Required to be false, the edge deleted from the graph");
        Assertions.assertFalse(testWGraphDS.hasEdge(3, 0), "Required to be false, the edge deleted from the graph");
    }

    @Test
    void getEdge() {
        testWGraphDS.addNode(0);
        testWGraphDS.addNode(1);
        testWGraphDS.addNode(2);
        testWGraphDS.addNode(9);
        testWGraphDS.connect(0, 1, 6.4);
        Assertions.assertEquals(6.4, testWGraphDS.getEdge(0,1), "Required to be 6.4");
        Assertions.assertEquals(-1, testWGraphDS.getEdge(0, 2), "There is no such edge in the graph, should be -1");
        Assertions.assertEquals(-1, testWGraphDS.getEdge(2, 6), "There is no such node in the graph, should be -1");
        testWGraphDS.connect(2, 9, 9.3);
        Assertions.assertEquals(9.3, testWGraphDS.getEdge(2, 9), "Required to be 9.3");
        Assertions.assertEquals(9.3, testWGraphDS.getEdge(9, 2), "Required to be 9.3");
    }

    @Test
    void addNode() {
             numberOfNodes = 1000;
            for(int node = 0; node < numberOfNodes; node++) {
                testWGraphDS.addNode(node);
            }
        assertEquals(testWGraphDS.nodeSize(),numberOfNodes);
    }

    @Test
    void connect() {
         numberOfNodes = 10090;
        for(int node = 0; node < numberOfNodes; node++) {
            testWGraphDS.addNode(node);
        }
        int numberOfEdges = 300;
        for(node = 0; node < numberOfEdges ; node++) {
            testWGraphDS.connect(node,node + 1, weight);
        }

        Assertions.assertTrue(  testWGraphDS.hasEdge(79,80), "Required to be true");
        assertEquals(testWGraphDS.edgeSize(),numberOfEdges);
    }

    @Test
    void getV() {
         numberOfNodes = 500;
        for (node = 0; node < numberOfNodes; node++) {
            testWGraphDS.addNode(node);
        }
        HashMap <Integer, node_info> Nodes_Graph = ((WGraph_DS) testWGraphDS).get_Node_Graph();

        for (node_info node : testWGraphDS.getV()) {
            assertTrue(Nodes_Graph.containsKey(node.getKey()),"There are missing nodes");
        }
    }
    @Test
    void GetV() {
         numberOfNodes = 654;
        for(node = 0 ; node < numberOfNodes; node++){
            testWGraphDS.addNode(node);
        }
        HashMap<node_info, Double> Neighbours = new HashMap<>();
        int nextNode = random.nextInt(numberOfNodes);

        for(node = 0; node < 155; node++){
            int thisNode = random.nextInt(numberOfNodes);
            if ( !Neighbours.containsKey(testWGraphDS.getNode(thisNode)) && thisNode != nextNode ) {
            testWGraphDS.connect(nextNode, thisNode, weight);
            Neighbours.put(testWGraphDS.getNode(thisNode), weight);}
        }
        Collection<node_info> neighbors = testWGraphDS.getV(nextNode);
        Iterator<node_info> iterator = neighbors.iterator();
        while (iterator.hasNext()) {
            node_info neighbor = iterator.next();
            assertTrue(Neighbours.containsKey(neighbor),"There are missing nodes");
        }
    }

    @Test
    void removeNode() {
         numberOfNodes = 444;
        for(node = 0; node < numberOfNodes; node++) {
            testWGraphDS.addNode(node);
        int randomNode1,randomNode2,counter = 0;
        while(counter < numberOfNodes/2) {
            randomNode1 = random.nextInt(numberOfNodes) ;
            randomNode2 = random.nextInt(numberOfNodes);
            if(randomNode1!=randomNode2) {
                testWGraphDS.removeNode(randomNode1);
                assertNull(testWGraphDS.getNode(randomNode1), "Required to be null");
                testWGraphDS.removeNode(randomNode2);
                testWGraphDS.addNode(randomNode2);
                assertNotNull(testWGraphDS.getNode(randomNode2), "Shouldn't be null");
                testWGraphDS.addNode(randomNode1);
            }
            counter+=50;
            }
        }
        for(node = 0; node < numberOfNodes ; node++) {
            testWGraphDS.connect(node,node + 1, weight);
        }
         numberOfEdges = testWGraphDS.edgeSize();
        for(node = 2; node < numberOfNodes;) {
            testWGraphDS.removeNode(node);
            numberOfEdges -= 2;
            numberOfNodes--;
            node +=30;
        }
        Assertions.assertEquals(testWGraphDS.nodeSize(),numberOfNodes);
        Assertions.assertEquals(testWGraphDS.edgeSize(),numberOfEdges);
    }

    @Test
    void removeEdge() {
        testWGraphDS.addNode(8);
        testWGraphDS.addNode(6);
        testWGraphDS.connect(6, 8, 6.6);
        Assertions.assertTrue(testWGraphDS.hasEdge(6, 8), "Required to be true");
        testWGraphDS.removeEdge(6,8);
        Assertions.assertFalse(testWGraphDS.hasEdge(6, 8),"Required to be false");
        Assertions.assertFalse(testWGraphDS.hasEdge(8, 6),"Required to be false");
        testWGraphDS.addNode(10);
        testWGraphDS.connect(10, 8, 5.5);
        Assertions.assertFalse(testWGraphDS.hasEdge(10, 6), "Required to be false");
        testWGraphDS.addNode(18);
        testWGraphDS.addNode(13);
        testWGraphDS.connect(18, 13, 9.3);
        testWGraphDS.connect(6, 18, 7.3);
         numberOfEdges = testWGraphDS.edgeSize();
        testWGraphDS.removeEdge(18,13);
        testWGraphDS.removeEdge(6,18);
        Assertions.assertEquals(numberOfEdges-2,testWGraphDS.edgeSize());
    }

    @Test
    void nodeSize() {
         numberOfNodes = 200;
         int numberOfDeletedNodes=0;
        for( node = 0 ; node < numberOfNodes; node++){
            testWGraphDS.addNode(node);
        }
      int actualNumberOfNodes =  testWGraphDS.nodeSize();
        assertEquals(numberOfNodes, actualNumberOfNodes,"Required to be 200");
        for (node = 0; node < numberOfNodes; ) {
            testWGraphDS.removeNode(node);
            numberOfDeletedNodes++;
            node+=40;
        }
        assertEquals(numberOfNodes - numberOfDeletedNodes, testWGraphDS.nodeSize());

    }

    @Test
    void edgeSize() {
        numberOfNodes = 231;
        numberOfEdges = 0;
        for(node = 0; node < numberOfNodes;){
            testWGraphDS.addNode(node);
            testWGraphDS.addNode(node+1);
            weight = ((double) (int) (Math.random() * 10) +8);
            testWGraphDS.connect(node,node+1, weight);
            double weight1 = testWGraphDS.getEdge(node,node+1);
            double weight2 = testWGraphDS.getEdge(node+1,node);
            Assertions.assertEquals(weight1,weight2,"Weight of the same edge required to be equal");
            Assertions.assertTrue(testWGraphDS.hasEdge(node+1,node),"Required to be true");
            numberOfEdges++;
            node += 23;
        }
        Assertions.assertEquals(numberOfEdges, testWGraphDS.edgeSize());
    }

    @Test
    void getMC() {
        numberOfNodes = 602;
        int modeCount = 0;
        for (node = 0; node < numberOfNodes; ) {
            testWGraphDS.addNode(node);
            modeCount++;
            node++;
        }

        for(node = 0; node < 75; ){
                testWGraphDS.connect(node, node+1, weight);
                modeCount++;
            node++;
        }
        testWGraphDS.removeEdge(30,31);
        modeCount++;

            assertEquals(testWGraphDS.getMC(), modeCount);
        }


}