
package displayGui;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import java.util.concurrent.ScheduledExecutorService;

import database.DataAccessObject;
import server.ClientHandler;

import server.Server;

public class UpdatedDisplay implements Runnable{

    static Vector<ClientHandler> clientVector = new Vector<>();
    private Socket displaySocket;

    private ScheduledExecutorService scheduler;

    private DataInputStream dis;
    private DataOutputStream dos;
    
    public UpdatedDisplay(Socket dSocket, Vector<ClientHandler> aVector, ScheduledExecutorService updates)
    {
        displaySocket = dSocket;
        clientVector = aVector;
        scheduler = updates;
        try
        {
            dis = new DataInputStream(displaySocket.getInputStream());
            dos = new DataOutputStream(displaySocket.getOutputStream());
        }
        catch (IOException e)
        {

        }


    }

    //called when thread starts
    public void run()
    {
        String clientNames = "";
        String time= "";
        int num = 0;

        for (int i = 0; i < Server.clientVector.size(); i++) {
            if (Server.clientVector.get(i).isloggedin == true) {
                clientNames = clientNames.concat(Server.clientVector.get(i).name + "," + Server.clientVector.get(i).timeReq + ",");
                num++;
            }
        }



        try
        {
            dos.writeUTF(clientNames);
        } catch (Exception e) {
            if(e instanceof SocketException)
            {
                try
                {
                    System.out.println("Socket error");
                    scheduler.shutdown();
                    displaySocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            System.out.println("Display Disconnected, Server lost connection to the display");
        }

        return;
    }
    
    
}
