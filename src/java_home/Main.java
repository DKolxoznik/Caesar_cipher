package java_home;

import java_home.ui.ConsoleUI;
import java_home.ui.CaesarCipherSwingUI;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Caesar Cipher started!");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите интерфейс:");
        System.out.println("1 - Консольный интерфейс");
        System.out.println("2 - Графический интерфейс (Swing)");
        System.out.print("Ваш выбор: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                ConsoleUI ui = new ConsoleUI();
                ui.StartProgram();
                break;
            case "2":
                // Запускаем Swing интерфейс
                CaesarCipherSwingUI.showGUI();
                break;
            default:
                System.out.println("Неверный выбор. Запускается консольный интерфейс...");
                ConsoleUI UI = new ConsoleUI();
                UI.StartProgram();
        }
    }
}