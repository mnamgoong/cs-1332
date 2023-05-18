import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Michelle Namgoong
 * @userid mnamgoong3
 * @GTID 903684766
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex cannot be "
                    + "null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("The graph cannot be null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex is not in "
                    + "the graph.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> bfsList = new ArrayList<>();

        visitedSet.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex<T> v = queue.remove();
            bfsList.add(v);
            for (VertexDistance<T> w : graph.getAdjList().get(v)) {
                if (!visitedSet.contains(w.getVertex())) {
                    visitedSet.add(w.getVertex());
                    queue.add(w.getVertex());
                }
            }
        }
        return bfsList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex cannot be "
                    + "null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("The graph cannot be null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex is not in "
                    + "the graph.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>();
        List<Vertex<T>> dfsList = new ArrayList<>();

        dfs(start, graph, visitedSet, dfsList);
        return dfsList;
    }

    /**
     * Private helper method for the recursive implementation of the depth
     * first search (dfs) algorithm.
     *
     * @param u          the vertex to begin the dfs on
     * @param graph      the graph to search through
     * @param visitedSet the set of visited vertices
     * @param dfsList    list of vertices in visited order
     * @param <T>        the generic tping of the data
     */
    private static <T> void dfs(Vertex<T> u, Graph<T> graph,
                                Set<Vertex<T>> visitedSet,
                                List<Vertex<T>> dfsList) {
        visitedSet.add(u);
        dfsList.add(u);

        for (VertexDistance<T> w : graph.getAdjList().get(u)) {
            if (!visitedSet.contains(w.getVertex())) {
                dfs(w.getVertex(), graph, visitedSet, dfsList);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex cannot be "
                    + "null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("The graph cannot be null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex is not in "
                    + "the graph.");
        }

        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        Queue<VertexDistance<T>> pq = new PriorityQueue<>();

        for (Vertex<T> v : graph.getAdjList().keySet()) {
            if (v.equals(start)) {
                distanceMap.put(v, 0);
            } else {
                distanceMap.put(v, Integer.MAX_VALUE);
            }
        }

        pq.add(new VertexDistance<>(start, 0));

        while (!pq.isEmpty()) {
            VertexDistance<T> u = pq.remove();
            for (VertexDistance<T> w : graph.getAdjList().get(u.getVertex())) {
                int path = u.getDistance() + w.getDistance();
                if (distanceMap.get(w.getVertex()) > path) {
                    distanceMap.put(w.getVertex(), path);
                    pq.add(new VertexDistance<>(w.getVertex(),
                            path));
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex cannot be "
                    + "null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("The graph cannot be null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex is not in "
                    + "the graph.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>();
        Set<Edge<T>> mstEdgeSet = new HashSet<>();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();

        for (VertexDistance<T> s : graph.getAdjList().get(start)) {
            pq.add(new Edge<>(start, s.getVertex(), s.getDistance()));
        }

        visitedSet.add(start);

        while (!pq.isEmpty()) {
            Edge<T> u = pq.remove();
            if (!visitedSet.contains(u.getV())) {
                visitedSet.add(u.getV());
                mstEdgeSet.add(u);
                mstEdgeSet.add(new Edge<>(u.getV(), u.getU(), u.getWeight()));

                for (VertexDistance<T> w : graph.getAdjList().get(u.getV())) {
                    if (!visitedSet.contains(w.getVertex())) {
                        pq.add(new Edge<>(u.getV(), w.getVertex(),
                                w.getDistance()));
                    }
                }
            }
        }

        if (mstEdgeSet.size() < graph.getVertices().size() - 1) {
            return null;
        } else {
            return mstEdgeSet;
        }
    }
}