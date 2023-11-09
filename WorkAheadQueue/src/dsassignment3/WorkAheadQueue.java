/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsassignment3;

import java.util.ArrayList;

/**
 * Modified version of a queue.
 * Specifically works for double linked lists.
 * adds the ability of being able to look at the first three nodes at the beginning of the queue.
 * adds the ability of being able to look at the first three elements(data)  at the beginning of the queue.
 * without removing them.
 * @author zandercowan
 */
public class WorkAheadQueue<T> implements WorkAheadQueueADT<T>{
    protected LinearNode<T> front;
    protected LinearNode<T> back;
    protected ArrayList<LinearNode<T>> frontThreeNodes;
    protected ArrayList<T> frontThreeElements;
    protected int numNodes;

  
    
    public WorkAheadQueue() {
        //set the size of frontThreeNodes and frontThreeElements to be 3
        frontThreeNodes = new ArrayList(); 
        frontThreeElements = new ArrayList(); 
        
        // fill them both with null elements
        
      
    }
    
    @Override
    /**
     * Adds the specified element e to the back of the queue.
     * @param T (a element wanted to be added to the queue)
     */
    public void enqueue(T element) {
        
        LinearNode<T> newNode = new LinearNode(element); 
        LinearNode<T> temp;

        if (numNodes == 0){
            front = newNode;
            back = newNode;
            this.numNodes++;
            this.updatefrontThreeNodesAndfrontThreeElements();
            return;
        }
        

        temp = back;
        temp.setNext(newNode);
        newNode.setPrev(temp);
        back = newNode;
        this.numNodes++;
        this.updatefrontThreeNodesAndfrontThreeElements();
        return;

        
        

      
        
    }

    
    /**
     * Removes and returns the element that is at the front of the queue.
     * @return T (The element that was removed)
     * @throws EmptyCollectionException (if there are no elements in the queue)
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        // if there are no elements in the queue throw an exception.
        if (this.isEmpty() == true) {throw new EmptyCollectionException("theres nothing in the queue");}
        
        LinearNode<T> temp;

        // there's only one element in the list.
        if (this.size() == 1){
            temp = front;
            front = null;
            back = null;
            this.numNodes--;
            this.updatefrontThreeNodesAndfrontThreeElements();
            return temp.getElement();
        }
        // if there's more than one node
        // point at the first, set front to be the next node after first
        // decrement the size. reutrn the element of the node removed.
        temp = front;
        front = front.getNext();
        this.numNodes--;
        this.updatefrontThreeNodesAndfrontThreeElements();
        return temp.getElement();
   
    }
    
    /**
     * Removes and returns the element that is at index n in the queue where n < 3.
     * @param x (a index in the queue where x < 3) (ASSUME THAT INCIDES IN THE ARRAY START FROM 0)
     * @return T (The element that was removed)
     * @throws EmptyCollectionException (if the queue is less than 3)
     * @throws InvalidArgumentException (if the index given is less than the length of the queue)
     * NOTE: ASSUME THAT THE BEGINNING INDEX STARTS AT 0 NOT 1
     */
    public T dequeue(int x) throws EmptyCollectionException, InvalidArgumentException {
        if (numNodes == 0) {throw new EmptyCollectionException("size of queue is 0");}
        if (x < 0){throw new InvalidArgumentException("x is negative");}
        if (this.numNodes - 1 < x){throw new InvalidArgumentException("index given is greater than the length of the queue");}
        
        LinearNode<T> temp;
        
        // if the index is 0, call dequeue()
        if (x == 0 ){ return this.dequeue();}
        
        
        // if the index  x given is equal to size - 1 of the list
        // its the last element
        // delete it like you would delete the last element in a linked list
        if (x == numNodes - 1){
            temp = back;
            back = temp.getPrev();
            numNodes--;
            this.updatefrontThreeNodesAndfrontThreeElements();
            return temp.getElement();
        }
        
        // if the index is neither of the 2 things
        // go to the index and delete it like it was the middle of the list.   
        temp = this.search(x);
        
        LinearNode<T> tempPrev = temp.getPrev();
        LinearNode<T> tempNext = temp.getNext();
        tempPrev.setNext(tempNext);
        tempNext.setPrev(tempPrev);
        numNodes--;
        this.updatefrontThreeNodesAndfrontThreeElements();
        return temp.getElement();
        
    
    }
    
