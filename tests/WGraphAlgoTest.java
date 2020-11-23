package ex1.tests;
import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;


class WGraphAlgoTest {
    int node;
    private weighted_graph graphDsTest;
    private weighted_graph_algorithms graphAlgoTest;

    void initGraph(weighted_graph g){
        graphDsTest = g;}

        @BeforeEach
        void init(){
            graphDsTest = new WGraph_DS();
            graphAlgoTest = new WGraph_Algo();
            graphAlgoTest.init(graphDsTest);
            initGraph(graphDsTest);
        }

    @Test
    void getGraph() {
        weighted_graph graphDs = WGraphAlgoTest.GraphCreator(1892,65);
        weighted_graph_algorithms graphAl = new WGraph_Algo();
        graphAl.init(graphDs);
        weighted_graph graphDs2 = WGraphAlgoTest.GraphCreator(1892,65);
        weighted_graph_algorithms graphAl2 = new WGraph_Algo();
        graphAl2.init(graphDs2);
        WGraph_DS a= (WGraph_DS) graphAl2.getGraph();
        WGraph_DS b=   (WGraph_DS) graphAl.getGraph();
        assertEquals(a,b,"Required to be equals");
        b.removeNode(0);
        assertNotEquals(a,b);
    }

    @Test
    void copy() {
        weighted_graph graphDsCheck = new WGraph_DS();
        for (int node = 0; node < 10; node++){
            graphDsCheck.addNode(node);}

        graphDsCheck.connect(0, 1, 19.3);
        graphDsCheck.connect(1, 2, 10.5);
        graphDsCheck.connect(2, 3, 8.3);
        graphDsCheck.connect(3, 4, 5.4);
        graphDsCheck.connect(4, 5, 5.5);
        graphDsCheck.connect(5, 6, 2);
        graphDsCheck.connect(6, 7, 1);
        graphDsCheck.connect(7, 8, 9.7);
        graphDsCheck.connect(7, 8, 9.7);
        graphDsCheck.connect(8, 9, 11.8);

        weighted_graph_algorithms graphAlCheck = new WGraph_Algo();
        graphAlCheck.init(graphDsCheck);
        weighted_graph graphCopy = graphAlCheck.copy();
        Assertions.assertEquals(graphDsCheck,graphCopy, "Required to be True");
    }

    @Test
    void isConnected() {
        int NodeGraphSize = 10;
        for( node = 0 ; node < NodeGraphSize; node++){
            graphDsTest.addNode(node);
        }
        graphDsTest.connect(0,1,10);
        graphDsTest.connect(1,2,5);
        graphDsTest.connect(2,3,11.3);
        graphDsTest.connect(3,4,4);
        graphDsTest.connect(4,5,3.2);
        graphDsTest.connect(5,6,0.5);
        graphDsTest.connect(6,7,7.4);
        graphDsTest.connect(7,8,7);
        graphDsTest.connect(8,9,8.4);
        graphDsTest.connect(9,10,0.3);
        Assertions.assertTrue(graphAlgoTest.isConnected());
        graphDsTest = WGraphAlgoTest.GraphCreator(1,0);
        weighted_graph_algorithms wGraphAlgo = new WGraph_Algo();
        wGraphAlgo.init(graphDsTest);
        assertTrue(wGraphAlgo.isConnected(),"Graph with only one node should be connected");
        graphDsTest = WGraphAlgoTest.GraphCreator(4,2);
        wGraphAlgo = new WGraph_Algo();
        wGraphAlgo.init(graphDsTest);
        assertFalse(wGraphAlgo.isConnected(),"Graph with four nodes and two edges can't be connected");
        graphDsTest = WGraphAlgoTest.GraphCreator(0,0);
        wGraphAlgo = new WGraph_Algo();
        wGraphAlgo.init(graphDsTest);
        assertTrue(wGraphAlgo.isConnected(),"Empty graph should be connected");
        graphDsTest = WGraphAlgoTest.GraphCreator(2,1);
        wGraphAlgo = new WGraph_Algo();
        wGraphAlgo.init(graphDsTest);
        assertTrue(wGraphAlgo.isConnected(),"Graph with two nodes and one edge should be connected");
    }

