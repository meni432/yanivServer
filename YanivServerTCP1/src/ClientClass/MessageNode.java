/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientClass;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Meni Samet
 */
public class MessageNode implements Serializable{
    public static enum MessageSign {
        GET_ROOM,
        ROOM_INFO,
        TAKE_FIRST,
        TAKE_LAST,
        TAKE_FROM_POT,
        TURN_CHANGE,
        CARDS_FOR_PLAYER,
        CARDS_FOR_POT,
        CARDS_FOR_MAIN_POT
    }
    public int id;
    public int initData;
    public Vector<Card> vectorCardsData;
    public MessageSign messageSign;

    @Override
    public String toString() {
        return "MessageNode{" + "id=" + id + ", initData=" + initData + ", vectorCardsData=" + vectorCardsData + ", messageSign=" + messageSign + '}';
    }

    
    
    
}