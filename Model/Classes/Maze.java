package Model.Classes;

import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;
import Model.Enums.LocationState;
import Model.Structs.Location;

public class Maze
{
    private LocationState[][] locationsStatesMatrix;
    private Location startLocation;
    private Location endLocation;

    public Maze(LocationState[][] locationsStatesMatrixParameter, Location startLocationParameter, Location endLocationParameter)
    {
        this.locationsStatesMatrix = locationsStatesMatrixParameter;
        this.startLocation = startLocationParameter;
        this.endLocation = endLocationParameter;
    }

    public Maze(Maze firstMazeParameter, Maze secondMazeParameter)
    {
        this.startLocation = firstMazeParameter.startLocation;
        this.endLocation = firstMazeParameter.endLocation;
        this.locationsStatesMatrix = new LocationState[firstMazeParameter.getSizeY()][firstMazeParameter.getSizeX()];
        for(int y = 0; y < this.locationsStatesMatrix.length; y++)
        {
            for(int x = 0; x < this.locationsStatesMatrix[0].length; x++)
            {
                Location location = new Location(x, y);
                this.setLocationState(location, firstMazeParameter.getLocationState(location));
            }
        }
        
        Stack<Location> pathLocations = secondMazeParameter.getPathLocations();
        int pathLocationsCrossoverIndex = ThreadLocalRandom.current().nextInt(0, pathLocations.size());
        for(int i = 0; i < pathLocationsCrossoverIndex; i++)
        {
            Location location = pathLocations.get(i);
            this.setLocationState(location, secondMazeParameter.getLocationState(location));
        }
    }

    private Maze(Maze mazeParameter)
    {
        this(null, mazeParameter.startLocation, mazeParameter.endLocation);
        
        this.locationsStatesMatrix = new LocationState[mazeParameter.locationsStatesMatrix.length][mazeParameter.locationsStatesMatrix[0].length];
        for(int y = 0; y < this.getSizeY(); y++)
        {
            for(int x = 0; x < this.getSizeX(); x++)
            {
                Location location = new Location(x, y);
                if(mazeParameter.getLocationState(location) == LocationState.BLOCKED)
                {
                    this.setLocationState(location, LocationState.BLOCKED);
                }
                else
                {
                    Stack<LocationState> possibleLocationStates = new Stack<LocationState>();
                    if(mazeParameter.getLocationState(location.getAdjacentLocation(LocationState.RIGHT)) != LocationState.BLOCKED)
                    {
                        possibleLocationStates.add(LocationState.RIGHT);
                    }
                    if(mazeParameter.getLocationState(location.getAdjacentLocation(LocationState.DOWN)) != LocationState.BLOCKED)
                    {
                        possibleLocationStates.add(LocationState.DOWN);
                    }
                    if(mazeParameter.getLocationState(location.getAdjacentLocation(LocationState.LEFT)) != LocationState.BLOCKED)
                    {
                        possibleLocationStates.add(LocationState.LEFT);
                    }
                    if(mazeParameter.getLocationState(location.getAdjacentLocation(LocationState.UP)) != LocationState.BLOCKED)
                    {
                        possibleLocationStates.add(LocationState.UP);
                    }

                    if(possibleLocationStates.isEmpty())
                    {
                        this.setLocationState(location, null);
                    }
                    else
                    {
                        int index = ThreadLocalRandom.current().nextInt(0, possibleLocationStates.size());
                        this.setLocationState(location, possibleLocationStates.get(index));
                    }
                }
            }
        }
    }

    private LocationState getLocationState(Location locationParameter)
    {
        if((locationParameter.getX() < 0) || (locationParameter.getY() < 0) || (locationParameter.getX() >= this.getSizeX()) || (locationParameter.getY() >= this.getSizeY()))
        {
            return LocationState.BLOCKED;
        }
        else
        {
            return this.locationsStatesMatrix[locationParameter.getY()][locationParameter.getX()];
        }
    }

    private void setLocationState(Location locationParameter, LocationState locationStateParameter)
    {
        this.locationsStatesMatrix[locationParameter.getY()][locationParameter.getX()] = locationStateParameter;
    }

    public int getSizeX()
    {
        return this.locationsStatesMatrix[0].length;
    }

    public int getSizeY()
    {
        return this.locationsStatesMatrix.length;
    }

    public Maze getMazeCopyWithRandomLocationStates()
    {
        return new Maze(this);
    }

    public Stack<Location> getPathLocations()
    {
        Stack<Location> locations = new Stack<Location>();
        Location location = this.startLocation;
        boolean pathEnded = false;
        while(pathEnded == false)
        {
            for(int i = 0; i < locations.size(); i++)
            {
                if(location.isEqual(locations.get(i)))
                {
                    pathEnded = true;
                    break;
                }
            }
            if(pathEnded)
            {
                break;
            }
            else
            {
                if(location.isEqual(this.endLocation))
                {
                    pathEnded = true;
                }
                locations.add(location.getCopy());
                location = location.getAdjacentLocation(this.getLocationState(location));
            }
        }
        return locations;
    }

    public boolean isPathFound()
    {
        return this.getPathLocations().lastElement().isEqual(this.endLocation);
    }

    public Location getStartLocation()
    {
        return this.startLocation;
    }

    public Location getEndLocation()
    {
        return this.endLocation;
    }

    public boolean isLocationBlocked(Location locationParameter)
    {
        return this.getLocationState(locationParameter) == LocationState.BLOCKED;
    }
}