    @Test
    void shortestPathDistAndShortestPath() {
        int NodeGraphSize = 13;
        for( node = 0 ; node < NodeGraphSize; node++){
            graphDsTest.addNode(node);
        }
        graphDsTest.connect(0,1,10);
        graphDsTest.connect(1,2,5);
        graphDsTest.connect(2,3,11.3);
        graphDsTest.connect(3,4,4);
        graphDsTest.connect(4,5,3.2);
        graphDsTest.connect(5,6,0.5);
        graphDsTest.connect(6,7,7.4);
        graphDsTest.connect(7,8,7);
        graphDsTest.connect(8,9,8.4);
        graphDsTest.connect(9,10,0.3);
        graphDsTest.connect(10,11,9);
        graphDsTest.connect(11,12,1);
        graphDsTest.connect(12,13,0.3);

        assertTrue(graphAlgoTest.isConnected());
        assertNotNull(graphAlgoTest.shortestPath(4,10));
        assertEquals(26.8, graphAlgoTest.shortestPathDist(4,10));
        graphDsTest.connect(10,4,10);
        assertEquals(10.0, graphAlgoTest.shortestPathDist(4,10));
        graphDsTest.removeNode(3);
        assertFalse(graphAlgoTest.isConnected());
        assertEquals(-1, graphAlgoTest.shortestPathDist(2,4));
        graphDsTest.connect(1,4,2);
        assertTrue(graphAlgoTest.isConnected());
        assertEquals(7.0, graphAlgoTest.shortestPathDist(2,4));
        graphDsTest = new WGraph_DS();
        for (int i = 0; i < 5; i++)
            graphDsTest.addNode(i);

        graphDsTest.connect(0, 1, 1);
        graphDsTest.connect(0, 2, 1.5);
        graphDsTest.connect(0, 4, 2.2);
        graphDsTest.connect(4, 3, 3.3);
        graphDsTest.connect(2, 3, 1);
        graphDsTest.connect(1, 3, 5.5);

        weighted_graph_algorithms graphAlgo = new WGraph_Algo();
        graphAlgo.init(graphDsTest);
        List<node_info> path = graphAlgo.shortestPath(1, 3);
       
        Assertions.assertEquals(4, path.size(), "Size of the shortest path should be be 4");
        assertEquals(3.5, graphAlgo.shortestPathDist(1,3),"Should be 3.5");
        graphDsTest.connect(0, 1, 11);
        weighted_graph_algorithms graphAlgo2 = new WGraph_Algo();
        graphAlgo2.init(graphDsTest);
        List<node_info> newPath = graphAlgo2.shortestPath(1, 3);
        Assertions.assertEquals(2, newPath.size(), "Size of the shortest path should be be 2");
      
        assertEquals(5.5, graphAlgo2.shortestPathDist(1,3),"Should be 5.5");
    }
    @Test
    void saveAndLoad() {
        WGraph_Algo fileGraphAlgo = new WGraph_Algo();
        weighted_graph fileGraphDs = WGraphAlgoTest.GraphCreator(680, 53);
        fileGraphAlgo.init(fileGraphDs);
        Assertions.assertTrue(fileGraphAlgo.save("checkFile"));
        WGraph_Algo graph = new WGraph_Algo();
        Assertions.assertFalse(graph.load("file"));
        Assertions.assertTrue(graph.load("checkFile"));
        weighted_graph graph2=graph.getGraph();
        Assertions.assertTrue(fileGraphDs.equals(graph2));

    }
    public static weighted_graph GraphCreator(int nodeGraphSize, int edgeGraphSize) {
        weighted_graph testGraphAlgo = new WGraph_DS();
        int seed = 2, firstNode , secondNode;
        double weight = 5.5;
        Random random = new Random(seed);

        for(int node = 0; node < nodeGraphSize; node++) {
            testGraphAlgo.addNode(node);
        }
        for (int edge = 0; edge < edgeGraphSize;) {
            firstNode = random.nextInt(nodeGraphSize);
            secondNode = random.nextInt(nodeGraphSize);
            while ( firstNode == secondNode ||testGraphAlgo.hasEdge(firstNode,secondNode)) {
                secondNode = random.nextInt(nodeGraphSize);
            }
            testGraphAlgo.connect(firstNode, secondNode, weight);
            edge++;

        }
        return testGraphAlgo;
    }

}
