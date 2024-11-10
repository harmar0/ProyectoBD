import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazGrafica extends JFrame {
    private UsuarioDAO usuarioDAO;
    private JTextField txtCedula, txtNombre1, txtNombre2, txtApellido1, txtApellido2;

    public InterfazGrafica() {
        usuarioDAO = new UsuarioDAO();

        // Configuración de la ventana principal
        setTitle("Gestión de Usuarios");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Botones de la interfaz de usuario
        JButton btnInsertar = new JButton("Insertar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMostrar = new JButton("Mostrar");

        add(btnInsertar);
        add(btnActualizar);
        add(btnEliminar);
        add(btnMostrar);

        // Acción de Insertar
        btnInsertar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel panelInsertar = new JPanel(new GridLayout(6, 2));
                txtCedula = new JTextField(10);
                txtNombre1 = new JTextField(10);
                txtNombre2 = new JTextField(10);
                txtApellido1 = new JTextField(10);
                txtApellido2 = new JTextField(10);
                panelInsertar.add(new JLabel("Cedula:"));
                panelInsertar.add(txtCedula);
                panelInsertar.add(new JLabel("Nombre1:"));
                panelInsertar.add(txtNombre1);
                panelInsertar.add(new JLabel("Nombre2:"));
                panelInsertar.add(txtNombre2);
                panelInsertar.add(new JLabel("Apellido1:"));
                panelInsertar.add(txtApellido1);
                panelInsertar.add(new JLabel("Apellido2:"));
                panelInsertar.add(txtApellido2);

                int result = JOptionPane.showConfirmDialog(null, panelInsertar, "Insertar Usuario", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    if (usuarioDAO.insertarUsuario(txtCedula.getText(), txtNombre1.getText(), txtNombre2.getText(), txtApellido1.getText(), txtApellido2.getText())) {
                        JOptionPane.showMessageDialog(null, "Usuario insertado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al insertar usuario.");
                    }
                }
            }
        });

        // Acción de Actualizar
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel panelActualizar = new JPanel(new GridLayout(6, 2));
                txtCedula = new JTextField(10);
                txtNombre1 = new JTextField(10);
                txtNombre2 = new JTextField(10);
                txtApellido1 = new JTextField(10);
                txtApellido2 = new JTextField(10);
                panelActualizar.add(new JLabel("Cedula:"));
                panelActualizar.add(txtCedula);
                panelActualizar.add(new JLabel("Nombre1:"));
                panelActualizar.add(txtNombre1);
                panelActualizar.add(new JLabel("Nombre2:"));
                panelActualizar.add(txtNombre2);
                panelActualizar.add(new JLabel("Apellido1:"));
                panelActualizar.add(txtApellido1);
                panelActualizar.add(new JLabel("Apellido2:"));
                panelActualizar.add(txtApellido2);

                int result = JOptionPane.showConfirmDialog(null, panelActualizar, "Actualizar Usuario", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    if (usuarioDAO.actualizarUsuario(txtCedula.getText(), txtNombre1.getText(), txtNombre2.getText(), txtApellido1.getText(), txtApellido2.getText())) {
                        JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar usuario.");
                    }
                }
            }
        });

        // Acción de Eliminar
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cedula = JOptionPane.showInputDialog("Ingrese la cédula del usuario a eliminar:");
                if (cedula != null && !cedula.trim().isEmpty()) {
                    if (usuarioDAO.eliminarUsuario(cedula)) {
                        JOptionPane.showMessageDialog(null, "Usuario eliminado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar usuario.");
                    }
                }
            }
        });

        // Acción de Mostrar
        btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder resultado = new StringBuilder();
                for (String[] usuario : usuarioDAO.obtenerUsuarios()) {
                    resultado.append("Cedula: ").append(usuario[0])
                            .append(", Nombre1: ").append(usuario[1])
                            .append(", Nombre2: ").append(usuario[2])
                            .append(", Apellido1: ").append(usuario[3])
                            .append(", Apellido2: ").append(usuario[4])
                            .append("\n");
                }
                JOptionPane.showMessageDialog(null, resultado.toString(), "Lista de Usuarios", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear un JPasswordField para ingresar la contraseña
            JPasswordField passwordField = new JPasswordField(10);
            JPanel panelLogin = new JPanel();
            panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.Y_AXIS));
            panelLogin.add(new JLabel("Ingrese su usuario:"));
            JTextField usuarioField = new JTextField(10);
            panelLogin.add(usuarioField);
            panelLogin.add(new JLabel("Ingrese su contraseña:"));
            panelLogin.add(passwordField);

            int option = JOptionPane.showConfirmDialog(null, panelLogin, "Autenticación", JOptionPane.OK_CANCEL_OPTION);

            // Verificar si se presionó OK
            if (option == JOptionPane.OK_OPTION) {
                String usuario = usuarioField.getText();
                String contrasena = new String(passwordField.getPassword());  // Obtener la contraseña como un String

                // Autenticar usuario
                if (ConexionBD.autenticarUsuario(usuario, contrasena)) {
                    new InterfazGrafica().setVisible(true); // Si la autenticación es exitosa, mostrar la interfaz gráfica
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas.");
                    System.exit(0); // Terminar el programa si las credenciales son incorrectas
                }
            } else {
                System.exit(0); // Si el usuario cancela, cerrar la aplicación
            }
        });
    }
}
