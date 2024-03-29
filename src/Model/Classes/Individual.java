package Model.Classes;

import java.util.Stack;

import Model.Structs.Location;

public class Individual 
{
    private Maze maze;

    public Individual(Maze mazeParameter)
    {
        this.maze = mazeParameter.getMazeCopyWithRandomLocationStates();
    }

    public Individual(Individual firstParentParameter, Individual secondParentParameter)
    {
        this.maze =  new Maze(firstParentParameter.maze, secondParentParameter.maze);
    }

    public boolean isPathFound()
    {
        return this.maze.isPathFound();
    }

    public double getFitness()
    {
        Stack<Location> pathLocations = this.maze.getPathLocations();
        return pathLocations.lastElement().getDistanceToLocation(this.maze.getEndLocation()) - pathLocations.size();
    }

    public Stack<Location> getPathLocations()
    {
        return this.maze.getPathLocations();
    }
}
