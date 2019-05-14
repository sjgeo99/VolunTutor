package com.example.voluntutor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.voluntutor.mRecycler.MySessionsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This class provides the tools accessed by the Hours Page fragment
 * and also allows for it to be displayed in accordance with the
 * commands recieved from the bottom navigation bar
 */
public class HoursFragment extends Fragment {
    public MySessionsAdapter hoursAdapter;
    public ArrayList<Sessions> verified = new ArrayList<Sessions>();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.hoursfragment, container, false);

        RecyclerView rv = view.findViewById(R.id.verified_sessions);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        hoursAdapter = new MySessionsAdapter(this.getContext(), verified);
        rv.setAdapter(hoursAdapter);
        Log.d("Making adapter", "yes");

        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        DatabaseReference dr = fb.getReference("tutors");
        dr.addValueEventListener(new ValueEventListener() {

            /**
             * Adds a completed session to the RecyclerView adapter for the Hours class
             * @param dataSnapshot copy of most recent Sessions data in Firebase
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hoursAdapter.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    if(ds.getKey().equals(MakeUserFragment.getID())) {
                        Tutor t = ds.getValue(Tutor.class);
                        ArrayList<Sessions> s = t.getVsessions();
                        for(Sessions sess: s) {
                            hoursAdapter.add(sess);
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

        Button button = (Button) view.findViewById(R.id.update_hours);
        button.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                Log.d("button clicked", "yes");
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference ref = fb.getReference("tutors");
                DatabaseReference nameRef = ref.child(MakeUserFragment.getID()).getRef().child("vsessions").getRef();
                nameRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    /**
                     * Returns updated hours count based on updated Session information
                     * @param dataSnapshot copy of current Sessions data in Firebase
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("in ondatachange", "yes");
                        int count = 0;
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            Sessions s = ds.getValue(Sessions.class);
                            count += s.getLength();
                        }
                        TextView t = view.findViewById(R.id.showHours);
                        String set = ((double) count / 60) + "";
                        t.setText(set);
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
}
