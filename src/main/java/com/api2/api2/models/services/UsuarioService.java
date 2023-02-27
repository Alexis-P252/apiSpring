package com.api2.api2.models.services;

import com.api2.api2.entities.Usuario;
import com.api2.api2.models.dao.IUsuarioDao;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
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

    public Usuario findByEmail(String email){
        return usuarioDao.findByEmail(email);
    }

    @Override
    public Usuario save(Usuario user) {
        return usuarioDao.save(user);
    }

    @Override
    public void deleteById(Long id) {
        usuarioDao.deleteById(id);
    }

    @Override
    public boolean verifyLogin(String email, String password) {

        if(usuarioDao.existsByEmail(email)){
            Usuario u = usuarioDao.findByEmail(email);
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            return argon2.verify(u.getPassword(),password);
        }
        else{
            return false;
        }

    }
}
