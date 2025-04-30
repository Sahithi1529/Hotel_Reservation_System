package api;

import model.Customer;
import service.CustomerService;
import model.IRoom;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource
{
    private AdminResource() {}
    private static final AdminResource adminRefer = new AdminResource();
    public static AdminResource getAdminResourceObj()
    {
        return adminRefer;
    }

    private final CustomerService cusService = CustomerService.getCustomerObj();

    public Customer getCustomer(String email)
    {
        return cusService.getCustomer(email);
    }

    private final ReservationService resService = ReservationService.getReservationObj();
    public void addRoom(List<IRoom> rooms)
    {
                resService.addRoomList(rooms);

    }

    public Collection<IRoom> getAllRooms()
    {
      return resService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers()
    {
      return cusService.getAllCustomers();
    }

    public void displayAllReservations()
    {
        resService.printAllReservation();
    }

}
