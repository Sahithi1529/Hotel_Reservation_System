package api;

import model.Customer;
import model.Reservation;
import service.CustomerService;
import model.IRoom;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource
{
    private static final HotelResource reference = new HotelResource();
    public static HotelResource getHotelResourceObj()
    {
        return reference;
    }
    private HotelResource() {}

    private final CustomerService cusService = CustomerService.getCustomerObj();
    public Customer getCustomer(String email)
    {
        return cusService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName)
    {
        cusService.addCustomer(email, firstName, lastName);
    }

    private final ReservationService resService = ReservationService.getReservationObj();
    public IRoom getRoom(String RoomNumber)
    {
        return resService.getARoom(RoomNumber);

    }

    public Reservation bookARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate)
    {
        if (customer == null) {
            throw new IllegalArgumentException("Customer with this email not found");
        }
        return resService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail)
    {
        if (cusService.getCustomer(customerEmail) == null) {
            throw new IllegalArgumentException("There is no customer found with the email: "+customerEmail);
        }
        return resService.getCustomerReservation(cusService.getCustomer(customerEmail));

    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut)
    {
    return resService.findRooms(checkIn, checkOut);
    }

}
