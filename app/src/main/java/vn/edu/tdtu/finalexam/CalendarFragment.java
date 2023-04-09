package vn.edu.tdtu.finalexam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CalendarFragment extends Fragment implements SelectRecycleViewInterface {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Calendar");
    TextView previousBtn, nextBtn;
    TextView monthYearText;
    RecyclerView calendarRecyclerView;
    LocalDate selectedDate;
    Activity activity;
    CalendarAdapter calendarAdapter;
    ArrayList<CalendarCell> daysInMonth;
    List<String> dayHasNotes;
    String[] colorList = {"red","blue","yellow","green"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
        monthYearText = activity.findViewById(R.id.monthYearText);
        calendarRecyclerView = activity.findViewById(R.id.calenderRecyclerView);

        previousBtn = activity.findViewById(R.id.previousBtn);
        nextBtn = activity.findViewById(R.id.nextBtn);

        calendarAdapter = new CalendarAdapter(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity,7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        selectedDate = LocalDate.now();
        dayHasNotes = new ArrayList<>();
        queryDateNote();
        setMonthView();
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
                queryDateNote();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.plusMonths(1);
                setMonthView();
                queryDateNote();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void queryDateNote() {
        String loginAccount = activity.getSharedPreferences("SP", Context.MODE_PRIVATE).getString("LoginBefore", "");
        reference.child(loginAccount).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dayHasNotes.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    if(!dayHasNotes.contains(child.child("date").getValue().toString())) {
                        dayHasNotes.add(child.child("date").getValue().toString());
                    }
                }
                System.out.println(dayHasNotes);
                List<String> dayNoteInMonth = dayHasNotes.stream().filter(obj -> obj.contains(selectedDate.getMonthValue() +"-"+ selectedDate.getYear())).collect(Collectors.toList());
                System.out.println(dayNoteInMonth);
                for(String i : dayNoteInMonth) {
                    String[] day = i.split("-");

                    int index = IntStream.range(0, daysInMonth.size())
                            .filter(obj -> daysInMonth.get(obj).getDay().equals(day[0]))
                            .findFirst()
                            .orElse(-1);

                    daysInMonth.get(index).setColor(colorList[new Random().nextInt(colorList.length)]);
                }
                calendarAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        monthYearText.setText(monthYearFormDate(selectedDate));
        daysInMonth = daysInMonthArray(selectedDate);

        calendarAdapter.setDayOfMonths(daysInMonth);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<CalendarCell> daysInMonthArray(LocalDate date) {
        ArrayList<CalendarCell> daysInMonthList = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);

        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int  i = 1; i <= 42; i++) {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthList.add(new CalendarCell("", "white" ));
            }
            else {
                String day = String.valueOf(i - dayOfWeek);
                daysInMonthList.add(new CalendarCell(day,"white"));
            }
        }
        return daysInMonthList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFormDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private String dayMonthYear(int position, LocalDate date) {
        return daysInMonth.get(position).getDay()+"-"+ date.getMonthValue() +"-"+ date.getYear();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position) {
        if(!daysInMonth.get(position).getDay().equals("")) {
            String textDay = dayMonthYear(position, selectedDate);
            Intent intent = new Intent(activity, DailyNoteActivity.class);
            intent.putExtra("date", textDay);
            startActivity(intent);
        }
    }

    @Override
    public void onDeleteClick(int position) {

    }
}