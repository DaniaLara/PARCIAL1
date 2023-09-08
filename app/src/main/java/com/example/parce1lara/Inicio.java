package com.example.parce1lara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class  Inicio extends AppCompatActivity {

    TextView Usertxt;
    Button BtnAplicaciones,BtnAcercaDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Usertxt = findViewById(R.id.TxtUsuario);

        BtnAplicaciones = findViewById(R.id.BtnAplicaciones);
        BtnAcercaDe = findViewById(R.id.BtnacercaDe);
        String user =getIntent().getStringExtra("usuario");

        Usertxt.setText("BIENVENIDO\n" + user);

        //programacion de botones
        BtnAplicaciones.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // Crear un Intent para ir a la otra actividad
                Intent intent = new Intent(Inicio.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        BtnAcercaDe.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // Crear un Intent para ir a la otra actividad
                // de esta this a la otra class
                Intent intent = new Intent(Inicio.this, AcercaDeActivity.class);
                startActivity(intent);
            }
        });
    }
}