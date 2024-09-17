package com.example.userlistapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class UserDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
        }

        TextView nameTextView = findViewById(R.id.name_text_view);
        TextView emailTextView = findViewById(R.id.email_text_view);
        TextView countryTextView = findViewById(R.id.country_text_view);
        ImageView profileImageView = findViewById(R.id.profile_image_view);
        ImageView flagImageView = findViewById(R.id.flag_image_view);

        if (user != null) {
            nameTextView.setText(user.getName().getFirst() + " " + user.getName().getLast());
            emailTextView.setText(user.getEmail());
            countryTextView.setText(user.getLocation().getCountry());

            Picasso.get()
                    .load(user.getPicture().getLarge())
                    .into(profileImageView);

            // Load flag image
            String countryCode = getCountryCode(user.getLocation().getCountry());
            String flagUrl = "https://flagcdn.com/w320/" + countryCode.toLowerCase() + ".png";
            Picasso.get()
                    .load(flagUrl)
                    .into(flagImageView);

            // Initialize map
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (user != null) {
            LatLng userLocation = new LatLng(user.getLocation().getLatitude(), user.getLocation().getLongitude());
            mMap.addMarker(new MarkerOptions().position(userLocation).title(user.getLocation().getCountry()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));
        }
    }

    private String getCountryCode(String countryName) {
        // Map country name to country code
        switch (countryName.toLowerCase()) {
            case "switzerland": return "ch";
            // Add more cases as needed
            default: return "us"; // Default to US flag if unknown
        }
    }
}

