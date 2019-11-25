package com.example.sma;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePicker1 extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, min,false);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker timePicker, int hour, int min) {
        EditText timeView = (EditText) getActivity().findViewById(R.id.time);
        timeView.setText(""+ timePicker.getCurrentHour()+ ":"+timePicker.getCurrentMinute());

    }
}
