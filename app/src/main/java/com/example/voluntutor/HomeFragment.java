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
import com.example.voluntutor.mRecycler.PendingSessionsAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class provides the tools accessed by the Home Page fragment
 * and also allows for it to be displayed in accordance with the
 * commands recieved from the bottom navigation bar
 */
public class HomeFragment extends Fragment {

    public ArrayList<Sessions> psession = new ArrayList<Sessions>();
    public ArrayList<Sessions> usession = new ArrayList<Sessions>();
    public MySessionsAdapter uadapter;
    public PendingSessionsAdapter padapter;
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
        padapter = new PendingSessionsAdapter(this.getContext(), usession);
        rv2.setAdapter(padapter);

        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.shared_pref_name), 0);
        final boolean isTutor = sharedPref.getBoolean(getString(R.string.isTutor), false);
        if(isTutor) { dr = fb.getReference("/tutors"); }
        else { dr = fb.getReference("/student"); }

        DatabaseReference myUsessions = dr.child(MakeUserFragment.getID()).getRef().child("usessions").getRef();
        DatabaseReference myPsessions = dr.child(MakeUserFragment.getID()).getRef().child("psessions").getRef();
        //checks if upcoming sessions are in the past and moves them to psessions
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    if(ds.getKey().equals(MakeUserFragment.getID())) {
                        if(isTutor) {
                            Tutor t = ds.getValue(Tutor.class);
                            ArrayList<Sessions> usessionsT = t.getUsessions();
                            for(Sessions sess: usessionsT) {
                                if(Long.parseLong(sess.getDate()) < Calendar.getInstance().getTime().getTime()) {
                                    t.removeUsession(sess);
                                    t.addPsession(sess);
                                }
                            }
                            ds.getRef().setValue(t);
                        }
                        else {
                            Student s = ds.getValue(Student.class);
                            ArrayList<Sessions> usessionsS = s.getUSessions();
                            for(Sessions sess: usessionsS) {
                                if(Long.parseLong(sess.getDate()) < Calendar.getInstance().getTime().getTime()) {
                                    s.removeUsession(sess);
                                    s.addPsession(sess);
                                }
                            }
                            ds.getRef().setValue(s);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //populates upcoming sessions
        myUsessions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uadapter.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Sessions t = ds.getValue(Sessions.class);
                    uadapter.add(t);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myPsessions.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Sessions sess = dataSnapshot.getValue(Sessions.class);
                padapter.add(sess);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    int key = 0;
                    if(s != null) {
                        int prevKey = Integer.parseInt(s);
                        key = prevKey + 1;
                    }
                    padapter.remove(key);
                    Sessions newSess = dataSnapshot.getValue(Sessions.class);
                    padapter.add(newSess);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Sessions sess = dataSnapshot.getValue(Sessions.class);
                padapter.remove(sess);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }

}
