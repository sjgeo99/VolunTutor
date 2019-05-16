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

    /**
     * Instantiates the UI view of a particular fragment
     * @param inflater inputted (Inflater) object which inflates views in a particular fragment
     * @param container inputted (ViewGroup) object which, when non-null, consists of
     * the parent view attached to a particular fragment
     * @param savedInstanceState inputted (Bundle) object which, when not-null, constructs a
     * particular fragment from a previously saved state
     * @return the View for the UI of a particular fragment, or NULL
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.settingsfragment, container, false);
        ada = new ArrayAdapter<TimeSlot>(this.getContext(),
                android.R.layout.simple_spinner_dropdown_item, ts);
        rSubs = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_dropdown_item, subjects);

        final Resources res = getResources();
        String[] subs = res.getStringArray(R.array.subjects);
        ArrayList<String> alSubs = new ArrayList<String>(Arrays.asList(subs));

        aSubs = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, alSubs);

        makeSpinners(view);
        setHints(view);
        //Allows user to change their name
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

        //Allows user to change their school
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

        //Lets a tutor add a subject to their subjects list
        Button buttonAddS = (Button) view.findViewById(R.id.add_subj_confirm);
        buttonAddS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference ref = fb.getReference("tutors");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    /**
                     * Adds a new subject to a tutor's subject list
                     * @param dataSnapshot copy of most recent Tutor data in Firebase
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            if(ds.getKey().equals(MakeUserFragment.getID())) {
                                Tutor t = ds.getValue(Tutor.class);
                                if (!t.hasSubject(addSubject)) {
                                    t.addSubject(addSubject);
                                    ds.getRef().setValue(t);
                                    Log.d("adding subject", addSubject);
                                }
                            }
                        }
                    }
                    /**
                     * This method is called if the onDataChange method cannot be executed for any reason
                     * @param databaseError error produced by the onDataChange method not being able to run
                     */
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        //Lets a tutor add a time slot to their preexisting list.
        Button buttonAddTS = (Button) view.findViewById(R.id.add_slot_confirm);
        buttonAddTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference ref = fb.getReference("tutors");

                final TimeSlot toAdd = new TimeSlot(DOWA, addVals[0], addVals[1], addVals[2],
                        addVals[3]);

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    /**
                     * Adds a new TimeSlot to a tutor's TimeSlot list
                     * @param dataSnapshot copy of most recent Tutor data in Firebase
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            if(ds.getKey().equals(MakeUserFragment.getID())) {
                                Tutor t = ds.getValue(Tutor.class);
                                if(!t.hasTs(toAdd)) {
                                    t.addTimeSlots(toAdd);
                                    ds.getRef().setValue(t);
                                }
                            }
                        }
                    }

                    /**
                     * This method is called if the onDataChange method cannot be executed for any reason
                     * @param databaseError error produced by the onDataChange method not being able to run
                     */
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //Lets a tutor remove a subject from their preexisting list.
        Button buttonRemSubj = (Button) view.findViewById(R.id.rem_subj_confirm);
        buttonRemSubj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference ref = fb.getReference("tutors");
                ref.addValueEventListener(new ValueEventListener() {
                    /**
                     * Removes a subject from a tutor's subject list
                     * @param dataSnapshot copy of most recent Tutor data in Firebase
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            Tutor t = ds.getValue(Tutor.class);
                            if(t.hasSubject(removeSubj) && ds.getKey().equals(MakeUserFragment.getID())) {
                                t.removeSubject(removeSubj);
                                ds.getRef().setValue(t);
                                Log.d("removing subject", removeSubj);
                            }
                        }
                    }
                    /**
                     * This method is called if the onDataChange method cannot be executed for any reason
                     * @param databaseError error produced by the onDataChange method not being able to run
                     */
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //Lets a tutor remove a time slot from their preexisting list.
        Button buttonRemSlot = (Button) view.findViewById(R.id.remove_slot_confirm);
        buttonRemSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("timeslots", Arrays.toString(ts.toArray()));
                Log.d("to remove", toRemove.toString());

                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference ref = fb.getReference("/tutors");
                ref.addValueEventListener(new ValueEventListener() {
                    /**
                     * Removes a TimeSlot from a tutor's TimeSlot list
                     * @param dataSnapshot copy of most recent Tutor data in Firebase
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            Tutor t = ds.getValue(Tutor.class);
                            if(t.hasTs(toRemove)) {
                                t.removeTimeSlot(toRemove);
                                ds.getRef().setValue(t);
                            }
                        }
                    }
                    /**
                     * This method is called if the onDataChange method cannot be executed for any reason
                     * @param databaseError error produced by the onDataChange method not being able to run
                     */
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return view;
    }

    //Creates a spinner object with all of the subjects
    private void makeSpinners(View v) {
        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        DatabaseReference ref = fb.getReference("/tutors");
        DatabaseReference mySubs = ref.child(MakeUserFragment.getID()).getRef().child("subjects");

        Spinner addSub = (Spinner) v.findViewById(R.id.addSubSpin);
        aSubs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addSub.setAdapter(aSubs);
        mySubs.addValueEventListener(new ValueEventListener() {
            /**
             * Removes a Subject from a tutor's TimeSlot list
             * @param dataSnapshot copy of most recent Tutor data in Firebase
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    String s = ds.getValue(String.class);
                    aSubs.remove(s);
                }
            }
            /**
             * This method is called if the onDataChange method cannot be executed for any reason
             * @param databaseError error produced by the onDataChange method not being able to run
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addSub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             *This method receives the user input when a specific subject is selected
             * in the add subject dropdown
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addSubject = (String) parent.getItemAtPosition(position);
            }
            /**
             * This method is called if there is no item selected
             * @param parent
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerRSubs = (Spinner) v.findViewById(R.id.spinnerRemS);
        rSubs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRSubs.setAdapter(rSubs);

        spinnerRSubs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            /**
             *This method receives the user input when a specific subject is selected
             * in the remove subject dropdown
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                removeSubj = (String) parent.getItemAtPosition(position);
            }
            /**
             * This method is called if there is no item selected
             * @param parent
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Gets the list of removed subjects from Firebase into a list.
        mySubs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rSubs.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    rSubs.add(ds.getValue(String.class));
                }
            }
            /**
             * This method is called if the onDataChange method cannot be executed for any reason
             * @param databaseError error produced by the onDataChange method not being able to run
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Spinner spinnerRemove = (Spinner) v.findViewById(R.id.spinnerRemove);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRemove.setAdapter(ada);

        spinnerRemove.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             *This method receives the user input when a specific TimeSlot is selected
             * in the remove subject dropdown
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toRemove = (TimeSlot) parent.getItemAtPosition(position);
            }
            /**
             * This method is called if there is no item selected
             * @param parent
             */
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
            /**
             * This method is called if the onDataChange method cannot be executed for any reason
             * @param databaseError error produced by the onDataChange method not being able to run
             */
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
            /**
             * This method is called if there is no item selected
             * @param parent
             */
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
            /**
             * This method is called if there is no item selected
             * @param parent
             */
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
            /**
             * This method is called if there is no item selected
             * @param parent
             */
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
            /**
             * This method is called if there is no item selected
             * @param parent
             */
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
            /**
             * This method is called if there is no item selected
             * @param parent
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setHints(View v) {
        final EditText changeName = (EditText) v.findViewById(R.id.change_name_student_field);
        final EditText changeSchool = (EditText) v.findViewById(R.id.change_school_student_field);

        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        DatabaseReference dr = fb.getReference("tutors");

        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             * This changes the name and school of a user in Firebase
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    if(ds.getKey().equals(MakeUserFragment.getID())) {
                        Tutor t = ds.getValue(Tutor.class);
                        Log.d("tutor name", t.getName());
                        changeName.setHint("Current name: " + t.getName());
                        changeSchool.setHint("Current school: " + t.getSchool());
                    }
                }
            }
            /**
             * This method is called if the onDataChange method cannot be executed for any reason
             * @param databaseError error produced by the onDataChange method not being able to run
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
