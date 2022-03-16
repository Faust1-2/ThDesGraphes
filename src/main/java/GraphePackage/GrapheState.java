package GraphePackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GrapheState {

    private Optional<List<GrapheState>> predecessors; // Contraintes
    private Optional<List<GrapheState>> successors; // Là où ils nous mènent
    private int stateNumber;
    private int duration;

    public GrapheState(int stateNumber, int duration) {
        this.stateNumber = stateNumber;
        this.duration = duration;
        this.predecessors = Optional.empty();
        this.successors = Optional.empty();
    }

    @Override
    public String toString() {
        if (successors.isEmpty())
            return stateNumber + " = " + duration;
        else {
            String s = "";
            for (GrapheState successor : successors.orElseThrow()){
                s += stateNumber + " -> " + successor + " = " + duration;
            }
            return s;
        }
    }

    public void addSuccessor(GrapheState state){
        if (successors.isEmpty()){
            successors = Optional.of(new ArrayList<>());
        }
        successors.orElseThrow().add(state);
    }

    public void addPredecessor(GrapheState state){
        if (predecessors.isEmpty()){
            predecessors = Optional.of(new ArrayList<>());
        }
        predecessors.orElseThrow().add(state);
    }
}
