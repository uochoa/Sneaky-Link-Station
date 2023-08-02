package com;
import java.io.IOException;
import java.util.Scanner;

public class Order {
    int standard = 100;
    int deluxe = 150;
    int suite = 200;
    public static void main(String[] args) throws IOException {
        int room;
        int day;
        System.out.println("We have several rooms available:\n (1)Standard $100/night\n " + "(2)Deluxe $150/night\n" +
                " (3)Suite $200/night\n Please enter the number you are interested in!");
        Scanner input = new Scanner(System.in);
        room = input.nextInt();
        System.out.println("Awesome, how many days would this be for?");
        day = input.nextInt();
        rooms(room, day);

    }

    public static void rooms(int room,int day){
        int standard = 100;
        int deluxe = 150;
        int suite = 200;
        String taxList = " This is including an 18% tax.";
        if(room == 1){
            int total = (standard*day);
            double newTotal = (total*.18)+total;
            closer(day, taxList, newTotal);


        }else if(room == 2){
            int total = (deluxe*day);
            double newTotal = (total*.18)+total;
            System.out.println("You have selected the Deluxe Room,for " + day + " day(s)!\n" +
                    "Your total would come out too $" + newTotal + taxList);
            closer(day, taxList, newTotal);

        }else if(room == 3){
            int total = (suite*day);
            double newTotal = (total*.18)+total;
            System.out.println("You have selected the Suite Room,for " + day + " day(s)!\n" +
                    "Your total would come out too $" + newTotal + taxList);
            closer(day,taxList, newTotal);

        }else{
            System.out.println("Sorry we did not quite get that, please try again");
            data();
        }
    }

    private static void closer(int day, String taxList, double newTotal) {
        System.out.println("You have selected the Standard Room, for " + day + " day(s)!\n" +
                "What date would you like to check in (**M/**D/****YEAR)?");
        Scanner input = new Scanner(System.in);
        String date = input.nextLine();
        System.out.println("What time would you like to check in (00:00 pm/am)?");
        String time = input.nextLine();
        System.out.println("Would you like to pay cash or card, credit or debit?");
        String payment = input.nextLine().trim().toLowerCase();
        System.out.println("We have confirmed your payment type to be " + payment +
                "\nThank you so much for booking with Paradise Hotel!\n" +
                "Your total comes out too $" + newTotal + taxList + "\nSee you on " +
                date + " at " + time + "!");
    }

    public static void data() {
        System.out.println("We have several rooms available:\n (1)Standard $100/night\n " + "(2)Deluxe $150/night\n" +
                "(3)Suite $200/night\n (4)STOP\n Please enter ONLY the number you are interested in!");
        Scanner input = new Scanner(System.in);
        int room = input.nextInt();
        if(room == 1||room == 2||room == 3) {
            System.out.println("Awesome, how many days would this be for?");
            int day = input.nextInt();
        }else if(room == 4){
            System.out.println("Okay, have a wonderful day!");
        }else{
            System.out.println("Invalid response please try again");
            data();
        }
    }

}
