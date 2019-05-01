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

import com.example.voluntutor.mRecycler.MyTutorAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
        View rootView = inflater.inflate(R.layout.searchfragment, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.searchRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchAdapter = new MyTutorAdapter(this.getActivity(), tutors);
        recyclerView.setAdapter(searchAdapter);

        FirebaseDatabase fb2 = FirebaseDatabase.getInstance();
        DatabaseReference dr2 = fb2.getReference("tutors");
        Query query = dr2.orderByChild("subjects").equalTo("math");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        Tutor t = snap.getValue(Tutor.class);
                        searchAdapter.add(t);
                    }
                    searchAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }



        }

