package com.pierredev.ferreteriadb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionBD extends SQLiteOpenHelper {

    //  Versión y nombre de la base de datos
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "Ferreteria.db";

    //    Constructor Bd
    public ConexionBD(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    //  Metodos para crear Bd
    @Override
    public void onCreate(SQLiteDatabase FerreteriaBD) {

        FerreteriaBD.execSQL("CREATE TABLE Clientes (" +
                "identificacion INT PRIMARY KEY," +
                "nombre TEXT," +
                "direccion TEXT," +
                "telefono TEXT)");

        FerreteriaBD.execSQL("CREATE TABLE Factura (" +
                "codigoFac INT PRIMARY KEY," +
                "fecha TEXT," +
                "valor REAL," +
                "identificacion2 INT," +
                "FOREIGN KEY (identificacion2) REFERENCES Clientes (identificacion))");

        FerreteriaBD.execSQL("CREATE TABLE Pedido(" +
                "codigoPed INT PRIMARY KEY," +
                "descripcion TEXT," +
                "fecha TEXT," +
                "identificacion1 INT," +
                "FOREIGN KEY (identificacion1) REFERENCES Clientes (identificacion))");

        FerreteriaBD.execSQL("CREATE TABLE Producto (" +
                "codigoPro INT PRIMARY KEY," +
                "fabricante TEXT," +
                "valor REAL)");

        FerreteriaBD.execSQL("CREATE TABLE Ped_Pro (" +
                "codigoPed1 INT," +
                "codigoPro1 INT," +
                "FOREIGN KEY (codigoPed1) REFERENCES Pedido (codigoPed)," +
                "FOREIGN KEY (codigoPro1) REFERENCES Producto (codigoPro))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
