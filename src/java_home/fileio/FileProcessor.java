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

    // Получение пути к папке resources
    public static String getResourcesPath() {
        // Для IDE типа IntelliJ IDEA/Eclipse
        String projectRoot = System.getProperty("user.dir");
        return projectRoot + File.separator + "src" + File.separator + "resources" + File.separator;
    }

    // Создание полного пути к файлу в resources
    public static String getResourceFilePath(String fileName) {
        return getResourcesPath() + fileName;
    }

    // Чтение файла с указанной кодировкой
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

    // Запись в файл с указанной кодировкой
    public static void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(getResourceFilePath(filePath)), StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }

    // Шифрование файла
    public static void encryptFile(String inputFile, String outputFile, CaesarCipher cipher, int key) throws IOException {
        System.out.println("Чтение файла: " + inputFile);
        String content = readFile(inputFile);

        System.out.println("Шифрование...");
        String encryptedContent = cipher.encrypt(content, key);

        System.out.println("Запись результата: " + outputFile);
        writeFile(outputFile, encryptedContent);

        System.out.println("Файл успешно зашифрован!");
    }

    // Расшифровка файла с известным ключом
    public static void decryptFile(String inputFile, String outputFile, CaesarCipher cipher, int key) throws IOException {
        System.out.println("Чтение зашифрованного файла: " + inputFile);
        String encryptedContent = readFile(inputFile);

        System.out.println("Расшифровка...");
        String decryptedContent = cipher.decrypt(encryptedContent, key);

        System.out.println("Запись результата: " + outputFile);
        writeFile(outputFile, decryptedContent);

        System.out.println("Файл успешно расшифрован!");
    }

    // Обработка больших файлов (построчно)
    public static void processLargeFile(String inputFile, String outputFile, CaesarCipher cipher, int key, boolean encrypt) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(getResourceFilePath(inputFile)), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream(getResourceFilePath(outputFile)), StandardCharsets.UTF_8))) {

            String line;
            int lineCount = 0;

            while ((line = reader.readLine()) != null) {
                String processedLine = encrypt ?
                        cipher.encrypt(line, key) :
                        cipher.decrypt(line, key);

                writer.write(processedLine);
                writer.newLine();

                lineCount++;
                if (lineCount % 100 == 0) {
                    System.out.println("Обработано строк: " + lineCount);
                }
            }
            System.out.println("Всего обработано строк: " + lineCount);
        }
    }

    // Проверка существования файла
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(getResourceFilePath(filePath)));
    }


}
