package ex1.src;

import java.util.*;
import java.io.Serializable;

/**
 * This class represents an undirectional weighted graph.
 * It should support a large number of nodes (over 10^6, with average degree of 10).
 * The implementation should be based on an efficient compact representation
 * (should NOT be based on a n*n matrix).
 */
public class WGraph_DS implements weighted_graph, Serializable {
    private HashMap<Integer, node_info> Node_Graph;
    private int ModeCount, EdgeGraphSize;

    /**
     * Constructor:
     */
    public WGraph_DS() {
        this.ModeCount = 0;
        this.EdgeGraphSize = 0;
        this.Node_Graph = new HashMap<>();
    }

    public HashMap<Integer, node_info> get_Node_Graph() {
        return this.Node_Graph;
    }

    /**
     * return the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        if (!Node_Graph.containsKey(key)) return null;
        return Node_Graph.get(key);
    }

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return is there a Edge between node1 and node2
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (node1 == node2 || !(Node_Graph.containsKey(node1)) || !(Node_Graph.containsKey(node2)))
            return false;
        return ((NodeInfo) Node_Graph.get(node2)).hasNi(node1);
    }

    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return the weight of this edge
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!hasEdge(node1, node2))
            return -1;
        return ((NodeInfo) Node_Graph.get(node2)).Edge_Graph.get(node1);
    }

    /**
     * add a new node to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (Node_Graph.containsKey(key)) {
            return;
        }
        ModeCount++;
        node_info node = new NodeInfo(key);
        Node_Graph.put(key, node);
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (node1 == node2 || w < 0 || !Node_Graph.containsKey(node1) || !Node_Graph.containsKey(node2))
            return;
        NodeInfo src = ((NodeInfo) Node_Graph.get(node1));
        NodeInfo dest = ((NodeInfo) Node_Graph.get(node2));
        if (this.hasEdge(node1, node2)) {
            src.Edge_Graph.replace(node2, w);
            dest.Edge_Graph.replace(node1, w);
            ModeCount++;
            return;
        }
        src.addNi(dest, w);
        dest.addNi(src, w);
        ModeCount++;
        EdgeGraphSize++;
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return Node_Graph.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        HashMap<Integer, node_info> nodeNeighbors = new HashMap<>();
        for (Integer thisKey : ((NodeInfo) Node_Graph.get(node_id)).getNi()) {
            nodeNeighbors.put(thisKey, Node_Graph.get(thisKey));
        }
        return nodeNeighbors.values();
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        if (!this.Node_Graph.containsKey(key))
            return null;
        Collection<node_info> neighbors = this.getV(key);
        node_info returnNode = this.Node_Graph.get(key);
        this.EdgeGraphSize -= neighbors.size();
        Iterator<node_info> iterator = neighbors.iterator();
        while (iterator.hasNext()) {
            node_info edge = iterator.next();
            ((NodeInfo) returnNode).removeNode((NodeInfo) edge);
            ModeCount++;
        }
        Node_Graph.remove(key);
        ModeCount++;
        return returnNode;
    }

    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (node1 == node2 || !this.hasEdge(node1, node2))
            return;
        ((NodeInfo) Node_Graph.get(node1)).removeNode((NodeInfo) Node_Graph.get(node2));
        ModeCount++;
        EdgeGraphSize--;
    }

    /**
     * return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return Node_Graph.size();
    }

    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     *
     * @return edgeSize
     */
    @Override
    public int edgeSize() {
        return EdgeGraphSize;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     *
     * @return ModeCount
     */
    @Override
    public int getMC() {
        return ModeCount;
    }

    /**
     * determine if this node is visited which will be used in algorithms
     *
     * @param node
     * @return is visited
     */
    public boolean getVisited(node_info node) {
        return ((NodeInfo) node).visited();
    }

    /**
     * set visited
     *
     * @param node
     * @param visited
     */
    public void setVisited(node_info node, boolean visited) {
        ((NodeInfo) node).setVisited(visited);
    }

    /**
     * return the parent of this node which will be used in algorithms
     *
     * @param node
     * @return parent
     */
    public node_info getPredecessor(node_info node) {
        return ((NodeInfo) node).getPredecessors();
    }

    /**
     * set the parent of this node which will be used in algorithms
     *
     * @param node,parent
     */
    public void setPredecessor(node_info parent, node_info node) {
        ((NodeInfo) node).setPredecessors((NodeInfo) parent);
    }

    @Override
    public String toString() {
        return "WGraph_DS [Node_Graph=" + Node_Graph.toString() + "\nModeCount=" + ModeCount
                + "\nEdgeGraphSize=" + EdgeGraphSize + "\nNodeGraphSize=" + Node_Graph.size() + "]";
    }

///////////////////////////////////////////////////////NodeInfo\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * This class will implements node_info interface to create a NodeInfo structure.
     */
    private class NodeInfo implements node_info, Serializable, Comparable<node_info> {
        private String Info;
        boolean Visited;
        private final int Key;
        private double Tag;
        private NodeInfo Predecessor;
        private HashMap<Integer, Double> Edge_Graph;

        /**
         * Constructors:
         */
        public NodeInfo(int key) {
            this.Info = "";
            this.Visited = false;
            this.Key = key;
            this.Tag = 0;
            this.Predecessor = null;
            this.Edge_Graph = new HashMap<>();
        }

        public NodeInfo(node_info n) {
            this.Info = n.getInfo();
            this.Visited = false;
            this.Key = n.getKey();
            this.Tag = n.getTag();
            this.Predecessor = null;
            this.Edge_Graph = new HashMap<>();
        }

        public HashMap<Integer, Double> get_Edge_Graph() {
            return this.Edge_Graph;
        }

        /**
         * determine if this node is visited which will be used in algorithms
         *
         * @return is visited
         */
        public boolean visited() {
            return this.Visited;
        }

        /**
         * set visited
         */
        public void setVisited(boolean visited) {
            this.Visited = visited;
        }

        /**
         * return parent of this node, which will be used in algorithm
         *
         * @return node_data
         */
        public node_info getPredecessors() {
            return this.Predecessor;
        }

        /**
         * set parent
         *
         * @param parent
         */
        public void setPredecessors(NodeInfo parent) {
            this.Predecessor = parent;
        }

        /**
         * Return the key (id) associated with this node.
         * Note: each node_data have a unique key.
         *
         * @return Key
         */
        @Override
        public int getKey() {
            return this.Key;
        }

        /**
         * This method adds the node_data (n) to this node_data with weight w.
         */
        public void addNi(node_info n, double w) {
            if (this.hasNi(n.getKey())) {
                return;
            }
            this.Edge_Graph.put(n.getKey(), w);
            ((NodeInfo) n).Edge_Graph.put(this.getKey(), w);
        }

        /**
         * return true iff this<==>key are adjacent, as an edge between them.
         *
         * @param key
         * @return hasNi
         */
        public boolean hasNi(int key) {
            return this.Edge_Graph.containsKey(key);
        }

        /**
         * This method returns a collection with all the Neighbor nodes of this node_data
         */
        public Collection<Integer> getNi() {
            return this.Edge_Graph.keySet();
        }

        /**
         * Removes all the edges of this-node and delete the node from the graph
         *
         * @param node
         */
        public void removeNode(NodeInfo node) {
            if (!this.hasNi(node.getKey())) {
                return;
            }
            Edge_Graph.remove(node.getKey());
            node.Edge_Graph.remove(this.getKey());
        }

        /**
         * return the remark (meta data) associated with this node.
         *
         * @return
         */
        @Override
        public String getInfo() {
            return this.Info;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.Info = s + "";
        }

        /**
         * Temporal data (aka color: e,g, white, gray, black)
         * which can be used be algorithms
         *
         * @return Tag
         */
        @Override
        public double getTag() {
            return this.Tag;
        }

        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.Tag = t;
        }

        @Override
        public String toString() {
            return "WGraph_DS [Node_Graph=" + Node_Graph.toString() + "\nEdge_Graph=" + Edge_Graph.toString() + "\nModeCount=" + ModeCount
                    + "\nEdgeGraphSize=" + EdgeGraphSize + "\nNodeGraphSize=" + Node_Graph.size() + "\nParent=" + Predecessor + "]";
        }


        @Override
        public int compareTo(node_info t) {
            return Double.compare(this.getTag(), t.getTag());
        }

    }


}