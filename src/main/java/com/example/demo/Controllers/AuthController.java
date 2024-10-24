package com.example.demo.Controllers;

import com.example.demo.APIRequest.JwtResponse;
import com.example.demo.APIRequest.LoginRequest;
import com.example.demo.APIRequest.RegisterRequest;

import com.example.demo.Services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")


public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest,  @RequestHeader(value = "X-API-KEY", required = true) String apiKey) {
        if (!"NacionalNacional".equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Clave API inválida");
        }

        try {
            usuarioService.registerUser(
                    registerRequest.getUsername(),
                    registerRequest.getPassword(),
                    registerRequest.getEmail()
            );
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, @RequestHeader(value = "X-API-KEY", required = true) String apiKey) {
        if (!"NacionalNacional".equals(apiKey)) {
            System.out.println("entro aca");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Clave API inválida");
        }
        try {
            System.out.println("Llego a usuarios");
            String token = usuarioService.loginUser(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );
            System.out.println("creo usuarios");
            return ResponseEntity.status(401).body("Credenciales correctas: ");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciales incorrectas: " + e.getMessage());
        }
    }
}
