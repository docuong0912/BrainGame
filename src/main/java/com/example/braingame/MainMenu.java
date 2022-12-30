package com.example.braingame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.braingame.MemorizeGame1.MainActivity;
import com.example.braingame.MemorizeGame2.game2;
import com.example.braingame.MemorizeGame3.Game3;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void startGame(View view) {
        Intent intent = new Intent(MainMenu.this, submenu.class);
        startActivity(intent);
        finish();
    }
}