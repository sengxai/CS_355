/**
Seng Xai Yang
Maya Ledvina
CS 355
Sprint 2
5/7/20
**/



package admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import database.DataAccessObject;

import java.util.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
* This is the Admin subsystem and GUI for the help queue project
**/


public class Admin extends JFrame{

    final static int ServerPort = 3076;
    final static String Host = "localhost";
    private static String readFromServer;
  //  private static DefaultListModel<String> list = new DefaultListModel<>();
    private static JPanel p6 = new JPanel();
    private static DataOutputStream dos;
    private static DataInputStream dis;
    private static Socket adminSocket;
    static DataAccessObject dao;


    public static void main(String[] args) throws Exception
    {
      InetAddress ip = null;
  		try {
        // getting host ip
         ip = InetAddress.getByName(Host);

        // establish the connection
        adminSocket = new Socket(ip, ServerPort);

        // obtaining input and out streams
         dis = new DataInputStream(adminSocket.getInputStream());
         dos = new DataOutputStream(adminSocket.getOutputStream());
        initialize(); //start

      } catch ( IOException e) {
        e.printStackTrace();
      }
    }

/**
* Description: This starts the admin gui
**/

	    public static void initialize(){
    JFrame f= new JFrame("ADMIN DISPLAY");
         f.setSize(700,500);

         f.setLayout(new FlowLayout());
         f.setLocationRelativeTo(null);

            //Setting up panels
            JPanel pnlServerControl = new JPanel();
        		pnlServerControl.setLayout(new FlowLayout());
        		JPanel pnlClientControl = new JPanel();
        		pnlClientControl.setLayout(new FlowLayout());
        		JPanel pnlDisplayControl = new JPanel();
        		pnlDisplayControl.setLayout(new FlowLayout());
            // Setting up buttons
        		JButton btnServerOn = new JButton("Start Server");
        		JButton btnInitalize = new JButton("Initialize");
        		JButton btnCancelClient = new JButton("Cancel Request From Student #");
        		JTextArea clientNumber = new JTextArea(1,5); //student number


            JButton course = new JButton( "Set Time/Date for Course"); //button to add to database
             course.addActionListener(new ActionListener(){

               @Override
               public void actionPerformed(ActionEvent event){
                 JFrame a= new JFrame("SET DATES");
                      a.setSize(500,500);
                      //Setting up text fields
                JButton btnSub = new JButton("Submit");
                JTextField textField = new JTextField("Course");
                JTextField section = new JTextField("Section");
                JTextField dateS = new JTextField("Start Date");
                JTextField dateE = new JTextField("End Date");
                JTextField day = new JTextField("Day");
                JTextField startT = new JTextField("Start Time");
                JTextField endT = new JTextField("End Time");
                
                
                //getting text
                btnSub.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                      String cor = textField.getText();
                      String sec = section.getText();
                      String ds = dateS.getText();
                      String de = dateE.getText();
                      String dy = day.getText();
                      String st = startT.getText();
                      String et = endT.getText();
                      try {
           
                    	  
                      insert(cor,sec,dy, ds,de,st,et); //insert into database
                    } catch (ParseException e1) {
                        System.out.println("database error");
                      }

                  }

                });
                              //adding to panel
                               JPanel pan = new JPanel();
                               pan.setSize(400,400);
                               pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

                               a.setVisible(true);
                               a.setLocationRelativeTo(null);

                               pan.add(textField);
                               pan.add(section);
                               pan.add(day);
                               pan.add(dateS);
                               pan.add(dateE);
                               pan.add(startT);
                               pan.add(endT);
                               pan.add(btnSub);
                               a.add(pan);
                               pan.setBorder(BorderFactory.createTitledBorder("Set Information"));
               }

             });

             //adding to panel
            pnlServerControl.add(course);
        		pnlServerControl.add(btnServerOn);
        		pnlServerControl.setBorder(BorderFactory.createTitledBorder("Server Control"));
        		pnlClientControl.add(btnInitalize);
        		pnlClientControl.add(btnCancelClient);
        		pnlClientControl.add(clientNumber);
        		pnlClientControl.setBorder(BorderFactory.createTitledBorder("Student Control"));

            f.add(pnlServerControl);
            pnlServerControl.setVisible(true);
            f.add(pnlClientControl);
            pnlClientControl.setVisible(true);

            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //start server button
            btnServerOn.addActionListener(new ActionListener(){
              public void actionPerformed(ActionEvent e){
                try {

                  dos.writeUTF("startServer"); //send command to server
                } catch (IOException e1) {
                  System.out.println("Start server error");
                }
              }

            });
            //Cancel student request button
            btnCancelClient.addActionListener(new ActionListener(){
              public void actionPerformed(ActionEvent e){
                try {
                  String adminCommand = "disconnectClient,"; //to send to server
                  adminCommand = adminCommand.concat(clientNumber.getText()); //get number from field
                  dos.writeUTF(adminCommand); //send which number to cancel
                } catch (IOException e1) {
                  System.out.println("disconnect Client Error");
                }
              }

            });
            
            //initialize button
            btnInitalize.addActionListener(new ActionListener(){
              public void actionPerformed(ActionEvent e){
                try {
                //  System.out.println("Initialize Button pressed!!");
                  String adminCommand = "disconnectAll";
                  
                  dropEventsLog();
                  dropCourseLog();
                  
                  createEventsLogTable();
                  createCourseLogTable();
                  
                  //dos.writeUTF(adminCommand);
                } catch ( ParseException e1) {
                  System.out.println("Initialize error");
                }
                
              }

            });

	}
  //To get the curret date- currently not used
  public static String getCurrentDate() {
    Date date = new Date();
    SimpleDateFormat timeFormatter= new SimpleDateFormat("hh:mm:ss a");
    return timeFormatter.format(date);
	}

