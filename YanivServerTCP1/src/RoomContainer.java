
import java.util.Hashtable;
import java.util.Vector;

/**
 * singleton class
 *
 * @author Meni Samet
 */
public class RoomContainer {
    
    private static int NUMBER_OF_PLAYERS = 3;

    private static RoomContainer instanse = null;
    Vector<Room> rooms;
    Hashtable<Player, Room> playerToRoom;

    private RoomContainer() {
        playerToRoom = new Hashtable<>();
        rooms = new Vector<>();
    }

    public synchronized RoomContainer getInstanse() {
        if (instanse == null) {
            instanse = new RoomContainer();
        }
        return instanse;
    }

    public Room getRoom(Player p) {
        if (playerToRoom.contains(p)) {
            return playerToRoom.get(p);
        } else {
            Room maxWaitRoom = getMaxWaitRoom();
            maxWaitRoom.incNumberOfActivePlayers();
            playerToRoom.put(p, maxWaitRoom);
            return maxWaitRoom;
        }
    }
    
    
    private synchronized Room getMaxWaitRoom(){
        if (rooms.isEmpty()){
            Room newRoom = new Room(NUMBER_OF_PLAYERS);
            rooms.add(newRoom);
            return newRoom;
        }
        Room maxRoom = rooms.get(0);
        int max = 0;
        for (int i = 0; i < rooms.size(); i++){
            int numberOfPlayers = rooms.get(i).getNumberOfActivePlayers();
            if (numberOfPlayers > max &&  numberOfPlayers < NUMBER_OF_PLAYERS){
                max = numberOfPlayers;
                maxRoom = rooms.get(i);
            }
        }
        return maxRoom;
    }
}
