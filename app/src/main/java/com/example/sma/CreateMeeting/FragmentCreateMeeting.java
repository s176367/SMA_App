package com.example.sma.CreateMeeting;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.sma.Database.MeetingDAO;
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FragmentCreateMeeting extends Fragment implements NumberPicker.OnValueChangeListener {


    final Calendar calender = Calendar.getInstance();
    EditText title;
    EditText dateView;
    EditText timeView;
    EditText duration;
    EditText location;
    Button create;
    MeetingObject tempMeeting;

    MeetingDAO meetingDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.createmeeting_fragment_1, container, false);
        tempMeeting = ((ActivityCreateMeeting)getActivity()).getMeeting();


        meetingDAO = new MeetingDAO();


        title = view.findViewById(R.id.insert_title);
        dateView= view.findViewById(R.id.date);
        timeView = view.findViewById(R.id.time);
        duration = view.findViewById(R.id.duration);
        location = view.findViewById(R.id.location);
        create = view.findViewById(R.id.but_create1 );

                if (tempMeeting.getTitle() != null){
                    title.setText(tempMeeting.getTitle());
                    dateView.setText(tempMeeting.getDate());
                    timeView.setText(tempMeeting.getTime());
                    duration.setText(tempMeeting.getDuration());
                    location.setText(tempMeeting.getLocation());
                    }





        duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker(v);

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFilled()) {
                    tempMeeting.setTitle(title.getText().toString());
                    tempMeeting.setDate(dateView.getText().toString());
                    tempMeeting.setTime(timeView.getText().toString());
                    tempMeeting.setLocation(location.getText().toString());
                    tempMeeting.setDuration(duration.getText().toString());
                    ((ActivityCreateMeeting)getActivity()).setMeeting(tempMeeting);



                    getActivity().getSupportFragmentManager().beginTransaction().replace(container.getId(), new FragmentCreateAgenda()).addToBackStack(null).commit();
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
             DatePickerDialog dp =  new DatePickerDialog(view.getContext(),date,calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));//.show();
            dp.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
            dp.show();
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

    DurationNPFragment np = new DurationNPFragment();

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        duration.setText(eValPicker(newVal));

    }

    public void showNumberPicker(View view) {
        DurationNPFragment newFragment = new DurationNPFragment();
        newFragment.setValueChangeListener(this);
        newFragment.show(getFragmentManager(), "time picker");
    }

    public String eValPicker(int npVal) {
        switch (npVal) {
            case 1:
                return "15 min";

            case 2:
                return "30 min";
            case 3:
                return "45 min";
            case 4:
                return "1 hour";
            case 5:
                return "1 hour, 15 min";
            case 6:
                return "1 hour, 30 min";
            case 7:
                return "1 hour, 45 min";
            case 8:
                return "2 hours";
            default:
        }
        return null;
    }


}
