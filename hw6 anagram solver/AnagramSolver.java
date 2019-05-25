/* 
   Henry Hough
   23 April 2019
   CSE143BL
   TA: Zachary Keyes
   Assignment #6: AnagramSolver
   
   This program is designed to let you make anagrams of input words using a given dictionary
*/

import java.util.*;

public class AnagramSolver
{
<<<<<<< HEAD
    private Map<String, LetterInventory> dictionaryMap;
    private List<String> dictionary;
=======
    private Map<String, LetterInventory> wordMap;
    private List<String> wordList;
    //could have only one field if LinkedHashMap was used
>>>>>>> a3719a58815f59562e102798a479122913286167
    
    //creates an AnagramSolver object with an input List<String> that is used as the dictionary
    //to "solve" anagrams with. words can only be rearranged to words present in the dictionary
    //list should not contain duplicates and should not be empty
    public AnagramSolver(List<String> list)
    {
        this.wordList = list;
        this.wordMap = new HashMap<String, LetterInventory>();

        for(String s : list)
        {
            wordMap.put(s, new LetterInventory(s));
        }
    }
    
    /*
    * prints to System.out all combinations of words from wordList that can be made from
    * anagrams of (input). will only anagram-ify into (maxCount) number of words
    * if (maxCount) is input as 0, will anagram-ify into any possible number(s) of word(s)
    * if (maxCount) is input as less than 0, throws IllegalArgumentException
    */
    public void print(String input, int maxCount)
    {
        if(maxCount < 0)
        {
            throw new IllegalArgumentException("max must be >=0!");
        }
        LetterInventory inputInv = new LetterInventory(input);
        List<String> availableDictionary = new ArrayList<String>(); //ArrayList for get() perf.
        for(String word : this.wordList) 
        {//must iterate over wordList and not wordMap.keySet() to preserve order of words
            if(inputInv.subtract(this.wordMap.get(word)) != null)
            {
                availableDictionary.add(word);
            }
        }
        if(maxCount == 0)
        { //allows for infinite number of words per printed set
            maxCount = -1;
        }
        print(inputInv, maxCount, new LinkedList<String>(), availableDictionary);
    }
    
    /*
    * assumes initial inputs of non-empty LetterInventory, maxCount >= 0, an empty List<String>
    * output, and a List<String> availableDictionary full of possible words
    * adds a word to (output) from (availableDictionary) if (inventory) contains the word's letters
    * if input (LetterInventory inventory) is empty, prints the input: (List<String> output)
    */
    private void print(LetterInventory inventory, int maxCount, 
                       List<String> output,       List<String> availableDictionary)
    {
        if(inventory.isEmpty())
        {//when all the letters in the inventory have been used up, impossible to make more words
            System.out.println(output);
        }
        else
        {
            for(int i = 0; i < availableDictionary.size(); i++)
            {//for each word left in the available dictionary
                String currWord = availableDictionary.get(i);
                LetterInventory currInv = this.wordMap.get(currWord);
                LetterInventory newInv = inventory.subtract(currInv);
                if(newInv != null)
                {//if removing this word is possible
                    output.add(currWord);//add this word to output list
                    if(maxCount > 0)
                    {
                        print(newInv, maxCount - 1, output, availableDictionary);
                    }else if(maxCount == -1) //when user did not request a limit to number of words
                    {
                        print(newInv, maxCount,     output, availableDictionary);
                    }
                    output.remove(currWord);
                }
            }
        }
    }
}