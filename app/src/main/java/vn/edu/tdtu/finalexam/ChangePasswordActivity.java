package vn.edu.tdtu.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Accounts");
    EditText oldPasswordInput, newPasswordInput, rePasswordInput;
    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPasswordInput = findViewById(R.id.oldPassword);
        newPasswordInput = findViewById(R.id.newPassowrd);
        rePasswordInput = findViewById(R.id.newPassowrdConfirm);

        submitBtn = findViewById(R.id.btn_changeConfirm);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = oldPasswordInput.getText().toString();
                String newPassword = newPasswordInput.getText().toString();
                String rePassword = rePasswordInput.getText().toString();
                String phoneNumber = getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");

                if(oldPassword.isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, "Chưa nhập mật khẩu cũ!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(newPassword.isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, "Chưa nhập mật khẩu mới!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(rePassword.isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, "Chưa nhập xác nhận mật khẩu mới!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(newPassword.length() < 6) {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!newPassword.equals(rePassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu xác nhận không đúng!", Toast.LENGTH_LONG).show();
                    return;
                }

                reference.child(phoneNumber).child("password").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            String password = oldPasswordInput.getText().toString();
                            String newPassword = newPasswordInput.getText().toString();
                            String phoneNumber = getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");
                            if(password.equals(task.getResult().getValue(String.class))) {
                                reference.child(phoneNumber).child("password").setValue(newPassword);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(ChangePasswordActivity.this, "Mật khẩu cũ không đúng!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });
    }
}