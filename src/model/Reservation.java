package model;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation
{
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate)
    {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public IRoom getRoom()
    {
        return room;
    }

    public Date getCheckInDate()
    {
        return checkInDate;
    }

    public Date getCheckOutDate()
    {
        return checkOutDate;
    }

    @Override
    public String toString()
    {
        return "Customer Details"+"\n"+"------------------------"+"\n"+"Name : "+customer+
                "\nRoom Number : "+room+"\nCheckIn Date : "+format.format(checkInDate)+"\nCheckOut Date : "+format.format(checkOutDate);

    }

}
