package br.com.catolica.springcrud.Repository;

import br.com.catolica.springcrud.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper para mapear ResultSet para Categoria
    private final RowMapper<Categoria> categoriaRowMapper = new RowMapper<Categoria>() {
        @Override
        public Categoria mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            Categoria categoria = new Categoria();
            categoria.setId(rs.getLong("id"));
            categoria.setNome(rs.getString("nome"));
            categoria.setDescricao(rs.getString("descricao"));
            return categoria;
        }
    };

    /**
     * CREATE - Salvar nova categoria
     */
    public Categoria save(Categoria categoria) {
        String sql = "INSERT INTO categoria (nome, descricao) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getDescricao());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            categoria.setId(key.longValue());
        }
        return categoria;
    }

    /**
     * READ - Buscar todas as categorias
     */
    public List<Categoria> findAll() {
        String sql = "SELECT * FROM categoria ORDER BY nome";
        return jdbcTemplate.query(sql, categoriaRowMapper);
    }

    /**
     * READ - Buscar categoria por ID
     */
    public Optional<Categoria> findById(Long id) {
        String sql = "SELECT * FROM categoria WHERE id = ?";
        List<Categoria> categorias = jdbcTemplate.query(sql, categoriaRowMapper, id);
        return categorias.isEmpty() ? Optional.empty() : Optional.of(categorias.get(0));
    }

    /**
     * UPDATE - Atualizar categoria
     */
    public boolean update(Long id, Categoria categoria) {
        String sql = "UPDATE categoria SET nome = ?, descricao = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, categoria.getNome(), categoria.getDescricao(), id);
        return rowsAffected > 0;
    }

    /**
     * DELETE - Excluir categoria por ID
     */
    public boolean delete(Long id) {
        String sql = "DELETE FROM categoria WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    /**
     * Verificar se categoria existe
     */
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM categoria WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}