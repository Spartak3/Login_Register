package service;

import user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class FileService {

    public static void createFile(String path,String file) throws IOException {
        File file1=new File(path);
        if(!file1.exists()){
            file1.mkdirs();
        }
        File file2=new File(path+"/"+file);
        file2.createNewFile();
    }

    public static void writeInFile(String path, String user)throws IOException{
        Files.write(Paths.get(path),user.getBytes(), StandardOpenOption.APPEND);
    }

    public static List<String> readFromFile(String path)throws IOException{
        return Files.readAllLines(Paths.get(path));
    }
}
