import java_home.cipher.Alphabet;
import java_home.cipher.CaesarCipher;
import java.util.Scanner;

import static java_home.cipher.Alphabet.RUSSIAN_LOWER;

void main() {
    Alphabet russianAlphabet = new Alphabet("абвгдёжзийклмнопрстуфхцчшщъыьэюя", false);
    Alphabet englishAlphabet = new Alphabet("abcdefghijklmnopqrstuvwxyz", false);
    Scanner scan = new Scanner(System.in);
    CaesarCipher cipher = new CaesarCipher(englishAlphabet);

    IO.println("Введи выражение");
    String original = scan.nextLine();
    String encrypted = cipher.encrypt(original, 3); // "тулезх рлу"
    IO.println(encrypted);
    String decrypted = cipher.decrypt(encrypted, 3); // "привет мир"
    IO.println(decrypted);

}
