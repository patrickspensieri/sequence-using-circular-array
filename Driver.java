package SequenceA2;

/**
 * Driver class for Assignment 2. Tests the Sequence implementation.
 * 
 * Patrick Anthony Spensieri - 40006417
 * COMP 352 - Assignment 2
 * @author p_spensi
 */
public class Driver {

    public static void main(String[] args) 
    {
        System.out.println("NOTE : Uncomment appropriate lines to conduct desried test.");
        //test Sequence implementation
        testSequence();
        
        //test expansion methods
        testExpansionMethods();
        
        //time expansion methods
        timeExpansionMethods();
    }
    
    /**
     * Tests the Sequence implementation.
     */
    private static void testSequence(){
        Sequence list = new Sequence();     //create a new Sequence
        System.out.println("TEST addFirst(), addLast(), addBefore() and addAfter() methods : ");
        System.out.println("\tShould be 10, 2, 1, 7, 8, 3, 4, 5, 9, 6, 11");
        Position p1 =  list.addFirst(1);
        Position p2 = list.addFirst(2);
        Position p3 = list.addLast(3);
        Position p4 = list.addLast(4);
        Position p5 = list.addLast(5);
        Position p6 = list.addLast(6);
        Position p7 = list.addAfter(p1, 7);
        Position p8 = list.addBefore(p3, 8);
        Position p9 = list.addAfter(p5, 9);
        Position p10 = list.addBefore(p2, 10);
        Position p11 = list.addAfter(p6, 11);
        list.print();
        
        System.out.println("\nTEST the delete() method by deleting p9 and p2 :");
        System.out.println("Should be 10, 1, 7, 8, 3, 4, 5, 6, 11");
        list.delete(p9);
        list.delete(p2);
        list.print();
        
        System.out.println("\nTEST the truncate() method : ");
        System.out.println("First position index should begin at 0");
        list.truncate();
        list.print();
        
        System.out.println("\nTEST the swap() method : ");
        System.out.println("p1 : index " + p1.index + ", element " + p1.element);
        System.out.println("p6 : index " + p6.index + ", element " + p6.element);
        System.out.println("Swap p1 and p6");
        list.swap(p1, p6);
        list.print();
        
        System.out.println("\nTEST the first() and last() method : ");
        System.out.println("\tfirst() : " + list.first());
        System.out.println("\tlast() : " + list.last());
        
        System.out.println("\nTEST the set() method : ");
        System.out.println("Calling set(p10, 100) : ");
        list.set(p10, 100);
        list.print();
    }
    
    /**
     * Tests the functionality of both expansion methods, ensuring that they are working correctly.
     */
    private static void testExpansionMethods(){
        System.out.println("\nTEST both expansion methods by creating two lists and " +
                "\nexecuting same operations on both the lists.");
        
        Sequence constantList = new Sequence();     //list with constant growth
        constantList.setExpansionRule('c');
        Sequence relativeList = new Sequence();     //list with relative growth
        relativeList.setExpansionRule('d');
        
        //populate both the lists using the same methods, test size of 50 elements
        constantList = testExpansionMethodsHelper(constantList, 50);
        System.out.println("\nPRINT constantList :");
        constantList.print();
        System.out.println("\nPRINT relativeList :");
        relativeList = testExpansionMethodsHelper(relativeList, 50);
        relativeList.print();
    }
    
    /**
     * Tests the runtime performance of both expansion methods.
     */
    private static void timeExpansionMethods(){
        int testSize = 50000;
        System.out.println("\nTIME both expansion methods by creating two lists with size of " + testSize +  
                ", executing same operations on both the lists.");
        Sequence constantList = new Sequence();
        constantList.setExpansionRule('c');
        Sequence relativeList = new Sequence();
        relativeList.setExpansionRule('d');
        
        
        long startTime = System.currentTimeMillis();
        constantList = testExpansionMethodsHelper(constantList, testSize);
        long elapsedTimeConstant = System.currentTimeMillis() - startTime;
        constantList = null;
        startTime = System.currentTimeMillis();
        relativeList = testExpansionMethodsHelper(relativeList, testSize);
        long elapsedTimeRelative = System.currentTimeMillis() - startTime;
        
        System.out.println("\n\tRESULTS : " + "\n\tconstant growth value = " + SequenceADT.CONSTANT_GROWTH);
        System.out.println("\trelative growth value = " + SequenceADT.RELATIVE_GROWTH);
        System.out.println("\n\tSIZE of Sequences = " + testSize);
        System.out.println("\tTIME for ConstantList (ms) = " + elapsedTimeConstant);
        System.out.println("\tTIME for RelativeList (ms) = " + elapsedTimeRelative);
        
    }
    
    /**
     * Helper method for testing the functionality of the expansion methods.
     * @param list list being tested
     * @param testSize size of list
     * @return populated and expanded list
     */
    private static Sequence testExpansionMethodsHelper(Sequence list, int testSize){
        for(int i = 0; i < testSize; i++){
            if(i%3 == 0)
                list.addFirst(i);
            else if (i%3 == 1)
                list.addLast(i);
            else  //(i%3 == 2)
                list.addAfter(list.first(), i);
        }
        return list;
    }
    
    
    /**
     * Prints the attributes of a given position.
     * @param p the position
     * @return String with information
     */
    private static String printPos(Position p){
        return("index : " + p.index + ", value : " + p.element);
    }

}