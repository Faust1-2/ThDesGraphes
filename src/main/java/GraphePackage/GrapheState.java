package GraphePackage;

import java.util.*;

public class GrapheState {

    private List<GrapheState> predecessors; // Contraintes
    private List<GrapheState> successors; // Là où ils nous mènent
    private final int stateName;
    private final int duration;
    private int rank;

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

    public int getRank() { return rank; }

    /**
     * ToString method
     * @return String - (rank) stateName -> successor(s) = duration
     */
    @Override
    public String toString() {
        if (!successors.isEmpty()) {
            String s = "";
            for (GrapheState successor : successors) {
                s += "\n(" + rank + ") " + stateName + " -> " + successor.stateName + " = " + duration;
            }
            return s;
        }
        return "";
    }

    /**
     * Add a state as a successor of a current set.
     * @param state state to had.
     */
    public void addSuccessor(GrapheState state){
        if (successors.isEmpty()){
            successors = new ArrayList<>();
        }
        successors.add(state);
    }

    /**
     * Add a state as a predecessor of a current set.
     * @param state state to had.
     */
    public void addPredecessor(GrapheState state){
        if (predecessors.isEmpty()){
            predecessors = new ArrayList<>();
        }
        predecessors.add(state);
    }

    /**
     * Check if current state is in the specified set
     * @param setToCheck set of integers, integers being the name of the state (stateName)
     * @return true if yes; false otherwise
     */
    public boolean isGrapheNameInSet(Set<Integer> setToCheck){
        for(Integer s : setToCheck){
            if (s.equals(stateName))
                return true;
        }
        return false;
    }

    /**
     * Check if current state has any successor.
     * In others terms, the function check if the state is a final state.
     * @return true if it has successor; false otherwise.
     */
    public boolean hasSuccessor(){
        return !successors.isEmpty();
    }

    /**
     * Function that takes a set of graphe and add all successors and their successors of this state to this set.
     * @param grapheStateSet the set has to be initialized.
     */
    public void returnAllSuccessors(Set<GrapheState> grapheStateSet){
        if (this.hasSuccessor()) {
            for (GrapheState successor : successors){
                if (successor.getStateName() != stateName){ // To prevent looping on itself
                    grapheStateSet.add(successor);
                    successor.returnAllSuccessors(grapheStateSet);
                }
            }
        }
    }

    public void setRank(){
        if (!predecessors.isEmpty()){
            int newrank = 0;
            for (GrapheState predecessor : predecessors){
                if (predecessor.getRank() > newrank)
                    newrank = predecessor.rank;
            }
            rank = newrank + 1;
        } else {
            rank = 0;
        }
    }
}
