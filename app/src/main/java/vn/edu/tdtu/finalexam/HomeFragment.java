package vn.edu.tdtu.finalexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    Activity activity;
    FloatingActionButton addNoteBtn;

    private RecyclerView rcvCategory;

    //private CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        addNoteBtn = activity.findViewById(R.id.addNoteBtn);

        rcvCategory = activity.findViewById(R.id.reView);
//        categoryAdapter = new CategoryAdapter(this);
//
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);
//
//        categoryAdapter.setData(getListCategory());

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
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
            case R.id.menu_schedules:
                return true;
            case R.id.menu_tasks:
                return true;
        }
        return false;
    }

//    private List<Category> getListCategory(){
//        List<Category> categoryList = new ArrayList<>();
//
//        /* Khởi tạo giá trị cho view */
//
//        List<NotesCardView> notesCardViewList = new ArrayList<>();
//        notesCardViewList.add(new NotesCardView(R.drawable.home_icon, "Ghi chu 1"));
//        notesCardViewList.add(new NotesCardView(R.drawable.home_icon, "Ghi chu 2"));
//        notesCardViewList.add(new NotesCardView(R.drawable.home_icon, "Ghi chu 3"));
//        notesCardViewList.add(new NotesCardView(R.drawable.home_icon, "Ghi chu 4"));
//        notesCardViewList.add(new NotesCardView(R.drawable.home_icon, "Ghi chu 5"));
//
//        categoryList.add(new Category( "Ghi chu",notesCardViewList));
//        categoryList.add(new Category( "Hinh",notesCardViewList));
//        categoryList.add(new Category( "Ve",notesCardViewList));
//        return categoryList;
//    }
}