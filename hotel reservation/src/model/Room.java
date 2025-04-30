package model;

public class Room implements IRoom
{
    private final String roomNumber;
    private final Double price;
    private final RoomType roomCategory;

    public Room(String roomNumber, Double price, RoomType roomCategory){
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomCategory = roomCategory;
    }


    public final String getRoomNumber()
    {
        return roomNumber;
    }

    public final Double getRoomPrice()
    {
        return price;
    }

    public final RoomType getRoomType()
    {
        return roomCategory;
    }


    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString(){
        return "The RoomNumber is "+roomNumber+"\n"+"The RoomPrice is "+price+"\n"+"The RoomType is "+roomCategory+"\n------------------";
    }

}
