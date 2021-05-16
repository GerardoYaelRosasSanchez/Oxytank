package com.example.oxytank_10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Telephony;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.SettingsClickListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class registrarComercio_ubicacion extends AppCompatActivity implements OnMapReadyCallback  {

    boolean dioPermiso; // Guardar si el usuario dio permiso de GPS.
    EditText solicitarDireccion; // Guarda la dirección ingresada por el usuario.
    GoogleMap mGoogleMap; //Variable para utilizar el fragmento de Google Maps en otras partes del código.

    // Al iniciar la aplicación.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_comercio_ubicacion);

        // Relacionar las variables con sus componentes graficos.
        solicitarDireccion = findViewById(R.id.edt_registrarComercio_comercio_solicitarDireccion);

        revisarPermiso(); // Verificar si el usuario dio permiso de GPS.

        // Si dio permiso de GPS, mostrar el mapa.
        if (dioPermiso){
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mapFr_registrarComercio_ubicacion_mapa);
            mapFragment.getMapAsync(this);

        }

    }

    // Métodos.

    // Verificar que el usuario aceptara los permisos de Geolocalización.
    private void revisarPermiso() {

        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            //Si el usuario acepta el permiso.
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                dioPermiso = true; // Guardar en la variable que el usuario acepto el permiso.
                //Toast.makeText(registrarComercio_ubicacion.this, "Permiso", Toast.LENGTH_SHORT).show();
            }
            //Si el usuario no acepta el permiso: Ir a configuraciones del dispositivo para habilitar los permisos de forma directa.
            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);
            }
            //Si el usuario no selecciona ninguna opción.
            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest(); // Seguir mostrando el pop up solicitando el permiso.
            }
        }).check();

    }

    // Mostrar la ubicación seleccionada en el mapa.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap; // Creo una nueva variable para poder mover la ubicación desde otras partes del código.
        LatLng cetiColomos = new LatLng(20.702429853485203, -103.38843748731824); // Guardar la latitud y la longitud en el mapa.
        googleMap.addMarker(new MarkerOptions().position(cetiColomos)); // Posiciónar  el marcador en la posición seleccionada.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cetiColomos, 18)); // Posicionar al camara en la posición donde se encuentra el marcador.

    }

    //Convertir la dirección ingresada por el usuario en latitud y longitud.
    public void convertirDireccionEnLatLon(View view){

        String direccionTexto = solicitarDireccion.getText().toString(); // Guardar la dirección ingresada por el usuario.

        /*
        * Declarar una variable de tipo Geocoder.
        * Esta variable sirve para convertir de dirección en texto a latitud y longitud.
        * */
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        //Regresar una arreglo con las direcciones que describen el nombre de una ubicacion.
        try {
            List<Address> addressList = geocoder.getFromLocationName(direccionTexto, 1);

            // Revisar si existe la dirección.
            if(addressList.size()>0){
                Address direccion = addressList.get(0); //Conseguir la primera dirección del array.

                gotoLocation(direccion.getLatitude(), direccion.getLongitude()); // Mostrar la dirección del usuario en el mapa.

                // Agregar un marcador en el mapa.
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(direccion.getLatitude(), direccion.getLongitude())));

            }

        } catch (IOException e) {
        }

    }

    // Mostrar una ubicación en el mapa.
    private void gotoLocation(double latitude, double longitude) {

        LatLng latLng = new LatLng(latitude, longitude); // Crear una variable para gaurdar la latitud y la longiutud.

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18); // Crear una variable para mover la ubicación del usuario en el mapa.
        mGoogleMap.moveCamera(cameraUpdate); // Mover la camara a la ubicacion.

    }

}