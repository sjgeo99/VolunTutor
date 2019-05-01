package com.example.voluntutor.mRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.voluntutor.R;
import com.example.voluntutor.TimeSlot;
import com.example.voluntutor.Tutor;

import java.util.ArrayList;

public class MyTutorAdapter extends RecyclerView.Adapter<myHolder> {

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
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_model,parent,false);


        return new myHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        holder.nametxt.setText(tutors.get(position).toString());

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
