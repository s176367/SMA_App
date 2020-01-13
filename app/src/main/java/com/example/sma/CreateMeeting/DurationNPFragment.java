package com.example.sma.CreateMeeting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;



/*
Denne klasse anvendes til at lave vores durationhjul, som angiver hvor lang tid et møde varer
Vi modificere derfor en numberpicker til at have de værdier vi gerne vil bruge (15 min intervaller)
*/

public class DurationNPFragment extends DialogFragment {
    private NumberPicker.OnValueChangeListener valueChangeListener;

    String[] minutInt = new String[]{"15 min","30 min", "45 min", "1 hour","1 hour, 15 min","1 hour, 30 min","1 hour, 45 min","2 hours"};


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final NumberPicker np = new NumberPicker(getActivity());

        np.setMinValue(1);
        np.setMaxValue(8);
        // sætter værdierne der vises på hjulet til vores string array
        np.setDisplayedValues(minutInt);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose meeting duration");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(np, np.getValue(), np.getValue());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(np, np.getValue(), np.getValue());
            }
        });
        builder.setView(np);
        return builder.create();
    }
    public NumberPicker.OnValueChangeListener getValueChangeListener(){
        return valueChangeListener;
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener){
        this.valueChangeListener = valueChangeListener;
    }
    }



