package java_home.fileio;
import java_home.cipher.CaesarCipher;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

//Чтение файлов порциями (для больших файлов)
//
//Построчная запись результатов
//
//Обработка разных кодировок

public class FileProcessor {

    public static String getResourcesPath() {
        String projectRoot = System.getProperty("user.dir");
        return projectRoot + File.separator + "src" + File.separator + "resources" + File.separator;
    }

    public static String getResourceFilePath(String fileName) {
        return getResourcesPath() + fileName;
    }

    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(getResourceFilePath(filePath)), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(getResourceFilePath(filePath)), StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }

    public static void encryptFile(String inputFile, String outputFile, CaesarCipher cipher, int key) throws IOException {
        if (inputFile == null || inputFile.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя входного файла не может быть пустым");
        }
        if (outputFile == null || outputFile.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя выходного файла не может быть пустым");
        }

        System.out.println("Чтение файла: " + inputFile);
        String content = readFile(inputFile);

        System.out.println("Шифрование...");
        String encryptedContent = cipher.encrypt(content, key);

        System.out.println("Запись результата: " + outputFile);
        writeFile(outputFile, encryptedContent);

        System.out.println("Файл успешно зашифрован!");
    }

    public static void decryptFile(String inputFile, String outputFile, CaesarCipher cipher, int key) throws IOException {
        if (inputFile == null || inputFile.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя входного файла не может быть пустым");
        }
        if (outputFile == null || outputFile.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя выходного файла не может быть пустым");
        }

        System.out.println("Чтение зашифрованного файла: " + inputFile);
        String encryptedContent = readFile(inputFile);

        System.out.println("Расшифровка...");
        String decryptedContent = cipher.decrypt(encryptedContent, key);

        System.out.println("Запись результата: " + outputFile);
        writeFile(outputFile, decryptedContent);

        System.out.println("Файл успешно расшифрован!");
    }

    // Проверка существования файла
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(getResourceFilePath(filePath)));
    }


}
