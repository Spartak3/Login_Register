package main;

import md5.Md5;
import service.FileService;
import user.User;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static Set<String> usernameSet=new HashSet<>();
    static String fullName, username, email, password;
    static Scanner scanner=new Scanner(System.in);
    static Scanner scanner2=new Scanner(System.in);
    static int currNum=0;
    static User currentUser;

    public static void main(String[] args) {
        boolean isActive=true;
        while (isActive){
            System.out.println("Do you want to register a user or login?");
            System.out.println("1-Register");
            System.out.println("2-Login");
            System.out.println("3- exit");
            String s="dkm";
            currNum=scanner.nextInt();
            //register
            switch (currNum){
                case 1:{
                    registerUser();
                    break;
                }
                case 2:{
                    loginUser();
                    break;
                }
                case 3:{
                    isActive=false;
                    System.out.println("Bye");
                    break;
                }
                default:{
                    System.out.println("Please select one of thous numbers");
                }
            }


        }
    }



    public static void registerUser(){
        System.out.println("Registering a user");
        System.out.println("Type full Name(name surname)--");
        fullName = scanner2.nextLine();
//        while (true) {
//            System.out.println("Type full Name(name surname)--");
//
//            //scanner.close();
//            if(fullName.matches("^[a-zA-Z]+([a-zA-Z]+)*$")){
//                break;
//            }
//            else{
//                System.out.println("Invalid full name, type again");
//            }
//        }
        while (true){
            System.out.println("Type username(at least 10 characters)--");
            //scanner.next();
            username = scanner2.next();
            if(username.length()>10&&!usernameSet.contains(username)){
                usernameSet.add(username);
                break;
            }
            else{
                System.out.println("Invalid username, please type again");
            }
        }
        while (true){
            System.out.println("Type email");
            email = scanner2.next();
            if(email.matches("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$")){
                break;
            }
            else {
                System.out.println("Invalid email, please type again");
            }
        }
        while (true){
            System.out.println("Type password(at least 8 character, 2 uppercase, 3 number)");
            password=scanner2.next();
            if(password.length() > 8 && checkPasswordValidation(password)){
                password= Md5.getMd5(password);
                break;
            }
            else{
                System.out.println("Invalid password, please type again");
            }
        }
        //create user to write in file
        //can also do by connecting strings by StringBuilder
        currentUser=new User(fullName,username,email,password);
        try {
            FileService.createFile("users","database.rtf");
            FileService.writeInFile("users/database.rtf",currentUser.toString());
            System.out.println("User is saved in database.rtf ");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean loginUser(){
        System.out.println("Please type username and password");
        System.out.println("Username----");
        username=scanner2.next();
        System.out.println("Password----");
        password=scanner2.next();
        password=Md5.getMd5(password);
        try {
            List<String> allUsers=FileService.readFromFile("users/database.rtf");
            for (String str : allUsers) {
                if (str.contains(username) && str.contains(password)) {
                    System.out.println("Login successful--------");
                    return true;
                }
            }
            System.out.println("Wrong username or password---------");
            return false;
        } catch (IOException e) {
            System.out.println("Something wrong with file");
            e.printStackTrace();
        }
        return false;
    }


    public static boolean checkPasswordValidation(String password) {
        int upperCaseCount=0,numberCounty=0;
        for (int i = 0; i < password.length(); i++) {
            if(Character.isUpperCase(password.charAt(i))){
                upperCaseCount++;
            }
            else if(Character.isDigit(password.charAt(i))){
                numberCounty++;
            }
        }

        return (upperCaseCount>=2 && numberCounty>=3);
    }



}
