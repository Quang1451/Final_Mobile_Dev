package vn.edu.tdtu.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class EditNoteActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Notes");
    Note note = null;
    TextView backBtn, editBtn;
    EditText titleInput, contentInput;
    BottomNavigationView toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        backBtn = findViewById(R.id.backBtn);
        editBtn = findViewById(R.id.editBtn);

        titleInput = findViewById(R.id.titleInput);
        contentInput = findViewById(R.id.contentInput);
        toolBar = (BottomNavigationView) findViewById(R.id.tool_bar);

        getData();

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

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEdit();
            }
        });
    }
    private  void getData() {
        note = (Note) getIntent().getSerializableExtra("NoteEdit");
        if(note == null) return;

        titleInput.setText(note.getTitle());
        //Convert to spannable
        contentInput.setText(Html.fromHtml(note.getData()));
    }

    private  void saveEdit() {
        String loginAccount = getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");
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

        note.setTitle(title);
        note.setData(content);

        reference.child(loginAccount).child(note.getId()).setValue(note);
        Toast.makeText(EditNoteActivity.this, "Đã lưu chỉnh sửa!", Toast.LENGTH_SHORT).show();
    }
}