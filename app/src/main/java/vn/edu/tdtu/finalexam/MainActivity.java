package vn.edu.tdtu.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import Category.Category;
import Category.CategoryAdapter;

//public class MainActivity extends AppCompatActivity {
//    BottomNavigationView bottomNavigationView;
//
//    HomeFragment homeFragment = new HomeFragment();
//    RecycleBinFragment recycleBinFragment = new RecycleBinFragment();
//    AlarmFragment alarmFragment = new AlarmFragment();
//    OptionFragment optionFragment = new OptionFragment();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar);
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
//
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                switch (id) {
//                    case R.id.menu_home:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
//                        return true;
//                    case R.id.menu_recycleBin:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, recycleBinFragment).commit();
//                        return true;
//                    case R.id.menu_alarm:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, alarmFragment).commit();
//                        return true;
//                    case R.id.menu_option:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, optionFragment).commit();
//                        return true;
//                }
//                return false;
//            }
//        });
//    }
//}

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvCategory;
    private CategoryAdapter categoryAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvCategory = findViewById(R.id.rcv_category);
        categoryAdapter = new CategoryAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);

        categoryAdapter.setData(getListCategory());

    }

    private List<Category> getListCategory(){
        List<Category> categoryList = new ArrayList<>();

        /* Khởi tạo giá trị cho view */

        List<NotesCardView> notesCardViewList = new ArrayList<>();
        notesCardViewList.add(new NotesCardView(R.drawable.home_icon, "Ghi chu 1"));
        notesCardViewList.add(new NotesCardView(R.drawable.home_icon, "Ghi chu 2"));
        notesCardViewList.add(new NotesCardView(R.drawable.home_icon, "Ghi chu 3"));
        notesCardViewList.add(new NotesCardView(R.drawable.home_icon, "Ghi chu 4"));
        notesCardViewList.add(new NotesCardView(R.drawable.home_icon, "Ghi chu 5"));

        categoryList.add(new Category( "Ghi chu",notesCardViewList));
        categoryList.add(new Category( "Hinh",notesCardViewList));
        categoryList.add(new Category( "Ve",notesCardViewList));
        return categoryList;
    }
}