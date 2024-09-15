package dao;

import model.Filmes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmesDAO {

    private Connection connection;

    // Construtor da classe, que inicializa a conexão com o banco de dados
    public FilmesDAO() {
        try {
            // Estabelece a conexão com o banco de dados PostgreSQL
            connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/CRUD Form Locadora Filmes", "postgres", "root");
        } catch (SQLException e) {
            // Imprime a pilha de erros se a conexão falhar
            e.printStackTrace();
        }
    }

    // Método para inserir um novo filme na tabela "Filmes"
    public void insert(Filmes filme) {
        String sql = "INSERT INTO \"Filmes\" (\"Nome\", \"Gênero\", \"Ano\") VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os parâmetros do comando SQL
            stmt.setString(1, filme.getNome());
            stmt.setString(2, filme.getGenero());
            stmt.setInt(3, filme.getAno());
            // Executa a inserção no banco de dados
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Imprime a pilha de erros se a inserção falhar
            e.printStackTrace();
        }
    }

    // Método para obter um filme pelo ID
    public Filmes get(int id) {
        Filmes filme = null;
        String sql = "SELECT * FROM \"Filmes\" WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define o parâmetro do comando SQL
            stmt.setInt(1, id);
            // Executa a consulta e obtém os resultados
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Cria um objeto Filme com os dados retornados da consulta
                filme = new Filmes(rs.getInt("id"), rs.getString("Nome"), rs.getString("Gênero"), rs.getInt("Ano"));
            }

            // Fecha o ResultSet
            rs.close();
        } catch (SQLException e) {
            // Imprime a pilha de erros se a consulta falhar
            e.printStackTrace();
        }
        return filme; // Retorna o filme encontrado ou null se não houver resultado
    }

    // Método para atualizar um filme existente
    public void update(Filmes filme) {
        String sql = "UPDATE \"Filmes\" SET \"Nome\" = ?, \"Gênero\" = ?, \"Ano\" = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os parâmetros do comando SQL
            stmt.setString(1, filme.getNome());
            stmt.setString(2, filme.getGenero());
            stmt.setInt(3, filme.getAno());
            stmt.setInt(4, filme.getId());
            // Executa a atualização no banco de dados
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Imprime a pilha de erros se a atualização falhar
            e.printStackTrace();
        }
    }

    // Método para excluir um filme pelo ID
    public void delete(int id) {
        String sql = "DELETE FROM \"Filmes\" WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define o parâmetro do comando SQL
            stmt.setInt(1, id);
            // Executa a exclusão no banco de dados
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Imprime a pilha de erros se a exclusão falhar
            e.printStackTrace();
        }
    }

    // Método para obter todos os filmes da tabela "Filmes"
    public List<Filmes> getAll() {
        List<Filmes> filmes = new ArrayList<>();
        String sql = "SELECT * FROM \"Filmes\" ORDER BY id ASC"; // Ordena os resultados pelo campo id de forma ascendente
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Cria um objeto Filme para cada linha retornada e adiciona à lista
                Filmes filme = new Filmes(rs.getInt("id"), rs.getString("Nome"), rs.getString("Gênero"), rs.getInt("Ano"));
                filmes.add(filme);
            }
        } catch (SQLException e) {
            // Imprime a pilha de erros se a consulta falhar
            e.printStackTrace();
        }
        return filmes; // Retorna a lista de filmes
    }
}
