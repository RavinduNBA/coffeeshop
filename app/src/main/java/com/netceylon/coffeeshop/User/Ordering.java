package com.netceylon.coffeeshop.User;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.netceylon.coffeeshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

public class Ordering extends AppCompatActivity {

    private TextInputEditText nameInput, phoneInput, address1Input, address2Input, locationInput;
    private TextInputLayout locationLayout;
    private Button placeOrderButton;
    private ImageButton backButton;
    private static final String ORDER_FILE_NAME = "user_orders.json";
    private static final String PREFS_NAME = "OrderingPrefs";
    private static final String KEY_NAME = "saved_name";
    private static final String KEY_PHONE = "saved_phone";
    private static final String KEY_ADDRESS1 = "saved_address1";
    private static final String KEY_ADDRESS2 = "saved_address2";
    private static final String KEY_LOCATION = "saved_location";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private LocationManager locationManager;
    private LocationListener locationListener;

    // Activity Result Launcher for location permission
    private final ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean fineLocationGranted = result.getOrDefault(
                        Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseLocationGranted = result.getOrDefault(
                        Manifest.permission.ACCESS_COARSE_LOCATION, false);

                if (fineLocationGranted != null && fineLocationGranted ||
                        coarseLocationGranted != null && coarseLocationGranted) {
                    // Location permissions granted
                    getUserLocation();
                } else {
                    // No location permissions granted
                    Toast.makeText(Ordering.this, "Location permission denied. Cannot get location.",
                            Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ordering);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize location services
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Initialize location listener
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // Call method to get address from location
                getAddressFromLocation(location);

                // Remove location updates to save battery
                locationManager.removeUpdates(locationListener);
            }
        };

        // Initialize UI components
        initUI();

        // Load saved user data
        loadSavedUserData();

        // Set up button click listeners
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrderData();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Set up location field click listener
        if (locationLayout != null) {
            locationLayout.setEndIconOnClickListener(v -> {
                checkLocationPermissionsAndGetLocation();
            });
        }

        // Check location permissions at start to automatically get location
        checkLocationPermissionsAndGetLocation();
    }

    private void initUI() {
        nameInput = findViewById(R.id.name);
        phoneInput = findViewById(R.id.email);
        address1Input = findViewById(R.id.password);
        address2Input = findViewById(R.id.passwordconfirm);
        locationInput = findViewById(R.id.location);
        locationLayout = findViewById(R.id.locationLayout);
        placeOrderButton = findViewById(R.id.buttonregister);
        backButton = findViewById(R.id.btn_back);
    }

    private void loadSavedUserData() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Retrieve saved user data
        String savedName = prefs.getString(KEY_NAME, "");
        String savedPhone = prefs.getString(KEY_PHONE, "");
        String savedAddress1 = prefs.getString(KEY_ADDRESS1, "");
        String savedAddress2 = prefs.getString(KEY_ADDRESS2, "");
        String savedLocation = prefs.getString(KEY_LOCATION, "");

        // Set the fields with saved data
        nameInput.setText(savedName);
        phoneInput.setText(savedPhone);
        address1Input.setText(savedAddress1);
        address2Input.setText(savedAddress2);
        locationInput.setText(savedLocation);
    }

    private void saveUserData() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Save current user data
        editor.putString(KEY_NAME, nameInput.getText().toString().trim());
        editor.putString(KEY_PHONE, phoneInput.getText().toString().trim());
        editor.putString(KEY_ADDRESS1, address1Input.getText().toString().trim());
        editor.putString(KEY_ADDRESS2, address2Input.getText().toString().trim());
        editor.putString(KEY_LOCATION, locationInput.getText().toString().trim());

        editor.apply();
    }

    private void saveOrderData() {
        String name = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String address1 = address1Input.getText().toString().trim();
        String address2 = address2Input.getText().toString().trim();
        String location = locationInput.getText().toString().trim();

        // Basic validation
        if (name.isEmpty() || phone.isEmpty() || address1.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Create order JSON object
            JSONObject orderData = new JSONObject();
            orderData.put("name", name);
            orderData.put("phone", phone);
            orderData.put("address1", address1);
            orderData.put("address2", address2);
            orderData.put("location", location);
            orderData.put("timestamp", System.currentTimeMillis());

            // Read existing orders or create new array
            JSONArray ordersArray = getExistingOrders();
            ordersArray.put(orderData);

            // Save to internal storage
            saveOrdersToFile(ordersArray.toString());

            // Save user data to SharedPreferences to persist through app restarts
            saveUserData();

            // Show success message
            Toast.makeText(this, "Order details saved successfully!", Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            Toast.makeText(this, "Error saving order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private JSONArray getExistingOrders() {
        File file = new File(getFilesDir(), ORDER_FILE_NAME);
        if (file.exists()) {
            try {
                FileInputStream fis = openFileInput(ORDER_FILE_NAME);
                InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
                StringBuilder stringBuilder = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                    String line = reader.readLine();
                    while (line != null) {
                        stringBuilder.append(line).append('\n');
                        line = reader.readLine();
                    }
                }

                String jsonString = stringBuilder.toString();
                return new JSONArray(jsonString);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return new JSONArray();
            }
        }
        return new JSONArray();
    }

    private void saveOrdersToFile(String jsonString) {
        try (FileOutputStream fos = openFileOutput(ORDER_FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(jsonString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error writing to storage", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to check permissions and get location
    private void checkLocationPermissionsAndGetLocation() {
        // First check if location is enabled
        if (!isLocationEnabled()) {
            showLocationServicesDialog();
            return;
        }

        // Then check for permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            // Request permissions
            locationPermissionRequest.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        } else {
            // Permissions already granted, get location
            getUserLocation();
        }
    }

    // Check if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null &&
                (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    // Get user's current location
    private void getUserLocation() {
        if (!isLocationEnabled()) {
            showLocationServicesDialog();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Show loading message
        Toast.makeText(this, "Getting location...", Toast.LENGTH_SHORT).show();

        // Try to get last known location first
        Location lastKnownLocation = null;

        // Try GPS provider first for more accuracy
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        // If GPS didn't work, try network provider
        if (lastKnownLocation == null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (lastKnownLocation != null) {
            // We have a last known location, use it
            getAddressFromLocation(lastKnownLocation);
        } else {
            // No last known location, request location updates

            // Request updates from both providers
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    // Get address from location coordinates
    private void getAddressFromLocation(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(
                    location.getLatitude(), location.getLongitude(), 1);

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                StringBuilder sb = new StringBuilder();

                // Format address string
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i));
                    if (i < address.getMaxAddressLineIndex()) {
                        sb.append(", ");
                    }
                }

                String addressText = sb.toString();
                locationInput.setText(addressText);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error getting address. Please try again.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Show dialog when location services are disabled
    private void showLocationServicesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Location Services Disabled");
        builder.setMessage("Please enable location services to get your current location.");

        // Make sure button text is clearly defined
        builder.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // Set explicit text for negative button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Ordering.this, "Location service required for automatic address detection", Toast.LENGTH_LONG).show();
            }
        });

        // Create dialog and show it
        AlertDialog dialog = builder.create();
        dialog.show();


        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

        if (positiveButton != null) {
            positiveButton.setAllCaps(false);

            positiveButton.setTextColor(getResources().getColor(android.R.color.darker_gray));
        }

        if (negativeButton != null) {
            negativeButton.setAllCaps(false);
            negativeButton.setTextColor(getResources().getColor(android.R.color.darker_gray));
        }
    }

    // Check if network connection is available
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remove location updates when activity is paused to save battery
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
