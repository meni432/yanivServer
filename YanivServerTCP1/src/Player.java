
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Vector;

import ClientClass.Card; // improt class Card from client Card
import ClientClass.MessageNode;

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
    private int idInRoom;
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
    
    public void sendMessage(MessageNode messageNode){
        try {
            getOutputStream().writeObject(messageNode);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int getSum() {
        return sum;
    }

    public Vector<Card> getCards() {
        return cards;
    }
    
     protected void calculateSum() {
        sum = 0;
        for (int i = 0; i < cards.size(); i++) {
            sum += cards.get(i).getVal();
        }
    }

    public int getIdInRoom() {
        return idInRoom;
    }

    public void setIdInRoom(int idInRoom) {
        this.idInRoom = idInRoom;
    }
     

}
