
import ClientClass.Card;
import ClientClass.Cards;
import ClientClass.MessageNode;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Meni Samet
 */
public class Room {

    private static int serialId = 0;
    int id;

    // Game variable
    int numOfPlayer;                 // Number of the players.
    int numberOfActivePlayers = 0;

    Cards Deck;                      // Deck - Random deck of cards.
    final Vector<Player> players;          // Array of all the players.
    Stack<Vector<Card>> mainPile;    // Stack of all the droped cards (Vector).
    boolean yaniv;
    static int player;
    
    private int currentTurn = -1;

    final Object activePlayerCounterLock = new Object();

    public Room(int numOfPlayer) {
        this.numOfPlayer = numOfPlayer;             // Initial the incoming number of players.
        this.Deck = new Cards();                    // Initial randomly the Card deck with all 54 cards.
        this.players = new Vector<>();              // *** Needs to create the players by threads(?)
//        initialMainPile();                          // Added first card to the mainPile.
//        createPlayers();                            // Distribute the cards fot all the players and more.
        yaniv = false;
        this.id = serialId++;

    }

    protected void initialMainPile() {
        Vector<Card> firstCard = new Vector<>();        // Create new vector of cards.
        firstCard.add(Deck.takeCard());                 // Take the first card from the deck.
        mainPile.add(firstCard);                        // Added the first card vector to the main pile.
        // need to draw the card
    }

    protected void createPlayers() {
        Vector<Card> vect = new Vector<>();
        for (int i = 0; i < numOfPlayer; i++) {
            for (int j = 0; j < 5; j++) {
                players.get(i).getCards().add(Deck.getCards().remove(0));
            }
            players.get(i).calculateSum();
        }
        vect.addAll(players.get(0).getCards());
        // *** Needs to call the function in game class to Draw the cards.
        // drawCards(vect);
    }

    public void dropCards(Vector<Card> dropCards) {
        // Needs to add the drop cards to the mainPile and update the mainPile Layout
        mainPile.add(dropCards);
        
    }

    // The function return new card from the deck.
    private Card takeFromDeck() {
        return Deck.takeCard();
    }

    private void declareYaniv(int playerIndex, int sum) {
        for (int i = 0; i < numOfPlayer; i++) {
            if (i != playerIndex) {
                if (players.get(i).getSum() <= sum) {
                    yaniv = true;
                    //player[i] win with ASSAF
                    // GAME END
                }
            }
        }
        //player[playerIndex] is the winner with YANIV
        // GAME END
        yaniv = true;
    }

    public int getNumberOfActivePlayers() {
        synchronized (activePlayerCounterLock) {
            return numberOfActivePlayers;
        }
    }

    public void setNumberOfActivePlayers(int numberOfPlayers) {
        synchronized (activePlayerCounterLock) {
            this.numberOfActivePlayers = numberOfPlayers;
        }
    }

    /**
     * inset new player to the room if room is full, start game
     *
     * @param newPlayer Player
     */
    public void insertNewPlayer(Player newPlayer) {
        synchronized (activePlayerCounterLock) {
            this.numberOfActivePlayers++;
            synchronized (players) {
                this.players.add(newPlayer);
                if (players.size() == numOfPlayer) {
                    startGame();
                }
            }
        }
    }

    /**
     * initials main pile and send the cards to the clients (players)
     */
    public void startGame() {
        synchronized (players) {
            initialMainPile();
            // send initial cards to all players
            for (Player player : players) {
                MessageNode messageNode = new MessageNode();
                messageNode.messageSign = MessageNode.MessageSign.CARDS_FOR_PLAYER;
                messageNode.data = player.getCards();
                player.sendMessage(messageNode);

                // send message game start (set turn to player 0 )
                setCurrentTurn(0);
            }

        }
    }

    public synchronized int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * set new turn, and send the new turn to all players
     * @param currentTurn 
     */
    public synchronized void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
        for (Player player : players){
            MessageNode messageNode = new MessageNode();
            messageNode.messageSign = MessageNode.MessageSign.TURN_CHANGE;
            messageNode.data = currentTurn;
        }
    }
    
    public synchronized void nextTurn(){
        int current = getCurrentTurn();
  
        setCurrentTurn(currentTurn%numOfPlayer);
    }

    public int getId() {
        return id;
    }

}
