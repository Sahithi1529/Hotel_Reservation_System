package model;

public class FreeRoom extends Room
{
    public FreeRoom(String roomNumber, RoomType roomCategory)
    {
        super(roomNumber, 0.0, roomCategory);
    }

    @Override
    public boolean isFree()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "Room Number "+getRoomNumber()+" is a FreeRoom\nRoom Type is "+getRoomType()+"---------------------";
    }

}
