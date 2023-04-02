package vn.edu.tdtu.finalexam;

import static vn.edu.tdtu.finalexam.DrawView.colorList;
import static vn.edu.tdtu.finalexam.DrawView.current_brush;
import static vn.edu.tdtu.finalexam.DrawView.pathList;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;

public class AddDrawActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Notes");
    TextView backBtn, saveBtn;
    DrawView drawView;
    EditText titleInput;
    Button eraserBtn, redBtn, yellowBtn, greenBtn, blackBtn;
    public static Path path = new Path();
    public static Paint paint_brush = new Paint();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_draw);

        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);
        titleInput = findViewById(R.id.titleInput);

        drawView = findViewById(R.id.drawView);

        eraserBtn = findViewById(R.id.eraser);
        redBtn = findViewById(R.id.redColor);
        yellowBtn = findViewById(R.id.yellowColor);
        greenBtn = findViewById(R.id.greenColor);
        blackBtn = findViewById(R.id.blackColor);

        clearDraw();
        eraserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint_brush.setColor(Color.WHITE);
                currentColor(paint_brush.getColor());
            }
        });

        redBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint_brush.setColor(Color.RED);
                currentColor(paint_brush.getColor());
            }
        });

        yellowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint_brush.setColor(Color.YELLOW);
                currentColor(paint_brush.getColor());
            }
        });

        greenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint_brush.setColor(Color.GREEN);
                currentColor(paint_brush.getColor());
            }
        });

        blackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    public void clearDraw() {
        pathList.clear();
        colorList.clear();
        path.reset();
    }

    public void currentColor(int c) {
        current_brush = c;
        path = new Path();
    }

    private void save() {
        String loginAccount = getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");
        int id = new Random().nextInt(10000);
        String title = titleInput.getText().toString();
        String base64String = "";

        drawView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(drawView.getDrawingCache());

        //Convert to base64
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            base64String = Base64.getEncoder().encodeToString(bytes);
            System.out.println(base64String);
        }

        if(title.isEmpty()) {
            Toast.makeText(this, "Chưa nhập tiêu đề", Toast.LENGTH_SHORT).show();
            return;
        }

        if(base64String.isEmpty()) {
            Toast.makeText(this, "Chưa nhập có hình vẽ", Toast.LENGTH_SHORT).show();
            return;
        }

        NoteItem noteItem = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate localDate = LocalDate.now();
            String date =  DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localDate);
            noteItem = new Draw(title, base64String, date);
        }

        if(noteItem == null) return;

        reference.child(loginAccount).child(String.format("%05d", id)).setValue(noteItem);
        Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
        finish();
        //        drawView2.setBitmap(bitmap);
//        drawView2.invalidate();
    }
}