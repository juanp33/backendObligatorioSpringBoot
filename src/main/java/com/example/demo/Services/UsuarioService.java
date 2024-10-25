package com.example.demo.Services;

import com.example.demo.Modelos.Usuario;
import com.example.demo.Repositorios.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Usuario registerUser(String username, String password, String email) {
        if (usuarioRepository.findByUsername(username) != null) {
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setUsername(username);
        newUsuario.setPassword(passwordEncoder.encode(password)); // Encriptar la contraseña
        newUsuario.setEmail(email);
        return usuarioRepository.save(newUsuario);
    }
}