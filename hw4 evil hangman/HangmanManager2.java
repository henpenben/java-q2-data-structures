import java.util.*;

public class HangmanManager2 extends HangmanManager
{
    private Set<String> words;
    private Set<Character> guesses;
    
    //constructs normal hangmanmanager
    public HangmanManager2(Collection<String> inputDictionary, int wordLength, int maxGuesses)
    {
        super(inputDictionary, wordLength, maxGuesses);
        words = super.words();
        guesses = super.guesses();
    }
    
    //ensures game player cannot mess with the returned available dictionary string set
    public Set<String> words()
    {
        if(words == super.words())
        {
            return super.words();
            
        }
        words = super.words();
        return Collections.unmodifiableSet(words);
    }
    
    //ensures game player cannot mess with the returned guesses character set
    public Set<Character> guesses()
    {
        if(guesses == super.guesses())
        {
            System.out.println("no change");
            return super.guesses();
        }
        guesses = super.guesses();
        System.out.println("change");
        return Collections.unmodifiableSet(guesses);
    }
    
    //extra evil extension of HangmanManager.record()
    //when only one guess is left, if there is any possible word left in the dictionary that does
    //not match your guess, picks that word instead of letting you continue to guess
    public int record(char guess)
    {
        if(super.guessesLeft() == 1 && !guesses().contains(guess))
        {
            for(String word : words())
            {
                if(!word.contains(String.valueOf(guess)))
                {
                    super.words().clear();
                    super.words().add(word);
                    return super.record(guess);
                }
            }
        }
        return super.record(guess);
    }
}