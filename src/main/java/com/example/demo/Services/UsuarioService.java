package com.example.demo.Services;

import com.example.demo.Components.JwtUtils;
import com.example.demo.Modelos.Usuario;
import com.example.demo.Repositorios.UsuarioRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public Usuario registerUser(String username, String password, String email) {
        Usuario existingUsuario = usuarioRepository.findByUsername(username);

        if (existingUsuario != null) {
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setUsername(username);
        newUsuario.setPassword(passwordEncoder.encode(password)); // Encriptar contraseña
        newUsuario.setEmail(email);
        return usuarioRepository.save(newUsuario);
    }

    public String loginUser(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null || !passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }
        return jwtUtils.generateJwtToken(username);
    }
}
