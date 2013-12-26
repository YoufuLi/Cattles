package com.cattles.schedulingframeworks.falkon.common;
//import java.lang.management.*; 
/**
   A stopwatch accumulates time when it is running. You can 
   repeatedly start and stop the stopwatch. You can use a
   stopwatch to measure the running time of a program.
*/
public class StopWatch
{  
   /**
      Constructs a stopwatch that is in the stopped state
      and has no time accumulated.
   */
   public StopWatch()
   {  
      reset();
      this.measureMS = true;
      this.measureMX = false;
      this.measureMICRO = false;
   }

   private boolean measureMS;
   private boolean measureMICRO;
   private boolean measureMX;
   //ThreadMXBean mx = null;

   public StopWatch(boolean measureMS, boolean measureMICRO, boolean measureMX)
   {  
      reset();
      this.measureMS = measureMS;
      this.measureMX = measureMX;
      this.measureMICRO = measureMICRO;

      if (measureMS == false && measureMX == false && measureMICRO == false)
      {
          measureMS = true;
      }

      if (measureMX)
      {
         //mx = ManagementFactory.getThreadMXBean();
         //mx.setThreadCpuTimeEnabled(true);
         
      }
      
   }


   /**
      Starts the stopwatch. Time starts accumulating now.
   */
   public void start()
   {  
      if (isRunning) return;
      isRunning = true;

      if (measureMS) startTime = System.currentTimeMillis();
      if (measureMICRO) startTimeMicro = 0;//System.nanoTime();
      if (measureMX) startTimeMX = 0;//(long)Math.max(0, mx.getCurrentThreadCpuTime());
      if (measureMX) startTimeMXuser = 0;//(long)Math.max(0, mx.getCurrentThreadUserTime());
   }
   
   /**
      Stops the stopwatch. Time stops accumulating and is
      is added to the elapsed time.
   */
   public void stop()
   {  
      if (!isRunning) return;
      isRunning = false;
      long endTime = 0;
      long endTimeMicro = 0;
      long endTimeMX = 0;
      long endTimeMXuser = 0;
      if (measureMS) endTime = System.currentTimeMillis();
      if (measureMICRO) endTimeMicro = 0;//System.nanoTime();
      if (measureMX) endTimeMX = 0;//(long)Math.max(0, mx.getCurrentThreadCpuTime());
      if (measureMX) endTimeMXuser = 0;//(long)Math.max(0, mx.getCurrentThreadUserTime());
      if (measureMS) elapsedTime = elapsedTime + endTime - startTime;
      if (measureMICRO) elapsedTimeMicro = elapsedTimeMicro + endTimeMicro - startTimeMicro;
      if (measureMX) elapsedTimeMX = elapsedTimeMX + endTimeMX - startTimeMX;
      if (measureMX) elapsedTimeMXuser = elapsedTimeMXuser + endTimeMXuser - startTimeMXuser;

   }
   
   /**
      Returns the total elapsed time.
      @return the total elapsed time
   */
   public long getElapsedTime()
   {  
      if (isRunning) 
      {  
         long endTime = System.currentTimeMillis();
         elapsedTime = elapsedTime + endTime - startTime;
         startTime = endTime;
      }
      return elapsedTime;
   }

   public long getElapsedTimeMicro()
   {  
      if (isRunning) 
      {  
         long endTimeMicro = 0;//System.nanoTime();
         elapsedTimeMicro = elapsedTimeMicro + endTimeMicro - startTimeMicro;
         startTimeMicro = endTimeMicro;
      }
      return (long)Math.ceil(elapsedTimeMicro/1000.0);
   }

   public double getElapsedTimeMX()
   {  

       return (getElapsedTimeMXsystem() + getElapsedTimeMXuser())*1.0/1000000.0;
       //return (getElapsedTimeMXsystem() + getElapsedTimeMXuser())*1.0;

   }


   public long getElapsedTimeMXsystem()
   {  
      if (isRunning) 
      {  
         long endTimeMX = 0;//(long)Math.max(0, mx.getCurrentThreadCpuTime());
         elapsedTimeMX = elapsedTimeMX + endTimeMX - startTimeMX;
         startTimeMX = endTimeMX;



      }
      return elapsedTimeMX;
   }


   public long getElapsedTimeMXuser()
   {  
      if (isRunning) 
      {  
         long endTimeMXuser = 0;//(long)Math.max(0, mx.getCurrentThreadUserTime());
         elapsedTimeMXuser = elapsedTimeMXuser + endTimeMXuser - startTimeMXuser;
         startTimeMXuser = endTimeMXuser;

      }
      return elapsedTimeMXuser;
   }


   /**
      Stops the watch and resets the elapsed time to 0.
   */
   public void reset()
   {  
      elapsedTime = 0;
      elapsedTimeMicro = 0;
      elapsedTimeMX = 0;
      elapsedTimeMXuser = 0;
      isRunning = false;
   }
   
   private long elapsedTime;
   private long startTime;
   private boolean isRunning;

   private long elapsedTimeMicro;
   private long startTimeMicro;

   private long elapsedTimeMX;
   private long startTimeMX;


   private long elapsedTimeMXuser;
   private long startTimeMXuser;
}

