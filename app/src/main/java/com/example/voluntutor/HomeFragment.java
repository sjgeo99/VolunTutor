package com.example.voluntutor;

import android.content.SharedPreferences;
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
import com.example.voluntutor.mRecycler.MySessionsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/**
 * This class provides the tools accessed by the Home Page fragment
 * and also allows for it to be displayed in accordance with the
 * commands recieved from the bottom navigation bar
 */
public class HomeFragment extends Fragment {

    public ArrayList<Sessions> psession = new ArrayList<Sessions>();
    public ArrayList<Sessions> usession = new ArrayList<Sessions>();
    public MySessionsAdapter uadapter;
    public MySessionsAdapter padapter;
    public DatabaseReference dr;

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

        View  rootView = inflater.inflate(R.layout.homefragment,container,false);

        RecyclerView rv = rootView.findViewById(R.id.homeRV);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        uadapter = new MySessionsAdapter(this.getContext(), psession);
        rv.setAdapter(uadapter);

        RecyclerView rv2 = rootView.findViewById(R.id.homeRV2);
        rv2.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        padapter = new MySessionsAdapter(this.getContext(), usession);
        rv2.setAdapter(padapter);

        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.shared_pref_name), 0);
        if(sharedPref.getBoolean(getString(R.string.isTutor), false)) { dr = fb.getReference("/tutors"); }
        else { dr = fb.getReference("/student"); }

        DatabaseReference myUsessions = dr.child(MakeUserFragment.getID()).getRef().child("usessions").getRef();
        DatabaseReference myPsessions = dr.child(MakeUserFragment.getID()).getRef().child("psessions").getRef();

        myUsessions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uadapter.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Sessions t = ds.getValue(Sessions.class);
                    Log.d("usessions", t.toString());
                    uadapter.add(t);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myPsessions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                padapter.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Sessions t = ds.getValue(Sessions.class);
                    Log.d("psessions", t.toString());
                    uadapter.add(t);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }

}
