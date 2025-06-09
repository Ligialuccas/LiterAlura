package br.com.alura.literAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Idiomas idioma;
    private Integer downloads;
    @Column(columnDefinition = "TEXT")
    private String sinopse;

    @ManyToOne
    @JoinColumn(name = "autor_id")  // Cria a coluna 'autor_id' na tabela 'livros'
    private Autor autor;

    public Livro() {

    }

    public Livro(String titulo, Idiomas idioma, Integer downloads, String sinopse, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.downloads = downloads;
        this.sinopse = sinopse;
        this.autor = autor;
    }

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.downloads = dadosLivro.downloads();
        if (!dadosLivro.autores().isEmpty()){
            DadosAutor dadosAutor = dadosLivro.autores().get(0);
            this.autor = new Autor(dadosAutor.nome(),dadosAutor.nascimento(),dadosAutor.falecimento());
        }
        this.idioma = Idiomas.fromString(dadosLivro.idiomas().get(0));
        if (!dadosLivro.sinopses().isEmpty()) {
            this.sinopse = dadosLivro.sinopses().get(0).trim();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idiomas getIdioma() {
        return idioma;
    }

    public void setIdioma(Idiomas idioma) {
        this.idioma = idioma;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "----------Livro----------" +
                "\n titulo: " + titulo +
                "\n Autor: " + autor.getNome() +
                "\n Idioma: " + idioma +
                "\n Número de downloads: " + downloads +
                "\n sinopse: " + sinopse +
                "\n-------------------------\n";
    }
}


