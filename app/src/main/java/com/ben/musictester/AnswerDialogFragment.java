package com.ben.musictester;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


/**
 * Created by Ben on 2017-03-05.
 */

public class AnswerDialogFragment extends DialogFragment {
    private boolean isCorrect;
    protected String action;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        isCorrect = getArguments().getBoolean("isCorrect");
        if (isCorrect) {
            builder.setMessage(R.string.correct);
        } else {
            builder.setMessage(R.string.incorrect);
        }
        builder.setPositiveButton(R.string.next_question, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dismiss();
                action = "next";
            }
        });
        builder.setNegativeButton(R.string.finish, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dismiss();
                action = "finish";
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}



