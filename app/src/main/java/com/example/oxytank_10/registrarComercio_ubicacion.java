package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.oxytank_10.databinding.ActivityRegistrarComercioUbicacion3Binding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class registrarComercio_ubicacion extends AppCompatActivity implements OnMapReadyCallback  {

    boolean dioPermiso;
    private GoogleMap mMap;
    private ActivityRegistrarComercioUbicacion3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_comercio_ubicacion);

        revisarPermiso();

    }

    private void revisarPermiso() {

        

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}