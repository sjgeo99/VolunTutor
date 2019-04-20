package com.example.voluntutor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.StringTokenizer;

public class MakeUserFragment extends Fragment {
    public static String id;

    /**
     * Updates the path to the object
     * @param s the new path name
     */
    public static void setID(String s) {
        id = s;
    }
    /**
     * returns the path to the tutor object of the person
     * @return the path
     */
    public static String getID() {
        return id;
    }
    //initializes the Settings Fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.make_user, container, false);
        Button button = (Button) view.findViewById(R.id.make_user_obj);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EditText txtName = (EditText) view.findViewById(R.id.enter_name);
                EditText txtSchool = (EditText) view.findViewById(R.id.enter_school);
                EditText txtDOW = (EditText) view.findViewById(R.id.enter_session_day);
                EditText txtStart = (EditText) view.findViewById(R.id.enter_session_start);
                EditText txtEnd = (EditText) view.findViewById(R.id.enter_session_end);
                EditText txtSubjects = (EditText) view.findViewById(R.id.enter_subjects);

                String name = txtName.getText().toString();
                String school = txtSchool.getText().toString();
                String DOW = txtDOW.getText().toString();
                String start = txtStart.getText().toString();
                StringTokenizer st = new StringTokenizer(start, ":");
                int startHr = Integer.parseInt(st.nextToken());
                int startMin = Integer.parseInt(st.nextToken());
                String end = txtEnd.getText().toString();
                StringTokenizer str = new StringTokenizer(end, ":");
                int endHr = Integer.parseInt(str.nextToken());
                int endMin = Integer.parseInt(str.nextToken());

                String subjects = txtSubjects.getText().toString();

                TimeSlot ts = new TimeSlot(DOW, startHr, startMin, endHr, endMin);
                Tutor t = new Tutor(name, school);
                t.addTimeSlots(ts);

                if(subjects.contains(", ")) {
                    String[] split = subjects.split(", ");
                    for(String s: split) {
                        t.addSubject(s);
                    }
                }
                else t.addSubject(subjects);

                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference ref = fb.getReference("tutors");
                DatabaseReference nRef = ref.push();
                nRef.setValue(t);
                id = nRef.getKey();
            }
        });
        return view;
    }
}
