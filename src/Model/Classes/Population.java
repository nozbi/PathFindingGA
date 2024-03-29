package Model.Classes;

import java.util.Stack;

import Model.Structs.Location;

public class Population 
{
    private Individual[] individuals;

    public Population(int sizeParameter, Maze mazeParameter)
    {
        this.individuals = new Individual[sizeParameter];
        for(int i = 0; i < this.individuals.length; i++)
        {
            this.individuals[i] = new Individual(mazeParameter);
        }
        this.sort(this.individuals, 0, this.individuals.length - 1);
    }

    private void sort(Individual individualsParameter[], int firstIndexParameter, int lastIndexParameter)
    {
        if (firstIndexParameter < lastIndexParameter) 
        {
            int middleIndex = firstIndexParameter + (lastIndexParameter - firstIndexParameter) / 2;
            sort(individualsParameter, firstIndexParameter, middleIndex);
            sort(individualsParameter, middleIndex + 1, lastIndexParameter);
            Individual leftIndividuals[] = new Individual[middleIndex - firstIndexParameter + 1];
            Individual rightIndividuals[] = new Individual[lastIndexParameter - middleIndex];
            for (int i = 0; i < leftIndividuals.length; i++)
            {
                leftIndividuals[i] = individualsParameter[firstIndexParameter + i];
            }
            for (int i = 0; i < rightIndividuals.length; i++)
            {
                rightIndividuals[i] = individualsParameter[middleIndex + 1 + i];
            } 
            int leftIndividualsIndex = 0;
            int rightIndividualsIndex = 0;
            int mergedIndividualsIndex = firstIndexParameter;
            while ((leftIndividualsIndex < leftIndividuals.length) && (rightIndividualsIndex < rightIndividuals.length)) 
            {
                if (leftIndividuals[leftIndividualsIndex].getFitness() <= rightIndividuals[rightIndividualsIndex].getFitness()) 
                {
                    individualsParameter[mergedIndividualsIndex] = leftIndividuals[leftIndividualsIndex];
                    leftIndividualsIndex++;
                }
                else 
                {
                    individualsParameter[mergedIndividualsIndex] = rightIndividuals[rightIndividualsIndex];
                    rightIndividualsIndex++;
                }
                mergedIndividualsIndex++;
            }
            while (leftIndividualsIndex < leftIndividuals.length) 
            {
                individualsParameter[mergedIndividualsIndex] = leftIndividuals[leftIndividualsIndex];
                leftIndividualsIndex++;
                mergedIndividualsIndex++;
            }
            while (rightIndividualsIndex < rightIndividuals.length) 
            {
                individualsParameter[mergedIndividualsIndex] = rightIndividuals[rightIndividualsIndex];
                rightIndividualsIndex++;
                mergedIndividualsIndex++;
            }
        }
    }

    public void nextGeneration()
    { 
        int individualForCrossoverIndex = 0;
        Individual[] newIndividuals = new Individual[this.individuals.length];
        for(int i = 0; i < newIndividuals.length; i++)
        { 
            newIndividuals[i] = new Individual(this.individuals[i], this.individuals[individualForCrossoverIndex]);
            individualForCrossoverIndex++;
            if(individualForCrossoverIndex > newIndividuals.length / 2)
            {
                individualForCrossoverIndex = 0;
            }
        }
        this.individuals = newIndividuals;
        this.sort(this.individuals, 0, this.individuals.length - 1);
    }

    @SuppressWarnings("unchecked")
    public Stack<Location>[] getPathsLocations()
    {
        Stack<Location>[] pathsLocations = new Stack[this.individuals.length];
        for(int i = 0; i < pathsLocations.length; i++)
        {
            pathsLocations[i] = this.individuals[i].getPathLocations();
        }
        return pathsLocations;
    }

    public boolean isPathFound()
    {
        for(int i = 0; i < this.individuals.length; i++)
        {
            if(this.individuals[i].isPathFound())
            {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public Stack<Location>[] getFoundPathLocations()
    {
        for(int i = 0; i < this.individuals.length; i++)
        {
            if(this.individuals[i].isPathFound())
            {
                return new Stack[]{this.individuals[i].getPathLocations()};
            }
        }
        return null;
    }

    public double getFitness()
    {
        return this.individuals[0].getFitness();
    }
}
