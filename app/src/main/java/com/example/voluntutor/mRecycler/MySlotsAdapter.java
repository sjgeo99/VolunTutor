package com.example.voluntutor.mRecycler;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.voluntutor.MakeUserFragment;
import com.example.voluntutor.R;
import com.example.voluntutor.Sessions;
import com.example.voluntutor.Student;
import com.example.voluntutor.TimeSlot;
import com.example.voluntutor.Tutor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MySlotsAdapter extends RecyclerView.Adapter<MySlotsHolder> {
    private Context c;
    private ArrayList<TimeSlot> slots;
    private ArrayList<String> datesList = new ArrayList<String>();
    private ArrayList<String> timesList = new ArrayList<String>();
    private String selectedDay;
    private String selectedTime;
    private String tutorName;
    private boolean firstStu = true;
    private boolean firstTut = true;

    public MySlotsAdapter(Context c, ArrayList<TimeSlot> slots1, String name)
    {
        this.c = c;
        this.slots = slots1;
        tutorName = name;
    }

    @NonNull
    @Override
    public MySlotsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_slots_model,viewGroup,false);
        return new MySlotsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MySlotsHolder mySlotsHolder, int i) {
        final int position = i;
        mySlotsHolder.getDaytxt().setText(slots.get(i).getDayOfWeek());
        int sHour = slots.get(i).getsHour();
        int sMin = slots.get(i).getsMinute();
        int eHour = slots.get(i).geteHour();
        int eMin = slots.get(i).geteMinute();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");

        String time = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, sHour);
        calendar.set(Calendar.MINUTE, sMin);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, eHour);
        calendar2.set(Calendar.MINUTE, eMin);
        time = time + sdf.format(calendar.getTime()) + " - " + sdf.format(calendar2.getTime());

        mySlotsHolder.getTimetxt().setText(time);

        ArrayAdapter<String> dates = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item,
                datesList);
        dates.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySlotsHolder.getDay().setAdapter(dates);
        //get first day coming up that is that day of the week
        int dow;
        switch(slots.get(i).getDayOfWeek()) {
            case "Sunday":
                dow = 1;
                break;
            case "Monday":
                dow = 2;
                break;
            case "Tuesday":
                dow = 3;
                break;
            case "Wednesday":
                dow = 4;
                break;
            case "Thursday":
                dow = 5;
                break;
            case "Friday":
                dow = 6;
                break;
            case "Saturday":
                dow = 7;
                break;
            default:
                dow = 7;
                break;
        }
        Log.d("Day of week number", slots.get(i).getDayOfWeek() + " " + dow);
        SimpleDateFormat sdf2 = new SimpleDateFormat("MMMM dd");
        boolean found = false;
        Calendar findFirst = Calendar.getInstance();
        while(!found) {
            if(findFirst.get(Calendar.DAY_OF_WEEK) == dow) {
                found = true;
                dates.add(sdf2.format(findFirst.getTime()));
                break;
            }
            else {
                findFirst.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        int year = findFirst.get(Calendar.YEAR);
        while(findFirst.get(Calendar.YEAR) == year) {
            findFirst.add(Calendar.DAY_OF_YEAR, 7);
            dates.add(sdf2.format(findFirst.getTime()));
        }
        mySlotsHolder.getDay().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedDay = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> times = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item,
                timesList);
        times.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySlotsHolder.getTime().setAdapter(times);

        while(calendar.getTime().before(calendar2.getTime())) {
            String s = sdf.format(calendar.getTime());
            times.add(s);
            calendar.add(Calendar.MINUTE, 1);
        }
        mySlotsHolder.getTime().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mySlotsHolder.getGo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mySlotsHolder.getLocation().getText() != null && mySlotsHolder.getEt().getText() != null
                && selectedDay != null && selectedTime != null) {
                    String location = mySlotsHolder.getLocation().getText().toString();
                    String len = mySlotsHolder.getEt().getText().toString();
                    int length = Integer.parseInt(len);
                    SharedPreferences sharedPref = c.getSharedPreferences("Startup info", 0);
                    String tutee = sharedPref.getString("Name", "");
                    String[] day = selectedDay.split(" ");
                    String[] time = selectedTime.split(":| ");

                    int month = 0;
                    switch (day[0]) {
                        case "February":
                            month = 1;
                            break;
                        case "March":
                            month = 2;
                            break;
                        case "April":
                            month = 3;
                            break;
                        case "May":
                            month = 4;
                            break;
                        case "June":
                            month = 5;
                            break;
                        case "July":
                            month = 6;
                            break;
                        case "August":
                            month = 7;
                            break;
                        case "September":
                            month = 8;
                            break;
                        case "October":
                            month = 9;
                            break;
                        case "November":
                            month = 10;
                            break;
                        case "December":
                            month = 11;
                            break;
                    }

                    Calendar setTime = Calendar.getInstance();
                    setTime.set(Calendar.MONTH, month);
                    setTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day[1]));
                    if(time[2].equals("PM")) {
                        setTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0])+12);
                    }
                    else { setTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0])); }
                    setTime.set(Calendar.MINUTE, Integer.parseInt(time[1]));

                    final Sessions s = new Sessions(Long.toString(setTime.getTime().getTime()), location, length, tutorName, tutee, false);
                    Log.d("session", s.toString());
                    FirebaseDatabase fb = FirebaseDatabase.getInstance();
                    Log.d("is tutor?", sharedPref.getBoolean("isTutor", false) + "");
                    Log.d("key", MakeUserFragment.getID());

                    TimeSlot t = slots.get(position);
                    if(t.isValid(s)) {

                        if (sharedPref.getBoolean("isTutor", false)) {
                            DatabaseReference dr = fb.getReference("tutors");
                            dr.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        if (ds.getKey().equals(MakeUserFragment.getID()) && firstStu) {
                                            Tutor t = ds.getValue(Tutor.class);
                                            t.addSession(s);
                                            ds.getRef().setValue(t);
                                            firstStu = false;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else {
                            DatabaseReference dr = fb.getReference("students");
                            dr.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        if (ds.getKey().equals(MakeUserFragment.getID()) && firstStu) {
                                            Student stu = ds.getValue(Student.class);
                                            stu.addSession(s);
                                            ds.getRef().setValue(stu);
                                            firstStu = false;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        //find tutor
                        DatabaseReference tutors = fb.getReference("tutors");
                        tutors.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Tutor t = ds.getValue(Tutor.class);
                                    if (t.getName().equals(tutorName) && firstTut) {
                                        s.setImTutor(true);
                                        t.addSession(s);
                                        ds.getRef().setValue(t);
                                        firstTut = false;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    else {
                        CharSequence text = "This session does not fit within the given time slot";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(c, text, duration);
                        toast.show();
                    }
                }
                else {
                    CharSequence text = "Please fill out all the fields to make a session";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(c, text, duration);
                    toast.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    public void addSlot(TimeSlot t) {
        slots.add(t);
        notifyDataSetChanged();
    }

    public void clear() {
        slots = new ArrayList<TimeSlot>();
    }
}
