package GraphePackage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GrapheFileReader {

    public static Optional<Map<Integer, List<Integer>>> readFile(String fileLocation) {
        try(Stream<String> lines = Files.lines(Path.of(fileLocation))) {
             return Optional.of(lines.map(line -> Arrays.stream(line.split(" "))
                    .map(Integer::parseInt).toList())
                    .toList()
                    .stream()
                    .collect(Collectors.toMap(intlist -> intlist.get(0), intlist -> intlist
                            .stream()
                            .skip(1)
                            .toList())));
        }catch (IOException ioException){
            System.out.println("An error occurred while reading the file.");
        }
        return Optional.empty();
    }
}
