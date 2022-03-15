package GraphePackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        GrapheFileReader.readFile("graphe.txt");
        assertEquals(1, 1);
    }
}