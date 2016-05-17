/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientClass;

/**
 *
 * @author Meni Samet
 */
public class MessageNode {
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
    public Object data;
    public MessageSign messageSign;
}
