/*
   Henry Hough
   6 June 2019
   CSE143BL
   TA: Zachary Keyes
   Assignment #8: Huffman Tree
   
   This class is the node for the HuffmanTree class' binary tree. Holds two ints,
   a character code (characterCode) and sometimes the (frequency) of that character
*/

public class HuffmanNode implements Comparable<HuffmanNode>
{
    public int         characterCode; //when not applicable: -1
    private int        frequency;     //when not applicable: -1
    //private because it does not actually ever need to be accessed; only here for Comparable<E>
    //should it be public anyways since its a node class?
    public HuffmanNode left;
    public HuffmanNode right;
    
    //constructs a branch HuffmanNode, placing the two input HuffmanNodes below it
    public HuffmanNode(HuffmanNode left, HuffmanNode right)
    {
        this.characterCode = -1;
        this.frequency = 0;
        if(left != null)
        {
            this.frequency += left.frequency;
            this.left = left;
        }
        if(right != null)
        {
            this.frequency += right.frequency;
            this.right = right;
        }
    }
    
    //constructs a leaf HuffmanNode, with no frequency
    public HuffmanNode(int characterCode)
    {
        this(characterCode, -1);
    }
    
    //constructs a leaf HuffmanNode with a characterCode and frequency
    public HuffmanNode(int characterCode, int frequency)
    {
        this.characterCode = characterCode;
        this.frequency = frequency;
    }
    
    //implements Comparable<HuffmanNode>
    //considered 'greater than' when frequency of this > frequency of other
    public int compareTo(HuffmanNode other)
    {
        return this.frequency - other.frequency;
    }
}