package br.com.alura.literAlura.dto;

import br.com.alura.literAlura.model.Idiomas;

import java.io.Serializable;

/**
 * DTO for {@link br.com.alura.literAlura.model.Livro}
 */
public record LivroDto(Long id,
                       String titulo,
                       Idiomas idioma,
                       Integer downloads,
                       String sinopse,
                       AutorDto autor) implements Serializable {
}