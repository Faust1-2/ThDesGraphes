package GraphePackage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class B3_GraphFileReader {

    public static int listTables() {
        return Stream.of(Objects.requireNonNull(new File("Tables").listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet()).size();
    }

    /**
     * Function that checks if the file is adapted to be a graph.
     * For now it consist to looks if every character in the file is a positive number.
     * @param fileLocation the location of the file on the hard-drive.
     * @return false if not an integer nor positive, true if not
     */
    public static boolean correctGrapheFile(String fileLocation) {
        try {
            File file = new File(fileLocation);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null){
                if (line.length() > 2){
                    try {
                        String[] lineList = line.split(" ");
                        for (String elem : lineList){
                            int elemInt = Integer.parseInt(elem);
                            if (elemInt < 0)
                                return false;
                        }
                    } catch (NumberFormatException nfe) {
                        return false;
                    }
                } else {
                    return false;
                }
                line = bufferedReader.readLine();
            }
            return true;
        } catch (IOException ioe){
            System.out.println("A problem happened while reading the file");
        }
        return false;
    }


    /**
     * Function that takes a string as a file to read and generate a map of B3_GraphState as key and String as values.
     * The values are representing the dependencies of the states, so they have to be add after that function.
     * @param fileLocation the location of the file on the hard-drive.
     * @return Map, key = B3_GraphState, value = List of String
     */
    public static Optional<Map<B3_GraphState, Set<Integer>>> readFile(String fileLocation) {
        if (correctGrapheFile(fileLocation)){
            try(Stream<String> lines = Files.lines(Path.of(fileLocation))) {
                return Optional.of(lines.map(line -> Arrays.stream(line.split(" "))
                                .map(Integer::parseInt).toList())
                        .toList()
                        .stream()
                        .collect(Collectors.toMap(streamList -> new B3_GraphState(streamList.get(0), streamList.get(1)), streamList -> streamList
                                .stream()
                                .skip(2)
                                .collect(Collectors.toSet()))));
            }catch (IOException ioException){
                System.out.println("An error occurred while reading the file.");
            }
        } else{
            System.out.println("\nVeuillez ??tre s??r de fournir un graphe adapt?? : " + fileLocation + "\n");
        }
        return Optional.empty();
    }


}
