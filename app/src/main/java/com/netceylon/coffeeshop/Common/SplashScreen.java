package com.netceylon.coffeeshop.Common;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.netceylon.coffeeshop.R;

public class SplashScreen extends AppCompatActivity {

    Button login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView imageView = findViewById(R.id.imageView);

        // Ensure the ImageView is wider than the screen with scaleType="centerCrop"
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // Calculate scrolling range (this assumes you know the dimensions of the source image)
        int scrollRange = 500; // Adjust based on the width of your image relative to ImageView

        // Create an ObjectAnimator for the scrollX property of the ImageView
        ObjectAnimator animator = ObjectAnimator.ofInt(imageView, "scrollX", 0, scrollRange);
        animator.setDuration(12000); // 4 seconds for one cycle
        animator.setRepeatMode(ObjectAnimator.REVERSE); // Reverse back after reaching the end
        animator.setRepeatCount(ObjectAnimator.INFINITE); // Infinite repetition
        animator.start();

        login_button = findViewById(R.id.button);

//        login_button.setOnClickListener(v -> {
//            Intent intent = new Intent(SplashScreen.this, Login.class);
//            startActivity(intent);
//            finish();
//        });

        login_button.setOnClickListener(v -> {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();
            }, 100); // Add a 100ms delay
        });
    }
}