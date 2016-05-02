/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Vector;

/**
 *
 * @author Ido
 */
    public class Cards {

    Vector<Card> jackpot;

    public Cards() {
        jackpot = new Vector<>();
        createJackPot();
    }

    void createJackPot() {
        Card temp[] = new Card[54];
        for (int i = 2; i < 54; i = i + 4) {
            int num = (i / 4) + 1;
            temp[i] = new Card(num, (char)'\u2764'); //hearts
            temp[i + 1] = new Card(num , (char)'\u2660'); //spades 
            temp[i + 2] = new Card(num , (char)'\u2666'); //diamonds
            temp[i + 3] = new Card(num, (char)'\u2663'); //clubs
        }
        temp[0] = new Card(0, (char)'\u2668');
        temp[1] = new Card(0, (char)'\u2668');
        int counter = 0;
        while (counter < 53) {
            int rand = (int) (Math.random() * 54);
            if (temp[rand] != null) {
                jackpot.add(temp[rand]);
                counter++;
                temp[rand]=null;
            }
        }

    }
}
