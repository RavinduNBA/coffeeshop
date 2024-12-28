package com.netceylon.coffeeshop.Common;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.netceylon.coffeeshop.R;

public class Login extends AppCompatActivity {

    Button login_button;
    Button forgot_button;
    Button create_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        login_button = findViewById(R.id.buttonregister);
        forgot_button = findViewById(R.id.buttonforgot);
        create_button = findViewById(R.id.buttoncreateaccount);

        login_button.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SplashScreen.class);
            startActivity(intent);
            finish();
        });

        forgot_button.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            finish();
        });

        create_button.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            finish();
        });
    }
}