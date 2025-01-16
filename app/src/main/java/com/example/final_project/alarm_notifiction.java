package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class alarm_notifiction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_notifiction);

        Button set_update_alarm = findViewById(R.id.setbtn);

        set_update_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = findViewById(R.id.datePicker);
                TimePicker timePicker = findViewById(R.id.timePicker);

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day, hour, minute);

                Intent intent = new Intent(getApplicationContext(), sendNotification.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 40, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent); // Set a one-time alarm
                Log.d("Alarm Created","");
                Toast.makeText(getApplicationContext(), "Alarm set for " + calendar.getTime().toString() + " so that you can recycle your trash", Toast.LENGTH_LONG).show();

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("alarmTime", calendar.getTimeInMillis());
                editor.commit();
            }
        });
//to delete the alarm setting
        Button deletebtn = findViewById(R.id.deletebtn);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(alarm_notifiction.this, sendNotification.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(alarm_notifiction.this, 40, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.cancel(pendingIntent);
                Toast.makeText(alarm_notifiction.this, "Alarm cancelled", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("alarmTime");
                editor.commit();
            }
        });
    }
}