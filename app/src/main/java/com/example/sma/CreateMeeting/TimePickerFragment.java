package com.example.sma.CreateMeeting;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.sma.R;
import java.util.Calendar;


/*
Fragment der bruges til at bruger kan vælge hvornår mødet skal starte
 */
// @Author Mads Geertsen s176367
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    String modifyHour;
    String modifyMin;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        //Indsætter urets "default" tid

        return new TimePickerDialog(getActivity(), this, 12, 0,true);
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
