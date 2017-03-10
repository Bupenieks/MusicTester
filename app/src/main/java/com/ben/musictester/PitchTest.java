package com.ben.musictester;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.io.IOException;
import java.util.Random;

public class PitchTest extends AppCompatActivity {

    private static final String TAG = "PitchTest"; //DEBUG
    private final int NUM_NOTES = 7;

    public static int score;
    public static int numTests; // Will be used for persistent scores

    private String answer;
    private boolean isCorrect;

    // Holds all notes' files to be played
    private final MediaPlayer[] media = new MediaPlayer[NUM_NOTES];
    private MediaPlayer noteToPlay;
    private int noteDecider;

    private Button A;
    private Button B;
    private Button C;
    private Button D;
    private Button E;
    private Button F;
    private Button G;
    private Button playNoteAgain;
    private int  buttonPressed = 0;

    private TextView curScore;
    private TextView totalTests;
    private TextView percentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Pitch Test Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitchtest);

        A = (Button) findViewById(R.id.buttonA);
        B = (Button) findViewById(R.id.buttonB);
        C = (Button) findViewById(R.id.buttonC);
        D = (Button) findViewById(R.id.buttonD);
        E = (Button) findViewById(R.id.buttonE);
        F = (Button) findViewById(R.id.buttonF);
        G = (Button) findViewById(R.id.buttonG);
        playNoteAgain = (Button) findViewById(R.id.playbutton);

        curScore = (TextView) findViewById(R.id.curscore);
        totalTests = (TextView) findViewById(R.id.numtests);
        percentScore = (TextView) findViewById(R.id.percentscore);

        // Initializes notes to be played
        media[0] = MediaPlayer.create(this, R.raw.a);
        media[1] = MediaPlayer.create(this, R.raw.b);
        media[2] = MediaPlayer.create(this, R.raw.c);
        media[3] = MediaPlayer.create(this, R.raw.d);
        media[4] = MediaPlayer.create(this, R.raw.e);
        media[5] = MediaPlayer.create(this, R.raw.f);
        media[6] = MediaPlayer.create(this, R.raw.g);

        score = 0;
        numTests = 0;
        initTest();
        beginTest();
    }

    private void initTest() {
        Random rand = new Random();
        noteDecider = rand.nextInt(7);
        noteToPlay = media[noteDecider];
        buttonPressed = 0;
        noteToPlay.stop();
        try {
            noteToPlay.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        noteToPlay.seekTo(0);
    }

    private void beginTest() {
        playSound();

        A.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ResultDecider(0);
            }
        });

        B.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ResultDecider(1);
            }
        });

        C.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ResultDecider(2);
            }
        });

        D.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ResultDecider(3);
            }
        });

        E.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ResultDecider(4);
            }
        });

        F.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ResultDecider(5);
            }
        });

        G.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ResultDecider(6);
            }
        });

        playNoteAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                playSound();
            }
        });
    }

    private void ResultDecider(int noteChosen) {
        buttonPressed++;
        if (buttonPressed == 1) {
            if (noteDecider == noteChosen) {
                isCorrect = true;
                score++;
            } else {
                isCorrect = false;
            }
            numTests++;
            curScore.setText(Integer.toString(score));
            totalTests.setText(Integer.toString(numTests));
            percentScore.setText((Integer.toString((int)((float)score/(float)numTests*100)) + "%"));
            CreateDialog();
        }
    }

    private void playSound() {
        noteToPlay.seekTo(0);
        noteToPlay.start();
    }

    private void Finish() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    private void CreateNewQuestion() {
        initTest();
        beginTest();
    }

    private void CreateDialog() {
        switch(noteDecider) {
            case 0: answer = "A"; break;
            case 1: answer = "B"; break;
            case 2: answer = "C"; break;
            case 3: answer = "D"; break;
            case 4: answer = "E"; break;
            case 5: answer = "F"; break;
            case 6: answer = "G"; break;
        }
        assert(answer != null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (isCorrect) {
            builder.setMessage(getString(R.string.correct));
        } else {
            builder.setMessage(getString(R.string.incorrect) + " The answer was: " + answer);
        }
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                CreateDialog();
            }
        });
        builder.setPositiveButton(R.string.next_question, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                CreateNewQuestion();
                return;
            }
        });
        builder.setNegativeButton(R.string.finish, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Finish();
                return;
            }
        });
        builder.create().show();
    }

}
