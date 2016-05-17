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
public class Card {

    int num;
    char symbol;
    int val;
    String type;
    public Card(int num, char symbol) {
        this.num = num;
        this.symbol = symbol;
        if (num > 10) {
            this.val = 10;
        } else {
            this.val = num;
        }

        switch(symbol){
            case '\u2764':
                type = "hearts";
                break;
            case '\u2660':
                type = "spades";
                break;
            case '\u2666':
                type = "diamonds";
                break;
            case '\u26C7':
                type = "black_joker";
                break;
            case '\u2663':
                type = "clubs";
                break;
            case '\u2603':
                type = "red_joker";
                break;
        }
    }

    public Card (Card card){
        this.num = card.num;
        this.symbol = card.symbol;
        this.val = card.val;
        this.type = card.type;
    }

    public String cardToString(){
        return "card_" + num + "_of_"+ type;
    }

    @Override
    public String toString() {
        return "" + num + symbol;
    }

    public int getVal() {
        return val;
    }
    
}