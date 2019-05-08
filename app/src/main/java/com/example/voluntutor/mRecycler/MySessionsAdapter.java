package com.example.voluntutor.mRecycler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.voluntutor.R;
import com.example.voluntutor.Sessions;
import com.example.voluntutor.SessionsPopup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class MySessionsAdapter extends RecyclerView.Adapter<MySessionsHolder> {

    private final Context c;
    private ArrayList<Sessions> sessions;
    private String name = "";
    private String date = "";
    private String time = "";

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
    public void onBindViewHolder(@NonNull final MySessionsHolder holder, int position) {
        Sessions s = sessions.get(position);

        if(s.getImTutor()) {
            name = "Tutoring: " + s.getTutee();
            holder.nametxt.setText(name);
        }
        else {
            name = "Tutored by: " + s.getTutor();
            holder.nametxt.setText(name);
        }

        Date d = new Date(Long.parseLong(s.getDate()));
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd");
        date = sdf.format(d);
        holder.date.setText(date);

        SimpleDateFormat sdf2 = new SimpleDateFormat("h:mm a");
        time = sdf2.format(d);
        time = time + " - ";
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, s.getLength());
        Date later = cal.getTime();
        time = time + sdf2.format(later);
        holder.time.setText(time);

        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, SessionsPopup.class);
                intent.putExtra("Name", name);
                intent.putExtra("Date", date);
                intent.putExtra("Time", time);
                intent.putExtra("Location", sessions.get(holder.getAdapterPosition()).getLocation());
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public void add(Sessions s) {
        sessions.add(s);
        Collections.sort(sessions);
        notifyDataSetChanged();
    }
    public void clear() {
        sessions = new ArrayList<Sessions>();
        notifyDataSetChanged();
    }
    public ArrayList<Sessions> getSessions() {
        return sessions;
    }
}
