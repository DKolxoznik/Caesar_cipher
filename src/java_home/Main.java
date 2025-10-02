import java_home.cipher.Alphabet;
import java_home.cipher.CaesarCipher;
import java.util.Scanner;

void main() {
    Alphabet russianAlphabet = new Alphabet("абвгдёжзийклмнопрстуфхцчшщъыьэюя", false);
    Alphabet englishAlphabet = new Alphabet("abcdefghijklmnopqrstuvwxyz", false);
    Scanner scan = new Scanner(System.in);
    CaesarCipher cipher = new CaesarCipher(englishAlphabet);
while (true) {
    try {
        IO.println("Введи выражение");
        String original = scan.nextLine();

        IO.println("Введи ключ");
        int key = scan.nextInt();
        scan.nextLine(); // Очистка

        String encrypted = cipher.encrypt(original, key); // Шифровка (с ключом)
        IO.println("Зашифровано: " + encrypted);

        String decrypted = cipher.decrypt(encrypted, key); // Расшифровка (с ключом)
        IO.println("Расшифровано: " + decrypted);

        IO.println("\nДля выхода введите 'exit', для продолжения - любую другую строку");
        if ("exit".equals(scan.nextLine())) {
            break;
        }

    } catch (java.util.InputMismatchException e) {
        IO.println("Ошибка: введите корректное число для ключа!");
        scan.nextLine(); // Очистка

    } catch (Exception e) {
        IO.println("Произошла ошибка: " + e.getMessage());
        IO.println("Попробуйте снова\nd");
    }
}


}
