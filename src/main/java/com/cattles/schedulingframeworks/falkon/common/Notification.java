//non-persistent notification engine...
package com.cattles.schedulingframeworks.falkon.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringBufferInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.impl.SimpleResourceKey;


public class Notification implements Serializable
{
    public int recvPort;    //port on which receive will bind
    public int SO_TIMEOUT;  //timeout (dynamic depending on the RTT) in ms for receive before retrying again
    public int RECV_BUFFER_SIZE;
    //public DatagramSocket  socket;  //used to establish network connectivity via UDP sockets
    public ServerSocket socket;//used to establish network connectivity via TCP sockets
    public boolean DEBUG;   //used to display debug information

    public int success;     //# of notifications that were successful
    public int failed;      //# of notifications that failed

    public Notification(int port, int timeout, int maxnr, boolean debug)
    {

        success = 0;
        failed = 0;
        RECV_BUFFER_SIZE = 1024;

        recvPort = port;


        try
        {

            //socket = new DatagramSocket(recvPort);
            socket = new ServerSocket(recvPort);
            //notification = new NotificationMessagge();

        } catch (Exception e)
        {
            if (DEBUG) System.out.println("error: Notification(int port, int timeout, int maxnr, boolean debug): " + e.getMessage());
        }



        SO_TIMEOUT = timeout;
        DEBUG = debug;
        //DEBUG = true;
    }


    public Notification(int port, int timeout)
    {

        success = 0;
        failed = 0;
        boolean debug = false;

        RECV_BUFFER_SIZE = 1024;

        recvPort = port;

        try
        {
            //socket = new DatagramSocket(recvPort);
            socket = new ServerSocket(recvPort);
            //notification = new NotificationMessagge();

        } catch (Exception e)
        {
            if (DEBUG) System.out.println("error: Notification(int port, int timeout): " + e.getMessage());
        }

        SO_TIMEOUT = timeout;
        DEBUG = debug;

        //DEBUG = true;
    }


    public Notification(int timeout, boolean DEBUG)
    {

        success = 0;
        failed = 0;
        boolean debug = DEBUG;
        int port = 50100;
        int port_max = 59999;

        RECV_BUFFER_SIZE = 1024;

        recvPort = port;
        boolean socket_bind = false;

        while (recvPort < port_max && socket_bind == false)
        {
            try
            {
                //socket = new DatagramSocket(recvPort);
                socket = new ServerSocket(recvPort);

                socket_bind = true;
            } catch (Exception e)
            {
                //if(DEBUG) System.out.println("error: Notification(int timeout): socket = new DatagramSocket(recvPort); " + e.getMessage());
                if (DEBUG) System.out.println("error: Notification(int timeout): socket = new ServerSocket(recvPort); " + e.getMessage());
                recvPort++;
            }
        }

        try
        {
            //notification = new NotificationMessagge();
        } catch (Exception e)
        {
            if (DEBUG) System.out.println("error: Notification(int timeout): notification = new NotificationMessagge();" + e.getMessage());
        }

        SO_TIMEOUT = timeout;
        this.DEBUG = debug;

        //DEBUG = true;
    }


    public boolean removePersistentSocket(String sockName)
    {
        //dummy function
        return true;
    }


