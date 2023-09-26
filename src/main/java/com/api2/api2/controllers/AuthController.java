package com.api2.api2.controllers;

import com.api2.api2.entities.Usuario;
import com.api2.api2.models.services.UsuarioService;
import com.api2.api2.utils.JWTUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// prueba juansito perez

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ObjectNode JSONObject){

        Map<String,Object> response = new HashMap<>();
        String email = null;
        String password = null;

        try{
            email = JSONObject.get("email").asText();
            password = JSONObject.get("password").asText();
        }catch (Exception e){
            response.put("msg", "Error al iniciar sesion!");
            response.put("error", "Debes indicar el email y la password");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }


        if( usuarioService.verifyLogin(email, password)){

            Usuario u = usuarioService.findByEmail(email);
            String token = jwtUtil.create(String.valueOf(u.getId()), u.getEmail());

            response.put("token",token);
            response.put("id", u.getId());
            response.put("email", u.getEmail());
            response.put("msg", "Login correcto!");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

        }
        else{
            response.put("msg", "Error al iniciar sesion!");
            response.put("error", "Las credenciales no coinciden");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
