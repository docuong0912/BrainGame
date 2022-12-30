package com.example.braingame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.braingame.MemorizeGame1.MainActivity;
import com.example.braingame.MemorizeGame2.game2;
import com.example.braingame.MemorizeGame3.Game3;

public class submenu extends AppCompatActivity {
    Button game1,game2,game3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu);
        game1 = findViewById(R.id.game1btn);
        game2 = findViewById(R.id.game2btn);
        game3 = findViewById(R.id.game3btn);


    }

    public void chooseGame(View view) {

        Button button = (Button) view;
        Intent intent;
        if (game1.equals(button)) {
            intent = new Intent(this, MainActivity.class);
        }
        else if(game2.equals(button)){
            intent = new Intent(this, com.example.braingame.MemorizeGame2.game2.class);
        }
        else{
            intent = new Intent(this, Game3.class);
        }
        startActivity(intent);
        finish();
    }
}