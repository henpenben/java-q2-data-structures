/*
   Henry Hough
   27 May 2019
   CSE143BL
   TA: Zachary Keyes
   Assignment #7: 20 Questions
   
   This class handles playing a 20-questions style game with the user. Uses a tree of questions to
   guess what the user might be thinking about, and learns new things from the user when it's wrong
*/

import java.util.Scanner;
import java.io.PrintStream;

public class QuestionTree
{
    private QuestionNode overallRoot;
    private Scanner console;
    
    //makes a new QuestionTree, containing just "computer" as an answer
    public QuestionTree()
    {
        console = new Scanner(System.in);
        overallRoot = new QuestionNode("A:", "computer");
    }
    
    //takes input (Scanner input)
    //replaces current tree with one read from (input)
    //assumes (input) is legally formatted (QuestionNode write() output)
    public void read(Scanner input)
    {
        overallRoot = readReturn(input);
    }

    //takes input (Scanner input)
    //creates and returns a binary tree by reading from (input)
    //assumes (input) is legally formatted (QuestionNode write() output)
    private QuestionNode readReturn(Scanner input)
    {
        if(input.hasNextLine())
        {
            String type = input.nextLine();
            String data = input.nextLine();
            QuestionNode root = new QuestionNode(type, data);
            if(type.equals("Q:"))
            {
                root.left  = readReturn(input);
                root.right = readReturn(input);
            }
            return root;
        }
        return null;
    }
    
    //prints to input (PrintStream output) a preoreder traversal of the current tree
    //writes with format "type\ndata" for each branch / leaf
    //assumes given PrintStream is open for writing   
    public void write(PrintStream output)
    {
        write(output, overallRoot);
    }
    
    //prints to input (PrintStream output) a preorder traversal of input (QuestionNode root)
    private void write(PrintStream output, QuestionNode root)
    {
        if(root != null)
        {
            output.println(root.type);
            output.println(root.data);
            write(output, root.left);
            write(output, root.right);
        }
    }
    
    //uses current tree to ask questions about the object the user is thinking about
    //continues to ask until it either guesses the object, or it fails
    //if it fails to guess the object, prompts user to add it to the tree
    public void askQuestions()
    {
        overallRoot = askQuestions(overallRoot);
    }
    
    //helper method for askQuestions()
    //manages asking questions until failure/success, and adding new questions to the tree  
    private QuestionNode askQuestions(QuestionNode root)
    {
        if(root.type.equals("Q:"))
        {
            if(yesTo(root.data))
            {
                root.left = askQuestions(root.left);
            }
            else
            {
                root.right = askQuestions(root.right);
            }
        }
        else if(yesTo("Would your object happen to be " + root.data + "?"))
        {
            System.out.println("Great, I got it right!");
        }
        else
        {   
            System.out.print("What is the name of your object? ");
            QuestionNode newAnswer = new QuestionNode("A:", console.nextLine());
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.print  ("and mine--> ");
            QuestionNode newQuestion = new QuestionNode("Q:", console.nextLine());
            root = addQuestion(root, newQuestion, newAnswer, 
                               yesTo("And what is the answer for your object?"));
        }
        return root;
    }
    
    //helper method for askQuestions(QuestionNode root)
    //takes input QuestionNodes (root), (newQuestion), (newAnswer)
    //returns small tree with (root) & (newAnswer) as branches of (newQuestion)
    //branch arrangement depends on (answer)
    private QuestionNode addQuestion(QuestionNode root, QuestionNode newQuestion, 
                                     QuestionNode newAnswer, boolean answer)
    {
        if(answer)
        {
            newQuestion.left = newAnswer;
            newQuestion.right = root;
        }else
        {
            newQuestion.left = root;
            newQuestion.right = newAnswer;
        }
        return newQuestion;
    }
    
    //post: asks the user a question, forcing an answer of "y" or "n";
    //returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt)
    {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n"))
        {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}