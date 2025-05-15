package br.com.application.repository;

import br.com.application.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UsuarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Usuario> usuarioRowMapper = (rs, rowNum) -> {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        return usuario;
    };

    public Usuario save(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, usuario.getNome(), usuario.getEmail(), usuario.getSenha());
        String sqlQuery = "SELECT LAST_INSERT_ID()";
        Long id = jdbcTemplate.queryForObject(sqlQuery, Long.class);
        usuario.setId(id);
        return usuario;
    }

    public Usuario findById(Long id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        List<Usuario> usuarios = jdbcTemplate.query(sql, usuarioRowMapper, id);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuario";
        return jdbcTemplate.query(sql, usuarioRowMapper);
    }

    public Usuario findByEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        List<Usuario> usuarios = jdbcTemplate.query(sql, usuarioRowMapper, email);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    public void update(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        jdbcTemplate.update(sql, usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
