package GraphePackage;

import java.util.*;

public class GraphState {

    private List<GraphState> predecessors; // Contraintes
    private List<GraphState> successors; // Là où ils nous mènent
    private final int stateName;
    private final int duration;
    private int rank;
    private int soonestDate = 0;
    private int latestDate = -1;

    public GraphState(int stateName, int duration) {
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

    public List<GraphState> getPredecessors() {
        return predecessors;
    }

    public List<GraphState> getSuccessors() {
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
            StringBuilder s = new StringBuilder();
            successors = successors.stream().sorted(Comparator.comparing(GraphState::getStateName)).toList();
            for (GraphState successor : successors) {
                s.append("\n(").append(rank).append(") ").append(stateName).append(" -> ").append(successor.stateName).append(" = ").append(duration);
            }
            return s.toString();
        }
        return "";
    }

    /**
     * Add a state as a successor of a current set.
     * @param state state to had.
     */
    public void addSuccessor(GraphState state){
        if (successors.isEmpty()){
            successors = new ArrayList<>();
        }
        successors.add(state);
    }

    /**
     * Add a state as a predecessor of a current set.
     * @param state state to had.
     */
    public void addPredecessor(GraphState state){
        if (predecessors.isEmpty()){
            predecessors = new ArrayList<>();
        }
        predecessors.add(state);
    }

    /**
     * Remove a state from a predecessors list.
     * @param stateToRmv, the state to remove from the predecessors.
     */
    public void removePredecessor(GraphState stateToRmv){
        if (!predecessors.isEmpty()){
            predecessors.remove(stateToRmv);
        }
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
     * Check if current state has any predecessor.
     * In others terms, the function check if the state is an entry state.
     * @return true if it has no predecessors; false otherwise.
     */
    public boolean hasNoPredecessor(){
        return predecessors.isEmpty();
    }

    /**
     * Check if current state is in the specified set
     * @param setToCheck set of integers, integers being the name of the state (stateName)
     * @return true if yes; false otherwise
     */
    public boolean isThisGraphInSet(Set<Integer> setToCheck){
        return setToCheck.contains(stateName);
    }

    /**
     * Check if the integer represents the name of a successor of this state.
     * @param steName state name which is used to be compared to one of the predecessor
     * @return true if yes; false otherwise
     */
    public boolean isThisSuccessor(Integer steName) {
        for (GraphState grpState : successors){
            if (steName.equals(grpState.getStateName())){
                return true;
            }
        }
        return false;
    }

    /**
     * Function that takes a set of graphe and add all successors and their successors of this state to this set.
     * @param grapheStateSet the set has to be initialized.
     */
    public void returnAllSuccessors(Set<GraphState> grapheStateSet){
        if (this.hasSuccessor()) {
            for (GraphState successor : successors){
                if (successor.getStateName() != stateName && !grapheStateSet.contains(successor)){ // To prevent looping on itself
                    grapheStateSet.add(successor);
                    successor.returnAllSuccessors(grapheStateSet);
                }
            }
        }
    }

    /**
     * Function that sets the rank of this state of the graph.
     * Use the rank of its predecessors to calculate it.
     */
    public void setRank(){
        if (!predecessors.isEmpty()){
            int newrank = 0;
            for (GraphState predecessor : predecessors){
                if (predecessor.getRank() > newrank)
                    newrank = predecessor.rank;
            }
            rank = newrank + 1;
        } else {
            rank = 0;
        }
    }

    public void setSoonestDate(){
        for (GraphState predecessor : predecessors){
            int computeDate = predecessor.getSoonestDate() + predecessor.getDuration();
            if (computeDate > soonestDate) {
                soonestDate = computeDate;
            }
        }
    }

    public int getSoonestDate(){
        return soonestDate;
    }

    public void setLatestDate(){
        if (successors.isEmpty()){
            latestDate = soonestDate;
        }
        else {
            for (GraphState successor : successors) {
                int computeDate = successor.getLatestDate() - duration;
                if (latestDate < 0 || computeDate < latestDate) {
                    latestDate = computeDate;
                }
            }
        }
    }

    public int getLatestDate(){
        return latestDate;
    }

    public int getMargin() { return latestDate-soonestDate;}
}
