package com.ksapps.uvote;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class CreateRoomActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    DatabaseReference myRef;
    FirebaseDatabase database;
    static final int TIME_DIALOG_ID = 1111;
    private EditText etTitle;
    String title, date, sTime, eTime;
    Date outTime, inTime;
    int dateDelta;
    Button btnDatePicker, btnTime1, btnTime2, btnSubmit;
    TextView txtTime1, txtTime2, txtDate;
    private int mHour, mMinute, mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isOnline()) {
            setContentView(R.layout.activity_create_room);

            auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference().child("room");

            btnDatePicker = (Button) findViewById(R.id.btnDate);
            btnTime1 = (Button) findViewById(R.id.btnTime1);
            btnTime2 = (Button) findViewById(R.id.btnTime2);
            txtDate = (TextView) findViewById(R.id.tvDate1);
            txtTime1 = (TextView) findViewById(R.id.tvTime1);
            txtTime2 = (TextView) findViewById(R.id.tvTime2);
            etTitle = (EditText) findViewById(R.id.etTitle);
            btnSubmit = (Button) findViewById(R.id.btnSubmit);
            btnDatePicker.setOnClickListener(this);
            btnTime1.setOnClickListener(this);
            btnTime2.setOnClickListener(this);
            btnSubmit.setOnClickListener(this);
        } else
            setContentView(R.layout.no_internet);
    }

    @Override
    public void onClick(View v) {
        final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            date = (year+ "-"+  (monthOfYear + 1) + "-" + dayOfMonth);
                            txtDate.setText(date);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();

        }

        if (v == btnTime1) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            try {
                                inTime = sdf.parse(sTime);
                                if (!(eTime == null)) {
                                    dateDelta = inTime.compareTo(outTime);
                                    switch (dateDelta) {
                                        case 0:
                                            Toast.makeText(CreateRoomActivity.this, "Please enter proper start time", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 1:
                                            Toast.makeText(CreateRoomActivity.this, "Please enter proper start time", Toast.LENGTH_SHORT).show();
                                            break;
                                        case -1:
                                            sTime = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
                                            txtTime1.setText(sTime);
                                            break;
                                    }
                                } else {
                                    txtTime1.setText(sTime);
                                }
                                Date currentTime = Calendar.getInstance().getTime();
                                Log.e("Errorr", currentTime.toString());
                            } catch (ParseException e) {
                                Log.e("Errorr", "Failure");
                            }

//                            txtTime1.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                        }
                    }, mHour, mMinute, false);
            if (date == null) {
                Toast.makeText(this, "Select Date First", Toast.LENGTH_SHORT).show();
            } else {
                System.currentTimeMillis();
                //getTimePicker().setMinDate(System.currentTimeMillis() - 1000);
                timePickerDialog.show();
            }

        }
        if (v == btnTime2) {

            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            eTime = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
                            try {
                                outTime = sdf.parse(eTime);
                                if (!(sTime == null)) {
                                    dateDelta = inTime.compareTo(outTime);
                                    Log.e("Errorr", Integer.toString(dateDelta));
                                    switch (dateDelta) {
                                        case 0:
                                            Toast.makeText(CreateRoomActivity.this, "Please enter proper end time", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 1:
                                            Toast.makeText(CreateRoomActivity.this, "Please enter proper end time", Toast.LENGTH_SHORT).show();
                                            break;
                                        case -1:

                                            txtTime2.setText(eTime);
                                            break;
                                    }
                                } else {
                                    txtTime2.setText(eTime);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (v == btnSubmit) {
            title = etTitle.getText().toString().trim();
            date = txtDate.getText().toString().trim();
            sTime = txtTime1.getText().toString().trim();
            eTime = txtTime2.getText().toString().trim();

            if (TextUtils.isEmpty(title)) {
                Toast.makeText(getApplicationContext(), "Enter Title!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(date)) {
                Toast.makeText(getApplicationContext(), "Enter Date!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(sTime)) {
                Toast.makeText(getApplicationContext(), "Enter Start Time!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(eTime)) {
                Toast.makeText(getApplicationContext(), "Enter End Time!", Toast.LENGTH_SHORT).show();
                return;
            }

            int room_id = new Random().nextInt(9999) + 1;
            String roomId = String.format("%04d", room_id);

            HashMap<String, String> map = new HashMap();
            map.put("title", title);
            map.put("date", date);
            map.put("sTime", date+" "+sTime);
            map.put("eTime", date+" "+eTime);
            map.put("roomId", roomId);
            myRef.child(roomId).setValue(map);
            Toast.makeText(CreateRoomActivity.this, "Success", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(CreateRoomActivity.this, AddCandidates.class);
            i.putExtra("roomId", roomId);
            startActivity(i);
        }

    }

    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
