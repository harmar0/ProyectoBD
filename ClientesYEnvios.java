public class ClientesYEnvios {
    private String numeroEnvio;
    private String cedulaCliente;
    private String estadoActual;

    public ClientesYEnvios(String numeroEnvio, String cedulaCliente, String estadoActual) {
        this.numeroEnvio = numeroEnvio;
        this.cedulaCliente = cedulaCliente;
        this.estadoActual = estadoActual;
    }

    // Getters y setters
    public String getNumeroEnvio() { return numeroEnvio; }
    public void setNumeroEnvio(String numeroEnvio) { this.numeroEnvio = numeroEnvio; }

    public String getCedulaCliente() { return cedulaCliente; }
    public void setCedulaCliente(String cedulaCliente) { this.cedulaCliente = cedulaCliente; }

    public String getEstadoActual() { return estadoActual; }
    public void setEstadoActual(String estadoActual) { this.estadoActual = estadoActual; }
}
