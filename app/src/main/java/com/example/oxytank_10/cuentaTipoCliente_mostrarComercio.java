package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
    int valoracion_usuario; // Guarda la valoración que se agregara en la base de datos.
    int valoracion_usuario_int; // Guarda la valoración seleccionada en la rating bar.
    float valoracion_comercio = 0; // Gurda la valoración del comercio de la base de datos por promedio.

    //Lista para guardar los datos referentes a las valoraciones del comercio
    int lista_valoraciones_valoracion[];
    int lista_valoraciones_idComercios[];
    int lista_valoraciones_idUsuarios[];

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

        guardarCanValoraciones(); // Guarda el numero de valoraciones registradas.

        //Le da a las listas el tamaño en base a las valoraciones.
        lista_valoraciones_valoracion = new int[numValoraciones];
        lista_valoraciones_idComercios = new int[numValoraciones];
        lista_valoraciones_idUsuarios = new int[numValoraciones];

        //Llenar el array "lista_comercios_nombre" con la información de comercio.
        guardarValoracionesenLista();

        mostrarDatos(); // Mostrar datos del comercio.

        valoracionRatingBar(); //Valorar comercio.

    }

    //Métodos.

    //Ir a la pantalla principal de la cuenta de tipo comercio.
    public void irPantallaPrincipal(View view){

        Intent Act_cuentaCliente_pantallaPrincipal = new Intent(this, cuentaTipoCliente_PantallaPrincipal.class);
        String usuario_id = getIntent().getStringExtra("usuario_id");
        Act_cuentaCliente_pantallaPrincipal.putExtra("usuario_id", usuario_id);
        startActivity(Act_cuentaCliente_pantallaPrincipal);

    }

    // Guardar la cantidad de valoraciones que hay.
    public void guardarCanValoraciones(){

        //Objeto: Administrar la base de datos.
        DBHelper administrador = new DBHelper(this, "Oxytank_db", null, 1);

        //Abrir Base de Datos
        SQLiteDatabase BaseDatos = administrador.getWritableDatabase();

        //Buscar todos los datos en las valoraciones de la base de datos.
        Cursor cursor = BaseDatos.rawQuery("select * from valoraciones", null);

        numValoraciones = cursor.getCount(); //Guardar cuantas caloraciones estan registradas.

        //Cerrar el cursor.
        cursor.close();

        //Cerrar la base de datos.
        BaseDatos.close();

    }

    //Guardar en una lista los datos del la valoracion, el id del comercio y usuario correspondiente a esa valoración.
    public void guardarValoracionesenLista(){

        int cont = 0; //Se utiliza para recorrer los arreglos y para realizar los ciclos en las funciones.

        //Objeto: Administrar la base de datos.
        DBHelper administrador = new DBHelper(this, "Oxytank_db", null, 1);

        //Abrir Base de Datos
        SQLiteDatabase BaseDatos = administrador.getReadableDatabase();

        //Buscar todos los datos de la tabla valoraciones de la base de datos.
        Cursor cursor = BaseDatos.rawQuery("select * from valoraciones", null);

        //Caso: Se encontraron valoraciones.
        if(cursor.moveToFirst()){
            //Llenar el arreglo valoraciones.
            do{
                lista_valoraciones_valoracion[cont] = cursor.getInt(1); //Guardar las valoraciones del comercio.
                lista_valoraciones_idComercios[cont] = cursor.getInt(2); //Guardar el id de los comercios valorados.
                lista_valoraciones_idUsuarios[cont] = cursor.getInt(3); //Guardar el id de los usuarios que valoron el comercio.
                cont += 1; //Pasar a la siguiente posición del arreglo.

            }while (cursor.moveToNext()); //Pasar al siguiente elemento del cursos.
        }

        //Cerrar el cursor.
        cursor.close();

        //Cerrar la base de datos.
        BaseDatos.close();

    }

    //Mostrar los datos corres.
    public void mostrarDatos(){

        int cant = 0;

        //Objeto: Administrar la base de datos.
        DBHelper administrador = new DBHelper(this, "Oxytank_db", null, 1);

        //Abrir Base de Datos
        SQLiteDatabase BaseDatos = administrador.getWritableDatabase();

        //Recibir el ID ingresado correspondiente al comercio del usuario en el registro.
        comercio_id = getIntent().getStringExtra("comercio_id");
        int id_comercio = Integer.parseInt(comercio_id); //Convertirlo a entero para usarlo en la base de datos.

        //Recibir el ID ingresado correspondiente al usuario que esta realizando la consulta.
        usuario_id = getIntent().getStringExtra("usuario_id");
        int id_usuario = Integer.parseInt(usuario_id); //Convertirlo a entero para usarlo en la base de datos.

        //Caso: Validar que los campos se encuentren llenos.
        //Objeto: Seleccionar un comercio.
        Cursor fila_comercio = BaseDatos.rawQuery
                ("select nombreComercio, telefono, direccion, renta, venta, refil from comercios " +
                        "where idComercio =" + id_comercio, null);

        //Guardar la suma de las valoraciones que corresponden al comercio seleccionado por el usuairo.
        for (int i = 0; i < numValoraciones; i++){
            // Verificar que la valoración en la lista corresponde al comercio que selecciono el usuario.
            if (lista_valoraciones_idComercios[i] == id_comercio){
            //if (lista_valoraciones_idComercios[i] == id_comercio && lista_valoraciones_idUsuarios[i] == id_usuario){
                valoracion_comercio += lista_valoraciones_valoracion[i];  //Guardar la suma de las valoraciones.
                cant += 1; // Guardar el numero de valoraciones del comercio que tiene el usuario.
            }

        }

        valoracion_comercio = valoracion_comercio/cant; // Calcular la valoración del comercio utilizando el promedio.

        //Caso: Se encontro el comercio.
        if(fila_comercio.moveToFirst()){

            //Mostrar datos del comercio en la pantalla.
            nombreComercio.setText(fila_comercio.getString(0));
            telefono.setText(fila_comercio.getString(1));
            direccion.setText(fila_comercio.getString(2));
            servicio_renta.setText("Renta:\n" +fila_comercio.getString(3));
            servicio_venta.setText("Venta:\n" +fila_comercio.getString(4));
            servicio_refil.setText("Refil:\n" + fila_comercio.getString(5));
            valoracion.setRating(valoracion_comercio);

        }

        //Cerrar el cursor.
        fila_comercio.close();

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

    // Guardar la valoración del ratingbar en una variable.
    public void valoracionRatingBar(){

        //Almacenar la valoracion del usuario en una variable.
        valoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                //Convertir la valoración del usuario en un valor entero.
                valoracion_usuario_int = (int) rating;

                // Almacenar la valoración que dio el usuario al comercio utilizando la rating bar.
                switch (valoracion_usuario_int){

                    case 1:
                        valoracion_usuario = 1;
                        break;

                    case 2:
                        valoracion_usuario = 2;
                        break;

                    case 3:
                        valoracion_usuario = 3;
                        break;

                    case 4:
                        valoracion_usuario = 4;
                        break;

                    case 5:
                        valoracion_usuario = 5;
                        break;

                }
            }
        });
    }

    //Guarda la valoración del comercio ingresada por el usuario en la base de datos.
    public void valorarComercio(View view){

        //Objeto: Administrar la base de datos.
        DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

        // Abrir la base de datos en modo lectura y escritura.
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        //Contar cuantas filas tiene la tabla para crear el id de la valoración.
        String contarValoraciones_Consulta = "SELECT valoracionUsuario FROM valoraciones"; // Guardar el texto que corresponde a la consulta.
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

        //Objeto: Guardar las opciones ingresadas por el usuario.
        ContentValues registro = new ContentValues();

        // Guardar el id de la valoración, el id del comercio que fue valorado y el id del usuario que valoro el comercio.
        registro.put("idValoracion", numValoraciones);
        registro.put("valoracionUsuario", valoracion_usuario);
        registro.put("idComercio", id_comercio);
        registro.put("idUsuario", id_usuario);

        //Insertar los valores dentro de la tabla "valoraciones".
        BaseDatos.insert("valoraciones", null, registro);


        //Cerrar la Base de datos.
        BaseDatos.close();

        //String algo = Integer.toString(valoracion_usuario);
        //Toast.makeText(this, algo, Toast.LENGTH_LONG).show();
    }





}