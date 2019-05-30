import java.util.*;
import java.io.*;

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
    //reads tree from scanner and adds it to root
    //assumes scanner contains legal, formatted node text
    public void read(Scanner input)
    {
        overallRoot = readHelper(input);
    }

    //x = change(x) is hard

//     private void read(QuestionNode root, Scanner input)
//     {
//         if(input.hasNextLine() && root != null && root.type.equals("Q:"))
//         {
//             if(root.left == null)
//             {   
//                 root.left = new QuestionNode(input.nextLine(), input.nextLine());
//                 read(root.left, input);
//             }
//             if(root.right == null && input.hasNextLine())
//             {//have to check for next line again, since don't always have left && right branch
//                 root.right = new QuestionNode(input.nextLine(), input.nextLine());
//                 read(root.right, input);
//             }
//         }
//     }
//

    //takes input (Scanner input)
    //reads tree from scanner and adds it to root
    //assumes scanner contains legal, formatted node text in preorder format
    private QuestionNode readHelper(Scanner input)
    {
        if(input.hasNextLine())
        {
            String type = input.nextLine();
            String data = input.nextLine();
            QuestionNode root = new QuestionNode(type, data);
            if(type.equals("Q:"))
            {
                root.left = readHelper(input);
                root.right = readHelper(input);
            }
            return root;
        }
        return null;
    }
    
    //writes a text version of the tree to given (PrintStream output)
    public void write(PrintStream output)
    {
        write(output, overallRoot);
    }
    
    //prints to input (PrintStream output) a pre-order traversal of input (QuestionNode root)
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
    
    public void askQuestions()
    {
        askQuestions(overallRoot, "");
    }
    
//     private void askQuestions(QuestionNode root, String path)
//     {
//         if(root.type.equals("Q:"))
//         {
//             if(yesTo(root.data))
//             {
//                 path += "y";
//                 askQuestions(root.left, path);
//             }else
//             {
//                 path += "n";
//                 askQuestions(root.right, path);
//             }
//         }else
//         {
//             if(yesTo("would your object happen to be " + root.data))
//             {
//                 System.out.println("Great, I got it right!");
//             }else
//             {
//                 System.out.print("What is the name of your object? ");
//                 String object = console.nextLine();
//                 System.out.println("Please give me a yes/no question that");
//                 System.out.println("distinguishes between your object");
//                 System.out.print("and mine--> ");
//                 String question = console.nextLine(); 
//                 boolean answer = yesTo("And what is the answer for your object?");
//                 change(overallRoot, path, answer, question, object, 0);
//             }
//         }
//     }
    
    private QuestionNode add(QuestionNode root, QuestionNode newQuestion, 
    
//     private void change(QuestionNode root, String path, boolean b, String q, String a, int i)
//     {
//         if(path.length() == 0)
//         {
//             QuestionNode newQ = new QuestionNode("Q:", q);
//             QuestionNode newA = new QuestionNode("A:", a);
//             if(b)
//             {
//                 newQ.left = newA;
//                 newQ.right = root;
//             }else
//             {
//                 newQ.left = root;
//                 newQ.right = newA;
//             }
//             overallRoot = newQ;
//         }else if(path.length() - i == 1)
//         {
//             QuestionNode newQ = new QuestionNode("Q:", q);
//             QuestionNode newA = new QuestionNode("A:", a);
//             if(path.charAt(path.length() - 1) == 'y')
//             {
//                 if(b)
//                 {
//                     newQ.left = newA;
//                     newQ.right = root.left;
//                 }else
//                 {
//                     newQ.left = root.left;
//                     newQ.right = newA;
//                 }
//                 root.left = newQ;
//                 
//             }else
//             {
//                 if(b)
//                 {
//                     newQ.left = newA;
//                     newQ.right = root.right;
//                 }else
//                 {
//                     newQ.left = root.right;
//                     newQ.right = newA;
//                 }
//                 root.right = newQ;
//             }
//         }else
//         {
//             if(path.charAt(i) == 'y')
//             {
//                 change(root.left, path, b, q, a, i+1);
//             }else
//             {
//                 change(root.right, path, b, q, a, i+1);
//             }
//         }
//     }
    
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