package com.example.parce1lara;

import androidx.appcompat.app.AppCompatActivity;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText Contratxt,Usertxt ;
    Button Logintxt;
    TextView TxtConteo;
    CountDownTimer countDownTimer;
    CheckBox GuardarCredChk;
    int intentos = 0;
    CheckBox VerPassChk;
    SharedPreferences sharedPreferences;

    private Map<String, String> usuarios = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Usertxt = findViewById(R.id.Usertxt);
        Contratxt = findViewById(R.id.Contratxt);
        Logintxt = findViewById(R.id.Logintxt);
        TxtConteo = findViewById(R.id.TxtConteo);
        GuardarCredChk= findViewById(R.id.Texttxt);

        sharedPreferences = getSharedPreferences("com.canales.ejercicio2", MODE_PRIVATE);

        // Agrega usuarios y contraseñas válidos a la lista
        usuarios.put("Dania", "Lara123");
        usuarios.put("newUser", "newPass");

        //Cargar datos guardados
        String saveUser=sharedPreferences.getString("user",null);
        String savePass=sharedPreferences.getString("pass",null);
        if(saveUser!=null && savePass!=null){
            Usertxt.setText(saveUser);
            Contratxt.setText(savePass);
            GuardarCredChk.setChecked(true);

        }

        Logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IniciarSeccion();
            }
        });
        GuardarCredChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    String user = Usertxt.getText().toString();
                    String pass = Contratxt.getText().toString();
                    if(!user.isEmpty() && !pass.isEmpty()){
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putString("user",user);
                        editor.putString("pass",pass);
                        editor.apply();
                    }else{
                        Toast.makeText(MainActivity.this, "ingrese sus credenciales",Toast.LENGTH_SHORT).show();
                        GuardarCredChk.setChecked(false);
                    }
                }else{
                    //Limpiar las Credenciales
                    limpiarCredenciales();
                }

            }
        });

    }
    private void limpiarCredenciales() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("user"); // Elimina la entrada "user" de las SharedPreferences
        editor.remove("pass"); // Elimina la entrada "pass" de las SharedPreferences
        editor.apply();
    }
    private void IniciarSeccion() {
        String user = Usertxt.getText().toString();
        String pass = Contratxt.getText().toString();

        //un usuario
        //  if ("roberto".equals(user) && "canales123".equals(pass))

        // más de un usuario
        //  if(usuarios.containsKey(user) && usuarios.get(user).equals(pass))
        if (usuarios.containsKey(user) && usuarios.get(user).equals(pass)) {
            Toast.makeText(this, "Inicio de Sesión exitoso!", Toast.LENGTH_SHORT).show();
            intentos = 0;
            // Nuevo codigo
            Intent intent= new Intent(MainActivity.this,Inicio.class);
            intent.putExtra("usuario",user);

            startActivity(intent);
        } else {
            intentos++;
            if (intentos >= 3) {

                Toast.makeText(this, "Se supero el numero de intentos", Toast.LENGTH_SHORT).show();
                Logintxt.setEnabled(false);
                IniciarConteo();
                //              new Handler().postDelayed(new Runnable() {
                //                @Override
                //              public void run() {
                //                BtnLogin.setEnabled(true);
                //              intentos = 0;
                //        }
                //  },5000);


            } else {
                Toast.makeText(this, "Usuario o Contraseña es incorrectos.  Intentos" + intentos + " de 3", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void IniciarConteo() {

        TxtConteo.setVisibility(View.VISIBLE);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TxtConteo.setText(String.valueOf(millisUntilFinished / 1000) + "s restantes");

            }

            @Override
            public void onFinish() {
                TxtConteo.setVisibility(View.GONE);
                Logintxt.setEnabled(true);
                intentos= 0;
            }
        }.start();
    }

}

