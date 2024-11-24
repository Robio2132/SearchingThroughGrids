/*
file name:      MazeDepthLimitedSearch.java
Authors:        Robbie Bennett
last modified:  11/12/2024

How to run:    does not need to be run. 
Purpose: Solves a maze using the Depth Limited Search algorithm.
*/

public class MazeDepthLimitedSearch extends AbstractMazeSearch {

    //Declaring variables.
    private Stack<Cell> stack;

    public MazeDepthLimitedSearch(Maze maze){
        //Constructor. 

        super(maze);
        this.stack = new LinkedList<>();
    }

    @Override
    public Cell findNextCell() {
        //Finds the next cell.

        return stack.pop();
    }

    @Override
    public void addCell(Cell next) {
        //Adds the next cell
        
        stack.push(next);
    }

    @Override
    public void updateCell(Cell next) {
        //Updates the next cell. 
        
        stack.push(next);
    }

    @Override
    public int numRemainingCells() {
        // Returns the number of remaining cells. 
        
        return stack.size();
    }
}