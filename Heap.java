/*
file name:      Heap.java
Authors:        Robbie Bennett
last modified:  11/18/2024
Class: CS231
Purpose: Provides the blueprint for a heap data structure.  (does not need to be run.)
*/

import java.util.Comparator;

public class Heap<T> implements PriorityQueue<T> {

    //Declaring variables.
    private T[] array;
    private int size;
    private Comparator<T> comparator;

    public Heap(Comparator<T> comparator){
        //Constructor.

        this.comparator = comparator;
        size = 0;
        array = makeArray(16);
    }

    private T[] makeArray(int capacity){
        //Creates an array of size capacity.

        return(T[]) new Object[capacity];
    }

    @Override
    public void offer(T item) {
        //Offer method.

        if(size == array.length){
            T[] oldArray = array;
            array = makeArray(size*2);
            for(int i = 0; i <oldArray.length; i++){
                array[i] = oldArray[i];
            }
        }
        array[size] = item;
        bubbleUp(size);
        size++;
    }

    private int parent(int index){
        //Returns the parents of the object at index.

        return (index -1) / 2;
    }

    private void swap(int index1, int index2){
        //Swaps to objects at both indexes. 

        T copy = array[index1];
        array[index1] = array[index2];
        array[index2] = copy;
    }

    private void bubbleUp(int index){
        //Bubble up method.

        if(index == 0){
            return;
        }

        int result = comparator.compare(array[index], array[parent(index)]);
        
        if (result <0){

            swap(index, parent(index));
            bubbleUp(parent(index));
        }
    }

    @Override
    public int size() {
       //Returns the size.

        return size;
    }

    @Override
    public T peek() {
        //Simple peak method for the first object of the heap. 

        return array[0];
    }

    @Override
    public T poll() {
        //Poll method. 

        T out = array[0];
        swap(0, size-1);
        array[size -1] = null;
        size--;
        sinkDown(0);
        return out;
    }  

    private int getLeftChildIdx( int idx ){
        //Returns the left child of the object at the specified index.

        int leftChild = 2* idx +1;
        return leftChild;
    }

    private int getRightChildIdx( int idx ){
        //Returns the right child of the object at the specified index.

        int rightChild = 2* idx +2;
        return rightChild;
    }

    private int getParentIdx(int idx) {
        if (idx == 0) {
            return -1; // Root has no parent
        }
        return (idx - 1) / 2; // Formula to find the parent index
    }

    private void sinkDown(int index){
        //Sink down method.

        int leftChild = getLeftChildIdx(index);
        int rightChild = getRightChildIdx(index);
        int smallest = index;

        if (leftChild < size && comparator.compare(array[leftChild], array[smallest]) < 0){
            smallest = leftChild;
        }

        if (rightChild < size && comparator.compare(array[rightChild], array[smallest]) < 0){
            smallest = rightChild;  
        }

        if (smallest != index){
            swap(index, smallest);
            sinkDown(smallest);
        }
    }

    public String toString() {
        //To string method. 

        int depth = 0 ;
        return toString( 0 , depth );
    }
    
    private String toString( int idx , int depth ) {
        //To string method. 

        if (idx >= this.size() ) {
            return "";
        }
        String left = toString(getLeftChildIdx( idx ) , depth + 1 );
        String right = toString(getRightChildIdx( idx ) , depth + 1 );

        String myself = "\t".repeat(depth) + this.array[idx] + "\n";
        return right + myself + left;
    }

    @Override
    public void updatePriority(T item){
        //Updates the priortiy of the item in the heap. 

        int index = 0;

        for(int i = 0; i< size; i++){
            if(array[i] == item){
                index = i;
                break;
            }
        }
        bubbleUp(index);
        sinkDown(index);
    }
}