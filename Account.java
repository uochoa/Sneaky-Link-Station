package com.Account;
import java.io.*;
import java.util.Scanner;
import java.util.HashMap;

import static java.lang.Character.getName;


public class Account {

    static HashMap<String, String> accounts = new HashMap<>();
    static String file = "C:\\Users\\saras\\IdeaProjects\\Hotel\\src\\all_logins";


    public static void main(String[] args) throws IOException {
        info();
    }
    public static void info() throws IOException {
        //HashMap<String, String> accounts = new HashMap<>();
        Scanner input = new Scanner(System.in);
        System.out.println("I am happy to assist, do you currently have an account with us(Y or N)?");
        String choice = input.nextLine().trim().toLowerCase();

        if (choice.equals("n")) {
            System.out.println("Welcome, newcomer! Lets create your account, please enter a valid username for your account!");
            String user = input.nextLine();
            System.out.println("Please create a password: ");
            String password = input.nextLine();
            accounts.put(user, password);
            try {
                BufferedWriter login = new BufferedWriter(new FileWriter(file));
                login.write(String.valueOf(user + " " + password + "\n"));
                login.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            System.out.println("Account created successfully, please log in!");
            login();

        }else if (choice.equals("y")) {
            login();
        }
    }

    //case "s" -> System.out.println("Okay have a wonderful day!");
            //default -> {
              //  System.out.println("Your request was invalid, try again");
               // info();
    protected static void login() throws IOException{
        System.out.println("Welcome back! Can you enter your username please!");
        Scanner input = new Scanner(System.in);
        String user = input.nextLine();
        System.out.println("PLease enter your password: ");
        String password = input.nextLine();
        //HashMap<String, String> accounts = new HashMap<>();
        //try{
          //  BufferedReader readLogin = new BufferedReader(new FileReader(file));
          //  while((readLogin.readLine()) != null) {
               // while(!(accounts.containsKey(user) && accounts.containsValue(password))) {
                  //  readLogin.readLine();
                   // readLogin.readLine();
             //   }
               // System.out.println("Account successfully logged in!");
              //  readLogin.close();
           // }
          //  System.out.println("Sorry, account does not exist. Please try again.");
         //   login();
           // readLogin.close();
      //  }catch(IOException e){
          //  e.printStackTrace();
      //  }

    }

    public <charSequence> Writing append(charSequence csq) throws IOException{

    }


}



