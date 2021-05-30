package com.example.oxytank_10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class Adaptador_listaComercios extends BaseAdapter {

    private static final LayoutInflater inflater = null; // Instanciar el archivo list_items_listaComercios

    Context context; //Genera el adaptador.
    //String [][] datosComercio; // Se pasan los parametros a mostrar.
    //int [] lista_calificacion; //Se pasa la lista con las calificaciones.

    /*
    //Generar el constructor de la clase.
    public Adaptador_listaComercios(Context context, String [][] datosComercio, int [] lista_calificacion){
        this.context = context;
        this.datosComercio = datosComercio;
        this.lista_calificacion = lista_calificacion;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Se relaciona con el diseño del layput "list_item_listaComercios".
        final View view = inflater.inflate(R.layout.list_item_listacomercios, null);

        //Relacion de las variables con el entorno grafico.
        TextView nombreComercio = view.findViewById(R.id.tv_listItems_listaComercios_nombre);
        TextView servicio = view.findViewById(R.id.tv_listItems_listaComercios_servicio);
        RatingBar calificacion = view.findViewById(R.id.ratbar_listItems_listaComercios_calificacion);

        //Que accion tomar si se llega a ese punto.
        //Asignar los valores.

        return null;

    }

    /*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Se relaciona con el diseño del layput "list_item_listaComercios".
        final View view = inflater.inflate(R.layout.list_item_listacomercios, null);

        //Relacion de las variables con el entorno grafico.
        TextView nombreComercio = view.findViewById(R.id.tv_listItems_listaComercios_nombre);
        TextView servicio = view.findViewById(R.id.lv_cuentaTipoCliente_PantallaPrincipal_mostrarComercios);
        RatingBar calificacion = view.findViewById(R.id.ratbar_listItems_listaComercios_calificacion);

        //Asignar los valores.
        nombreComercio.setText(datosComercio[position][0]);
        servicio.setText(datosComercio[position][1]);
        calificacion.setProgress(lista_calificacion[position]);

        //Que accion tomar si se llega a ese punto.


        return view;
    }

     */

    @Override
    public int getCount() {
        return 0;
        //return datosComercio.length; //Obtener el total de elementos que hay.
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
