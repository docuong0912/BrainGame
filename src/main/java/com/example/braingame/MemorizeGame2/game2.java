package com.example.braingame.MemorizeGame2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Debug;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.braingame.Card;
import com.example.braingame.CardList;
import com.example.braingame.GameHandler;
import com.example.braingame.Player;
import com.example.braingame.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class game2 extends AppCompatActivity {
    private CardList cardList;
    private CardList card_init;
    private CardList cardlist_total;
    private androidx.gridlayout.widget.GridLayout card_container;
    private Player player;
    TextView score;
    int level ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gametwo);
        Random random = new Random();
        score = findViewById(R.id.score);

        card_container = findViewById(R.id.game_container);
        card_container.setColumnCount(7);
        cardList =new CardList();
        cardlist_total = new CardList();
        card_init = new CardList();
        player = new Player(1);
        score.setText(Integer.toString(player.getScore(1)));
        TypedArray animal_themes = getResources().obtainTypedArray(R.array.animal_theme);
        //initial set img id and img tag
        for (int i = 0; i < animal_themes.length(); i++) {
            cardlist_total.getCards().add(new Card(animal_themes.getResourceId(i,0),i));
        }
        if(getIntent().getIntExtra("levels",3)==3){
            //reference to initial list card tag (cardlist_total)
            for (int i = 0; i < animal_themes.length(); i++) {
                cardList.getCards().add(new Card(animal_themes.getResourceId(i,0),cardlist_total.getCards().get(i).getTag()));
            }
            animal_themes.recycle();
            for (int i = 0; i < getIntent().getIntExtra("levels",3); i++) {
                int index = random.nextInt(cardList.getCards().size());
                Card card = new Card(cardList.getCards().get(index).getImgId(),cardList.getCards().get(index).getTag());
                card_init.getCards().add(card);
                cardList.getCards().remove(index);
            }
        }
        else{
            cardList.setCards((ArrayList<Card>) getIntent().getSerializableExtra("cardList"));
            card_init.setCards((ArrayList<Card>) getIntent().getSerializableExtra("card_init"));
            player.setSelected((ArrayList<Card>) getIntent().getSerializableExtra("player"));
            for (int i = card_init.getCards().size(); i < getIntent().getIntExtra("levels",3); i++) {
                int index = random.nextInt(cardList.getCards().size());
                Card card = new Card(cardList.getCards().get(index).getImgId(),cardList.getCards().get(index).getTag());
                card_init.getCards().add(card);
                cardList.getCards().remove(index);
            }
        }


        card_init.shuffle();






        for (int i = 0; i < card_init.getCards().size(); i++) {
            ImageView card_view = new ImageView(this);
            card_view.setImageResource(card_init.getCards().get(i).getImgId());
            card_view.setTag(card_init.getCards().get(i));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.height = (int) (this.getResources().getDisplayMetrics().density*60+0.5f);
            params.width = (int) (this.getResources().getDisplayMetrics().density*60+0.5f);

            params.setGravity(Gravity.FILL);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            card_view.setLayoutParams(params);

            int finalI = i;
            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (player.getSelected().size()==0){
                        player.selects(card_init.getCards().get(finalI));

                        RoundSuccess();
                    }

                    else
                    for (int j = 0; j < player.getSelected().size(); j++) {

                        if(player.getSelected().get(j).getTag() == card_init.getCards().get(finalI).getTag()){
                            RoundOver();
                        }
                        else {
                            player.selects(card_init.getCards().get(finalI));
                            RoundSuccess();
                        }
                    }


                }
            });
            card_container.addView(card_view);
        }

    }


    public void RoundSuccess() {
        Log.d("MainActivity","success");
        Scoring(500);
        int temp = card_init.getCards().size()+1;
        Intent intent = getIntent();
        intent.putExtra("levels",temp);
        intent.putExtra("cardList",cardList.getCards());
        intent.putExtra("card_init",card_init.getCards());
        intent.putExtra("player",player.getSelected());
        finish();
        startActivity(intent);
    }


    public void RoundOver() {
        Log.d("MainActivity","over");
        int temp = 3;
        Intent intent = getIntent();
        intent.putExtra("levels",temp);
        finish();
        startActivity(intent);
    }


    public void Scoring(int score) {
        Log.d("MainActivity",Integer.toString(player.getScore(1)));
        int bonus = 100;
        if(player.getSelected().size()==0)
        player.setScore(500,1);
        else{
            player.setScore((player.getScore(1)+500)*player.getSelected().size()*bonus,player.getGamenum());
        }

    }

}