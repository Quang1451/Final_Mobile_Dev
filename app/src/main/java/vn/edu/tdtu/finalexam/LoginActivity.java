package vn.edu.tdtu.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Accounts");
    EditText phoneInput, passwordInput;
    Button loginBtn;
    TextView registerTv,forgotPasswordTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.btnLogin);
        registerTv = findViewById(R.id.createAccount);
        forgotPasswordTv = findViewById(R.id.forgotPassword);

        phoneInput = findViewById(R.id.phone);
        passwordInput = findViewById(R.id.txtPassword);

        CheckLoginBefore();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneInput.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Chưa nhập số điện thoại!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(passwordInput.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Chưa nhập số mật khẩu!", Toast.LENGTH_LONG).show();
                    return;
                }
                CheckLogin();
            }
        });

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgotPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, OTPResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CheckLogin() {
        reference.child(phoneInput.getText().toString().trim()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    if(task.getResult().exists()) {
                        if(passwordInput.getText().toString().equals(task.getResult().child("password").getValue())) {
                            getSharedPreferences("SP", MODE_PRIVATE).edit().putString("LoginBefore", (String) task.getResult().getKey()).commit();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            return;
                        }
                        Toast.makeText(LoginActivity.this, "Mật khẩu không đúng!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Số điện thoại đã tồn tại!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void CheckLoginBefore() {
        String loginAccount = getSharedPreferences("SP", MODE_PRIVATE).getString("LoginBefore", "");
        Log.d("Detail",loginAccount);
        if(loginAccount.isEmpty()) return;

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}