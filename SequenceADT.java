package SequenceA2;

/**
 * NodeList Abstract Data Type.
 * 
 * Patrick Anthony Spensieri - 40006417
 * COMP 352 - Assignment 2
 * @author p_spensi
 */
public interface SequenceADT 
{
    static final int INITIAL_CAPACITY = 10;
    static final int CONSTANT_GROWTH = 100;
    static final int RELATIVE_GROWTH = 2;
    static final double MAX_SIZE_RATIO = 0.8;
    
    /**
     * Returns Position of the first element.
     * @return Position of the first element.
     * @throws Exception if list is empty
     */
    public Position first() throws Exception;
    
    /**
     * Returns Position of the last element.
     * @return Position of the last element.
     * @throws Exception if list is empty.
     */
    public Position last() throws Exception;
    
    /**
     * Returns the position preceeding the current position.
     * @param p current position
     * @return previous position
     * @throws Exception if no previous
     */
    public Position prev(Position p) throws Exception;
    
    /**
     * Returns the position following the current position.
     * @param p current position
     * @return following position
     * @throws Exception null if no next
     */
    public Position next(Position p) throws Exception;
    
    /**
     * Sets the element of a given position.
     * @param p position
     * @param e new element
     * @return old element
     */
    public int set(Position p, int e);
    
    /**
     * Adds a position to the front of the list.
     * @param e element
     * @return the new position
     */
    public Position addFirst(int e);
    
    /**
     * Adds a position to the end of the list.
     * @param e element
     * @return new position
     */
    public Position addLast(int e);
    
    /**
     * Adds a position before the given position.
     * @param p current position
     * @param e new element
     * @return new position
     */
    public Position addBefore(Position p, int e);
    
    /**
     * Adds a position after the given position.
     * @param p current position
     * @param e new element 
     * @return new position
     */
    public Position addAfter(Position p, int e);
    
    /**
     * Deletes a given position.
     * @param p position to be deleted
     * @return deleted element
     */
    public int delete(Position p);
    
    /**
     * Swaps two given positions.
     * @param p1 position 1
     * @param p2 position 2
     */
    public void swap(Position p1, Position p2);
    
    /**
     * Truncates the underlying array's size to fit the exact amount of positions.
     */
    public void truncate();
    
    /**
     * Sets the current expansion rule used for growing the array.
     * @param c d = relative, c = constant growth
     */
    public boolean setExpansionRule(char c);
    
    /**
     * Returns the index of a given position.
     * @param p given position
     * @return index
     */
    public int indexOf(Position p);
    
    /**
     * Returns the position residing at a given index, null otherwise.
     * @param i index
     * @return position at index
     */
    public Position atIndex(int i);
}