
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Vector;

import ClientClass.Card; // improt class Card from client Card

/**
 *
 * @author Meni Samet
 */
public class Player {

    // Server data
    private int id;
    private static int serialId = 1;

    // Network Variable
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private Socket socket = null;
    private InetAddress networkInformation = null;
    
    // Game Variable
    private Vector<Card> cards;
    private String name;
    private int index;
    private int sum;

    /**
     * default constructor
     * @param socket pointer to the socket for I\O operation
     * @param inetAddress network information
     * @throws IOException 
     */
    public Player(final Socket socket, InetAddress inetAddress) throws IOException {
        this.socket = socket;
        this.networkInformation = inetAddress;
        this.id = serialId++;

        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public int getSum() {
        return sum;
    }
    
    
    
    

}
