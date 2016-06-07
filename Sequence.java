package SequenceA2;

/**
 * Sequence implementation.
 * 
 * Patrick Anthony Spensieri - 40006417
 * COMP 352 - Assignment 2
 * @author p_spensi
 */
public class Sequence implements SequenceADT
{   
    private Position[] list;            //Position array
    private int f;                      //array index to first position
    private int l;                      //array index to lst position
    private int size = 0;               //numeber of positions
    private boolean relativeExpansion = true;   //type of expansion
    
    /**
     * Default constructor.
     * Sets the first and last indices to appropriate values.
     */
    public Sequence(){
        list = new Position[INITIAL_CAPACITY];      //instantiate to intitial capacity
        f = l = list.length/2;      //set the front and last pointer in the middle of the array
        ++f;                        //increment front
    }
    
    /**
     * Returns true if list is empty, false otherwise.
     * @return boolean
     */
    private boolean isEmpty(){
        return (size == 0);
    }
    
    /**
     * Returns true is size is above the threshold size.
     * @return boolean
     */
    private boolean thresholdExceeded(){
        return (size > (MAX_SIZE_RATIO*list.length));
    }
    
    /**
     * Returns the first position, null if empty.
     * @return first position
     */
    public Position first(){
        if(isEmpty())
            return null;
        return list[f];
    }
    
    /**
     * Returns the last position, null if empty.
     * @return last position
     */
    public Position last(){
        if(isEmpty())
            return null;
        return list[l];
    }
    
    /**
     * Returns the previous position. Null if current position is the head of list.
     * @param n current position
     * @return previous position
     */
    public Position prev(Position n){
        if(n.index == f)
            return null;
        return list[n.index - 1];
    }
    
    /**
     * Returns the next position. Null if current is the tail of list.
     * @param n current position.
     * @return next position
     */
    public Position next(Position n){
        if(n.index == l)
            return null;
        return list[n.index + 1];
    }
   
    /**
     * Sets a given position's element.
     * @param n position
     * @param e new element
     * @return old element
     */
    public int set(Position n, int e){
        int temp = n.getElement();      //hold old element
        n.element = e;                  //set new element
        return temp;                    //return old element
    }
    
    /**
     * Adds a new position to the beginning of the list.
     * @param e new position's element.
     * @return new position
     */
    public Position addFirst(int e){
        f = prevIndex(f);               //move first pointer leftward
        list[f] = new Position(e, f);   //create new position
        incSize();                      //increment size (method expands if necessary)
        return list[f];                 //return new position

    }
    
    /**
     * Adds a new position to the end of the list.
     * @param e new position's element
     * @return new position
     */
    public Position addLast(int e){
        l = nextIndex(l);               //move last pointer rightward
        list[l] = new Position(e, l);   //create new position
        incSize();                      //increment size (method expands if necessary)
        return list[l];                 //return new position
    }
    
    /**
     * Returns true if there are more positions to the left of the specified 
     * position than there are on the right. False otherwise.
     * @param n position
     * @return boolean
     */
    private boolean moreToLeft(Position n){
        //if(more positions to the left of n)
        //      return true
        //      else return false
        return ((n.index - f)%size > (l - n.index)%size);
    }
    
    /**
     * Adds a position before the specified position.
     * @param n position following the new one being inserted
     * @param e new position's element
     * @return new position
     */
    /*
    Algorithm addBefore(Position n, integer e)
        IF(n.index equals first)
            return addFirst(e);
        ENDIF
        IF(more positions to the left of n than the right)
            shift n and all elements to the right rightward;
            create new position (index = index before n);
            return new position;
        ELSE
            shift all positions to the left of n leftward;
            create new position (index = index before n);
            return new position;
        ENDIF
    END
    */
    public Position addBefore(Position n, int e){
        if(n.index == f)
            return addFirst(e);
        
        if(moreToLeft(n)){
            shiftRight(n);
            list[prevIndex(n.index)] = new Position(e, prevIndex(n.index));
            incSize();
            return list[n.index];
        }
        else{
            shiftLeft(list[prevIndex(n.index)]);
            list[prevIndex(n.index)] = new Position(e, prevIndex(n.index));
            incSize();
            return list[prevIndex(n.index)];
        }     
    }
    
    /**
     * Adds a position after a specified position.
     * @param n position preceding the new one being inserted
     * @param e new position's element
     * @return new position
     */
    /*
    Algorithm addAfter(Position n, integer e)
        IF(n.index is last)
            return addLast(n);
        ENDIF
        IF(more positions to the left of n than the right)
            shift all elements to the right of n righward;
            create new position (index = index after n);
            return new position;
        ELSE
            shift n and all positions to the left leftward;
            create new position (index = index after n);
            return new position;
        ENDIF
    END
    */
    public Position addAfter(Position n, int e){
        if(n.index == l)        
            return addLast(e);
        if(moreToLeft(n)){
            shiftRight(list[nextIndex(n.index)]);
            list[nextIndex(n.index)] = new Position(e, nextIndex(n.index));
            incSize();
            return list[nextIndex(n.index)];
        }
        else{
            shiftLeft(n);
            list[nextIndex(n.index)] = new Position(e, nextIndex(n.index));
            incSize();
            return list[nextIndex(n.index)];
        }
    }
    
