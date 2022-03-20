package GraphePackage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrapheTest {

    @Test
    public void testMapIntToMapState(){
        System.out.println("\n---------------Graphe Test---------------\n");
        Graphe graphe = new Graphe("graphe.txt");
        graphe.initializeGraphe();
        graphe.setAllRanks();
        System.out.println(graphe);
    }

}