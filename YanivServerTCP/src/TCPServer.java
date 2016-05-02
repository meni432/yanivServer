
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class extends the Thread class so we can receive and send messages at the
 * same time
 */
public class TCPServer extends Thread {

    public static final int SERVERPORT = 4444;
    private boolean running = false;
    private PrintWriter mOut;
    private OnMessageReceived messageListener;

    static int id = 1;

    private ArrayList<Player> players = new ArrayList<>();

    /**
     * Constructor of the class
     *
     * @param messageListener listens for the messages
     */
    public TCPServer(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }

    /**
     * Method to send the messages from server to client
     *
     * @param message the message sent by the server
     */
    public void sendMessage(String message) {
        if (mOut != null && !mOut.checkError()) {
            mOut.println(message);
            mOut.flush();
        }
    }

    @Override
    public void run() {
        super.run();

        running = true;

        try {
            System.out.println("S: Connecting...");

            //create a server socket. A server socket waits for requests to come in over the network.
            final ServerSocket serverSocket = new ServerSocket(SERVERPORT);

            Socket client = serverSocket.accept();

            new Thread("Listener Thread") {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Socket client = serverSocket.accept();
//                            players.add(new Player(client, id++));
                        } catch (IOException ex) {
                            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }.start();

            try {

                for (Player player : players) {
                    //sends the message to the client
                    mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

                    //read the message received from client
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    //in this while we wait to receive messages from client (it's an infinite loop)
                    //this while it's like a listener for messages
                    while (running) {
                        String message = in.readLine();

                        if (message != null && messageListener != null) {
                            //call the method messageReceived from ServerBoard class
                            messageListener.messageReceived(message);
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("S: Error");
                e.printStackTrace();
            } finally {
                client.close();
                System.out.println("S: Done.");
            }

        } catch (Exception e) {
            System.out.println("S: Error");
            e.printStackTrace();
        }

    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the ServerBoard
    //class at on startServer button click
    public interface OnMessageReceived {

        public void messageReceived(String message);
    }

    public static void main(String[] args) {

        //opens the window where the messages will be received and sent
        TCPServer tcpServer;
        tcpServer = new TCPServer(new OnMessageReceived() {
            @Override
            public void messageReceived(String message) {
                System.out.println("message recived :" + message);
            }
        });
        tcpServer.start();
    }

}
