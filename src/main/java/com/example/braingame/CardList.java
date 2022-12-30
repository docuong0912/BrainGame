package com.example.braingame;

import android.content.res.TypedArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardList implements Serializable {
    private ArrayList<Card> cards;
    private ArrayList<Card> ansPicsFront;
    private ArrayList<Card> ansPicBack;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }


    public CardList(){

        cards = new ArrayList<>();
        ansPicsFront = new ArrayList<>();
        ansPicBack = new ArrayList<>();
        //ans position


        shuffle();
    }

    public void shuffle(){
        Random random = new Random();
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Collections.swap(cards,i,j);
        }
    }
    public void shuffleAns(){
        Random random = new Random();
        for (int i = ansPicsFront.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Collections.swap(ansPicsFront,i,j);
        }
    }
    public ArrayList<Card> getAnsPics() {
        return ansPicsFront;
    }

    public ArrayList<Card> getAnsPicBack() {
        return ansPicBack;
    }


}
