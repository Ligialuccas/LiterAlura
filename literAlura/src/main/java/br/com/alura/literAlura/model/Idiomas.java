package br.com.alura.literAlura.model;

public enum Idiomas {

    ESPANHOL("es"),
    FRANCES("fr"),
    INGLES("en"),
    LATIN("la"),
    PORTUGUES("pt");

    private String idiomasGutendex;

    Idiomas(String idiomasGutendex){
        this.idiomasGutendex = idiomasGutendex;
    }

    public static Idiomas fromString(String text){
        for (Idiomas idiomas : Idiomas.values()){
            if (idiomas.idiomasGutendex.equalsIgnoreCase(text)){
                return idiomas;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado para a string fornecida: " + text);
    }

}
