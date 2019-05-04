package com.example.voluntutor.mRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.voluntutor.R;
import com.example.voluntutor.TimeSlot;

import java.util.ArrayList;

public class MySlotsAdapter extends RecyclerView.Adapter<MySlotsHolder> {
    private Context c;
    private ArrayList<TimeSlot> slots;

    public MySlotsAdapter(Context c, ArrayList<TimeSlot> slots1)
    {
        this.c = c;
        this.slots = slots1;
    }

    @NonNull
    @Override
    public MySlotsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_slots_model,viewGroup,false);
        return new MySlotsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MySlotsHolder mySlotsHolder, int i) {
        mySlotsHolder.daytxt.setText(slots.get(i).getDayOfWeek());
        int sHour = slots.get(i).getsHour();
        int sMin = slots.get(i).getsMinute();
        int eHour = slots.get(i).geteHour();
        int eMin = slots.get(i).geteMinute();

        String time = new String();
        if(sHour > 12) {
            time = time.concat((sHour - 12) + ":" + sMin + " p.m.");
        }
        else {
            time = time.concat(sHour + ":" + sMin + " a.m.");
        }
        time = time.concat(" - ");
        if(eHour > 12) {
            time = time.concat((eHour - 12) + ":" + eMin + " p.m.");
        }
        else {
            time = time.concat(eHour + ":" + eMin + " a.m.");
        }
        mySlotsHolder.timetxt.setText(time);

        mySlotsHolder.l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked!", "that's it");
            }
        });
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    public void addSlot(TimeSlot t) {
        slots.add(t);
        notifyDataSetChanged();
    }

    public void clear() {
        slots = new ArrayList<TimeSlot>();
    }
}
