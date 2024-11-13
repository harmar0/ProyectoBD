public class Envio {
    private String numeroEnvio;
    private String cedulaCliente;
    private String cantonSucursal;
    private String fechaEnvio;
    private double costo;
    private String estadoActual;
    private String cedulaDestinatario;
    private String detalle;
    

    public Envio(String numeroEnvio,  String cedulaCliente,  String cantonSucursal, String fechaEnvio, double costo, String estadoActual,  String cedulaDestinatario,  String detalle) {
        this.numeroEnvio = numeroEnvio;
        this.cedulaCliente = cedulaCliente;
        this.cantonSucursal = cantonSucursal;
        this.fechaEnvio = fechaEnvio;
        this.costo = costo;
        this.estadoActual = estadoActual;
        this.cedulaDestinatario = cedulaDestinatario;
        this.detalle = detalle;
        
    }

    // Getters y setters
    public String getnumeroEnvio() { return numeroEnvio; }
    public String getcedulaCliente() { return cedulaCliente; }
    public String getcantonSucursal() { return cantonSucursal; }
    public String getFechaEnvio() { return fechaEnvio; }
    public double getCosto() { return costo; }
    public String getestadoActual() { return estadoActual; }
    public String getcedulaDestinatario() { return cedulaDestinatario; }
    public String getdetalle() { return detalle; }
    
}
