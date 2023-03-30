package vn.edu.tdtu.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText passwordInput, rePasswordInput;
    Button submitBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Accounts");
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        passwordInput = findViewById(R.id.password);
        rePasswordInput = findViewById(R.id.passwordReset);

        submitBtn = findViewById(R.id.btn_resetConfirm);

        progressBar = findViewById(R.id.forgotPassword_progressbarConfirm);

        progressBar.setVisibility(View.INVISIBLE);
        submitBtn.setVisibility(View.VISIBLE);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = passwordInput.getText().toString();
                String rePassword = rePasswordInput.getText().toString();
                String phone = getIntent().getStringExtra("Phone");
                if(password.isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Chưa nhập mật khẩu mới!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(password.length() < 6) {
                    Toast.makeText(ResetPasswordActivity.this, "Mật khẩu tối thiều có 6 ký tự!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(rePassword.isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Chưa nhập xác nhận mật khẩu mới!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!password.equals(rePassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "Mật khẩu không đúng!", Toast.LENGTH_LONG).show();
                    return;
                }

                reference.child(phone).child("password").setValue(password);
                progressBar.setVisibility(View.VISIBLE);
                submitBtn.setVisibility(View.INVISIBLE);
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}