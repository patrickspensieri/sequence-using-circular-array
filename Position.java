package SequenceA2;

/**
 * Position implementation. 
 * 
 * Patrick Anthony Spensieri - 40006417
 * COMP 352 - Assignment 2
 * @author p_spensi
 */
public class Position implements PositionADT{
    protected int element;
    protected int index;
    public Position(int element, int index){
        this.element = element;
        this.index = index;
    }
        
    @Override
    public int getElement(){
        return element;
    }
    
    public String toString(){
        return ("index " + index + ", element " + element);
    }
    
}
