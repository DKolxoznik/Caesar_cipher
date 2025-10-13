package java_home.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java_home.cipher.Alphabet;
import java_home.cipher.CaesarCipher;
import java_home.fileio.FileProcessor;
import java.io.IOException;

public class CaesarCipherSwingUI extends JFrame {
    private CaesarCipher cipher;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JTextField keyField;
    private JTextField inputFileField;
    private JTextField outputFileField;
    private JTextField fileKeyField;
    private JTextArea fileInfoArea;

    public CaesarCipherSwingUI() {
        Alphabet russianAlphabet = new Alphabet("абвгдёжзийклмнопрстуфхцчшщъыьэюя", false);
        cipher = new CaesarCipher(russianAlphabet);

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Шифр Цезаря - Графический интерфейс");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // Создаем вкладки
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Текст", createTextTab());
        tabbedPane.addTab("Файлы", createFileTab());

        add(tabbedPane);
    }

    private JPanel createTextTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Верхняя панель - ввод текста
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Исходный текст:"), BorderLayout.NORTH);

        inputTextArea = new JTextArea(8, 50);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        JScrollPane inputScroll = new JScrollPane(inputTextArea);
        topPanel.add(inputScroll, BorderLayout.CENTER);

        // Центральная панель - ключ и кнопки
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Панель ключа
        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        keyPanel.add(new JLabel("Ключ:"));
        keyField = new JTextField(10);
        keyPanel.add(keyField);
        centerPanel.add(keyPanel);

        // Панель кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton encryptBtn = new JButton("Зашифровать");
        JButton decryptBtn = new JButton("Расшифровать");
        JButton clearBtn = new JButton("Очистить");

        buttonPanel.add(encryptBtn);
        buttonPanel.add(decryptBtn);
        buttonPanel.add(clearBtn);
        centerPanel.add(buttonPanel);

        // Нижняя панель - результат
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(new JLabel("Результат:"), BorderLayout.NORTH);

        outputTextArea = new JTextArea(8, 50);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputTextArea);
        bottomPanel.add(outputScroll, BorderLayout.CENTER);

        // Обработчики событий
        encryptBtn.addActionListener(e -> processText(true));
        decryptBtn.addActionListener(e -> processText(false));
        clearBtn.addActionListener(e -> {
            inputTextArea.setText("");
            outputTextArea.setText("");
            keyField.setText("");
        });

        // Компоновка основной панели
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFileTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Верхняя панель - форма ввода
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Параметры файлов"));

        // Поля для файлов и ключа
        formPanel.add(createLabeledField("Входной файл:", inputFileField = new JTextField(30)));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(createLabeledField("Выходной файл:", outputFileField = new JTextField(30)));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(createLabeledField("Ключ:", fileKeyField = new JTextField(10)));

        // Центральная панель - кнопки
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Действия"));

        JButton encryptFileBtn = new JButton("Зашифровать файл");
        JButton decryptFileBtn = new JButton("Расшифровать файл");
        JButton checkFileBtn = new JButton("Проверить файл");
        JButton createTestBtn = new JButton("Создать тестовый файл");

        buttonPanel.add(encryptFileBtn);
        buttonPanel.add(decryptFileBtn);
        buttonPanel.add(checkFileBtn);
        buttonPanel.add(createTestBtn);

        // Нижняя панель - информационная область
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Информация"));

        fileInfoArea = new JTextArea(12, 50);
        fileInfoArea.setEditable(false);
        JScrollPane infoScroll = new JScrollPane(fileInfoArea);
        infoPanel.add(infoScroll, BorderLayout.CENTER);

        // Обработчики событий
        encryptFileBtn.addActionListener(e -> processFile(true));
        decryptFileBtn.addActionListener(e -> processFile(false));

        checkFileBtn.addActionListener(e -> {
            String fileName = inputFileField.getText().trim();
            if (fileName.isEmpty()) {
                fileInfoArea.append("✗ Ошибка: введите название файла!\n");
                return;
            }

            if (FileProcessor.fileExists(fileName)) {
                fileInfoArea.append("✓ Файл '" + fileName + "' существует!\n");
            } else {
                fileInfoArea.append("✗ Файл '" + fileName + "' не существует!\n");
            }
        });

        createTestBtn.addActionListener(e -> {
            try {
                java_home.fileio.FileGenerator.createRussianTextFile("test_input.txt");
                java_home.fileio.FileGenerator.createLargeTestFile("large_input.txt", 50);
                fileInfoArea.append("✓ Тестовые файлы созданы!\n");
            } catch (Exception ex) {
                fileInfoArea.append("✗ Ошибка создания файлов: " + ex.getMessage() + "\n");
            }
        });

        // Компоновка основной панели
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createLabeledField(String label, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(field);
        return panel;
    }

    private void processText(boolean encrypt) {
        try {
            String text = inputTextArea.getText();
            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Введите текст для обработки", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String keyText = keyField.getText().trim();
            if (keyText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Введите ключ", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int key = Integer.parseInt(keyText);
            String result;

            if (encrypt) {
                result = cipher.encrypt(text, key);
            } else {
                result = cipher.decrypt(text, key);
            }

            outputTextArea.setText(result);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Введите корректный ключ (целое число)", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Произошла ошибка: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void processFile(boolean encrypt) {
        try {
            String inputFile = inputFileField.getText().trim();
            String outputFile = outputFileField.getText().trim();
            String keyText = fileKeyField.getText().trim();

            // Проверка ввода
            if (inputFile.isEmpty()) {
                fileInfoArea.append("✗ Ошибка: введите название входного файла!\n");
                return;
            }
            if (outputFile.isEmpty()) {
                fileInfoArea.append("✗ Ошибка: введите название выходного файла!\n");
                return;
            }
            if (keyText.isEmpty()) {
                fileInfoArea.append("✗ Ошибка: введите ключ!\n");
                return;
            }

            int key = Integer.parseInt(keyText);

            if (!FileProcessor.fileExists(inputFile)) {
                fileInfoArea.append("✗ Файл '" + inputFile + "' не существует!\n");
                return;
            }

            if (encrypt) {
                FileProcessor.encryptFile(inputFile, outputFile, cipher, key);
                fileInfoArea.append("✓ Файл '" + inputFile + "' зашифрован в '" + outputFile + "'\n");
            } else {
                FileProcessor.decryptFile(inputFile, outputFile, cipher, key);
                fileInfoArea.append("✓ Файл '" + inputFile + "' расшифрован в '" + outputFile + "'\n");
            }

        } catch (NumberFormatException e) {
            fileInfoArea.append("✗ Ошибка: введите корректный ключ (целое число)\n");
        } catch (IOException e) {
            fileInfoArea.append("✗ Ошибка ввода-вывода: " + e.getMessage() + "\n");
        } catch (Exception e) {
            fileInfoArea.append("✗ Ошибка: " + e.getMessage() + "\n");
        }
    }

    public static void showGUI() {
        SwingUtilities.invokeLater(() -> {
            try {
                // Устанавливаем системный look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new CaesarCipherSwingUI().setVisible(true);
        });
    }
}