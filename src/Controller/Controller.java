package Controller;

import java.util.Stack;

import Model.Model;
import Model.Classes.Maze;
import Model.Structs.Location;
import View.View;

public class Controller 
{
    private Model model;
    private View view;

    public Controller(Model modelParameter, View viewParameter)
    {
        this.model = modelParameter;
        this.view = viewParameter;

        this.view.addOnClickActionListenerToLoadMazeButton(event ->this.onLoadMazeButtonClicked());
        this.view.addOnClickActionListenerToStartButton(event -> onStartButtonClicked());
    }

    private void onLoadMazeButtonClicked()  
    {
        if(this.model.loadMaze())
        {
            this.view.resetStagesTextArea();
            this.view.setStartButtonEnabled(true);
        }
    }

    private void onStartButtonClicked()
    { 
        this.view.resetStagesTextArea();
        this.model.start(this.view.getPopulationSizeSpinnerValue(), this.view.getDelaySpinnerValue(), this.view.isVisualizeCheckboxChecked());
    }

    public void setMazePanel(Maze mazeParameter)
    {
        this.view.setMazePanel(mazeParameter);
        this.view.setStartButtonEnabled(true);
    }

    public void refreshMazePanel(Maze mazeParameter, Stack<Location>[] pathsLocationsParameter)
    {
        this.view.refreshMazePanel(mazeParameter, pathsLocationsParameter);
    }

    public void printStageInfo(String stringParameter)
    {
        this.view.printStageInfo(stringParameter);
    }

    public void showAlert(String stringParameter)
    {
        this.view.showAlert(stringParameter);
    }
}
