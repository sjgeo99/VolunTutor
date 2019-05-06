package com.example.voluntutor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.voluntutor.mRecycler.MyTutorAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/**
 * This class provides the tools accessed by the Search Page fragment
 * and also allows for it to be displayed in accordance with the
 * commands recieved from the bottom navigation bar
 */
public class SearchFragment extends Fragment {

    public ArrayList<Tutor> tutors = new ArrayList<Tutor>();
    public MyTutorAdapter searchAdapter;
    public String searched_for;
    public String checked = "";

    /**
     * Instantiates the UI view of a particular fragment
     *
     * @param inflater           inputted (Inflater) object which inflates views in a particular fragment
     * @param container          inputted (ViewGroup) object which, when non-null, consists of
     *                           the parent view attached to a particular fragment
     * @param savedInstanceState inputted (Bundle) object which, when not-null, constructs a
     *                           particular fragment from a previously saved state
     * @return the View for the UI of a particular fragment, or NULL
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.searchfragment, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.searchRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchAdapter = new MyTutorAdapter(this.getActivity(), tutors);
        recyclerView.setAdapter(searchAdapter);

        onRadioButtonClicked(rootView);

        ImageButton go = (ImageButton) rootView.findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = rootView.findViewById(R.id.subject_search);
                searched_for = et.getText().toString();
                FirebaseDatabase fb2 = FirebaseDatabase.getInstance();
                DatabaseReference dr2 = fb2.getReference("tutors");
                dr2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        searchAdapter.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Tutor t = ds.getValue(Tutor.class);
                            if (t.containsSub(searched_for)) {
                                searchAdapter.add(t);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


        });


        return rootView;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        final View v = view;
        RadioGroup r = (RadioGroup) view.findViewById(R.id.radioSubs);
        r.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checked = ((RadioButton) v.findViewById(checkedId)).getText().toString();

                FirebaseDatabase fb2 = FirebaseDatabase.getInstance();
                DatabaseReference dr2 = fb2.getReference("tutors");
                dr2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        searchAdapter.clear();
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            Tutor t = ds.getValue(Tutor.class);
                            if(t.containsSub(checked)) searchAdapter.add(t);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}


