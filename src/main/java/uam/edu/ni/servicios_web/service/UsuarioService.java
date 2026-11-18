package uam.edu.ni.servicios_web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import uam.edu.ni.servicios_web.model.Usuario;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private static final String URL = "https://jsonplaceholder.typicode.com/users";

    public static List<Usuario> obtenerUsuarios() throws IOException, InterruptedException {

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();
        HttpResponse<String> response = cliente.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new IOException("Error al obtener el usuario");
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode datos = mapper.readTree(response.body());

        if (!datos.isArray()) {
            throw new IOException("La respuesta no contiene un formato ");
        }

        List <Usuario> usuarios = new ArrayList<>();
        for (JsonNode nodo : datos) {
            Usuario usuario = new Usuario(
                    nodo.path("id").asInt(),
                    nodo.path("name").asText(),
                    nodo.path("username").asText(),
                    nodo.path("email").asText(),
                    nodo.path("phone").asText(),
                    nodo.path("address").path("city").asText(),
                    nodo.path("company").path("name").asText()
            );
            usuarios.add(usuario);
        }
        return usuarios;
    }
}
