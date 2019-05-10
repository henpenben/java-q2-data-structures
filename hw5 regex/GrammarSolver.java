/* 
 * Henry Hough
 *
 * 8 May 2019
 * CSE143BL
 * TA: Zachary Keyes
 * Assignment #5: Recursive Grammar Solver
 *  
 * This class is designed to accept a list of Backus-Naur Form grammar rules
 * and provide various methods related to grammar / sentence generation         
*/

import java.util.*;

public class GrammarSolver
{
    private SortedMap<String,List<String>> grammar;
    
    //on creation, maps input list of grammar symbols to rules
    //assumes input Strings are formatted in Backus-Naur Form
    //throws IllegalArgumentException if given list is empty, or contains duplicate symbols
    public GrammarSolver(List<String> inputGrammar)
    {
        if(inputGrammar.isEmpty())
        {
            throw new IllegalArgumentException("grammar empty!");
        }
        grammar = new TreeMap<String,List<String>>();
        for(String s : inputGrammar)
        {
            parseGrammar(s);
        }
    }
    
    //helper method for constructor
    //assumes non-empty input string with correct grammar-rule formatting
    //maps nonterminal grammar rules to their options (case-sensitive)
    //throws IllegalArgumentException if a duplicate nonterminal is passed
    private void parseGrammar(String inputLine)
    {
        Scanner parse = new Scanner(inputLine);
        parse.useDelimiter("::=");
        String key = parse.next(); //grab everything before "::="
        if(grammar.containsKey(key))
        {
            throw new IllegalArgumentException("duplicate nonterminals!");
        }
        grammar.put(key, new ArrayList<String>());
        parse.skip("::="); //skip over the "::=", it's not a grammar rule
        parse.useDelimiter("\\|"); //grammar rules are separated by "|"
        while(parse.hasNext())
        {
            List<String> values = grammar.get(key);
            values.add(parse.next());
            grammar.replace(key, values);
        }
    }
    
    //returns boolean of whether or not the given grammar set contains input nonterminal
    //case-sensitive
    public boolean grammarContains(String nonterminal)
    {
        return grammar.keySet().contains(nonterminal);
    }
    
    //accepts input nonterminal symbol (case-sensitive) and an integer count
    //uses grammar rules to generate count # of string groups
    //returns them as String array
    //throws IllegalArgumentException if input nonterminal not in grammar set, or if count < 0
    //when nonterminals have multiple rules, picks one at random to apply
    public String[] generate(String nonterminal, int count)
    {
        if(!grammarContains(nonterminal))
        {
            throw new IllegalArgumentException("nonterminal does not exist in given grammar set!");
        }
        String[] output = new String[count];
        for(int i = 0; i < count; i++)
        {
            List<String> sentenceArr = new ArrayList<String>();
            generate(nonterminal, sentenceArr);
            String sentence = sentenceArr.get(0); //fEnCePoSt
            for(int j = 1; j < sentenceArr.size(); j++)
            {//String.join(" ", sentenceArr) :(
                sentence += " " + sentenceArr.get(j);
            }
            output[i] = sentence;
        }        
        return output;
    }
    
    //helper method for generate
    //takes input symbol, finds its equivalent rules, follows until reaches terminal symbol
    //adds found terminal symbol to a list
    private void generate(String symbol, List<String> sentenceArr)
    {
        if(!grammarContains(symbol))
        {//check if it is a nonterminal
            sentenceArr.add(symbol);
        }else
        {
            String nextSymbol = randomRule(symbol);
            if(nextSymbol.contains(" "))
            {//if chosen rule has multiple rules
                Scanner symbols = new Scanner(nextSymbol);
                while(symbols.hasNext())
                {//generate again for each rule
                    generate(symbols.next(), sentenceArr);
                }
            }else
            {//generate for found rule
                generate(nextSymbol, sentenceArr);
            }
        }
    }
    
    //helper method for generate()
    //returns a randomly chosen grammar rule out of those available with input String symbol
    private String randomRule(String symbol)
    {
        Random r = new Random();
        List<String> availableSymbols = grammar.get(symbol);
        int randomIndex = r.nextInt(availableSymbols.size());
        return availableSymbols.get(randomIndex);
        //this could also be one, very hard to read line:
        //grammar.get(symbol).get((int)(Math.random() * grammar.get(symbol).size()))
    }
    
    //returns String of available grammar symbols, surrounded by brackets and comma-separated
    public String getSymbols()
    {
        return grammar.keySet().toString();
    }
}