# Dijkstra Shortest Path Java

A console-based Java project that represents an undirected weighted graph and calculates shortest distances with Dijkstra's algorithm.

## Project Structure

```text
dijkstra-shortest-path-java/
├── .gitignore
├── README.md
├── .vscode/
│   └── settings.json
└── src/
    ├── Graph.java
    ├── Main.java
    └── Node.java
```

The project intentionally uses the default Java package so that it works cleanly with VS Code Code Runner when a source file is executed directly from the `src` directory.

Compiled `.class` files are excluded from the repository.

## Requirements

- Java Development Kit (JDK) 8 or newer
- A terminal, PowerShell, Command Prompt, or Java-enabled IDE

Check the installed Java version with:

```bash
java -version
javac -version
```

## Graph Model

The program creates an undirected graph with 20 vertices numbered from `1` to `20`.

Each edge has a non-negative integer weight. Dijkstra's algorithm requires non-negative edge weights, so negative values are rejected both by input validation and by `Graph.addEdge`.

Adding an edge that already exists replaces its previous weight in both directions.

## Running with VS Code Code Runner

Open `src/Main.java` and run it with Code Runner.

The generated command should be equivalent to:

```powershell
cd "path\to\dijkstra-shortest-path-java\src"
javac Main.java
java Main
```

Because `Graph.java` and `Node.java` are in the same directory and use the same default package, `javac Main.java` can compile the required source dependencies automatically.

## Compilation from the Project Root

You can also compile all source files into a separate `bin` directory.

### Windows PowerShell

```powershell
New-Item -ItemType Directory -Force bin | Out-Null
javac -d bin src\Graph.java src\Node.java src\Main.java
java -cp bin Main
```

### Linux or macOS

```bash
mkdir -p bin
javac -d bin src/Graph.java src/Node.java src/Main.java
java -cp bin Main
```

## Program Options

The program provides the following options:

1. Add an edge.
2. Print all graph edges and their weights.
3. Calculate the shortest distances from a selected source vertex.
4. Exit.

## Input Validation

The program validates:

- menu options;
- source and destination vertices;
- integer input;
- vertex range (`1` through `20`);
- non-negative edge weights;
- whether the input stream is unexpectedly closed.

## Dijkstra Implementation

Shortest distances are stored as `long` values to reduce the risk of overflow when valid paths contain large integer edge weights.

The priority queue can contain an older entry for a vertex after a shorter route has been found. These outdated entries are skipped before their neighbors are processed.

The current program reports shortest **distances** from the selected source. It does not reconstruct and print the full sequence of vertices for each shortest path because that behavior was not specified by the provided project files.

## Verification Status

The corrected source files were checked in an isolated environment using the same direct-source workflow expected from Code Runner:

```text
javac Main.java
java Main
```

Final verification still depends on compilation and execution on the target machine. Any local compiler output, runtime error, or test result should be reviewed before the project is considered fully verified.

## Suggested Commit Message

```text
fix: make Dijkstra project compatible with direct Code Runner execution
```
