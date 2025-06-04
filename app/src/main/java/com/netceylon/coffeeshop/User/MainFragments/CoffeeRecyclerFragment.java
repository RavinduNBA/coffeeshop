package com.netceylon.coffeeshop.User.MainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netceylon.coffeeshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CoffeeRecyclerFragment extends Fragment {

    private ImageView coffeeImage;
    private TextView coffeeName, ingredients, descriptionText;

    private final String apiUrl = "https://api.sampleapis.com/coffee/iced";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coffee_recycler, container, false);

        coffeeImage = view.findViewById(R.id.coffeeImage1);
        coffeeName = view.findViewById(R.id.coffeeName1);
        ingredients = view.findViewById(R.id.ingredients1);
        descriptionText = view.findViewById(R.id.descriptionText1);

        fetchCoffeeData();

        return view;
    }

    private void fetchCoffeeData() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                if (response.body() == null) return;

                String jsonData = response.body().string();

                try {
                    JSONArray jsonArray = new JSONArray(jsonData);

                    if (jsonArray.length() == 0) return;

                    int randomIndex = new Random().nextInt(jsonArray.length());
                    JSONObject coffee = jsonArray.getJSONObject(randomIndex);

                    String title = coffee.getString("title");
                    String description = coffee.getString("description");
                    String imageUrl = coffee.getString("image");
                    JSONArray ingredientsArray = coffee.getJSONArray("ingredients");

                    StringBuilder ingredientsText = new StringBuilder();
                    for (int i = 0; i < ingredientsArray.length(); i++) {
                        ingredientsText.append(ingredientsArray.getString(i));
                        if (i < ingredientsArray.length() - 1) ingredientsText.append(", ");
                    }

                    requireActivity().runOnUiThread(() -> {
                        coffeeName.setText(title);
                        descriptionText.setText(description);
                        ingredients.setText("Ingredients: " + ingredientsText.toString());

                        Glide.with(requireContext())
                                .load(imageUrl)
                                .placeholder(R.drawable.coffeeboba_1)
                                .into(coffeeImage);
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}