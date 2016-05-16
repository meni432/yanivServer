
import ClientClass.MessageNode;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
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
public class PlayersMonitor implements Runnable {

    private final Vector<Player> players;
    private boolean isRunning = false;
    private final ExecutorService executor;

    public PlayersMonitor(Vector<Player> players, ExecutorService executor) {
        this.players = players;
        this.isRunning = true;
        this.executor = executor;

    }

    @Override
    public void run() {
        while (isRunning) {
            for (final Player player : players) {
                boolean read = false;
                Thread listener = new Thread("TCP Listener") {
                    @Override
                    public void run() {
                        try {
                            MessageNode read = (MessageNode) player.getInputStream().readObject();

                            executor.execute(new MessageTask(player, read));
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
                if (read = false) {
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
