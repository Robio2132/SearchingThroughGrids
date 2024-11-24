/*
file name:      MazeUCS.java
Authors:        Robbie Bennett
last modified:  11/12/2024
How to run:    does not need to be run. 
Purpose: Solves a maze using the UCS Search algorithm. (extension.)
*/

import java.util.Comparator;
import java.util.PriorityQueue;

public class MazeUCS extends AbstractMazeSearch {

    //Declaring variables.
    private PriorityQueue<Cell> priorityQueue;

    public MazeUCS(Maze maze) {
        //Constructor. 
        super(maze);

        //Creating the comparator. 
        Comparator<Cell> comparator = new Comparator<Cell>() {
            public int compare(Cell cell1, Cell cell2) {

                // Compare based on the cost to reach each cell
                int cost1 = cell1.getCost();
                int cost2 = cell2.getCost();
                if (cost1 > cost2) {
                    return 1;
                } else if (cost1 < cost2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        this.priorityQueue = new PriorityQueue<>(comparator);
    }

    @Override
    public Cell findNextCell() {
        // Finds the next cell. 

        return priorityQueue.poll();
    }

    @Override
    public void addCell(Cell next) {
        // Adds the next cell. 
        
        priorityQueue.offer(next);
    }

    @Override
    public void updateCell(Cell next) {
        // UCS does not need to update priority.
    }

    @Override
    public int numRemainingCells() {
        // Return the number of cells remaining. 

        return priorityQueue.size();
    }

    @Override
    public LinkedList<Cell> search(Cell start, Cell target, boolean display, int delay) throws InterruptedException {
        //Updated search method.

        this.start = start;
        this.target = target;
        
        MazeSearchDisplay viz = null;
        if (display) {
            viz = new MazeSearchDisplay(this, 20);
        }

        setCur(start);
        start.setPrev(start);  
        start.setCost(0);  
        
        addCell(start);

        while (numRemainingCells() > 0) { 
            setCur(findNextCell());

            if (display) {
                Thread.sleep(delay);
                viz.repaint();
            }

            for (Cell neighbor : getMaze().getNeighbors(cur)) {
                if (neighbor.getPrev() == null || cur.getCost() + 1 < neighbor.getCost()) {
                    neighbor.setPrev(cur);  
                    neighbor.setCost(cur.getCost() + 1);  // Update the cost for this neighbor
                    addCell(neighbor); 
                }
            }

            if (cur.equals(target)) {
                return traceback(target);
            }
        }
        return null;
    }
}