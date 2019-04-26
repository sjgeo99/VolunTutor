package com.example.voluntutor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {

    //initializes the Settings Fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.settingsfragment, container, false);

        Button buttonName = (Button) view.findViewById(R.id.name_change_student_confirm);
        buttonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference ref = fb.getReference("tutors");
                DatabaseReference nameRef = ref.child(MakeUserFragment.getID()).getRef().child("name").getRef();

                EditText et = (EditText) view.findViewById(R.id.change_name_student_field);
                String newName = et.getText().toString();

                nameRef.setValue(newName);
            }
        });

        Button buttonSchool = (Button) view.findViewById(R.id.school_change_student_confirm);
        buttonSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference ref = fb.getReference("tutors");
                DatabaseReference nameRef = ref.child(MakeUserFragment.getID()).getRef().child("school").getRef();

                EditText et = (EditText) view.findViewById(R.id.change_school_student_field);
                String newSchool = et.getText().toString();

                nameRef.setValue(newSchool);
            }
        });

        Button buttonAddS = (Button) view.findViewById(R.id.add_subj_confirm);
        buttonAddS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference ref = fb.getReference("tutors");
                DatabaseReference nameRef = ref.child(MakeUserFragment.getID()).getRef().child("subjects").getRef();

                EditText et = (EditText) view.findViewById(R.id.add_subj_field);
                String newSub = et.getText().toString();

                nameRef.push().setValue(newSub);
            }
        });

        Button buttonAddTS = (Button) view.findViewById(R.id.add_slot_confirm);
        buttonAddTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference ref = fb.getReference("tutors");
                DatabaseReference nameRef = ref.child(MakeUserFragment.getID()).getRef().child("timeSlots").getRef();

                EditText et = (EditText) view.findViewById(R.id.add_slot_field);
                String[] splitted = et.getText().toString().split(", |:|-");


                nameRef.push().setValue(new TimeSlot(splitted[0], Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]),
                        Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4])));
            }
        });

        Button buttonRemSubj = (Button) view.findViewById(R.id.rem_subj_confirm);
        buttonRemSubj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                final DatabaseReference ref = fb.getReference("tutors");
                DatabaseReference mySubs = ref.child(MakeUserFragment.getID()).getRef().child("subjects");
                mySubs.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            String subj = ds.getValue(String.class);
                            EditText et = (EditText) view.findViewById(R.id.rem_subj_field);
                            String toRemove = et.getText().toString();
                            if(subj.equals(toRemove)) ds.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        Button buttonRemSlot = (Button) view.findViewById(R.id.remove_slot_confirm);
        buttonRemSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference ref = fb.getReference("/tutors");
                DatabaseReference mySlots = ref.child(MakeUserFragment.getID()).getRef().child("timeSlots");

                EditText et = (EditText) view.findViewById(R.id.remove_slot_field);
                String slotString = et.getText().toString();
                String[] split = slotString.split(", |:|-");
                final TimeSlot ts = new TimeSlot(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]),
                        Integer.parseInt(split[4]));
                Log.d("this is being removed", ts.toString());

                mySlots.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            TimeSlot check = ds.getValue(TimeSlot.class);
                            Log.d("these are the ts's", check.toString());
                            if(check.equals(ts)) {
                                ds.getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return view;
    }
}
