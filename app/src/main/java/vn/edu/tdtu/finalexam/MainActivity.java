package vn.edu.tdtu.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    RecycleBinFragment recycleBinFragment = new RecycleBinFragment();
    AlarmFragment alarmFragment = new AlarmFragment();
    OptionFragment optionFragment = new OptionFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.menu_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.menu_recycleBin:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, recycleBinFragment).commit();
                        return true;
                    case R.id.menu_alarm:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, alarmFragment).commit();
                        return true;
                    case R.id.menu_option:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, optionFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}