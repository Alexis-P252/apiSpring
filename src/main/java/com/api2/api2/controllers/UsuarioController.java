package com.api2.api2.controllers;

import com.api2.api2.entities.Usuario;
import com.api2.api2.models.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api")

public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuario> findAll(){
        return usuarioService.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> findById(@PathVariable(value="id") Long id) {

        Usuario user = null;
        Map<String,Object> response = new HashMap<>();
        try {
            user = usuarioService.findById(id);

        } catch( DataAccessException e) {
            response.put("msg","Error en el acceso a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(user == null) {
            response.put("msg","No existe un usuario con id = ".concat(id.toString()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<Usuario>(user, HttpStatus.OK);

    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> create(@RequestBody Usuario cliente) {

        Usuario newUsuario = null;
        Map<String,Object> response = new HashMap<>();
        try {
            newUsuario = usuarioService.save(cliente);
        }catch(DataAccessException e) {
            response.put("msg","Error al intentar insertar un usuario");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("msg", "El usuario ha sido creado correctamente");
        response.put("usuario", newUsuario);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> update(@RequestBody Usuario cliente,@PathVariable(value="id")Long id ) {

        Usuario usuarioActual = usuarioService.findById(id);
        Usuario usuarioActualizado = null;
        Map<String,Object> response = new HashMap<>();

        if(usuarioActual == null) {
            response.put("msg","No existe un usuario con id = ".concat(id.toString()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }

        usuarioActual.setApellido(cliente.getApellido());
        usuarioActual.setNombre(cliente.getNombre());
        usuarioActual.setEmail(cliente.getEmail());

        try {
            usuarioActualizado = usuarioService.save(usuarioActual);
        }catch(DataAccessException e) {
            response.put("msg","Error al intentar modificar el usuario");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("msg", "Usuario actualizado correctamente");
        response.put("usuario", usuarioActualizado);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/usaurios/{id}")
    public ResponseEntity<?> delete(@PathVariable(value="id") Long id) {

        Map<String,Object> response = new HashMap<>();

        try {
            usuarioService.deleteById(id);

        }catch(DataAccessException e) {
            response.put("msg","Error al intentar eliminar el usuario");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("msg", "Usuario eliminado con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }

}
