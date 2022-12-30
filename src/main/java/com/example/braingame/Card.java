package com.example.braingame;

import android.content.Context;
import android.content.res.TypedArray;
import android.widget.ImageView;

import java.io.Serializable;

public class Card implements Serializable {
    private int cardFront;
    private int cardBack;
    private int tag;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getImgId() {
        return cardFront;
    }

    public void setImgId(int imgId) {
        this.cardFront = imgId;
    }

    public Card(int imgId,int tag){
        this.cardFront = imgId;
        this.tag = tag;
    }
    public void setCardBack(int imgid){
        cardBack = imgid;
    }

}
