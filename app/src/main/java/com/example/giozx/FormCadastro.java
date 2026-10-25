package com.example.giozx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FormCadastro extends AppCompatActivity {

    private EditText editNome, editEmail, editSenha;
    private AppCompatButton btCadastrar;
    private AppDatabase db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_cadastro);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = AppDatabase.getInstance(this);
        IniciarComponentes();

        btCadastrar.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String senha = editSenha.getText().toString().trim();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(FormCadastro.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(FormCadastro.this, "Digite um e-mail válido!", Toast.LENGTH_SHORT).show();
                return;
            }

            executor.execute(() -> {
                User usuarioExistente = db.userDao().buscarPorEmail(email);

                if (usuarioExistente != null) {
                    runOnUiThread(() -> Toast.makeText(FormCadastro.this, "E-mail já cadastrado!", Toast.LENGTH_SHORT).show());
                } else {
                    User novoUsuario = new User(nome, email, senha);
                    db.userDao().cadastrarUsuario(novoUsuario);

                    SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("emailCorreto", email);
                    editor.putString("senhaCorreta", senha);
                    editor.putString("nomeUsuario", nome);
                    editor.apply();

                    runOnUiThread(() -> {
                        Toast.makeText(FormCadastro.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FormCadastro.this, FormLogin.class);
                        startActivity(intent);
                        finish();
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
        editNome = findViewById(R.id.edit_nome);
        editEmail = findViewById(R.id.edit_email);
        editSenha = findViewById(R.id.edit_senha);
        btCadastrar = findViewById(R.id.bt_cadastrar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
