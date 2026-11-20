package uam.edu.ni.servicios_web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import uam.edu.ni.servicios_web.model.Cliente;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {
    private static final String URL = "http://127.0.0.1:8080/clientes";
    private static final String API_KEY = "sb_publishable_satho_local_7K2dX9mP4qR8";

    public List<Cliente> obtenerClientes() throws IOException, InterruptedException {
        HttpRequest solicitud = HttpRequest.newBuilder(URI.create(URL))
                .timeout(Duration.ofSeconds(15))
                .header("apikey", API_KEY)
                .GET()
                .build();
        HttpResponse<String> respuesta = HttpClient.newHttpClient().send(
                solicitud, HttpResponse.BodyHandlers.ofString()
        );

        if (respuesta.statusCode() != 200) {
            throw new IOException("El servidor respondió con HTTP " + respuesta.statusCode());
        }

        JsonNode datos = new ObjectMapper().readTree(respuesta.body());
        if (datos == null || !datos.isArray()) {
            throw new IOException("La respuesta no contiene una lista de clientes");
        }

        List<Cliente> clientes = new ArrayList<>();
        for (JsonNode dato : datos) {
            clientes.add(new Cliente(
                    dato.path("id").asInt(),
                    dato.path("name").asText(),
                    dato.path("email").asText(),
                    dato.path("phone").asText(),
                    dato.path("city").asText(),
                    dato.path("annual_income").asDouble(),
                    dato.path("credit_score").asInt(),
                    dato.path("has_unpaid_debt").asBoolean()
            ));
        }
        return clientes;
    }
}
