package br.com.application.service;

import br.com.application.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void criarTabelaUsuarioSeNaoExistir() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS usuario (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "nome VARCHAR(255) NOT NULL, " +
                "email VARCHAR(255) NOT NULL UNIQUE, " +
                "senha VARCHAR(255) NOT NULL" +
                ")");
    }

    @Transactional
    public Usuario criarUsuario(String nome, String email, String senha) {
        criarTabelaUsuarioSeNaoExistir(); // Garante que a tabela exista

        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, nome, email, senha);

        // Para simular o retorno do usuário criado (semelhante ao JPA),
        // você precisaria consultar o banco de dados após a inserção.
        // Isso é simplificado aqui para o exemplo.
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senha);
        return novoUsuario;
    }
    
    public List<Usuario> getUsuarios() {
    	criarTabelaUsuarioSeNaoExistir();
    	
    	String sql = "SELECT * FROM usuario";
    	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
    	
    	List<Usuario> usuarios = new ArrayList<Usuario>();
    	while(rs.next()) {
    		Usuario novoUsuario = new Usuario();
    		novoUsuario.setId(rs.getLong("id"));
	        novoUsuario.setNome(rs.getString("nome"));
	        novoUsuario.setEmail(rs.getString("email"));
	        novoUsuario.setSenha(rs.getString("senha"));
	        usuarios.add(novoUsuario);
    	}
    	
    	return usuarios;
    }
    
    public Usuario getUsuario() {
    	criarTabelaUsuarioSeNaoExistir();
    	
    	String sql = "SELECT * FROM usuario";
    	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
    	
    	Usuario usuario = new Usuario();
    	while(rs.next()) {
    		usuario.setId(rs.getLong("id"));
    		usuario.setNome(rs.getString("nome"));
    		usuario.setEmail(rs.getString("email"));
    		usuario.setSenha(rs.getString("senha"));
    	}
    	
    	return usuario;
    }

    // Você precisaria implementar métodos para buscar, atualizar e deletar
    // usuários usando JdbcTemplate e RowMapper para mapear os resultados.
}