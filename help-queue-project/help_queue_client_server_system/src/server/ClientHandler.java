
package server;

import java.io.*;
import java.util.*;
import java.net.*;

//ClientHandler class
public class ClientHandler implements Runnable {
	// data
	//Scanner scn = new Scanner(System.in); 		// ???
	//private String name; 							// client name
	public String name; // client name
	InetAddress ip;
	DataInputStream dis; 						// input stream for this client
	DataOutputStream dos; 					// output stream for this client
	Socket s; 										// socket for this client
	public boolean isloggedin; 							// flag, whether client is currently connected
  public String timeReq;
		private int clientNumberDisplay;									// time client requested
	// constructor
	public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos, InetAddress ip, String timeReq) {
		this.dis = dis;
		this.dos = dos;
		this.name = name;
		this.s = s;
		this.ip = ip;
		this.isloggedin = true;
		this.timeReq = timeReq;
	}	// end - constructor

	// run method - called when thread starts
	@Override
	public void run() {
		String received;
		boolean run = true;
		while (run)
		{
			try
			{
				// receive the string
				received = dis.readUTF();
				System.out.println(received);
				boolean makeGUI = true;


				if(received.equals("logout")){
					this.isloggedin = false;
					this.s.close();
					break;

			}

			if(received.equals("abortGUI"))
							{
									makeGUI = false;
									this.dos.writeUTF("abortGUI");
							}
			else
							{
								 this.dos.writeUTF("makeGUI");
				 				 //this.dos.writeUTF("help");

							}

				// break the string into message and recipient part
				StringTokenizer st = new StringTokenizer(received, "#");
				String MsgToSend = st.nextToken();
				String recipient = st.nextToken();


				for (ClientHandler mc : Server.clientVector) {
					// if the recipient is found, write on its output stream
					if (mc.name.equals(recipient) && mc.isloggedin == true)
					{
						mc.dos.writeUTF(this.name +" : "+ MsgToSend);
						break;
					}
				}
			} catch (IOException e) {
				if(e instanceof SocketException)
					{
						try
						{
							System.out.println("Socket error for student " + this.name);
							run = false;
							s.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					this.isloggedin = false;
				}
		}	// end - while true

		// closing resources
		try {
			this.dis.close();
			this.dos.close();
		} catch(IOException e) {
			System.out.println("ClientHandler, closing resources section");
			e.printStackTrace();
		}
	} 	// end - method run

	public void reconnectClient(Socket s) throws IOException {
		this.s = s;
		this.isloggedin = true;

		this.dis = new DataInputStream(s.getInputStream());
		this.dos = new DataOutputStream(s.getOutputStream());

	}

	public void setClientNumberDisplay(int clientNumberDisplay) {
		this.clientNumberDisplay = clientNumberDisplay;
	}
	public int getClientNumberDisplay() {
		return clientNumberDisplay;
	}

	public void disconnect()
	{
		this.isloggedin = false;
		try
		{
			this.s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
