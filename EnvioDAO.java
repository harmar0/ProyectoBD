import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnvioDAO {
    private Connection conexion;

    public EnvioDAO() {
        conexion = ConexionBD.getConexion(); 
    }

    public boolean insertarEnvio(String Numero_envio, String Cedula_cliente, String Canton_sucursal, 
                                 String Fecha_envio, double Costo, String Estado_actual, 
                                 String Cedula_destinatario, String Detalle) {
        String sql = "{ CALL `Insertar Envios`(?, ?, ?, ?, ?, ?, ?, ?) }";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setString(1, Numero_envio);
            stmt.setString(2, Cedula_cliente);
            stmt.setString(3, Canton_sucursal);
            stmt.setString(4, Fecha_envio);
            stmt.setDouble(5, Costo);
            stmt.setString(6, Estado_actual);
            stmt.setString(7, Cedula_destinatario);
            stmt.setString(8, Detalle);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarEnvio(String Numero_envio, String Cedula_cliente, String Canton_sucursal, 
                                   String Fecha_envio, double Costo, String Estado_actual, 
                                   String Cedula_destinatario, String Detalle) {
        String sql = "{ CALL `Actualizarenvio`(?, ?, ?, ?, ?, ?, ?, ?) }";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setString(1, Numero_envio);
            stmt.setString(2, Cedula_cliente);
            stmt.setString(3, Canton_sucursal);
            stmt.setString(4, Fecha_envio);
            stmt.setDouble(5, Costo);
            stmt.setString(6, Estado_actual);
            stmt.setString(7, Cedula_destinatario);
            stmt.setString(8, Detalle);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarEnvio(int numeroEnvio) {
        String sql = "{ CALL `EliminarEnvio`(?) }";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setInt(1, numeroEnvio);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Integer> obtenerNumerosEnvios() {
        List<Integer> envios = new ArrayList<>();
        String sql = "{CALL `Selectnumeroenvio`()}";
        try (CallableStatement stmt = conexion.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                envios.add(rs.getInt("Numero_envio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return envios;
    }

    public boolean eliminarEnvio(String Numero_envio) {
        String sql = "{ CALL `EliminarEnvio`(?) }";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setString(1, Numero_envio);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
public List<Envio> obtenerEnvios() {
    List<Envio> envios = new ArrayList<>();
    String sql = "{ CALL MostrarEnvios() }";

    try (CallableStatement stmt = conexion.prepareCall(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String numeroEnvio = rs.getString("Numero_envio");
            String cedulaCliente = rs.getString("Cedula_cliente");
            String cantonSucursal = rs.getString("Canton_sucursal");
            String fechaEnvio = rs.getString("Fecha_envio");
            double costo = rs.getDouble("Costo");
            String estadoActual = rs.getString("Estado_actual");
            String cedulaDestinatario = rs.getString("Cedula_destinatario");
            String detalle = rs.getString("Detalle");

            Envio envio = new Envio(numeroEnvio, cedulaCliente, cantonSucursal, fechaEnvio, costo, estadoActual, cedulaDestinatario, detalle);
            envios.add(envio);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return envios;
}

public List<Object[]> obtenerCantidadEnviosPorCliente() {
    List<Object[]> cantidades = new ArrayList<>();
    String sql = "{ CALL `Cantidad_de_Envios_por_Clientes`() }";

    try (CallableStatement stmt = conexion.prepareCall(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Object[] cantidad = new Object[3];
            cantidad[0] = rs.getString("Cedula_cliente");
            cantidad[1] = rs.getString("Nombre1");
            cantidad[2] = rs.getInt("cantidad_envios");
            cantidades.add(cantidad);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return cantidades;
}

    public List<Object[]> obtenerClientesYEnvios() {
        List<Object[]> clientesYEnvios = new ArrayList<>();
        String sql = "{ CALL `Mostrar_Clientes_y_Envios`() }";

        try (CallableStatement stmt = conexion.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String numeroEnvio = rs.getString("Numero_envio");
                String cedulaCliente = rs.getString("Cedula_cliente");
                String estadoActual = rs.getString("Estado_actual");

                clientesYEnvios.add(new Object[] { numeroEnvio, cedulaCliente, estadoActual });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientesYEnvios;
    }
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
