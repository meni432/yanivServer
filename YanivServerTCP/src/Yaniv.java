/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Ido
 */
public class Yaniv {
    int numOfPlayer;
    Cards c;
    Player players[];
    Stack<Card> primaryPot;
    boolean yaniv;
    int minYaniv = 5;

    public Yaniv(int numOfPlayer) {
        this.numOfPlayer = numOfPlayer;
        this.c = new Cards();
        this.players = new Player[numOfPlayer];
        createPlayers();
        this.primaryPot = new Stack<>();
        primaryPot.add(c.jackpot.remove(0));
        yaniv = false;
    }

    void print() {
        System.out.println("Number of players: " + numOfPlayer);
        for (int i = 0; i < numOfPlayer; i++) {
            System.out.println("player " + players[i].name + ": " + players[i].cards);
        }
        System.out.println("Jackpots: " + c.jackpot);
        System.out.println("Primary Pot: " + primaryPot);
    }

    void createPlayers() {
        for (int i = 0; i < numOfPlayer; i++) {
            players[i] = new Player("" + i, i);
            for (int j = 0; j < 5; j++) {
                players[i].cards.add(c.jackpot.remove(0));
            }
            players[i].calculateSum();
        }
    }

    void startGame() {
        int player = (int) (Math.random() * numOfPlayer);
        System.out.println("Enter 0 to take the card from the Primary Jackpot or 1 from the Jackpot");
        while (yaniv == false) {
            int num = player % numOfPlayer;
            System.out.println("\n=====    " + players[num].name + " turn    =====");
            if (checkYanivOpportunity(num) == 1) {
                break;
            }
            Scanner reader = firstQuestions(num);
            String[] split = reader.nextLine().split(",");
            int CVD = checkVaildDrop(reader, num, split);
            while (CVD != 1) {
                System.out.println("You enter illegal cards to drop, Try again. ");
                split = reader.nextLine().split(",");
                CVD = checkVaildDrop(reader, num, split);
            }
            System.out.println("Enter 0 to take the card from the Primary Jackpot or 1 from the Jackpot");
            int n = reader.nextInt();            if (n == 0) {
                takeFromPrimaryPot(split, num);
            }
            if (n == 1) {
                takeFromJackPot(split, num);
            }
            player++;
        }
    }

    private int checkVaildDrop(Scanner reader, int num, String[] split) {
        int Tokens[] = new int[split.length];
        if (Tokens.length == 1) { // Checking if there is only 1 card
            return 1;
        } else {
            for (int i = 0; i < Tokens.length; i++) {
                Tokens[i] = Integer.parseInt(split[i]);
                if (Tokens[i] >= players[num].cards.size()) { //Checking out of bounds
                    return 0;
                }
            }
            if (Tokens.length == 2) {  //Checking case of 2 jokers or 2 equals cards
                if (players[num].cards.get(Tokens[0]).num == players[num].cards.get(Tokens[1]).num) {
                    return 1;
                }
            }
            if (checkEqualArray(Tokens, num) == 1) { // Vaild drop of equals cards
                return 1;
            } else if (checkOrderArray(Tokens, num) == 1) {
                return 1;
            }
            return 0;

        }

    }

    private int checkEqualArray(int Tokens[], int num) {
        int val = -1;
        for (int i = 0; i < Tokens.length; i++) {
            if (players[num].cards.get(Tokens[i]).num != 0 && players[num].cards.get(Tokens[i]).num != val) {
                if (val == -1) {
                    val = players[num].cards.get(Tokens[i]).num;
                } else if (players[num].cards.get(Tokens[i]).num != val) {
                    return 0;
                }
            }

        }
        return 1;
    }

