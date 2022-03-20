package GraphePackage;

import java.util.*;
import java.util.List;

public class Graphe {
    private final GrapheState firstState;
    private GrapheState lastState;
    private final String fileLocation;


    public Graphe(String fileLocation) {
        this.firstState = new GrapheState(0, 0);
        this.fileLocation = fileLocation;
    }

    public List<GrapheState> getAllStates(){
        Set<GrapheState> grapheStateSet = new HashSet<>(firstState.getSuccessors());

        for (GrapheState state : firstState.getSuccessors()){
            state.returnAllSuccessors(grapheStateSet);
        }

        List<GrapheState> grapheList = grapheStateSet.stream()
                .sorted(Comparator.comparing(GrapheState::getStateName))
                .toList();

        return grapheList;
    }

    public void initializeGraphe(){
        try {
            Map<GrapheState, Set<Integer>> grapheStringsMap = GrapheFileReader.readFile(fileLocation).orElseThrow();
            lastState = new GrapheState(grapheStringsMap.size()+1, 0);
            for(GrapheState key : grapheStringsMap.keySet()){
                if (grapheStringsMap.get(key).isEmpty()){
                    this.firstState.addSuccessor(key);
                    key.addPredecessor(this.firstState);
                }
                int count = 0;
                for (Set<Integer> value : grapheStringsMap.values()){

                    if (key.isGrapheNameInSet(value)){
                        GrapheState selectedState = getKeyFromValue(grapheStringsMap, value).orElseThrow();
                        key.addSuccessor(selectedState);
                        selectedState.addPredecessor(key);
                    }
                    else{
                        count++;
                    }
                    if (count == grapheStringsMap.values().size()){ // if no successors
                        lastState.addPredecessor(key);
                        key.addSuccessor(lastState);
                    }
                }
            }
        } catch (NoSuchElementException nsee) {
            System.out.println("End of lecture.");
        }
    }

    public void setAllRanks(){
        getAllStates().forEach(GrapheState::setRank);

    }

    public Optional<GrapheState> getKeyFromValue(Map<GrapheState, Set<Integer>> mapToCheck, Set<Integer> value){
        for (GrapheState key : mapToCheck.keySet()){
            if (mapToCheck.get(key) == value){
                return Optional.of(key);
            }
        }
        return Optional.empty();
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        List<GrapheState> grapheList = getAllStates();

        s.append(firstState);
        for (GrapheState state : grapheList){
            s.append(state.toString());
        }

        return s.toString();
    }

    public String adjacenceMatrix(){
        return "";
    }

}
