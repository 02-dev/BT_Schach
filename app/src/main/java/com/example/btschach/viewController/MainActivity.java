package com.example.btschach.viewController;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.btschach.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    public void buttonNewGame(View view) {
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);

    }

    /*
    public void buttonLoadGame(View view) {
        Intent intent = new Intent(this, LoadGameActivity.class);
        startActivity(intent);
    }*/

}