/* 
 * Henry Hough
 *
 * 2 May 2019
 * CSE143BL
 * TA: Zachary Keyes
 * Assignment #4: Evil Hangman
 *  
 * This class is deisgned to contain the playing of a twisted version of hangman by calling
 * various methods about the state of the game or recording a new letter to guess
*/

import java.util.*;

public class HangmanManager
{
    private int guessesLeft;
    private String bestKey;
    private Set<String> availableDictionary;
    private Set<Character> charsGuessed;
    
    //requires that wordLength be greater than 1 and maxGuesses be greater than 0
    //will throw IllegalArgumentException if above is not followed
    //only uses unique (case insensitive), words from inputDictionary of length wordLength for game
    public HangmanManager(Collection<String> inputDictionary, int wordLength, int maxGuesses)
    {
        if(wordLength < 1 || maxGuesses < 0 /*|| inputDictionary.size() == 0*/)
        {
            throw new IllegalArgumentException("invalid arguments!");
        }
        guessesLeft = maxGuesses;
        charsGuessed = new TreeSet<Character>();
        availableDictionary = new TreeSet<String>();
        for(String word : inputDictionary)
        { //only add words of requested length to the available word set
            if(word.length() == wordLength)
            {
                availableDictionary.add(word);
            }
        }
        bestKey = "";
        for(int i = 0; i < wordLength; i++)
        { //initial best key will just be a key that fits the whole dictionary: all "-"
            bestKey += "-";
        }
    }
    
    //returns Set<String> of possible words currently available to HangmanManager
    public Set<String> words()
    {
        return availableDictionary;
    }
    
    //returns integer of how many guesses left the player is allowed
    public int guessesLeft()
    {
        return guessesLeft;
    }
    
    //returns Set<Character> of letters already guessed by the player
    public Set<Character> guesses()
    {
        return charsGuessed;
    }
    
    //returns a pattern representing what every word in words() has in common
    //fills in guessed letters and uses "-" for letters the player has not yet guessed
    //separates each character of the pattern from eachother with one character of whitespace
    //assumes words() is not empty. If it is: throws IllegalStateException
    public String pattern()
    {
        if(availableDictionary.isEmpty())
        {
            throw new IllegalStateException("no possible words!");
        }
        String pattern = String.valueOf(bestKey.charAt(0));
        for(int i = 1; i < bestKey.length(); i++)
        {
            pattern += " " + String.valueOf(bestKey.charAt(i));
        }
        return pattern;
    }
    
    //records a new guess made by the user
    //throws IllegalStateException if guessesLeft() returns < 1 or if words() returns empty
    //decrements guessesLeft if the guess is not contained in the new word pattern
    //changes words() return to the largest subset of words() that is still valid play
    //returns the count of times guess appears in the new word pattern
    public int record(char guess)
    {
        if(guessesLeft < 1 || availableDictionary.isEmpty())
        {
            throw new IllegalStateException("no guesses left or no possible guesses!");
        }
        if(charsGuessed.contains(guess))
        {
            throw new IllegalArgumentException("guess already made!");
        }
        
        charsGuessed.add(guess);
        Map<String, Set<String>> wordSets = new TreeMap<String, Set<String>>();
        mapWords(guess, wordSets); //create map of patterns (keys) to sets of words (values)

        for(String key : wordSets.keySet())
        { //find best key (the one with the largest set)
            if(!wordSets.containsKey(bestKey)
               ||wordSets.get(key).size() > wordSets.get(bestKey).size())
            {
                bestKey = key;
            }
        }
        availableDictionary = wordSets.get(bestKey);
        
        if(!bestKey.contains(String.valueOf(guess)))
        { //decrement guessesLeft if the guessed letter isn't in the new dictionary
            guessesLeft--;
            return 0;
        }
        int charCount = 0;
        for(int i = 0; i < bestKey.length(); i++)
        {
            if(bestKey.charAt(i) == guess)
            {
                charCount++;
            }
        }
        return charCount;
        
    }
    
    //helper method for record()
    //uses input char guess to map possible patterns(keys) to possible sets of words
    private void mapWords(char guess, Map<String, Set<String>> wordSets)
    {
        for(String word : availableDictionary)
        {
            String key = "";
            //for(char c : word.toCharArray())            :(
            for(int i = 0; i < word.length(); i++)
            {
                char c = word.charAt(i);
                if(charsGuessed.contains(c))
                { //only let the pattern contain letters the player has guessed
                    key += c;
                }else
                {
                    key += "-";
                }
            }
            addToMap(key, word, wordSets); //add the key to the map
        }
    }
    
    //helper method for mapWords()
    //places an input key and input String value onto an input map
    private void addToMap(String key, String word, Map<String, Set<String>> wordSets)
    {
        if(!wordSets.containsKey(key))
        { //create a new set with word if the map does not have this key yet
            Set<String> singleSet = new TreeSet<String>();
            singleSet.add(word);
            wordSets.put(key, singleSet);
        }else
        { //grab the existing set from the key and add word to it
            wordSets.get(key).add(word);
        }
    }

}