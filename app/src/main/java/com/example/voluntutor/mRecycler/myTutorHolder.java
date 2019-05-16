package com.example.voluntutor.mRecycler;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.voluntutor.R;

/**
 * Holds all the components of the view to be populated in the tutor adapter
 */

public class myTutorHolder extends RecyclerView.ViewHolder{

    private TextView nametxt;
    private TextView schooltxt;
    private TextView subjectstxt;
    private ConstraintLayout rl;

    public myTutorHolder(View itemView)
    {
        super(itemView);
        nametxt = itemView.findViewById(R.id.tutor_name_txt);
        schooltxt = itemView.findViewById(R.id.tutor_school_txt);
        subjectstxt = itemView.findViewById(R.id.tutor_subjects_txt);
        rl = itemView.findViewById(R.id.relativeLayout);

    }

    /**
     * Gets the Text View that holds the name of the tutor
     * @return the Text View that holds the name of the tutor
     */
    public TextView getNametxt() {
        return nametxt;
    }

    /**
     * Gets the Text View that holds the school of the tutor
     * @return the Text View that holds the school of the tutor
     */
    public TextView getSchooltxt() {
        return schooltxt;
    }

    /**
     * Gets the Text View that holds the subjects the tutor tutors in
     * @return the Text View that holds the subjects the tutor tutors in
     */
    public TextView getSubjectstxt() {
        return subjectstxt;
    }

    /**
     * Gets the constraint layout holding the tutor
     * @return the constraint layout holding tutor
     */
    public ConstraintLayout getRl() {
        return rl;
    }
}
