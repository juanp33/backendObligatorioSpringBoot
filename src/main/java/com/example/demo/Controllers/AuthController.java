package com.example.demo.Controllers;

import com.example.demo.APIRequest.LoginRequest;
import com.example.demo.APIResponse.LoginResponse;
import com.example.demo.Modelos.Usuario;
import com.example.demo.Services.JugadorService;
import com.example.demo.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class AuthController {



    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JugadorService jugadorService;

    public AuthController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, JugadorService jugadorService) {
        this.usuarioService = usuarioService;
        this.jugadorService=jugadorService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Verificar si el usuario existe
            Optional<Usuario> usuarioOpt = usuarioService.encontrarPorUsername(loginRequest.getUsername());

            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();

                // Verificar la contraseña
                if (passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                    LoginResponse loginResponse = new LoginResponse(
                            "Login exitoso",
                            usuario.getUsername(),
                            usuario.getEmail(),
                            jugadorService.ObtenerMontoUsuario(usuario)
                    );
                    return ResponseEntity.ok(loginResponse);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error durante la autenticación");
        }
    }
}
