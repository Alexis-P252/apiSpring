package com.api2.api2.models.dao;

import com.api2.api2.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
}
