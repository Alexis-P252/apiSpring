package com.api2.api2.models.services;

import com.api2.api2.entities.Usuario;

import java.util.List;

public interface IUsuarioService {
    public List<Usuario> findAll();

    public Usuario findById(Long id);

    public Usuario findByEmail(String email);

    public Usuario save(Usuario user);
    public void deleteById(Long id);

    public boolean verifyLogin(String email, String password);
}
