package br.com.alura.literAlura.repository;

import br.com.alura.literAlura.model.Idiomas;
import br.com.alura.literAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Livro findByTitulo(String titulo);

    List<Livro> findByIdioma(Idiomas idioma);

}