//Inserting into the database
	public static void insert(String cour, String sect, String dayy, String startD, String endD, String timeS, String timeE) throws ParseException {

	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	      Date date = new Date();
	  String curDate = dateFormat.format(date);

	  dao = new DataAccessObject();

	  dao.connect();

	  dao.setAutoCommit(false);;

	  dao.executeSQLNonQuery("INSERT INTO CourseLog VALUES (" + 1 + ", '" + cour + "', '" + sect + "', '" + dayy + "', " + 
	  "TO_DATE('" + startD + " " + timeS + "', " + "'YYYY-MM-DD HH24:mi:ss')" + ", " + 
			  "TO_DATE('" + endD + " " + timeS + "', " + "'YYYY-MM-DD HH24:mi:ss')"+ ", " + 
			  "TO_DATE('" + startD + " " + timeS + "', " + "'YYYY-MM-DD HH24:mi:ss')" +", " +
			  "TO_DATE('" + endD + " " + timeE + "', " + "'YYYY-MM-DD HH24:mi:ss')" + ")");

	  dao.commit();

	  dao.disconnect();
	}

	public static void dropEventsLog() throws ParseException {

		  dao = new DataAccessObject();

		  dao.connect();

		  dao.setAutoCommit(false);;

		  dao.executeSQLNonQuery("DROP TABLE EventsLog");

		  dao.commit();

		  dao.disconnect();
		}
	
	public static void dropCourseLog() throws ParseException {

		  dao = new DataAccessObject();

		  dao.connect();

		  dao.setAutoCommit(false);;

		  dao.executeSQLNonQuery("DROP TABLE CourseLog");

		  dao.commit();

		  dao.disconnect();
		}

	public static void createEventsLogTable() throws ParseException {

		  dao = new DataAccessObject();

		  dao.connect();

		  dao.setAutoCommit(false);;

		  dao.executeSQLNonQuery("CREATE TABLE EventsLog\r\n" + 
		  		"(\r\n" + 
		  		"	ID					INTEGER,\r\n" + 
		  		"	Event				VARCHAR(10) NOT NULL,\r\n" + 
		  		"	WkstName			VARCHAR(20) NOT NULL,\r\n" + 
		  		"	Role				VARCHAR(10) NOT NULL,\r\n" + 
		  		"	ClassCourseNum		VARCHAR(8)  NOT NULL,\r\n" + 
		  		"	ClassCourseSec		INTEGER,\r\n" + 
		  		"	ReqTime				TIMESTAMP        NOT NULL,\r\n" + 
		  		"	WaitTime			INTERVAL DAY TO SECOND,\r\n" + 
		  		"    CancelTime          TIMESTAMP,\r\n" + 
		  		"	PRIMARY KEY (ID)\r\n" + 
		  		")");

		  dao.commit();

		  dao.disconnect();
		}
	
	public static void createCourseLogTable() throws ParseException {

		  dao = new DataAccessObject();

		  dao.connect();

		  dao.setAutoCommit(false);;

		  dao.executeSQLNonQuery("CREATE TABLE CourseLog\r\n" + 
		  		"(\r\n" + 
		  		"	ID					INTEGER,\r\n" + 
		  		"	Course				VARCHAR(10) NOT NULL,\r\n" + 
		  		"	Section			VARCHAR(20) NOT NULL,\r\n" + 
		  		"	Day				VARCHAR(10) NOT NULL,\r\n" + 
		  		"	StartDate		TIMESTAMP        NOT NULL,\r\n" + 
		  		"	EndDate			TIMESTAMP,\r\n" + 
		  		"	StartTime				TIMESTAMP,\r\n" + 
		  		"	EndTime		   	 TIMESTAMP,\r\n" + 
		  		"	PRIMARY KEY (ID)\r\n" + 
		  		")");

		  dao.commit();

		  dao.disconnect();
		}}
