package com.example.userlistapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapView;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import android.widget.ImageView;
import android.widget.TextView;

public class UserDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView profileImageView;
    private ImageView flagImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        profileImageView = findViewById(R.id.profile_image);
        flagImageView = findViewById(R.id.flag_image);
        nameTextView = findViewById(R.id.name_text);
        emailTextView = findViewById(R.id.email_text);
        phoneTextView = findViewById(R.id.phone_text);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("USER");

        if (user != null) {
            nameTextView.setText(user.getName().getFirst() + " " + user.getName().getLast());
            emailTextView.setText(user.getEmail());
            phoneTextView.setText(user.getPhone());
            Picasso.get().load(user.getPicture().getLarge()).into(profileImageView);
            // Asumiendo que la bandera del país está en el campo 'nat'
            Picasso.get().load("https://www.countryflags.io/" + user.getNat() + "/flat/64.png").into(flagImageView);

            // Configuración del mapa
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng userLocation = new LatLng(-34, 151); // Default location, replace with user location
        mMap.addMarker(new MarkerOptions().position(userLocation).title("User Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));
    }
}
