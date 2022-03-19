package GraphePackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GrapheFileReaderTest {

    @BeforeEach
    void setUp() {
        System.out.println("GrapheFileReader.setUp...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("GrapheFileReader.tearDown...");
    }

    @Test
    public void testReadFile(){
        System.out.println("\n---------------ReadFile test---------------\n");
        Optional<Map<GrapheState, Set<Integer>>> mapOfInts = GrapheFileReader.readFile("graphe.txt");
        if (mapOfInts.isPresent()){
            System.out.println(mapOfInts.stream().toList());
        }
    }

    @Test
    public void correctGrapheFile(){
        System.out.println("\n---------------CorrectGrapheFile test---------------\n");
        assertFalse(GrapheFileReader.correctGrapheFile("src/test/java/GraphePackage/graphe.txt"));
        assertTrue(GrapheFileReader.correctGrapheFile("src/test/java/GraphePackage/graphe1.txt"));
        assertFalse(GrapheFileReader.correctGrapheFile("src/test/java/GraphePackage/graphe2.txt"));
    }
}