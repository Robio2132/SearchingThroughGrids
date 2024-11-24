/*
file name:      LinkedList.java
Authors:        Robbie Bennett
last modified:  10/6/2024
Class: CS231
How to run:     Does not need to be run. 
Purpose: Provides funcitonality to the Linked List structure. 
*/

import java.util.Iterator;

public class LinkedList<T> implements Iterable<T>, Queue<T>, Stack<T> {

    private class Node {

        // Creating variables for the node class.
        private T data;
        private Node next;

        public Node(T item) {
            // Creating constructor.

            this.data = item;
            this.next = null;
        }

        public T getData() {
            // Returns the data of the node.

            return data;
        }

        public void setNext(Node n) {
            // Sets the next node.

            this.next = n;
        }

        public Node getNext() {
            // Returns the next node.

            return next;
        }

    }

    // Variables for the Linked List constructor.
    private Node head;
    private Node tail;
    private int size;

    public LinkedList() {
        // Constructor for LinkedList

        this.head = null;
        this.size = 0;
        this.tail = null;
    }

    public int size() {
        // Returns the size of the LinkedList.

        return size;
    }

    public void clear() {
        // Clears the linked list.

        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        // Checks if size of the linked list is 0.

        return size == 0;
    }

    public String toString() {
        // Returns a string representation of the list

        StringBuilder result = new StringBuilder();
        Node current = head;
        while (current != null) {
            result.append(current.getData()).append(" -> ");
            current = current.getNext();
        }
        result.append("null");
        return result.toString();
    }

    public void add(T item) {
        // Adds an item (T) to the linked list.

        Node newNode = new Node(item);

        if (head == null) {
            // If empty, set both head and tail to the new node
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head = newNode;
        }
        size++;
    }

    public boolean contains(Object o) {
        // Returns true if the Object is in the list.

        for(T item: this){
            if (o == item){
                return true;
            }
        } 
        return false;
    }

    public T get(int index) {
        // Returns the object in the linked list at the specified index.

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        if (index == size) {
            this.getLast(); /// Check this.
        }

        return current.getData();
    }

    public T remove() {
        // Removed the head of the data.

        T removedData = head.getData();
        head = head.getNext();
        size--;

        if (head == null) {
            tail = null;
        }

        return removedData;
    }

    public void addLast(T data) {
        //Adds data to the list of nodes. 

        Node newNode = new Node(data);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public void add(int index, T item) {
        // Adds a specified item to the index list at the specidied index.

        if (index < 0) {
            return;
        }
        if (index > size) {
            index = size;
        }

        Node newNode = new Node(item);

        if (index == 0) {
            add(item);
        } else if (index == size) {
            this.addLast(item); /// Check this.
        } else {

            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }

            newNode.setNext(current.getNext());
            current.setNext(newNode);
            size++;
        }

    }

    public T remove(int index) {
        // Removes the item at the specified index.

        if (index == 0) {
            return remove();
        }
        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.getNext();
        }
        T removedData = current.getNext().getData(); // Get the data to remove
        current.setNext(current.getNext().getNext()); // Bypass the node to remove
        size--; // Decrement size

        if (index == size - 1) {

            this.removeLast(); /// Check this.
            size--;
        }

        return removedData; // Return the removed data

    }

    public T removeFirst() {
        if (head == null) {
            return null; // List is empty
        }

        T data = head.data;
        head = head.next;

        if (head == null) {
            tail = null; // List is now empty
        }
        size--;
        return data;
    }

    // ... Node class ...

    public T removeLast() {
        //Removes the last object of the data structure.

        if (size == 0) {
            return null;
        }

        if (size == 1) {
            T oneValue = head.data;
            clear();
            return oneValue;
        }

        Node current = head;

        System.out.println("The Error is Here " + current.data);
        while (current.next != tail) {
            current = current.next;

        }
        T originalTail = tail.data;
        tail = current;
        tail.next = null;

        size--;
        return originalTail;
    }

    public T getLast() {
        return tail.data;
    }

    private class LLIterator implements Iterator<T> {

        // Declaring variables for the LLIterator class.
        private Node current;

        public LLIterator(Node head) {
            // Constructor for the LLIterator.

            this.current = head;
        }

        public boolean hasNext() {
            // Returns true if there is a next value in the list.

            if (this.current != null) {
                return true;
            }
            return false;
        }

        public T next() {
            // Returs a copy of the next item.

            T copy = this.current.getData();
            current = current.next;
            return copy;
        }

        public void remove() {
            // Does nothing.

            System.out.println("Does Nothing. ");
        }
    }

    public Iterator<T> iterator() {
        // Returns a new iterator.

        return new LLIterator(this.head);
    }

    @Override
    public void offer(T item) {
        //Offer method.

        add(size(), item);
    }

    @Override
    public T peek() {
        //Peek method.

        if (size == 0) return null;
        return get(0);
    }

    @Override
    public T poll() {
        //Poll method.

        return removeFirst();
    }

    public T pop(){
        //Pop method.

        if (!isEmpty()){
            T data = head.data;
            head = head.next;
            size--;
            
            if(this.head == null){ //Checks if the list is empty after pop shrunk it. 
                this.tail = null;
            }
            return data;
        }
        return null;
    }

    public void push(T item){
        //Push Method.

        add(item);
    }
}