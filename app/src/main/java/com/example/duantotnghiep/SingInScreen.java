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
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingInScreen extends AppCompatActivity {
    private boolean isPasswordVisible = false;
    private EditText edt_passin, edt_mailin;
    private ImageView hidein;
    private Button btn_signin;
    private TextView txtsignup;
    private FirebaseAuth mAuth; // Firebase Authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sing_in_screen);

        // Khởi tạo các view
        hidein = findViewById(R.id.hidein);
        edt_passin = findViewById(R.id.edt_passin);
        edt_mailin = findViewById(R.id.edt_mailin); // Trường nhập email
        btn_signin = findViewById(R.id.btn_signin);
        txtsignup = findViewById(R.id.txtSignup);
        hidein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    edt_passin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    hidein.setImageResource(R.drawable.hide);
                } else {
                    edt_passin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    hidein.setImageResource(R.drawable.hideon);
                }
                isPasswordVisible = !isPasswordVisible;
                edt_passin.setSelection(edt_passin.getText().length());
            }
        });

        // Sự kiện khi nhấn nút đăng nhập
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_mailin.getText().toString();
                String password = edt_passin.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SingInScreen.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Đăng nhập bằng Firebase
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SingInScreen.this, task -> {
                                if (task.isSuccessful()) {
                                    // Đăng nhập thành công, chuyển đến màn hình khác (ví dụ màn hình chính)
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(SingInScreen.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    // startActivity(new Intent(SingInScreen.this, HomeActivity.class));
                                    // finish();
                                } else {
                                    // Đăng nhập thất bại
                                    Toast.makeText(SingInScreen.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        // Chuyển về màn hình đăng ký nếu chưa có tài khoản
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingInScreen.this, SignUpScreen.class));
            }
        });
    }
}
