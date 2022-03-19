package GraphePackage;

import java.util.*;

public class GrapheState {

    private List<GrapheState> predecessors; // Contraintes
    private List<GrapheState> successors; // Là où ils nous mènent
    private final int stateName;
    private final int duration;

    public GrapheState(int stateName, int duration) {
        this.stateName = stateName;
        this.duration = duration;
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
    }

    public int getStateName() {
        return stateName;
    }

    public int getDuration() {
        return duration;
    }

    public List<GrapheState> getPredecessors() {
        return predecessors;
    }

    public List<GrapheState> getSuccessors() {
        return successors;
    }

    @Override
    public String toString() {
        if (!successors.isEmpty()) {
            String s = "";
            for (GrapheState successor : successors) {
                s += "\n" + stateName + " -> " + successor.stateName + " = " + duration;
            }
            return s;
        }
        return "";
    }

    public void addSuccessor(GrapheState state){
        if (successors.isEmpty()){
            successors = new ArrayList<>();
        }
        successors.add(state);
    }

    public void addPredecessor(GrapheState state){
        if (predecessors.isEmpty()){
            predecessors = new ArrayList<>();
        }
        predecessors.add(state);
    }

    public boolean isGrapheNameInList(Set<Integer> listToCheck){
        for(Integer s : listToCheck){
            if (s.equals(stateName))
                return true;
        }
        return false;
    }

    public boolean hasSuccessor(){
        return !successors.isEmpty();
    }

    public void returnAllSuccessors(Set<GrapheState> grapheStateSet){
        if (this.hasSuccessor()) {
            for (GrapheState successor : successors){
                grapheStateSet.add(successor);
                successor.returnAllSuccessors(grapheStateSet);
            }
        }
    }
}
