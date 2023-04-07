package vn.edu.tdtu.finalexam;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener,SelectRecycleViewInterface {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Notes");
    List<NoteItem> noteItemList = new ArrayList<>();
    Activity activity;
    FloatingActionButton addNoteBtn;
    RecyclerView recyclerView;
    RelativeLayout emptyLayout;
    NoteItemAdapter noteItemAdapter;
    SearchView searchView;
    ProgressBar progressBar;
    private static boolean flag = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        addNoteBtn = activity.findViewById(R.id.addNoteBtn);

        searchView = activity.findViewById(R.id.searchMenu);
        progressBar = activity.findViewById(R.id.progressBar);
        recyclerView = activity.findViewById(R.id.reView);
        emptyLayout = activity.findViewById(R.id.emptyDailyEvent);

        noteItemAdapter = new NoteItemAdapter(activity, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        getListNoteItems("");

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getListNoteItems(newText.trim());
                searchView.clearFocus();
                return false;
            }
        });
    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(activity, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_popup);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_notes:
                Intent intent = new Intent(activity, AddNoteActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_draws:
                Intent intent1 = new Intent(activity, AddDrawActivity.class);
                startActivity(intent1);
                return true;
            case R.id.menu_pictures:
                Intent intent2 = new Intent(activity, AddPictureActivity.class);
                startActivity(intent2);
                return true;
        }
        return false;
    }

    private void getListNoteItems(String searchText) {
        if(flag) return;
        flag = true;

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        emptyLayout.setVisibility(View.INVISIBLE);


        String loginAccount = activity.getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");
        noteItemList.clear();
        //Query data
        reference.child(loginAccount).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                   for(DataSnapshot child : task.getResult().getChildren()) {
                       NoteItem item = new NoteItem();
                       String type = child.child("type").getValue().toString();
                       String id = child.getKey();
                       String title = child.child("title").getValue().toString();
                       String data = child.child("data").getValue().toString();
                       String time = child.child("time").getValue().toString();
                       boolean isCanceled = child.child("canceled").getValue(boolean.class);

                       if(!searchText.isEmpty() && !title.contains(searchText)) continue;

                       if(isCanceled) continue;

                       switch (type.trim()) {
                           case "Picture" :
                               item = new Picture(id,title,data,time);
                               break;
                           case "Draw" :
                               item = new Draw(id,title,data,time);
                               break;
                           default:
                               item = new Note(id,title,data,time);
                               break;
                       }
                       item.setCanceled(isCanceled);
                       System.out.println(item.getId());
                       noteItemList.add(item);
                   }

                   if(noteItemList.size() > 0) {
                       progressBar.setVisibility(View.INVISIBLE);
                       recyclerView.setVisibility(View.VISIBLE);
                       emptyLayout.setVisibility(View.INVISIBLE);
                   }
                   else {
                       progressBar.setVisibility(View.INVISIBLE);
                       recyclerView.setVisibility(View.INVISIBLE);
                       emptyLayout.setVisibility(View.VISIBLE);
                   }
                   noteItemAdapter.setData(noteItemList);
                   recyclerView.setAdapter(noteItemAdapter);
                   flag = false;
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = null;
        switch (noteItemList.get(position).getType()) {
            case "Picture":
                intent = new Intent(activity, EditPictureActivity.class);
                intent.putExtra("PictureEdit",noteItemList.get(position));
                break;
            case "Draw":
                intent = new Intent(activity, EditDrawActivity.class);
                intent.putExtra("DrawEdit",noteItemList.get(position));
                break;
            default:
                intent = new Intent(activity, EditNoteActivity.class);
                intent.putExtra("NoteEdit",noteItemList.get(position));
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(activity);
        deleteDialog.setTitle("Thông báo");
        deleteDialog.setMessage("Bạn có muốn xóa ghi chú này?");

        deleteDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String loginAccount = activity.getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");
                reference.child(loginAccount).child(noteItemList.get(position).getId()).child("canceled").setValue(true);
                Toast.makeText(activity, "Đã xóa ghi chú!", Toast.LENGTH_SHORT).show();
                getListNoteItems(searchView.getQuery().toString().trim());
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