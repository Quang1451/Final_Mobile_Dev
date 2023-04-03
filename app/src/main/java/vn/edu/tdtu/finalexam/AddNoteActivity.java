package vn.edu.tdtu.finalexam;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class AddNoteActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Notes");
    TextView backBtn, saveBtn;
    EditText titleInput, contentInput;
    BottomNavigationView toolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);

        titleInput = findViewById(R.id.titleInput);
        contentInput = findViewById(R.id.contentInput);
        toolBar = (BottomNavigationView) findViewById(R.id.tool_bar);

        toolBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Spannable spannable = new SpannableStringBuilder(contentInput.getText());
                switch (item.getItemId()) {
                    case R.id.tool_bold:
                        spannable.setSpan(new StyleSpan(Typeface.BOLD),
                                contentInput.getSelectionStart(),
                                contentInput.getSelectionEnd(),
                                0);

                        break;
                    case R.id.tool_italic:
                        spannable.setSpan(new StyleSpan(Typeface.ITALIC),
                                contentInput.getSelectionStart(),
                                contentInput.getSelectionEnd(),
                                0);
                        break;
                    case R.id.tool_underline:
                        spannable.setSpan(new UnderlineSpan(),
                                contentInput.getSelectionStart(),
                                contentInput.getSelectionEnd(),
                                0);
                        break;

                    case R.id.tool_reset_all:
                        contentInput.setText(contentInput.getText().toString());
                        return false;
                }
                contentInput.setText(spannable);
                return false;
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }
    private void save() {
        String loginAccount = getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");
        int id = new Random().nextInt(10000);
        String strId = String.format("%05d", id);
        String title = titleInput.getText().toString();
        String content = Html.toHtml(new SpannableStringBuilder(contentInput.getText()));
        if(title.isEmpty()) {
            Toast.makeText(this, "Chưa nhập tiêu đề", Toast.LENGTH_SHORT).show();
            return;
        }

        if(content.isEmpty()) {
            Toast.makeText(this, "Chưa nhập nội dung", Toast.LENGTH_SHORT).show();
            return;
        }

        NoteItem noteItem = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate localDate = LocalDate.now();
            String date =  DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localDate);
            noteItem = new Note(strId, title, content, date);
        }

        if(noteItem == null) return;

        reference.child(loginAccount).child(strId).setValue(noteItem);
        Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
        finish();
    }
}