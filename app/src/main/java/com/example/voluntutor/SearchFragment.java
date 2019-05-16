package com.example.voluntutor;

import android.content.res.Resources;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.voluntutor.mRecycler.MyTutorAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class provides the tools accessed by the Search Page fragment
 * and also allows for it to be displayed in accordance with the
 * commands recieved from the bottom navigation bar
 */
public class SearchFragment extends Fragment {

    public ArrayList<Tutor> tutors = new ArrayList<Tutor>();
    public MyTutorAdapter searchAdapter;
    public boolean firstTime = true;
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
        //instantiates recyclerview displaying tutors of a specific subject, making it empty at first
        RecyclerView recyclerView = rootView.findViewById(R.id.searchRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchAdapter = new MyTutorAdapter(this.getActivity(), tutors);
        recyclerView.setAdapter(searchAdapter);

        //makes the spinner and populates it with the subjects from resources
        Spinner s = rootView.findViewById(R.id.subj_search);
        Resources res = getResources();
        String[] subs = res.getStringArray(R.array.subjects);
        ArrayList<String> alSubs = new ArrayList<String>(Arrays.asList(subs));
        ArrayAdapter<String> a = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, alSubs);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(a);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             *
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!firstTime) {
                    final String selected = (String) parent.getItemAtPosition(position);
                    FirebaseDatabase fb2 = FirebaseDatabase.getInstance();
                    DatabaseReference dr2 = fb2.getReference("tutors");
                    dr2.addValueEventListener(new ValueEventListener() {
                        /**
                         * This method allows the searchAdapter to show all tutors with the specified subject
                         * @param dataSnapshot copy of most recent Tutor data in Firebase
                         */
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            searchAdapter.clear();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Tutor t = ds.getValue(Tutor.class);
                                if (t.containsSub(selected)) {
                                    searchAdapter.add(t);
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
                else { firstTime = false; }
            }

            /**
             * This method is called if there is no item selected
             * @param parent
             */
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }
}


