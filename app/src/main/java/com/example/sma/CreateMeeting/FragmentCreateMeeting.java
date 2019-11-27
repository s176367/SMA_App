package com.example.sma.CreateMeeting;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sma.FakeMeetingDatabase;
import com.example.sma.R;
import com.example.sma.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FragmentCreateMeeting extends Fragment  {


    final Calendar calender = Calendar.getInstance();
    EditText title;
    EditText dateView;
    EditText timeView;
    EditText duration;
    EditText location;
    Button create;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_createmeeting, container, false);


        title = view.findViewById(R.id.title);
        dateView= view.findViewById(R.id.date);
        timeView = view.findViewById(R.id.time);
        duration = view.findViewById(R.id.duration);
        location = view.findViewById(R.id.location);
        create = view.findViewById(R.id.addTopic);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!allFilled()) {
                    FakeMeetingDatabase db = new FakeMeetingDatabase();
                //    db.addMeeting(title.getText().toString(), timeView.getText().toString(), location.getText().toString(), 11,dateView.getText().toString());

                    Fragment fragment = new FragmentCreateAgenda();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(container.getId(), fragment);
                    transaction.commit();
                }
                else {
                    Toast toast = Toast.makeText(view.getContext(), "Fill in the remaining fields", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calender.set(calender.YEAR,year);
                calender.set(calender.MONTH, month);
                calender.set(calender.DAY_OF_MONTH,day);
                updateLabel();
            }};

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(),date,calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

     timeView.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View view) {
             DialogFragment timeFragment = new TimePickerFragment();
             timeFragment.show(getFragmentManager(), "timepicker");

         }
     });
                return view;
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateView.setText(sdf.format(calender.getTime()));
    }




    public boolean allFilled (){

        Boolean returnMe = true;

        if (title.getText().toString().isEmpty()){
            returnMe = false;
        }
        if (dateView.getText().toString().isEmpty()){
            returnMe = false;
        }
        if (timeView.getText().toString().isEmpty()){
            returnMe = false;
        }
        if (duration.getText().toString().isEmpty()){
            returnMe = false;
        }
        if (location.getText().toString().isEmpty()){
            returnMe = false;
        }

        return returnMe;
    }
}
