package com.example.voluntutor.mRecycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.voluntutor.R;
import com.example.voluntutor.Tutor;
import com.example.voluntutor.TutorPopup;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Creates each of the individual views in the recycler view that displays tutors
 */

public class MyTutorAdapter extends RecyclerView.Adapter<myTutorHolder> {

    private Context c;
    private ArrayList<Tutor> tutors;

    public MyTutorAdapter(Context c, ArrayList<Tutor> tutors)
    {
        this.c = c;
        this.tutors = tutors;
    }

    /**
     * Initializes the ViewHolder for each element being displayed
     * @param parent the user will not have to put this in (it is automatically called)
     * @param viewType this will also be automatically called
     * @return the holder
     */
    @NonNull
    @Override
    public myTutorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_model,parent,false);
        return new myTutorHolder(v);
    }

    /**
     * Populates each individual aspect of the holder, contains the onclick as well
     * @param holder the holder to be populated
     * @param position which element of the holder is being put in
     */
    @Override
    public void onBindViewHolder(@NonNull myTutorHolder holder, final int position) {
        //populates the general tutor view with general info
        String n = "Name: " + tutors.get(position).getName();
        holder.getNametxt().setText(n);
        String s = "School: " + tutors.get(position).getSchool();
        holder.getSchooltxt().setText(s);

        String subs = "Subject(s): ";
        ArrayList<String> subjects = tutors.get(position).getSubjects();
        if(subjects.size() == 2) {
            subs = subs + subjects.get(0) + " and " + subjects.get(1);
        }
        else if(subjects.size() > 2) {
            for (int i = 0; i < subjects.size() - 1; i++) {
                subs = subs.concat(subjects.get(i) + ", ");
            }
            subs = subs.concat("and " + subjects.get(subjects.size() - 1));
        }
        else if(subjects.size() == 1) subs = subs.concat(subjects.get(0));
        holder.getSubjectstxt().setText(subs);

        //opens the tutor popup page if the tutor gets clicked on
        holder.getRl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tutor t = tutors.get(position);
                Intent intent = new Intent(c, TutorPopup.class);
                intent.putExtra("Name", t.getName());
                intent.putExtra("School", t.getSchool());
                intent.putExtra("Subjects", t.getSubjects());
                Log.d("timeSlots put", Arrays.toString(t.getTimeSlots().toArray()));
                intent.putExtra("TimeSlots", t.getTimeSlots());
                c.startActivity(intent);
            }
        });
    }

    /**
     * Gets the number of items in the adapter
     * @return the number of items in the adapter
     */
    @Override
    public int getItemCount() {
        return tutors.size();
    }

    /**
     * Adds a tutor to the adapter
     * @param s the new tutor
     */
    public void add(Tutor s) {
        tutors.add(s);
        notifyDataSetChanged();
    }

    /**
     * Clears the adapter of all tutors
     */
    public void clear() {
        tutors = new ArrayList<Tutor>();
        notifyDataSetChanged();
    }
}
