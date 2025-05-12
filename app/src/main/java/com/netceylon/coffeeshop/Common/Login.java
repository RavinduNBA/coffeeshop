package com.netceylon.coffeeshop.Common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.UserActivity;

public class Login extends AppCompatActivity {

    Button login_button;
    Button forgot_button;
    Button create_button;
    TextInputLayout username, password;
    String usernameT, passwordT;

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
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

//        login_button.setOnClickListener(v -> {
//            Intent intent = new Intent(Login.this, UserDashboard.class);
//            startActivity(intent);
//            finish();
//        });

        login_button.setOnClickListener(v -> {

            if (!validateFields()) {
                return;
            }
            usernameT = username.getEditText().getText().toString().trim();
            passwordT = password.getEditText().getText().toString().trim();

            Query checkUser = FirebaseDatabase.getInstance("https://coffeeshop-d75f8-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users").orderByChild("name").equalTo(usernameT);

            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        username.setError(null);
                        username.setErrorEnabled(false);

                        String passwordFromDB = snapshot.child(usernameT).child("password").getValue(String.class);
                        if (passwordFromDB.equals(passwordT)) {
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, UserActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            password.setError("Invalid Credentials");
                            password.requestFocus();
                        }

                    } else {
                        username.setError("User does not exist");
                        username.requestFocus();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

//            new Handler().postDelayed(() -> {
//                Intent intent = new Intent(Login.this, UserActivity.class);
//                startActivity(intent);
//                finish();
//            }, 100); // Add a 100ms delay


        });

        forgot_button.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        create_button.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }

    private boolean validateFields() {
        String val = username.getEditText().getText().toString().trim();
        String val2 = password.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            username.setError("Field can't be empty");
            return false;
        } else if (val2.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        } else {
            username.setError(null);
            password.setError(null);
            username.setErrorEnabled(false);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void letTheUserLogIn(View view) {
        if (!validateFields()) {
            return;
        }
        usernameT = username.getEditText().getText().toString().trim();
        passwordT = password.getEditText().getText().toString().trim();

        Query checkUser = FirebaseDatabase.getInstance("https://coffeeshop-d75f8-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users").orderByChild("fullName").equalTo(usernameT);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(usernameT).child("password").getValue(String.class);
                    if (passwordFromDB.equals(passwordT)) {
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, UserActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        password.setError("Invalid Credentials");
                        password.requestFocus();
                    }

                } else {
                    username.setError("User does not exist");
                    username.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}