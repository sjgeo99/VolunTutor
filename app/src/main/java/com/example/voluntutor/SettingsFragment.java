package com.example.voluntutor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class SettingsFragment extends Fragment {
    public int[] addVals = new int[4];
    public int[] remVals = new int[4];
    public String DOWA, DOWR;
    //initializes the Settings Fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.settingsfragment, container, false);

        makeAddSpinners(view);
        makeRemSpinners(view);

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

                nameRef.push().setValue(new TimeSlot(DOWA, addVals[0], addVals[1], addVals[2],
                        addVals[3]));
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

                final TimeSlot ts = new TimeSlot(DOWR, remVals[0], remVals[1],
                        remVals[2], remVals[3]);

                mySlots.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            TimeSlot check = ds.getValue(TimeSlot.class);

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

    private void makeAddSpinners(View v) {
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

    public void makeRemSpinners(View v) {
        Spinner spinnerDOWR = (Spinner) v.findViewById(R.id.spinnerDOWR);
        ArrayAdapter<CharSequence> ada = ArrayAdapter.createFromResource(getContext(),
                R.array.days, android.R.layout.simple_spinner_item);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDOWR.setAdapter(ada);

        spinnerDOWR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                DOWR = s;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerSHRem = (Spinner) v.findViewById(R.id.spinnerSHRem);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.hours, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSHRem.setAdapter(adapter);

        spinnerSHRem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                remVals[0] = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerSMRem = (Spinner) v.findViewById(R.id.spinnerSMRem);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.minutes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSMRem.setAdapter(adapter2);

        spinnerSMRem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                remVals[1] = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerEHRem = (Spinner) v.findViewById(R.id.spinnerEHRem);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getContext(),
                R.array.hours, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEHRem.setAdapter(adapter3);

        spinnerEHRem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                remVals[2] = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerEMRem = (Spinner) v.findViewById(R.id.spinnerEMRem);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(getContext(),
                R.array.minutes, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEMRem.setAdapter(adapter4);

        spinnerEMRem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                remVals[3] = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
