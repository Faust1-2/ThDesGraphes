package GraphePackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Optional<Map<Integer, List<Integer>>> mapOfInts = GrapheFileReader.readFile("graphe.txt");
        if (mapOfInts.isPresent()){
            System.out.println(mapOfInts.stream().toList());
        }
    }
}