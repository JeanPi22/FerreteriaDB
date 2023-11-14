package com.pierredev.ferreteriadb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UsuarioActivity extends AppCompatActivity {

    EditText etIdentificacion, etNombre, etDireccion, etTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        etIdentificacion = findViewById(R.id.etIdC);
        etNombre = findViewById(R.id.etNomC);
        etDireccion = findViewById(R.id.etDirC);
        etTelefono = findViewById(R.id.etTelC);
    }

    //  METODO PARA REGISTRAR USUARIO
    public void registrarCliente(View view) {
//      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(UsuarioActivity.this);
        SQLiteDatabase BaseDatos = conexionBD.getWritableDatabase();

//      Variables donde se almacenaran los datos
        String identificacion = etIdentificacion.getText().toString();
        String nombre = etNombre.getText().toString();
        String direccion = etDireccion.getText().toString();
        String telefono = etTelefono.getText().toString();

//      Solicitar a usuario que ingrese información en esas variables
        if (!identificacion.isEmpty() && !nombre.isEmpty() && !direccion.isEmpty() && !telefono.isEmpty()) {
            ContentValues registro = new ContentValues();
//
//          Llevar registros de las variables a entidades de la tabla
            registro.put("identificacion", identificacion);
            registro.put("nombre", nombre);
            registro.put("direccion", direccion);
            registro.put("telefono", telefono);

//          Insertar información a la table
            BaseDatos.insert("Clientes", null, registro);

//          Cerrar base de datos
            BaseDatos.close();

//          Limpiar campos
            limpiar();

//          Mensaje de almacenamiento o error
            Toast.makeText(this,"Registro Exitos", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Ingrese correctamente todos los datos", Toast.LENGTH_LONG).show();
        }
    }

    //  METODO PARA CONSULTAR USUARIO
    public void consultarCliente(View view) {
//      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(UsuarioActivity.this);
        SQLiteDatabase BaseDatos = conexionBD.getWritableDatabase();

//      Variables donde se almacenaran los datos
        String identificacion1 = etIdentificacion.getText().toString();

//      Realizar busqueda con "Cedula"
        if (!identificacion1.isEmpty()) {
//          Sentencia Sql para llamar datos
            Cursor fila = BaseDatos.rawQuery("select nombre, direccion, telefono from Clientes where identificacion = " + identificacion1, null);

//          Condicional para mostrar información
//          "moveToFirst()" mueve la información encontrada por la sentencia SQL, posicionandola arriba de la tabla
            if (fila.moveToFirst()) {
                etNombre.setText(fila.getString(0));
                etDireccion.setText(fila.getString(1));
                etTelefono.setText(fila.getString(2));

//              Cerrar base de datos
                BaseDatos.close();
            }
            else {
                Toast.makeText(this,"No se encontro usuario", Toast.LENGTH_LONG).show();
            }
        }

    }

    //    Metodo para limpiar campos
    private void limpiar(){
        etIdentificacion.setText("");
        etNombre.setText("");
        etDireccion.setText("");
        etTelefono.setText("");
    }

}