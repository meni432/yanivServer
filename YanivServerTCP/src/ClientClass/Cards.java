/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientClass;

import java.util.Vector;

/**
 *
 * @author Meni Samet
 */
public class Cards {

    Vector<Card> Cards;

    public Cards() {
        Cards = new Vector<>();
        createJackPot();
    }

    public Vector<Card> getCards() {
        return Cards;
    }

    public Card takeCard() {
        // TODO what if the deck is empty?
        return Cards.remove(0);
    }

    void createJackPot() {
        Card temp[] = new Card[54];
        for (int i = 2; i < 54; i = i + 4) {
            int num = (i / 4) + 1;
            temp[i] = new Card(num, (char) '\u2764'); //hearts
            temp[i + 1] = new Card(num, (char) '\u2660'); //spades 
            temp[i + 2] = new Card(num, (char) '\u2666'); //diamonds
            temp[i + 3] = new Card(num, (char) '\u2663'); //clubs
        }
        temp[0] = new Card(0, (char) '\u26C7'); // black joker
        temp[1] = new Card(0, (char) '\u2603'); // red joker
        int counter = 0;
        while (counter < 53) {
            int rand = (int) (Math.random() * 54);
            if (temp[rand] != null) {
                Cards
                        .add(temp[rand]);
                counter++;
                temp[rand] = null;
            }
        }

    }
}
