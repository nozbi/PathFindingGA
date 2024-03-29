package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JOptionPane;

import Model.Classes.Maze;
import Model.Structs.Location;
import View.Components.Button;
import View.Components.CheckBox;
import View.Components.Frame;
import View.Components.Label;
import View.Components.LocationPanel;
import View.Components.MainPanel;
import View.Components.MarkerPanel;
import View.Components.MenuBar;
import View.Components.MazePanel;
import View.Components.PopulationSizeSpinner;
import View.Components.StagesScrollPanel;
import View.Components.StagesTextArea;
import View.Components.DelaySpinner;

public class View 
{
    private MazePanel mazePanel;
    private Button loadMazeButton;
    private Button startButton;
    private DelaySpinner delaySpinner;
    private PopulationSizeSpinner populationSizeSpinner;
    private CheckBox visualizeCheckBox;
    private StagesTextArea stagesTextArea;

    public View()
    {
        //FRAME
        Frame frame = new Frame();

            //MENU BAR
            MenuBar menuBar = new MenuBar();

                this.loadMazeButton = new Button("Load maze");
                menuBar.add(loadMazeButton);

                this.startButton = new Button("Run");
                menuBar.add(this.startButton);

                menuBar.add(new Label("Delay:"));
                this.delaySpinner = new DelaySpinner();
                menuBar.add(this.delaySpinner);

                menuBar.add(new Label("Population size:"));
                this.populationSizeSpinner = new PopulationSizeSpinner();
                menuBar.add(this.populationSizeSpinner);

                this.visualizeCheckBox = new CheckBox("Visualize");
                menuBar.add(this.visualizeCheckBox);

            frame.setJMenuBar(menuBar);

            //MAIN PANEL
            MainPanel mainPanel = new MainPanel();
            frame.add(mainPanel);

                //MAZE PANEL
                this.mazePanel = new MazePanel();
                mainPanel.add(this.mazePanel, BorderLayout.CENTER);

               
                //SCROLL PANEL WITH STAGES TEXT AREA
                this.stagesTextArea = new StagesTextArea();
                StagesScrollPanel stagesScrollPanel = new StagesScrollPanel(stagesTextArea);
                mainPanel.add(stagesScrollPanel, BorderLayout.EAST);

        frame.setVisible(true);

        this.setStartButtonEnabled(false);
    }

    public void setMazePanel(Maze mazeParameter)
    {
        int sizeX = mazeParameter.getSizeX();
        int sizeY = mazeParameter.getSizeY();
        this.mazePanel.removeAll();
        this.mazePanel.setLayout(new GridLayout(sizeY, sizeX));

        for(int i = 0; i < sizeX * sizeY; i++)
        {
            this.mazePanel.add(new LocationPanel());
        }

        int startPanelIndex = (mazeParameter.getStartLocation().getY() * mazeParameter.getSizeX()) + mazeParameter.getStartLocation().getX();
        ((LocationPanel)this.mazePanel.getComponent(startPanelIndex)).add(new MarkerPanel(Color.GREEN));

        int targetPanelIndex = (mazeParameter.getEndLocation().getY() * mazeParameter.getSizeX()) + mazeParameter.getEndLocation().getX();
        ((LocationPanel)this.mazePanel.getComponent(targetPanelIndex)).add(new MarkerPanel(Color.RED));

        this.mazePanel.revalidate();
        this.mazePanel.repaint();
    }

    public void refreshMazePanel(Maze mazeParameter, Stack<Location>[] pathsLocationsParameter)
    {
        Component[] locationPanels = this.mazePanel.getComponents();
        for(int y = 0; y < mazeParameter.getSizeY(); y++)
        {
            for(int x = 0; x < mazeParameter.getSizeX(); x++)
            {
                int index = (y * mazeParameter.getSizeX()) + x;
                if(mazeParameter.isLocationBlocked(new Location(x, y)))
                {
                    locationPanels[index].setBackground(Color.BLACK);
                }
                else
                {
                    locationPanels[index].setBackground(Color.WHITE);
                }
            }
        }

        if(pathsLocationsParameter != null)
        {
            if(pathsLocationsParameter.length == 1)
            {
                for(int i = 0; i < pathsLocationsParameter[0].size(); i++)
                {
                    Location location = pathsLocationsParameter[0].get(i);
                    int index =  (location.getY() * mazeParameter.getSizeX()) + location.getX();
                    locationPanels[index].setBackground(Color.YELLOW);
                }
            }
            else
            {
                for(int i = 0; i < pathsLocationsParameter.length; i++)
                {
                    for(int j = 0; j < pathsLocationsParameter[i].size(); j++)
                    {
                        Location location = pathsLocationsParameter[i].get(j);
                        int index =  (location.getY() * mazeParameter.getSizeX()) + location.getX();
                        locationPanels[index].setBackground(Color.ORANGE);
                    }
                }
            }
        }

        this.mazePanel.repaint();
    }

    public void showAlert(String stringParameter)
    {
        JOptionPane.showMessageDialog(null, stringParameter);
    }

    public void printStageInfo(String stringParameter)
    {
        this.stagesTextArea.append(stringParameter + "\n");
    }

    public void addOnClickActionListenerToLoadMazeButton(ActionListener actionListenerParameter)
    {
        this.loadMazeButton.addActionListener(actionListenerParameter);
    }

    public void addOnClickActionListenerToStartButton(ActionListener actionListenerParameter)
    {
        this.startButton.addActionListener(actionListenerParameter);
    }

    public void setStartButtonEnabled(boolean enabledParameter)
    {
        this.startButton.setEnabled(enabledParameter);
        this.delaySpinner.setEnabled(enabledParameter);
        this.populationSizeSpinner.setEnabled(enabledParameter);
        this.visualizeCheckBox.setEnabled(enabledParameter);
    }

    public int getDelaySpinnerValue()
    {
        return (int)(double)this.delaySpinner.getValue();
    }

    public int getPopulationSizeSpinnerValue()
    {
        return (int)(double)this.populationSizeSpinner.getValue();
    }

    public boolean isVisualizeCheckboxChecked()
    {
        return this.visualizeCheckBox.isSelected();
    }

    public void resetStagesTextArea()
    {
        this.stagesTextArea.setText(null);
    }
}
