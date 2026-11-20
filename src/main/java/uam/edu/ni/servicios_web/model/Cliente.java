package uam.edu.ni.servicios_web.model;

public record Cliente(int id, String nombre, String correo, String telefono,
                      String ciudad, double ingresoAnual, int puntajeCredito, boolean deudor) {
}
