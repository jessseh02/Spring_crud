package br.com.catolica.springcrud.Repository;

import br.com.catolica.springcrud.model.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class livroRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Livro> listarLivros() {
        String sql = "SELECT * FROM livro";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Livro.class));
    }

    public void adicionarLivro(Livro livro) {
        String sql = "INSERT INTO livro (titulo, autor, ano, genero, editora, numero_paginas) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAno(),
                livro.getGenero(),
                livro.getEditora(),
                livro.getNumeroPaginas()
        );
    }


    public void atualizarLivro(Livro livro) {
        String sql = "UPDATE livro SET titulo = ?, autor = ?, ano = ?, genero = ?, editora = ?, numero_paginas = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAno(),
                livro.getGenero(),
                livro.getEditora(),
                livro.getNumeroPaginas(),
                livro.getId()
        );
    }


    public void deletarLivro(int id) {
        String sql = "DELETE FROM livro WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

