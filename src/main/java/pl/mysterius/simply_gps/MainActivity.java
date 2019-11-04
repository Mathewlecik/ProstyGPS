package pl.mysterius.simply_gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textView_latitude;
    private TextView textView_longitude;
    private TextView textView_city;

    private Double latitude;
    private Double longitude;
    private Geocoder geo;
    private List<Address> adresses;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 98;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_latitude = findViewById(R.id.TextView_Latitude);
        textView_longitude = findViewById(R.id.TextView_Longitude);
        textView_city= findViewById(R.id.textView_City);

        geo = new Geocoder(getBaseContext(), Locale.getDefault());

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);

        }

        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, locationListener);

        Location location= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        latitude=location.getLatitude();
        longitude=location.getLongitude();
        textView_latitude.setText(Double.toString(latitude));
        textView_longitude.setText(Double.toString(longitude));

        try{
            adresses=geo.getFromLocation(latitude,longitude,1);
            if(!adresses.isEmpty()){
                textView_city.setText(adresses.get(0).getLocality());
            }
        }
        catch (IOException e){

        }




    }

    private class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location location) {
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            textView_latitude.setText(Double.toString(latitude));
            textView_longitude.setText(Double.toString(longitude));
            try{
                adresses=geo.getFromLocation(latitude,longitude,1);
                if(!adresses.isEmpty()){
                    textView_city.setText(adresses.get(0).getLocality());
                }
            }
            catch (IOException e){

            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }






}
