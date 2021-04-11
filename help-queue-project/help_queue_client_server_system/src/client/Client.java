/**
Seng Xai Yang
Maya Ledvina
CS 355
Sprint 2
5/7/20
**/



package client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.*;

public class Client {
	// data
	final static int ServerPort = 3074;
	final static String Host = "localhost";
	private static Socket clientSocket;
	private static String msg = ""; //error msg



//	@SuppressWarnings("resource")
	public static void main(String args[]) throws UnknownHostException, IOException
	{
//this was moved to the button

		/*Scanner scn = new Scanner(System.in);
		boolean runGUI = true;

		// getting host ip
		InetAddress ip = InetAddress.getByName(Host);

		try
		{
			// establish the connection
			clientSocket= new Socket(ip, ServerPort);

			// obtaining input and out streams
			DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

			String received = dis.readUTF();
			if(received.equals("abortGUI"))
			{
				runGUI = false;
			}
			else
			{
				runGUI = true;
			}

			Thread sendMessage = new Thread(new Runnable()
			{
				private boolean exit = false;


				@Override
				public void run() {
					while (!exit) {

						// read the message to deliver.
						msg = scn.nextLine();

						// write on the output stream
						try {
							dos.writeUTF(msg);
						} catch (IOException e) {

							//e.printStackTrace();
							exit = true;
						}
					}	// end - while

				}	// end - method run
			});	// end - thread sendMessage


			// readMessage thread
			Thread readMessage = new Thread(new Runnable()
			{
				private boolean exit = false;
				// private String name;   could new Thread(___, name) above;

				@Override
				public void run() {
					while (!exit) {
						try {
							// read the message sent to this client
							msg = dis.readUTF();
							System.out.println(msg);
							getError(msg);
						} catch (IOException e) {

							exit = true;
						}
					}	// end - while true

				}	// end - method run
			});	// end - thread readMessage

			sendMessage.start();
			readMessage.start();
			System.out.println("Client has connected to the server...");
		}
		catch(IOException e)
		{
			if(e instanceof SocketException)
			{
				try
				{
					System.out.println("Cannot connect to server...Server may be down or this ip may have already connected to server");
					clientSocket.close();
				} catch (IOException e1) {

				}

			}
			runGUI = false;
			System.out.println("Cannot connect to server...Server may be down or this ip may have already connected to server");


		}*/

		//if(runGUI)
			CreateGUI();


	}
//GUI start
public static void CreateGUI() {
	JFrame guiFrame = new JFrame();
		//make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Help Request");
		guiFrame.setSize(350,300);
		//This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);



		//Create the second JPanel. Add a JLabel and JList and
		//make use the JPanel is not visible.
		final JPanel listPanel = new JPanel();
		listPanel.setVisible(false);
		JLabel help = new JLabel(msg);
		JLabel jlaDisplayMessage = new JLabel();


		listPanel.add(help);
		listPanel.add(jlaDisplayMessage);


		//both buttons
		JButton helpBut = new JButton( "Request Help");
		helpBut.setForeground(Color.GREEN);
		JButton cancelBut = new JButton( "Cancel Request");
		cancelBut.setForeground(Color.RED);
		cancelBut.setVisible(false);


		//this is where it was moved
		helpBut.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event){
				Scanner scn = new Scanner(System.in);

				boolean runGUI = true;

				// getting host ip

				try
				{
					InetAddress ip = InetAddress.getByName(Host);

					// establish the connection
					clientSocket= new Socket(ip, ServerPort);

					// obtaining input and out streams
					DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
					DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

					String received = dis.readUTF(); //get command from server
					if(received.equals("abortGUI"))
					{
						runGUI = false;
					}
					else
					{
						runGUI = true;
					}

					Thread sendMessage = new Thread(new Runnable() //for error
					{
						private boolean exit = false;


						@Override
						public void run() { //this is stuff for errors
							while (!exit) {

								// read the message to deliver.
								msg = scn.nextLine();
								//msg = "help";

								// write on the output stream
								try {
									dos.writeUTF(msg);
								} catch (IOException e) {

									//e.printStackTrace();
									exit = true;
								}
							}	// end - while

						}	// end - method run
					});	// end - thread sendMessage
					sendMessage.start();
					System.out.println("Client has connected to the server...");

				}
				catch(IOException e)
				{
					if(e instanceof SocketException)
					{
						try
						{
							System.out.println("Cannot connect to server...Server may be down or this ip may have already connected to server");
							clientSocket.close();
						} catch (IOException e1) {

						}

					}
					runGUI = false;
					System.out.println("Cannot connect to server...Server may be down or this ip may have already connected to server");


				}

				jlaDisplayMessage.setFont(jlaDisplayMessage.getFont().deriveFont(18f));
				jlaDisplayMessage.setText("Your help request has been received.");
				listPanel.setVisible(!listPanel.isVisible());
				helpBut.setVisible(false);
				cancelBut.setVisible(true);

			}
		});

//this doesn't work currently I think we are overthinking it
		cancelBut.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event){
				try
				{
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
					listPanel.setVisible(!listPanel.isVisible());
					helpBut.setVisible(true);
					cancelBut.setVisible(false);
				}
			});



	guiFrame.add(listPanel, BorderLayout.CENTER);
	guiFrame.add(helpBut,BorderLayout.SOUTH);
	guiFrame.add(cancelBut,BorderLayout.NORTH);
	//make sure the JFrame is visible
	guiFrame.setVisible(true);
	}
//get message
	public static String getError(String error) {
		 if(error == null){
			 error = "Help Requested!";
		 }
		return error;
	}
}
