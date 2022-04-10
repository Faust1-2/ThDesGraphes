package GraphePackage;

import java.util.*;
import java.util.List;

public class Graph {
    private final GraphState firstState;
    private GraphState lastState;
    private final String fileLocation;


    public Graph(String fileLocation) {
        this.firstState = new GraphState(0, 0);
        this.fileLocation = fileLocation;
    }

    /**
     * Function that takes all states of a graph and put it into a list.
     * @return list of all graph states.
     */
    public List<GraphState> getAllStates(){
        Set<GraphState> grapheStateSet = new HashSet<>(firstState.getSuccessors());
        grapheStateSet.add(firstState);

        for (GraphState state : firstState.getSuccessors()){
            state.returnAllSuccessors(grapheStateSet);
        }

        return grapheStateSet.stream() // This stream is to sort the list.
                .sorted(Comparator.comparing(GraphState::getStateName))
                .toList();
    }

    /**
     * Function that creates the graph.
     * Use readFile from GraphFileReader to create the graph.
     * Associate a graph to its successors and predecessors.
     * Associate the entry states with the first state of the graph, so there is only one entry for the graph.
     * Create a final state wich the successors of every state without natural successors.
     */
    public boolean initializeGraphe(){
        try {
            Map<GraphState, Set<Integer>> grapheStringsMap = GraphFileReader.readFile(fileLocation).orElseThrow();
            lastState = new GraphState(grapheStringsMap.size()+1, 0); // Creation of the last state of the graph.
            for(GraphState key : grapheStringsMap.keySet()){
                if (grapheStringsMap.get(key).isEmpty()){  // If no predecessors
                    this.firstState.addSuccessor(key);
                    key.addPredecessor(this.firstState);
                }
                int count = 0; // variable to check if a state has successors or no.
                for (Set<Integer> value : grapheStringsMap.values()){

                    if (key.isThisGraphInSet(value)){ // Check if this key (state) is one of the predecessors of another state.
                        GraphState selectedState = getKeyFromValue(grapheStringsMap, value).orElseThrow();
                        key.addSuccessor(selectedState);
                        selectedState.addPredecessor(key);
                    }
                    else{ // If no, count increase.
                        count++;
                    }
                    if (count == grapheStringsMap.size()){ // if count is equal to the size of the map, then he has no natural successors.
                        lastState.addPredecessor(key);
                        key.addSuccessor(lastState);
                    }
                }
            }
            return true;
        } catch (NoSuchElementException nsee) {
            System.out.println("Une erreur a eu lieu. Veuillez choisir un nouveau graphe.\n");
        }
        return false;
    }

    /**
     * Function that set the rank of every state of the graph.
     */
    public void setAllRanks(){
        Queue<GraphState> queueRank = new ArrayDeque<>(firstState.getSuccessors());

        firstState.setRank();

        while (!queueRank.isEmpty()) {
            boolean isRankable = true;
            GraphState actState = queueRank.remove();
            for (GraphState state : actState.getPredecessors()){
                if (queueRank.contains(state)) {
                    isRankable = false;
                    break;
                }
            }
            if (isRankable) {
                actState.setRank();
                queueRank.addAll(actState.getSuccessors());

            } else {
                queueRank.add(actState);
            }
        }
    }

    public String getAllRanksString(){
        List<GraphState> listOfStates = getAllStates();

        StringBuilder name = new StringBuilder();
        StringBuilder rank = new StringBuilder();

        name.append("Etat |");
        rank.append("Rang |");

        for (GraphState state : listOfStates) {

            int nameInt = state.getStateName(); // First part -- Name
            if (nameInt < 10) name.append("   ").append(nameInt).append(" |");
            else if (nameInt < 100) name.append("  ").append(nameInt).append(" |");
            else name.append(" ").append(nameInt).append(" |");

            int rankInt = state.getRank();
            if (rankInt < 10) rank.append("   ").append(rankInt).append(" |");
            else if (rankInt < 100) rank.append("  ").append(rankInt).append(" |");
            else rank.append(" ").append(rankInt).append(" |");

        }

        return name.append("\n").append(rank).toString();
    }

