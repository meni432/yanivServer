
import ClientClass.MessageNode;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Meni Samet
 */
public class PlayersMonitor implements Runnable {

    private final Vector<Player> players;       // pointer to vector store connected player in this moment
    private boolean isRunning = false;          // server is running
    private final ExecutorService executor;     // pointer to executor (thread pool) manager

    /**
     * Default Constructor
     *
     * @param players pointer to vector store connected player in this moment
     * @param executor pointer to executor (thread pool) manager
     */
    public PlayersMonitor(final Vector<Player> players, final ExecutorService executor) {
        this.players = players;
        this.isRunning = true;
        this.executor = executor;
    }

    @Override
    public void run() {
        while (isRunning) {
            for (final Player player : players) {                   // pass all player in players vector
                final boolean[] readSuccess = {false};              // flag that sign if TCP Listrner thread succsses read from socket
                Thread listener = new Thread("TCP Listener") {
                    @Override
                    public void run() {
                        try {
                            MessageNode read = (MessageNode) player.getInputStream().readObject();  // try read from socket
                            executor.execute(new MessageTask(player, read));
                            readSuccess[0] = true; // turn on flag read
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }

                };
                listener.start();
                try {
                    listener.join(Main.TTW);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                // if can't read in TTW, interrupt the reading
                if (readSuccess[0] = false) {
                    listener.interrupt();
                }

            }
        }
    }

    public void stop() {
        this.isRunning = false;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

}
