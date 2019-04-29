package com.example.voluntutor.mRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.voluntutor.R;

public class MyAdapter extends RecyclerView.Adapter<myHolder> {

    Context c;
    String[] sessions;

    public MyAdapter(Context c, String[] sessions)
    {
        this.c = c;
        this.sessions =sessions;
    }


    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_model,parent,false);


        return new myHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        holder.nametxt.setText(sessions[position]);

    }

    @Override
    public int getItemCount() {
        return sessions.length;
    }
}
