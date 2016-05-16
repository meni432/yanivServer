
import ClientClass.Card;
import ClientClass.Cards;
import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author Meni Samet
 */
public class Room {

    private static int serialId = 1;
    int id;

    // Game variable
    int numOfPlayer;                 // Number of the players.

    Cards Deck;                      // Deck - Random deck of cards.
    Player players[];                // Array of all the players.
    Stack<Vector<Card>> mainPile;    // Stack of all the droped cards (Vector).
    boolean yaniv;
    static int player;

    int numberOfActivePlayers = 0;
    final Object activePlayerCounterLock = new Object();

    public Room(int numOfPlayer) {
        this.numOfPlayer = numOfPlayer;             // Initial the incoming number of players.
        this.Deck = new Cards();                    // Initial randomly the Card deck with all 54 cards.
        this.players = new Player[numOfPlayer];     // *** Needs to create the players by threads(?)
        initialMainPile();                          // Added first card to the mainPile.
        createPlayers();                            // Distribute the cards fot all the players and more.
        yaniv = false;
    }

    protected void initialMainPile() {
        Vector<Card> firstCard = new Vector<>();        // Create new vector of cards.
        firstCard.add(Deck.takeCard());                 // Take the first card from the deck.
        mainPile.add(firstCard);                        // Added the first card vector to the main pile.
        // need to draw the card
    }

    protected void createPlayers() {
//        Vector<Card> vect = new Vector<>();
//        for (int i = 0; i < numOfPlayer; i++) {
//            players[i] = new Player("" + i, i);
//            for (int j = 0; j < 5; j++) {
//                players[i].getCards().add(Deck.getCards().remove(0));
//            }
//            players[i].calculateSum();
//        }
//        vect.addAll(players[0].getCards());
//        // *** Needs to call the function in game class to Draw the cards.
//        // drawCards(vect);
    }

    private void dropCards(Vector<Card> dropCards) {
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
                if (players[i].getSum() <= sum) {
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

    public void incNumberOfActivePlayers() {
        synchronized (activePlayerCounterLock) {
            this.numberOfActivePlayers++;
        }
    }

}
