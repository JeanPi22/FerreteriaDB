package com.pierredev.ferreteriadb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Instanciar Botones
        Button btnCliente = findViewById(R.id.btnCli);
        Button btnPedido = findViewById(R.id.btnPed);
        Button btnProducto = findViewById(R.id.btnPro);
        Button btnFactura = findViewById(R.id.btnFac);

//      Metodos para abrir nuevas interfaces
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UsuarioActivity.class);
                startActivity(intent);
            }
        });

        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PedidoActivity.class);
                startActivity(intent);
            }
        });

        btnProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductoActivity.class);
                startActivity(intent);
            }
        });

        btnFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FacturaActivity.class);
                startActivity(intent);
            }
        });

    }
}