package vn.edu.tdtu.finalexam;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class RecycleBinFragment extends Fragment implements SelectRecycleViewInterface {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Notes");
    List<NoteItem> noteItemList = new ArrayList<>();
    Activity activity;

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
        return inflater.inflate(R.layout.fragment_recycle_bin, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        searchView = activity.findViewById(R.id.searchMenuBin);
        progressBar = activity.findViewById(R.id.progressBarBin);
        recyclerView = activity.findViewById(R.id.reViewBin);
        emptyLayout = activity.findViewById(R.id.emptyListBin);

        noteItemAdapter = new NoteItemAdapter(activity, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        getListNoteItems("");

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

                        if(!isCanceled) continue;

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
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(activity);
        deleteDialog.setTitle("Khôi phục ghi chú");
        deleteDialog.setMessage("Bạn có muốn khôi phục ghi chú "+ noteItemList.get(position).getTitle()+" hay không?");

        deleteDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String loginAccount = activity.getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");
                reference.child(loginAccount).child(noteItemList.get(position).getId()).child("canceled").setValue(false);
                Toast.makeText(activity, "Đã khôi phục ghi chú!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDeleteClick(int position) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(activity);
        deleteDialog.setTitle("Xóa ghi chú");
        deleteDialog.setMessage("Bạn có muốn xóa ghi chú "+ noteItemList.get(position).getTitle()+" hay không?");

        deleteDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String loginAccount = activity.getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");
                reference.child(loginAccount).child(noteItemList.get(position).getId()).removeValue();
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