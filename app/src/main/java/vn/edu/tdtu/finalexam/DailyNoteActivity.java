package vn.edu.tdtu.finalexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DailyNoteActivity extends AppCompatActivity {
    TextView backBtn, dayTV;
    RelativeLayout emptyDailyEvent;
    RecyclerView dailyRecyclerView;
    Button addEventBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_note);

        backBtn = findViewById(R.id.backBtn);
        dayTV = findViewById(R.id.dayTextView);
        emptyDailyEvent = findViewById(R.id.emptyDailyEvent);
        dailyRecyclerView = findViewById(R.id.dailyRecycleView);
        addEventBtn = findViewById(R.id.addEventBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
    }

    private void showAddDialog() {
        EventDialog eventDialog = new EventDialog();
        eventDialog.show(getSupportFragmentManager(), "");
    }
}