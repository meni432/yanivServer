
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
    
    private static final int PORT = 4000;
    private static final int NUMBER_OF_THREADS = 5;
    public static final int TTW = 1000; // time to wait
    final Vector<Player> players = new Vector<>();
    ExecutorService executor;
    
    public Main(){
        this.executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        new Thread(new Server(PORT, players)).start();
        new Thread(new PlayersMonitor(players, executor)).start();
    }
    
    public static void main(String[] args) {
        new Main();
    }
    
}
