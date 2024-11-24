/*
file name:      MazeBreadthFirstSearch.java
Authors:        Robbie Bennett
last modified:  11/12/2024
How to run:    does not need to be run. 
Purpose: Solves a maze using the Breadth First Search algorithm.
*/

public class MazeBreadthFirstSearch extends AbstractMazeSearch {

    //Declaring variables.
    private Queue<Cell> cellQueue;
    
    public MazeBreadthFirstSearch(Maze maze){
    //Constructor.
        
        super(maze);
        this.cellQueue = new LinkedList<>();
    }

    @Override
    public Cell findNextCell() {
    //Finds the next cell.

        return cellQueue.poll();
    }

    @Override
    public void addCell(Cell next) {
    // Adds the next cell
        
        cellQueue.offer(next);  
    }
    
    @Override
    public void updateCell(Cell next) {
    //Updates the cell. 
        
        cellQueue.offer(next);
    }

    @Override
    public int numRemainingCells() {
        //Returns the number of remaining cells. 

        return cellQueue.size();
    } 
}