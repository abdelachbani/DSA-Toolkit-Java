# DSA Toolkit (Java)
A comprehensive, from-scratch implementation of fundamental data structures and algorithms in Java.

## Data Structures
| Structure | Description |
|-----------|-------------|
| `Queue` / `CircularArrayQueue` | Circular array-based FIFO queue. |
| `PriorityQueue` / `BinaryHeap` | Priority queue implemented as a binary min-heap. |
| `CursorList` / `LinkedCursorList` | A linked list with an internal cursor for sequential access. |
| `SortedLinkedCursorList` | An extension of CursorList maintaining sorted order. |
| `Map` / `HashTable` | A hash table implementation of a dictionary mapping keys to values. |
| `BinarySearchTree` | A binary search tree implementation. |
| `UnionFind` / `DisjointSetForest` | Disjoint-set forest for union-find operations. |
| `Graph` (`DirectedGraph`, `UndirectedGraph`) | Graph representations. |

## Algorithms
- **Sorting**: Includes quicksort, mergesort (with functional variants), and median-of-three.
- **Graph Algorithms**: BFS traversals, spanning trees, and Kruskal's algorithm for minimum spanning trees.

## Demos
- **BST Visualizer**: A graphical interface for building and visualizing binary search trees.
- **Hospital OR Simulator**: Simulates different queueing policies (FIFO, Priority) for a hospital operating room.

## Compilation and Execution
To compile the source code, navigate to the `src` directory and run:
```bash
javac -d ../bin -sourcepath . $(find . -name "*.java")
```
To run a specific test or demo, use the `java` command from the `bin` directory.
