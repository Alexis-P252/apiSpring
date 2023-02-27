package com.api2.api2.models.services;

import com.api2.api2.entities.Usuario;
import com.api2.api2.models.dao.IUsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioDao.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario user) {
        return usuarioDao.save(user);
    }

    @Override
    public void deleteById(Long id) {
        usuarioDao.deleteById(id);
    }
}
