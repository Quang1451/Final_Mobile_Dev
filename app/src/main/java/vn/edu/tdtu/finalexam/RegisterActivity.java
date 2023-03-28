package vn.edu.tdtu.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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


public class RegisterActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Accounts");
    EditText nameInput, phoneInput, passwordInput, rePasswordInput;
    Button registerBtn;
    Account newAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameInput = findViewById(R.id.username);
        phoneInput = findViewById(R.id.phoneNumber);
        passwordInput = findViewById(R.id.password);
        rePasswordInput = findViewById(R.id.confirmPassword);

        registerBtn = findViewById(R.id.btn_register);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, phone, password, confirmPassword;
                name = String.valueOf(nameInput.getText().toString());
                phone = String.valueOf(phoneInput.getText().toString());
                password = String.valueOf(passwordInput.getText().toString());
                confirmPassword = String.valueOf(rePasswordInput.getText().toString());
                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(RegisterActivity.this, "Chưa nhập Họ tên!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(phone) || phone.length() != 10) {
                    Toast.makeText(RegisterActivity.this, "Số điện thoại không hợp lệ!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password) || password.length() < 6 || !password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không hợp lệ!", Toast.LENGTH_LONG).show();
                    return;
                }

                newAccount = new Account(name, phone, password);

                reference.child(phone).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            if(task.getResult().exists()) {
                                Toast.makeText(RegisterActivity.this, "Số điện thoại đã tồn tại!", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                //Send value account
                                Intent intent = new Intent(RegisterActivity.this, OTPRegisterActivity.class);
                                intent.putExtra("AccountInfo", newAccount);
                                startActivity(intent);
                            }
                        }
                    }
                });
            }
        });
    }
}