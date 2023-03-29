package vn.edu.tdtu.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OTPResetPasswordActivity extends AppCompatActivity {
    EditText phoneInput, otpInput;
    Button resetOTP, submitBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Accounts");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String verifyCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_resetpassword);

        phoneInput = findViewById(R.id.phoneReset);
        otpInput = findViewById(R.id.OTPStringReset);

        resetOTP = findViewById(R.id.btnOTPReset);
        submitBtn = findViewById(R.id.btn_reset);

        resetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phoneInput.getText().toString();
                if(phoneNumber.isEmpty()) {
                    Toast.makeText(OTPResetPasswordActivity.this, "Chưa nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                    return;
                }

                reference.child(phoneNumber.trim()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            if(task.getResult().exists()) {
                                //Send OTP
                                sendOtp(String.valueOf(task.getResult().getKey()));
                            }
                            else
                            {
                                Toast.makeText(OTPResetPasswordActivity.this, "Số điện thoại đã tồn tại!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phoneInput.getText().toString();
                String otp = otpInput.getText().toString();

                if(phoneNumber.isEmpty()) {
                    Toast.makeText(OTPResetPasswordActivity.this, "Chưa nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(otp.isEmpty()) {
                    Toast.makeText(OTPResetPasswordActivity.this, "Chưa nhập OTP!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(verifyCode == null) {
                    Toast.makeText(OTPResetPasswordActivity.this, "Chưa có mã OTP!", Toast.LENGTH_SHORT).show();
                    return;
                }

                reference.child(phoneNumber.trim()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            if(task.getResult().exists()) {
                                signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(verifyCode, otp));
                            }
                            else
                            {
                                Toast.makeText(OTPResetPasswordActivity.this, "Số điện thoại đã tồn tại!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });
    }

    private void sendOtp(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(myCallback)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks myCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {}

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTPResetPasswordActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            verifyCode = verificationId;
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(OTPResetPasswordActivity.this, ResetPasswordActivity.class);
                            intent.putExtra("Phone", phoneInput.getText().toString().trim());
                            startActivityForResult(intent, 1);
                        } else {
                            Toast.makeText(OTPResetPasswordActivity.this, "OTP không hợp lệ!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }
}