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
                finish();  // Dừng màn hình hiện tại để không quay lại màn hình SignUpScreen khi nhấn nút Back
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_mailup.getText().toString();
                String password = edt_passup.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpScreen.this, "Vui lòng nhập user & pass", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(SignUpScreen.this, "Email phải có kí tự @ , Vui lòng nhập lại !", Toast.LENGTH_SHORT).show();
                } else {
                    // Đăng ký người dùng mới với Firebase
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpScreen.this, task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpScreen.this, "Registration successful !", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpScreen.this, SingInScreen.class)); // Chuyển sang màn hình Sign In
                                    finish();
                                } else {

                                    Toast.makeText(SignUpScreen.this, "Registration failed =_= " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
    //
}
