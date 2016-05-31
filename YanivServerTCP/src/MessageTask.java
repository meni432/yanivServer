
import ClientClass.Card;
import ClientClass.MessageNode;
import java.io.IOException;
import java.util.Vector;
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
public class MessageTask implements Runnable {

    // Server data
    int number;
    int id;
    final Player ouwner;

    // Message Node data
    int messageId;
    int initData;
    Vector<Card> vectorCardsData;
    MessageNode.MessageSign messageSign;

    /**
     * default constructor
     *
     * @param ouwner Player object that create the task
     * @param messageNode messageNode that include details from client
     */
    public MessageTask(Player ouwner, MessageNode messageNode) {
        this.messageId = messageNode.id;
        this.initData = messageNode.initData;
        this.vectorCardsData = messageNode.vectorCardsData;
        this.messageSign = messageNode.messageSign;
        this.ouwner = ouwner;
    }

    @Override
    public void run() {
        if (null != messageSign) {

            switch (messageSign) {
                case GET_ROOM:
                    sendRoomInfo();
                    break;
                case TAKE_FROM_POT:
                    getFromPot();
                    break;
                case CARDS_FOR_MAIN_POT:
                    dropCard();
                    break;
                default:
                    break;
            }
        }
        
        System.out.println("end task");
    }


    private void dropCard(){
        Vector<Card> cards = vectorCardsData;
        Room room = RoomContainer.getInstanse().getRoom(ouwner);
        if (ouwner.getIdInRoom() == room.getCurrentTurn()){
            room.dropCards(cards);
        }
    }

    private void getFromPot() {
        
    }

    private void sendRoomInfo() {
        MessageNode messageNode = new MessageNode();
        messageNode.messageSign = MessageNode.MessageSign.ROOM_INFO;
        Room room = RoomContainer.getInstanse().getRoom(ouwner);
        messageNode.initData = ouwner.getIdInRoom();
        ouwner.sendMessage(messageNode);
    }

}
