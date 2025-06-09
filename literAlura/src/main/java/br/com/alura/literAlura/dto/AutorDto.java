package br.com.alura.literAlura.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link br.com.alura.literAlura.model.Autor}
 */
public record AutorDto(Long id,
                       String nome,
                       Integer nascimento,
                       Integer falecimento) implements Serializable {
}