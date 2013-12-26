//portal
package com.cattles.schedulingframeworks.falkon.common;

import java.io.Serializable;
import java.util.*;
import java.io.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.globus.GenericPortal.services.core.WS.impl.*;


public class WorkQueue implements Serializable 
{

    static final Log logger = LogFactory.getLog(WorkQueue.class);
        public LinkedList queue;
        //public boolean DEBUG = false;
	//LinkedList queue = Collections.synchronizedList(new LinkedList());

	public WorkQueue()
	{
	    logger.debug("Intializing Work Queue!");
	    this.queue = new LinkedList();

            //this.DEBUG = logger.isDebugEnabled();

	}

        public synchronized Iterator iterator()
        {
            return queue.iterator();
        }


        public synchronized ListIterator listIterator()
        {
            return queue.listIterator();
        }


        // Add work to the work queue
        public synchronized void insert(Object o) {
            queue.addLast(o);
            logger.debug("insert(): entering notifyAll()... " + o.toString());
            notifyAll();
            logger.debug("insert(): exiting notifyAll()... " + o.toString());
        }


                // Add work to the work queue
        public synchronized void insertFront(Object o) {
            queue.addFirst(o);
            logger.debug("insert(): entering notifyAll()... " + o.toString());
            notifyAll();
            logger.debug("insert(): exiting notifyAll()... " + o.toString());
        }

        public synchronized void insertNoNotify(Object o) {
            queue.addLast(o);
            //notify();
        }

        // Retrieve work from the work queue; block if the queue is empty
        public synchronized Object remove() throws InterruptedException {
            logger.debug("remove(): entering wait()...");

            while (queue.isEmpty()) {
                wait();
            }

            logger.debug("remove(): exiting wait()...");
            Object o = queue.removeFirst();
            logger.debug("remove(): " + o.toString());
            return o;
        }

        public synchronized boolean waitUntilNotEmpty() throws InterruptedException {
            logger.debug("waitUntilNotEmpty(): entering wait()...");
            logger.debug("waitUntilNotEmpty(): entering wait()... this.hashCode() = " + this.hashCode());
            logger.debug("waitUntilNotEmpty(): entering wait()... queue.hashCode() = " + queue.hashCode());

                while (queue.isEmpty())
                {

                    wait();
                    logger.debug("waitUntilNotEmpty(): waking up to check queue length... "+queue.size());
                }
            logger.debug("waitUntilNotEmpty(): exiting wait()... queue.size()=" + queue.size());
            return true;
        }


        // Retrieve work from the work queue; block if the queue is empty
        public synchronized boolean remove(String worker) //throws InterruptedException {
	{

            boolean retCode = false;
	    while(queue.contains(worker))
	    {
		retCode = queue.remove(worker);
                
	    }

	    return retCode;
	}

                // Retrieve work from the work queue; block if the queue is empty
        public synchronized boolean remove(GPResource gp) //throws InterruptedException {
	{

            boolean retCode = false;
	    while(queue.contains(gp))
	    {
		retCode = queue.remove(gp);
                
	    }

	    return retCode;
	}


        // Retrieve work from the work queue; block if the queue is empty
        public synchronized boolean removeTask(Object task) //throws InterruptedException {
        {

            boolean retCode = false;
            while(queue.contains(task))
            {
                retCode = queue.remove(task);
            }

            return retCode;
        }


        // Retrieve work from the work queue; block if the queue is empty
        public synchronized boolean exists(String worker) //throws InterruptedException {
	{
	
	    return queue.contains(worker);
	}

	// Retrieve work from the work queue; block if the queue is empty
	public synchronized Object removeNoWait() //throws InterruptedException {
	{
	
	    if (queue.isEmpty()) 
	    {
		return null;
		//wait();
	    }
	    return 
		queue.removeFirst();
	}


	// gets the size of the queue
	public synchronized int size() 
	{
	    return queue.size();
	}

        public synchronized String toString()
        {
            String result = new String("[ ");
            for (Iterator it=queue.iterator(); it.hasNext(); ) {
        result += (String)it.next() + " ";
    }
            result += "]";
            return result;

        }



        
        private void writeObject(java.io.ObjectOutputStream out) throws IOException
        {
            out.defaultWriteObject();
        }

        private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
        {
             in.defaultReadObject();
        }

        private void readObjectNoData() throws ObjectStreamException
        {
            //hopefully this will never happen :)
            logger.debug("Serialization error: readObjectNoData");


        }   

    }