    /**
     * Tool function to get a key of a map from the value of the map.
     * @param mapToCheck : the map to check
     * @param value : the used value
     * @return key, a state.
     */
    public Optional<GraphState> getKeyFromValue(Map<GraphState, Set<Integer>> mapToCheck, Set<Integer> value){
        for (GraphState key : mapToCheck.keySet()){
            if (mapToCheck.get(key) == value){
                return Optional.of(key);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        List<GraphState> grapheList = getAllStates();

        for (GraphState state : grapheList){
            s.append(state.toString());
        }

        return s.toString();
    }

    /**
     * Function that creates the adjacency matrix of the graph.
     * An adjacency matrix represents the links between the states of a graph.
     * @return adjacencyMatrix : StateName => resultLinkList
     * The result LinkList is composed by 0 and 1. 0 where there isn't link between a state and another, 1 otherwise.
     */
    public HashMap<Integer, List<Integer>> computeAdjacencyMatrix(){

        List<GraphState> listOfStates = getAllStates();
        List<Integer> listOFAllNames = listOfStates.stream().map(GraphState::getStateName).toList();
        HashMap<Integer, List<Integer>> finalResultMatrix = new HashMap<>();

        for (GraphState state : listOfStates){
            List<Integer> listOfLine = new ArrayList<>();
            for(Integer steName : listOFAllNames){
                if (state.isThisSuccessor(steName)){
                    listOfLine.add(1);
                } else {
                    listOfLine.add(0);
                }
            }
            finalResultMatrix.put(state.getStateName(), listOfLine);
        }

        return finalResultMatrix;
    }

    public StringBuilder matrixString(Collection<Integer> collection) {
        StringBuilder s = new StringBuilder("    ");
        for (Integer i : collection){
            if (i < 10){
                s.append("  ").append(i).append(" ");
            } else if (i < 100) {
                s.append(" ").append(i).append(" ");
            } else {
                s.append(i).append(" ");
            }
        }
        s.append('\n');
        return s;
    }

    /**
     * Function that is used as a toString method of the adjacencyMatrix.
     * First line : state names; First column : state names.
     * The lines are the resultLinkList of the adjacencyMatrix.
     */
    public void showAdjacencyMatrix(){

        HashMap<Integer, List<Integer>> adjacencyMatrix = computeAdjacencyMatrix();
        Set<Integer> steSet = adjacencyMatrix.keySet();
        StringBuilder s = matrixString(steSet);

        for (Integer i :steSet){
            List<Integer> resultList = adjacencyMatrix.get(i);
            if (i < 10){
                s.append("  ").append(i).append(" ");
            } else if (i < 100) {
                s.append(" ").append(i).append(" ");
            } else {
                s.append(i).append(" ");
            }
            for (int result : resultList){
                s.append("  ").append(result).append(" ");
            }
            s.append('\n');
        }
        System.out.println(s.toString());
    }

    /**
     * Function that create the value matrix of the graph, show it and return it.
     */
    public void valueMatrix(){
        List<GraphState> listOfStates = getAllStates();
        List<Integer> listOFAllNames = listOfStates.stream().map(GraphState::getStateName).toList();

        StringBuilder s = matrixString(listOFAllNames);

        for (GraphState state : listOfStates){
            Integer stateName = state.getStateName();
            if (stateName < 10){
                s.append("  ").append(stateName).append(" ");
            } else if (stateName < 100) {
                s.append(" ").append(stateName).append(" ");
            } else {
                s.append(state.getStateName()).append(" ");
            }
            for (Integer name : listOFAllNames){

                if (state.isThisSuccessor(name)){
                    int duration = state.getDuration();
                    if (duration < 10){
                        s.append("  ").append(duration).append(" ");
                    } else if (duration < 100) {
                        s.append(" ").append(duration).append(" ");
                    } else {
                        s.append(duration).append(" ");
                    }
                } else {
                  s.append("  ").append("*").append(" ");
                }
            }
            s.append('\n');
        }
        System.out.println(s);
    }

    /**
     * Function to check if the graph has a circuit or not.
     * Use the entry states elimination method.
     * Show the details of the method.
     * @return false if there is no circuit; true otherwise.
     */
    public boolean circuitDetectionDetailed() {

        System.out.println("* Detection de circuit.");
        System.out.println("* Methode d'elimination des points d'entree");

        ArrayList<GraphState> listOfAllStates = new ArrayList<>(getAllStates());
        boolean entryListEmpty = false;
        while (!entryListEmpty){
            List<GraphState> listOfEntries = getAllEntryStates(listOfAllStates);
            if (!listOfEntries.isEmpty()) {
                StringBuilder message = new StringBuilder("Points d'entree : ");
                for (GraphState entry : listOfEntries) {
                    message.append(entry.getStateName()).append(" ");
                }
                System.out.println(message);
                for (GraphState entry : listOfEntries) {
                    List<GraphState> entrySuccessor = entry.getSuccessors();
                    for (GraphState successor : entrySuccessor) {
                        successor.removePredecessor(entry);
                    }
                    listOfAllStates.remove(entry);
                }
                System.out.println("Supression des points d'entree");
                message = new StringBuilder("Sommets restant : ");
                if (listOfAllStates.isEmpty()){
                    message.append("Aucun");
                } else {
                    for (GraphState state : listOfAllStates){
                        message.append(state.getStateName()).append(" ");
                    }
                }
                System.out.println(message);

            } else {
                if (!listOfAllStates.isEmpty()){
                    System.out.println("Il n'y a pas de points d'entree.");
                }
                entryListEmpty = true;
            }
        }

        if (listOfAllStates.isEmpty()) {
            System.out.println("-> Il n'y a pas de circuit");
            return false;
        } else {
            System.out.println("-> Il y a un circuit");
            return true;
        }

    }

    /**
     * Function to check if the graph has a circuit or not.
     * Use the entry states elimination method.
     * @return false if there is no circuit; true otherwise.
     */
    public boolean circuitDetection() {

        ArrayList<GraphState> listOfAllStates = new ArrayList<>(getAllStates());
        boolean entryListEmpty = false;
        while (!entryListEmpty){
            List<GraphState> listOfEntries = getAllEntryStates(listOfAllStates);
            if (!listOfEntries.isEmpty()) {
                for (GraphState entry : listOfEntries) {
                    List<GraphState> entrySuccessor = entry.getSuccessors();
                    for (GraphState successor : entrySuccessor) {
                        successor.removePredecessor(entry);
                    }
                    listOfAllStates.remove(entry);
                }

            } else {
                entryListEmpty = true;
            }
        }

        return !listOfAllStates.isEmpty();

    }


    /**
     * Function that returns all entries of the current graph.
     * @param listOfAllStates, a list composed of EVERY state of the graph?
     * @return List of every entry of the graph.
     */
    public List<GraphState> getAllEntryStates(List<GraphState> listOfAllStates){
        return listOfAllStates.stream().filter(GraphState::hasNoPredecessor).toList();
    }

    /**
     * Function that check if every state in the graph has no negative duration.
     * @param detail : if true, show the details of the method
     * @return true if yes; false otherwise
     */
    public boolean hasNegativeDuration(boolean detail){
        List<GraphState> listOfALlStates = getAllStates();
        for (GraphState state : listOfALlStates){
            if (state.getDuration() < 0){
                if (detail) System.out.println("Un arc incident negatif a ete detecte.");
                return true;
            }
        }
        return false;
    }

    /**
     * Test if the current graph is a scheduling graph.
     * It means :
     * <ul>
     *     <li>Only one entry state. (Created at the graph initialization)</li>
     *     <li>Only one exit state. (Created at the graph initialization)</li>
     *     <li>No circuit in the graph. -> circuitDetection function.</li>
     *     <li>All incidents arcs have the same duration. (The implementation of the graph validates this condition)</li>
     *     <li>The duration of the first state is equal to zero. (The implementation of the graph validates this condition)</li>
     *     <li>No negative duration. -> hasNoNegativeDuration function.</li>
     * </ul>
     * @param detail : if true, show the details of the method
     * @return true if scheduling graph; false otherwise
     */
    public boolean isSchedulingGraph(boolean detail){
        if (detail) {
            if (hasNegativeDuration(true) || circuitDetectionDetailed()) {
                System.out.println("Ce graphe n'est pas un graphe d'ordonnancement.");
                return false;
            }
            System.out.println("Ce graphe est un graphe d'ordonnancement.");
        } else {
            return !hasNegativeDuration(false) && !circuitDetection();
        }
        return true;
    }

    public void createSoonestDate(){

        ArrayList<GraphState> listOfStatePerRank = new ArrayList<>(getAllStates().stream().sorted(Comparator.comparing(GraphState::getRank)).toList());
        listOfStatePerRank.remove(0);

        for (GraphState state : listOfStatePerRank){
            state.setSoonestDate();
        }

    }

    public String getSoonestDate() {
        StringBuilder statesnames = new StringBuilder("Nom  |");
        StringBuilder statesSoonestDate = new StringBuilder("Date |");

        ArrayList<GraphState> listOfStatePerRank = new ArrayList<>(getAllStates().stream().sorted(Comparator.comparing(GraphState::getRank)).toList());

        for (GraphState state : listOfStatePerRank){
            int name = state.getStateName();
            int date = state.getSoonestDate();
            if (name < 10)
                statesnames.append("   ").append(name).append(" |");
            else if (name < 100)
                statesnames.append("  ").append(name).append(" |");
            else
                statesnames.append(" ").append(state.getStateName()).append(" |");
            if (date < 10)
                statesSoonestDate.append("   ").append(date).append(" |");
            else if (date < 100 )
                statesSoonestDate.append("  ").append(date).append(" |");
            else
                statesSoonestDate.append(" ").append(date).append(" |");
        }

        return statesnames.append("\n").append(statesSoonestDate).toString();
    }

    public void createLatestDate(){


        ArrayList<GraphState> listOfStatePerRank = new ArrayList<>(getAllStates().stream().sorted(Comparator.comparing(GraphState::getRank)).toList());
        Collections.reverse(listOfStatePerRank);
        listOfStatePerRank.remove(0);

        lastState.setLatestDate();

        for (GraphState state : listOfStatePerRank){
            state.setLatestDate();
        }
    }

    public String getLatestDate(){

        StringBuilder statesnames = new StringBuilder("Nom  |");
        StringBuilder statesLatestDate = new StringBuilder("Date |");

        ArrayList<GraphState> listOfStatePerRank = new ArrayList<>(getAllStates().stream().sorted(Comparator.comparing(GraphState::getRank)).toList());
        Collections.reverse(listOfStatePerRank);

        for (GraphState state : listOfStatePerRank){
            int name = state.getStateName();
            int date = state.getLatestDate();
            if (name < 10)
                statesnames.append("   ").append(name).append(" |");
            else if (name < 100)
                statesnames.append("  ").append(name).append(" |");
            else
                statesnames.append(" ").append(state.getStateName()).append(" |");
            if (date < 10)
                statesLatestDate.append("   ").append(date).append(" |");
            else if (date < 100 )
                statesLatestDate.append("  ").append(date).append(" |");
            else
                statesLatestDate.append(" ").append(date).append(" |");

        }

        return statesnames.append("\n").append(statesLatestDate).toString();
    }

    public String marginDate(){
        ArrayList<GraphState> listOfStates = new ArrayList<>(getAllStates());
        StringBuilder sNames = new StringBuilder();
        StringBuilder sMargin = new StringBuilder();

        sNames.append("Sommet |");
        sMargin.append("Marge  |");

        for (GraphState state : listOfStates) {
            int nameInt = state.getStateName(); // First part -- Name
            if (nameInt < 10) sNames.append("   ").append(nameInt).append(" |");
            else if (nameInt < 100) sNames.append("  ").append(nameInt).append(" |");
            else sNames.append(" ").append(nameInt).append(" |");

            int margin = state.getMargin();
            if (margin < 10) sMargin.append("   ").append(margin).append(" |");
            else if (margin < 100) sMargin.append("  ").append(margin).append(" |");
            else sMargin.append(" ").append(margin).append(" |");
        }

        return sNames.append("\n").append(sMargin).toString();
    }
}