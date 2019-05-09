/* 
 * Henry Hough
 *
 * 26 April 2019
 * CSE143BL
 * TA: Zachary Keyes
 * Assignment #3: Assassination Game
 *  
 * This class is deisgned to handle playing the assassination game by calling various methods,
 * while keeping track of who is still alive and who is dead
*/

import java.util.*;

public class AssassinManager
{
    private AssassinNode frontKill;
    private AssassinNode frontGrave;
    
    //creates the kill ring when AssassinManager object is created
    //must be created with a List<String> as an argument
    //assumes the list does not have duplicate or empty strings
    //throws IllegalArgumentException if the list is empty
    public AssassinManager(List<String> inputList)
    {
        if(inputList.isEmpty())
        {
            throw new IllegalArgumentException("List should not be empty!");
        }
        
        frontKill = new AssassinNode(inputList.get(0));
        frontKill.next = frontKill;
        
        AssassinNode ringCreate = frontKill;
        for(int i = 1; i < inputList.size(); i++)
        {
            ringCreate.next = new AssassinNode(inputList.get(i), frontKill);
            ringCreate = ringCreate.next;
        }
    }
    
    //prints names of people still alive in the game and who they are stalking
    //prints one name-stalking-name pair per line, indented with 4 spaces
    //prints that someone is stalking themself if they are the only player alive
    public void printKillRing()
    {
        AssassinNode printKill = frontKill;
        System.out.println("    " + printKill.name + " is stalking " + printKill.next.name);
        printKill = printKill.next;
        while(!printKill.equals(frontKill))
        {
            System.out.println("    " + printKill.name + " is stalking " + printKill.next.name);
            printKill = printKill.next;
        }
    }
    
    //prints the names of people who are dead in the game, and who killed them
    //prints one pair per line, indented 4 spaces
    //prints nothing if nobody is dead
    public void printGraveyard()
    {
        if(frontGrave != null)
        {
            AssassinNode printGrave = frontGrave;
            System.out.println("    " + printGrave.name + " was killed by " + printGrave.killer);
            printGrave = printGrave.next;
            while(!printGrave.equals(frontGrave))
            {
                System.out.println("    " + printGrave.name + " was killed by "
                                   + printGrave.killer);
                printGrave = printGrave.next;
            }
        }
    }
    
    //returns boolean whether or not the kill-ring contains the name searched for
    public boolean killRingContains(String searchedName)
    {
        return contains(frontKill, searchedName);
    }
    
    //returns boolean wheter or not the graveyard contains the name searched for
    public boolean graveyardContains(String searchedName)
    {
        return contains(frontGrave, searchedName);
    }
    
    //helper method for killRingContains() & graveyardContains()
    //uses input front node of list (in this case killring & graveyard) & a name to search
    //outputs boolean if the list asssociated with frontal node contains the searched name
    private boolean contains(AssassinNode frontNode, String searchedName)
    {
        if(frontNode == null)
        {
            return false;
        }
        if(frontNode.name.equalsIgnoreCase(searchedName))
        {
            return true;
        }
        
        AssassinNode search = frontNode.next;
        while(search != frontNode)
        {
            if(search.name.equalsIgnoreCase(searchedName))
            {
                return true;
            }
            search = search.next;
        }
        return false;
    }
    
    //returns boolean whether or not the game is over (only one player left alive)
    //assumes the game exists (eg kill ring contains at least one player)
    public boolean gameOver()
    {
        return frontKill.equals(frontKill.next);
    }
    
    //returns String of name of what player is the last alive, and therefore winner
    //returns null if there isn't a winner yet (the game isn't over)
    public String winner()
    {
        if(gameOver())
        {
            return frontKill.name;
        }else
        {
            return null;
        }
    }
    
    //takes an input name as String and attempts to move the associated player from the kill ring
    //to the graveyard. throws IllegalArgumentException if requested player is not part of the game
    //throws IllegalStateException if the game is already over
    public void kill(String input)
    {
        if(!killRingContains(input))
        {
            throw new IllegalArgumentException("Player dead or not present!");
        }
        if(gameOver())
        {
            throw new IllegalStateException("Game is over!");
        }
        if(input.equalsIgnoreCase(frontKill.name))
        {
            frontKill = frontKill.next;
        }
        
        AssassinNode killer = frontKill;
        while(!killer.next.name.equalsIgnoreCase(input))
        {
            killer = killer.next;
        }
        addToGraveyard(killer, killer.next);
    }
    
    
    //helper method for kill()
    //assists in moving players from killring to graveyard while keeping them in order
    private void addToGraveyard(AssassinNode killer, AssassinNode killed)
    {
        killed.killer = killer.name;
        killer.next = killer.next.next;
        killed.next = null;
        if(frontGrave == null)
        {
            frontGrave = killed;
            frontGrave.next = frontGrave;
        }
        AssassinNode traverseGrave = frontGrave;
        killed.next = frontGrave;
        while(!traverseGrave.next.equals(frontGrave))
        {
            traverseGrave = traverseGrave.next;
        }
        traverseGrave.next = killed;
        frontGrave = killed;
    }
}