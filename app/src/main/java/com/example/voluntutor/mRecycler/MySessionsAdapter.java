package com.example.voluntutor.mRecycler;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.voluntutor.R;
import com.example.voluntutor.Sessions;
import com.example.voluntutor.Tutor;

import java.util.ArrayList;

public class MySessionsAdapter extends RecyclerView.Adapter<MySessionsHolder> {

    Context c;
    ArrayList<Sessions> sessions;

    public MySessionsAdapter(Context c, ArrayList<Sessions> sessions)
    {
        this.c = c;
        this.sessions =sessions;
    }
    public MySessionsAdapter(Context c)
    {
        this.c = c;
        sessions = new ArrayList<Sessions>();
    }


    @NonNull
    @Override
    public MySessionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_sessions_model,parent,false);


        return new MySessionsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MySessionsHolder holder, int position) {
        String name = "";
        holder.nametxt.setText(sessions.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public void add(Sessions s) {
        sessions.add(s);
        notifyDataSetChanged();
    }
    public void clear() {
        sessions = new ArrayList<Sessions>();
        notifyDataSetChanged();
    }
}
