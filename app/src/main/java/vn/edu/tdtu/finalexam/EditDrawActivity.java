package vn.edu.tdtu.finalexam;

import static vn.edu.tdtu.finalexam.DrawView.colorList;
import static vn.edu.tdtu.finalexam.DrawView.current_brush;
import static vn.edu.tdtu.finalexam.DrawView.pathList;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Base64;

public class EditDrawActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Notes");
    Draw draw = null;
    TextView backBtn, editBtn;
    DrawView drawView;
    EditText titleInput;
    Button eraserBtn, redBtn, yellowBtn, greenBtn, blackBtn;
    public static Path path = new Path();
    public static Paint paint_brush = new Paint();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_draw);

        backBtn = findViewById(R.id.backBtn);
        editBtn = findViewById(R.id.editBtn);
        titleInput = findViewById(R.id.titleInput);

        drawView = findViewById(R.id.drawView);

        eraserBtn = findViewById(R.id.eraser);
        redBtn = findViewById(R.id.redColor);
        yellowBtn = findViewById(R.id.yellowColor);
        greenBtn = findViewById(R.id.greenColor);
        blackBtn = findViewById(R.id.blackColor);

        getData();

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
                paint_brush.setColor(Color.BLACK);
                currentColor(paint_brush.getColor());
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
        clearDraw();
        draw = (Draw) getIntent().getSerializableExtra("DrawEdit");

        if(draw == null) return;

        titleInput.setText(draw.getTitle());
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            byte[] bytes = Base64.getDecoder().decode(draw.getData());
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//            drawView.setBitmap(bitmap);
//            drawView.invalidate();
//        }

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

    private  void saveEdit() {

    }
}