    /**
     * Deletes the given position by calling most efficient helper method.
     * @param n position to be deleted
     * @return element of deleted position
     */
    /*
    Algorithm delete(Position n)
        IF(more elements to the left of n than to the right)
            return deleteShiftLeft(n); (helper method)
        ELSE
            return deleteShiftRight(n); (helper method)
    END
    */
    public int delete(Position n){
        if(moreToLeft(n))
            return deleteShiftLeft(n);
        return deleteShiftRight(n);
    }
    
    /**
     * Swaps the two given positions.
     * @param p1 position 1
     * @param p2 position 2
     */
    /*
    Algorithm swap(Position p1, Position p2)
        SET position at p1.index to p2;
        SET position at p2.index to p1;
        
    */
    public void swap(Position p1, Position p2){
        list[p1.index] = p2;
        list[p2.index] = p1;
        int temp = p1.index;
        p1.index = p2.index;
        p2.index = temp;
    }
    
    /**
     * Truncates the array to hold the exact number of positions currently contained.
     */
    /*
    Algorithm truncate()
        DECLARE new Position array temp;
        FOR(int i = 0, to size-1)
            temp[i] = current[index after (first + i - 1)];
            temp[i].index = i;
        ENDFOR
        set current to temp;
        first = 0;
        last = size-1;
    END
    */
    public void truncate(){
        Position[] temp = new Position[size];
        for(int i = 0; i < size; i++){
            temp[i] = list[nextIndex(f+i-1)];
            temp[i].index = i;
        }
        list = temp;
        f = 0;
        l = size-1;
    }
    
    /**
     * Sets the current expansion rule used for growing the array.
     * @param c d = relative, c = constant growth
     */
    /*
    Algorithm setExpansionRule(char c)
        IF(c equals 'd')
            relativeExpansion = true;
        ELSE IF(c equals 'c')
            relativeExpansion = false;
        ELSE
            print "invalid input";
        ENDIF
        return relativeExpansion;
    END
    */
    public boolean setExpansionRule(char c){
        switch (c) {
            case 'd':
                relativeExpansion = true;
                break;
            case 'c':
                relativeExpansion = false;
                break;
            default:
                System.out.println("invalid input");
                break;
        }
        return relativeExpansion;
    }
    
    /**
     * Grows the array relative to its current size.
     * newCapacity = oldCapacity*growthFactor
     * @return grown array capacity
     */
    /*
    Algorithm expand()
        IF(relativeExpansion)
           DECLARE new Position array newArray with length = size*RELATIVE_GROWTH;
        ELSE
           DECLARE new Position array newArray with length = size + CONSTANT_GROWTH;
        ENDIF
        DECLARE int start = 0.5*currentArray.length - 0.5*size;
        FOR(int i = 0 to size-1)
            newArray[start + i] = currentArray[index after(f+i-1)];
            newArray[start + i].index = start + i;
        ENDFOR
        first = start;
        last = first + size - 1;
        return length of newArray;
    END
    */
    private int expand(){
        Position arr[];
        if(relativeExpansion)
            arr = new Position[size*RELATIVE_GROWTH];
        else
            arr = new Position[size + CONSTANT_GROWTH];
        //starting at index 0
        int i = f;
        int count = 0;
        while(count < size){
                arr[count] = list[i];
                arr[count].index = count;
                i = nextIndex(i);
                count++;
        }
        f = 0;
        l = size - 1;
        list = arr;
        return list.length;
    }
    
    /**
     * Shifts Position n and all elements before it leftward by one index.
     * @param n 
     */
    /*
    Algorithm shiftLeft(Position n)
        DECLARE new Position temp (index, element = n);
        DECLARE integer i = first;
        WHILE(i != index after temp.index)
            list[index before i] = list[i];
            list[index before i].index = index before i;
            i = index after i;
        ENDWHILE
        Call incSize() (method will expand if necessary)
        first = index before first;
    END
    */
    private void shiftLeft(Position n){
        Position temp = new Position(n.element, n.index);
        for(int i = f; i != nextIndex(temp.index); i = nextIndex(i)){
            list[prevIndex(i)] = list[i];
            list[prevIndex(i)].index = prevIndex(i);
        }
//        incSize();
        f = prevIndex(f);
    }
    
