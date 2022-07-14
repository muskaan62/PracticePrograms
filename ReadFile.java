import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadFile {
    public static void main(String[] args) {

        try (Stream<Path> files = Files.list(Paths.get("/home/gaian/IdeaProjects/quiz/src"))) {
            long count = files.count();
            System.out.println("There are "+count +".java files ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File dir = new File("/home/gaian/IdeaProjects/quiz/src");
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                               if (file.getName().contains(".java")) {
                    System.out.println(file.getName()+" "+file.getAbsolutePath());
                }

            }
        }
    }
}