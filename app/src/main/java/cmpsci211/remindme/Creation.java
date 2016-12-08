package cmpsci211.remindme;

/**
 * Created by Collin on 11/11/2016.
 */

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

//Need to incorporate ID's into the object. Figure out how.
/*This  class is the entire screen for creating activities. Selecting name, description, time, and date is already implemented.
*
* TO DO: Research database techniques to store entries for each reminder. I think we can use objects - thus the DataEntry class. An object is created upon clicking the save button below.*/

public class Creation extends AppCompatActivity implements View.OnClickListener{

    private EditText reminderTitle;
    private EditText reminderDescription;
    private TextView textViewDate, textViewTime;
    Button setDateButton, setTimeButton, saveButton, cancelButton;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private boolean isAM;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation_main);

        reminderTitle = (EditText)findViewById(R.id.reminderTitle);
        reminderDescription = (EditText) findViewById(R.id.reminderDescription);

        textViewDate = (TextView) findViewById(R.id.textDate);
        textViewTime = (TextView) findViewById(R.id.textTime);
        setDateButton = (Button) findViewById(R.id.btnDate);
        setTimeButton = (Button) findViewById(R.id.btnTime);
        saveButton = (Button) findViewById(R.id.btnSave);
        cancelButton = (Button) findViewById(R.id.btnCancel);
        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        setDateButton.setOnClickListener(this);
        setTimeButton.setOnClickListener(this);

        boolean isEditing = this.getIntent().getBooleanExtra("isEditing", false);

        if(isEditing){

            Intent i = this.getIntent();

            index = i.getIntExtra("index", -1);
            reminderTitle.setText(i.getStringExtra("name"));
            reminderDescription.setText(i.getStringExtra("desc"));
            mYear = i.getIntExtra("year", 0);
            mMonth = i.getIntExtra("month", 0);
            mDay = i.getIntExtra("day", 0);
            mHour = i.getIntExtra("hour", 0);
            mMinute = i.getIntExtra("minute", 0);
            isAM = i.getBooleanExtra("am", false);

        }else {

            index = -1;


            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            isAM = true;

        }

        textViewDate.setText((mMonth + 1) + "-" + (mDay) + "-" + mYear);

        String ending = "AM";
        int displayHour = mHour;

        if(displayHour >= 12){

            isAM = false;
            ending = "PM";

            if(displayHour != 12)
                displayHour -= 12;

        }else if(displayHour == 0){

            isAM = true;
            ending = "AM";
            displayHour = 12;

        }


        textViewTime.setText(String.format("%2d:%02d" + ending, displayHour, mMinute));

    }

    @Override
    public void onClick(View v) {

        if (v == setDateButton) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;

                            textViewDate.setText((mMonth + 1) + "-" + (mDay) + "-" + mYear);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == setTimeButton ) {

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            mHour = hourOfDay;
                            mMinute = minute;

                            String ending = "AM";
                            isAM = true;
                            int displayHour = mHour;

                            if(displayHour >= 12){

                                isAM = false;
                                ending = "PM";

                                if(displayHour != 12)
                                    displayHour -= 12;

                            }else if(displayHour == 0){

                                isAM = true;
                                ending = "AM";
                                displayHour = 12;

                            }


                            textViewTime.setText(String.format("%2d:%02d" + ending, displayHour, mMinute));
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (v == saveButton){

            Calendar dateCreated = Calendar.getInstance();
            Calendar timeToRemind = Calendar.getInstance();
            timeToRemind.set(Calendar.YEAR, mYear);
            timeToRemind.set(Calendar.MONTH, mMonth);
            timeToRemind.set(Calendar.DAY_OF_MONTH, mDay);
            timeToRemind.set(Calendar.HOUR_OF_DAY, mHour);
            timeToRemind.set(Calendar.MINUTE, mMinute);
            timeToRemind.set(Calendar.SECOND, 0);

            if(timeToRemind.before(dateCreated)){

                Toast.makeText(this, "Date entered is in the past", Toast.LENGTH_LONG).show();
                return;

            }


            String name = reminderTitle.getText().toString();
            String desc = reminderDescription.getText().toString();

            Intent intent = new Intent();
            intent.putExtra("name", name);
            intent.putExtra("desc", desc);
            intent.putExtra("year", mYear);
            intent.putExtra("month", mMonth);
            intent.putExtra("day", mDay);
            intent.putExtra("hour", mHour);
            intent.putExtra("minute", mMinute);
            intent.putExtra("am", isAM);
            intent.putExtra("index", index);


            setResult(RESULT_OK, intent);
            finish();
        }

        if (v == cancelButton){
            setResult(RESULT_CANCELED, new Intent());
            finish();
        }
    }
}
