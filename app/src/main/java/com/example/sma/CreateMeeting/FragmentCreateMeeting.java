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
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


// @Author Gustav Kristensen s180077

public class FragmentCreateMeeting extends Fragment implements NumberPicker.OnValueChangeListener {

    /*
    Dette fragment står for at tilføje de basale informationer til mødet
     */


    final Calendar calender = Calendar.getInstance();
    public EditText title;
    EditText dateView;
    EditText timeView;
    EditText duration;
    EditText location;
    Button create;
    MeetingObject tempMeeting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.createmeeting_fragment_1, container, false);
        tempMeeting = ((ActivityCreateMeeting)getActivity()).getMeeting();
        title = view.findViewById(R.id.insert_title);
        dateView= view.findViewById(R.id.date);
        timeView = view.findViewById(R.id.time);
        duration = view.findViewById(R.id.duration);
        location = view.findViewById(R.id.location);
        create = view.findViewById(R.id.but_create1 );

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
        // Datepicker er lavet af Mads Geertsen 176367
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
                System.out.println(calender);
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
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        System.out.println(sdf.toString());

        dateView.setText(sdf.format(calender.getTime()));
    }

    // metode til at tjekke at alle felter er udfyldt.
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

            case 9:
                return "2 hours, 15 min";

            case 10:
                return "2 hours, 30 min";

            case 11:
                return "2 hours, 45 min";

            case 12:
                return "3 hours";

            case 13:
                return "4 hours";

            case 14:
                return "5 hours";

            case 15:
                return "6 hours";

            case 16:
                return "7 hours";

            case 17:
                return "8 hours";

            case 18:
                return "9 hours";

            case 19:
                return "10 hours";
            default:
        }

        return null;
    }


}
