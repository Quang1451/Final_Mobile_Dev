package vn.edu.tdtu.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class DailyNoteActivity extends AppCompatActivity implements EventDialog.EventDialogInterface, SelectRecycleViewInterface {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Calendar");
    List<DailyNoteItem> dailyNoteItemList = new ArrayList<>();
    TextView backBtn, dayTV;
    RelativeLayout emptyDailyEvent;
    RecyclerView dailyRecyclerView;
    Button addEventBtn;
    DailyNoteAdapter dailyNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_note);

        backBtn = findViewById(R.id.backBtn);
        dayTV = findViewById(R.id.dayTextView);
        emptyDailyEvent = findViewById(R.id.emptyDailyEvent);
        dailyRecyclerView = findViewById(R.id.dailyRecycleView);
        addEventBtn = findViewById(R.id.addEventBtn);

        dayTV.setText(getIntent().getStringExtra("date"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        dailyRecyclerView.setLayoutManager(linearLayoutManager);
        dailyNoteAdapter = new DailyNoteAdapter(dailyNoteItemList, this);

        getListEvent();
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

    private void getListEvent() {
        String loginAccount = this.getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");
        String date = dayTV.getText().toString().trim();
        dailyNoteItemList.clear();

        reference.child(loginAccount).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            for(DataSnapshot child : task.getResult().getChildren()) {
                                String id = child.getKey();
                                String date = child.child("date").getValue().toString();
                                String time = child.child("time").getValue().toString();
                                String data = child.child("data").getValue().toString();
                                if(!date.equals(dayTV.getText().toString().trim())) {
                                    continue;
                                }
                                System.out.println(id);
                                DailyNoteItem dailyNoteItem = new DailyNoteItem(id, date, time, data);
                                dailyNoteItemList.add(dailyNoteItem);
                            }
                            Collections.sort(dailyNoteItemList, new Comparator<DailyNoteItem>() {
                                public int compare(DailyNoteItem o1, DailyNoteItem o2) {
                                    return o1.getTime().compareTo(o2.getTime());
                                }
                            });

                            dailyNoteAdapter.setDailyNoteList(dailyNoteItemList);
                            dailyRecyclerView.setAdapter(dailyNoteAdapter);

                            if(dailyNoteItemList.size() > 0) {
                                emptyDailyEvent.setVisibility(View.INVISIBLE);
                                dailyRecyclerView.setVisibility(View.VISIBLE);
                            }
                            else {
                                emptyDailyEvent.setVisibility(View.VISIBLE);
                                dailyRecyclerView.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });
    }

    private void showAddDialog() {
        EventDialog eventDialog = new EventDialog();
        eventDialog.show(getSupportFragmentManager(), "add daily event");
    }

    private void showEditDialog(DailyNoteItem item) {
        EventDialog eventDialog = new EventDialog("Edit", item);
        eventDialog.show(getSupportFragmentManager(), "edit daily event");
    }

    @Override
    public void addEventValue(String time, String content) {
        String loginAccount = this.getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");
        String date = dayTV.getText().toString().trim();
        int id = new Random().nextInt(10000);
        String strId = String.format("%05d", id);

        DailyNoteItem dailyNoteItem = new DailyNoteItem(strId, date, time, content);

        reference.child(loginAccount).child(strId).setValue(dailyNoteItem);
        getListEvent();
        Toast.makeText(this,"Đã thêm một hoạt động!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void editEventValue(String id, String time, String content) {
        String loginAccount = this.getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");
        String date = dayTV.getText().toString().trim();

        DailyNoteItem dailyNoteItem = new DailyNoteItem(id, date, time, content);
        reference.child(loginAccount).child(id).setValue(dailyNoteItem);
        getListEvent();
        Toast.makeText(this,"Đã chỉnh sửa hoạt động!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position) {
        DailyNoteItem item = dailyNoteItemList.get(position);
        showEditDialog(item);
    }

    @Override
    public void onDeleteClick(int position) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
        deleteDialog.setTitle("Thông báo");
        deleteDialog.setMessage("Bạn có muốn xóa hoạt động này?");

        deleteDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String loginAccount = getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");
                reference.child(loginAccount).child(dailyNoteItemList.get(position).getId()).removeValue();
                Toast.makeText(DailyNoteActivity.this, "Đã xóa hoạt động!", Toast.LENGTH_SHORT).show();
                getListEvent();
            }
        });

        deleteDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        deleteDialog.create().show();
    }
}