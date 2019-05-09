public class MyTest
{
    public static void main(String[] args)
    {
        int[] list = new int[5];
        for(int i = 0; i<list.length; i++)
        {
            System.out.print(list[i]);
        }
        System.out.println("---------------");
        for(int item: list)
        {
            System.out.print(list[i]);
        }
    }
}