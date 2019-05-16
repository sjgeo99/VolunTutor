package com.example.voluntutor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.voluntutor.mRecycler.MySlotsAdapter;
import java.util.ArrayList;

//This class models the functionality of the popup window that contains more information about a Tutor object

public class TutorPopup extends AppCompatActivity {
    /**
     * Populates empty xml layout with appropriate Tutor information
     * @param savedInstanceState Bundle, saves user choices for next onCreate
     */
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String name = (String) bundle.get("Name");
        String school = (String) bundle.get("School");
        ArrayList<String> subjects = (ArrayList<String>) bundle.get("Subjects");
        ArrayList<TimeSlot> timeSlots = (ArrayList<TimeSlot>) bundle.get("TimeSlots");
        setContentView(R.layout.tutor_popup_fragment);

        TextView setName = (TextView) findViewById(R.id.setName);
        TextView setSchool = (TextView) findViewById(R.id.setSchool);
        TextView setSubs = (TextView) findViewById(R.id.setSubs);
        RecyclerView rv = (RecyclerView) findViewById(R.id.slots_rv);

        setName.setText(name);
        setSchool.setText(school);
        String subs = "Subject(s): ";
        if(subjects.size() == 2) {
            subs = subs + subjects.get(0) + " and " + subjects.get(1);
        }
        else if(subjects.size() > 2) {
            for (int i = 0; i < subjects.size() - 1; i++) {
                subs = subs.concat(subjects.get(i) + ", ");
            }
            subs = subs.concat("and " + subjects.get(subjects.size() - 1));
        }
        else if(subjects.size() == 1) subs = subs.concat(subjects.get(0));
        setSubs.setText(subs);
            rv.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));
        MySlotsAdapter adapter = new MySlotsAdapter(this.getBaseContext(), timeSlots, name);
        rv.setAdapter(adapter);

        Button b = (Button) findViewById(R.id.cancel_see_tutor);
        b.setOnClickListener(new View.OnClickListener() {

            /**
             * Shifts xml views when the back button is pressed
             * @param v current view the popup is on
             */
            @Override
            public void onClick(View v) {
                TutorPopup.super.onBackPressed();
            }
        });
    }


}
