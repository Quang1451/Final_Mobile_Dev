package vn.edu.tdtu.finalexam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Base64;


public class AddPictureActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    private static final int CAMERA_REQUEST = 1500;
    TextView backBtn, saveBtn;
    Button takePictureBtn;
    EditText titleInput;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);

        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);

        titleInput = findViewById(R.id.titleInput);
        imageView = findViewById(R.id.picture);

        takePictureBtn = findViewById(R.id.takePictureBtn);
        checkUseCamera();

        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUseCamera();
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

    private void checkUseCamera() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA};
                requestPermissions(permission, PERMISSION_CODE);
            }
            else {
                openCamera();
            }
        }
        else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Không đc sử dụng camera", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    private void save() {
        String title = titleInput.getText().toString();
        String base64String = "";
        BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView.getDrawable());
        Bitmap bitmap = bitmapDrawable.getBitmap();

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
            Toast.makeText(this, "Chưa nhập có ảnh", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();

//
//            //Decode
//            byte[] b1 = Base64.getDecoder().decode(encodedImage);
//            Bitmap decode = BitmapFactory.decodeByteArray(b1, 0, b1.length);
    }
}