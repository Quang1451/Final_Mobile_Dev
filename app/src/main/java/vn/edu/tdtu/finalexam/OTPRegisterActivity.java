package vn.edu.tdtu.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

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
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OTPRegisterActivity extends AppCompatActivity {
    EditText otpInput;
    Button verifyBtn;
    TextView backBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Accounts");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_register);

        otpInput = findViewById(R.id.otpVerify);
        verifyBtn = findViewById(R.id.verify);
        backBtn = findViewById(R.id.skip);

        account = (Account) getIntent().getSerializableExtra("AccountInfo");

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = otpInput.getText().toString();
                if(otp.isEmpty()) {
                    Toast.makeText(OTPRegisterActivity.this, "Chưa nhập OTP!", Toast.LENGTH_LONG).show();
                    return;
                }
                //Get code
                String verifyCode = getIntent().getStringExtra("VerifyCode");

                if(verifyCode.isEmpty()) {
                    Toast.makeText(OTPRegisterActivity.this, "Không có mã !", Toast.LENGTH_LONG).show();
                    return;
                }
                signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(verifyCode, otp));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            reference.child(account.getPhoneNumber().trim()).setValue(account);
                            getSharedPreferences("SP", MODE_PRIVATE).edit().putString("LoginBefore", account.getPhoneNumber().trim()).commit();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(OTPRegisterActivity.this, "OTP không hợp lệ!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}