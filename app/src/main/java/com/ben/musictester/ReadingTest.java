package com.ben.musictester;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class ReadingTest extends AppCompatActivity {
    private static final String TAG = "PitchTest";


    // Used to initialize the note to its correct default position
    private final int NOTE_X_START_POSITION = 670;
    private final int NOTE_Y_START_POSITION = 920;
    // Y distance between consecutive notes
    private final int NOTE_SPACING = 31;
    private final int NUM_NOTES = 14;
    // These numbers correspond to their correct, lowest index on the ledger line. Used for checking answer;
    private final int   Ai = 6,
                        Bi = 0,
                        Ci = 1,
                        Di = 2,
                        Ei = 3,
                        Fi = 4,
                        Gi = 5;

    private final int OCTAVE = 7;

    // Maximum number of note repetitions on the ledger lines. In this case B appears 3 times.
    private final int MAX_REPETITIONS = 3;

    // Scores are public; will be used for persistence
    public static int scoreReading;
    public static int numTestsReading;
    private TextView curScore;
    private TextView totalTests;
    private TextView percentScore;

    private int noteDecider;
    private int buttonPressed = 0;

    private String  answer;
    private boolean isCorrect;

    private Button A, B, C, D, E, F, G;

    private ImageView trebleClef;
    private ImageView noteImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_test);
        setContentView(R.layout.activity_reading_test);

        A = (Button) findViewById(R.id.buttonA);
        B = (Button) findViewById(R.id.buttonB);
        C = (Button) findViewById(R.id.buttonC);
        D = (Button) findViewById(R.id.buttonD);
        E = (Button) findViewById(R.id.buttonE);
        F = (Button) findViewById(R.id.buttonF);
        G = (Button) findViewById(R.id.buttonG);

        curScore = (TextView) findViewById(R.id.curscore);
        totalTests = (TextView) findViewById(R.id.numtests);
        percentScore = (TextView) findViewById(R.id.percentscore);

        ImageView ledgerLine = (ImageView) findViewById(R.id.ledgerline1);
        trebleClef = (ImageView) findViewById(R.id.trebleclef);
        noteImg = (ImageView) findViewById(R.id.noteIV);

        noteImg.setImageResource(R.drawable.wholenote);
        ledgerLine.setImageResource(R.drawable.ledgerlines);
        trebleClef.setImageResource(R.drawable.trebleclef);

        noteImg.setX(NOTE_X_START_POSITION);

        scoreReading = 0;
        numTestsReading = 0;

        createNewQuestionReading();
    }

    private void createNewQuestionReading() {
        initTest();
        beginTest();
    }

    private void initTest() {
        noteImg.setY(NOTE_Y_START_POSITION);
        Random rand = new Random();
        noteDecider = rand.nextInt(NUM_NOTES);
        noteImg.setY(noteImg.getY() - NOTE_SPACING * noteDecider);
    }

    private void beginTest() {

        A.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (++buttonPressed == 1 ) {
                    checkAnswer(Ai);
                    createDialog();
                }
            }
        });

        B.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (++buttonPressed == 1 ) {
                    checkAnswer(Bi);
                    createDialog();
                }
            }
        });

        C.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (++buttonPressed == 1 ) {
                    checkAnswer(Ci);
                    createDialog();
                }
            }
        });

        D.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (++buttonPressed == 1 ) {
                    checkAnswer(Di);
                    createDialog();
                }
            }
        });

        E.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (++buttonPressed == 1) {
                    checkAnswer(Ei);
                    createDialog();
                }
            }
        });

        F.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (++buttonPressed == 1 ) {
                    checkAnswer(Fi);
                    createDialog();
                }
            }
        });

        G.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (++buttonPressed == 1) {
                    checkAnswer(Gi);
                    createDialog();
                }
            }
        });

    }

    private void checkAnswer(int userChoice) {

        numTestsReading++;
        isCorrect = false;
        for (int i = 0; i < MAX_REPETITIONS; i += OCTAVE){
            if (userChoice + i == noteDecider) {
                scoreReading++;
                isCorrect = true;
            }
        }
        updateScores();
    }

    private void createDialog() {
        switch(noteDecider) {
            case 6:
            case 13:answer = "A"; break;

            case 0:
            case 7:
            case 14: answer = "B"; break;

            case 1:
            case 8: answer = "C"; break;

            case 2:
            case 9: answer = "D"; break;

            case 3:
            case 10: answer = "E"; break;

            case 4:
            case 11: answer = "F"; break;

            case 5:
            case 12: answer = "G"; break;
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
                createDialog();
            }
        });
        builder.setPositiveButton(R.string.next_question, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                createNewQuestionReading();
                return;
            }
        });
        builder.setNegativeButton(R.string.finish, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                finishReading();
                return;
            }
        });
        builder.create().show();
    }

    private void updateScores() {
        curScore.setText(Integer.toString(scoreReading));
        totalTests.setText(Integer.toString(numTestsReading));
        percentScore.setText((Integer.toString((int)((float) scoreReading /(float) numTestsReading *100)) + "%"));
    }

    private void finishReading() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
