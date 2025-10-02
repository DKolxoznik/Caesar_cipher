import java_home.cipher.Alphabet;
import java_home.cipher.CaesarCipher;
import java_home.fileio.FileGenerator;
import java_home.fileio.FileProcessor;

import java.util.Scanner;

void main() {
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
                    FileGenerator.createLargeTestFile("large_input.txt", 1000);
                    System.out.println("Тестовые файлы созданы!");
                }

                case "2" -> {
                    // Шифрование файла
                    System.out.print("Введите путь к исходному файлу: ");
                    String inputFile = scan.nextLine();
                    System.out.print("Введите путь для зашифрованного файла: ");
                    String outputFile = scan.nextLine();
                    System.out.print("Введите ключ шифрования: ");
                    int key = Integer.parseInt(scan.nextLine());

                    if (!FileProcessor.fileExists(inputFile)) {
                        System.out.println("Ошибка: файл не существует!");
                        break;
                    }

                    FileProcessor.encryptFile(inputFile, outputFile, cipher, key);
                }

                case "3" -> {
                    // Расшифровка файла
                    System.out.print("Введите путь к зашифрованному файлу: ");
                    String inputFile = scan.nextLine();
                    System.out.print("Введите путь для расшифрованного файла: ");
                    String outputFile = scan.nextLine();
                    System.out.print("Введите ключ шифрования: ");
                    int key = Integer.parseInt(scan.nextLine());

                    if (!FileProcessor.fileExists(inputFile)) {
                        System.out.println("Ошибка: файл не существует!");
                        break;
                    }

                    FileProcessor.decryptFile(inputFile, outputFile, cipher, key);
                }
                case "4" -> {
                    // Проверка на существования файла
                    System.out.print("Введите путь к файлу: ");
                    String inputFile = scan.nextLine();
                    if (!FileProcessor.fileExists(inputFile)) {
                        System.out.println("Ошибка: файл не существует!");
                        break;
                    } else {
                        System.out.println("Файл существует!");
                    }
                }
                case "5" -> {
                    // Работа с текстом в консоли (ваш существующий код)
                    while(true){
                        System.out.println("Введите текст для шифрования:");
                        String original = scan.nextLine();

                        System.out.println("Введите ключ:");
                        int key = Integer.parseInt(scan.nextLine());

                        String encrypted = cipher.encrypt(original, key);
                        System.out.println("Зашифровано: " + encrypted);

                        String decrypted = cipher.decrypt(encrypted, key);
                        System.out.println("Расшифровано: " + decrypted);

                        System.out.println("\nДля выхода введите 'exit', для продолжения - любую другую строку");
                        if ("exit".equals(scan.nextLine())) {
                            break;
                        }
                    }
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