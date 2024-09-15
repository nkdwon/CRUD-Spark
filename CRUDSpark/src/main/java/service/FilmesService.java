package service;

import dao.FilmesDAO;
import model.Filmes;
import spark.Request;
import spark.Response;

import java.util.List;

public class FilmesService {

    private FilmesDAO filmesDAO;

    // Construtor da classe FilmesService
    public FilmesService() {
        filmesDAO = new FilmesDAO(); // Inicializa o DAO para operações no banco de dados
    }

    // Adiciona um novo filme
    public Object add(Request request, Response response) {
        String nome = request.queryParams("nome");
        String genero = request.queryParams("genero");
        int ano = Integer.parseInt(request.queryParams("ano"));

        Filmes filme = new Filmes(-1, nome, genero, ano);
        filmesDAO.insert(filme); // Insere o novo filme no banco de dados

        response.status(201); // Status HTTP 201: Created
        return "<html><body><div class='message'>Filme cadastrado com sucesso!</div></body></html>";
    }

    // Obtém detalhes de um filme específico pelo ID
    public String get(Request request, Response response) {
        String idParam = request.queryParams("id");

        // Verifica se o ID foi fornecido
        if (idParam == null || idParam.isEmpty()) {
            return "<html><body><div class='error'>Erro: ID não fornecido.</div></body></html>";
        }

        try {
            int id = Integer.parseInt(idParam);
            Filmes filme = filmesDAO.get(id); // Obtém o filme pelo ID

            if (filme == null) {
                return "<html><body><div class='error'>Erro: Filme não encontrado.</div></body></html>";
            }

            // Retorna os detalhes do filme em HTML
            StringBuilder retorno = new StringBuilder();
            retorno.append("<html><head><style>");
            retorno.append("body { font-family: Arial, sans-serif; margin: 20px; padding: 0; background-color: #f4f4f4; }");
            retorno.append("div { background: #fff; margin: 10px 0; padding: 15px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }");
            retorno.append(".message { margin-top: 20px; padding: 10px; background-color: #e7f4e4; border: 1px solid #d4e6d0; border-radius: 4px; color: #2e8b57; }");
            retorno.append(".error { background-color: #f4e4e4; border: 1px solid #e6d0d0; color: #d9534f; }");
            retorno.append("</style></head><body>");
            
            retorno.append("<div>");
            retorno.append("Nome: ").append(filme.getNome()).append("<br><br>");
            retorno.append("Gênero: ").append(filme.getGenero()).append("<br><br>");
            retorno.append("Ano: ").append(filme.getAno());
            retorno.append("</div>");
            
            retorno.append("</body></html>");

            return retorno.toString();
        } catch (NumberFormatException e) {
            // Retorna uma mensagem de erro se o ID for inválido
            return "<html><body><div class='error'>Erro: ID inválido.</div></body></html>";
        }
    }

    // Atualiza os detalhes de um filme existente
    public Object update(Request request, Response response) {
        String idParam = request.queryParams("id");
        String nome = request.queryParams("nome");
        String genero = request.queryParams("genero");
        String anoParam = request.queryParams("ano");

        // Verifica se o ID foi fornecido
        if (idParam == null || idParam.isEmpty()) {
            response.status(400); // Status HTTP 400: Bad Request
            return "<html><body><div class='error'>Erro: ID não fornecido.</div></body></html>";
        }

        try {
            int id = Integer.parseInt(idParam);
            Filmes filme = filmesDAO.get(id); // Obtém o filme pelo ID

            if (filme != null) {
                // Atualiza os campos apenas se os novos valores não forem nulos
                if (nome != null && !nome.isEmpty()) {
                    filme.setNome(nome);
                }
                if (genero != null && !genero.isEmpty()) {
                    filme.setGenero(genero);
                }
                if (anoParam != null && !anoParam.isEmpty()) {
                    filme.setAno(Integer.parseInt(anoParam));
                }

                filmesDAO.update(filme); // Atualiza o filme no banco de dados

                return "<html><body><div class='message'>Filme atualizado com sucesso!</div></body></html>";
            } else {
                response.status(404); // Status HTTP 404: Not Found
                return "<html><body><div class='error'>Filme não encontrado!</div></body></html>";
            }
        } catch (NumberFormatException e) {
            // Retorna uma mensagem de erro se o ID ou o ano forem inválidos
            response.status(400); // Status HTTP 400: Bad Request
            return "<html><body><div class='error'>Erro: ID ou ano inválidos.</div></body></html>";
        }
    }

    // Remove um filme pelo ID
    public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id")); // Obtém o ID do filme
        Filmes filme = filmesDAO.get(id); // Obtém o filme pelo ID

        if (filme != null) {
            filmesDAO.delete(id); // Remove o filme do banco de dados
            return "<html><body><div class='message'>Filme removido com sucesso!</div></body></html>";
        } else {
            response.status(404); // Status HTTP 404: Not Found
            return "<html><body><div class='error'>Filme não encontrado!</div></body></html>";
        }
    }

    // Obtém todos os filmes cadastrados
    public Object getAll(Request request, Response response) {
        List<Filmes> filmes = filmesDAO.getAll(); // Obtém todos os filmes do banco de dados
        StringBuilder retorno = new StringBuilder();

        // Monta a resposta HTML
        retorno.append("<html><head><style>");
        retorno.append("body { font-family: Arial, sans-serif; margin: 20px; padding: 0; background-color: #f4f4f4; }");
        retorno.append("ul { list-style-type: none; padding: 0; }");
        retorno.append("li { background: #fff; margin: 10px 0; padding: 15px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }");
        retorno.append(".message { margin-top: 20px; padding: 10px; background-color: #e7f4e4; border: 1px solid #d4e6d0; border-radius: 4px; color: #2e8b57; }");
        retorno.append(".error { background-color: #f4e4e4; border: 1px solid #e6d0d0; color: #d9534f; }");
        retorno.append("</style></head><body>");
        
        retorno.append("<h2>Todos os Filmes</h2>");
        
        if (filmes.isEmpty()) {
            retorno.append("<p>Não há filmes cadastrados.</p>");
        } else {
            retorno.append("<ul>");
            for (Filmes filme : filmes) {
                retorno.append("<li>");
                retorno.append("ID: ").append(filme.getId()).append("<br><br>");
                retorno.append("Nome: ").append(filme.getNome()).append("<br><br>");
                retorno.append("Gênero: ").append(filme.getGenero()).append("<br><br>");
                retorno.append("Ano: ").append(filme.getAno());
                retorno.append("</li>");
            }
            retorno.append("</ul>");
        }
        
        retorno.append("</body></html>");

        return retorno.toString();
    }
}
