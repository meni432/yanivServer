
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Meni Samet
 */
public class Main {
    
    private static Main instance = null;
    
    private static final int PORT = 4000;               // server listener port
    private static final int NUMBER_OF_THREADS = 5;     // maximum number of thread allowed in thread pool
    public static final int TTW = 1000;                 // time to wait for reading from socket
    final Vector<Player> players = new Vector<>();      // vector that store pointer to all connected Players
    ExecutorService executor;
    
    /**
     * default constructor  - singleton method
     */
    private Main(){
        this.executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);    // cread new thread pool
        new Thread(new Server(PORT, players)).start();                      // run socket server in diffrent thread
        new Thread(new PlayersMonitor(players, executor)).start();          // run players monitor in diffrent thread
    }
    
    /**
     * get instance singleton 
     * run the socket (run only one time)
     * @return Main instance
     */
    public static Main startServer(){
        if (instance == null){
            instance = new Main();
        }
        return instance;
    }
    
    public static void main(String[] args) {
        Main.startServer();
    }
    
}
