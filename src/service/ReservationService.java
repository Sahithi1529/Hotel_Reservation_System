package service;
import model.IRoom;
import model.Reservation;
import model.Customer;

import java.util.*;

public class ReservationService
{
    private ReservationService() {}
    private static final ReservationService reference = new ReservationService();
    public static ReservationService getReservationObj()
    {

        return reference;
    }

    private final Map<String, IRoom> accommodations = new HashMap<>();
    public void addRoom(IRoom room)
    {

        accommodations.put(room.getRoomNumber(),room);
    }
    public IRoom getARoom(String roomId)
    {

        return accommodations.get(roomId);
    }

    private final ArrayList<Reservation> bookings = new ArrayList<>();
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate)
    {
         Reservation reservations = new Reservation(customer, room, checkInDate, checkOutDate);
         bookings.add(reservations);
         return reservations;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate)
    {
        ArrayList<IRoom> vacantRooms = new ArrayList<>();
        if (accommodations.isEmpty())
        {
            return vacantRooms;
        }
        for (IRoom room : accommodations.values())
        {
            if (isVacant(room, checkInDate, checkOutDate)) {
                vacantRooms.add(room);
            }
        }
        return vacantRooms;
    }
    private boolean isVacant(IRoom room, Date checkInDate, Date checkOutDate)
    {
        for (Reservation reservation : bookings)
        {
            if (reservation.getRoom().equals(room))
            {
                if (!(checkOutDate.before(reservation.getCheckInDate()) || checkInDate.after(reservation.getCheckOutDate())))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer)
    {
        ArrayList<Reservation> bookingReservation = new ArrayList<>();
        for (Reservation booking : bookings)
        {
            if (booking.getCustomer().equals(customer))
            {
                bookingReservation.add(booking);
            }
        }
        return bookingReservation;
    }
    public void printAllReservation() {
        if (bookings.isEmpty())
        {
            System.out.println("Reservation List is Empty.");
        }

        for (Reservation reserve : bookings)
        {
            System.out.println(reserve);
        }
    }

    public Collection<IRoom> getAllRooms()
    {
        return accommodations.values();
    }

    public Collection<IRoom> getRecommendedRooms(Date checkInDate, Date checkOutDate)
    {
        ArrayList<IRoom> alternativeVacantRooms = new ArrayList<>();
        if (accommodations.isEmpty())
        {
            return alternativeVacantRooms;
        }
        for (IRoom room : accommodations.values())
        {
            if (isVacant(room, checkInDate, checkOutDate)) {
                alternativeVacantRooms.add(room);
            }
        }
        return alternativeVacantRooms;

    }

    public void addRoomList(List<IRoom> cells) {
        if (cells != null)
        {
            for (IRoom cell : cells)
            {
                accommodations.put(cell.getRoomNumber(), cell);
            }
        }
        else
        {
            throw new IllegalArgumentException("Enter Valid list of Rooms. Null values are not allowed..");
        }
    }

}
