package com.example.voluntutor.mRecycler;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.voluntutor.R;

public class myTutorHolder extends RecyclerView.ViewHolder{

    TextView nametxt;
    TextView schooltxt;
    TextView subjectstxt;
    ConstraintLayout rl;

    public myTutorHolder(View itemView)
    {
        super(itemView);
        nametxt = itemView.findViewById(R.id.tutor_name_txt);
        schooltxt = itemView.findViewById(R.id.tutor_school_txt);
        subjectstxt = itemView.findViewById(R.id.tutor_subjects_txt);
        rl = itemView.findViewById(R.id.relativeLayout);

    }
}
