package com.netceylon.coffeeshop.User.MainFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netceylon.coffeeshop.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SpecialOffersFragment extends Fragment {

    private LinearLayout promoContainer;
    private static final String EXTERNAL_JSON_URL = "https://raw.githubusercontent.com/NadikaPramudith/ExternalJson/main/promos.json";
    private ConnectivityReceiver connectivityReceiver;
    private boolean wasOffline = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special_offers, container, false);
        promoContainer = view.findViewById(R.id.promoContainer);

        wasOffline = !isOnline();

        if (isOnline()) {
            Toast.makeText(requireContext(), "Online mode: Fetching latest offers.", Toast.LENGTH_SHORT).show();
            new FetchPromosTask(inflater).execute(EXTERNAL_JSON_URL);
        } else {
            Toast.makeText(requireContext(), "Offline mode: Showing local offers.", Toast.LENGTH_SHORT).show();
            loadAllPromosFromAssets(inflater);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register BroadcastReceiver to track connectivity changes
        connectivityReceiver = new ConnectivityReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        requireContext().registerReceiver(connectivityReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister BroadcastReceiver when fragment is paused
        if (connectivityReceiver != null) {
            requireContext().unregisterReceiver(connectivityReceiver);
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private void loadAllPromosFromAssets(LayoutInflater inflater) {
        try {
            InputStream is = requireContext().getAssets().open("promos.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            displayPromosFromJson(json, inflater);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayPromosFromJson(String json, LayoutInflater inflater) {
        try {
            // Clear existing promos before adding new ones
            promoContainer.removeAllViews();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray promosArray = jsonObject.getJSONArray("promos");

            for (int i = 0; i < promosArray.length(); i++) {
                String message = promosArray.getString(i);
                addPromoCard(inflater, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPromoCard(LayoutInflater inflater, String message) {
        View cardView = inflater.inflate(R.layout.item_promo_card, promoContainer, false);
        TextView textView = cardView.findViewById(R.id.promoText);
        textView.setText(message);
        promoContainer.addView(cardView);
    }

    private class FetchPromosTask extends AsyncTask<String, Void, String> {
        private LayoutInflater inflater;

        FetchPromosTask(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                in.close();
            } catch (Exception e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String json) {
            if (json != null) {
                displayPromosFromJson(json, inflater);
            } else {
                Toast.makeText(requireContext(), "Failed to fetch online offers. Switching to offline mode.", Toast.LENGTH_SHORT).show();
                loadAllPromosFromAssets(inflater);
            }
        }
    }

    // BroadcastReceiver to monitor network connectivity changes
    private class ConnectivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isNowOnline = isOnline();

            // Check if connectivity state has changed
            if (isNowOnline && wasOffline) {
                // Changed from offline to online
                Toast.makeText(context, "Connected to internet. Switching to online mode.", Toast.LENGTH_SHORT).show();
                new FetchPromosTask(LayoutInflater.from(context)).execute(EXTERNAL_JSON_URL);
                wasOffline = false;
            } else if (!isNowOnline && !wasOffline) {
                // Changed from online to offline
                Toast.makeText(context, "Internet disconnected. Switching to offline mode.", Toast.LENGTH_SHORT).show();
                loadAllPromosFromAssets(LayoutInflater.from(context));
                wasOffline = true;
            }
        }
    }
}
