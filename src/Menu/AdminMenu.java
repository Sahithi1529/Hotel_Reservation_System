package Menu;

import api.AdminResource;
import api.HotelResource;
import model.*;
import service.ReservationService;

import java.util.Collection;
import java.util.Scanner;

public class AdminMenu
{
    public static void printAdminMenu()
    {
        System.out.println("Admin Menu\n-------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("Enter your choice from above options:");
    }

    private static final Scanner input = new Scanner(System.in);
    public static void adminMenuList() {
                String choice;
                do {
                    printAdminMenu();
                    choice = input.nextLine();
                    switch (choice)
                    {
                        case "1": seeAllCustomers();break;
                        case "2": seeAllRooms();break;
                        case "3": seeAllReservations();break;
                        case "4": addARoom();break;
                        case "5":System.out.println("Exiting Admin Menu.....");MainMenu.mainMenuList();break;
                        default:System.out.println("Enter correct input.Choose number from 1 to 5.");
                    }
                }while(!choice.equals("5"));
    }

    public static void seeAllCustomers()
    {
       Collection<Customer> allCustomers =  admin.getAllCustomers();
       if(allCustomers.isEmpty())
       {
           System.out.println("The customer list is empty.");
       }
       for(Customer user:allCustomers)
       {
           System.out.println(user);
       }
    }

    private static final AdminResource admin = AdminResource.getAdminResourceObj();
    private static final ReservationService ResService = ReservationService.getReservationObj();
    public static void seeAllRooms()
    {
        Collection<IRoom> allRooms = admin.getAllRooms();
        if(allRooms.isEmpty())
        {
            System.out.println("Rooms List is empty.");
        }
        for(IRoom space:allRooms)
        {
            System.out.println(space);
        }
    }

    public static void seeAllReservations()
    {
        admin.displayAllReservations();
    }

    private static final HotelResource hotel = HotelResource.getHotelResourceObj();
    public static void addARoom()
    {
       try{
           System.out.println("Enter Room number to add:");
           String roomNo = input.nextLine();
           if(hotel.getRoom(roomNo) != null)
           {
               System.out.println("Use other Room Number, since this Room Number already exists!!");
               adminMenuList();
               return;
           }

           System.out.println("Enter cost of room per night:");
           double cost = Double.parseDouble(input.nextLine());

           if(cost<0)
           {
               System.out.println("Cost cannot be negative...");
               adminMenuList();
           }

           System.out.println("Enter type of Room (SINGLE/DOUBLE):");
           String typeOfRoom = input.nextLine().toUpperCase();
           if (!typeOfRoom.equals("SINGLE") && !typeOfRoom.equals("DOUBLE"))
           {
               System.out.println("Only SINGLE/DOUBLE Room Type is Allowed!!");
               return;
           }

           IRoom newRoom;
           if(cost==0.0)
           {
               newRoom = new FreeRoom(roomNo, RoomType.valueOf(typeOfRoom));
           }
           else
           {
               newRoom = new Room(roomNo, cost, RoomType.valueOf(typeOfRoom));
           }
           ResService.addRoom(newRoom);
           System.out.println("New Room has been added Successfully!!");

           System.out.println("Add another room (yes/no):");
           String addOther = input.nextLine();
           if(addOther.equalsIgnoreCase("yes"))
           {
               addARoom();
           }
           else
           {
               System.out.println("Only yes or no is allowed..Try again!!!");
               adminMenuList();
           }

       } catch (NumberFormatException exception)
       {
           System.out.println("Wrong price format is entered. Try Again!!");
           adminMenuList();
       }
       catch(Exception exception)
       {
           System.out.println("Exception caught:"+ exception.getMessage());
           adminMenuList();
       }
    }

    public static void main(String[] args)
    {
        adminMenuList();
    }

}
