package com.example.oxytank_10;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.oxytank_10.databinding.ActivityCuentaTipoClienteVerDireccionBinding;

public class cuentaTipoCliente_verDireccion extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityCuentaTipoClienteVerDireccionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCuentaTipoClienteVerDireccionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Se guardan la latitud y longitud del negocio obtenidas en la pantalla anterior.
        String latitud = getIntent().getStringExtra("comercio_latitud");
        String longitud = getIntent().getStringExtra("comercio_longitud");

        //Se converten los datos en flotante para utilizarlos en la longitud.
        float latitud_float = Float.parseFloat(latitud);
        float longitud_float = Float.parseFloat(longitud);

        mMap = googleMap;

        // Agregar la localización del comercio
        LatLng comercio = new LatLng(latitud_float, longitud_float);
        //Marcar la posición del comercio
        mMap.addMarker(new MarkerOptions().position(comercio).title("Comercio"));
        //Posicionar la camara en la ubicación del comercio
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(comercio,18));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE); //Cambiar el tipo de mapa.
    }
}