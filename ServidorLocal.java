import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class ServidorLocal {
    private static final String API_KEY = "sb_publishable_satho_local_7K2dX9mP4qR8";
    private static final String CLIENTES = """
            [
              {
                "id": 1,
                "name": "Ana Lopez",
                "email": "ana@example.com",
                "phone": "0000-0000",
                "city": "Managua",
                "annual_income": 18000,
                "credit_score": 720,
                "has_unpaid_debt": false
              }
            ]
            """;

    private static final String USUARIOS = """
            [
              {
                "id": 1,
                "name": "Ana Lopez",
                "username": "analopez",
                "email": "ana@example.com",
                "phone": "0000-0000",
                "address": {"city": "Managua"},
                "company": {"name": "Empresa de ejemplo"}
              }
            ]
            """;

    public static void main(String[] args) throws IOException {
        HttpServer servidor = HttpServer.create(new InetSocketAddress("127.0.0.1", 8080), 0);
        servidor.createContext("/", ServidorLocal::atender);
        servidor.start();
        System.out.println("Servidor iniciado en http://127.0.0.1:8080/usuarios");
    }

    private static void atender(HttpExchange solicitud) throws IOException {
        try {
            String datos = switch (solicitud.getRequestURI().getPath()) {
                case "/usuarios" -> USUARIOS;
                case "/clientes" -> CLIENTES;
                default -> null;
            };
            if (datos == null) {
                responder(solicitud, 404, "{\"error\":\"Ruta no encontrada\"}");
                return;
            }
            if (!"GET".equals(solicitud.getRequestMethod())) {
                solicitud.getResponseHeaders().set("Allow", "GET");
                responder(solicitud, 405, "{\"error\":\"Metodo no permitido\"}");
                return;
            }
            String clave = solicitud.getRequestHeaders().getFirst("apikey");
            if (!API_KEY.equals(clave)) {
                responder(solicitud, 401, "{\"error\":\"Falta la API key o es incorrecta\"}");
                return;
            }
            responder(solicitud, 200, datos);
        } finally {
            solicitud.close();
        }
    }

    private static void responder(HttpExchange solicitud, int estado, String json) throws IOException {
        byte[] cuerpo = json.getBytes(StandardCharsets.UTF_8);
        solicitud.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        if ("HEAD".equals(solicitud.getRequestMethod())) {
            solicitud.sendResponseHeaders(estado, -1);
            return;
        }
        solicitud.sendResponseHeaders(estado, cuerpo.length);
        solicitud.getResponseBody().write(cuerpo);
    }
}
