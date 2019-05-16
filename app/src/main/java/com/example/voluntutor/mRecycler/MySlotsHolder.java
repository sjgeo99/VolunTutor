package com.example.voluntutor.mRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.voluntutor.R;

/**
 * Holds all the components of the view to be populated in the slots adapter
 */

public class MySlotsHolder extends RecyclerView.ViewHolder {
    private TextView daytxt;
    private TextView timetxt;
    private Spinner time;
    private Spinner day;
    private EditText et;
    private Button go;
    private EditText location;

    public MySlotsHolder(View itemView)
    {
        super(itemView);
        daytxt = itemView.findViewById(R.id.slot_day);
        timetxt = itemView.findViewById(R.id.slot_time);
        time = itemView.findViewById(R.id.make_sess_time);
        day = itemView.findViewById(R.id.make_sess_day);
        et = itemView.findViewById(R.id.make_sess_length);
        go = itemView.findViewById(R.id.make_sess);
        location = itemView.findViewById(R.id.enterLocation);
    }

    /**
     * Gets the TextView that holds the day of the week of the session
     * @return the TextView that holds the day of the week of the session
     */
    public TextView getDaytxt() {
        return daytxt;
    }

    /**
     * Gets the TextView that holds the time of day of the session
     * @return the TextView that holds the time of day of the session
     */
    public TextView getTimetxt() {
        return timetxt;
    }

    /**
     * Gets the spinner that holds the times the user can choose to begin the session
     * @return the spinner with times
     */
    public Spinner getTime() {
        return time;
    }

    /**
     * Gets the spinner that holds the days the user can choose to hold the session
     * @return the spinner with the days the user can have the session
     */
    public Spinner getDay() {
        return day;
    }

    /**
     * Gets the field where the user can put in the length of the session
     * @return the field were the user puts the length of the session
     */
    public EditText getEt() {
        return et;
    }

    /**
     * Gets the Button that creates the session
     * @return the Button that creates the session
     */
    public Button getGo() {
        return go;
    }

    /**
     * Gets the field where the user can enter the location of the session
     * @return the field where the user can enter the location of the session
     */
    public EditText getLocation() { return location; }

}
