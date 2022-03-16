package GraphePackage;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Graphe {
    GrapheState[] firstStates;
    String fileLocation;

    public void GrapheCreation(String fileLocation){

    }

    public static void mapIntToMapState(String fileLocation){
        Optional<Map<Integer, List<Integer>>> mapOfInts = GrapheFileReader.readFile(fileLocation);
        if (mapOfInts.isPresent()){
            List<GrapheState> listOfStates = new ArrayList<>();
            for(Map.Entry<Integer, List<Integer>> entry : mapOfInts.get().entrySet()){
                listOfStates.add(new GrapheState(entry.getKey(), entry.getValue().get(0)));
            }

        }
    }
}
