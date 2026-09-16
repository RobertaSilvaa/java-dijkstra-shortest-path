import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public final class Main {
    private static final int VERTEX_COUNT = 20;

    private Main() {
        // Utility class; no instances are required.
    }

    public static void main(String[] args) {
        Graph graph = new Graph(VERTEX_COUNT);

        try (Scanner scanner = new Scanner(System.in)) {
            runMenu(scanner, graph);
        } catch (IllegalStateException exception) {
            System.err.println("Input error: " + exception.getMessage());
        }
    }

    private static void runMenu(Scanner scanner, Graph graph) {
        int option;

        do {
            printMenu();
            option = readIntInRange(scanner, "Choose an option: ", 1, 4);

            switch (option) {
                case 1:
                    addEdges(scanner, graph);
                    break;
                case 2:
                    graph.printEdges();
                    break;
                case 3:
                    showShortestDistances(scanner, graph);
                    break;
                case 4:
                    System.out.println("Program finished.");
                    break;
                default:
                    throw new IllegalStateException("Unexpected menu option: " + option);
            }
        } while (option != 4);
    }

    private static void printMenu() {
        System.out.println("1 - Add edge");
        System.out.println("2 - Print graph edges");
        System.out.println("3 - Show shortest distances");
        System.out.println("4 - Exit");
    }

    private static void addEdges(Scanner scanner, Graph graph) {
        int continueOption;

        do {
            int source = readIntInRange(
                    scanner, "Source vertex (1-" + graph.getVertexCount() + "): ",
                    1, graph.getVertexCount());
            int destination = readIntInRange(
                    scanner, "Destination vertex (1-" + graph.getVertexCount() + "): ",
                    1, graph.getVertexCount());
            int weight = readNonNegativeInt(scanner, "Weight: ");

            graph.addEdge(weight, source, destination);
            System.out.println("Edge added successfully.");

            continueOption = readIntInRange(
                    scanner, "Add another edge? (1 = yes, 0 = no): ", 0, 1);
        } while (continueOption == 1);
    }

    private static void showShortestDistances(Scanner scanner, Graph graph) {
        int source = readIntInRange(
                scanner, "Source vertex (1-" + graph.getVertexCount() + "): ",
                1, graph.getVertexCount());

        Map<Integer, Long> shortestDistances = dijkstra(graph, source);

        System.out.println();
        System.out.println("Shortest distances from vertex " + source + ":");
        for (Map.Entry<Integer, Long> entry : shortestDistances.entrySet()) {
            if (entry.getValue() != Long.MAX_VALUE) {
                System.out.println("To vertex " + entry.getKey() + ": " + entry.getValue());
            }
        }
        System.out.println();
    }

    public static Map<Integer, Long> dijkstra(Graph graph, int source) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }
        if (source < 1 || source > graph.getVertexCount()) {
            throw new IllegalArgumentException(
                    "Source vertex must be between 1 and " + graph.getVertexCount() + ".");
        }

        Map<Integer, Long> distances = new LinkedHashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>();

        for (int vertex = 1; vertex <= graph.getVertexCount(); vertex++) {
            distances.put(vertex, Long.MAX_VALUE);
        }

        distances.put(source, 0L);
        queue.add(new Node(source, 0L));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // Ignore an outdated queue entry if a shorter route was found later.
            if (current.distance != distances.get(current.vertex)) {
                continue;
            }

            for (Map.Entry<Integer, Integer> neighbor : graph.getNeighbors(current.vertex).entrySet()) {
                int destination = neighbor.getKey();
                int weight = neighbor.getValue();

                long candidateDistance = current.distance + weight;
                if (candidateDistance < distances.get(destination)) {
                    distances.put(destination, candidateDistance);
                    queue.add(new Node(destination, candidateDistance));
                }
            }
        }

        return distances;
    }

    private static int readNonNegativeInt(Scanner scanner, String prompt) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value >= 0) {
                return value;
            }
            System.out.println("Invalid value. Enter a non-negative integer.");
        }
    }

    private static int readIntInRange(Scanner scanner, String prompt, int minimum, int maximum) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value >= minimum && value <= maximum) {
                return value;
            }
            System.out.println(
                    "Invalid value. Enter an integer between " + minimum + " and " + maximum + ".");
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);

            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("The input stream was closed.");
            }

            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Enter an integer.");
            }
        }
    }
}
