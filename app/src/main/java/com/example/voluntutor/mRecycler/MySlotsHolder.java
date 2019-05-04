package com.example.voluntutor.mRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.voluntutor.R;

public class MySlotsHolder extends RecyclerView.ViewHolder {
    TextView daytxt;
    TextView timetxt;
    LinearLayout l;

    public MySlotsHolder(View itemView)
    {
        super(itemView);
        daytxt = itemView.findViewById(R.id.slot_day);
        timetxt = itemView.findViewById(R.id.slot_time);
        l = itemView.findViewById(R.id.slots_module);

    }
}
