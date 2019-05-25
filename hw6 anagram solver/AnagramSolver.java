
import java.util.*;

public class AnagramSolver
{
    private Map<String, LetterInventory> dictionaryMap;
    private List<String> dictionary;
    
    public AnagramSolver(List<String> list)
    {
        dictionary = new HashMap<String, LetterInventory>();
        for(String s : list)
        {
            dictionary.put(s, new LetterInventory(s));
        }
    }
    
    public void print(String s, int max)
    {
        if(max < 0)
        {
            throw new IllegalArgumentException("max must be >=0!");
        }
        
        Map<String, LetterInventory> subDictionary = new HashMap<String, LetterInventory>();
        
        for(String s : dictionary.keySet())
        {
        
        }
        
        print(dictionary.get(s), max, new LinkedList<String>());
    }
    
    private void print(LetterInventory inv, int max, List<String> output)
    {
        
    }
}