package java_home.ui;
import java_home.cipher.Alphabet;
import java_home.cipher.CaesarCipher;
import java_home.fileio.FileGenerator;
import java_home.fileio.FileProcessor;

import java.io.IOException;
import java.util.Scanner;

//Меню с выбором режима работы
//
//Запрос путей к файлам и параметров
//
//Вывод результатов и ошибок

public class ConsoleUI {
    private static void fileErrPrint() {
        System.out.println("Ошибка: файл не существует!");
    }

    public static void StartProgram(){
        Alphabet russianAlphabet = new Alphabet("абвгдёжзийклмнопрстуфхцчшщъыьэюя", false);
        Scanner scan = new Scanner(System.in);
        CaesarCipher cipher = new CaesarCipher(russianAlphabet);

        while (true) {
            try {
                System.out.println("""
                    \n=== Шифр Цезаря ===
                    Введите цифру для выбора функционала:
                    1) Создание тестового файла
                    2) Шифрование файла
                    3) Расшифровка файла с ключом
                    4) Проверка существования файла
                    5) Шифрование/расшифровка текста в консоли
                    0) Выход
                    
                    Ввод:""");
                String mainScan = scan.nextLine();

                switch (mainScan) {
                    case "1" -> {
                        // Создание тестовых файлов
                        FileGenerator.createRussianTextFile("test_input.txt");
                        FileGenerator.createLargeTestFile("large_input.txt", 100);
                        System.out.println("Тестовые файлы созданы!");
                    }

                    case "2" -> {
                        // Шифрование файла
                        System.out.print("Введите название файла: ");
                        String inputFile = scan.nextLine();
                        System.out.print("Введите название для зашифрованного файла: ");
                        String outputFile = scan.nextLine();
                        System.out.print("Введите ключ шифрования: ");
                        int key = Integer.parseInt(scan.nextLine());

                        if (!FileProcessor.fileExists(inputFile)) {
                            fileErrPrint();
                            break;
                        }

                        FileProcessor.encryptFile(inputFile, outputFile, cipher, key);
                    }

                    case "3" -> {
                        // Расшифровка файла
                        System.out.print("Введите название зашифрованного файла: ");
                        String inputFile = scan.nextLine();
                        System.out.print("Введите название для расшифрованного файла: ");
                        String outputFile = scan.nextLine();
                        System.out.print("Введите ключ шифрования: ");
                        int key = Integer.parseInt(scan.nextLine());

                        if (!FileProcessor.fileExists(inputFile)) {
                            fileErrPrint();
                            break;
                        }

                        FileProcessor.decryptFile(inputFile, outputFile, cipher, key);
                    }
                    case "4" -> {
                        // Проверка на существования файла
                        System.out.print("Введите название файла: ");
                        String inputFile = scan.nextLine();
                        if (!FileProcessor.fileExists(inputFile)) {
                            fileErrPrint();
                        } else {
                            System.out.println("Файл существует!");
                        }
                    }
                    case "5" -> {
                        // Работа с текстом в консоли (ваш существующий код)
                        do {
                            System.out.println("Введите текст для шифрования:");
                            String original = scan.nextLine();

                            System.out.println("Введите ключ:");
                            int key = Integer.parseInt(scan.nextLine());

                            String encrypted = cipher.encrypt(original, key);
                            System.out.println("Зашифровано: " + encrypted);

                            String decrypted = cipher.decrypt(encrypted, key);
                            System.out.println("Расшифровано: " + decrypted);

                            System.out.println("\nДля выхода введите 'exit', для продолжения - любую другую строку");
                        } while (!"exit".equals(scan.nextLine()));
                    }

                    case "0" -> {
                        System.out.println("Выход из программы...");
                        return;
                    }

                    default -> System.out.println("Неверный выбор! Попробуйте снова.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число для ключа!");
            } catch (IOException e) {
                System.out.println("Ошибка ввода-вывода: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
                System.out.println("Попробуйте снова");
            }
        }
    }
}
