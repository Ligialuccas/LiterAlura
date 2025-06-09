package br.com.alura.literAlura.service;

import br.com.alura.literAlura.dto.AutorDto;
import br.com.alura.literAlura.dto.LivroDto;
import br.com.alura.literAlura.model.*;
import br.com.alura.literAlura.repository.AutorRepository;
import br.com.alura.literAlura.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private Scanner leitura = new Scanner(System.in);

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private final String ENDERECOMAISBAIXADOS = "https://gutendex.com/books/?sort=popular&page=1";

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private List<Livro> livros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();

    public void buscarLivroApi() {
        DadosLivro dados = getDadosLivro();

        if (dados != null) {
            DadosAutor dadosAutor = dados.autores().get(0);
            Optional<Autor> autorOptional = autorRepository.findByNome(dadosAutor.nome());

            Autor autor = autorOptional.orElseGet(() ->{
                Autor novoAutor =new Autor(dadosAutor.nome(),dadosAutor.nascimento(),dadosAutor.falecimento());
                return autorRepository.save(novoAutor);
            });
            Livro livro = new Livro();
            livro.setTitulo(dados.titulo());
            livro.setDownloads(dados.downloads());
            livro.setAutor(autor);
            livro.setIdioma(Idiomas.fromString(dados.idiomas().get(0)));

            if (!dados.sinopses().isEmpty()) {
                livro.setSinopse(dados.sinopses().get(0).trim());
            }

            livroRepository.save(livro);
            System.out.println(dados);
        }else{
            System.out.println("Nenhum livro encontrado.");
        }
    }

    private DadosLivro getDadosLivro() {
        System.out.println("Digite o nome do Livro que você deseja procurar: ");
        var nomeLivro = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
        var dadosResultado = conversor.obterDados(json, DadosResultado.class);
        if (!dadosResultado.results().isEmpty()) {
            return dadosResultado.results().get(0);
        } else {
            return null;
        }
    }


    private List<LivroDto> converteDados(List<Livro> livros){
        return livros.stream()
                .map(l -> {
                    Autor autor = l.getAutor();
                    AutorDto autorDto = new AutorDto(
                            autor.getId(),
                            autor.getNome(),
                            autor.getNascimento(),
                            autor.getFalecimento());

                    return new LivroDto(
                            l.getId(),
                            l.getTitulo(),
                            l.getIdioma(),
                            l.getDownloads(),
                            l.getSinopse(),
                            autorDto
                    );
                })
                .collect(Collectors.toList());
    }


    public void listarLivros() {
        livros = livroRepository.findAll();
        livros.stream()
                .forEach(System.out::println);

    }


    public void listarAutores() {
        autores = autorRepository.findAll();
        autores.stream().forEach(System.out::println);
    }

    public void listarAutoresVivosPorAno() {
        autores = autorRepository.findAll();
        System.out.print("Digite o ano para verificar autores vivos: ");
        int ano = leitura.nextInt();
        leitura.nextLine();

        boolean encontrou = false;
        for (Autor autor : autores) {
            Integer nascimento = autor.getNascimento();
            Integer falecimento = autor.getFalecimento(); // pode ser null

            boolean vivoNesseAno = (nascimento != null && ano >= nascimento) &&
                    (falecimento == null || ano <= falecimento);

            if (vivoNesseAno) {
                System.out.println(autor.toString());
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum autor estava vivo no ano " + ano + ".");
        }
    }

    public void listarLivrosPorIdioma() {

        System.out.println("""
                Insira o idioma que deseja realizar a busca:
                ESPANHOL
                FRANCES
                INGLES
                LATIN
                PORTUGUES
                """);
        String idiomaStr = leitura.nextLine().toUpperCase();

        try {
            Idiomas idiomaEnum = Idiomas.valueOf(idiomaStr);
            List<Livro> livrosPorIdioma = livroRepository.findByIdioma(idiomaEnum);

            if (livrosPorIdioma.isEmpty()) {
                System.out.println("Nenhum livro encontrado no idioma: " + idiomaEnum);
            } else {
                livrosPorIdioma.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma inválido! Tente novamente com uma das opções fornecidas.");
        }


    }

    public void top10MaisBaixados() {
        var json = consumoApi.obterDados(ENDERECOMAISBAIXADOS);

        var dadosResultado = conversor.obterDados(json, DadosResultado.class);
        List<DadosLivro> top10Livros = dadosResultado.results().stream()
                .limit(10)
                .collect(Collectors.toList());

        top10Livros.forEach(l -> System.out.println(
                "Título: " + l.titulo() +
                        ", Downloads: " + l.downloads() +
                        ", Idiomas: " + l.idiomas() +
                        ", Autores: " + l.autores().stream()
                        .map(a -> a.nome())
                        .collect(Collectors.joining(", "))
        ));


    }
}
