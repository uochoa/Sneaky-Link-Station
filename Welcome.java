package com.Welcome;
import com.Account.Account;

import java.io.IOException;
import java.util.Scanner;

public class Welcome {
    public static void main(String[] args) throws IOException {
        welcome();
        Account.info();
    }

    public static void welcome() throws IOException {
        System.out.println("Hi there, thank you for considering Paradise Hotel, " +
                "I am a virtual text helper for the company!" +
                " Would you like to learn about the hotel? " +
                "Please enter Y or N");
        Scanner input = new Scanner(System.in);
        String choice = input.nextLine().trim().toLowerCase();

        if (choice.equals("y")) {
            System.out.println("Awesome! We are located in the capital of Hawaii, Honolulu, right next to Waikiki Beach.\n" +
                    "We have a gym that is 24 hours open, along with an indoor basketball court and track field.\n" +
                    "We also have a Sauna and Steam room available for all hotel guests.\n" +
                    "We have a children's day care for families that are interested, along with an onsite nurse for everyone's safety.\n" +
                    "We offer a game room for all ages and Saturday Night Poker for 21+.\n" +
                    "Sunday morning and evening group Yoga by the Waikiki Beach.\n" +
                    "For more information on what we offer, you can visit our website!\n");
            choose();
            String ans = input.nextLine().trim().toLowerCase();
            switch (ans) {
                case "book":
                    Book();
                    break;
                case "cancel":
                    System.out.println("I'm sorry to hear you want to cancel your reservation. " +
                            "Lets have you log into your account. " +
                            "What is your email?");
                    break;
                case "s":
                    System.out.println("Have a wonderful day!");

                    break;
                default:
                    System.out.println("Please try again");
                    welcome();
                    break;
            }
        } else if (choice.equals("n")) {
            System.out.println("All right");
            choose();


        } else {
            System.out.println("I'm sorry, I did not quite get your request. Good bye!");
        }
    }

    public static void Book() {
        //
    }


    public static void choose() throws IOException {
        System.out.println("Would you like to book or cancel a room? If at any point you would like to stop, please type s.");
        Scanner a = new Scanner(System.in);
        String an = a.nextLine().trim().toLowerCase();
        switch (an) {
            case "book" -> Account.info();
            case "cancel" -> System.out.println("I'm sorry to hear you want to cancel your reservation." +
                    " Lets have you first log in. What is your email?");
            case "s" -> System.out.println("Okay, have a wonderful day!");
            default -> {
                System.out.println("Please try again");
                choose();
            }
        }
    }

}
