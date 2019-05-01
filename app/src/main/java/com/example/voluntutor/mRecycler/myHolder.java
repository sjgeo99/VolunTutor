package com.example.voluntutor.mRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.voluntutor.R;

public class myHolder extends RecyclerView.ViewHolder{

    TextView nametxt;

    public myHolder(View itemView)
    {
        super(itemView);
        nametxt = itemView.findViewById(R.id.nameTxt);
    }

}
