import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ListFilesInDirectory {
    public static void main(String directoryPath) {
        //String directoryPath = "/path/to/your/directory"; // Replace with the actual directory path

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directoryPath))) {
            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                    System.out.println("File: " + path.getFileName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
