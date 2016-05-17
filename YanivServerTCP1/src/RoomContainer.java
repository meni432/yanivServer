
import java.util.Hashtable;
import java.util.Vector;

/**
 * singleton class
 *
 * @author Meni Samet
 */
public class RoomContainer {

    private static RoomContainer instance = null;       // instanse of Class singletone method
    private static int NUMBER_OF_PLAYERS = 3;           // number of player in one room

    private Vector<Room> rooms;                                 // vector that contain all active room in the server
    private Hashtable<Player, Room> playerToRoom;               // hash map point Player->Room

    /**
     * default constructor - singleton method
     */
    private RoomContainer() {
        playerToRoom = new Hashtable<>();
        rooms = new Vector<>();
    }

    /**
     * @return instance of RoomContainer (singleton method)
     */
    public static synchronized RoomContainer getInstanse() {
        if (instance == null) {
            instance = new RoomContainer();
        }
        return instance;
    }

    /**
     * find and return the room for individual Plater
     *
     * @param p get room for this player
     * @return room for the player
     */
    public Room getRoom(Player p) {
        if (playerToRoom.contains(p)) {
            return playerToRoom.get(p);
        } else {
            Room maxWaitRoom = getMaxWaitRoom();
            playerToRoom.put(p, maxWaitRoom);
            maxWaitRoom.insertNewPlayer(p);
            return maxWaitRoom;
        }
    }

    /**
     * @return get the most relevant room to insert player
     */
    private synchronized Room getMaxWaitRoom() {
        if (rooms.isEmpty()) {
            Room newRoom = new Room(NUMBER_OF_PLAYERS);
            rooms.add(newRoom);
            return newRoom;
        }
        Room maxRoom = rooms.get(0);
        int max = 0;
        for (int i = 0; i < rooms.size(); i++) {
            int numberOfPlayers = rooms.get(i).getNumberOfActivePlayers();
            if (numberOfPlayers > max && numberOfPlayers < NUMBER_OF_PLAYERS) {
                max = numberOfPlayers;
                maxRoom = rooms.get(i);
            }
            if (max == NUMBER_OF_PLAYERS - 1) {
                return maxRoom;
            }
        }
        return maxRoom;
    }
}