    public boolean send(String dest, ResourceKey key)
    //public boolean send(String dest, String key)
    {

        NotificationMessagge notification;   //notification message
        InetAddress addr = null;
        SocketAddress sockaddr = null;
        Socket sock = null;
        BufferedWriter wr = null;

        if (DEBUG) System.out.println("send() function...");
        try
        {
            if (DEBUG) System.out.println("send(): creating notification...");

            notification = new NotificationMessagge();
            if (DEBUG) System.out.println("send(): notification created!");

            String[] args = dest.split(":");
            String host = args[0];
            int port = Integer.parseInt(args[1]);

            if (DEBUG) System.out.println("send(): attempting to connect to host " + host + " on port " + port + "...");
            // Create a socket with a timeout
            try
            {
                if (DEBUG) System.out.println("send(): getByName()...");
                addr = InetAddress.getByName(host);
                //int port = 80;
                if (DEBUG) System.out.println("send(): InetSocketAddress()...");
                sockaddr = new InetSocketAddress(addr, port);

                // Create an unbound socket
                if (DEBUG) System.out.println("send(): Socket()...");
                sock = new Socket();

                // This method will block no more than timeoutMs.
                // If the timeout occurs, SocketTimeoutException is thrown.
                //int timeoutMs = 2000;   // 2 seconds

                if (DEBUG) System.out.println("send(): connect() with a timeout of " + SO_TIMEOUT + " ...");
                sock.connect(sockaddr, SO_TIMEOUT);
            } catch (UnknownHostException e)
            {

                if (DEBUG) System.out.println("error: send(" + dest + "," + String.valueOf(key) + ") UnknownHostException: " + e.getMessage());
                failed++;
                try
                {

                    if (sock != null) sock.close();
                } catch (Exception ee)
                {
                    if (DEBUG) ee.printStackTrace();

                }

                return false;
            } catch (SocketTimeoutException e)
            {

                if (DEBUG) System.out.println("error: send(" + dest + "," + String.valueOf(key) + ") SocketTimeoutException: " + e.getMessage());
                failed++;

                try
                {

                    if (sock != null) sock.close();
                } catch (Exception ee)
                {
                    if (DEBUG) ee.printStackTrace();

                }
                return false;
            } catch (IOException e)
            {

                if (DEBUG) System.out.println("error: send(" + dest + "," + String.valueOf(key) + ") IOException: " + e.getMessage());
                failed++;

                try
                {

                    if (sock != null) sock.close();
                } catch (Exception ee)
                {
                    if (DEBUG) ee.printStackTrace();

                }
                return false;

            }


            if (DEBUG) System.out.println("send(): connected!");



            //DatagramPacket  packet;
            //InetAddress     address;
            //byte[]          buffer = new byte[10];
            if (DEBUG) System.out.println("send(): notification.incSeq()...");
            notification.incSeq();
            if (DEBUG) System.out.println("NOTIFICATION: key="+String.valueOf(key));

            notification.setResourceKey(key);
            if (DEBUG) System.out.println("NOTIFICATION: notification.getResourceKey()="+String.valueOf(notification.getResourceKey()));


            //address=InetAddress.getByName(host);

            //socket.setSoTimeout(SO_TIMEOUT);
            //int maxNumRetries = MAX_NUM_RETRIES;
            //int retryNum = 0;

            //long start = 0;
            //long end = 0;

            //while (true)
            //{
            // try
            // {
            //packet = new DatagramPacket(notification.toBytes(), notification.length(), address, port);
            if (DEBUG) System.out.println("Notification: send() " + notification.length() + " bytes...");
            //   start = System.currentTimeMillis();
            if (DEBUG) System.out.println("NotificationEngine:send(String dest, ResourceKey key): Sending notification with key " + String.valueOf(key) + " to " + dest + " ...");
            //socket.send(packet);
            wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            //wr.write("aString");
            //wr.write(notification.toString());
            wr.write(String.valueOf(key) + "\n");

            //import org.globus.wsrf.ResourceKey;

            wr.flush();
            if (DEBUG) System.out.println("NotificationEngine:send(String dest, ResourceKey key): Notification with key " + String.valueOf(key) + " sent to " + dest + " successfully!");

            //setting the timeout for the ACK

            //needed for ACK
            /*
            sock.setSoTimeout(10000);


            BufferedReader ACK = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            if (DEBUG) System.out.println("Waiting for notification ack for at most 10000 ms");
            String sACK = ACK.readLine();

            boolean retCode = false;

            if (sACK != null && sACK.contains("OK"))
            {
                if (DEBUG) System.out.println("Notification ack received successfully!");
                retCode = true;
                success++;

            }
            else
            {
                if (DEBUG) System.out.println("Notification ack failed :(");
                retCode = false;
                failed++;

            }    */

            //socket.receive(packet);
            // end = System.currentTimeMillis();

            //NotificationMessagge notRecv = new NotificationMessagge(packet.getData()); 

            //checkNotification(notRecv);
            //if (DEBUG) System.out.println("Received notification ack: " + notRecv.seq + " " + notRecv.ack + " " + notRecv.idle_state + " " + notRecv.eta + " " + notRecv.getResourceKey().getValue());

            boolean retCode = true;
            success++;
            if (DEBUG) System.out.println("send(): closing down stream and socket...");


            try
            {

                if (wr != null) wr.close();
                if (sock != null) sock.close();
            } catch (Exception ee)
            {
                if (DEBUG) ee.printStackTrace();

            }

            //////if (wr != null) wr.close();
            //////if (sock != null) sock.close();
            if (DEBUG) System.out.println("send(): exiting with return code " + retCode);

            return retCode;
            //   } catch (Exception e)
            //  {
            //
            //       if (DEBUG) System.out.println("error: send(String dest, ResourceKey key): " + e.getMessage());
            //      if (wr != null) wr.close();
            //      if (sock != null) sock.close();
            //      return false;

            //  }
            //}
        } catch (Exception e)
        {
            if (DEBUG) System.out.println("error: send(" + dest + "," + String.valueOf(key) + ") Exception: " + e.getMessage());
            failed++;

            try
            {

                if (wr != null) wr.close();
                if (sock != null) sock.close();
            } catch (Exception ee)
            {
                if (DEBUG) ee.printStackTrace();

            }


            //if (wr != null) wr.close();
            //if (sock != null) sock.close();
            return false;
        }
    }


