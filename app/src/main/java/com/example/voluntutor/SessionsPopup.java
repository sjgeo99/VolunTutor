package com.example.voluntutor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//This class models the functionality of the popup window that contains more information about a Session object

public class SessionsPopup extends AppCompatActivity {

    /**
     * Populates empty xml layout with appropriate Session information
     * @param savedInstanceState Bundle, saves user choices for next onCreate
     */
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

        Button button = (Button) findViewById(R.id.cancel_see_session);
        button.setOnClickListener(new View.OnClickListener() {
            /**
             * Instantiates functionality for button on Sessions Popup
             * allows for changing of pages when button is pressed (sessions popup back to browse page)
             * @param v current view where Popup is located
             */
            @Override
            public void onClick(View v) {
                SessionsPopup.super.onBackPressed();
            }
        });
    }
}
