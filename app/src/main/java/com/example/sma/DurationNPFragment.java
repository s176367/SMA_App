package com.example.sma;

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


public class DurationNPFragment extends DialogFragment {
    private NumberPicker.OnValueChangeListener valueChangeListener;

    String[] minutInt = new String[]{"15 min","30 min", "45 min", "1 hour","1 hour, 15 min","1 hour, 30 min","1 hour, 45 min","2 hours"};


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final NumberPicker np = new NumberPicker(getActivity());

        np.setMinValue(1);
        np.setMaxValue(8);
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

    /** public interface DialogListener{
        public void ready(int n);

        public void cancelled();
    }



    public DurationNPFragment(Context context, ArrayList duration, DialogListener readyListener){
        super(context);
        mReadyListener = readyListener;
        mContext = context;
        mDuration = new ArrayList<Integer>();
        mDuration = duration;
        duration.add(15);
        duration.add(30);
        duration.add(45);
        duration.add(60);
        duration.add(75);
        duration.add(90);
        duration.add(105);
        duration.add(120);
        duration.add(135);
        duration.add(150);
        duration.add(165);
        duration.add(180);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_duration_spinner);
        mSpinner = findViewById(R.id.dialog_spinner);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(mContext, android.R.layout.simple_spinner_dropdown_item, mDuration);
        mSpinner.setAdapter(adapter);

        Button buttonOK = findViewById(R.id.dialogOK);
        Button buttonCancel = findViewById(R.id.dialogCancel);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = mSpinner.getSelectedItemPosition();
                mReadyListener.ready(n);
                DurationNPFragment.this.dismiss();

            }
        });
    }**/

}
