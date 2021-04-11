

package server;

import java.io.*;
import java.util.*;

import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import database.DataAccessObject;
import displayGui.UpdatedDisplay;

public class Server
{
	// Vector to store active clients
	public static Vector<ClientHandler> clientVector = new Vector<>();
	static ArrayList<String> aList = new ArrayList<>();

	// counter for clients
	static int i = 0;

	private static ScheduledExecutorService displayScheduledUpdates;
	private static UpdatedDisplay keepUpdatedDisplay = null;

	private static ServerSocket clientServerSocket;
	private static ServerSocket displayServerSocket;
	private static ServerSocket adminServerSocket;

	private static Socket clientSocket;
	private static Socket displaySocket;
	private static Socket adminSocket;

	private static DataInputStream adminInput;
	private static DataOutputStream adminOutput;

	private static DataOutputStream displayOutput;

	private static boolean waitForAdmin = true;
	private static boolean serverRunning = false;
	private static boolean displayRunning = false;
	private static boolean adminConnected = false;

	static String resultSetStr = null;
	static boolean commitSuccess;

	static DataAccessObject dao;

	public static void main(String[] args) throws IOException
	{
		// server is listening on port 1234
	//	@SuppressWarnings("resource")
		clientServerSocket = new ServerSocket(3074); 	//socket for client side
		displayServerSocket = new ServerSocket(3075); //socket for display
		adminServerSocket = new ServerSocket(3076);
	//	Socket clientSocket;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:MM:ss");

		/* waiting for admin to connect*/
		if(waitForAdmin){
			serverWait();
		} else {
			startServer();
		}
		return;
	}

	//starts the server then wait to for client to connect
	private static void startServer() throws IOException {
		System.out.println("Server started successfully... Connecting Display");
		serverRunning = true;
		// System.out.println("Waiting for display to connect...");
		connectDisplay();
		System.out.println("Waiting on student connections...");
		// System.out.println("Waiting...");
		while (serverRunning) {
			connectClient();
			interpretAdminCommand();
			if(keepUpdatedDisplay != null)
				keepUpdatedDisplay.run();
		}

	}


	/* connects to display and has a timer to update the display*/
	public static void connectDisplay()throws IOException
	{
		try
		{
			displaySocket = displayServerSocket.accept();
			displayScheduledUpdates = Executors.newSingleThreadScheduledExecutor();
			keepUpdatedDisplay = new UpdatedDisplay(displaySocket, clientVector, displayScheduledUpdates);
			displayScheduledUpdates.scheduleAtFixedRate(keepUpdatedDisplay, 0,4, TimeUnit.SECONDS);
		}
		catch (SocketTimeoutException e)
		{
			throw e;
		}

	}

	/* connects to the client through the sockets */
	public static void connectClient() throws IOException
	{

		try
		{
			clientSocket = clientServerSocket.accept();
			InetAddress ip = clientSocket.getInetAddress();
			boolean clientSetup = false;
			// reconnecting clients and not connecting same substation
			for (ClientHandler mc : Server.clientVector) {

				if (mc.ip.equals(ip) && mc.isloggedin == false) {
					mc.reconnectClient(clientSocket);
					Thread t = new Thread(mc);
					t.start();
					mc.dos.writeUTF("goodToGo");
					clientSetup = true;
					break;
				}
				else if(mc.ip.equals(ip) && mc.isloggedin == true)
				{

					clientSetup = true;
					mc.dos.writeUTF("abortGUI");
					System.out.println("Error, cannot connect multiple client applications from same workstation...");
					clientSocket.close();
				}
			}
			if(!clientSetup)
			{
					//when clients connect it adds them to the list
					addClient();

			}

		}
		catch (IOException e)
		{

		}

	}

	
	public static void addClient() throws IOException
	{
		try
		{
			//This array list holds the current time it was requested
			ArrayList<String> strGG = new ArrayList<String>();


			System.out.println("New student connected : " + clientSocket );

			// create input and output streams for this socket
			DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

			//Gets current time help was requested then adds it to an arraylist
			//just need to make Cancel button work to actually cancel a job
			//so when cancel button is clicked it gets a new current time and can be subtracted from the time in the Arraylist
			String timeReq = getCurrentTime();
			strGG.add("00:44:00");					//just added in a time to test it out (Time help was requested)

			String clientName;
			String help = "Help";
			String cancel = "Cancel";
			String client = "CLIENT";
			int classSec = 01;
			String classCourse = "CS 260";
			String cancelTime = null;	//these two time variables are only used if Help button was pressed
										//otherwise in the method insertEvent(...) is used for cancelling.
										//insertEvent() and insertHelptReq() are pretty much the same so until
										//when we figure out how to do the Cancel button, we can make the method into one.

			//Gets the current date

			String curDate = getCurrentDate();

			clientName = "Client " + i;


			// Create a new handler object for handling this request.
			InetAddress ip = clientSocket.getInetAddress();
			ClientHandler mtch = new ClientHandler(clientSocket, clientName, dis, dos, ip, strGG.get(i));

			// Create a new Thread with handler object.
			Thread t = new Thread(mtch);
			mtch.dos.writeUTF("goodToGo");

			System.out.println("Adding Student " + clientName + " at " + timeReq);

			// add this clientto active clientlist
			clientVector.add(mtch);

			// display clientlist
			System.out.println("Current students: ");
			for (ClientHandler mc : Server.clientVector) {
				if (mc.isloggedin == true) {
					System.out.println(mc.name);
				}
			}

			//gets current wait time
			String waitTime = getWaitTime(mtch.timeReq);


			keepUpdatedDisplay.run();
			System.out.println();

			// start the thread.
			t.start();

			//inserting Help event into the database
			insertHelpReq(i, help, clientName, client, classCourse, classSec, timeReq);

			/*inserting Cancel event into the database
				Uncomment this block when cancel button works.
			try {
				insertEvent(i, cancel, clientName, client, classCourse, classSec, mtch.timeReq, curDate, waitTime);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			 */

			// increment i for new clientname
			i++;
		}
		catch (IOException e)
		{

		}

	}

