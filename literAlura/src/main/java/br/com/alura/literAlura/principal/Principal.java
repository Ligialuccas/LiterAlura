package br.com.alura.literAlura.principal;

import br.com.alura.literAlura.model.*;
import br.com.alura.literAlura.repository.AutorRepository;
import br.com.alura.literAlura.repository.LivroRepository;
import br.com.alura.literAlura.service.LivroService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private List<DadosLivro> dadosLivro = new ArrayList<>();

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;
    private LivroService livroService;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository,LivroService livroService){
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.livroService = livroService;
    }

    public void exibeMenu(){

        var opcao = -1;
        while (opcao != 0){
            var menu = """
                    ----------------------------------------------
                    Escolha o número de sua opção:
                    1 - Buscar livro pelo titulo
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    6 - Top 10 livros mais baixados
                    
                    0 - Sair
                    ----------------------------------------------
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao){
                case 1:
                    livroService.buscarLivroApi();
                    break;
                case 2:
                    livroService.listarLivros();
                    break;
                case 3:
                    livroService.listarAutores();
                    break;
                case 4:
                    livroService.listarAutoresVivosPorAno();
                    break;
                case 5:
                    livroService.listarLivrosPorIdioma();
                    break;
                case 6:
                    livroService.top10MaisBaixados();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }


}
