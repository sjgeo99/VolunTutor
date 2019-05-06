package com.example.voluntutor.mRecycler;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.voluntutor.R;

public class MySessionsHolder extends RecyclerView.ViewHolder{

    TextView nametxt;
    TextView date;
    TextView time;
    ConstraintLayout c;

    public MySessionsHolder(View itemView)
    {
        super(itemView);
        nametxt = itemView.findViewById(R.id.sessions_name_txt);
        date = itemView.findViewById(R.id.sessions_date_txt);
        time = itemView.findViewById(R.id.sessions_time_txt);
        c = itemView.findViewById(R.id.click_me);
    }

}
