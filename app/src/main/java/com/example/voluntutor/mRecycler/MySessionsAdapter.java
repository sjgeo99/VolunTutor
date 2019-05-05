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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        Sessions s = sessions.get(position);
        SharedPreferences sharedPref = c.getSharedPreferences("Startup info", 0);
        boolean b = sharedPref.getBoolean("isTutor", true);
        if(b) {
            String tutee = "Tutoring " + s.getTutee();
            holder.nametxt.setText(tutee);
        }
        else {
            String tutor = "Tutored by " + s.getTutor();
            holder.nametxt.setText(tutor);
        }

        Date d = s.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        String date = sdf.format(d);
        holder.date.setText(date);

        SimpleDateFormat sdf2 = new SimpleDateFormat("h:mm a");
        String time = sdf2.format(d);
        time = time + " - ";
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MINUTE, s.getLength());
        Date later = c.getTime();
        time = time + sdf2.format(later);
        holder.time.setText(time);

        holder.l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add bundle code to transfer sessions data
            }
        });
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
