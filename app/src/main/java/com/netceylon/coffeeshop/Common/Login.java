package com.netceylon.coffeeshop.Common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.OrderCoffee;
import com.netceylon.coffeeshop.User.UserActivity;
import com.netceylon.coffeeshop.User.UserDashboard;

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

//        login_button.setOnClickListener(v -> {
//            Intent intent = new Intent(Login.this, UserDashboard.class);
//            startActivity(intent);
//            finish();
//        });

        login_button.setOnClickListener(v -> {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(Login.this, UserActivity.class);
                startActivity(intent);
                finish();
            }, 100); // Add a 100ms delay
        });

        forgot_button.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, OrderCoffee.class);
            startActivity(intent);
        });

        create_button.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }
}