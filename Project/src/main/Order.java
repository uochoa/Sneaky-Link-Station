import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

public class Order {
    static int standard = 100;
    static int deluxe = 150;
    static int suite = 200;
    static int availableStandardRooms = 5;
    static int availableDeluxeRooms = 5;
    static int availableSuiteRooms = 5;
    static String room;
    static int day;
    static int roomNum;
    static String valid_name = "^[a-zA-Z]+ [a-zA-Z]+$";
    static String valid_phone_number = "^\\d{10}$";
    static String valid_email = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    static String valid_date = "^(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])-[0-9]{4}$";
    static String roomsFilePath = "rooms.txt";

    // static String valid_time = "(1[012]|[1-9]):"+ "[0-5][0-9](\\s)"+
    // "?(?i)(am|pm)";
    private static final DateTimeFormatter DATE_PARSER = DateTimeFormatter.ofPattern("MM-dd-uuuu");

    public static void main(String[] args) throws IOException {
        opener();

    }

    public static int getAvailableRooms(String roomType) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("rooms.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(roomType)) {
                System.out.println("Line read: " + line); // Add this line
                Matcher matcher = Pattern.compile("\\d+").matcher(line);
                if (matcher.find()) {
                    int availableRooms = Integer.parseInt(matcher.group());
                    reader.close();
                    return availableRooms;
                }
            }
        }
        reader.close();
        return 0;
    }
    

    private static String getRoomType(int selectedRoomType) {
        if (selectedRoomType == 1) {
            return "Standard";
        } else if (selectedRoomType == 2) {
            return "Deluxe";
        } else if (selectedRoomType == 3) {
            return "Suite";
        } else {
            return ""; // Handle invalid room type
        }
    }

    private static void updateAvailableRooms(String roomType, int change) throws IOException {
        File file = new File("rooms.txt");
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(roomType)) {
                    Matcher matcher = Pattern.compile("\\((\\d+)\\)").matcher(line);
                    if (matcher.find()) {
                        int totalRooms = Integer.parseInt(matcher.group(1)); // Extract total rooms
                        int availableRooms = totalRooms - change; // Update available rooms
                        line = roomType + " (" + totalRooms + "): " + availableRooms + " rooms available";
                    }
                }
                lines.add(line);
            }
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
    
    
    
    
    
    
    
    

    public static void opener() throws IOException {
        String taxList = " This is including an 18% tax.";
        System.out.println("We have several rooms available:\n (1)Standard $" + standard + "/night\n " +
                "(2)Deluxe $" + deluxe + "/night\n (3)Suite $" + suite + "/night\n Please enter the number you are interested in!");
        Scanner input = new Scanner(System.in);
        int selectedRoomType = input.nextInt(); // Store user's selected room type
        String roomType; // Store room type identifier
    
        if (selectedRoomType == 1) {
            roomType = "Standard";
        } else if (selectedRoomType == 2) {
            roomType = "Deluxe";
        } else if (selectedRoomType == 3) {
            roomType = "Suite";
        } else {
            System.out.println("Invalid room type selection.");
            return;
        }
    
        int availableRooms = getAvailableRooms(roomType);
    
        if (availableRooms == 0) {
            System.out.println("Sorry, there are no available rooms of this type.");
            return;
        }
    
        int roomNum;
        int checkRoom = 0;
        do {
            System.out.println("Enter the number of Rooms you wish to reserve: ");
            roomNum = input.nextInt();
    
            if (roomNum <= 0 || roomNum > availableRooms) {
                System.out.println("Invalid number of rooms. Please enter a number between 1 and " + availableRooms);
            } else {
                checkRoom = 1;
            }
        } while (checkRoom == 0);
    
        System.out.println("Awesome, how many days would this be for?");
        int day = input.nextInt();
        rooms(selectedRoomType, day, roomNum, taxList);
        updateAvailableRooms(roomType, roomNum);
    }
    
    
    

    public static void rooms(int selectedRoomType, int day, int roomNum, String taxList) throws IOException {
        String roomType;
        int roomPrice;
    
        if (selectedRoomType == 1) {
            roomType = "Standard";
            roomPrice = 100;
        } else if (selectedRoomType == 2) {
            roomType = "Deluxe";
            roomPrice = 150;
        } else if (selectedRoomType == 3) {
            roomType = "Suite";
            roomPrice = 200;
        } else {
            System.out.println("Invalid room type selection.");
            return;
        }
    
        int availableRooms = getAvailableRooms(roomType);
    
        if (availableRooms >= roomNum) {
            int total = (roomPrice * day * roomNum);
            double newTotal = (total * 0.18) + total;
            System.out.println("You have selected the " + roomType + " Room, for " + day + " day(s)!\n" +
                    "Your total would come out to $" + newTotal + taxList);
            closer(selectedRoomType, day, taxList, newTotal);
        } else {
            System.out.println("Sorry, there are not enough available rooms of this type.");
        }
    }

    private static void closer(int selectedRoomType, int day, String taxList, double newTotal) throws IOException {
        System.out.println("We will now need you to provide us with a set of information to help us finalize your " +
                "reservation.\nEnter your first and last name: ");

        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        validate_user_entry(valid_name, name);

        System.out.println("Enter Phone Number (provide only digits): ");
        String number = input.nextLine();
        validate_user_entry(valid_phone_number, number);

        System.out.println("Enter email address: ");
        String email = input.nextLine();
        validate_user_entry(valid_email, email);

        System.out.println("What date would you like to check in (MM-DD-YYYY)?");
        String date = input.nextLine();
        validate_user_entry(valid_date, date);

        LocalDate today = LocalDate.now();
        LocalDate myDate = LocalDate.parse(date, DATE_PARSER);
        int checkDate = 0;
        do {
            if (myDate.isBefore(today)) {
                System.out.println(date + " is in the past. Please enter valid date");
                date = input.nextLine();
                myDate = LocalDate.parse(date, DATE_PARSER);
            } else if (myDate.isAfter(today)) {
                checkDate = 1;
            } else {
                System.out.println(date + " is today. Please enter a date in the future");
                date = input.nextLine();
                myDate = LocalDate.parse(date, DATE_PARSER);
            }
        } while (checkDate == 0);

        System.out.println("Would you like to pay Cash or Credit/Debit?");
        String payment = input.nextLine().trim().toLowerCase();
        System.out.println("Thank you " + name + " for your patience. Please confirm if the following statements are " +
                "true...\nName: " + name + "\nPhone Number: " + number + "\nEmail: " + email + "\nCheck in: " + date +
                "\nPayment type: " + payment + "\nIs this correct (Yes or No)?");
        String correct = input.next().trim().toLowerCase();
        if (correct.equals("y") || correct.equals("yes")) {
            System.out.println("Awesome, your total comes out to $" + newTotal + taxList + "\nPlease be ready to show "
                    +
                    "your ID for verification at the front desk upon arrival! The check in time at 2:00 pm. We will be giving you your room number after "
                    +
                    "check in was successful.\nSee you on " + date + "!");

            String roomType = getRoomType(selectedRoomType); // Get the corresponding room type

            // Update available rooms count in the rooms.txt file
            updateAvailableRooms(roomType, roomNum);

            Account.addReservation(name, number, email, date, "2:00 pm", payment, newTotal);
        } else {
            System.out.println("Please try again");
            closer(selectedRoomType, day, taxList, newTotal);
        }
    }

    public static void validate_user_entry(String regex, String user_entry) {
        int check = 0;
        Scanner input = new Scanner(System.in);

        do {
            // regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(user_entry);

            if (m.matches()) {
                check = 1;
            } else {
                System.out.println("Invalid entry. Please re-enter.");
                user_entry = input.nextLine();
            }

        } while (check == 0);
    }

}
