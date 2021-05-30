package com.example.oxytank_10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

public class cuentaTipoComercio_PantallaPrincipal extends AppCompatActivity {

    //Relación con el entorno gráfico.
    RadioButton venta_Disponible, venta_NoDisponible, venta_SinServicio;
    RadioButton renta_Disponible, renta_NoDisponible, renta_SinServicio;
    RadioButton refil_Disponible, refil_NoDisponible, refil_SinServicio;

    //Guarda los datos de disponibilidad del servicio.
    String servicio_venta, servicio_renta, servicio_refil;

    //Recibir el ID del comercio.
    String comercio_id;
    int id_comercio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_tipo_comercio_pantalla_principal);

        //Realción con de las varibales con el entorno gráfico.
        venta_Disponible = findViewById(R.id.rb_cuentaTipoComercio_PantallaPrincipal_venta_Disponible);
        venta_NoDisponible = findViewById(R.id.rb_cuentaTipoComercio_PantallaPrincipal_venta_NoDisponible);
        venta_SinServicio = findViewById(R.id.rb_cuentaTipoComercio_PantallaPrincipal_venta_SinServicio);
        renta_Disponible = findViewById(R.id.rb_cuentaTipoComercio_PantallaPrincipal_renta_Disponible);
        renta_NoDisponible = findViewById(R.id.rb_cuentaTipoComercio_PantallaPrincipal_renta_NoDisponible);
        renta_SinServicio = findViewById(R.id.rb_cuentaTipoComercio_PantallaPrincipal_renta_SinServicio);
        refil_Disponible = findViewById(R.id.rb_cuentaTipoComercio_PantallaPrincipal_refil_Disponible);
        refil_NoDisponible = findViewById(R.id.rb_cuentaTipoComercio_PantallaPrincipal_refil_NoDisponible);
        refil_SinServicio = findViewById(R.id.rb_cuentaTipoComercio_PantallaPrincipal_refil_SinServicio);

        //Recibir el ID del comercio.
        comercio_id = getIntent().getStringExtra("comercio_id");
        id_comercio = Integer.parseInt(comercio_id); //Convertirlo a entero para usarlo en la base de datos.

        //Marca los radiobuttons como check en base a la disponibilidad seleccionada por el usuario.
        seleccionarDatosDisponibilidad();

    }

    //Métodos.

    //Da check a los datos que corresponden a la disponibilidad del comercio.
    public void seleccionarDatosDisponibilidad() {

        //Objeto: Administrar la base de datos.
        DBHelper administrador = new DBHelper(this, "Oxytank_db", null, 1);

        //Abrir Base de Datos
        SQLiteDatabase BaseDatos = administrador.getWritableDatabase();

        //Caso: Validar que los campos se encuentren llenos.
        //Objeto: Seleccionar un comercio.
        Cursor fila_comercio = BaseDatos.rawQuery
                ("select venta, renta, refil, idUsuario from comercios " +
                        "where idComercio =" + id_comercio, null);

        //Caso: Se encontro el comercio.
        if(fila_comercio.moveToFirst()){

            //Guardar los datos de disponibilidad del servicio.
            servicio_venta = fila_comercio.getString(0);
            servicio_renta = fila_comercio.getString(1);
            servicio_refil = fila_comercio.getString(2);

        }

        //Aplicar como check al radiobutton correspondiente.
            //Aplicar check a venta.
        if (servicio_venta.equals("Disponible")){
            venta_Disponible.setChecked(true);
        }
        else if (servicio_venta.equals("No Disponible")){
            venta_NoDisponible.setChecked(true);
        }
        else if (servicio_venta.equals("Sin servicio")){
            venta_SinServicio.setChecked(true);
        }

            //Aplicar check a renta.
        if (servicio_renta.equals("Disponible")){
            renta_Disponible.setChecked(true);
        }
        else if (servicio_renta.equals("No Disponible")){
            renta_NoDisponible.setChecked(true);
        }
        else if (servicio_renta.equals("Sin servicio")){
            renta_SinServicio.setChecked(true);
        }

            //Aplicar check a refil.
        if (servicio_refil.equals("Disponible")){
            refil_Disponible.setChecked(true);
        }
        else if (servicio_refil.equals("No Disponible")){
            refil_NoDisponible.setChecked(true);
        }
        else if (servicio_refil.equals("Sin servicio")){
            refil_SinServicio.setChecked(true);
        }

        //Cerrar la Base de datos.
        BaseDatos.close();

    }

    //Actualizar el estado de los tanques de oxígeno.
    public void actualizarDisponibilidad(View view){

        //Objeto: Administrar la base de datos.
        DBHelper admin = new DBHelper(this, "Oxytank_db", null, 1);

        // Abrir la base de datos en modo lectura y escritura.
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        //Objeto: Guardar las opciones ingresadas por el usuario.
        ContentValues registro = new ContentValues();

        //Verificar cual fue la seleccion del usuario.
            //Selección: Venta.
        if (venta_Disponible.isChecked()){
            servicio_venta = "Disponible";
            venta_Disponible.setChecked(true);
        }
        else if (venta_NoDisponible.isChecked()){
            servicio_venta = "No Disponible";
            venta_NoDisponible.setChecked(true);
        }
        else if (venta_SinServicio.isChecked()){
            servicio_venta = "Sin Servicio";
            venta_SinServicio.setChecked(true);
        }

            //Selección: Renta.
        if (renta_Disponible.isChecked()){
            servicio_renta = "Disponible";
            renta_Disponible.setChecked(true);
        }
        else if (renta_NoDisponible.isChecked()){
            servicio_renta = "No Disponible";
            renta_NoDisponible.setChecked(true);
        }
        else if (renta_SinServicio.isChecked()){
            servicio_renta = "Sin Servicio";
            renta_SinServicio.setChecked(true);
        }

            //Selección: Refil.
        if (refil_Disponible.isChecked()){
            servicio_refil = "Disponible";
            refil_Disponible.setChecked(true);
        }
        else if (refil_NoDisponible.isChecked()){
            servicio_refil = "No Disponible";
            refil_NoDisponible.setChecked(true);
        }
        else if (renta_SinServicio.isChecked()){
            servicio_refil = "Sin Servicio";
            renta_SinServicio.setChecked(true);
        }

        //Guardar los datos en el objeto "registro".
        registro.put("venta", servicio_venta);
        registro.put("renta", servicio_renta);
        registro.put("refil", servicio_refil);

            // Agregar la dirección del usuario en la base de datos.
            BaseDatos.update("comercios", registro, "idComercio=" + id_comercio, null);

            //Cerrar la Base de datos.
            BaseDatos.close();

            Toast.makeText(this, "Actualizacion exitosa.", Toast.LENGTH_LONG).show();
    }

}