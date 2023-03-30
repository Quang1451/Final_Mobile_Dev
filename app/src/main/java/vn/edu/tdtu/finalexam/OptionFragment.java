package vn.edu.tdtu.finalexam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;

public class OptionFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    Activity activity;
    NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_option, container, false);
        return  view;
    }

    @Override
    public void onStart(){
        super.onStart();
        navigationView = activity.findViewById(R.id.navigationOption);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.changePassword:
                startActivity( new Intent(activity,ChangePasswordActivity.class));
                return true;
            case R.id.sync:
                return true;
            case R.id.theme:
                return true;
            case R.id.logout:
                activity.getSharedPreferences("SP", Context.MODE_PRIVATE).edit().putString("LoginBefore", "").commit();
                startActivity(new Intent(activity,LoginActivity.class));
                return true;
        }
        return false;
    }
}