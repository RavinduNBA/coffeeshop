package com.netceylon.coffeeshop.Common;

import static com.netceylon.coffeeshop.Databases.SessionManager.SESSION_REMEBERME;
import static com.netceylon.coffeeshop.Databases.SessionManager.SESSION_USERSESSION;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.netceylon.coffeeshop.Databases.SessionManager;
import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.UserActivity;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    Button login_button;
    Button forgot_button;
    Button create_button;
    TextInputLayout username, password;
    TextInputEditText usernameEdit, passwordEdit;
    String usernameT, passwordT, emailT;
    RelativeLayout progressBar;
    CheckBox rememberMe;

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
        progressBar = findViewById(R.id.loading_layout);
        progressBar.setVisibility(View.GONE);
        rememberMe = findViewById(R.id.rememberMe);
        usernameEdit = findViewById(R.id.usernameEdit);
        passwordEdit = findViewById(R.id.passwordEdit);


        SessionManager sessionManager = new SessionManager(Login.this, SESSION_REMEBERME);
        if (sessionManager.checkRememberMe()) {
            HashMap<String, String> userData = sessionManager.getRememberMeDetailsFromSession();
            usernameEdit.setText(userData.get(SessionManager.KEY_SESSIONUSERNAME));
            passwordEdit.setText(userData.get(SessionManager.KEY_SESSIONPASSWORD));
            rememberMe.setChecked(true);
        }


        login_button.setOnClickListener(v -> {

            if (!isConnected(Login.this)) {
                Toast.makeText(Login.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(this::showCustomDialog, 1000);
                return;
            }


            if (!validateFields()) {
                return;
            }


            progressBar.setVisibility(View.VISIBLE);

            usernameT = username.getEditText().getText().toString().trim();
            passwordT = password.getEditText().getText().toString().trim();

            if (rememberMe.isChecked()) {
                //SessionManager sessionManager2 = new SessionManager(Login.this, SESSION_REMEBERME);
                sessionManager.createRememberMeSession(usernameT, passwordT);
            }


            Query checkUser = FirebaseDatabase.getInstance("https://coffeeshop-d75f8-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users").orderByChild("name").equalTo(usernameT);

            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        username.setError(null);
                        username.setErrorEnabled(false);

                        String passwordFromDB = snapshot.child(usernameT).child("password").getValue(String.class);
                        emailT = snapshot.child(usernameT).child("email").getValue(String.class);

                        if (passwordFromDB.equals(passwordT)) {

                            SessionManager sessionManager = new SessionManager(Login.this, SESSION_USERSESSION);
                            sessionManager.createLoginSession(usernameT, passwordT, emailT);

                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, UserActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            password.setError("Invalid Credentials");
                            password.requestFocus();
                        }

                    } else {
                        progressBar.setVisibility(View.GONE);
                        username.setError("User does not exist");
                        username.requestFocus();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


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

    private boolean isConnected(Login login) {

        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo MobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifiConn != null && wifiConn.isConnected()) || (MobileConn != null && MobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("No Internet Connection").setCancelable(false).setPositiveButton("Retry", (dialog, id) -> {
            startActivity(new Intent(Login.this, Login.class));
        });
    }
}