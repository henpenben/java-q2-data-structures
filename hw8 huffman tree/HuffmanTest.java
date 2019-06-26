import java.io.*;
import java.util.*;

public class HuffmanTest
{
    public static void main(String[] args) throws FileNotFoundException
    {
        File file = new File("short.code");
        Scanner s = new Scanner(file);
        HuffmanTree tree = new HuffmanTree(s);
        tree.write(System.out);
        tree.decode(new BitInputStream("short.code"), System.out, 256);
    }
}