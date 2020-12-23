package com.coursework;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class MyLocationListener implements LocationListener {

    static Location imHere; // здесь будет всегда доступна самая последняя информация о местоположении пользователя.

    public static void SetUpLocationListener(Context context) // это нужно запустить в самом начале работы программы
    {
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener); // здесь можно указать другие более подходящие вам параметры

        imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onLocationChanged(Location loc) {
        imHere = loc;
    }
   public static Address getCountryInfo(Location location, Context context) {
        Address address = null;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        String errorMessage;
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = "IOException>>" + ioException.getMessage();
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "IllegalArgumentException>>" + illegalArgumentException.getMessage();
        }
        if (addresses != null && !addresses.isEmpty()) {
            address = addresses.get(0);
        }
        return address;
    }
    public static ArrayList<Double> getLocationLatLong(Context context, String address) throws IOException {
        ArrayList <Double> latLong=new ArrayList<>();
        double latitude=0;
        double longitude=0;
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        addresses = geocoder.getFromLocationName(address, 1);
        if(addresses.size() > 0) {
            latitude= addresses.get(0).getLatitude();
            longitude= addresses.get(0).getLongitude();
        }
        latLong.add(latitude);
        latLong.add(longitude);
            return latLong;




    }
    @Override
    public void onProviderDisabled(String provider) {}
    @Override
    public void onProviderEnabled(String provider) {}
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
