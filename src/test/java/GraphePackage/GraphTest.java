package GraphePackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    public void testinitializeGraphe(){
        System.out.println("\n---------------Graph Test---------------\n");
        Graph graph = new Graph("graphe.txt");
        graph.initializeGraphe();
        graph.setAllRanks();
        System.out.println(graph);
        System.out.println(graph.circuitDetection());
    }

    @Test
    public void testAdjacenceMatrix(){
        System.out.println("\n---------------Adjacence Matrix Test---------------\n");
        Graph graph = new Graph("src/test/java/GraphePackage/graphe1.txt");
        graph.initializeGraphe();
        String result = graph.showAdjacencyMatrix();
        String s = "      0   1   2   3   4   5   6 \n" +
                "  0   0   1   1   0   0   0   0 \n" +
                "  1   0   0   0   1   1   0   0 \n" +
                "  2   0   0   0   0   1   1   0 \n" +
                "  3   0   0   0   0   0   0   1 \n" +
                "  4   0   0   0   0   0   1   0 \n" +
                "  5   0   0   0   0   0   0   1 \n" +
                "  6   0   0   0   0   0   0   0 \n";
        assertEquals(s, result);
        System.out.println(result);
    }

    @Test
    public void testValueMatrix(){
        System.out.println("\n---------------Value Matrix Test---------------\n");
        Graph graph = new Graph("src/test/java/GraphePackage/graphe1.txt");
        graph.initializeGraphe();
        String result = graph.valueMatrix();

        String s = "      0   1   2   3   4   5   6 \n" +
                "  0   *   0   0   *   *   *   * \n" +
                "  1   *   *   *   1   1   *   * \n" +
                "  2   *   *   *   *   2   2   * \n" +
                "  3   *   *   *   *   *   *   3 \n" +
                "  4   *   *   *   *   *   4   * \n" +
                "  5   *   *   *   *   *   *   5 \n" +
                "  6   *   *   *   *   *   *   * \n";
        assertEquals(s, result);
    }

    @Test
    public void testCircuitDetection(){
        System.out.println("\n---------------Circuit detection Test---------------\n");
        Graph graph = new Graph("src/test/java/GraphePackage/graphe1.txt");
        graph.initializeGraphe();
        assertFalse(graph.circuitDetection());
    }

    @Test
    public void testSchedulingGraph(){
        System.out.println("\n---------------Scheduling graph Test---------------\n");
        Graph graph = new Graph("src/test/java/GraphePackage/graphe3.txt");
        graph.initializeGraphe();
        System.out.println(graph);
        assertFalse(graph.isSchedulingGraph(false));
    }

    @Test
    public void testSoonestDate(){
        System.out.println("\n---------------Soonest date Test---------------\n");
        Graph graph = new Graph("src/test/java/GraphePackage/Graphe-C2.txt");
        graph.initializeGraphe();
        graph.setAllRanks();
        graph.valueMatrix();
        System.out.println(graph.soonestDate());
    }

    @Test
    public void testLatestDate(){
        System.out.println("\n---------------Latest date Test---------------\n");
        Graph graph = new Graph("src/test/java/GraphePackage/Graphe-C3.txt");
        graph.initializeGraphe();
        graph.setAllRanks();
        graph.soonestDate();
        graph.latestDate();
    }
}