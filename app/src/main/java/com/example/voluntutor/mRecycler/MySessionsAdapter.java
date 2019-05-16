package com.example.voluntutor.mRecycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

/**
 * Creates each of the individual views in the recycler views that display Sessions (except pending)
 */

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


    /**
     * Initializes the ViewHolder for each element being displayed
     * @param parent the user will not have to put this in (it is automatically called)
     * @param viewType this will also be automatically called
     * @return the holder
     */
    @NonNull
    @Override
    public MySessionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_sessions_model,parent,false);


        return new MySessionsHolder(v);
    }

    /**
     * Populates each individual aspect of the holder, contains the onclick for each object as well
     * @param holder the holder to be populated
     * @param position which element of the holder is being put in
     */
    @Override
    public void onBindViewHolder(@NonNull final MySessionsHolder holder, int position) {
        Sessions s = sessions.get(position);

        if(s.getImTutor()) {
            name = "Tutoring: " + s.getTutee();
            holder.getNametxt().setText(name);
        }
        else {
            name = "Tutored by: " + s.getTutor();
            holder.getNametxt().setText(name);
        }

        Date d = new Date(Long.parseLong(s.getDate()));
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd");
        date = sdf.format(d);
        holder.getDate().setText(date);

        SimpleDateFormat sdf2 = new SimpleDateFormat("h:mm a");
        time = sdf2.format(d);
        time = time + " - ";
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, s.getLength());
        Date later = cal.getTime();
        time = time + sdf2.format(later);
        holder.getTime().setText(time);

        holder.getC().setOnClickListener(new View.OnClickListener() {
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

    /**
     * Gets how many items are in the adapter
     * @return the number of items in the adapter
     */
    @Override
    public int getItemCount() {
        return sessions.size();
    }

    /**
     * Adds a new element to the adapter
     * @param s the new Session
     */
    public void add(Sessions s) {
        sessions.add(s);
        Collections.sort(sessions);
        notifyDataSetChanged();
    }

    /**
     * Clears the adapter
     */
    public void clear() {
        sessions = new ArrayList<Sessions>();
        notifyDataSetChanged();
    }

    /**
     * Returns the whole list of sessions in the adapter
     * @return the list of sessions in the adapter
     */
    public ArrayList<Sessions> getSessions() {
        return sessions;
    }
}
