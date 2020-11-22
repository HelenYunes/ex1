package ex1.src;
import java.util.*;
import java.io.*;

/**
 * This class represents an Undirected (positive) Weighted Graph Theory algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected();
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_data> shortestPath(int src, int dest);
 * 5. Save(file);
 * 6. Load(file);
 *
 * @author boaz.benmoshe
 */
public class WGraph_Algo implements weighted_graph_algorithms {
    private WGraph_DS Graph;

    /**
     * constructor:
     */
    public WGraph_Algo() {
        this.Graph = new WGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.Graph = (WGraph_DS) g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return this.Graph;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public weighted_graph copy() {
        weighted_graph copyGraph = new WGraph_DS();
        for (node_info node : Graph.getV()) {
            node_info copyNode = Graph.getNode(node.getKey());
            copyGraph.addNode(copyNode.getKey());
            copyGraph.getNode(node.getKey()).setTag(node.getTag());
            copyGraph.getNode(node.getKey()).setInfo(node.getInfo());
        }
        for (node_info node : Graph.getV()) {
            for (node_info neighbor : Graph.getV(node.getKey())) {
                copyGraph.connect(node.getKey(), neighbor.getKey(), Graph.getEdge(node.getKey(), neighbor.getKey()));
            }
        }
        return copyGraph;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node.
     * This function determine if it is possible to reach all the nodes in the graph (connected graph)
     * by doing a BFS search. These algorithms works with private function Bfs - the first step
     * is initialize all vertices as not visited. then we do a BFS traversal on the graph.
     * If BFS traversal does not visit all the nods in the graph, then return false.
     * The idea is: if every node can be reached.
     *
     * @return graph connected
     */
    @Override
    public boolean isConnected() {
        if (this.Graph.nodeSize() == 1 || this.Graph.nodeSize() == 0) return true;
        int numbersOfVisitedNodes = BFS(RandomKey(this.Graph));
        return (numbersOfVisitedNodes == this.Graph.nodeSize()); // check if we reached all the nodes in the graph by doing a BFS search
    }

    /**
     * A private function to help us find a key to start with
     */
    private int RandomKey(WGraph_DS g) {
        int randomKeyNumber = 0;
        for (node_info node : g.getV()) {
            randomKeyNumber = node.getKey();
            break;
        }
        return randomKeyNumber;
    }

    /**
     * returns the length of the shortest path between src to dest with the help of dijkstra algorithm
     * It picks the unvisited vertex with the lowest distance, calculates the distance through
     * it to each unvisited neighbor, and updates the neighbor's distance if smaller.
     * Mark visited when done with neighbors.
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return distance
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest)
            return 0;
        if (!this.Graph.get_Node_Graph().containsKey(src) || !this.Graph.get_Node_Graph().containsKey(dest))
            return -1;
        node_info destination = Graph.getNode(dest);
        dijkstra(Graph.getNode(src));
        double distance = destination.getTag();
        if (destination.getTag() == Integer.MAX_VALUE) {
            return -1;
        }
        return distance;
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * A variation of dijkstra algorithm used for finding the shortest path between two nodes src and dest.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return list of the shortest path
     */

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (!(Graph.get_Node_Graph().containsKey(dest)) || !(Graph.get_Node_Graph().containsKey(dest)))
            return null;
        LinkedList<node_info> path = new LinkedList<>();
        if (src == dest) {
            path.add(Graph.getNode(src));
            return path;
        }

        dijkstra(Graph.getNode(src));
        node_info destination = Graph.getNode(dest);

        while (Graph.getPredecessor(destination) != null) {
            path.addFirst(destination);
            destination = Graph.getPredecessor(destination);
        }
        path.addFirst(destination);
        if (!(path.contains(Graph.getNode(src)))) {
            return null;
        }

        return path;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {

        try {
            FileOutputStream fileO = new FileOutputStream(file);
            ObjectOutputStream fileSave = new ObjectOutputStream(fileO);
            fileSave.writeObject(this.Graph);
            fileSave.close();
            fileO.close();
            return true;

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            File NewFile = new File(file);
            FileInputStream input = new FileInputStream(NewFile);
            ObjectInputStream l = new ObjectInputStream(input);
            this.Graph = (WGraph_DS) l.readObject();
            l.close();
            input.close();
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
            return false;
        }

    }

    public String toString() {
        return "WGraph_Algo [Node_Graph=" + this.Graph.get_Node_Graph().toString() + "\nModeCount=" + this.Graph.getMC()
                + "\nEdgeGraphSize=" + this.Graph.edgeSize() + "\nNodeGraphSize=" + this.Graph.get_Node_Graph().size() + "]";
    }

    /**
     * dijkstra algorithm:
     * Mark all nodes unvisited.
     * Assign to every node a tentative distance value: set it to zero for our initial node and to infinity for all other nodes.
     * For the current node, consider all of its unvisited neighbours and calculate their tentative distances through the current node.
     * Compare the newly calculated tentative distance to the current assigned value and assign the smaller one. A visited node will never be checked again.
     * If the destination node has been marked visited or if the smallest tentative distance among the nodes in the unvisited nodes is infinity
     * Otherwise, select the unvisited node that is marked with the smallest tentative distance, set it as the new "current node" and do the compare again
     * the algorithm finds all the connected nodes to the source
     * when the all the nodes are visited,we can see all the connected nodes to the source, with each vertex
     * reached and sets every node's predecessor in the shortest possible way.
     *
     * @param source node
     * @return int
     */
    private void dijkstra(node_info source) {

        resetData();
        source.setTag(0);
        PriorityQueue<node_info> queue = new PriorityQueue<>(Graph.getV());
        while (!queue.isEmpty()) {
            node_info firstNode = queue.poll();
            for (node_info neighbor : Graph.getV(firstNode.getKey())) {
                if (!(Graph.getVisited(neighbor))) {
                    double distance = firstNode.getTag() + Graph.getEdge(firstNode.getKey(), neighbor.getKey());
                    if (neighbor.getTag() > distance) {
                        neighbor.setTag(distance);
                        Graph.setPredecessor(firstNode, neighbor);
                        queue.remove(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
            Graph.setVisited(firstNode, true);
        }
    }

    /**
     * /**
     * A private function to determine if it is possible to reach all the nodes in the graph (connected graph)
     * by doing a BFS search. These algorithms work by traversing the nodes in the graph using a queue for storage of
     * unprocessed nodes. Nodes are removed from the queue as soon as their processing has been completed.
     * This Breadth first search (BFS) is used for finding all connected components in a graph.
     * The first step is initialize all vertices as not visited. then we do a BFS traversal of graph. If BFS traversal
     * does not visit all vertices in the graph, then return false. The idea is again simple: if every node can be reached.
     *
     * @param src node
     * @return numberOfVisitedNodes
     */
    private int BFS(int src) {
        this.resetInfo();
        Queue<node_info> queue = new LinkedList<>();
        queue.add(Graph.getNode(src));
        Graph.getNode(src).setInfo("1");
        int numberOfVisitedNodes = 1;

        while (!queue.isEmpty()) {
            node_info node = queue.remove();
            Collection<node_info> neighbors = Graph.getV(node.getKey());
            for (node_info next : neighbors) {

                if (!next.getInfo().equals("1")) {

                    queue.add(next);
                    next.setInfo("1");

                    numberOfVisitedNodes++;
                }
            }
        }

        return numberOfVisitedNodes;
    }

    /**
     * A private function to set all the info of the nodes in the graph to zero.
     */
    private void resetInfo() {
        for (node_info node : this.Graph.getV()) {
            node.setInfo("0");
        }
    }

    /**
     * a function to reset all the node's Tags, visited and Parents
     */
    private void resetData() {
        for (node_info node : Graph.getV()) {
            node.setTag(Integer.MAX_VALUE);
            Graph.setVisited(node, false);
            Graph.setPredecessor(null, node);
        }
    }


}