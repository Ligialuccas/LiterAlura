package br.com.alura.literAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private Integer nascimento;
    private Integer falecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(){}


    public Autor(String nome, Integer nascimento, Integer falecimento, List<Livro> livros) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.falecimento = falecimento;
    }

    public Autor(String nome, Integer nascimento, Integer falecimento) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.falecimento = falecimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNascimento() {
        return nascimento;
    }

    public void setNascimento(Integer nascimento) {
        this.nascimento = nascimento;
    }

    public Integer getFalecimento() {
        return falecimento;
    }

    public void setFalecimento(Integer falecimento) {
        this.falecimento = falecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        StringBuilder livrosTitulos = new StringBuilder();
        for (Livro livro : livros) {
            livrosTitulos.append("\n - ").append(livro.getTitulo());
        }
        return "-----------------------------" +
                "\nAutor: " + nome +
                "\nNascimento: " + nascimento +
                "\nFalecimento: " + falecimento +
                "\nLivros: " + livrosTitulos;
    }
}
