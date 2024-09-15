package app;

import static spark.Spark.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import service.FilmesService;

public class Main {

    // Instância do serviço FilmesService que será usado para lidar com as operações CRUD
    private static FilmesService filmeService = new FilmesService();

    public static void main(String[] args) {
        port(4567); // Define a porta na qual a aplicação será executada

        // Rota para servir o formulário HTML na raiz do servidor ("/")
        get("/", (request, response) -> {
            response.type("text/html"); // Define o tipo de conteúdo da resposta como HTML
            return renderHTMLForm(); // Carrega o HTML do arquivo
        });

        // Rota para criar um novo filme
        post("/filmes", (request, response) -> filmeService.add(request, response));

        // Rota para listar um único filme baseado no ID
        post("/filmes/get", (request, response) -> filmeService.get(request, response));

        // Rota para atualizar um filme existente
        post("/filmes/update", (request, response) -> filmeService.update(request, response));

        // Rota para remover um filme baseado no ID
        post("/filmes/delete", (request, response) -> filmeService.remove(request, response));

        // Rota para listar todos os filmes
        post("/filmes/all", (request, response) -> filmeService.getAll(request, response));
    }

    // Método para carregar o HTML do arquivo
    private static String renderHTMLForm() {
        try {
            // Lê o conteúdo do arquivo HTML localizado em src/main/resources/formulario.html
            return new String(Files.readAllBytes(Paths.get("src/main/resources/formulario.html")));
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o stack trace se houver uma exceção ao ler o arquivo
            return "Erro ao carregar o formulário."; // Retorna uma mensagem de erro se o arquivo não puder ser carregado
        }
    }
}
