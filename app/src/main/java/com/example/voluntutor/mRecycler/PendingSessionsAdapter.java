package com.example.voluntutor.mRecycler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.voluntutor.MakeUserFragment;
import com.example.voluntutor.R;
import com.example.voluntutor.Sessions;
import com.example.voluntutor.SessionsPopup;
import com.example.voluntutor.Student;
import com.example.voluntutor.Tutor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class PendingSessionsAdapter extends RecyclerView.Adapter<PendingSessionsHolder> {

    private Context c;
    private ArrayList<Sessions> sessions;
    private String str = "";
    private String date = "";
    private String time = "";
    private boolean yes;
    private boolean yess;
    private boolean tuteeDelete;
    private boolean tuteeDelete2;


    public PendingSessionsAdapter(Context c, ArrayList<Sessions> sess1)
    {
        this.c = c;
        sessions = sess1;
    }

    @NonNull
    @Override
    public PendingSessionsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_psessions_model,viewGroup,false);
        return new PendingSessionsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PendingSessionsHolder holder, int position) {
        final int pos = position;
        final Sessions s = sessions.get(position);

        if(s.getImTutor()) {
            str = "Tutoring: " + s.getTutee();
            holder.getName().setText(str);
        }
        else {
            str = "Tutored by: " + s.getTutor();
            holder.getName().setText(str);
        }

        Date d = new Date(Long.parseLong(s.getDate()));
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd");
        date = sdf.format(d);
        holder.getDay().setText(date);

        SimpleDateFormat sdf2 = new SimpleDateFormat("h:mm a");
        time = sdf2.format(d);
        time = time + " - ";
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, s.getLength());
        Date later = cal.getTime();
        time = time + sdf2.format(later);
        holder.getTime().setText(time);

        yes = true;
        yess = true;
        tuteeDelete = true;
        tuteeDelete2 = true;

        //TODO: check if this actually works
        holder.getYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Sessions selected = sessions.get(pos);
                Log.d("selected", selected.getSVerified() + " " + selected.getImTutor() +
                        " " + selected.getTVerified());
                //this works
                if(selected.getImTutor()) {
                    FirebaseDatabase fb = FirebaseDatabase.getInstance();
                    DatabaseReference dr = fb.getReference("tutors");
                    dr.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                Tutor t = ds.getValue(Tutor.class);
                                if(t.hasPsession(selected)) {
                                    t.removePsession(selected);
                                    Sessions tVer = selected;
                                    tVer.setTverified(true);
                                    t.addPsession(tVer);
                                    ds.getRef().setValue(t);
                                    selected.setTverified(false);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    final Sessions s1 = new Sessions(selected.getDate(), selected.getLocation(), selected.getLength(),
                            selected.getTutor(), selected.getTutee(), true);
                    final Sessions s2 = new Sessions(selected.getDate(), selected.getLocation(), selected.getLength(),
                            selected.getTutor(), selected.getTutee(), selected.getImTutor());
                    s2.setImTutor(false);

                    FirebaseDatabase fb = FirebaseDatabase.getInstance();
                    DatabaseReference dr = fb.getReference("tutors");
                    dr.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                Tutor t = ds.getValue(Tutor.class);
                                if(t.hasPsession(s2)) {
                                    t.removePsession(s2);
                                    ds.getRef().setValue(t);
                                }
                                if(t.hasPsession(s1)) {
                                    t.psessionSverify(s1);
                                    ds.getRef().setValue(t);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //this also works
                    DatabaseReference studentRef = fb.getReference("students");
                    studentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                Student t = ds.getValue(Student.class);
                                if(t.hasPsession(s2)) {
                                    t.removePsession(s2);
                                    ds.getRef().setValue(t);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        holder.getNo().setOnClickListener(new View.OnClickListener() {
            final Sessions selected = sessions.get(pos);
            @Override
            public void onClick(View v) {
                //check through tutor list and delete any occurrences of the selected session
                //delete whether the imTutor boolean is true or false (because we also want to
                //delete the tutee's session)
                //this seems to work
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference dr = fb.getReference("tutors");
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            Sessions s = selected;
                            s.setSverified(false);
                            s.setImTutor(false);
                            Sessions s2 = s;
                            s.setImTutor(true);
                            Tutor t = ds.getValue(Tutor.class);
                            if(t.hasPsession(s)) {
                                t.removePsession(s);
                                ds.getRef().setValue(t);
                            }
                            else if(t.hasPsession(s2)) {
                                t.removePsession(s2);
                                ds.getRef().setValue(t);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //this also works
                DatabaseReference studentRef = fb.getReference("students");
                studentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            Sessions s = selected;
                            s.setSverified(false);
                            s.setImTutor(false);
                            Student t = ds.getValue(Student.class);
                            if(t.hasPsession(s)) {
                                t.removePsession(s);
                                ds.getRef().setValue(t);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        holder.getC().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, SessionsPopup.class);
                intent.putExtra("Name", str);
                intent.putExtra("Date", date);
                intent.putExtra("Time", time);
                intent.putExtra("Location", sessions.get(holder.getAdapterPosition()).getLocation());
                c.startActivity(intent);
            }
        });
    }

    public void remove(int n) {
        sessions.remove(n);
        notifyDataSetChanged();
    }

    public void remove(Sessions s) {
        sessions.remove(s);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public void add(Sessions t) {
        sessions.add(t);
        notifyDataSetChanged();
    }

    public void clear() {
        sessions = new ArrayList<Sessions>();
    }
}
