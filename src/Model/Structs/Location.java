package Model.Structs;

import Model.Enums.LocationState;

public class Location 
{
    private int x;
    private int y;

    public Location(int xParameter, int yParameter)
    {
        this.x = xParameter;
        this.y = yParameter;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public Location getAdjacentLocation(LocationState locationStateParameter)
    {
        switch(locationStateParameter)
        {
            case BLOCKED:
                break;
            case RIGHT:
                return new Location(this.x + 1, this.y);
            case DOWN:
                return new Location(this.x, this.y + 1);
            case LEFT:
                return new Location(this.x - 1, this.y);
            case UP:
                return new Location(this.x, this.y - 1);
        }
        return null;
    }

    public Location getCopy()
    {
        return new Location(this.x, this.y);
    }

    public boolean isEqual(Location locationParameter)
    {
        return ((this.x == locationParameter.x) && (this.y == locationParameter.y));
    }

    public double getDistanceToLocation(Location locationParameter)
    {
        return Math.hypot(this.x - locationParameter.x, this.y - locationParameter.y);
    }
}
