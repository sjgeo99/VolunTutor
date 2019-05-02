package com.example.voluntutor.mRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.voluntutor.R;
import com.example.voluntutor.TimeSlot;
import com.example.voluntutor.Tutor;

import java.util.ArrayList;

public class MyTutorAdapter extends RecyclerView.Adapter<myTutorHolder> {

    Context c;
    ArrayList<Tutor> tutors;

    public MyTutorAdapter(Context c, ArrayList<Tutor> tutors)
    {
        this.c = c;
        this.tutors =tutors;
    }
    public MyTutorAdapter(Context c)
    {
        this.c = c;
        tutors = new ArrayList<Tutor>();
    }


    @NonNull
    @Override
    public myTutorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_model,parent,false);

        return new myTutorHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myTutorHolder holder, int position) {
        String n = "Name: " + tutors.get(position).getName();
        holder.nametxt.setText(n);
        String s = "School: " + tutors.get(position).getSchool();
        holder.schooltxt.setText(s);

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
        holder.subjectstxt.setText(subs);
    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }

    public void add(Tutor s) {
        tutors.add(s);
        notifyDataSetChanged();
    }
    public void clear() {
        tutors = new ArrayList<Tutor>();
        notifyDataSetChanged();
    }
}
