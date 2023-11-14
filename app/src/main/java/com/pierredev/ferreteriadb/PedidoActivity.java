package com.pierredev.ferreteriadb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PedidoActivity extends AppCompatActivity {

    EditText etCodigo, etDescripcion, etFecha, etIdCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        etCodigo = findViewById(R.id.etCPed);
        etDescripcion = findViewById(R.id.etDPed);
        etFecha = findViewById(R.id.etFPed);
        etIdCliente = findViewById(R.id.etIdPed);
    }

    //  METODO PARA REGISTRAR PEDIDO
    public void registrarPedido(View view) {
//      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(PedidoActivity.this);
        SQLiteDatabase BaseDatos = conexionBD.getWritableDatabase();

//      Variables donde se almacenaran los datos
        String codigo = etCodigo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String fecha = etFecha.getText().toString();
        String idCliente = etIdCliente.getText().toString();

//      Solicitar a usuario que ingrese información en esas variables
        if (codigoPedExiste(BaseDatos, codigo)) {
            Toast.makeText(this, "El código de pedido ya existe", Toast.LENGTH_LONG).show();
        }
        else {
            if (!codigo.isEmpty() && !descripcion.isEmpty() && !fecha.isEmpty() && !idCliente.isEmpty()) {
                ContentValues registro = new ContentValues();
//
//              Llevar registros de las variables a entidades de la tabla
                registro.put("codigoPed", codigo);
                registro.put("descripcion", descripcion);
                registro.put("fecha", fecha);
                registro.put("identificacion1", idCliente);

//              Insertar información a la table
                BaseDatos.insert("Pedido", null, registro);

//              Cerrar base de datos
                BaseDatos.close();

//              Limpiar campos
                limpiar();

//              Mensaje de almacenamiento o error
                Toast.makeText(this,"Registro Exito", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Ingrese correctamente todos los datos", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Método para verificar si el código de factura ya existe en la base de datos
    private boolean codigoPedExiste(SQLiteDatabase baseDatos, String codigoPed) {
        Cursor cursor = baseDatos.rawQuery("SELECT * FROM Pedido WHERE codigoPed = ?", new String[]{codigoPed});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    //  METODO PARA CONSULTAR PEDIDO
    public void consultarPedido(View view) {
//      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(PedidoActivity.this);
        SQLiteDatabase BaseDatos = conexionBD.getWritableDatabase();

//      Variables donde se almacenaran los datos
        String codigoPed = etCodigo.getText().toString();

//      Realizar busqueda con "Codigo de Pedido"
        if (!codigoPed.isEmpty()) {
//          Sentencia Sql para llamar datos
            Cursor fila = BaseDatos.rawQuery("select descripcion, fecha, identificacion1 from Pedido where codigoPed = " + codigoPed, null);

//          Condicional para mostrar información
//          "moveToFirst()" mueve la información encontrada por la sentencia SQL, posicionandola arriba de la tabla
            if (fila.moveToFirst()) {
                etDescripcion.setText(fila.getString(0));
                etFecha.setText(fila.getString(1));
                etIdCliente.setText(fila.getString(2));

//              Cerrar base de datos
                BaseDatos.close();
            }
            else {
                Toast.makeText(this,"No se encontro pedido", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Ingrese código del pedido a buscar", Toast.LENGTH_LONG).show();
        }

    }

    // METODO PARA EDITAR PEDIDO
    public void editarPedido(View view) {
        //      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(PedidoActivity.this);
        SQLiteDatabase baseDatos = conexionBD.getWritableDatabase();

//      Variables donde se almacenaran los datos
        String codigoPed = etCodigo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String fecha = etFecha.getText().toString();
        String identificacion = etIdCliente.getText().toString();

        if (!codigoPed.isEmpty()) {
            ContentValues valores = new ContentValues();
            valores.put("descripcion", descripcion);
            valores.put("fecha", fecha);
            valores.put("identificacion1", identificacion);

            int filasActualizadas = baseDatos.update("Pedido", valores, "codigoPed=?", new String[]{codigoPed});

            if (filasActualizadas > 0) {
                Toast.makeText(this, "Pedido actualizado exitosamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No se encontró Pedido con ese código", Toast.LENGTH_LONG).show();
            }

//          Cerrar base de datos
            baseDatos.close();
        } else {
            Toast.makeText(this, "Ingrese código del pedido a editar", Toast.LENGTH_LONG).show();
        }
    }

    // METODO PARA ELIMINAR PEDIDO
    public void eliminarPedido(View view) {
        //      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(PedidoActivity.this);
        SQLiteDatabase baseDatos = conexionBD.getWritableDatabase();

        String codigoPed = etCodigo.getText().toString();

        if (!codigoPed.isEmpty()) {
            int filasEliminadas = baseDatos.delete("Pedido", "codigoPed=?", new String[]{codigoPed});

            if (filasEliminadas > 0) {
                Toast.makeText(this, "Pedido eliminado exitosamente", Toast.LENGTH_LONG).show();
                limpiar();
            } else {
                Toast.makeText(this, "No se encontró pedido con ese código", Toast.LENGTH_LONG).show();
            }
//          Cerrar base de datos
            baseDatos.close();
        } else {
            Toast.makeText(this, "Ingrese el código del pedido a eliminar", Toast.LENGTH_LONG).show();
        }
    }

    //    Metodo para limpiar campos
    private void limpiar(){
        etCodigo.setText("");
        etDescripcion.setText("");
        etFecha.setText("");
        etIdCliente.setText("");
    }

}