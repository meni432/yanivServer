
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Meni Samet
 */
public class Server implements Runnable {

    int serverPort = 45000; // The port that this server is listening on
    ServerSocket serverSocket = null;  // Server socket that will listen for incoming connections
    Thread runningThread = null;
    boolean isStopped = false;
    Vector<Player> players = null;

    public Server(int port, Vector<Player> players) {
        this.serverPort = port;
        this.players = players;
    }

    @Override
    public void run() {
        //open server Socket
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            System.err.println("Cannot listen on this port.\n" + e.getMessage());
            System.exit(1);
        }
        while (!isStopped()) {
            Socket clientSocket = null;  // socket created by accept

            try {
                clientSocket = this.serverSocket.accept(); // wait for a client to connect

            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("IOException: Server Stopped.");
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);    //Accept failed
            }
            //server code here ...
            System.out.println("Server accepts the client connection");

            // Client information               
            InetAddress addr = clientSocket.getInetAddress();
            System.out.println("Server: Received a new connection from (" + addr.getHostAddress() + "): " + addr.getHostName() + " on port: " + clientSocket.getPort());
            String clientInfo = "";
            clientInfo = "Client on port " + clientSocket.getPort();
            
            
            try {
                players.add(new Player(clientSocket, addr));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            

        }

    }
    
    public boolean isStopped(){
        return false;
    }
}
