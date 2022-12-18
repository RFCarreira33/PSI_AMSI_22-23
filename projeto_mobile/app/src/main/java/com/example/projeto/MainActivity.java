package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
<<<<<<< Updated upstream
=======
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentContainerView;
>>>>>>> Stashed changes

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import Utils.Public;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar = null;
    TextView latitudes, longitudes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.drawable.logo);

        latitudes = findViewById(R.id.latitude);
        longitudes = findViewById(R.id.longitude);

        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            } else {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                longitudes.setText(String.valueOf(longitude));
                latitudes.setText(String.valueOf(latitude));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Intent i = null;
        SharedPreferences sharedPreferences = getSharedPreferences(Public.SHARED_FILE, MODE_PRIVATE);
        boolean isLogged = sharedPreferences.contains(Public.TOKEN);
        if(!isLogged) {
            i = new Intent(this, Login.class);
            startActivity(i);
            return super.onOptionsItemSelected(item);
        }

        switch (itemId){
            case R.id.app_bar_cart:
                i = new Intent(this, ShoppingCart.class);
                break;

            case R.id.app_bar_search:
                i = new Intent(this, FiltersActivity.class);
                break;

            case R.id.app_bar_personalArea:
                i = new Intent(this, PersonalArea.class);
                break;
        }
        if (i != null) {
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickbtn(View view) {
        Intent intent = new Intent(this, QRReader.class);
        startActivity(intent);
    }
    
}