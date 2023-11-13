package com.pierredev.ferreteriadb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionBD extends SQLiteOpenHelper {

    //  Versión y nombre de la base de datos
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "Ferreteria.db";

    //  Tablas de la base de datos
    public static final String TABLE_CLIENTE = "t_cliente";
    public static final String TABLE_FACTURA = "t_factura";
    public static final String TABLE_PEDIDO = "t_pedido";
    public static final String TABLE_PRODUCTO = "t_producto";
    public static final String TABLE_PED_PROD = "t_ped_prod";

    //    Constructor Bd
    public ConexionBD(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    //  Metodos para crear Bd
    @Override
    public void onCreate(SQLiteDatabase FerreteriaBD) {

        FerreteriaBD.execSQL("CREATE TABLE " + TABLE_CLIENTE + "(" +
                "cod_cli INTEGER PRIMARY KEY," +
                "nombre TEXT," +
                "direccion TEXT," +
                "telefono TEXT)");

        FerreteriaBD.execSQL("CREATE TABLE " + TABLE_FACTURA + "(" +
                "cod_fact INTEGER PRIMARY KEY," +
                "fecha TEXT," +
                "valor REAL," +
                "cod_cli2 INTEGER," +
                "FOREIGN KEY (cod_cli2) REFERENCES " + TABLE_CLIENTE + "(cod_cli))");

        FerreteriaBD.execSQL("CREATE TABLE " + TABLE_PEDIDO + "(" +
                "cod_ped INTEGER PRIMARY KEY," +
                "descripcion TEXT," +
                "fecha TEXT," +
                "cod_cli1 INTEGER," +
                "FOREIGN KEY (cod_cli1) REFERENCES " + TABLE_CLIENTE + "(cod_cli))");

        FerreteriaBD.execSQL("CREATE TABLE " + TABLE_PRODUCTO + "(" +
                "cod_prod INTEGER PRIMARY KEY," +
                "fabricante TEXT," +
                "valor REAL)");

        FerreteriaBD.execSQL("CREATE TABLE " + TABLE_PED_PROD + "(" +
                "cod_ped1 INTEGER," +
                "cod_prod1 INTEGER," +
                "FOREIGN KEY (cod_ped1) REFERENCES " + TABLE_PEDIDO + "(cod_ped)," +
                "FOREIGN KEY (cod_prod1) REFERENCES " + TABLE_PRODUCTO + "(cod_prod))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
