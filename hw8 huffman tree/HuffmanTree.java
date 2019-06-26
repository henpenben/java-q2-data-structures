/*
   Henry Hough
   6 June 2019
   CSE143BL
   TA: Zachary Keyes
   Assignment #8: Huffman Tree
   
   This class is manages operations from classes MakeCode and Decode. Also uses output from Encode.
   Allows creation of Huffman trees, writing them to files, loading them from files, and decoding
   encoded files made from Huffman trees.
*/

import java.util.Scanner;
import java.util.Queue;
import java.util.PriorityQueue;
import java.io.PrintStream;

public class HuffmanTree
{
    private HuffmanNode overallRoot;
    
/*===========================================PART ONE============================================*/

    //creates a HuffmanTree using given array of character frequencies
    public HuffmanTree(int[] count)
    {
        Queue<HuffmanNode> queue = constructQueue(count);        
        queue.add(new HuffmanNode(count.length, 1));
        while(queue.size() != 1)
        {
            constructTreeFromQueue(queue);
        }
        this.overallRoot = queue.remove();
    }
    
    //helper method for HuffmanTree(int[])
    //takes input (int[] count), returns a PriorityQueue of HuffmanNodes made of (count)'s elements
    private Queue<HuffmanNode> constructQueue(int[] count)
    {
        Queue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>();
        for(int i = 0; i < count.length; i++)
        {
            if(count[i] > 0)
            {
                queue.add(new HuffmanNode((char)i, count[i]));
            }
        }
        return queue;
    }
    
    //helper method for HuffmanTree(int[])
    //constructs a binary tree by removing the first two nodes from the given (queue)
    //  and placing the removed nodes on left & right of a new node
    //puts this new node back into the queue
    private void constructTreeFromQueue(Queue<HuffmanNode> queue)
    {
        HuffmanNode root;
        if(queue.size() > 1)
        {
            root = new HuffmanNode(queue.remove(), queue.remove());
        }
        else
        {
            root = new HuffmanNode(queue.remove(), null);
        }
        if(root != null)
        {
            queue.add(root);
        }
    }
    
    //writes current tree to given (PrintStream output) in the standard format
    public void write(PrintStream output)
    {
        write(output, overallRoot, "");
    }
    
    //helper method for write()
    //recursively traverses tree to write to (PrintStream output) in standard format
    private void write(PrintStream output, HuffmanNode root, String code)
    {
        if(root != null)
        {
            if(root.characterCode != -1)
            {
                output.println(root.characterCode);
                output.println(code);
            }
            write(output, root.left, code + "0");
            write(output, root.right, code + "1");
        }
    }
    
/*===========================================PART TWO============================================*/

    //creates a HuffmanTree using Scanner (input) linked to a file filled with output of write()
    public HuffmanTree(Scanner input)
    {
        overallRoot = new HuffmanNode(null, null); //addToTree() needs a starting point
        while(input.hasNextLine())
        {
            int characterCode = Integer.parseInt(input.nextLine());
            String codePath = input.nextLine();
            HuffmanNode leaf = new HuffmanNode(characterCode);
            overallRoot = addToTree(codePath, overallRoot, leaf);
        }
    }
    
    //helper method for HuffmanTree(Scanner)
    //recursively adds (leaf) to (root) tree
    //adds at position (path) traced from (root)
    private HuffmanNode addToTree(String path, HuffmanNode root, HuffmanNode leaf)
    {
        if(path.isEmpty())
        {//with no more path to follow, finally return the leaf
            return leaf;
        }
        else if(path.charAt(0) == '0') //0 is left, 1 is right
        {//follow the path
            if(root.left == null)
            {//if no node is already at the next step of the path, create a dummy node
                root.left = new HuffmanNode(null, null);
            }
            //recurse
            root.left = addToTree(path.substring(1), root.left, leaf);
        }
        else//if(path.charAt(0) == '1')
        {
            if(root.right == null)
            {
                root.right = new HuffmanNode(null, null);
            }
            
            root.right = addToTree(path.substring(1), root.right, leaf);
        }
        return root;
    }
    
    //decodes encoded file using (input) and current tree and writes it to (output)
    //stops reading at (endOfFileCode) (and does not write the eof to (output))
    //assumes valid (input) and that no more content exists past (endOfFileCode)
    public void decode(BitInputStream input, PrintStream output, int endOfFileCode)
    {
        int x = 0;
        HuffmanNode root = overallRoot;
        while(x != -1)
        {//while readBit() is not past the end of the file
            if(root.left != null && root.right != null)
            {//while root != a leaf node
                x = input.readBit();
                if(x == 0)
                {//0 is left, move to left
                    root = root.left;
                }
                else//if(x == 1)
                {//1 is right, move to right
                    root = root.right;
                }
            }else
            {
                if(root.characterCode != endOfFileCode)
                {//don't want to write the eof code
                    output.write(root.characterCode);
                    //break; //would stop potential for endless loop when given invalid input
                }
                root = overallRoot;
            }
        }
        input.close();
    }
}