package com.example.voluntutor.mRecycler;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.voluntutor.R;

public class PendingSessionsHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView day;
    private TextView time;
    private Button yes;
    private Button no;
    private ConstraintLayout c;

    public PendingSessionsHolder(View view) {
        super(view);
        name = view.findViewById(R.id.psessions_name);
        day = view.findViewById(R.id.psessions_date);
        time = view.findViewById(R.id.psessions_time);
        yes = view.findViewById(R.id.verify_button);
        no = view.findViewById(R.id.no_verify_button);
        c = view.findViewById(R.id.psessions_constraint);
    }

    /**
     * Gets the constraint layout holding the pending session
     * @return the layout holding the session
     */
    public ConstraintLayout getC() {
        return c;
    }

    /**
     * Gets the Text View holding the name of the person the session is with
     * @return the Text View holding the name of the person the session is with
     */
    public TextView getName() {
        return name;
    }

    /**
     * Gets the Text View holding the day of the session
     * @return the Text View holding the day of the session
     */
    public TextView getDay() {
        return day;
    }

    /**
     * Gets the Text View holding the time of the session
     * @return the Text View holding the time of the session
     */
    public TextView getTime() {
        return time;
    }

    /**
     * Gets the button that says yes
     * @return the button that says yes
     */
    public Button getYes() {
        return yes;
    }

    /**
     * Gets the button that says no
     * @return the button that says no
     */
    public Button getNo() {
        return no;
    }
}
