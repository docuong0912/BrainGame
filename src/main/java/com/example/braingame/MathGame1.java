package com.example.braingame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathGame1 extends AppCompatActivity implements GameHandler {
    TextView ex1,ex2;
    List<TextView> view;
    int level;
    Player player;
    int selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_game1);
        view = new ArrayList<>();
        player = new Player(2);
        ex1 = findViewById(R.id.expression1);
        ex2 = findViewById(R.id.expression2);
        Random random = new Random();
        ex1.setText(Integer.toString(random.nextInt(100)));
        ex2.setText(Integer.toString(random.nextInt(100)));
        
        
        ex1.setTag(1);
        ex2.setTag(2);

        view.add(ex1);
        view.add(ex2);

    }

    @Override
    public void RoundSuccess() {
        int temp = level+1;

        // int tempSCore = score + map.getLevel()*100;
        Intent intent = getIntent();
        intent.putExtra("level",temp);
        //  intent.putExtra("score",tempSCore);
        finish();
        startActivity(intent);
    }

    @Override
    public void RoundOver() {
        int temp = level;
        Intent intent = getIntent();
        intent.putExtra("level",temp);

        finish();
        startActivity(intent);
    }

    @Override
    public void Scoring(int score) {
        player.setScore(500 + score,player. getGamenum());
    }

    public void selects(View view) {
        TextView v = (TextView) view;
        check(v);
    }
    private void check(TextView view){
        for (int i = 0; i < this.view.size(); i++) {
            if(this.view.get(i).getTag()!=view.getTag()){
                int ex1 = Integer.parseInt((String) this.view.get(i).getText());
                int ex2 = Integer.parseInt((String) view.getText());
                if(ex1>ex2){
                    RoundSuccess();
                }
                else RoundOver();
            }
        }
    }
}