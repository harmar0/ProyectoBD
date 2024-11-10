import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection conexion;

    public UsuarioDAO() {
        conexion = ConexionBD.getConexion(); // Suponiendo que la conexión es gestionada externamente
    }

    public boolean insertarUsuario(String Cedula, String Nombre1, String Nombre2, String Apellido1, String Apellido2) {
        String sql = "{ CALL clientes(?, ?, ?, ?, ?) }";
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
        String sql = "{ CALL ActualizarCliente(?, ?, ?, ?, ?) }";
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

    public boolean eliminarUsuario(String Cedula) {
        String sql = "{ CALL EliminarCliente(?) }";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setString(1, Cedula);
            boolean result = stmt.executeUpdate() > 0;
            // Cerrar conexión si es necesario
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String[]> obtenerUsuarios() {
        List<String[]> usuarios = new ArrayList<>();
        String sql = "{ CALL SeleccionarClientes() }";
        try (CallableStatement stmt = conexion.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] usuario = new String[5];
                usuario[0] = rs.getString("Cedula");
                usuario[1] = rs.getString("Nombre1");
                usuario[2] = rs.getString("Nombre2");
                usuario[3] = rs.getString("Apellido1");
                usuario[4] = rs.getString("Apellido2");
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        // Cerrar conexión si es necesario
        return usuarios;
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
