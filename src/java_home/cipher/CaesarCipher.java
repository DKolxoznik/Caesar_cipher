package java_home.cipher;

//Шифрование: принимает текст и ключ, возвращает зашифрованный текст
//
//Дешифрование: принимает зашифрованный текст и ключ, возвращает исходный текст
//
//Важно: продумать обработку разных символов (только буквы? все символы? регистр?)

public class CaesarCipher {
    private final Alphabet alphabet;

    public CaesarCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    private char encryptChar(char character, int key) {
        // Просто делегируем алфавиту
        return alphabet.shift(character, key);
    }

    private char decryptChar(char character, int key) {
        // Дешифровка = сдвиг в обратную сторону
        return alphabet.shift(character, -key);
    }

    public String encrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            result.append(encryptChar(character, key));
        }
        return result.toString();
    }

    public String decrypt(String text, int key) {
        // Можно просто вызвать encrypt с отрицательным ключом
        return encrypt(text, -key);
    }
}
