
import ClientClass.MessageNode;

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

    int number;
    int id;
    final Player ouwner;

    // Message Node data
    int messageId;
    int type;
    Object data;

    public MessageTask(Player ouwner, MessageNode messageNode) {
        this.messageId = messageNode.id;
        this.type = messageNode.type;
        this.data = messageNode.data;

        this.ouwner = ouwner;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
