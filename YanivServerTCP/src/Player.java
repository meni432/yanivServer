/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Vector;

/**
 * @author Ido
 */
public class Player {
    Vector<Card> cards;
    String name;
    int index;
    int sum;

    public Player(String name, int index) {
        this.cards = new Vector();
        this.name = name;
        this.index = index;
        this.sum = 0;
    }

    void calculateSum() {
        sum = 0;
        for (int i = 0; i < cards.size(); i++) {
            sum += cards.get(i).val;
        }
    }

}
