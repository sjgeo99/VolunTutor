package com.example.voluntutor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SessionsPopup extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        //TODO: get session data from the bundle
        setContentView(R.layout.sessions_popup);

        //TODO: set text views
    }
}