    public boolean sendString(String dest, String msg)
    //public boolean send(String dest, String key)
    {

        NotificationMessagge notification;   //notification message
        InetAddress addr = null;
        SocketAddress sockaddr = null;
        Socket sock = null;
        BufferedWriter wr = null;

        if (DEBUG) System.out.println("sendString() function...");
        try
        {
            if (DEBUG) System.out.println("sendString(): creating notification...");

            notification = new NotificationMessagge();

            String[] args = dest.split(":");
            String host = args[0];
            int port = Integer.parseInt(args[1]);

            // Create a socket with a timeout
            try
            {
                addr = InetAddress.getByName(host);
                //int port = 80;
                sockaddr = new InetSocketAddress(addr, port);

                // Create an unbound socket
                sock = new Socket();

                // This method will block no more than timeoutMs.
                // If the timeout occurs, SocketTimeoutException is thrown.
                //int timeoutMs = 2000;   // 2 seconds
                sock.connect(sockaddr, SO_TIMEOUT);
            } catch (UnknownHostException e)
            {

                if (DEBUG) System.out.println("error: sendString(" + dest+","+ msg +") UnknownHostException: " + e.getMessage());
                failed++;

                try
                {

                    if (sock != null) sock.close();
                } catch (Exception ee)
                {
                    if (DEBUG) ee.printStackTrace();

                }

                return false;
            } catch (SocketTimeoutException e)
            {

                if (DEBUG) System.out.println("error: sendString(" + dest+","+ msg +") SocketTimeoutException: " + e.getMessage());
                failed++;

                try
                {

                    if (sock != null) sock.close();
                } catch (Exception ee)
                {
                    if (DEBUG) ee.printStackTrace();

                }
                return false;
            } catch (IOException e)
            {

                if (DEBUG) System.out.println("error: sendString(" + dest+","+ msg +") IOException: " + e.getMessage());
                failed++;

                try
                {

                    if (sock != null) sock.close();
                } catch (Exception ee)
                {
                    if (DEBUG) ee.printStackTrace();

                }
                return false;

            }






            //DatagramPacket  packet;
            //InetAddress     address;
            //byte[]          buffer = new byte[10];
            notification.incSeq();
            if (DEBUG) System.out.println("sendString(): NOTIFICATION: msg="+msg);

            //notification.setResourceKey(key);
            //if (DEBUG) System.out.println("NOTIFICATION: notification.getResourceKey()="+String.valueOf(notification.getResourceKey()));


            //address=InetAddress.getByName(host);

            //socket.setSoTimeout(SO_TIMEOUT);
            //int maxNumRetries = MAX_NUM_RETRIES;
            //int retryNum = 0;

            //long start = 0;
            //long end = 0;

            //while (true)
            //{
            // try
            // {
            //packet = new DatagramPacket(notification.toBytes(), notification.length(), address, port);
            //if (DEBUG) System.out.println("Notification: send() " + notification.length() + " bytes...");
            //   start = System.currentTimeMillis();
            if (DEBUG) System.out.println("sendString(): Sending notification...");
            //socket.send(packet);
            wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            //wr.write("aString");
            //wr.write(notification.toString());
            //wr.write(String.valueOf(key));
            wr.write(msg + "\n");
            //import org.globus.wsrf.ResourceKey;

            wr.flush();
            if (DEBUG) System.out.println("sendString(): Notification sent...");

            //needed for ACK
            /*

            //setting the timeout for the ACK
            sock.setSoTimeout(10000);

            BufferedReader ACK = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            if (DEBUG) System.out.println("Waiting for notification ack for at most 10000 ms");
            String sACK = ACK.readLine();

            boolean retCode = false;

            if (sACK != null && sACK.contains("OK"))
            {
                if (DEBUG) System.out.println("Notification ack received successfully!");
                retCode = true;
                success++;

            }
            else
            {
                retCode = false;
                failed++;
                if (DEBUG) System.out.println("Notification ack failed :(");


            } 
            */

            //socket.receive(packet);
            // end = System.currentTimeMillis();

            //NotificationMessagge notRecv = new NotificationMessagge(packet.getData()); 

            //checkNotification(notRecv);
            //if (DEBUG) System.out.println("Received notification ack: " + notRecv.seq + " " + notRecv.ack + " " + notRecv.idle_state + " " + notRecv.eta + " " + notRecv.getResourceKey().getValue());


            boolean retCode = true;
            success++;



            try
            {

                if (wr != null) wr.close();
                if (sock != null) sock.close();
            } catch (Exception ee)
            {
                if (DEBUG) ee.printStackTrace();

            }

            //////if (wr != null) wr.close();
            //////if (sock != null) sock.close();
            return retCode;
            //   } catch (Exception e)
            //  {
            //
            //       if (DEBUG) System.out.println("error: send(String dest, ResourceKey key): " + e.getMessage());
            //      if (wr != null) wr.close();
            //      if (sock != null) sock.close();
            //      return false;

            //  }
            //}
        } catch (Exception e)
        {
            if (DEBUG) System.out.println("error: sendString(" + dest+","+ msg +") last Exception: " + e.getMessage());
            failed++;

            try
            {

                if (wr != null) wr.close();
                if (sock != null) sock.close();
            } catch (Exception ee)
            {
                if (DEBUG) ee.printStackTrace();
                
                

            }
            return false;
        }
    }

