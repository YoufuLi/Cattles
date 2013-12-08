package com.cattles.falkon.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utility
{
    public Utility() 
    {

        
    }

    public int rand()
    {
        int rawRandomNumber;
        int min = 0;
//        int max = 65536 - 1;
        int max = 2147483646;
        rawRandomNumber = (int) (Math.random() * (max - min + 1) ) + min;
        //System.out.println("RAND: " + rawRandomNumber);
        return rawRandomNumber;
    }


    public int pid()
    {
        String file = "";
        String sPID[] = null;
        int iPID = -1;
        try 
        { 

	   FileReader fr     = new FileReader("/proc/self/stat");
           BufferedReader br = new BufferedReader(fr);

           file = new String();
           file = br.readLine();
           sPID = file.split(" ");

           iPID = (new Integer(sPID[0])).intValue();
           //System.out.println("PID: " + iPID);
           
        } 
        catch (IOException e) 
        { 
           // catch possible io errors from readLine()
           System.out.println("WARNING: Process PID could not be determined (possibly not running Linux/Unix)!");
           System.out.println("WARNING: Using a random number instead of PID, could have adverse effects if too many clients run on the same physical machine!");
           //e.printStackTrace();
        }
        return iPID;

    }

    /*

    public static void main(String args[]) 
    {
        try
        {
        
            pid run = new pid();
            //run.readFile();
            for (int i=0;i<10;i++) 
            {


                run.readPID();
                run.readRAND();
            }
        }
        catch (Exception e)
            {
                System.out.println("ERROR: ... " + e);
            }
    }
    */
}
