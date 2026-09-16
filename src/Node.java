/**
 * Priority-queue entry used by Dijkstra's algorithm.
 */
final class Node implements Comparable<Node> {
    final int vertex;
    final long distance;

    Node(int vertex, long distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other) {
        return Long.compare(this.distance, other.distance);
    }
}