    public boolean sendStrings(String dest, String msg[])
    //public boolean send(String dest, String key)
    {

        NotificationMessagge notification;   //notification message
        InetAddress addr = null;
        SocketAddress sockaddr = null;
        Socket sock = null;
        BufferedWriter wr = null;

        if (DEBUG) System.out.println("sendStrings(): function...");
        try
        {
            if (DEBUG) System.out.println("sendStrings(): creating notification...");

            notification = new NotificationMessagge();

            String[] args = dest.split(":");
            String host = args[0];
            int port = Integer.parseInt(args[1]);

            // Create a socket with a timeout
            try
            {
                addr = InetAddress.getByName(host);
                //int port = 80;
                sockaddr = new InetSocketAddress(addr, port);

                // Create an unbound socket
                sock = new Socket();

                // This method will block no more than timeoutMs.
                // If the timeout occurs, SocketTimeoutException is thrown.
                //int timeoutMs = 2000;   // 2 seconds
                sock.connect(sockaddr, SO_TIMEOUT);
            } catch (UnknownHostException e)
            {

                if (DEBUG) System.out.println("error: sendStrings(" + dest+","+ msg.length +") UnknownHostException: " + e.getMessage());
                for (int i=0;i<msg.length;i++)
                {
                    if (DEBUG) System.out.println("error: sendStrings(" + dest+","+ msg[i] +") UnknownHostException: " + e.getMessage());
                    failed++;
                }
                

                try
                {

                    if (sock != null) sock.close();
                } catch (Exception ee)
                {
                    if (DEBUG) ee.printStackTrace();

                }

                return false;
            } catch (SocketTimeoutException e)
            {

                if (DEBUG) System.out.println("error: sendStrings(" + dest+","+ msg.length +") SocketTimeoutException: " + e.getMessage());
                for (int i=0;i<msg.length;i++)
                {
                    if (DEBUG) System.out.println("error: sendStrings(" + dest+","+ msg[i] +") SocketTimeoutException: " + e.getMessage());
                    failed++;
                }

                try
                {

                    if (sock != null) sock.close();
                } catch (Exception ee)
                {
                    if (DEBUG) ee.printStackTrace();

                }
                return false;
            } catch (IOException e)
            {

                if (DEBUG) System.out.println("error: sendStrings(" + dest+","+ msg.length +") IOException: " + e.getMessage());
                for (int i=0;i<msg.length;i++)
                {
                    if (DEBUG) System.out.println("error: sendStrings(" + dest+","+ msg[i] +") IOException: " + e.getMessage());
                    failed++;
                }

                try
                {

                    if (sock != null) sock.close();
                } catch (Exception ee)
                {
                    if (DEBUG) ee.printStackTrace();

                }
                return false;

            }




            wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));


