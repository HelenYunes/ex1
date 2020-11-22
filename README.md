## Ex1 OOP Course - Unweighted Undirected Graph:

In this project we built the data structure of the project: a unweighted undirected graph. A Graph is a collection of nodes and edges which connects between the nodes.
Every node has information like the key and the tag of the node, and his neighbors. The key and the tag are from int type, and the info is a string type. The neighbors of each node stored in HashMap.
My implementation is based on HashMap, because it allows most of the functions to run in O(1) time.
To access a value on HashMap one must know its key. HashMap is known as HashMap because it uses a technique called Hashing. Hashing is a technique of converting a large value to small value that represents the same value. A shorter value helps in indexing and faster searches. HashMap does not allow duplicate keys but allows duplicate values, that means each node_data will have a unique key like it should in the graph.  
**WGraph_DS data structure:**
HashMap <Intger, node_data> **Node_Graph** - will hold all the nodes in a HashMap by the node's key. It will contain all the nodes (node data) in the graph.
This class implements the weighted_graph interface and contains an inner class "NodeInfo" that implements the node_info interface.
NodeInfo data structure:
HashMap<Integer, Double> **Edge_Graph** - will hold the neighbors (there is an edge between them) for each node in the graph. The first Integer is the key of the node and for each key, the Double will represent the weight of the edge.
The implementation is based on an efficient compact representation that support many nodes (over 10^6, with average degree of 10).
The classes in this project are:
###WGraph_DS:
This class represents an undirectional weighted graph.
This class represents a undirected weighted graph. This class can create a undirectional and weighted graphs and add or remove nodes or edges on a given graph. The WGraph_DS class implements the weighted_graph interface.
**WGraph_Algo:**
This interface represents an Undirected (positive) Weighted Graph Theory algorithms including:
1. clone(); (copy)
2. init(graph);
3. isConnected() - which returns true if there is a valid path from every node to each other by doing a BFS search.
4.  double shortestPathDist(int src, int dest) - return the shortest distance between 2    nodes  by doing dijkstra algorithm.
5. List<node_data> shortestPath(int src, int dest) - returns list of the shortest path between src to dest - as an ordered list of nodes.
6. Save (file)  – allowing to saves this weighted (undirected) graph to the given
  file name.
7. Load(file) - This method allowing to load a graph to this graph algorithm.  if the file was successfully loaded - the underlying graph of this class will be changed (to the loaded one), in case the graph was not loaded the original graph should remain "as is".
The WGraph_Algo class implements the weighted_graph_algorithms interface.
**dijkstra algorithm:**
[![dijkstra algorithm:](https://ds055uzetaobb.cloudfront.net/brioche/uploads/bW1sDrFu5l-graph4.png?width=2000 "dijkstra algorithm:")


**dijkstra algorithm step by step:**

- Mark all nodes unvisited. Create a set of all the unvisited nodes called the unvisited set.
- Assign to every node a tentative distance value: set it to zero for our initial node and to infinity for all other nodes. Set the initial node as current.[16]
- For the current node, consider all of its unvisited neighbours and calculate their tentative distances through the current node. Compare the newly calculated tentative distance to the current assigned value and assign the smaller one. For example, if the current node A is marked with a distance of 6, and the edge connecting it with a neighbour B has length 2, then the distance to B through A will be 6 + 2 = 8. If B was previously marked with a distance greater than 8 then change it to 8. Otherwise, the current value will be kept.
- When we are done considering all of the unvisited neighbours of the current node, mark the current node as visited and remove it from the unvisited set. A visited node will never be checked again.
- If the destination node has been marked visited (when planning a route between two specific nodes) or if the smallest tentative distance among the nodes in the unvisited set is infinity (when planning a complete traversal; occurs when there is no connection between the initial node and remaining unvisited nodes), then stop. The algorithm has finished.
- Otherwise, select the unvisited node that is marked with the smallest tentative distance, set it as the new "current node", and go back to step 3.

[Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm "Dijkstra's algorithm")
