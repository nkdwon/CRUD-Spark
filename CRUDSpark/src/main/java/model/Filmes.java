package model;

public class Filmes {

    // Atributos da classe Filmes
    private int Id; // ID do filme
    private String Nome; // Nome do filme
    private String Genero; // Gênero do filme
    private int Ano; // Ano de lançamento do filme

    // Construtor padrão da classe, inicializa os atributos com valores padrão
    public Filmes() {
        this.Id = -1; // Valor padrão para o ID, indicando que ainda não foi definido
        this.Nome = ""; // Valor padrão para o nome, indicando que está vazio
        this.Genero = ""; // Valor padrão para o gênero, indicando que está vazio
        this.Ano = -1; // Valor padrão para o ano, indicando que ainda não foi definido
    }

    // Construtor da classe, inicializa os atributos com os valores fornecidos
    public Filmes(int id, String nome, String genero, int ano) {
        setId(id);
        setNome(nome);
        setGenero(genero);
        setAno(ano);
    }

    // Método para obter o ID do filme
    public int getId() {
        return Id;
    }

    // Método para definir o ID do filme
    public void setId(int id) {
        Id = id;
    }

    // Método para obter o nome do filme
    public String getNome() {
        return Nome;
    }

    // Método para definir o nome do filme
    public void setNome(String nome) {
        Nome = nome;
    }

    // Método para obter o gênero do filme
    public String getGenero() {
        return Genero;
    }

    // Método para definir o gênero do filme
    public void setGenero(String genero) {
        Genero = genero;
    }

    // Método para obter o ano de lançamento do filme
    public int getAno() {
        return Ano;
    }

    // Método para definir o ano de lançamento do filme
    public void setAno(int ano) {
        Ano = ano;
    }

    // Método para retornar uma representação em String do objeto Filmes
    @Override
    public String toString() {
        return "Filmes [Id=" + Id + ", Nome=" + Nome + ", Gênero=" + Genero + ", Ano=" + Ano + "]";
    }
}
