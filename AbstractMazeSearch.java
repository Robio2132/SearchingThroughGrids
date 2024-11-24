/*
file name:      AbstractMazeSearch.java
Authors:        Robbie Bennett
last modified:  11/18/2024
Class: CS231
Purpose: Provides abstract methods for maze searching.  (does not need to be run.)
*/

import java.awt.Graphics;
import java.awt.Color;

public abstract class AbstractMazeSearch {

    //Declaring variables
    private Maze maze;
    protected Cell start;
    protected Cell target;
    protected Cell cur;
    private int exploredCells;

    public AbstractMazeSearch(Maze maze) {
        //Constructor. 

        this.maze = maze;
        this.start = null;
        this.target = null;
        this.cur = null;
        this.exploredCells=0;
    }

    public abstract Cell findNextCell();
    //Abstract method for findNextCell.

    public abstract void addCell(Cell next);
    //Abstract method for addCell.

    public abstract void updateCell(Cell next);
    //Abstract method for update Cell.

    public abstract int numRemainingCells();
    //Abstract method for find the number of remaining cells in the maze. 

    public Maze getMaze() {
        // Returns the underlying Maze.

        return this.maze;
    }

    public Cell getTarget() {
        // returns the target of the search.

        return this.target;
    }

    public void setCur(Cell cell) {
        // sets the given cell to be the current location of the search.

        this.cur = cell;
    }

    public Cell getCur() {
        // Returns the current Cell location of the search.

        return this.cur;
    }

    public Cell getStart() {
        // Returns the start of the search.

        return this.start;
    }

    public void reset() {
        // Sets the current, start, and target Cells to be null

        this.cur = null;
        this.start = null;
        this.target = null;
        this.exploredCells=0;
    }

    public LinkedList<Cell> traceback(Cell cell) {
        //Finds a path from the start to the specified cell by repeatedly following the prev path.


        Cell curCell = cell;
        LinkedList<Cell> path = new LinkedList<>();

        while (curCell != null) {
            path.add(curCell);
            if (curCell == this.start) {
                return path; // we've completed the path from the start to the specified cell
            }
            curCell = curCell.getPrev();
        }
        return null; // we weren't able to find a path, so we return null
    }

    public LinkedList<Cell> search(Cell start, Cell target, boolean display, int delay) throws InterruptedException {
        // the method that actually does the searching. (Comments are centered arround the psuedo code from the method description.)

        this.start = start;
        this.target = target;
        MazeSearchDisplay viz = null;
        if (display == true) {
            viz = new MazeSearchDisplay(this, 20);
        }

        setCur(start);

        // This line is just to make the drawing work correctly
        start.setPrev(start);

        // add the starting cell to the set of Cells to explore
        addCell(start);

        while (numRemainingCells() > 0) { // there are still Cells left to explore
            // set Cur to be the next Cell from the Cells to explore (findNextCell())
            setCur(findNextCell());
            if (display) {
                Thread.sleep(delay);
                viz.repaint();
            }
            for (Cell neighbor : maze.getNeighbors(cur)) { // each neighboring Cell of cur

                if (neighbor.getPrev() == null) { // this neighbor hasn't been visited before (if its prev is null)
                    neighbor.setPrev(cur); // set this neighbor's prev to be cur
                    addCell(neighbor); // add this neighbor to the future Cells to explore
                    exploredCells++; 

                } else if (traceback(cur).size() + 1 < traceback(neighbor).size()) { // we've found a faster path to
                                                                                     // this neighbor (hint: use
                                                                                     // traceback)){
                    neighbor.setPrev(cur); // set this neighbor's prev to be cur
                    updateCell(neighbor); // update this neighbor in our search structure
                }

                if (neighbor == target) { // this neighbor is the target
                    exploredCells++; 
                    return traceback(target); // we found the target, we're done
                }
            }
            System.out.println("Number of Cells explored: " + exploredCells);
        }
        System.out.println("Number of Cells explored: " + exploredCells);
        return null; // we couldn't find the target, but we're done
    }

    public void draw(Graphics g, int scale) {
        // Draws the base version of the maze

        getMaze().draw(g, scale);
        // Draws the paths taken by the searcher
        getStart().drawAllPrevs(getMaze(), g, scale, Color.RED);
        // Draws the start cell
        getStart().draw(g, scale, Color.BLUE);
        // Draws the target cell
        getTarget().draw(g, scale, Color.RED);
        // Draws the current cell
        getCur().draw(g, scale, Color.MAGENTA);

        // If the target has been found, draws the path taken by the searcher to reach
        // the target sans backtracking.
        if (getTarget().getPrev() != null) {
            Cell traceBackCur = getTarget().getPrev();
            while (!traceBackCur.equals(getStart())) {
                traceBackCur.draw(g, scale, Color.GREEN);
                traceBackCur = traceBackCur.getPrev();
            }
            getTarget().drawPrevPath(g, scale, Color.BLUE);
        }
    }
}