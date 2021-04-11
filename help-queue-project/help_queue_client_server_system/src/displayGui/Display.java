/**
Seng Xai Yang
Maya Ledvina
CS 355
Sprint 2
5/7/20
**/



package displayGui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import database.DataAccessObject;

import java.util.*;
import java.io.*;
import java.net.*;
import java.text.*;

/**
* This is the Display subsystem and GUI for the help queue project
**/


public class Display extends JFrame{


    final static int ServerPort = 3075;
    final static String Host = "localhost";
    private static String readFromServer;
    private static JTextArea displayArea = new JTextArea();
    private static JTextArea jt2 = new JTextArea();
    private static JTextArea jt3 = new JTextArea();
    private static JLabel course= new JLabel();
    private static JLabel section = new JLabel();

    static DataAccessObject dao;

    public static void main(String[] args) throws Exception
    {
        // getting host ip
        InetAddress ip = InetAddress.getByName(Host);

        // establish the connection
        Socket s = new Socket(ip, ServerPort);

        // obtaining input and out streams
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        initialize();

        boolean active = true;
        while(active) //loop to get Requests
        {
            try
            {
                readFromServer = dis.readUTF(); //get command
                if(readFromServer.length() > 0)
                {
                    String[] students = readFromServer.split(",");
                    displayArea.setText("");
                    for(int i = 0; i < students.length; i++)
                    {
                      //adding students to list
                      displayArea.append("#" + i + "\n");
                      jt2.append(" - " + students[i] + "\n");
                      i++;
                      jt3.append("@ " + getCurrentDate() + "\n");
                      course.setText(students[i]);
                    }



                }
            }
            catch( IOException e)
            {
                if(e instanceof SocketException)
                {
                    System.out.println("Connection to server lost");
                    try
                    {
                       s.close();
                       active = false;
                    }
                    catch (IOException f)
                    {
                    }
                }
            }

        }

    }






	    public static void initialize(){
		/*
		 * just to test a sample list
		 * String client = "c1";
		String client2 = "c2";

		Queue<String> myClients = new LinkedList<String>();

		myClients.add(client);
		myClients.add(client2);*/
    JFrame f= new JFrame("Help Queue Display");
         f.setSize(1000,500);

         displayArea.setPreferredSize(new Dimension(200,300));
         displayArea.setBorder(BorderFactory.createTitledBorder("Position Number"));
         //JTextArea jt2 = new JTextArea();

         JTextArea jt4 = new JTextArea();
         //getting panels and borders
         jt2.setPreferredSize(new Dimension(200,300));
         jt2.setBorder(BorderFactory.createTitledBorder("Name"));
         jt3.setPreferredSize(new Dimension(200,300));
         jt3.setBorder(BorderFactory.createTitledBorder("Time Requested"));
         jt4.setPreferredSize(new Dimension(200,300));
         jt4.setBorder(BorderFactory.createTitledBorder("Wait Time"));
         JLabel t1= new JLabel("Philips Room 115");
         t1.setPreferredSize(new Dimension(170,10));

         //getting time and section
         JLabel timeLabel = new JLabel("Current Time: " + getCurrentDate());
         timeLabel.setPreferredSize(new Dimension(20,45));


         //course.setPreferredSize(new Dimension(20,45));
         course.setText("Course: " + showClass());
         section.setText("Section: " + showSection());


         f.setVisible(true);
         f.setLocationRelativeTo(null);
         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         //more panels
         Container contentPane = f.getContentPane();
         contentPane.setLayout(new FlowLayout());
         JPanel p1 = new JPanel();
         p1.setSize(20,30);
         p1.setLayout(new FlowLayout());

         JPanel p4 = new JPanel();
         p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
         p4.add(t1);
         p4.add(timeLabel);
         p4.add(course);
         p4.add(section);
         p1.add(p4);


         JScrollPane p9 = new JScrollPane(displayArea);
         JScrollPane p8 = new JScrollPane(jt2);
         JScrollPane p7 = new JScrollPane(jt3);
         JScrollPane p6 = new JScrollPane(jt4);

         p9.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         p8.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         p7.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         p6.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

          p9.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
          p8.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
          p7.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
          p6.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

         f.getContentPane().add(p9);
         f.getContentPane().add(p8);
         f.getContentPane().add(p7);
         f.getContentPane().add(p6);

         f.add(p1);

         p1.setBorder(BorderFactory.createTitledBorder("Display Information"));
         

        //from above from the queuelist I was testing it displayed all from the clients
        //for(String str : myClients)
       // {
        //	displayArea.append(str + "\n");
        //}
       // displayArea.append(dd.getName() + " " + dd.getTime() + "\n");

        	//displayArea.append(name + time + "\n");


	}
  //getting current date
  public static String getCurrentDate() {
    Date date = new Date();
    SimpleDateFormat timeFormatter= new SimpleDateFormat("hh:mm:ss a");
    return timeFormatter.format(date);
  }
  	public static String showClass()
	{

		String resultSetStr;
		dao = new DataAccessObject();

		dao.connect();										// establish db connection
		dao.setAutoCommit(false); 							// take control of and start transaction
		dao.executeSQLQuery("SELECT COURSE FROM COURSELOG");
		resultSetStr = dao.processResultSet();

		dao.commit();										// commit the transaction if got this far
		dao.disconnect();					// clean up db connection

		return resultSetStr;
	}

  	public static String showSection()
	{
		String resultSetStr;
		dao = new DataAccessObject();

		dao.connect();										// establish db connection
		dao.setAutoCommit(false); 							// take control of and start transaction
		dao.executeSQLQuery("SELECT SECTION FROM COURSELOG");
		resultSetStr = dao.processResultSet();

		dao.commit();										// commit the transaction if got this far
		dao.disconnect();					// clean up db connection

		return resultSetStr;
	}

}
