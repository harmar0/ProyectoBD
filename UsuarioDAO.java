import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection conexion;

    public UsuarioDAO() {
        conexion = ConexionBD.getConexion(); // Suponiendo que la conexión es gestionada externamente
    }

    public boolean insertarUsuario(String Cedula, String Nombre1, String Nombre2, String Apellido1, String Apellido2) {
        String sql = "{ CALL Insertarcliente(?, ?, ?, ?, ?) }";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setString(1, Cedula);
            stmt.setString(2, Nombre1);
            stmt.setString(3, Nombre2);
            stmt.setString(4, Apellido1);
            stmt.setString(5, Apellido2);
            boolean result = stmt.executeUpdate() > 0;
            // Aquí puedes cerrar la conexión si es necesario o manejarla externamente
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } 
    }

    public boolean actualizarUsuario(String Cedula, String Nombre1, String Nombre2, String Apellido1, String Apellido2) {
        String sql = "{ CALL Actualizarcliente(?, ?, ?, ?, ?) }";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setString(1, Cedula);
            stmt.setString(2, Nombre1);
            stmt.setString(3, Nombre2);
            stmt.setString(4, Apellido1);
            stmt.setString(5, Apellido2);
            boolean result = stmt.executeUpdate() > 0;
            // Cerrar conexión si es necesario
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCliente(String cedula) {
        String sql = "{ CALL EliminarCliente(?) }";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setString(1, cedula);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<String> obtenerCedulasClientes() {
        List<String> cedulas = new ArrayList<>();
        String sql = "{ CALL Selectclientecedula() }";
        try (CallableStatement stmt = conexion.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cedulas.add(rs.getString("Cedula_cliente"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cedulas;
    }

    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "{ CALL MostrarClientes() }";
    
        try (CallableStatement stmt = conexion.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                String cedula = rs.getString("Cedula_cliente");
                String nombre1 = rs.getString("Nombre1");
                String nombre2 = rs.getString("Nombre2");
                String apellido1 = rs.getString("Apellido1");
                String apellido2 = rs.getString("Apellido2");
    
                Cliente cliente = new Cliente(cedula, nombre1, nombre2, apellido1, apellido2);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return clientes;
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
    // Es recomendable cerrar la conexión explícitamente si es que esta clase maneja la conexión directamente
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
