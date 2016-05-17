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
    public enum MessageSign {
        GET_ROOM,
        TAKE_FIRST,
        TAKE_LAST,
        TAKE_FROM_POT
    }
    public int id;
    public int type;
    public Object data;
    public MessageSign messageSign;
}