	//Server starts up and waits for the admin to connect
	public static void serverWait() throws IOException {
		System.out.println("Server idle, waiting for admin connection");
		while (!adminConnected) {
			connectAdmin();
		}

		String adminCommand = "";
		try {
			adminCommand = adminInput.readUTF();
			if (adminCommand.equals("startServer")) {
				startServer();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//admin is connected to the server.
	public static void connectAdmin() throws IOException {

		try {
			adminSocket = adminServerSocket.accept();
			adminInput = new DataInputStream(adminSocket.getInputStream());
			adminOutput = new DataOutputStream(adminSocket.getOutputStream());
			adminConnected = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//does the commands from the admin class
	private static void interpretAdminCommand() {
		String adminCommand = "";
		try {
			if (adminInput.available() != 0) {
				adminCommand = adminInput.readUTF();
				if (adminCommand.equals("stopServer")) {
					serverWait();
				}
				else if( adminCommand.length() > 15 && adminCommand.substring(0,15).equals("disconnectStudent"))
				{
					String[] arr = adminCommand.split(",");
					int clientNumber = Integer.parseInt(arr[1]);
					disconnectStudent(clientNumber);
				}
			}else

				return;



		} catch (IOException e) {
			System.out.println("No admin commands");
		}
	}



	//disconnects the clients/students
	private static void disconnectStudent(int clientNumber)
	{
		boolean clientDisconnectSuccess = false;
		for(int i = 0; i<clientVector.size(); i++)
		{
			if(clientVector.get(i).getClientNumberDisplay() == clientNumber)
			{
				ClientHandler h = clientVector.get(i);
				h.disconnect();
				clientDisconnectSuccess = true;
				break;
			}
		}

		if(!clientDisconnectSuccess)
			System.out.println("Student not found, failed to disconnect Student " + clientNumber);
		else
			System.out.println("Student " + clientNumber + " successfully disconnected");
	}


	//gets the current time.
	public static String getCurrentTime() {
	    Date date = new Date();
	    SimpleDateFormat timeFormatter= new SimpleDateFormat("HH:mm:ss");
	    return timeFormatter.format(date);
	}

	//gets current date
	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
	}

	//gets wait time by calculating
	public static String getWaitTime(String timeReq) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date helpTime = null;
		Date cancelTime = null;
		String waitTime;

		try {
			helpTime = format.parse(timeReq);
			cancelTime = format.parse(getCurrentTime());

		} catch (ParseException e) {
			
			e.printStackTrace();
		}

		//calculates the time
		long difference =  cancelTime.getTime() - helpTime.getTime();

		long seconds = difference / 1000 % 60;
		long minutes = difference / (60 * 1000) % 60;
		long hours = difference / (60 * 60 * 1000);

        waitTime = minutes + ":" + seconds;

		return waitTime;
	}

	/*
	 * This method is for Help event the next method can handle both events
	 * Will remove this method when cancel button works
	 * This method is to insert into the database.
	 */
	 public static void insertHelpReq(int ID, String Event, String wksht, String role, String classCourse, int classSec, String timeReq) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
		String curDate = dateFormat.format(date);

		dao = new DataAccessObject();

		dao.connect();

		dao.setAutoCommit(false);;

		dao.executeSQLNonQuery("INSERT INTO EventsLog VALUES (" + ID + ", '" + Event + "', '" + wksht + "', '" + role + "', '" + classCourse + "', " +
				classSec + ", " + "TO_DATE('" + curDate + " " + timeReq + "', " + "'YYYY-MM-DD HH24:mi:ss')" + ", " + "null, " + "null" + ")");

		dao.commit();

		dao.disconnect();
	}

	//use this one for Help event & Cancel event and pass in the variables. Will update for better modularity when cancel button works
	 //	 * These method is to insert into the database.

	public static void insertEvent(int ID, String Event, String wksht, String role, String classCourse, int classSec, String timeReq,
										String curDate, String waitTime) throws ParseException {

		dao = new DataAccessObject();

		dao.connect();

		dao.setAutoCommit(false);;

		dao.executeSQLNonQuery("INSERT INTO EventsLog VALUES (" + ID + ", '" + Event + "', '" + wksht + "', '" + role + "', '" + classCourse + "', " +
				classSec + ", " + "TO_DATE('" + curDate + " " + timeReq + "', " + "'YYYY-MM-DD HH24:mi:ss')" + ", " + "INTERVAL '" + waitTime +"' MINUTE TO SECOND," +
				"TO_DATE('" + curDate + " " + getCurrentTime() + "', " + "'YYYY-MM-DD HH24:mi:ss')" + ")");

		dao.commit();

		dao.disconnect();
	}


}
