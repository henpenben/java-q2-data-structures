import java.util.*;
import java.io.*;

public class HangmanTest
{
    public static final String DICTIONARY_FILE = "dictionary2.txt";
    
    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner input = new Scanner(new File(DICTIONARY_FILE));
        List<String> dictionary = new ArrayList<String>();
        while (input.hasNext()) {
            dictionary.add(input.next().toLowerCase());
        }
        
        HangmanManager test = new HangmanManager(dictionary, 4, 999);
        test.record('a');
    }
}