import java.io.*;
import java.util.*;

public class Account {
    static HashMap<String, String> accounts = new HashMap<>();
    static String fileName = "all_logins.txt";
    static File info = new File(fileName);
    static BufferedWriter bf = null;
    

    static Map<String, Reservation> reservationsMap = new HashMap<>();
    static List<Reservation> reservations = new ArrayList<>();
    static String reservationFile = "reservations.txt";
    
    public static void main(String[] args) throws IOException {
        readReservationsFromFile();
        info();
    }

     public static void info() throws IOException 
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Our Hotel!");
        System.out.println("Do you have an account with us (Yes or No)?");
        
        int valid_input = 0;
        do{
            String choice = input.nextLine().trim().toLowerCase();
        if (choice.equals("n") || choice.equals("no")) 
        {
            createAccount();
            valid_input = 1;
        } 
        else if (choice.equals("y") || choice.equals("yes")) 
        {
            login();
            valid_input = 1;
        }
        else 
        {
            System.out.println("Invalid input! Please try again");
            
        }
    }while(valid_input == 0);
    }

    public static void createAccount() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome, newcomer! Let's create your account.");
        System.out.println("Please enter a valid username for your account:");
        String user = input.nextLine();
        System.out.println("Please create a password:");
        String password = input.nextLine();
        accounts.put(user, password);
        try {
            bf = new BufferedWriter(new FileWriter(info, true));
            bf.append(user).append(":").append(password);
            bf.newLine();
            System.out.println("Account successfully created!");
            bf.flush();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try {
                bf.close();
            } 
            catch (Exception ignored) 
            {
            }
        }
        showMainMenu(user);
    }

    protected static void login() throws IOException 
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your username:");
        String user = input.nextLine();
        System.out.println("Please enter your password:");
        String password = input.nextLine();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(info))) 
        {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(user) && parts[1].equals(password)) 
                {
                    System.out.println("Hello, " + user + "!");
                    showMainMenu(user); // Show the main menu after successful login
                    found = true;
                    break;
                }
            }
            if (!found) 
            {
                System.out.println("Sorry, invalid username or password. Please enter a valid user name. ");
                login();
            }
        }
    }

    public static void showMainMenu(String username) throws IOException 
    {

        Scanner input = new Scanner(System.in);
        System.out.println("\nWelcome, " + username + "!");
        System.out.println("Please select an option:");
        System.out.println("1. Make a Reservation");
        System.out.println("2. View Reservations");
        System.out.println("3. Cancel Reservation");
        System.out.println("4. Exit");
        int choice = input.nextInt();

        switch (choice) 
        {
            case 1:
                Order.opener();
                break;
            case 2:
                viewReservations();
                break;
            case 3:
                input.nextLine(); // Consume the newline character
                System.out.println("Enter the phone number associated with the reservation you want to cancel:");
                String phoneNumber = input.nextLine();
                cancelReservation(phoneNumber);
                break;
            
            case 4:
                System.out.println("Exiting. Have a nice day!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please choose again.");
                showMainMenu(username);
                break;
        }
    }

    public static void viewReservations() 
    {
        System.out.println("Viewing Reservations:");
        try (BufferedReader reader = new BufferedReader(new FileReader(reservationFile))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                System.out.println(line);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void cancelReservation(String phoneNumber) 
    {
        phoneNumber = phoneNumber.replaceAll("\\s+", ""); // Remove spaces from the input
    
        System.out.println("Searching for reservation with phone number: " + phoneNumber);
    
        boolean found = false;
        List<String> updatedReservations = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(reservationFile))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String reservationPhoneNumber = extractPhoneNumber(line);
                String cleanedReservationPhoneNumber = reservationPhoneNumber.replaceAll("[^\\d]", ""); // Clean the extracted phone number
                String firstTenDigits = cleanedReservationPhoneNumber.substring(0, Math.min(cleanedReservationPhoneNumber.length(), 10)); // Extract first 10 digits
    
                if (firstTenDigits.equals(phoneNumber)) 
                {
                    found = true;
                    System.out.println("Found matching reservation. Removing...");
                } 
                else 
                {
                    updatedReservations.add(line); // Add non-matching reservations to the updated list
                }
                
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    
        if (found) 
        {
            // Write the updated reservations back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reservationFile))) 
            {
                for (String reservation : updatedReservations) 
                {
                    writer.write(reservation);
                    writer.write("\n");
                }
                System.out.println("Reservation canceled successfully.");
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        } 
        else 
        {
            System.out.println("No reservation found with the provided phone number.");
            found = false;
        }
        
    }
    
    
    
    
    public static void addReservation(String name, String number, String email, String date, String time, String payment, double total) {
        reservations.add(new Reservation(name, number, email, date, time, payment, total)); // Updated method call
        writeReservationsToFile();
    }
    
    
    
    public static void writeReservationsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reservationFile, true))) {
            for (Reservation reservation : reservations) {
                writer.write(reservation.toString());
                writer.write("\n"); // Add a newline character after each reservation
            }
            System.out.println("Reservations written to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void readReservationsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(reservationFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String phoneNumber = extractPhoneNumber(line); // Extract phone number
                Reservation reservation = Reservation.createReservationFromLine(line); // Use Reservation.createReservationFromLine
                reservationsMap.put(phoneNumber, reservation); // Store reservation in map
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    private static String extractPhoneNumber(String line) {
        String phoneNumber = line.substring(line.indexOf("Phone Number: ") + 14);
        phoneNumber = phoneNumber.replaceAll("[^\\d]", ""); // Remove non-digit characters
        return phoneNumber;
    }
    
    
    

    private static class Reservation {
        private String name;
        private String number;
        private String email;
        private String date;
        private String time;
        private String payment;
        private double total;
        
        
        public Reservation(String name, String number, String email, String date, String time, String payment, double total) {
            this.name = name;
            this.number = number;
            this.email = email;
            this.date = date;
            this.payment = payment;
            this.total = total;
        }
        
        public static Reservation fromString(String line) {
            String[] parts = line.split(", ");
            if (parts.length == 7) {
                String name = parts[0].substring(6);
                String phoneNumber = extractPhoneNumber(parts[1]);
                String email = parts[2].substring(7);
                String date = parts[3].substring(13);
                String time = parts[4].substring(3);
                String payment = parts[5].substring(14);
                double total = Double.parseDouble(parts[6].substring(8));
        
                return new Reservation(name, phoneNumber, email, date, time, payment, total);
            }
            return null; // Return null if parsing fails
        }
        public static Reservation createReservationFromLine(String line) {
            // Parse the line to extract reservation details
            String[] parts = line.split(", ");
            if (parts.length == 7) {
                String name = parts[0].substring(6);
                String phoneNumber = extractPhoneNumber(parts[1]);
                String email = parts[2].substring(7);
                String date = parts[3].substring(13);
                String time = parts[4].substring(3);
                String payment = parts[5].substring(14);
                double total = Double.parseDouble(parts[6].substring(8));
        
                return new Reservation(name, phoneNumber, email, date, time, payment, total);
            }
            return null; // Return null if parts length is not 7 or parsing fails
        }
        
        
        @Override
        public String toString() {
            return "Name: " + name + ", Phone Number: " + number + ", Email: " + email +
                    ", Check-in: " + date + " at " + time + ", Payment Type: " + payment + ", Total: $" + total;
        }

        
    }
}








/* import java.io.*;
import java.util.*;

public class Account 
{
    static HashMap<String, String> accounts = new HashMap<>();
    static String fileName = "all_logins.txt";
    static File info = new File(fileName);
    static BufferedWriter bf = null;

    static List<Reservation> reservations = new ArrayList<>();
    static String reservationFile = "reservations.txt";

    public static void main(String[] args) throws IOException 
    {
        // readReservationsFromFile();
        info();
    }

    public static void info() throws IOException 
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Our Hotel!");
        System.out.println("Do you have an account with us (Yes or No)?");

        int valid_input = 0;
        do{
            String choice = input.nextLine().trim().toLowerCase();
        if (choice.equals("n") || choice.equals("no")) 
        {
            createAccount();
            valid_input = 1;
        } 
        else if (choice.equals("y") || choice.equals("yes")) 
        {
            login();
            valid_input = 1;
        }
        else 
        {
            System.out.println("Invalid input! Please try again");
            
        }
    }while(valid_input == 0);
    }

    public static void createAccount() throws IOException 
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome, newcomer! Let's create your account.");
        System.out.println("Please enter a valid username for your account:");
        String user = input.nextLine();
        System.out.println("Please create a password:");
        String password = input.nextLine();
        accounts.put(user, password);
        try {
            bf = new BufferedWriter(new FileWriter(info, true));
            bf.append(user).append(":").append(password);
            bf.newLine();
            System.out.println("Account successfully created!");
            bf.flush();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try {
                bf.close();
            } catch (Exception ignored) 
            {
            }
        }
        showMainMenu(user);
    }

    protected static void login() throws IOException 
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome back! Please enter your username:");
        String user = input.nextLine();
        System.out.println("Please enter your password:");
        String password = input.nextLine();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(info))) 
        {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(user) && parts[1].equals(password)) 
                {
                    System.out.println("Hello, wellcom back " + user + "!");
                    showMainMenu(user); // Show the main menu after successful login
                    found = true;
                    break;
                }
            }
            if (!found) 
            {
                System.out.println("Sorry, invalid username or password.");
            }
        }
    }

    public static void showMainMenu(String username) throws IOException 
    {
        Scanner input = new Scanner(System.in);
        System.out.println("\nWelcome, " + username + "!");
        System.out.println("Please select an option:");
        System.out.println("1. Make a Reservation");
        System.out.println("2. View Reservations");
        System.out.println("3. Cancel Reservation");
        System.out.println("4. Exit");
        int choice = input.nextInt();

        switch (choice) 
        {
            case 1:
                Order.opener();
                break;
            case 2:
                viewReservations();
                break;
            case 3:
                input.nextLine(); // Consume the newline character
                System.out.println("Enter the phone number associated with the reservation you want to cancel:");
                String phoneNumber = input.nextLine();
                cancelReservation(phoneNumber);
                break;
            
            case 4:
                System.out.println("Exiting. Have a nice day!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please choose again.");
                showMainMenu(username);
                break;
        }
    }

    public static void viewReservations() 
    {
        System.out.println("Viewing Reservations:");
        try (BufferedReader reader = new BufferedReader(new FileReader(reservationFile))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                System.out.println(line);
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void cancelReservation(String phoneNumber) 
    {
        System.out.println("Searching for reservation with phone number: " + phoneNumber);
    
        Iterator<Reservation> iterator = reservations.iterator();
        boolean found = false;
    
        while (iterator.hasNext()) 
        {
            Reservation reservation = iterator.next();
            System.out.println("Checking reservation with phone number: " + reservation.getNumber());
    
            if (reservation.getNumber().equals(phoneNumber)) 
            {
                iterator.remove();
                found = true;
                break;
            }
        }
    
        if (found) 
        {
            System.out.println("Reservation canceled successfully.");
            writeReservationsToFile();
        } else 
        {
            System.out.println("No reservation found with the provided phone number.");
        }
    }
    
    public static void addReservation(String name, String number, String email, String date, String time, String payment) 
    {
        reservations.add(new Reservation(name, number, email, date, time, payment,0.0));
        writeReservationsToFile();
    }
    
    public static void writeReservationsToFile() 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reservationFile))) 
        {
            for (Reservation reservation : reservations) {
                writer.write(reservation.toString());
                writer.newLine();
            }
            System.out.println("Reservations written to file.");
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    private static void readReservationsFromFile() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(reservationFile))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                System.out.println("Reading line: " + line); // Debug print
    
                String[] parts = line.split(", Phone Number: ");
                if (parts.length == 2) {
                    String phoneNumber = parts[1].split(", Email: ")[0];
                    phoneNumber = phoneNumber.replaceAll("\\s+", ""); // Remove spaces
                    System.out.println("Phone Number: " + phoneNumber); // Debug print
    
                    reservations.add(new Reservation(parts[0], phoneNumber, parts[2], parts[3], parts[4], parts[5], 0.0));
                }
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    

    // Reservation class
    private static class Reservation 
    {
        private String name;
        private String number;
        private String email;
        private String date;
        private String time;
        private String payment;
        private double total;

        public Reservation(String name, String number, String email, String date, String time, String payment, double total) 
        {
            this.name = name;
            this.number = number;
            this.email = email;
            this.date = date;
            this.time = time;
            this.payment = payment;
            this.total = total; // Set the total value directly
        }

        @Override
        public String toString() 
        {
            return "Name: " + name + ", Phone Number: " + number + ", Email: " + email +
                    ", Check-in: " + date + " at " + time + ", Payment Type: " + payment + ", Total: $" + total;
        }
        public String getNumber() 
        {
        return number;
        }
    }
}




 */








/* //package com.Account;
//import com.Order.Order;
import java.io.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import static java.lang.Character.getName;


public class Account {
    static HashMap<String, String> accounts = new HashMap<>();
    static String file = "C:\\Users\\saras\\IdeaProjects\\Hotel\\src\\all_logins";
    static File info = new File(file);
    static BufferedWriter bf = null;


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
                bf = new BufferedWriter(new FileWriter(info, true));
                for (Map.Entry<String, String> entry : accounts.entrySet()) {
                    bf.append(entry.getKey()).append(":").append(entry.getValue());
                    bf.newLine();
                    System.out.println("Account successfully created!");
                    Order.opener();
                }
                bf.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bf.close();
                } catch (Exception ignored) {

                }
            }
        } else if (choice.equals("y")) {
            login();
        }
    }
    protected static void login() throws IOException {
        System.out.println("Welcome back! Can you enter your username please!");
        Scanner input = new Scanner(System.in);
        String user = input.nextLine();
        System.out.println("Please enter your password: ");
        String password = input.nextLine();
        Scanner read = new Scanner(info);



        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String str;
            while((str = reader.readLine()) != null){
                if ((str.contains(user) && str.contains(password))) {
                    System.out.println("Hello, " + user + "!");
                    Order.opener();
                }
                else{
                    str = read.nextLine();
                }
            } System.out.println("Sorry, would you like to try again (Y or N)?");
            String again = input.nextLine().trim().toLowerCase();
            if(again.equals("y")){
                login();
            }else{
                System.out.println("okay, have a nice day!");
            }



        }

        // (read.hasNextLine()) {
          //  String str = read.nextLine();
          //  if ((str.contains(user) && str.contains(password))) {
            //    System.out.println("Hello, " + user + "!");
             //   Order.opener();
          //  }
          //  System.out.println("Account not found, would you like to try again(Y or N)?");
          //  String training = input.nextLine().trim().toLowerCase();
          //  if (training.equals("y")) {
          //      login();
          //  } else {
         //       System.out.println("Okay have a nice day!");
         //   }


        }


        } */