            for (int i=0;i<msg.length;i++)
            {

            
            notification.incSeq();
            if (DEBUG) System.out.println("sendStrings(): NOTIFICATION: msg="+msg[i]);

            if (DEBUG) System.out.println("sendStrings(): Sending notification...");
            wr.write(msg[i] + "\n");

            //wr.flush();
            if (DEBUG) System.out.println("sendStrings(): Notification sent...");



            success++;

            }
            wr.flush();
            boolean retCode = true;

            try
            {

                if (wr != null) wr.close();
                if (sock != null) sock.close();
            } catch (Exception ee)
            {
                if (DEBUG) ee.printStackTrace();

            }

            return retCode;
        } catch (Exception e)
        {
            if (DEBUG) System.out.println("error: sendStrings(" + dest+","+ msg.length +") last Exception: " + e.getMessage());
            for (int i=0;i<msg.length;i++)
            {
                if (DEBUG) System.out.println("error: sendStrings(" + dest+","+ msg[i] +") last Exception: " + e.getMessage());
                failed++;
            }

            try
            {

                if (wr != null) wr.close();
                if (sock != null) sock.close();
            } catch (Exception ee)
            {
                if (DEBUG) ee.printStackTrace();



            }
            return false;
        }
    }




    public ResourceKey recv() throws Exception
    //public String recv()   throws Exception
    {

        Socket srv = null;
        BufferedReader rd = null;
        try
        {
            //DatagramPacket  packet;
            //ServerPacket pack;


            //byte[] buffer = new byte[RECV_BUFFER_SIZE];
            char buffer[] = new char[RECV_BUFFER_SIZE];//String();

            //try
            //{

            //    packet = new DatagramPacket(buffer, buffer.length);

            //} catch (Exception e)
            //{
            //    throw new Exception("Error in DatagramPacket() creation: " + e);
            //}

            socket.setSoTimeout(SO_TIMEOUT);
            srv = socket.accept();

            //while (true)
            //{
            if (DEBUG) System.out.println("Waiting for notification for "+ SO_TIMEOUT +" ms");

            rd = new BufferedReader(new InputStreamReader(srv.getInputStream()));

            String str;
            //while ((str = rd.readLine()) != null)
            //{
            //str = rd.readLine();
            //int numBytes = rd.read(buffer);

            String string = rd.readLine();
            //if (str != null)
            if (string != null && string.length() > 0)
            {
            } else
            {

                failed++;
                try
                {

                    if (rd != null) rd.close();
                    if (srv != null) srv.close();
                } catch (Exception ee)
                {
                    if (DEBUG) ee.printStackTrace();

                }
                throw new Exception("Error in recv(): null");
            }


            String tokens[] = string.split("=");

            if (tokens.length != 2)
            {
                failed++;
                try
                {

                    if (rd != null) rd.close();
                    if (srv != null) srv.close();
                } catch (Exception ee)
                {
                    if (DEBUG) ee.printStackTrace();

                }

                return null;

            }



            ResourceKey key = new SimpleResourceKey(QName.valueOf(tokens[0]), (Object)tokens[1]);
            if (DEBUG) System.out.println("new key = " + String.valueOf(key));


            //NotificationMessagge notRecv = new NotificationMessagge(string); 

            //return notRecv.getResourceKey();


            //needed for ACK
            /*
            BufferedWriter ACK = new BufferedWriter(new OutputStreamWriter(srv.getOutputStream()));
            if (DEBUG) System.out.println("Sending back ACK...");

            ACK.write("OK\n");
            ACK.flush();
            ACK.close();
            */


            try
            {

                if (rd != null) rd.close();
                if (srv != null) srv.close();
            } catch (Exception ee)
            {
                if (DEBUG) ee.printStackTrace();

            }
            //if (rd != null) rd.close();
            //if (srv != null) srv.close();
            success++;


            return key;

            //Integer mapKey = new Integer(notRecv.seq);

            //}
        } catch (Exception e)
        {
            failed++;


            try
            {

                if (rd != null) rd.close();
                if (srv != null) srv.close();
            } catch (Exception ee)
            {
                if (DEBUG) ee.printStackTrace();

            }
            //if (rd != null) rd.close();
            //if (srv != null) srv.close();
            throw new Exception("Error in recv(): " + e);
        }
    }


    public String recvString() throws Exception
    //public String recv()   throws Exception
    {

        Socket srv = null;
        BufferedReader rd = null;

        try
        {
            //DatagramPacket  packet;
            //ServerPacket pack;


            //byte[] buffer = new byte[RECV_BUFFER_SIZE];
            char buffer[] = new char[RECV_BUFFER_SIZE];//String();

            //try
            //{

            //    packet = new DatagramPacket(buffer, buffer.length);

            //} catch (Exception e)
            //{
            //    throw new Exception("Error in DatagramPacket() creation: " + e);
            //}

            socket.setSoTimeout(SO_TIMEOUT);
            srv = socket.accept();

            //while (true)
            //{
            if (DEBUG) System.out.println("Waiting for notification for "+ SO_TIMEOUT +" ms");

            rd = new BufferedReader(new InputStreamReader(srv.getInputStream()));

            String str;
            //while ((str = rd.readLine()) != null)
            //{
            //str = rd.readLine();
            //int numBytes = rd.read(buffer);
            String string = rd.readLine();

            //String string = null;
            //if (str != null)
            if (string != null && string.length() > 0)
            {
                //string = new String(buffer, 0, numBytes);


                //success++;
            } else
            {

                failed++;

                try
                {

                    if (rd != null) rd.close();
                    if (srv != null) srv.close();
                } catch (Exception ee)
                {
                    if (DEBUG) ee.printStackTrace();

                }
                //if (rd != null) rd.close();
                //if (srv != null) srv.close();
                throw new Exception("Error in recv(): null");
            }

            //if (rd != null) rd.close();
            //if (srv != null) srv.close();

            /*
            String tokens[] = string.split("=");

            if (tokens.length != 2)
            {
                return null;

            }


            
            ResourceKey key = new SimpleResourceKey(QName.valueOf(tokens[0]), (Object)tokens[1]);
            System.out.println("new key = " + String.valueOf(key));  */


            //NotificationMessagge notRecv = new NotificationMessagge(string); 

            //return notRecv.getResourceKey();
            //return key;

            if (DEBUG) System.out.println("Received notification with msg = " + string);


            //needed for ACK
            /*
            BufferedWriter ACK = new BufferedWriter(new OutputStreamWriter(srv.getOutputStream()));

            if (DEBUG) System.out.println("Sending back ACK...");
            ACK.write("OK\n");
            ACK.flush();
            ACK.close();
            */


            try
            {

                if (rd != null) rd.close();
                if (srv != null) srv.close();
            } catch (Exception ee)
            {
                if (DEBUG) ee.printStackTrace();

            }
            //if (rd != null) rd.close();
            //if (srv != null) srv.close();
            success++;



            return string;

            //Integer mapKey = new Integer(notRecv.seq);

            //}
        } catch (Exception e)
        {
            failed++;

            try
            {

                if (rd != null) rd.close();
                if (srv != null) srv.close();
            } catch (Exception ee)
            {
                if (DEBUG) ee.printStackTrace();

            }


            throw new Exception("Error in recv(): " + e);
        }
    }


    public String[] recvStrings() throws Exception
    //public String recv()   throws Exception
    {

        Socket srv = null;
        BufferedReader rd = null;

        try
        {
            char buffer[] = new char[RECV_BUFFER_SIZE];//String();


            socket.setSoTimeout(SO_TIMEOUT);
            srv = socket.accept();

            if (DEBUG) System.out.println("Waiting for notification for "+ SO_TIMEOUT +" ms");

            rd = new BufferedReader(new InputStreamReader(srv.getInputStream()));

            String str;

            List strings = new LinkedList();

            String string = null;// = rd.readLine();

            while ((string = rd.readLine()) != null)
            {
            

            //if (string != null && string.length() > 0)
            //{
                strings.add(string);
                success++;
            //} 
            }
            if (strings.size() == 0)
            {

                failed++;

                try
                {

                    if (rd != null) rd.close();
                    if (srv != null) srv.close();
                } catch (Exception ee)
                {
                    if (DEBUG) ee.printStackTrace();

                }
                //if (strings.size() == 0)
                    throw new Exception("Error in recv(): null");
            }


            if (DEBUG) System.out.println("Received notification with " + strings.size() + " messages");

            try
            {

                if (rd != null) rd.close();
                if (srv != null) srv.close();
            } catch (Exception ee)
            {
                if (DEBUG) ee.printStackTrace();

            }
            //success++;


            return (String[])strings.toArray(new String[strings.size()]);

        } catch (Exception e)
        {
            failed++;

            try
            {

                if (rd != null) rd.close();
                if (srv != null) srv.close();
            } catch (Exception ee)
            {
                if (DEBUG) ee.printStackTrace();

            }


            throw new Exception("Error in recv(): " + e);
        }
    }

    public void destroy()
    {
        try
        {

            if (socket != null) socket.close();
        } catch (Exception e)
        {
            if (DEBUG) System.out.println("Error in closing server socket..." + e);
            e.printStackTrace();
        }
    }


    /*
     public static void main(String[] args)
                              throws Exception
        {
         int numNot = 1000;

         if (args.length != 1)
         {
             System.out.println("incorect parameters...");
             System.exit(0);
         }

         if (args[0].contains("-server"))
         {

         


            Notification n = new Notification(50009, 60000);
            for (int i=0;i<numNot;i++)
            {

            
            System.out.println(i+": recv()...");
            ResourceKey KEY = n.recv();
            System.out.println("received key = " + String.valueOf(KEY));
            }
         }
         else if (args[0].contains("-client"))
         {
             Notification n = new Notification(50009, 60000);
             //ResourceKey KEY = "hello";
             Object obj = new String("246f54c0-b8bc-11db-98bb-f0495782e0d2");
             ResourceKey KEY = new SimpleResourceKey(new QName("key"), obj);

             long start = System.currentTimeMillis();
             for (int i=0;i<numNot;i++)
             {
                 System.out.println(i+ ": send()...");
                 n.send("localhost:50009", KEY);
             }
             long elapsedTimeMillis = System.currentTimeMillis()-start;
             System.out.println("Completed " + numNot + " in " + elapsedTimeMillis + " ms... TP/sec = " + numNot*1.0/(elapsedTimeMillis/1000.0));
         }

        }
        */


}


