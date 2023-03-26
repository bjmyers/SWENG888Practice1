package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private TextView mFirstNameTitle;
    private EditText mFirstNameEntry;
    private TextView mLastNameTitle;
    private EditText mLastNameEntry;
    private TextView mDateTitle;
    private DatePicker mDateEntry;
    private Button mCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirstNameTitle = findViewById(R.id.firstNameTitle);
        mFirstNameEntry = findViewById(R.id.firstNameEntry);
        mLastNameTitle = findViewById(R.id.lastNameTitle);
        mLastNameEntry = findViewById(R.id.lastNameEntry);
        mDateTitle = findViewById(R.id.dateEntryLabel);
        // Date picker goes back to 1900, but oldest living person was born in 1907, so we're good
        mDateEntry = findViewById(R.id.dateEntry);

        mCalculate = findViewById(R.id.calculateButton);

        mCalculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final int year = mDateEntry.getYear();
                // DatePicker uses 0-indexed months, add 1 for compatibility with LocalDate
                final int month = mDateEntry.getMonth() + 1;
                final int day = mDateEntry.getDayOfMonth();
                // Build inputs from DatePicker into a format which can be compared to today
                final LocalDate birthDate = LocalDate.of(year, month, day);
                final LocalDate now = LocalDate.now();

                // Display age as a Toast
                if (now.isBefore(birthDate)) {
                    // User said they were born after today!
                    Toast.makeText(MainActivity.this, "ERROR: You haven't been born yet",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    final String firstName = mFirstNameEntry.getText().toString().strip();
                    final String lastName = mLastNameEntry.getText().toString().strip();
                    // This is the number of whole years between birthDate and now
                    final long yearsOld = ChronoUnit.YEARS.between(birthDate, now);

                    // Display the user's name, only if they provided one or both
                    final String nameString;
                    if (firstName.isEmpty() && lastName.isEmpty()) {
                        nameString = "";
                    }
                    else {
                        nameString = firstName + " " + lastName + ", ";
                    }

                    final String displayString = nameString + "you are "
                            + yearsOld + " years old";
                    Toast.makeText(MainActivity.this, displayString, Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}