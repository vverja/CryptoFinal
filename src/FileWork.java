import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FileWork {
    private Path filepath;

    public String readFile(String filepath) throws IOException{
            this.filepath = Paths.get(filepath);
            try(BufferedReader reader = Files.newBufferedReader(this.filepath)){
                return reader.lines().collect(Collectors.joining());
            }
    }
    public void writeFile(String text, String suffix) throws IOException{
        String[] splittedFileName = filepath.toString().split("\\.");
        Path filenameWithsSuffix = Paths.get(filepath.toString()+suffix);
        if(splittedFileName.length>1)
            filenameWithsSuffix = Paths.get(splittedFileName[0] + suffix + "." + splittedFileName[1]);
        try (BufferedWriter writer = Files.newBufferedWriter(filenameWithsSuffix)) {
            writer.write(text);
            System.out.println("Файл записан " + filenameWithsSuffix);
        }
    }
}
