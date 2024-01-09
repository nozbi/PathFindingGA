package Model;

import javax.swing.Timer;
import Controller.Controller;
import Model.Classes.Maze;
import Model.Classes.MazeChooser;
import Model.Classes.Population;

public class Model 
{
    private Controller controller;
    private Maze maze;
    private Timer timer;
    private Population population;
    private int stageIndex;
    private boolean visualize;

    public void setController(Controller controllerParameter)
    {
        this.controller = controllerParameter;
    }

    private void nextStage()
    {
        this.controller.printStageInfo("Stage " + this.stageIndex + " fitness: " + this.population.getFitness());
        if(this.visualize)
        {
            this.controller.refreshMazePanel(this.maze, this.population.getPathsLocations());
        }

        if(population.isPathFound())
        {
            this.timer.stop();
            this.controller.refreshMazePanel(this.maze, this.population.getFoundPathLocations());
            this.controller.showAlert("Path found!");
        }
        else
        {
            this.population.nextGeneration();
            this.stageIndex++;
            if(this.stageIndex == 10000)
            {
                this.timer.stop();
                this.controller.showAlert("Path not found!");
            }
        }
    }

    public boolean loadMaze()
    {
        try
        {
            Maze newMaze = MazeChooser.getChoosenMaze();

            if(newMaze != null)
            {
                if(this.timer != null)
                {
                    this.timer.stop();
                }
                
                this.maze = newMaze;
                this.controller.setMazePanel(this.maze);
                this.controller.refreshMazePanel(this.maze, null);
                return true;
            }
        }
        catch(Exception exception)
        {
            this.controller.showAlert("Incorrect file format!");
        } 
        return false;
    }

    public void start(int populationSizeParameter, int delayParameter, boolean visualizeParameter)
    {
        if(this.timer != null)
        {
            this.timer.stop();
        }
        this.stageIndex = 0;
        this.population = new Population(populationSizeParameter, this.maze);
        this.visualize = visualizeParameter;

        this.controller.refreshMazePanel(this.maze, null);

        this.timer = new Timer(delayParameter, actionEvent -> 
        {
            this.nextStage();
        });
        this.timer.setRepeats(true);
		this.timer.setInitialDelay(0);
        this.timer.start();
    }
}
