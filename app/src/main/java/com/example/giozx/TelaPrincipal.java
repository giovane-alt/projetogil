package com.example.giozx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaPrincipal extends AppCompatActivity {

    private Button bt_deslogar;
    private TextView textNomeUsuario, textNomeEmailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_principal);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        IniciarComponentes();

        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String nome = preferences.getString("nomeUsuario", "Giovane Rodrigo");
        String email = preferences.getString("emailCorreto", "giovanerodrigo43@gmail.com");

        if (textNomeUsuario != null) {
            textNomeUsuario.setText(nome);
        }
        if (textNomeEmailUsuario != null) {
            textNomeEmailUsuario.setText(email);
        }

        bt_deslogar.setOnClickListener(v -> {
            Intent intent = new Intent(TelaPrincipal.this, FormLogin.class);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void IniciarComponentes() {
        bt_deslogar = findViewById(R.id.bt_deslogar);
        textNomeUsuario = findViewById(R.id.textNomeUsuario);
        textNomeEmailUsuario = findViewById(R.id.textNomeEmailUsuario);
    }
}
