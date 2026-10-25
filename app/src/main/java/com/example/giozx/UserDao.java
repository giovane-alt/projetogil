package com.example.giozx;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void cadastrarUsuario(User usuario);

    @Query("SELECT * FROM usuarios WHERE LOWER(email) = LOWER(:email) AND senha = :senha LIMIT 1")
    User autenticarUsuario(String email, String senha);

    @Query("SELECT * FROM usuarios WHERE LOWER(email) = LOWER(:email) LIMIT 1")
    User buscarPorEmail(String email);

    @Query("SELECT * FROM usuarios")
    List<User> getTodosUsuarios();
}