class NotificationMessagge implements Serializable 
{
    public int seq;
    public boolean ack;
    public boolean idle_state;
    public int eta;
    private int SEQ_MAX = 2147483647;
    public ResourceKey key; //ResourceKey to identify the particular resource where the notificatoin originated

    public NotificationMessagge() throws Exception
    {
        seq = 0;
        ack = false;
        idle_state = false;
        eta = 0;
        key = null;
    }

    public NotificationMessagge(int s, boolean a, boolean is, int e, ResourceKey k/*EndpointReferenceType er, String k*/) throws Exception
    {
        seq = s;
        ack = a;
        idle_state = is;
        eta = e;
        key = k;

    }

    public byte[] toBytes() throws Exception
    {  
        // Serialize to a byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;

        ObjectOutput out = new ObjectOutputStream(bos) ;
        out.writeInt(seq);
        out.writeBoolean(ack);
        out.writeBoolean(idle_state);
        out.writeInt(eta);

        out.writeObject(key);
        out.close();

        // Get the bytes of the serialized object
        byte[] buf = bos.toByteArray();

        return buf;
    }


    public String toString()
    {  
        try
        {

            // Serialize to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream() ;

            ObjectOutput out = new ObjectOutputStream(bos) ;
            out.writeInt(seq);
            out.writeBoolean(ack);
            out.writeBoolean(idle_state);
            out.writeInt(eta);

            out.writeObject(key);
            out.close();

            // Get the bytes of the serialized object
            return bos.toString();
        } catch (Exception e)
        {
            System.out.println("error in toString()" + e);
            e.printStackTrace();
            return null;
        }
    }

    public NotificationMessagge(byte[] bytes) throws Exception
    {

        ObjectInputStream in =  new ObjectInputStream(new ByteArrayInputStream(bytes));
        // Deserialize from a byte array
        seq = in.readInt();
        ack = in.readBoolean();
        idle_state = in.readBoolean();
        eta = in.readInt();
        key = (ResourceKey) in.readObject();
        in.close();
    }

    public NotificationMessagge(String string) throws Exception
    {
        //char[] bytes = string.toCharArray();

        ObjectInputStream in =  new ObjectInputStream(new StringBufferInputStream(string));
        // Deserialize from a byte array
        seq = in.readInt();
        ack = in.readBoolean();
        idle_state = in.readBoolean();
        eta = in.readInt();
        key = (ResourceKey) in.readObject();
        in.close();
    }


    public void setAck(boolean a) throws Exception
    {
        ack = a;
    }


    public void incSeq() throws Exception
    {
        if (seq>=SEQ_MAX)
            seq = 1;
        else
            seq++;
    }


    public ResourceKey getResourceKey()
    {
        return key; 

    }



    public void setResourceKey(ResourceKey k)
    {
        key = k; 

    }



    public int length() throws Exception
    {
        byte[] buf = toBytes();
        return buf.length;
    }
}
