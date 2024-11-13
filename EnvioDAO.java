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
        String sql = "{ CALL `Actualizar Envio`(?, ?, ?, ?, ?, ?, ?, ?) }";
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

    public List<String[]> mostrarEnvios() {
        List<String[]> envios = new ArrayList<>();
        String sql = "{ CALL `MostrarEnvios`() }";
        try (CallableStatement stmt = conexion.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] envio = new String[8];
                envio[0] = rs.getString("Numero_envio");
                envio[1] = rs.getString("Cedula_cliente");
                envio[2] = rs.getString("Canton_sucursal");
                envio[3] = rs.getString("Fecha_envio");
                envio[4] = rs.getString("Costo");
                envio[5] = rs.getString("Estado_actual");
                envio[6] = rs.getString("Cedula_destinatario");
                envio[7] = rs.getString("Detalle");
                envios.add(envio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return envios;
    }

    public List<String[]> cantidadEnviosPorClientes() {
        List<String[]> cantidades = new ArrayList<>();
        String sql = "{ CALL `Cantidad de Envios por Clientes`() }";
        try (CallableStatement stmt = conexion.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] cantidad = new String[2];
                cantidad[0] = rs.getString("Cedula_cliente");
                cantidad[1] = rs.getString("CantidadEnvios");
                cantidades.add(cantidad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cantidades;
    }

    public List<String[]> mostrarClientesYEnvios() {
        List<String[]> clientesYEnvios = new ArrayList<>();
        String sql = "{ CALL `Mostrar Clientes y Envios`() }";
        try (CallableStatement stmt = conexion.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] clienteEnvio = new String[3];
                clienteEnvio[0] = rs.getString("Cedula_cliente");
                clienteEnvio[2] = rs.getString("Numero_envio");
                clienteEnvio[6] = rs.getString("Estado_actual");
                clientesYEnvios.add(clienteEnvio);
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
