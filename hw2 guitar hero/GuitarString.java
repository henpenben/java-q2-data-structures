/* 
   Henry Hough
   15 April 2019
   CSE143BL
   TA: Zachary Keyes
   Assignment #2: GuitarHero
   
   
*/

import java.util.*;

public class GuitarString
{
    public static final int SAMPLE_RATE = 44000;
    public static final double DECAY_FACTOR = 0.996;

    public GuitarString(double frequency)
    {
        Queue<Double> ringBuffer = new LinkedList<Double>();
    }
    
    //pre: input array has more than two elements (or it will throw IllegalArgumentException)
    //post: constructs ring buffer containing values from given array
    public GuitarString(double[] init)
    {
        if(init.length < 2)
        {
            throw new IllegalArgumentException();
        }
    }
    
    public void pluck()
    {
    
    }
    
    public void tic()
    {
    
    }
    
    public double sample()
    {
        return 0;
    }
}