package com.netceylon.coffeeshop.User.MainFragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.netceylon.coffeeshop.Databases.SessionManager;
import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.UserActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class ProfileFragment extends Fragment {

    Button backtoshopping;
    TextInputEditText name, email, password;
    private Uri imageUri;
    ImageView imageView;
    Button button;
    private File photoFile;

    private static final int CAMERA_REQUEST_CODE = 100;

    private ActivityResultLauncher<String> pickImageLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize gallery picker
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imageUri = uri;
                        try {
                            // Create internal file
                            File destinationFile = new File(requireContext().getFilesDir(), "profile_image.jpg");

                            // Copy content to internal file
                            InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
                            OutputStream outputStream = new FileOutputStream(destinationFile);

                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = inputStream.read(buffer)) > 0) {
                                outputStream.write(buffer, 0, length);
                            }

                            inputStream.close();
                            outputStream.close();

                            // Load to ImageView
                            imageView.setImageURI(Uri.fromFile(destinationFile));

                            // Save internal file path
                            SharedPreferences prefs = requireActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
                            prefs.edit().putString("profile_image_path", destinationFile.getAbsolutePath()).apply();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        imageView = view.findViewById(R.id.profilePicture);
        button = view.findViewById(R.id.addImage);

        SessionManager sessionManager = new SessionManager(getActivity(), SessionManager.SESSION_USERSESSION);
        HashMap<String, String> userData = sessionManager.getUserDetailFromSession();
        String usernameT = userData.get("username");
        String passwordT = userData.get("password");
        String emailT = userData.get("email");

        name.setText(usernameT);
        email.setText(emailT);
        password.setText(passwordT);

        //request for camera runtime permission
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA}, 100);
        }



        SharedPreferences prefs = requireActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        String savedUri = prefs.getString("profile_image_path", null);
        if (savedUri != null) {
            imageView.setImageURI(Uri.parse(savedUri));
        }
        button.setOnClickListener(v -> {
            String[] options = {"Take Photo", "Choose from Gallery"};
            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Set Profile Picture")
                    .setItems(options, (dialog, which) -> {
                        if (which == 0) {
                            // Camera
                            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(requireActivity(),
                                        new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                            } else {
                                openCamera();
                            }
                        } else if (which == 1) {
                            // Gallery
                            pickImageLauncher.launch("image/*");
                        }
                    })
                    .show();
        });




        // Set up button and its click listener
        backtoshopping = view.findViewById(R.id.backToShopping);

        backtoshopping.setOnClickListener(v -> {
            if (getActivity() instanceof UserActivity) {
                UserActivity activity = (UserActivity) getActivity();
                activity.navigateToFragment(new HomeFragment(), R.id.homeFragment, true);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (imageUri != null) {
                imageView.setImageURI(imageUri);


                // Save URI path to SharedPreferences for persistence
                SharedPreferences prefs = requireActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
                prefs.edit().putString("profile_image_path", imageUri.toString()).apply();
            }
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
                if (photoFile != null) {
                    imageUri = FileProvider.getUriForFile(requireContext(),
                            requireContext().getPackageName() + ".fileprovider", photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "profile_picture_" + timeStamp;
        File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(storageDir, fileName + ".jpg");
    }
}