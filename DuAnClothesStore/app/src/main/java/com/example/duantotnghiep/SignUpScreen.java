package com.example.duantotnghiep;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpScreen extends AppCompatActivity {
    private boolean isPasswordVisible = false;
    private EditText edt_passup, edt_mailup, edt_nameup;
    private ImageView hideup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_screen);
        hideup = findViewById(R.id.hideup);
        edt_passup= findViewById(R.id.edt_passup);
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
    }
}