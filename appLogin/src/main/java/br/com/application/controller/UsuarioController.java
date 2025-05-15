package br.com.application.controller;

import br.com.application.model.Usuario;
import br.com.application.service.UsuarioService;

import java.util.List;

import org.apache.juli.JsonFormatter.JSONFilter;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.util.JSONPObject;

import org.springframework.dao.DataIntegrityViolationException;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/teste")
    public ResponseEntity<List<Usuario>> getUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.getUsuarios();
            
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw e; // Deixe o GlobalExceptionHandler tratar a exceção
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        try {
            Usuario novoUsuario = usuarioService.criarUsuario(usuarioRequest.getNome(), usuarioRequest.getEmail(), usuarioRequest.getSenha()); // Chamada correta
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw e; // Deixe o GlobalExceptionHandler tratar a exceção
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    static class UsuarioRequest { // Renomeado para UsuarioRequest para consistência

        private String nome;
        private String email;
        private String senha;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }
}