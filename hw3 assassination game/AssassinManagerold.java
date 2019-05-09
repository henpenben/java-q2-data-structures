import java.util.*;

public class AssassinManager
{
    private AssassinNode frontRing;
    private AssassinNode frontGraveyard;
    
    public AssassinManager(List<String> names)
    {
        if(names.isEmpty())
        {
            throw new IllegalArgumentException("List should not be empty!");
        }
        
        frontRing = new AssassinNode(names.get(0));
        
        if(names.size() == 1)
        {
            frontRing.next = frontRing;
        }
        
        AssassinNode current = frontRing;
        for(int i = 1; i < names.size(); i++)
        {
            current.next = new AssassinNode(names.get(i), frontRing);
            current = current.next;
        }
    }
    
    public void printKillRing()
    {
        print(frontRing, "is stalking");
    }
    
    //prints the players who are dead
    public void printGraveyard()
    {
        print(frontGraveyard, "was killed by");
    }
    
    private void print(AssassinNode frontList, String action)
    {
        if(frontList != null)
        {
            AssassinNode temp = frontList;
            while(temp.next != frontList)
            {
                System.out.println("    " + temp.name.toString() + " " + action + " " + temp.next.name);
                temp = temp.next;
            }
            if(action.equals("is stalking"))
            {
            System.out.println("    " + temp.name + " " + action + " " + temp.next.name);
            }
        }
    }
    
    public boolean killRingContains(String name)
    {
        return find(frontRing, name);
    }
    
    public boolean graveyardContains(String name)
    {
        return find(frontGraveyard, name);
    }
    
    //helper method for killRingContains and graveyardContains
    //takes input AssassinNode frontList (frontal node of list to consider) and String input (what
    //outputs boolean depending on if input name is contained within list         name to look for)
    private boolean find(AssassinNode frontList, String input)
    {
        if(frontList == null)
        {
            return false;
        }
        if(input.equalsIgnoreCase(frontList.name))
        {
            return true;
        }
        AssassinNode temp = frontList.next;
        while(temp.next != frontList.next)
        {
            if(temp.name.equalsIgnoreCase(input))
            {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
    
    public boolean gameOver()
    {
        return frontRing.next.next == frontRing.next;
    }
    
    public String winner()
    {
        if(gameOver())
        {
            return frontRing.name;
        }else
        {
            return null;
        }
    }
    
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
        if(input.equalsIgnoreCase(frontRing.name))
        {
            frontRing = frontRing.next;
        }
        
        AssassinNode beforeKilled = frontRing;
        while(!beforeKilled.next.name.equalsIgnoreCase(input))
        {
            beforeKilled = beforeKilled.next;
        }
        AssassinNode toGraveyard = new AssassinNode(beforeKilled.next.name);
        addToGraveyard(toGraveyard);
        beforeKilled.next = beforeKilled.next.next;
    }
    
    private void addToGraveyard(AssassinNode dead)
    {
            if(frontGraveyard == null)
            {
                frontGraveyard = dead;
                frontGraveyard.next = dead;
            }
            AssassinNode killed = frontGraveyard;
            while(killed.next != frontGraveyard)
            {
                killed = killed.next;
            }
            killed.next = dead;
    }
}