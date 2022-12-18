package com.example.projeto;

import static java.lang.Math.acos;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentContainerView;

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
    TextView latitudes, longitudes, distancia;
    private static final int EARTH_RADIUS = 6371;
    double latitude = 0;
    double longitude = 0;

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
        distancia = findViewById(R.id.distancia);

        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                longitudes.setText("Permissão negada");
                latitudes.setText("Permissão negada");

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                longitudes.setText(String.valueOf(longitude));
                latitudes.setText(String.valueOf(latitude));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        double d = calcDistancia(latitude, longitude);
        distancia.setText(String.valueOf(Math.round(d)));  //KM



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

    public double calcDistancia(double lat2, double lon2 )
    {
        lat2 = 24.288103439276064; // teste
        lon2 = 45.63301192299735; // teste

        double R = 6371e3; // metres
        double φ1 = latitude * Math.PI/180; // φ, λ in radians
        double φ2 = lat2 * Math.PI/180;
        double Δφ = (lat2-latitude) * Math.PI/180;
        double Δλ = (lon2-(longitude * Math.PI/180)) * Math.PI/180;

        double a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
                Math.cos(φ1) * Math.cos(φ2) *
                        Math.sin(Δλ/2) * Math.sin(Δλ/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double d = R * c / 1000; // in metres
        return d;
    }
}