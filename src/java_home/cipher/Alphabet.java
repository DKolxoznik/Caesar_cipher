package java_home.cipher;

//Хранение алфавита (русский, английский, или оба)
//
//Методы для определения принадлежности символа к алфавиту
//
//Вычисление сдвига с учётом размера алфавита

public class Alphabet {
    private final String characters;
    private final int size;
    public final boolean caseSensitive;

    public static final String RUSSIAN_LOWER = "абвгдёжзийклмнопрстуфхцчшщъыьэюя";
    public static final String RUSSIAN_UPPER = "АБВГДЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static final String ENGLISH_LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String ENGLISH_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public Alphabet(String characters, boolean caseSensitive) {
        this.characters = characters;
        this.size = characters.length();
        this.caseSensitive = caseSensitive;
    }

    public Alphabet(String characters) {
        this(characters, true);
    }


    public boolean contains(char character) {
        if (!caseSensitive) {
            char lowerChar = Character.toLowerCase(character);
            return characters.indexOf(lowerChar) != -1;
        }
        return characters.indexOf(character) != -1;
    }

    public int getIndex(char character) {
        if (!caseSensitive) {
            char lowerChar = Character.toLowerCase(character);
            return characters.indexOf(lowerChar);
        }
        return characters.indexOf(character);
    }

    public char getChar(int index) {
        int normalizedIndex = Math.floorMod(index, size);
        return characters.charAt(normalizedIndex);
    }

    public char shift(char character, int shift) {
        if (!contains(character)) return character; // нешифруемые символы

        int originalIndex = getIndex(character);
        int newIndex = (originalIndex + shift) % size;
        if (newIndex < 0) newIndex += size; // для отрицательных сдвигов

        return getChar(newIndex);
    }


    public String getCharacters() {return this.characters; }
    public int getSize() { return this.size; }
    public boolean isCaseSensitive() { return this.caseSensitive; }
    public boolean isAlphabetic(char character) { return contains(character); }

    public boolean isValidKey(int key) { return key >= 0 && key < size; }
    public int normalizeKey(int key) { return Math.floorMod(key, size); }
}
