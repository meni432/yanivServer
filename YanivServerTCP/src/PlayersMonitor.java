
import ClientClass.MessageNode;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Meni Samet
 */
class ReaderRunable implements Runnable {

    private final ExecutorService executor;
    private final Player player;
    private boolean finish = false;

    public boolean isFinish() {
        return finish;
    }

    public void resetFinish() {
        this.finish = false;
    }

    public ReaderRunable(ExecutorService executor, Player player) {
        this.executor = executor;
        this.player = player;
    }

    @Override
    public void run() {
        try {
            String read = (String) player.getInputStream().readObject();  // try read from socket
            Gson gson = new Gson();
            MessageNode messageNode = gson.fromJson(read, MessageNode.class);

            System.out.println("get message: " + messageNode);
            executor.execute(new MessageTask(player, messageNode));

            System.out.println("thread finish");
        } catch (IOException | ClassNotFoundException ex) {
//            ex.printStackTrace();
        } finally {
            finish = true;
        }
    }

}

public class PlayersMonitor implements Runnable {

    private final List<Player> players;       // pointer to vector store connected player in this moment
    private boolean isRunning = false;          // server is running
    private final ExecutorService executor;     // pointer to executor (thread pool) manager
    ReentrantLock addToListLock;
    public static int listenerId = 1;

    ExecutorService readerExecutors;
    HashMap<Player, ReaderRunable> playerToReaderRunable = new HashMap<>();

    /**
     * Default Constructor
     *
     * @param players pointer to vector store connected player in this moment
     * @param executor pointer to executor (thread pool) manager
     * @param addToListLock
     */
    public PlayersMonitor(final List<Player> players, final ExecutorService executor, ReentrantLock addToListLock) {
        Thread.currentThread().setName("Player monitor");
        this.players = players;
        this.isRunning = true;
        this.executor = executor;
        this.addToListLock = addToListLock;
        readerExecutors = Executors.newFixedThreadPool(Server.READER_LIMIT);
    }

    @Override
    public void run() {

        while (isIsRunning()) {
            addToListLock.lock();
            try {
                for (Player player : players) {
//                    if (playerToReaderRunable.get(player) == null) {
//                        ReaderRunable readerRunable = new ReaderRunable(executor, player);
//                        readerExecutors.execute(readerRunable);
//                        playerToReaderRunable.put(player, readerRunable);
//                        if (playerToReaderRunable.size() == Server.READER_LIMIT) {
//                            readerExecutors.shutdown();
//                            List<Runnable> finish = readerExecutors.shutdownNow();
//                            for (Runnable finiRunnable : finish) {
//                                ReaderRunable current = (ReaderRunable) finiRunnable;
//                                playerToReaderRunable.remove(current);
//                            }
//                            System.out.println("after terminated");
//                            readerExecutors = Executors.newFixedThreadPool(Server.READER_LIMIT);
//                        }
//                    }

                    if (!playerToReaderRunable.containsKey(player)) {
                        ReaderRunable readerRunable = new ReaderRunable(executor, player);
                        playerToReaderRunable.put(player, readerRunable);
                        readerExecutors.execute(readerRunable);
                    } else {
                        ReaderRunable readerRunable = playerToReaderRunable.get(player);
                        if (readerRunable.isFinish()){
                            readerRunable.resetFinish();
                            readerExecutors.execute(readerRunable);
                        }
                    }

                }

            } finally {
                addToListLock.unlock();
            }
        }
    }

    void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        this.isRunning = false;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

}
