/*
file name:      MazeAStarSearch.java
Authors:        Robbie Bennett
last modified:  11/12/2024
How to run:    does not need to be run. 
Purpose: Solves a maze using the A* search algorithm.
*/

import java.util.Comparator;

public class MazeAStarSearch extends AbstractMazeSearch {

    //Declaring variables.
   PriorityQueue<Cell> priorityQueue;

    public MazeAStarSearch(Maze maze) {
        //Constructor.

        super(maze);

        //Creating the comparator. 
        Comparator<Cell> comparator = new Comparator<Cell>(){

            public int compare(Cell cell1, Cell cell2){

                //Distance 1.
                int cell1trace = traceback(cell1).size();
                int firstDistance = Math.abs(target.getRow() - cell1.getRow()) + Math.abs(target.getCol() - cell1.getCol());
                int rightPath1 = cell1trace + firstDistance; 
                
                //Distance 2.
                int cell2trace = traceback(cell2).size();
                int secondDistance = Math.abs(target.getRow() - cell2.getRow()) + Math.abs(target.getCol() - cell2.getCol());
                int rightPath2 = cell2trace + secondDistance; 
        
                //Comparosin. 
                if(rightPath1 > rightPath2){
                    return 1;
                } else if (rightPath1 < rightPath2){
                    return -1;
                } else{
                    return 0;
                }
            }
        };
        //Creating the priority queue. 
        this.priorityQueue = new Heap<Cell>(comparator);
    }

    @Override
    public Cell findNextCell() {
        //Finds the next cell.

        return priorityQueue.poll();
    }

    @Override
    public void addCell(Cell next) {
        // Adds the next cell

        priorityQueue.offer(next);
    }

    @Override
    public void updateCell(Cell next) {
       //Updates the cell. 

        priorityQueue.updatePriority(next);
    }

    @Override
    public int numRemainingCells() {
        //Returns the number of remaining cells. 

        return priorityQueue.size();
    }
}