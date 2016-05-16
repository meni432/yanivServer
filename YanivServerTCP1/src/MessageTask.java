/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Meni Samet
 */
public class MessageTask implements Runnable{
    
    int number;
    int id;
    Object data;
    Player ouwner;
    
    
    public MessageTask(Player ouwner){
        this.ouwner = ouwner;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
