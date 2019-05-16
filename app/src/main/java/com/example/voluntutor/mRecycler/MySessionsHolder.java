package com.example.voluntutor.mRecycler;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.voluntutor.R;

/**
 * Holds all the components of the view to be populated in the sessions adapter
 */

public class MySessionsHolder extends RecyclerView.ViewHolder{

    private TextView nametxt;
    private TextView date;
    private TextView time;
    private ConstraintLayout c;

    public MySessionsHolder(View itemView)
    {
        super(itemView);
        nametxt = itemView.findViewById(R.id.sessions_name_txt);
        date = itemView.findViewById(R.id.sessions_date_txt);
        time = itemView.findViewById(R.id.sessions_time_txt);
        c = itemView.findViewById(R.id.click_me);
    }

    /**
     * gets the TextView that holds the name of the person the session is with
     * @return the TextView that holds the name of the person the session is with
     */
    public TextView getNametxt() {
        return nametxt;
    }

    /**
     * Gets the TextView that holds the date of the session
     * @return the TextView that holds the date of the session
     */
    public TextView getDate() {
        return date;
    }

    /**
     * Gets the TextView that holds the time of the session
     * @return the TextView that holds the time of the session
     */
    public TextView getTime() {
        return time;
    }

    /**
     * Gets the constraint layout that holds the session
     * @return the constraint layout that holds the session
     */
    public ConstraintLayout getC() {
        return c;
    }
}
