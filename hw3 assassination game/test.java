import java.util.*;

public class test
{
    public static void main(String[] args)
    {
        List<String> list = new LinkedList<String>();
        list.add("name1");
        list.add("name2");
        list.add("name3");
        list.add("name4");
        list.add("name5");
        list.add("name6");
        AssassinManager game = new AssassinManager(list);

        if(game.killRingContains("name1"))
            System.out.println("found 1");
        if(game.killRingContains("NAME2"))
            System.out.println("found 2");
        if(game.killRingContains("name4"))
            System.out.println("found 4");
        if(!game.killRingContains("fff"))
            System.out.println("not found");
            
        game.printKillRing();
        game.kill("name2");
        game.kill("name4");
        game.kill("name6");
        game.kill("name1");
        game.printGraveyard();
        game.printKillRing();
        System.out.println();
        if(game.graveyardContains("name2"))
            System.out.println("found 2");
        if(!game.graveyardContains("name3"))
            System.out.println("not found 3");
        if(!game.graveyardContains("fff"))
            System.out.println("not found");
        
    }
}