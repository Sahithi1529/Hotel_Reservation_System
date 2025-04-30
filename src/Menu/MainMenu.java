package Menu;
import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.ReservationService;

import java.util.Calendar;
import java.util.Collection;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class MainMenu {

    private static final ReservationService RService = ReservationService.getReservationObj();
    private static final HotelResource hotel = HotelResource.getHotelResourceObj();
    private static final AdminResource admin = AdminResource.getAdminResourceObj();

    public static void main(String[] args) {
        MainMenu.mainMenuList();
    }

    public static void printMainMenu() {
        System.out.println("MainMenu\n-----------------------------------");
        System.out.println("Choose any option from below Menu");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my Reservations");
        System.out.println("3. create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.print("Enter your choice : ");

    }

    private static final Scanner input = new Scanner(System.in);

    public static void mainMenuList() {
        String choice;
        do {
            printMainMenu();
            choice = input.nextLine();
            switch (choice) {
                case "1": findAndReserveRoom();break;
                case "2": seeMyReservations();break;
                case "3": createAccount();break;
                case "4": AdminMenu.adminMenuList();break;
                case "5": System.out.println("Closing the Application.......");break;
                default: System.out.println("Enter valid input. choose a number from 1 to 5");
            }
        } while (!choice.equals("5"));
        input.close();
    }

    private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    static {
        format.setLenient(false);
    }

    public static void findAndReserveRoom() {
        try {
            System.out.println("Enter CheckInDate in format MM/DD/YYYY : ");
            Date checkInDate = format.parse(input.nextLine());

            System.out.println("Enter CheckOutDate in format MM/DD/YYYY : ");
            Date checkOutDate = format.parse(input.nextLine());
            Date today = format.parse(format.format(new Date()));

            if (checkInDate.before(today)) {
                System.out.println("Provide correct checkInDate. It should be current date or later...");
                mainMenuList();
            }
            if (!checkOutDate.after(checkInDate)) {
                System.out.println("Provide correct checkOutDate. It should be the date after checkInDate");
                mainMenuList();
            }

            Collection<IRoom> rooms = hotel.findARoom(checkInDate, checkOutDate);

            if (rooms.isEmpty()) {
                System.out.println("In between the selected dates, No Rooms are Vacant.");
                Date alterCheckInDate = addDays(checkInDate);
                Date alterCheckOutDate = addDays(checkOutDate);
                Collection<IRoom> alternativeRooms = RService.getRecommendedRooms(alterCheckInDate, alterCheckOutDate);
                if (alternativeRooms.isEmpty()) {
                    System.out.println("In alternative dates also no rooms are vacant!!");
                } else {
                    showAlternativeRooms(alternativeRooms);
                }
                mainMenuList();
                return;
            }

            System.out.println("All Available Rooms are:");
            for (IRoom room : rooms) {
                System.out.print(room + "\n");
            }

            boolean correctInput = false;
            String registeredUser;
            do {
                System.out.print("Are you a registered customer and has account ? Enter y/n:");
                registeredUser = input.nextLine();

                if (registeredUser.equalsIgnoreCase("y") || registeredUser.equalsIgnoreCase("n")) {
                    correctInput = true;
                } else {
                    System.out.println("Input provided is not valid. Enter only either 'y' or 'n' for yes or no.");
                }
            } while (!correctInput);

            if (registeredUser.equalsIgnoreCase("n")) {
                System.out.println("To further proceed with the application, Create account first and get registered....");
                createAccount();
                return;
            }
            else{
                System.out.print("Please provide your email: ");
            }



            String mail = input.nextLine();
            Customer customer = admin.getCustomer(mail);

            if (customer == null) {
                System.out.println("Unregistered mail is provided, no customer found!! Create account first...");
                mainMenuList();
            }

            System.out.print("Please select a room number to reserve from the available options above:");
            String roomNo = input.nextLine();
            IRoom getRoom = hotel.getRoom(roomNo);

            if (getRoom == null) {
                System.out.println("Try Again!! No Room is found.");
                mainMenuList();
            }

            if (!rooms.contains(getRoom)) {
                System.out.println("For the chosen dates room is not available!!");
                mainMenuList();
            }

            Reservation reservation = hotel.bookARoom(customer, getRoom, checkInDate, checkOutDate);
            System.out.println("Reservation is Successful... Details of reservation is:");
            System.out.println(reservation);
            mainMenuList();

        } catch (ParseException exception) {
            System.out.println("Invalid dateFormat is entered. Use the format MM/DD/YYYY only.");
            mainMenuList();
        } catch (Exception exception)
        {
            System.out.println("Exception caught: " + exception.getMessage());
            mainMenuList();
        }

    }

    public static void seeMyReservations() {
        System.out.println("Enter your registered Email ID:");
        String mailId = input.nextLine();

        Customer customer = hotel.getCustomer(mailId);
        if (customer == null)
        {
            System.out.println("Customer is not registered..Create account to proceed further.");
            mainMenuList();
        }


        Collection<Reservation> allMyReservations = hotel.getCustomerReservations(mailId);
        if (allMyReservations.isEmpty())
        {
            System.out.println("No Reservations found with the provided mail..");
        }
        else
        {
            System.out.println("My Reservations:");
            for (Reservation reservation : allMyReservations)
            {
                System.out.println(reservation);
            }
        }
    }

    public static void createAccount() {
        try {
            System.out.println("Enter your details to create an account and get registered...");
            System.out.println("Enter your email in format:name@domain.com:");
            String mailId = input.nextLine();

            System.out.println("Enter your first name:");
            String fName = input.nextLine();

            System.out.println("Enter your last name:");
            String lName = input.nextLine();

            hotel.createACustomer(mailId, fName, lName);
            System.out.println("Congratulations!! Your account setup is done.");

            mainMenuList();
        } catch (IllegalArgumentException exception)
        {
            System.out.println("Exception caught: " + exception.getMessage());
        }
    }

    private static Date addDays(Date newDate)
    {
        Calendar schedule = Calendar.getInstance();
        schedule.setTime(newDate);
        schedule.add(Calendar.DAY_OF_MONTH, 7);
        return schedule.getTime();
    }

    private static void showAlternativeRooms(Collection<IRoom> rooms)
    {
        System.out.println("Vacant Rooms available after 7 days are:");
        for (IRoom room : rooms)
        {
            System.out.println(room);
        }
    }
}

