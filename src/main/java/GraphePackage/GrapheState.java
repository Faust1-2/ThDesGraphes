package GraphePackage;

import java.util.List;

public class GrapheState {

    private List<GrapheState> predecessors; // Contraintes
    private List<GrapheState> successors; // Là où ils nous mènent
    private int stateNumber;
    private int duration;

    public GrapheState(int stateNumber, int duration, List<GrapheState> predecessors) {
        this.stateNumber = stateNumber;
        this.duration = duration;
        this.predecessors = predecessors;
    }

    @Override
    public String toString() {
        String s = "";
        for (GrapheState successor: successors){
            s += stateNumber + " -> " + successor + " = " +  duration + "\n";
        }
        return s;
    }
}
