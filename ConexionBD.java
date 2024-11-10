
import java.sql.*;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/laboratorio";
    private static final String USER = "root";
    private static final String PASSWORD = "D130620n.";

    // Establecer conexión con la base de datos
    public static Connection getConexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para autenticar usuario y contraseña
    public static boolean autenticarUsuario(String usuario, String contraseña) {
        // Consulta SQL para verificar el usuario y contraseña
        String query = "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?";

        try (Connection conn = getConexion(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecemos los parámetros de la consulta
            stmt.setString(1, usuario);
            stmt.setString(2, contraseña);

            // Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();

            // Si se encuentra un usuario con esa contraseña, la autenticación es exitosa
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
