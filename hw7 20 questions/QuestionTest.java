import java.util.*;
import java.io.*;

public class QuestionTest
{
    public static void main(String[] args) throws FileNotFoundException
    {
        File f = new File("question.txt");
        Scanner s = new Scanner(f);
        
        QuestionTree q = new QuestionTree();
        
        q.read(s);
        q.write(System.out);
        q.askQuestions();
    }
    
}