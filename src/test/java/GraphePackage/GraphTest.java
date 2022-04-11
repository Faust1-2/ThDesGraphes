package GraphePackage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    public void testinitializeGraphe(){
        System.out.println("\n---------------B3_Graph Test---------------\n");
        B3_Graph graph = new B3_Graph("graphe.txt");
        graph.initializeGraphe();
        graph.setAllRanks();
        System.out.println(graph);
        System.out.println(graph.circuitDetection());
    }

    @Test
    public void testAdjacenceMatrix(){
        System.out.println("\n---------------Adjacence Matrix Test---------------\n");
        B3_Graph graph = new B3_Graph("src/test/java/GraphePackage/graphe1.txt");
        graph.initializeGraphe();
        graph.showAdjacencyMatrix();
    }

    @Test
    public void testValueMatrix(){
        System.out.println("\n---------------Value Matrix Test---------------\n");
        B3_Graph graph = new B3_Graph("src/test/java/GraphePackage/graphe1.txt");
        graph.initializeGraphe();
        graph.valueMatrix();
    }

    @Test
    public void testCircuitDetection(){
        System.out.println("\n---------------Circuit detection Test---------------\n");
        B3_Graph graph = new B3_Graph("src/test/java/GraphePackage/graphe1.txt");
        graph.initializeGraphe();
        assertFalse(graph.circuitDetection());
    }

    @Test
    public void testSchedulingGraph(){
        System.out.println("\n---------------Scheduling graph Test---------------\n");
        B3_Graph graph = new B3_Graph("src/test/java/GraphePackage/graphe3.txt");
        graph.initializeGraphe();
        graph.setAllRanks();
        System.out.println(graph);
        assertFalse(graph.isSchedulingGraph(false));
    }

    @Test
    public void testSoonestDate(){
        System.out.println("\n---------------Soonest date Test---------------\n");
        B3_Graph graph = new B3_Graph("src/test/java/GraphePackage/Graphe-C2.txt");
        graph.initializeGraphe();
        graph.setAllRanks();
        graph.createSoonestDate();
        System.out.println(graph.getSoonestDate());
    }

    @Test
    public void testLatestDate(){
        System.out.println("\n---------------Latest date Test---------------\n");
        B3_Graph graph = new B3_Graph("src/test/java/GraphePackage/Graphe-C3.txt");
        graph.initializeGraphe();
        graph.setAllRanks();
        graph.createSoonestDate();
        graph.createLatestDate();
        graph.getLatestDate();
        System.out.println(graph.getLatestDate());
    }
}