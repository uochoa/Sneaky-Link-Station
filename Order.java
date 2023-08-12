
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Order {
    static int standard = 100;
    static int deluxe = 150;
    static int suite = 200;
    static String room;
    static int day;
    static int roomNum;
    static String valid_name = "^[a-zA-Z]+ [a-zA-Z]+$";
    static String valid_phone_numer = "^\\d{10}$";
    static String valid_email =  "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    static String valid_date = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$";

    //static String valid_time = "(1[012]|[1-9]):"+ "[0-5][0-9](\\s)"+ "?(?i)(am|pm)";
    private static final DateTimeFormatter DATE_PARSER = DateTimeFormatter.ofPattern("MM-dd-uuuu");
    public static void main(String[] args) throws IOException {
        opener();

    }

    public static void opener() 
    {
        System.out.println("We have several rooms available:\n (1)Standard $100/night\n " + "(2)Deluxe $150/night\n" +
                " (3)Suite $200/night\n Please enter the number you are interested in!");
        Scanner input = new Scanner(System.in);
        room = input.nextLine(); 
        
        // check for entry needs to happen here and not in the room function.
        int check_room = 0;
        do{
            //room = input.nextInt();
            if (room.equals("1")) 
        {
            System.out.println("great");
            
            //room = standard;
            check_room = 1;
            
        } else if (room.equals("2")) 
        {
             System.out.println("great");
            
            //room = deluxe;
            check_room = 1;
        } else if (room.equals("3")) 
        {
             System.out.println("great");
             
            //room = suite;
            check_room = 1;
        }
        else
        {
            System.out.println("Sorry invalid entry");
            room = input.nextLine();
            
        }

        }while(check_room == 0);
    
        
        System.out.println("Enter the number of Rooms you wish to reserve: ");
        roomNum = input.nextInt();
        System.out.println("Awesome, how many days would this be for?");
        day = input.nextInt();
        rooms(room, day, roomNum);
    }


    public static void rooms(String room, int day, int roomNum)
    {
        int standard = 100;
        int deluxe = 150;
        int suite = 200;
        String taxList = " This is including an 18% tax.";
        if(room.equals("1")){
            int total = (standard*day*roomNum);
            double newTotal = (total*.18)+total;
            System.out.println("You have selected the Standard Room,for " + day + " day(s)!\n" +
                    "Your total would come out too $" + newTotal + taxList);
            closer(day, taxList, newTotal);


        }else if(room.equals("2")){
            int total = (deluxe*day*roomNum);
            double newTotal = (total*.18)+total;
            System.out.println("You have selected the Deluxe Room,for " + day + " day(s)!\n" +
                    "Your total would come out too $" + newTotal + taxList);
            closer(day, taxList, newTotal);

        }else if(room.equals("3")){
            int total = (suite*day*roomNum);
            double newTotal = (total*.18)+total;
            System.out.println("You have selected the Suite Room,for " + day + " day(s)!\n" +
                    "Your total would come out too $" + newTotal + taxList);
            closer(day,taxList, newTotal);

        }else
        {
            System.out.println("Sorry we did not quite get that, please try again");
            
        }
    }

    private static void closer(int day, String taxList, double newTotal) 
    {

        
        System.out.println("We will now need you to provide us with a set of information to help us finalize your " +
                "reservation.\nEnter your first and last name: ");

        Scanner input = new Scanner(System.in);
        // Nora: fixed user entry capture ... input.next() will store user entry until it sees space.
        String name = input.nextLine();
        // Nora: Added a validation function for user entries.
        validate_user_entry(valid_name, name);
        
        System.out.println("Enter Phone Number (provide only digits): ");
        // Nora: fixed user entry capture ... input.next() will store user entry until it sees space.
        String number = input.nextLine();
        // Nora: Added a validation function for user entries.
        validate_user_entry(valid_phone_numer, number);


        System.out.println("Enter email address: ");
        // Nora: fixed user entry capture ... input.next() will store user entry until it sees space.
        String email = input.nextLine();
        // Nora: Added a validation function for user entries.
        validate_user_entry(valid_email, email);
        
        
        System.out.println("What date would you like to check in (MM-DD-YYYY)?");
        String date = input.nextLine();
        validate_user_entry(valid_date, date);
        // Nora: Capturing today's date
        LocalDate today = LocalDate.now();
        // Nora: Parsing the date in the wanted format to compare against today's date.
        LocalDate myDate = LocalDate.parse(date, DATE_PARSER);
        int check_date = 0;
        // Nora: do while loop to check for user entry for the date.
        do {
            if (myDate.isBefore(today)) 
            {
                System.out.println(date + " is in the past. Please enter valid date");
                date = input.nextLine();
                myDate = LocalDate.parse(date, DATE_PARSER);
            } else if (myDate.isAfter(today)) {
                check_date = 1;
            } else {
                System.out.println(date + " is today. Please enter a date in the future");
                date = input.nextLine();
                myDate = LocalDate.parse(date, DATE_PARSER);
            }
        }while(check_date == 0);

       /*  System.out.println("What time would you like to check in (00:00 Am/Pm)?");

        String time = input.nextLine();

        validate_user_entry(valid_time, time); */

        System.out.println("Would you like to pay cash or credit/debit?"); 
            

        String payment = input.next().trim().toLowerCase();
        System.out.println("Thank you " + name + " for your patience. Please confirm if the following statements are " +
                "true...\nName: " + name + "\nPhone Number: " + number + "\nEmail: " + email + "\nCheck in: " + date +
                 "\nPayment type: " + payment + "\nIs this correct (Y or N)?");
        String correct = input.next().trim().toLowerCase();
        if(correct.equals("y") || correct.equals("yes")){
            System.out.println("Awesome, your total comes out to $" + newTotal + taxList + "\nPlease be ready to show " +
                    "your ID for verification at the front desk upon arrival! The check in time at 2:00 pm. We will be giving you your room number after " +
                    "check in was successful.\nSee you on " + date + "!");
            //Struggling to exit program fron here
        }
        else
        {
            System.out.println("Please try it again");
            closer(day,taxList,newTotal);
        }

    }

       /*   public static void data() 
    {
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
    }  */

    public static void validate_user_entry(String regex, String user_entry)
    {
        int check = 0;
        Scanner input = new Scanner(System.in);

        do {  
            //regex =  "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(user_entry);

            if (m.matches())
            {
                check = 1;
            }
            else
            {
                System.out.println("Invalid entry. Please re-enter.");
                user_entry = input.nextLine();
            }
            
        }while(check == 0);
    }

}
