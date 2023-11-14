package com.pierredev.ferreteriadb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProductoActivity extends AppCompatActivity {

    EditText etCodigo, etFabricante, etValor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        etCodigo = findViewById(R.id.etCPro);
        etFabricante = findViewById(R.id.etFPro);
        etValor = findViewById(R.id.etVPro);
    }

    //  METODO PARA REGISTRAR PRODUCTO
    public void registrarProducto(View view) {
//      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(ProductoActivity.this);
        SQLiteDatabase BaseDatos = conexionBD.getWritableDatabase();

//      Variables donde se almacenaran los datos
        String codigo = etCodigo.getText().toString();
        String fabricante = etFabricante.getText().toString();
        String valor = etValor.getText().toString();

//      Solicitar a usuario que ingrese información en esas variables
        if (codigoProExiste(BaseDatos, codigo)) {
            Toast.makeText(this, "El código del producto ya existe", Toast.LENGTH_LONG).show();
        }
        else {
            if (!codigo.isEmpty() && !fabricante.isEmpty() && !valor.isEmpty()) {
                ContentValues registro = new ContentValues();
//
//              Llevar registros de las variables a entidades de la tabla
                registro.put("codigoPro", codigo);
                registro.put("fabricante", fabricante);
                registro.put("valor", valor);

//              Insertar información a la table
                BaseDatos.insert("Producto", null, registro);

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
    private boolean codigoProExiste(SQLiteDatabase baseDatos, String codigoPro) {
        Cursor cursor = baseDatos.rawQuery("SELECT * FROM Producto WHERE codigoPro = ?", new String[]{codigoPro});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    //  METODO PARA CONSULTAR PRODUCTO
    public void consultarProducto(View view) {
//      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(ProductoActivity.this);
        SQLiteDatabase BaseDatos = conexionBD.getWritableDatabase();

//      Variables donde se almacenaran los datos
        String codigoPro = etCodigo.getText().toString();

//      Realizar busqueda con "Codigo de Pedido"
        if (!codigoPro.isEmpty()) {
//          Sentencia Sql para llamar datos
            Cursor fila = BaseDatos.rawQuery("select fabricante, valor from Producto where codigoPro = " + codigoPro, null);

//          Condicional para mostrar información
//          "moveToFirst()" mueve la información encontrada por la sentencia SQL, posicionandola arriba de la tabla
            if (fila.moveToFirst()) {
                etFabricante.setText(fila.getString(0));
                etValor.setText(fila.getString(1));

//              Cerrar base de datos
                BaseDatos.close();
            }
            else {
                Toast.makeText(this,"No se encontro producto", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Ingrese código del producto a buscar", Toast.LENGTH_LONG).show();
        }

    }

    // METODO PARA EDITAR PRODUCTO
    public void editarProducto(View view) {
        //      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(ProductoActivity.this);
        SQLiteDatabase baseDatos = conexionBD.getWritableDatabase();

//      Variables donde se almacenaran los datos
        String codigo = etCodigo.getText().toString();
        String fabricante = etFabricante.getText().toString();
        String valor = etValor.getText().toString();

        if (!codigo.isEmpty()) {
            ContentValues valores = new ContentValues();
            valores.put("fabricante", fabricante);
            valores.put("valor", valor);

            int filasActualizadas = baseDatos.update("Producto", valores, "codigoPro=?", new String[]{codigo});

            if (filasActualizadas > 0) {
                Toast.makeText(this, "Producto actualizado exitosamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No se encontró Producto con ese código", Toast.LENGTH_LONG).show();
            }

//          Cerrar base de datos
            baseDatos.close();
        } else {
            Toast.makeText(this, "Ingrese código del Producto a editar", Toast.LENGTH_LONG).show();
        }
    }

    // METODO PARA ELIMINAR PRODUCTO
    public void eliminarProducto(View view) {
        //      Abrir base de datos
        ConexionBD conexionBD = new ConexionBD(ProductoActivity.this);
        SQLiteDatabase baseDatos = conexionBD.getWritableDatabase();

        String codigoPro = etCodigo.getText().toString();

        if (!codigoPro.isEmpty()) {
            int filasEliminadas = baseDatos.delete("Producto", "codigoPro=?", new String[]{codigoPro});

            if (filasEliminadas > 0) {
                Toast.makeText(this, "Producto eliminado exitosamente", Toast.LENGTH_LONG).show();
                limpiar();
            } else {
                Toast.makeText(this, "No se encontró producto con ese código", Toast.LENGTH_LONG).show();
            }
//          Cerrar base de datos
            baseDatos.close();
        } else {
            Toast.makeText(this, "Ingrese el código del producto a eliminar", Toast.LENGTH_LONG).show();
        }
    }

    //    Metodo para limpiar campos
    private void limpiar(){
        etCodigo.setText("");
        etFabricante.setText("");
        etValor.setText("");
    }

}