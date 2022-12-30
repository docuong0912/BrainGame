package com.example.braingame;

import com.example.braingame.Card;
import com.example.braingame.CardList;
import com.example.braingame.GameHandler;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private ArrayList<Card> selected;
    private int intselected;
    private int[] score = new int[5];
    private int gamenum;

    public int getGamenum() {
        return gamenum;
    }

    public Player(int gamenum){
        this.gamenum = gamenum;
        selected = new ArrayList<>();


    }

    public void setSelected(ArrayList<Card> selected) {
        this.selected = selected;
    }

    public int getScore(int gamenum){
        return this.score[gamenum];
    }
    public void setScore(int score,int gamenum) {
        this.score[gamenum] = score;
    }

    public Player(int score, int gamenum){
        this.score[gamenum] = score;
        selected = new ArrayList<>();
    }

    public ArrayList<Card> getSelected() {
        return selected;
    }
    public int getIntSelected(){
        return intselected;
    }
    public void selects(Card card){
        selected.add(card);
    }
    public void selects(int select){
        intselected = select;
    }
}
