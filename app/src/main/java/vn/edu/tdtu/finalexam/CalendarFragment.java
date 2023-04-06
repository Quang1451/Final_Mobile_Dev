package vn.edu.tdtu.finalexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarFragment extends Fragment implements SelectRecycleViewInterface {
    TextView previousBtn, nextBtn;
    TextView monthYearText;
    RecyclerView calendarRecyclerView;
    LocalDate selectedDate;
    Activity activity;
    ArrayList<String> daysInMonth;
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

        selectedDate = LocalDate.now();
        setMonthView();

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.plusMonths(1);
                setMonthView();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        monthYearText.setText(monthYearFormDate(selectedDate));
        daysInMonth = daysInMonthArray(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(this);
        calendarAdapter.setDayOfMonths(daysInMonth);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity,7);

        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthList = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        System.out.println(yearMonth);
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);

        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int  i = 1; i <= 42; i++) {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthList.add("");
            }
            else {
                daysInMonthList.add(String.valueOf(i - dayOfWeek));
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
        return daysInMonth.get(position)+"/"+ date.getMonthValue() +"/"+ date.getYear();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position) {
        if(!daysInMonth.get(position).equals("")) {
            String text = dayMonthYear(position, selectedDate);
            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(activity, DayNoteActivity.class));
        }
    }

    @Override
    public void onDeleteClick(int position) {

    }
}