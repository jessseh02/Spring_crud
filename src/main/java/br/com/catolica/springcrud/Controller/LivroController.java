package br.com.catolica.springcrud.Controller;

import br.com.catolica.springcrud.Model.Livro;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    // Lista em memória
    private List<Livro> livros = new ArrayList<>();

    /**
     * CREATE - Adiciona um novo livro
     * Endpoint: POST /livros
     */
    @PostMapping
    public String adicionarLivro(@RequestBody Livro livro) {
        for (Livro l : livros) {
            if (l.getId() == livro.getId()) {
                return "Erro: Já existe um livro com esse ID!";
            }
        }
        livros.add(livro);
        return "Livro adicionado com sucesso!";
    }

    /**
     * READ - Retorna todos os livros
     * Endpoint: GET /livros
     */
    @GetMapping
    public List<Livro> listarLivros() {
        return livros;
    }

    /**
     * READ - Retorna um livro pelo ID
     * Endpoint: GET /livros/{id}
     */
    @GetMapping("/{id}")
    public Livro buscarPorId(@PathVariable int id) {
        return livros.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
    }

    /**
     * READ - Busca por título
     * Endpoint: GET /livros/busca?nome=valor
     */
    @GetMapping("/busca")
    public List<Livro> buscarPorNome(@RequestParam String nome) {
        return livros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(nome.toLowerCase()))
                .toList();
    }

    /**
     * UPDATE - Atualiza livro por ID
     * Endpoint: PUT /livros/{id}
     */
    @PutMapping("/{id}")
    public String atualizarLivro(@PathVariable int id, @RequestBody Livro livroAtualizado) {
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getId() == id) {
                livros.set(i, livroAtualizado);
                return "Livro atualizado com sucesso!";
            }
        }
        return "Erro: Livro não encontrado.";
    }

    /**
     * DELETE - Remove livro por ID
     * Endpoint: DELETE /livros/{id}
     */
    @DeleteMapping("/{id}")
    public String deletarLivro(@PathVariable int id) {
        boolean removido = livros.removeIf(l -> l.getId() == id);
        if (removido) return "Livro removido com sucesso!";
        return "Erro: Livro não encontrado.";
    }
}
