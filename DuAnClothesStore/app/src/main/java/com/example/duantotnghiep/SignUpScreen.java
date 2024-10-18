package com.example.duantotnghiep;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpScreen extends AppCompatActivity {

    private boolean isPasswordVisible = false;
    private EditText edt_passup, edt_mailup, edt_nameup;
    private ImageView hideup;
    private TextView txtSignin;
    private Button btnSignup;

    // Firebase Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_screen);

        mAuth = FirebaseAuth.getInstance();

        hideup = findViewById(R.id.hideup);
        edt_passup = findViewById(R.id.edt_passup);
        edt_mailup = findViewById(R.id.edt_mailup);
        edt_nameup = findViewById(R.id.edt_nameup);
        txtSignin = findViewById(R.id.txtSignin);
        btnSignup = findViewById(R.id.btn_signup);

        // Sự kiện ẩn/hiện mật khẩu
        hideup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    edt_passup.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    hideup.setImageResource(R.drawable.hide);
                } else {
                    edt_passup.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    hideup.setImageResource(R.drawable.hideon);
                }
                isPasswordVisible = !isPasswordVisible;
                edt_passup.setSelection(edt_passup.getText().length());
            }
        });


        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpScreen.this, SingInScreen.class));
                finish();
            }
        });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_nameup.getText().toString().trim();
                String email = edt_mailup.getText().toString().trim();
                String password = edt_passup.getText().toString().trim();

                // Kiểm tra tên người dùng
                if (name.isEmpty()) {
                    Toast.makeText(SignUpScreen.this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
                    edt_nameup.requestFocus();  // Đặt con trỏ vào trường dữ liệu bị thiếu
                }
                // Kiểm tra email
                else if (email.isEmpty()) {
                    Toast.makeText(SignUpScreen.this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
                    edt_mailup.requestFocus();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(SignUpScreen.this, "Email phải có kí tự @ , Vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                    edt_mailup.requestFocus();
                }
                // Kiểm tra mật khẩu
                else if (password.isEmpty()) {
                    Toast.makeText(SignUpScreen.this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    edt_passup.requestFocus();
                } else {
                    // Đăng ký người dùng mới với Firebase
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpScreen.this, task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpScreen.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpScreen.this, SingInScreen.class));
                                    finish();
                                } else {
                                    Toast.makeText(SignUpScreen.this, "Đăng kí thất bại =_= " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    // Phương thức kiểm tra email hợp lệ đuôi @
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}
