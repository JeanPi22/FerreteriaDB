package com.pierredev.ferreteriadb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FacturaActivity extends AppCompatActivity {

    EditText etCodigo, etFecha, etValor, etIdCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        etCodigo = findViewById(R.id.etCFac);
        etFecha = findViewById(R.id.etFFac);
        etValor = findViewById(R.id.etVFac);
        etIdCliente = findViewById(R.id.etIdCFac);
    }

    //  METODO PARA REGISTRAR FACTURA
    public void registrarFactura(View view) {
//      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(FacturaActivity.this);
        SQLiteDatabase BaseDatos = conexionBD.getWritableDatabase();

//      Variables donde se almacenaran los datos
        String codigoFac = etCodigo.getText().toString();
        String fecha = etFecha.getText().toString();
        String valor = etValor.getText().toString();
        String idCliente = etIdCliente.getText().toString();

//      Solicitar a usuario que ingrese información en esas variables
        if (!codigoFac.isEmpty() && !fecha.isEmpty() && !valor.isEmpty() && !idCliente.isEmpty()) {
            ContentValues registro = new ContentValues();
//
//          Llevar registros de las variables a entidades de la tabla
            registro.put("codigoFac", codigoFac);
            registro.put("fecha", fecha);
            registro.put("valor", valor);
            registro.put("identificacion2", idCliente);

//          Insertar información a la table
            BaseDatos.insert("Factura", null, registro);

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

    //  METODO PARA CONSULTAR FACTURA
    public void consultarFactura(View view) {
//      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(FacturaActivity.this);
        SQLiteDatabase BaseDatos = conexionBD.getWritableDatabase();

//      Variables donde se almacenaran los datos
        String codigoFac1 = etCodigo.getText().toString();

//      Realizar busqueda con "Cedula"
        if (!codigoFac1.isEmpty()) {
//          Sentencia Sql para llamar datos
            Cursor fila = BaseDatos.rawQuery("select fecha, valor, identificacion2 from Factura where codigoFac = " + codigoFac1, null);

//          Condicional para mostrar información
//          "moveToFirst()" mueve la información encontrada por la sentencia SQL, posicionandola arriba de la tabla
            if (fila.moveToFirst()) {
                etFecha.setText(fila.getString(0));
                etValor.setText(fila.getString(1));
                etIdCliente.setText(fila.getString(2));

//              Cerrar base de datos
                BaseDatos.close();
            }
            else {
                Toast.makeText(this,"No se encontro factura", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Ingrese el código de la factura a buscar", Toast.LENGTH_LONG).show();
        }

    }

    // METODO PARA EDITAR FACTURA
    public void editarFactura(View view) {
        //      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(FacturaActivity.this);
        SQLiteDatabase baseDatos = conexionBD.getWritableDatabase();

//      Variables donde se almacenaran los datos
        String codigoFac = etCodigo.getText().toString();
        String fecha = etFecha.getText().toString();
        String valor = etValor.getText().toString();
        String idCliente = etIdCliente.getText().toString();

        if (!codigoFac.isEmpty()) {
            ContentValues valores = new ContentValues();
            valores.put("fecha", fecha);
            valores.put("valor", valor);
            valores.put("identificacion2", idCliente);

            int filasActualizadas = baseDatos.update("Factura", valores, "codigoFac=?", new String[]{codigoFac});

            if (filasActualizadas > 0) {
                Toast.makeText(this, "Factura actualizado exitosamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No se encontró factura con ese código", Toast.LENGTH_LONG).show();
            }

//          Cerrar base de datos
            baseDatos.close();
        } else {
            Toast.makeText(this, "Ingrese el código de la factura a editar", Toast.LENGTH_LONG).show();
        }
    }

    // METODO PARA ELIMINAR FACTURA
    public void eliminarFactura(View view) {
        //      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(FacturaActivity.this);
        SQLiteDatabase baseDatos = conexionBD.getWritableDatabase();

        String codigoFac = etCodigo.getText().toString();

        if (!codigoFac.isEmpty()) {
            int filasEliminadas = baseDatos.delete("Factura", "codigoFac=?", new String[]{codigoFac});

            if (filasEliminadas > 0) {
                Toast.makeText(this, "Factura eliminado exitosamente", Toast.LENGTH_LONG).show();
                limpiar();
            } else {
                Toast.makeText(this, "No se encontró factura con ese código", Toast.LENGTH_LONG).show();
            }
//          Cerrar base de datos
            baseDatos.close();
        } else {
            Toast.makeText(this, "Ingrese el código de la factura a eliminar", Toast.LENGTH_LONG).show();
        }
    }

    //    Metodo para limpiar campos
    private void limpiar(){
        etCodigo.setText("");
        etFecha.setText("");
        etValor.setText("");
        etIdCliente.setText("");
    }

}