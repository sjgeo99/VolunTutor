package com.example.voluntutor;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsFragment extends Fragment {
    public int[] addVals = new int[4];
    public String DOWA;
    public TimeSlot toRemove = new TimeSlot();
    public ArrayList<TimeSlot> ts = new ArrayList<TimeSlot>();
    public ArrayList<String> subjects = new ArrayList<String>();
    public ArrayAdapter<TimeSlot> ada;
    public ArrayAdapter<String> rSubs;
    public String removeSubj;
    public ArrayAdapter<String> aSubs;
    public String addSubject;

    //initializes the Settings Fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.settingsfragment, container, false);
        ada = new ArrayAdapter<TimeSlot>(this.getContext(),
                android.R.layout.simple_spinner_dropdown_item, ts);
        rSubs = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_dropdown_item, subjects);

        Resources res = getResources();
        String[] subs = res.getStringArray(R.array.subjects);
        ArrayList<String> alSubs = new ArrayList<String>(Arrays.asList(subs));

        aSubs = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, alSubs);

        makeSpinners(view);
        setHints(view);

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
                final DatabaseReference nameRef = ref.child(MakeUserFragment.getID()).getRef();

                nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            if(ds.getKey().equals("subjects")) {
                                ArrayList<String> s = (ArrayList<String>) ds.getValue();
                                s.add(addSubject);
                                ds.getRef().setValue(s);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        Button buttonAddTS = (Button) view.findViewById(R.id.add_slot_confirm);
        buttonAddTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference ref = fb.getReference("tutors");
                DatabaseReference nameRef = ref.child(MakeUserFragment.getID()).getRef();

                TimeSlot toAdd = new TimeSlot(DOWA, addVals[0], addVals[1], addVals[2],
                        addVals[3]);

                nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            if(ds.getKey().equals("timeSlots")) {
                                ArrayList<TimeSlot> s = (ArrayList<TimeSlot>) ds.getValue();
                                TimeSlot toAdd = new TimeSlot(DOWA, addVals[0], addVals[1], addVals[2],
                                        addVals[3]);
                                s.add(toAdd);
                                ds.getRef().setValue(s);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
                            if(subj.equals(removeSubj)) ds.getRef().removeValue();
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
                Log.d("timeslots", Arrays.toString(ts.toArray()));
                Log.d("to remove", toRemove.toString());
                Spinner s = (Spinner) view.findViewById(R.id.spinnerRemove);

                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference ref = fb.getReference("/tutors");
                DatabaseReference mySlots = ref.child(MakeUserFragment.getID()).getRef().child("timeSlots");
                mySlots.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            TimeSlot check = ds.getValue(TimeSlot.class);

                            if(check.equals(toRemove)) {
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

    private void makeSpinners(View v) {
        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        DatabaseReference ref = fb.getReference("/tutors");
        DatabaseReference mySubs = ref.child(MakeUserFragment.getID()).getRef().child("subjects");

        Spinner addSub = (Spinner) v.findViewById(R.id.addSubSpin);
        aSubs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addSub.setAdapter(aSubs);
        mySubs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    String s = ds.getValue(String.class);
                    aSubs.remove(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addSub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addSubject = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerRSubs = (Spinner) v.findViewById(R.id.spinnerRemS);
        rSubs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRSubs.setAdapter(rSubs);

        spinnerRSubs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                removeSubj = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mySubs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    rSubs.add(ds.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Spinner spinnerRemove = (Spinner) v.findViewById(R.id.spinnerRemove);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRemove.setAdapter(ada);

        spinnerRemove.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toRemove = (TimeSlot) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DatabaseReference mySlots = ref.child(MakeUserFragment.getID()).getRef().child("timeSlots");
        mySlots.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ada.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    ada.add(ds.getValue(TimeSlot.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Spinner spinnerDOWA = (Spinner) v.findViewById(R.id.spinnerDOWA);
        ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(getContext(),
                R.array.days, android.R.layout.simple_spinner_item);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDOWA.setAdapter(adapt);

        spinnerDOWA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                DOWA = s;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerSHAdd = (Spinner) v.findViewById(R.id.spinnerSHAdd);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.hours, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSHAdd.setAdapter(adapter);

        spinnerSHAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addVals[0] = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerSMAdd = (Spinner) v.findViewById(R.id.spinnerSMAdd);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.minutes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSMAdd.setAdapter(adapter2);

        spinnerSMAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addVals[1] = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerEHAdd = (Spinner) v.findViewById(R.id.spinnerEHAdd);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getContext(),
                R.array.hours, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEHAdd.setAdapter(adapter3);

        spinnerEHAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addVals[2] = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerEMAdd = (Spinner) v.findViewById(R.id.spinnerEMAdd);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(getContext(),
                R.array.minutes, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEMAdd.setAdapter(adapter4);

        spinnerEMAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addVals[3] = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setHints(View v) {
        final EditText changeName = (EditText) v.findViewById(R.id.change_name_student_field);
        final EditText changeSchool = (EditText) v.findViewById(R.id.change_school_student_field);

        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.shared_pref_name), 0);
        String nameHint = sharedPref.getString(getString(R.string.name), "");
        String schoolHint = sharedPref.getString(getString(R.string.school), "");

        changeName.setHint("Current name: " + nameHint);
        changeSchool.setHint("Current school: " + schoolHint);
    }
}
