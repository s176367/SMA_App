package com.example.sma;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    String modifyHour;
    String modifyMin;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, min,true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {

        EditText timeView = (EditText) getActivity().findViewById(R.id.time);
        int settedHour = timePicker.getCurrentHour();
        int settedMin = timePicker.getCurrentMinute();
        if (settedMin <10){
            modifyMin = "0" + settedMin;}
        else {
            modifyMin = String.valueOf(settedMin);
        }
        if (settedHour < 10){
            modifyHour ="0" + settedHour;
        }
        else {
            modifyHour = String.valueOf(settedHour);
        }

        timeView.setText(modifyHour+ ":"+modifyMin);

    }
}
