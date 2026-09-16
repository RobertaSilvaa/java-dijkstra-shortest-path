import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Undirected weighted graph whose vertices are numbered from 1 to vertexCount.
 */
public final class Graph {
    private final Map<Integer, Map<Integer, Integer>> adjacencyList;
    private final int vertexCount;

    public Graph(int vertexCount) {
        if (vertexCount <= 0) {
            throw new IllegalArgumentException("Vertex count must be greater than zero.");
        }

        this.vertexCount = vertexCount;
        this.adjacencyList = new LinkedHashMap<>();

        for (int vertex = 1; vertex <= vertexCount; vertex++) {
            adjacencyList.put(vertex, new TreeMap<>());
        }
    }

    /**
     * Adds or replaces an undirected edge between two existing vertices.
     */
    public void addEdge(int weight, int source, int destination) {
        validateVertex(source);
        validateVertex(destination);

        if (weight < 0) {
            throw new IllegalArgumentException("Edge weight cannot be negative.");
        }

        adjacencyList.get(source).put(destination, weight);
        adjacencyList.get(destination).put(source, weight);
    }

    public Map<Integer, Integer> getNeighbors(int vertex) {
        validateVertex(vertex);
        return Collections.unmodifiableMap(adjacencyList.get(vertex));
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void printEdges() {
        boolean hasEdges = false;

        System.out.println();
        for (int source = 1; source <= vertexCount; source++) {
            for (Map.Entry<Integer, Integer> edge : adjacencyList.get(source).entrySet()) {
                int destination = edge.getKey();

                // Each undirected edge is stored twice, so print it only once.
                if (source <= destination) {
                    System.out.println(source + " - " + destination + " : " + edge.getValue());
                    hasEdges = true;
                }
            }
        }

        if (!hasEdges) {
            System.out.println("Graph has no edges.");
        }
        System.out.println();
    }

    private void validateVertex(int vertex) {
        if (vertex < 1 || vertex > vertexCount) {
            throw new IllegalArgumentException(
                    "Vertex must be between 1 and " + vertexCount + ". Received: " + vertex);
        }
    }
}
