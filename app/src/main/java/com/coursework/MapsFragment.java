package com.coursework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(final GoogleMap googleMap) {
            ArrayList<String> adresses = new ArrayList<>();

            adresses = DataManager.selectAllAddresses(getContext());

            for (int i = 0; i < adresses.size(); i++) {

                try {
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(MyLocationListener.
                            getLocationLatLong(getContext(), adresses.get(i)).get(0), MyLocationListener.
                            getLocationLatLong(getContext(), adresses.get(i)).get(1))).
                            title(adresses.get(i)));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(MyLocationListener.
                            getLocationLatLong(getContext(), adresses.get(adresses.size() - 1)).get(0), MyLocationListener.
                            getLocationLatLong(getContext(), adresses.get(adresses.size() - 1)).get(1))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            final ArrayList<String> finalAdresses = adresses;
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    ScrollView info=getActivity().findViewById(R.id.infoAboutMarker);
                    LinearLayout infoLL= getActivity().findViewById(R.id.infoAboutMarkerLL);

                    if(info.getChildCount()>0)
                    infoLL.removeAllViews();
                    ArrayList<Integer> id=new ArrayList<>();
                    id=DataManager.getIdByLocation(getContext(),marker.getTitle());
                    for (int i = 0; i < Collections.frequency(finalAdresses,marker.getTitle()); i++) {
                        itemViolation iv=new itemViolation();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",id.get(i));
                        iv.setArguments(bundle);
                      getActivity().getSupportFragmentManager().beginTransaction().add(R.id.infoAboutMarkerLL,iv).commit();
                    }

                    return false;
                }
            });
        }

    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);

        }
    }
}