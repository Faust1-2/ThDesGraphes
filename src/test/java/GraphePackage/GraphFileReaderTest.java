package GraphePackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GraphFileReaderTest {

    @BeforeEach
    void setUp() {
        System.out.println("GraphFileReader.setUp...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("GraphFileReader.tearDown...");
    }

    @Test
    public void testReadFile(){
        System.out.println("\n---------------ReadFile test---------------\n");
        Optional<Map<GraphState, Set<Integer>>> mapOfInts = GraphFileReader.readFile("graphe.txt");
        if (mapOfInts.isPresent()){
            System.out.println(mapOfInts.stream().toList());
        }
    }

    @Test
    public void correctGrapheFile(){
        System.out.println("\n---------------CorrectGrapheFile test---------------\n");
        assertFalse(GraphFileReader.correctGrapheFile("src/test/java/GraphePackage/graphe.txt"));
        assertTrue(GraphFileReader.correctGrapheFile("src/test/java/GraphePackage/graphe1.txt"));
        assertFalse(GraphFileReader.correctGrapheFile("src/test/java/GraphePackage/graphe2.txt"));
    }
}