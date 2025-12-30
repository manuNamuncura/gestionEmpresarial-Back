package com.gestion.getionempresarial.controller;

import com.gestion.getionempresarial.model.Usuario;
import com.gestion.getionempresarial.repository.UsuarioRepository;
import com.gestion.getionempresarial.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public String registrar(@RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return "Usuario registrado con éxito";
    }

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        return usuarioRepository.findByUsername(usuario.getUsername())
                .filter(u -> passwordEncoder.matches(usuario.getPassword(), u.getPassword()))
                .map(u -> jwtUtils.generateJwtToken(u.getUsername()))
                .orElse("Credenciales inválidas");
    }
}
