package com.example.giozx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FormLogin extends AppCompatActivity {

    private TextView text_tela_cadastro;
    private AppCompatButton bt_entrar;
    private EditText editEmail, editSenha;
    private AppDatabase db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = AppDatabase.getInstance(this);
        IniciarComponentes();

        text_tela_cadastro.setOnClickListener(v -> {
            Intent intent = new Intent(FormLogin.this, FormCadastro.class);
            startActivity(intent);
        });

        bt_entrar.setOnClickListener(v -> {
            String emailDigitado = editEmail.getText().toString().trim();
            String senhaDigitada = editSenha.getText().toString();

            if (emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {
                Toast.makeText(FormLogin.this,
                        "Preencha todos os campos!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailDigitado).matches()) {
                Toast.makeText(FormLogin.this,
                        "Digite um e-mail válido!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            executor.execute(() -> {
                User usuario = db.userDao().autenticarUsuario(emailDigitado, senhaDigitada);

                SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String emailSaved = preferences.getString("emailCorreto", "giovanerodrigo43@gmail.com");
                String senhaSaved = preferences.getString("senhaCorreta", "123456");

                boolean loginValido = (usuario != null) ||
                        (emailDigitado.equalsIgnoreCase(emailSaved) && senhaDigitada.equals(senhaSaved));

                if (loginValido) {
                    String nome = (usuario != null) ? usuario.getNome() : preferences.getString("nomeUsuario", "Giovane Rodrigo");
                    String email = (usuario != null) ? usuario.getEmail() : emailSaved;

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("nomeUsuario", nome);
                    editor.putString("emailCorreto", email);
                    editor.apply();

                    runOnUiThread(() -> {
                        Toast.makeText(FormLogin.this,
                                "Login realizado com sucesso!",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(FormLogin.this, TelaDasIas.class);
                        startActivity(intent);
                        finish();
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(FormLogin.this,
                                "E-mail ou senha incorretos!",
                                Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void IniciarComponentes() {
        text_tela_cadastro = findViewById(R.id.text_tela_cadastro);
        bt_entrar = findViewById(R.id.bt_entrar);
        editEmail = findViewById(R.id.edit_email);
        editSenha = findViewById(R.id.edit_senha);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
