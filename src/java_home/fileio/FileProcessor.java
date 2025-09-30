package java_home.fileio;
import java.io.*;
import java.util.ArrayList;

//Чтение файлов порциями (для больших файлов)
//
//Построчная запись результатов
//
//Обработка разных кодировок

public class FileProcessor {
    public void Except(String[] File){
        try {
            FileReader reader = new FileReader("notes3.txt");
            int c;
            ArrayList<String> FileString = new ArrayList<>();
            while((c=reader.read())!=-1){

                System.out.print((char)c);
            }
        } catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }


}
