package vn.edu.tdtu.finalexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    Activity activity;
    FloatingActionButton addNoteBtn;
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
            //case R.id.menu_categories:
            //    return true;
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
}