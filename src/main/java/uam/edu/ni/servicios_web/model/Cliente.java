package uam.edu.ni.servicios_web.model;

public final class Cliente {
    private final int id;
    private final String nombre;
    private final String correo;
    private final String telefono;
    private final String ciudad;
    private final double ingresoAnual;
    private final int puntajeCredito;
    private final boolean deudor;

    public Cliente(int id, String nombre, String correo, String telefono,
                   String ciudad, double ingresoAnual, int puntajeCredito, boolean deudor) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.ingresoAnual = ingresoAnual;
        this.puntajeCredito = puntajeCredito;
        this.deudor = deudor;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public double getIngresoAnual() {
        return ingresoAnual;
    }

    public int getPuntajeCredito() {
        return puntajeCredito;
    }

    public boolean tieneDeuda() {
        return deudor;
    }
}
