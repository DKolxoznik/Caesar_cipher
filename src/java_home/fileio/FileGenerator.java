package java_home.fileio;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileGenerator {
    public static void createSampleFile(String fileName, String fileContent){
        try {
            Path filePath = Paths.get(fileName);
            Files.write(filePath, fileContent.getBytes(), StandardOpenOption.CREATE);
            IO.println("Файл создан: " + fileName);
        } catch (IOException e) {
            IO.println("Ошибка создания файла: " + e.getMessage());
        }
    }

    public static void createLargeTestFile(String fileName, int lines){
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))){
            for (int i = 0; i < lines; i++) {
                writer.write("Строка " + (i + 1) + ": Это тестовый текст для проверки работы шифра Цезаря.\n");
            }
            IO.println("Большой файл создан: " + fileName + " (" + lines + " строк)");
        } catch (IOException e) {
            IO.println("Ошибка: " + e.getMessage());
        }
    }

    public static void createRussianTextFile(String filename) {
        String content = """
                В чащах юга жил бы цитрус? Да, но фальшивый экземпляр!
                Широкая электрификация южных губерний даст мощный толчок подъёму сельского хозяйства.
                Съешь же ещё этих мягких французских булок да выпей чаю.
                Любя, съешь щипцы, — вздохнёт мэр, — кайф жгуч.
                Южно-эфиопский грач увёл мышь за хобот на съезд ящериц.
                """;
        createSampleFile(filename, content);
    }

}
