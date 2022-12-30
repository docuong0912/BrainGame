package com.example.braingame.MemorizeGame1;

import android.util.Log;

import androidx.core.math.MathUtils;

import java.util.Random;

public class TileMap {
    private int width,height;
    private int[][] mat;
    private int level;
    private int current = 0;

    public int getLevel() {
        return level;
    }

    public TileMap(int level){
        this.level = level;
        setLevel(level);
        mat = new int[height][width];
        Random ran = new Random();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mat[i][j] = ran.nextInt(2);
                mat[i][j] = isAns(mat[i][j]);

            }
        }
    }
    private void setLevel(int level){
        switch (level){
            case 1:
                height = 3;
                width =2;
                break;
            case 2:
                width = 3;
                height =3;
                break;
            case 3:
                width = 3;
                height =4;
                break;
            case 4:
                width = 4;
                height = 4;
                break;
            case 5:
            case 6:
                width = 4;
                height = 5;
                break;
            case 7:
                width = 5;
                height=5;
                break;
            case 8:
            case 9:
                width = 5;
                height=6;
                break;
            case 10:
                width = 6;
                height=6;
                break;
            case 11:
            case 12:
                width = 6;
                height=7;
                break;
            case 13:
            case 14:
                width = 7;
                height=7;
                break;
            case 15:
            case 16:
                width = 7;
                height=8;
                break;
            case 17:
            case 18:
            case 19 :
                width = 8;
                height=8;
                break;
            case 20:
            case 21:
                width = 8;
                height=9;
                break;
            case 22:
            case 23:
            case 24:
                width = 9;
                height=9;
                break;
            case 25:
            case 26:
            case 27:
                width = 9;
                height=10;
                break;
            case 28:
            case 29:
            case 30:
                width = 10;
                height=10;
                break;

        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMat(int j, int i){
        return mat[i][j];
    }
    private int isAns(int ans){
        if(current != level){
            if(ans ==1 ){
                current++;
            }
            return ans;
        }
       else return 0;
    }
}
