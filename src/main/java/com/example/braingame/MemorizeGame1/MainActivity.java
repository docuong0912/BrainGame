package com.example.braingame.MemorizeGame1;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.braingame.GameHandler;
import com.example.braingame.R;

public class MainActivity extends AppCompatActivity  {
    TileMap map;
    LinearLayout gamelayout;
    int counter = 0;
    int buttonWidth = 30;
    int buttonHeight = 50;
    int score ;
    boolean gameStarted = true;
    ProgressBar progressBar;
    TextView scoreDetail;
    // create handler
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get score text
        scoreDetail =(TextView) findViewById(R.id.ScoreDetail);

        // get progress bar
        progressBar = findViewById(R.id.progressBar);

        // create tiles
        if(getIntent().getExtras() != null){
            map = new TileMap(getIntent().getIntExtra("level",0));
            score = getIntent().getIntExtra("score",0);
        }
        else{
            map = new TileMap(1);
            score = 0;
        }
        scoreDetail.setText(Integer.toString(score));
        // find main layout
        gamelayout = findViewById(R.id.game_layout);
        Animator slide = AnimatorInflater.loadAnimator(this,R.animator.slidedown);
        slide.setTarget(gamelayout);
        slide.start();
        slide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startClock();
            }
        });

        // create level
        for (int i = 0; i < map.getHeight(); i++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < map.getWidth(); j++) {
                Button button = new Button(this);
                Animation anim = new ScaleAnimation(0f,1f,1f,1f);
                anim.setDuration(1000);
                button.startAnimation(anim);
                button.setEnabled(false);
                if(map.getMat(j,i)==1){
                    button.setBackgroundColor(Color.YELLOW);

                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            button.setEnabled(true);
                            button.setBackgroundColor(Color.LTGRAY);
                        }

                },3000);

                //set onclick listener

                int finalI = i;
                int finalJ = j;
                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Log.d("button ",Integer.toString(map.getMat(finalJ, finalI)));
                        if(map.getMat(finalJ,finalI)==1){
                            button.setBackgroundColor(Color.YELLOW);
                            button.setEnabled(false);
                            score +=200;
                            counter++;
                            if(counter == map.getLevel())
                            {
                                RoundSuccess();
                            }
                        }
                        else {
                            button.setBackgroundColor(Color.RED);
                            // from alpha 0 to alpha 1
                            Animation animation =  new AlphaAnimation(0f,1f);
                            animation.setDuration(50);
                            animation.setStartOffset(10);
                            animation.setRepeatMode(Animation.REVERSE);
                            animation.setRepeatCount(Animation.INFINITE);
                            button.startAnimation(animation);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                   RoundOver();
                                }
                            },3000);
                        }
                    }
                });

                //convert px to dp
                LinearLayout.LayoutParams params  = new LinearLayout.LayoutParams((int) (this.getResources().getDisplayMetrics().density*buttonWidth+0.5f),(int) (this.getResources().getDisplayMetrics().density*buttonHeight+0.5f));
                params.setMargins(5,5,5,5);
                button.setLayoutParams(params);
                layout.addView(button);
            }
            gamelayout.addView(layout);
        }

    }

    public void RoundOver(){
        Log.d("MainActivity","round over");
        int temp = map.getLevel();
        Intent intent = getIntent();
        intent.putExtra("level",temp);
        finish();
        startActivity(intent);
    }

    public void Scoring() {

    }

    public void RoundSuccess(){
        gameStarted = false;
        int temp = map.getLevel()+1;
        int tempSCore = score + map.getLevel()*100;
        Intent intent = getIntent();
        intent.putExtra("level",temp);
        intent.putExtra("score",tempSCore);
        finish();
        startActivity(intent);
    }
    private void startClock(){
        progressBar.setMax(100);
        ValueAnimator animator = ValueAnimator.ofInt(0, progressBar.getMax());
        animator.setDuration(10000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                progressBar.setProgress((Integer)valueAnimator.getAnimatedValue());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(!gameStarted) return;
                RoundOver();
            }
        });
        animator.start();
    }

}