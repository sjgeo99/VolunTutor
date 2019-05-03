package com.example.voluntutor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TutorPopup extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String name = (String) bundle.get("Name");
        String school = (String) bundle.get("School");
        ArrayList<String> subjects = (ArrayList<String>) bundle.get("Subject");
        ArrayList<TimeSlot> timeSlots = (ArrayList<TimeSlot>) bundle.get("TimeSlots");
        setContentView(R.layout.tutor_popup_fragment);

        TextView setName = (TextView) findViewById(R.id.setName);
        TextView setSchool = (TextView) findViewById(R.id.setSchool);
        RecyclerView rv = (RecyclerView) findViewById(R.id.setSubjects);

        setName.setText(name);
        setSchool.setText(school);
    }


}
