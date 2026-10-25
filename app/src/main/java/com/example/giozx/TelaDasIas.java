package com.example.giozx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaDasIas extends AppCompatActivity {

    private ImageView ia1, ia2, ia3, ia4;
    private ImageView bgIa1, bgIa2, bgIa3, bgIa4;
    private ImageView btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tela_das_ias);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        IniciarComponentes();

        // Grok (img_3 / ia_1)
        if (ia1 != null) ia1.setOnClickListener(v -> abrirSiteIa("https://grok.com"));
        if (bgIa1 != null) bgIa1.setOnClickListener(v -> abrirSiteIa("https://grok.com"));

        // ChatGPT (img_4 / ia_2)
        if (ia2 != null) ia2.setOnClickListener(v -> abrirSiteIa("https://chatgpt.com"));
        if (bgIa2 != null) bgIa2.setOnClickListener(v -> abrirSiteIa("https://chatgpt.com"));

        // Gemini (img_5 / ia_3)
        if (ia3 != null) ia3.setOnClickListener(v -> abrirSiteIa("https://claude.ai/login?returnTo=%2F%3Fredirect%3Dclaude.com%26via%3Dcookie"));
        if (bgIa3 != null) bgIa3.setOnClickListener(v -> abrirSiteIa("https://claude.ai/login?returnTo=%2F%3Fredirect%3Dclaude.com%26via%3Dcookie"));

        // Copilot (img_6 / ia_4)
        if (ia4 != null) ia4.setOnClickListener(v -> abrirSiteIa("https://gemini.google.com/app"));
        if (bgIa4 != null) bgIa4.setOnClickListener(v -> abrirSiteIa("https://gemini.google.com/app"));

        // Abrir Perfil do Usuário
        if (btnPerfil != null) {
            btnPerfil.setOnClickListener(v -> {
                Intent intent = new Intent(TelaDasIas.this, TelaPrincipal.class);
                startActivity(intent);
            });
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void abrirSiteIa(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Não foi possível abrir o link", Toast.LENGTH_SHORT).show();
        }
    }

    private void IniciarComponentes() {
        ia1 = findViewById(R.id.ia_1);
        ia2 = findViewById(R.id.ia_2);
        ia3 = findViewById(R.id.ia_3);
        ia4 = findViewById(R.id.ia_4);

        bgIa1 = findViewById(R.id.bg_ia_1);
        bgIa2 = findViewById(R.id.bg_ia_2);
        bgIa3 = findViewById(R.id.bg_ia_3);
        bgIa4 = findViewById(R.id.bg_ia_4);

        btnPerfil = findViewById(R.id.btn_perfil);
    }
}
