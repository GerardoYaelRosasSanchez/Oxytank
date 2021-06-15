package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class cuentaTipoCliente_mostrarComercio extends AppCompatActivity {

    //Relación con el entorno grafico.
    TextView nombreComercio, telefono, direccion;
    TextView servicio_venta, servicio_renta, servicio_refil;
    RatingBar valoracion;

    //Guarda el id del comercio de la pantalla anterior.
    String comercio_id;

    //Guardar el id del usuario que esta consultando el comercio.
    String usuario_id;

    // Guardar el ID correspondiente al la valoración.
    int numValoraciones;

    //Guardar la valoración ingresada por el usuario.
    int valoracion_usuario;
    int valoracion_usuario_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_tipo_cliente_mostrar_comercio);

        // Relación de las variables con la parte gráfica.
        nombreComercio = findViewById(R.id.tv_cuentaTipoCliente_mostrarComercio_titulo);
        telefono = findViewById(R.id.tv_cuentaTipoCliente_mostrarComercio_telefono);
        direccion = findViewById(R.id.tv_cuentaTipoCliente_mostrarComercio_direccion);
        servicio_venta = findViewById(R.id.tv_cuentaTipoCliente_mostrarComercio_serviciosVenta);
        servicio_renta = findViewById(R.id.tv_cuentaTipoCliente_mostrarComercio_serviciosRenta);
        servicio_refil = findViewById(R.id.tv_cuentaTipoCliente_mostrarComercio_serviciosRefil);
        valoracion = findViewById(R.id.ratbr_cuentaTipoCliente_mostrarComercio_valoracion);

        mostrarDatos(); // Mostrar datos del comercio.

        valorarComercio(); //Valorar comercio.

    }

    //Métodos.

    //Ir a la pantalla principal de la cuenta de tipo comercio.
    public void irPantallaPrincipal(View view){

        Intent Act_cuentaCliente_pantallaPrincipal = new Intent(this, cuentaTipoCliente_PantallaPrincipal.class);
        String usuario_id = getIntent().getStringExtra("usuario_id");
        Act_cuentaCliente_pantallaPrincipal.putExtra("usuario_id", usuario_id);
        startActivity(Act_cuentaCliente_pantallaPrincipal);

    }

    //Mostrar los datos corres.
    public void mostrarDatos(){


        //Objeto: Administrar la base de datos.
        DBHelper administrador = new DBHelper(this, "Oxytank_db", null, 1);

        //Abrir Base de Datos
        SQLiteDatabase BaseDatos = administrador.getWritableDatabase();

        //Recibir el ID ingresado correspondiente al comercio del usuario en el registro.
        comercio_id = getIntent().getStringExtra("comercio_id");
        int id_comercio = Integer.parseInt(comercio_id); //Convertirlo a entero para usarlo en la base de datos.

        //Caso: Validar que los campos se encuentren llenos.
        //Objeto: Seleccionar un comercio.
        Cursor fila_comercio = BaseDatos.rawQuery
                ("select nombreComercio, telefono, direccion, renta, venta, refil from comercios " +
                        "where idComercio =" + id_comercio, null);

        //Caso: Se encontro el comercio.
        if(fila_comercio.moveToFirst()){

            //Mostrar datos del comercio en la pantalla.
            nombreComercio.setText(fila_comercio.getString(0));
            telefono.setText(fila_comercio.getString(1));
            direccion.setText(fila_comercio.getString(2));
            servicio_renta.setText("Renta:\n" +fila_comercio.getString(3));
            servicio_venta.setText("Venta:\n" +fila_comercio.getString(4));
            servicio_refil.setText("Refil:\n" + fila_comercio.getString(5));

        }

        //Cerrar el cursor.
        fila_comercio.close();

        //Cerrar la Base de datos.
        BaseDatos.close();

    }

    //Guarda la valoración del comercio ingresada por el usuario en la base de datos.
    public void valorarComercio(){

        //Objeto: Administrar la base de datos.
        DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

        // Abrir la base de datos en modo lectura y escritura.
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        //Contar cuantas filas tiene la tabla para crear el id.
        String contarValoraciones_Consulta = "SELECT nombreComercio FROM comercios"; // Guardar el texto que corresponde a la consulta.
        Cursor cursor = BaseDatos.rawQuery(contarValoraciones_Consulta, null); //Realizar la consulta en la base de datos.
        numValoraciones = cursor.getCount(); // Guardar el resultado de la consulta en una variable.
        cursor.close();

        // En base al numero de valoraciones, crear el ID de la valoracion.
        numValoraciones += 1;

        //Recibir el ID ingresado correspondiente al comercio del usuario en el registro.
        comercio_id = getIntent().getStringExtra("comercio_id");
        int id_comercio = Integer.parseInt(comercio_id); //Convertirlo a entero para usarlo en la base de datos.

        //Recibir el ID del usuario que esta realizando la valoración.
        usuario_id = getIntent().getStringExtra("usuario_id");
        int id_usuario = Integer.parseInt(usuario_id); //Convertirlo a entero para usarlo en la base de datos.

        //Almacenar la valoracion del usuario en una variable.
        valoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                //Convertir la valoración del usuario en un valor entero.
                valoracion_usuario_int = (int) rating;
                String mensaje = null;

                // Almacenar la valoración que dio el usuario al comercio utilizando la rating bar.
                switch (valoracion_usuario_int){

                    case 1:
                        mensaje = "Uno";
                        break;

                    case 2:
                        mensaje = "Dos";
                        break;

                    case 3:
                        mensaje = "Tres";
                        break;

                    case 4:
                        mensaje = "Cuatro";
                        break;

                    case 5:
                        mensaje = "Cinco";
                        break;

                }

                //Toast.makeText(cuentaTipoCliente_mostrarComercio.this, mensaje, Toast.LENGTH_LONG).show();

            }
        });

        //Cerrar la Base de datos.
        BaseDatos.close();

    }

    //Ver dirección del comercio
    public void VerDireccion(View view){
        //Objeto: Administrar la base de datos.
        DBHelper administrador = new DBHelper(this, "Oxytank_db", null, 1);

        //Abrir Base de Datos
        SQLiteDatabase BaseDatos = administrador.getWritableDatabase();

        int id_int = Integer.parseInt(comercio_id);

        //Caso: Validar que los campos se encuentren llenos.
        //Objeto: Seleccionar un comercio.
        Cursor fila = BaseDatos.rawQuery
                ("select latitud, longitud from comercios " +
                        "where idComercio =" + id_int, null);

        //Caso: Se encontro el comercio.
        if(fila.moveToFirst()){

            //Conseguir la latitud y longitud
            String latitud = fila.getString(0);
            String longitud = fila.getString(1);

            fila.close(); //Cerrar el cursor.

            //Cerrar la Base de datos.
            BaseDatos.close();

            //Pasar a la actividad "cuentaTipoCliente_verDireccion".
            Intent Act_cuentaTipoCliente_verDireccion = new Intent(this, cuentaTipoCliente_verDireccion.class);
            Act_cuentaTipoCliente_verDireccion.putExtra("comercio_latitud", latitud); //Pasar la latitud del comercio a la siguiente pantalla.
            Act_cuentaTipoCliente_verDireccion.putExtra("comercio_longitud", longitud); //Pasar la longitud del comercio a la siguiente pantalla.
            String usuario_id = getIntent().getStringExtra("usuario_id");
            Act_cuentaTipoCliente_verDireccion.putExtra("usuario_id", usuario_id);
            startActivity(Act_cuentaTipoCliente_verDireccion);

        }

    }



}