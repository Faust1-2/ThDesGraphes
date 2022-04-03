import GraphePackage.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner readerInput = new Scanner(System.in);

        System.out.println("Projet de theorie des graphes.");
        System.out.println("Veuillez choisir un des fichiers suivant (préciser uniquement le numéro) : ");

        int tableSize = GraphFileReader.listTables();
        boolean running = true;
        while (running) {
            int choice = -1;
            while (choice <= 0 || choice > tableSize) {
                for (int table = 0; table<tableSize; table++) {
                    System.out.println((table + 1) + ". table " + (table + 1));
                }
                System.out.print("\nVotre choix : ");
                try {
                    choice = Integer.parseInt(readerInput.nextLine());
                } catch (NumberFormatException nfe) {
                    choice = -1;
                }
                if (choice < 0 || choice > tableSize) {
                    System.out.println("Veuillez réessayer en rentrant le numéro du fichier que vous souhaitez lire.");
                }
            }
            Graph graphTable = new Graph("Tables/table " + choice + ".txt");
            boolean isInitialezed = graphTable.initializeGraphe();
            boolean isScheduling = false;
            boolean stayOnThisGraph = false;
            if (isInitialezed) {
                isScheduling = graphTable.isSchedulingGraph(false);
                graphTable = new Graph("Tables/table " + choice + ".txt");
                graphTable.initializeGraphe();
                if (isScheduling){
                    graphTable.setAllRanks();
                    graphTable.createSoonestDate();
                    graphTable.createLatestDate();
                }

                stayOnThisGraph = true;
            }

            while (stayOnThisGraph) {
                choice = -1;
                while (choice <= 0 || choice > 9) {
                    System.out.println("Que souhaitez-vous faire ?");
                    System.out.println("    1. Afficher la matrice de valeurs du graphe.");
                    System.out.println("    2. Afficher la matrice d'adjacence du graphe.");
                    System.out.println("    3. Vérifier que le graphe est un graphe d'ordonnancement.");
                    if (isScheduling) {
                        System.out.println("    4. Calculer les rangs des états du graphe.");
                        System.out.println("    5. Afficher les dates au plus tôt.");
                        System.out.println("    6. Afficher les dates au plus tard.");
                        System.out.println("    7. Afficher les dates au plus tôt, au plus tard, ainsi que la marge.");
                        System.out.println("    8. Changer de graphe.");
                        System.out.println("    9. Arrêter le programme.");
                    }
                    else {
                        System.out.println("    4. Changer de graphe.");
                        System.out.println("    5. Arrêter le programme.");
                    }
                    System.out.print("\nVotre choix : ");
                    try {
                        choice = Integer.parseInt(readerInput.nextLine());
                    } catch (NumberFormatException nfe) {
                        choice = -1;
                    }
                    if (isScheduling && (choice <= 0 || choice > 9)) {
                        System.out.println("Veuillez réessayer en rentrant le numéro du fichier que vous souhaitez lire.");
                    } else if (!isScheduling && (choice <= 0 || choice > 5)){
                        System.out.println("Veuillez réessayer en rentrant le numéro du fichier que vous souhaitez lire.");
                    }
                }
                switch (choice) {
                    case 1:
                        graphTable.valueMatrix();
                        break;
                    case 2:
                        graphTable.showAdjacencyMatrix();
                        break;
                    case 3:
                        graphTable.isSchedulingGraph(true);
                        break;
                    case 4:
                        if (isScheduling) {
                            System.out.println(graphTable.getAllRanksString());
                        } else {
                            stayOnThisGraph = false;
                        }
                        break;
                    case 5:
                        if (isScheduling) {
                            System.out.println(graphTable.getSoonestDate());
                        } else {
                            stayOnThisGraph = false;
                            running = false;
                        }
                        break;
                    case 6:
                        System.out.println(graphTable.getLatestDate());
                        break;
                    case 7:
                        System.out.println("\nDate au plus tôt :");
                        System.out.println(graphTable.getSoonestDate());
                        System.out.println("\nDate au plus tard :");
                        System.out.println(graphTable.getLatestDate());
                        System.out.println("\nMarge :");
                        System.out.println(graphTable.marginDate());
                        break;
                    case 8:
                        stayOnThisGraph = false;
                        break;
                    case 9:
                        stayOnThisGraph = false;
                        running = false;
                        break;
                }
            }
        }
    }


}
