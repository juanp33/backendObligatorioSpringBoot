package com.example.demo.Controllers;

import com.example.demo.APIRequest.LoginRequest;
import com.example.demo.Modelos.Usuario;
import com.example.demo.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Verificar si el usuario existe
            if (usuarioRepository.existsByUsername(loginRequest.getUsername())) {
                // Obtener el usuario de la base de datos
                Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername());

                // Si llegaste aquí, el usuario existe y puedes verificar la contraseña.
                if (passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                    return ResponseEntity.ok("Login exitoso");
                }

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error durante la autenticación");
        }
    }

}