    private int checkOrderArray(int Tokens[], int num) {
        int[] arr = new int[Tokens.length];
        arr=Tokens;
        arr=bubbleSort(arr,num);
        int jokerCount = 0;
        if (players[num].cards.get(Tokens[0]).num == 0) {
            jokerCount++;
        }
        if (players[num].cards.get(Tokens[1]).num == 0) {
            jokerCount++;
        }
        for (int i = 0 + jokerCount; i < arr.length; i++) {
            if (i != arr.length - 1) {
                //Checking if there is the same symbol on the both cards
                if (players[num].cards.get(arr[i]).symbol != players[num].cards.get(arr[i + 1]).symbol) {
                    return 0;
                }
                System.out.println(""+Integer.toString(players[num].cards.get(arr[i]).num)+"   "+ Integer.toString(players[num].cards.get(arr[i+1]).num - 1));
                if (players[num].cards.get(arr[i]).num == players[num].cards.get(arr[i+1]).num - 1) {

                } else if (players[num].cards.get(arr[i]).num == players[num].cards.get(arr[i + 1]).num - 2) {
                    if (jokerCount > 0) {
                        jokerCount--;
                    } else {
                        return 0;
                    }
                } else if (players[num].cards.get(arr[i]).num == players[num].cards.get(arr[i + 1]).num - 3) {
                    if (jokerCount == 2) {
                        jokerCount = 0;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        }
        return 1;
    }
    int[] bubbleSort(int arr[], int num){
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (players[num].cards.get(arr[i]).num > players[num].cards.get(arr[j]).num){
                    int temp=arr[i];
                    arr[i]=arr[j];
                    arr[j]=temp;
                }
            }
        }
        return arr;
    }
    private int declareYaniv(int playerIndex, int sum) {
        System.out.println("\n========  You have " + sum + " Points, Declare Yaniv ?  ========");
        System.out.println("========       Enter 0 to NO, 1 to YES.       ========");
        Scanner reader = new Scanner(System.in);
        if (reader.nextInt() == 1) {

            for (int i = 0; i < numOfPlayer; i++) {
                if (i != playerIndex) {
                    if (players[i].sum <= sum) {
                        System.out.println("******************************************************");
                        System.out.println("******************************************************");
                        System.out.println("                     " + players[playerIndex].name + " LOSE :( ");
                        System.out.println("           " + players[i].name + " Declare ASSAF with " + players[i].sum + " Points.");
                        System.out.println("******************************************************");
                        System.out.println("******************************************************");
                        yaniv = true;
                        return 1;
                    }
                }
            }
            System.out.println();
            System.out.println("\n\n******************************************************");
            System.out.println("******************************************************");
            System.out.println("                " + players[playerIndex].name + " Win with " + players[playerIndex].sum + " Points.");
            System.out.println("******************************************************");
            System.out.println("******************************************************");

            yaniv = true;
            return 1;
        }
        return 0;
    }

    private int checkYanivOpportunity(int num) {
        if (players[num].sum < 6) {
            return declareYaniv(num, players[num].sum);
        }
        return 0;
    }

    private Scanner firstQuestions(int num) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Primary Pot: " + primaryPot.peek());
        System.out.println(players[num].name + " Cards: " + players[num].cards + " [sum: " + players[num].sum + "]");
        System.out.println("Enter the index's of cards to drop (with ',' between each index)");
        return reader;
    }

    private void takeFromPrimaryPot(String[] split, int num) {
        Card pop = primaryPot.pop();

        int Tokens[] = new int[split.length];
        for (int i = 0; i < Tokens.length; i++) {
            Tokens[i] = Integer.parseInt(split[i]);
        }
        Arrays.sort(Tokens);
        for (int i = Tokens.length - 1; i >= 0; i--) {
            int card = Tokens[i];
            System.out.println("card " + i + ": " + card + " (" + players[num].cards.get(card).num + ")");
            primaryPot.add(players[num].cards.remove(card));

        }
        System.out.println("You got from the Primary Pot: " + pop.toString());

        players[num].cards.add(pop);
        players[num].calculateSum();

        System.out.println("Your new Cards:  " + players[num].cards + " [sum: " + players[num].sum + "]");

    }

    private void takeFromJackPot(String[] split, int num) {
        Card pop = c.jackpot.remove(0);
        int Tokens[] = new int[split.length];

        for (int i = 0; i < Tokens.length; i++) {
            Tokens[i] = Integer.parseInt(split[i]);
        }
        Arrays.sort(Tokens);
        for (int i = Tokens.length - 1; i >= 0; i--) {
            int card = Tokens[i];
            System.out.println("card " + i + ": " + card + " (" + players[num].cards.get(card).num + ")");
            primaryPot.add(players[num].cards.remove(card));

        }
        System.out.println("You got from the JackPot: " + pop.toString());
        players[num].cards.add(pop);
        players[num].calculateSum();

        System.out.println("Your new Cards:  " + players[num].cards + " [sum: " + players[num].sum + "]");

    }

}
