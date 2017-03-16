package com.ben.musictester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void goToPitchTest(View view) {
        Log.d("Home", "Pt Created");
        Intent intent = new Intent(this, PitchTest.class);
        startActivity(intent);
    }

    public void goToReadingTest(View view) {
        Log.d("Home", "Rt Created");
        Intent intent = new Intent(this, ReadingTest.class);
        startActivity(intent);
    }
}
