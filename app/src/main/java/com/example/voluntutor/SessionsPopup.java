package com.example.voluntutor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class SessionsPopup extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String name = (String) b.get("Name");
        String date = (String) b.get("Date");
        Log.d("Date? II", date);
        String time = (String) b.get("Time");
        String location = (String) b.get("Location");
        setContentView(R.layout.sessions_popup);
        TextView name_popup = (TextView) findViewById(R.id.sess_popup_name);
        TextView date_popup = (TextView) findViewById(R.id.sess_popup_day);
        TextView time_popup = (TextView) findViewById(R.id.sess_popup_time);
        TextView location_popup = (TextView) findViewById(R.id.sess_popup_location);

        name_popup.setText(name);
        date_popup.setText(date);
        time_popup.setText(time);
        location_popup.setText(location);
    }
}
