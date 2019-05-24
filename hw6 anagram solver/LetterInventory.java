/* 
   Henry Hough
   5 April 2019
   CSE143BL
   TA: Zachary Keyes
   Assignment #1: LetterInventory
   
   This program is designed to allow various operations on a LetterInventory object, which keeps
   track of an inventory of alphabeticals in a given string
*/

public class LetterInventory
{
    public static final int ALPHABET = 26;
    private int[] charCounts;
    private int size;
    
    //pre: no arguments
    //post: creates empty inventory 
    public LetterInventory()
    {
        charCounts = new int[ALPHABET];
    }
    
    //pre: a string is given on creation
    //post: creates case-insensitive inventory that counts each occurence of letter a-z in data
    public LetterInventory(String data)
    {
        charCounts = new int[ALPHABET];
        for(int i = 0; i < data.length(); i++)
        {
            char thisChar = data.toLowerCase().charAt(i);
            if(thisChar >= 'a' && thisChar < 'a' + ALPHABET) //alphabet characters only
            {
                charCounts[thisChar - 'a']++;
                size++;
            }
        }
    }
    
    //pre: letter is alphabetic (throws IllegalArgumentException if not)
    //post: returns count of letter in the inventory
    public int get(char letter)
    {
        char inputChar = Character.toLowerCase(letter);
        checkInputChar(inputChar);
        return charCounts[inputChar - 'a'];
    }
    
    //pre: letter is alphabetic && value > 0 (throws IllegalArgumentException if not)
    //post: sets count of letter to value in the inventory, ignoring case of letter
    public void set(char letter, int value)
    {
        if(value < 0)
        {
            throw new IllegalArgumentException("Value must be >= 0. Your value : " + value);
        }
        checkInputChar(letter);
        int charDifference = Character.toLowerCase(letter) - 'a';
        size += value - charCounts[charDifference];
        charCounts[charDifference] = value;
    }
    
    //post: returns sum of counts in the inventory
    public int size()
    {
        return size;
    }
    
    //post: returns false unless inventory is empty
    public boolean isEmpty()
    {
        return size() == 0;
    }
    
    //post: returns string representation of the inventory, surrounded by brackets
    public String toString()
    {
        String outputString = "";
        for(int i = 0; i < ALPHABET; i++)
        {
            for(int j = 0; j < charCounts[i]; j++)
            {
                outputString += (char) ('a' + i);
            }
        }
        return "[" + outputString + "]";
    }
    
    //post: returns new inventory that's the sum of this inventory and another inventory
    public LetterInventory add(LetterInventory other)
    {
        //LetterInventory sum = new LetterInventory(this.toString() + other.toString());
        //writeup states it must add counts, but above is definitely easier
        LetterInventory sum = new LetterInventory();
        for(int i = 0; i < ALPHABET; i++)
        {
            char letter = (char) ('a' + i);
            sum.set(letter, this.get(letter) + other.get(letter));
        }
        return sum;
    }
    
    //post: returns new inventory that's the difference of the this inventory and another inventory
    //returns null if subtraction of any count is less than 0
    public LetterInventory subtract(LetterInventory other)
    {
        LetterInventory difference = new LetterInventory();
        for(int i = 0; i < ALPHABET; i++)
        {
            char letter = (char) ('a' + i);
            int value = this.get(letter) - other.get(letter);
            if(value < 0)
            {
                return null;
            }
            difference.set(letter, value);
        }
        return difference;
    }
    
    //helper method, 
    private void checkInputChar(char inputChar)
    {
        if(Character.toLowerCase(inputChar) < 'a' || 
           Character.toLowerCase(inputChar) >= 'a' + ALPHABET)
        {
            throw new IllegalArgumentException("Input must be part of the alphabet. Your input: "
                                               + inputChar);
        }    
    }
}