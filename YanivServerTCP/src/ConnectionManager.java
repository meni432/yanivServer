
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Meni Samet
 */
public class ConnectionManager implements Runnable {

    ServerSocket serverSocket = null;  // Server socket that will listen for incoming connections
    Thread runningThread = null;
    boolean isStopped = false;
    final List<Player> players;
    
    ReentrantLock addToListLock;

    public ConnectionManager(ServerSocket serverSocket, final List<Player> players, ReentrantLock addToListLock) {
        this.players = players;
        this.addToListLock = addToListLock;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
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
                addToListLock.lock();
                Player player = new Player(clientSocket, addr);
                players.add(player);
            } catch (IOException ex) {
                ex.printStackTrace();
            }finally{
                addToListLock.unlock();
            }

        }

    }

    public boolean isStopped() {
        return false;
    }
}
