package com.example.braingame.MemorizeGame3;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.braingame.Card;
import com.example.braingame.CardList;
import com.example.braingame.GameHandler;
import com.example.braingame.Player;
import com.example.braingame.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game3 extends AppCompatActivity implements GameHandler {
    GridLayout ansSec;
    LinearLayout quesSec;
    TypedArray animal_themes ;
    int level ;
    Player player;
    CardList cardList;
    ArrayList<Card> ans;
    CardList multipleChoice;
    private void shuffle(int[] arr){
        Random random= new Random();
        for (int i = arr.length-1; i > 0; i--) {
            int j = random.nextInt(i+1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3);
        Random random = new Random();
        player = new Player(3);
        cardList = new CardList();
        ans = new ArrayList<>();
        multipleChoice = new CardList();
        animal_themes = getResources().obtainTypedArray(R.array.animal_theme);
        level = getIntent().getIntExtra("level",1);
        for (int i = 0; i < animal_themes.length(); i++) {
            Card card = new Card(animal_themes.getResourceId(i,0),i);
            cardList.getCards().add(card);
        }
        cardList.shuffle();
        for (int i = 0; i < 4; i++) {
            cardList.getAnsPics().add(cardList.getCards().get(i));
        }
        cardList.shuffle();
        for (int i = 0; i < 4; i++) {
            cardList.getAnsPicBack().add(cardList.getAnsPics().get(i));
        }
        Toast toast = Toast.makeText(this,"LevelView: "+Integer.toString(level),Toast.LENGTH_SHORT);
        toast.show();

        if(level < 5){
            int ran = random.nextInt(cardList.getAnsPics().size());
            ans.add(cardList.getAnsPics().get(ran));
            multipleChoice.getCards().add(cardList.getAnsPics().get(ran));
            for (int i = 0; i < 3; i++) {
                multipleChoice.getCards().add(cardList.getCards().get(i));
            }
        }
        else if(level >= 5 && level < 8) {
            cardList.shuffleAns();
            for (int i = 0; i < 2; i++) {
                ans.add(cardList.getAnsPics().get(i));
                multipleChoice.getCards().add(cardList.getAnsPics().get(i));;
            }
            for (int i = 0; i < 4; i++) {
                multipleChoice.getCards().add(cardList.getCards().get(i));
            }
        }
        else{
            cardList.shuffleAns();
            for (int i = 0; i < 3; i++) {
                ans.add(cardList.getAnsPics().get(i));
                multipleChoice.getCards().add(cardList.getAnsPics().get(i));
            }
            for (int i = 0; i < 5; i++) {
                multipleChoice.getCards().add(cardList.getCards().get(i));
            }
        }
        multipleChoice.shuffle();
        //display
        ansSec = findViewById(R.id.ans_section);
        ansSec.setColumnCount(4);
        quesSec =(LinearLayout) findViewById(R.id.question_section);
        Animator front_anim;
        Animator back_anim;
        Handler handler = new Handler();
        for (int i = 0; i < cardList.getAnsPics().size(); i++) {
            // create front face and back face
            ImageView img = new ImageView(this);
            ImageView imgBack = new ImageView(this);
            img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imgBack.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            img.setAdjustViewBounds(true);
            imgBack.setAdjustViewBounds(true);

            imgBack.setImageResource(cardList.getAnsPicBack().get(i).getImgId());

            img.setImageResource(cardList.getAnsPics().get(i).getImgId());
            // set tag for comparison
            img.setTag(cardList.getAnsPics().get(i).getImgId());
            imgBack.setTag(cardList.getAnsPicBack().get(i).getImgId());
            LinearLayout.LayoutParams params  = new LinearLayout.LayoutParams((int) (this.getResources().getDisplayMetrics().density*90+0.5f),(int) (this.getResources().getDisplayMetrics().density*90+0.5f));
            img.setLayoutParams(params);
            imgBack.setLayoutParams(params);
            ConstraintLayout constraintLayout = new ConstraintLayout(this);
            constraintLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,1.0f));

            constraintLayout.addView(imgBack);
            constraintLayout.addView(img);

            quesSec.addView(constraintLayout);

            // animation

            front_anim = AnimatorInflater.loadAnimator(this,R.animator.front_animator);
            back_anim = AnimatorInflater.loadAnimator(this,R.animator.back_animator);
            Animator finalFront_anim = front_anim;
            Animator finalBack_anim = back_anim;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finalFront_anim.setTarget(img);
                    finalBack_anim.setTarget(imgBack);

                    finalFront_anim.start();
                    finalBack_anim.start();
                    finalFront_anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            ansSec.setVisibility(View.VISIBLE);
                        }


                    });
                    for (int j = 0; j < ans.size(); j++) {
                        if((int)imgBack.getTag() == ans.get(j).getImgId()){
                            imgBack.setImageResource(R.drawable.question);
                        }
                    }


                }
            },3000);


        }
        //display multiple choice
        for (int i = 0; i < multipleChoice.getCards().size(); i++) {
            ImageView img = new ImageView(this);
            img.setTag(multipleChoice.getCards().get(i));
            LinearLayout.LayoutParams params  = new LinearLayout.LayoutParams((int) (this.getResources().getDisplayMetrics().density*90+0.5f),(int) (this.getResources().getDisplayMetrics().density*90+0.5f),1.0f);
            img.setLayoutParams(params);
            img.setImageResource(multipleChoice.getCards().get(i).getImgId());
            ansSec.addView(img);
            ansSec.setVisibility(View.INVISIBLE);
            // add listener
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    player.selects((Card) img.getTag());
                    if(ans.size() == 1){
                        if(img.getTag().equals(ans.get(0))){
                            RoundSuccess();
                        }
                        else RoundOver();
                    }
                    else {
                        if(player.getSelected().size() ==ans.size()){
                            if(Arrays.asList(ans).containsAll(Arrays.asList(player.getSelected()))){
                                RoundSuccess();
                            }
                            else RoundOver();
                        }
                    }



                }
            });
        }
    }
    @Override
    public void RoundOver(){
        int temp = level;
        Intent intent = getIntent();
        intent.putExtra("level",temp);

        finish();
        startActivity(intent);
    }

    @Override
    public void Scoring(int score) {

    }


    @Override
    public void RoundSuccess(){

        int temp = level+1;
       // int tempSCore = score + map.getLevel()*100;
        Intent intent = getIntent();
        intent.putExtra("level",temp);
      //  intent.putExtra("score",tempSCore);
        finish();
        startActivity(intent);
    }
}