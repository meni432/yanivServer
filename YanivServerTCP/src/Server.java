
import com.google.gson.Gson;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.ReentrantLock;
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
public class Server {

    private static Server instance = null;

    private static final int PORT = 4600;               // server listener port
    private static final int NUMBER_OF_THREADS = 5;     // maximum number of thread allowed in thread pool
    public static final int TTW = 1000;                 // time to wait for reading from socket
    final List<Player> players = Collections.synchronizedList(new ArrayList<Player>());      // vector that store pointer to all connected Players
    ExecutorService executor;
    ReentrantLock addToListLock = new ReentrantLock(true);
    ServerSocket serverSocket;
    public static final int READER_LIMIT = 2; 

    /**
     * default constructor - singleton method
     */
    private Server() {
        this.executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);    // cread new thread pool
        // run players monitor in diffrent thread
        startServerConnection();
    }

    private void startServerConnection() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        new Thread(new ConnectionManager(serverSocket, players, addToListLock), "connection manager").start();                      // run socket server in diffrent thread
        new Thread(new PlayersMonitor(players, executor, addToListLock), "player monitor").start();
    }

    /**
     * get instance singleton run the socket (run only one time)
     *
     * @return Main instance
     */
    public static Server startServer() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public static void main(String[] args) {
        Server.startServer();
    }

}