    /**
     * Returns (without removing) the element that is at the front of the queue.
     * Returns (T the element at the front of the queue)
     */
    @Override
    public T first() throws EmptyCollectionException {   
        if (numNodes == 0) {throw new EmptyCollectionException("size of queue is 0");}
        return front.getElement();
    }   
    
    
    /**
     * Returns (without removing) the element that is at index n in the queue where n < 3.
     * @param x (a index in the queue where x < 3)
     * @return (T Element that's at the X index)
     */
    @Override
    public T first(int x) throws EmptyCollectionException, InvalidArgumentException {
        if (numNodes == 0) {throw new EmptyCollectionException("size of queue is 0");}
        if (x < 0){throw new InvalidArgumentException("x is negative");}
        if (this.numNodes - 1 < x){throw new InvalidArgumentException("index given is greater than the length of the queue");} 
        
        LinearNode<T> temp;
        temp = this.search(x);
        return temp.getElement();   
    }
    
    
    /**
     * Returns an ArrayList of the first three NODES in the queue*.
     * @return ArrayList<LinearNode<T>> (a ArrayList of the first three nodes in the queue)
     * @throws EmptyCollectionException if there are less than three nodes in the queue
     */
    @Override
    public ArrayList<LinearNode<T>> firstThreeNodes() throws EmptyCollectionException {
        return frontThreeNodes;
    }
    
    
    
    /**
     * Returns an ArrayList of the first three ELEMENTS in the queue*.
     * @return ArrayList<LinearNode<T>> (a ArrayList of the first three elements in the queue)
     * @throws EmptyCollectionException if there are less than three elements in the queue
     */
    @Override
    public ArrayList<T> firstThreeElements() throws EmptyCollectionException {
        return frontThreeElements;
    }
    
    
    
    /**
     * Checks if there are no elements in the queue.
     * @return Boolean (True there are no elements in the queue) (False if there is elements)
     */
    @Override
    public boolean isEmpty() {
        return (numNodes == 0);
    }
    
    
    
    /**
     * Returns the number of elements in the queue.
     * @return (int number of elements in the queue)
     */
     
    @Override
    public int size() {
        return numNodes;
    }
    
    /**
     * searches to the ith index inside the linked list.
     * @param i (the position specified)
     * @returns LinearNode<T> (the node that the index is given at)
     * NOTE: this method doesnt not take into account for any test cases. Make sure to handle them outside of the method when you use it.
     */
    private LinearNode<T> search(int i){
        LinearNode<T> temp = front;
        int tempCounter = 0;
        while (tempCounter < i){
            temp = temp.getNext();
            tempCounter++;
        }
        return temp;
        
    }
    
    
    /**
     * Updates the frontThreeNodes and frontThreeElements array lists given the size of the queue.
     * Code could have been written in the other classes, but I chose to make it its own method because for reusability.
     * NOTE: USE at the very end of the methods right before the return statements. 
     */
    
    private void updatefrontThreeNodesAndfrontThreeElements(){
        
        LinearNode<T> temp = front;
        
        // wipe both clean 
        // this is to take in account for when you .remove()
        frontThreeNodes.clear();
        frontThreeElements.clear();
        
        
       
        int tempCounter = 0;
        // when .next() is not null, and size < 2 
        while (temp != null && tempCounter < 3){
            frontThreeNodes.add(temp); // get the node and add it to the frontThreeNode
            frontThreeElements.add(temp.getElement()); //get the noes  contents, stick them into frontThreeElements
            temp = temp.getNext();
            tempCounter++;
            
        }
        
    }
    


        
    
   /**
     * Returns a string representation of the queue.
     * @return String (a string representation of the queue)
     */         
    @Override
    public String toString() {
        if (numNodes == 0){return "";}
        LinearNode<T> temp = front;
        if (numNodes == 1){return temp.getElement().toString();}
        
        String output = temp.getElement().toString();
      
        int tempCounter = 0;
        temp = temp.getNext();
        while (temp != null && numNodes != 1){
            output = output + ", " + temp.getElement().toString();
           temp = temp.getNext();
        }
        return output;
    }
    
    
    
}
