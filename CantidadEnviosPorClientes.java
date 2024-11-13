public class CantidadEnviosPorClientes {
    private String cedulaCliente;
    private int cantidadEnvios;

    public CantidadEnviosPorClientes(String cedulaCliente, int cantidadEnvios) {
        this.cedulaCliente = cedulaCliente;
        this.cantidadEnvios = cantidadEnvios;
    }

    // Getters y setters
    public String getCedulaCliente() { return cedulaCliente; }
    public void setCedulaCliente(String cedulaCliente) { this.cedulaCliente = cedulaCliente; }

    public int getCantidadEnvios() { return cantidadEnvios; }
    public void setCantidadEnvios(int cantidadEnvios) { this.cantidadEnvios = cantidadEnvios; }
}
