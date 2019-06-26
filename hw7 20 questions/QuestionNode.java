/*
   Henry Hough
   27 May 2019
   CSE143BL
   TA: Zachary Keyes
   Assignment #7: 20 Questions
   
   This class contains the node used in QuestionTree.java. Holds a reference to a left branch, 
   a right branch, a type (question: "Q:" or answer: "A:"), and a String of data
*/

public class QuestionNode
{
    public String type;
    public String data;
    public QuestionNode left;
    public QuestionNode right;    
     
    //constructs a new QuestionNode leaf with given Strings (type) & (data)
    //Should throw IllegalArgumentException when passed an invalid type but it wasn't part of spec
    public QuestionNode(String type, String data)
    {
//      if(!(type.equals("A:") || type.equals("Q:")))
//      {
//          throw new IllegalArgumentException("Type can only be \"A:\" or \"Q:\"!");
//      }
        this.type = type;
        this.data = data;
        this.left = null;
        this.right = null;
    }
}