    /**
     * Shifts Position n and all elements after it rightward by one index.
     * @param n 
     */
    /*
    Algorithm shiftRight(Position n)
        DECLARE new Position temp (index, element = n);
        DECLARE integer i = last;
        WHILE(i != index before(temp.index))
            list[index after i] = list[i];
            list[index after i].index = index after i;
            i = index before i;
        ENDWHILE
        last = index before last;
        call incSize() (method will expand if necessary)
        return temp.element;
    END
    */
    private int shiftRight(Position n){
        Position temp = new Position(n.element, n.index);
        for(int i = l; i != prevIndex(temp.index); i = prevIndex(i)){
            list[nextIndex(i)] = list[i];
            list[nextIndex(i)].index = nextIndex(i);
        }
        l = nextIndex(l);
//        incSize();
        return temp.element;
    }
    
    /**
     * Deletes Position n by shifting elements leftward by one index.
     * Preferred to deleteShiftRight() when there are more positions
     * to the right of n.
     * @param n position to be deleted
     * @return deleted element
     */
    /*
    Algorithm deleteShiftRight(Position n)
        DECLARE new Position temp (index, element of n);
        DECLARE integer i = temp.index;
        WHILE(i != first)
            list[i] = list[index before i];
            list[i].index = i;
            i = index before i;
        ENDWHILE
        SET list[first] to null;
        first = index after first;
        size = size -1;
        return temp.element;
    END
    */
    private int deleteShiftRight(Position n){
        Position temp = new Position(n.element, n.index);
        for(int i = temp.index; i != f; i = prevIndex(i)){
            list[i] = list[prevIndex(i)];
            list[i].index = i;
        }
        list[f] = null;
        f = nextIndex(f);
        size--;
        return temp.element;
    }
    
    /**
     * Deletes Position n by shifting elements rightward by one index.
     * Preferred to deleteShiftLeft() when there are more positions
     * to the left of n.
     * @param n position to be deleted
     * @return deleted element
     */
    /*
    Algorithm deleteShiftRight(Position n)
        DECLARE new Position temp (index, element of n);
        DECLARE integer i = temp.index;
        WHILE(i != last)
            list[i] = list[index after i];
            lsit[i].index = i;
            i = index after i;
        ENDWHILE
        SET list[last] to null;
        last = index before last;
        size = size - 1;
        return temp.element;
    END
    */
    private int deleteShiftLeft(Position n){
        Position temp = new Position(n.element, n.index);
        for(int i = temp.index; i != l; i = nextIndex(i)){
            list[i] = list[nextIndex(i)];
            list[i].index = i;
        }
        list[l] = null;
        l = prevIndex(l);
        size--;
        return temp.element;
    }
    
    /**
     * Returns the previous index. Required for circular array.
     * @param i initial index
     * @return previous index
     */
    /*
    Algorithm prevIndex(int i)
        IF(i equals 0)
            return list.length - 1;
        ELSE return i-1;
        ENDIF
    END
    */
    private int prevIndex(int i){
        if(i == 0)
            return list.length - 1;
        return i-1; 
    }
    
    /**
     * Returns the next index. Required for circular array.
     * @param i initial index
     * @return next index
     */
    /*
    Algorithm nextIndex(int i)
        IF(i equals list.length - 1)
            return 0;
        ELSE return i+1;
        ENDIF
    END
    */
    private int nextIndex(int i){
        if(i == list.length - 1)
            return 0;
        return i+1;
    }
    
    /**
     * Prints the list, including node elements and indices.
     */
    protected void print(){
        System.out.println("\tf = " + f + ", l = " + l);
        System.out.println("\tsize : " + size + ", length : " + list.length);
//        for(int i = f; (i != nextIndex(l)); i = nextIndex(i))
        int i = f;
        int count = 0;
        while(count < size){
                System.out.println("\tlist["+i+"] : index " + list[i].index + " and value = " + list[i].element);
                i = nextIndex(i);
                count++;
        }
        
    }
    
    /**
     * Increments the size count of the list and expands array if necessary. 
     * User should avoid incrementing by modifying variable directly as the structure
     * will not verify if the threshold has been exceeded.
     * @return new size
     */
    /*
    Algorithm incSize()
        size = size + 1;
        IF(threshold is exceeded)
            expand();
        ENDIF
    END
    */
    private int incSize(){
        size++;
        if(thresholdExceeded())
            expand();
        return size;
    }
    
    /**
     * Returns the index of a given position.
     * @param p given position
     * @return index of position
     */
    public int indexOf(Position p){
        return p.index;
    }
    
    /**
     * Returns the position at a given index, null otherwise.
     * @param i index
     * @return position
     */
    public Position atIndex(int i){
        if(i > size - 1){
            System.out.println("Invalid index.");
        }
        return list[i];
    }
}
