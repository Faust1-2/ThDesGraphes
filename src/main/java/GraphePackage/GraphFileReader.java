package GraphePackage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GraphFileReader {

    public static int listTables() {
        return Stream.of(new File("Tables").listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet()).size();
    }

    /**
     * Function that checks if the file is adapted to be a graph.
     * For now it consist to looks if every character in the file is a number.
     * @param fileLocation
     * @return false if char in column 2, true if not
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
                            Integer.parseInt(elem);
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
     * Function that takes a string as a file to read and generate a map of GraphState as key and String as values.
     * The values are representing the dependencies of the states, so they have to be add after that function.
     * @param fileLocation
     * @return Map, key = GraphState, value = List of String
     */
    public static Optional<Map<GraphState, Set<Integer>>> readFile(String fileLocation) {
        if (correctGrapheFile(fileLocation)){
            try(Stream<String> lines = Files.lines(Path.of(fileLocation))) {
                return Optional.of(lines.map(line -> Arrays.stream(line.split(" "))
                                .map(Integer::parseInt).toList())
                        .toList()
                        .stream()
                        .collect(Collectors.toMap(streamList -> new GraphState(streamList.get(0), streamList.get(1)), streamList -> streamList
                                .stream()
                                .skip(2)
                                .collect(Collectors.toSet()))));
            }catch (IOException ioException){
                System.out.println("An error occurred while reading the file.");
            }
        } else{
            System.out.println("Please make sure to give an adapted file. Wrong file : " + fileLocation);
        }
        return Optional.empty();
    }


}
