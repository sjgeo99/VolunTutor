package com.example.voluntutor.mRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.voluntutor.R;

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

    public TextView getDaytxt() {
        return daytxt;
    }

    public TextView getTimetxt() {
        return timetxt;
    }

    public Spinner getTime() {
        return time;
    }

    public Spinner getDay() {
        return day;
    }

    public EditText getEt() {
        return et;
    }

    public Button getGo() {
        return go;
    }

    public EditText getLocation() { return location; }

}
