import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InterfazGrafica extends JFrame {
    private UsuarioDAO usuarioDAO;
    private FondoPanel fondoPanel;
    private JButton btnInsertar, btnActualizar, btnEliminar, btnMostrar, btnCerrarSesion;

    public InterfazGrafica() {
        usuarioDAO = new UsuarioDAO();

        // Configuración de la ventana principal
        setTitle("Gestión de Usuarios");
        setSize(400, 425);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana
        setLayout(null);

        // Imagen de fondo
        String imagePath = "C:/Users/david/OneDrive/Documentos/VSC proyects/ProyectoBD/.vscode/image/fondo.jpg";
        fondoPanel = new FondoPanel(imagePath);
        fondoPanel.setLayout(null);

        // Botón Insertar
        btnInsertar = new JButton("Insertar");
        btnInsertar.setBounds(100, 50, 200, 35);
        btnInsertar.setBackground(Color.WHITE);
        fondoPanel.add(btnInsertar);

        // Botón Actualizar
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(100, 100, 200, 35);
        btnActualizar.setBackground(Color.WHITE);
        fondoPanel.add(btnActualizar);

        // Botón Eliminar
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(100, 150, 200, 35);
        btnEliminar.setBackground(Color.WHITE);
        fondoPanel.add(btnEliminar);

        // Botón Mostrar
        btnMostrar = new JButton("Mostrar");
        btnMostrar.setBounds(100, 200, 200, 35);
        btnMostrar.setBackground(Color.WHITE);
        fondoPanel.add(btnMostrar);

        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBounds(100, 250, 200, 35);
        fondoPanel.add(btnCerrarSesion);

        btnCerrarSesion.addActionListener(e -> {
            dispose(); // Cierra la ventana actual
            new VentanaLogin().setVisible(true); // Abre la ventana de login
        });

        setContentPane(fondoPanel);

        // Acción para abrir la ventana de insertar usuario
        btnInsertar.addActionListener(e -> {
            this.setVisible(false);
            new VentanaInsertar(usuarioDAO, this);
        });

        // Acción para abrir la ventana de actualizar usuario
        btnActualizar.addActionListener(e -> {
            this.setVisible(false);
            new VentanaActualizar(usuarioDAO, this);
        });

        // Acción para eliminar usuario
        btnEliminar.addActionListener(e -> {
            String cedula = JOptionPane.showInputDialog("Ingrese la cédula del usuario a eliminar:");
            if (cedula != null && !cedula.trim().isEmpty()) {
                if (usuarioDAO.eliminarUsuario(cedula)) {
                    JOptionPane.showMessageDialog(null, "Usuario eliminado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar usuario.");
                }
            }
        });

        // Acción para abrir la ventana de mostrar usuarios
        btnMostrar.addActionListener(e -> {
            this.setVisible(false);
            new VentanaMostrar(usuarioDAO, this);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaLogin::new);
    }
}

// Ventana para Insertar Usuario
class VentanaInsertar extends JFrame {
    private JTextField txtCedula, txtNombre1, txtNombre2, txtApellido1, txtApellido2;
    private UsuarioDAO usuarioDAO;

    public VentanaInsertar(UsuarioDAO usuarioDAO, JFrame parent) {
        this.usuarioDAO = usuarioDAO;
        setTitle("Insertar Usuario");
        setSize(300, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2));
        txtCedula = new JTextField(10);
        txtNombre1 = new JTextField(10);
        txtNombre2 = new JTextField(10);
        txtApellido1 = new JTextField(10);
        txtApellido2 = new JTextField(10);

        panel.add(new JLabel("Cédula:"));
        panel.add(txtCedula);
        panel.add(new JLabel("Nombre1:"));
        panel.add(txtNombre1);
        panel.add(new JLabel("Nombre2:"));
        panel.add(txtNombre2);
        panel.add(new JLabel("Apellido1:"));
        panel.add(txtApellido1);
        panel.add(new JLabel("Apellido2:"));
        panel.add(txtApellido2);

        JButton btnInsertar = new JButton("Insertar");
        JButton btnVolver = new JButton("Volver");

        btnInsertar.addActionListener(e -> {
            if (usuarioDAO.insertarUsuario(txtCedula.getText(), txtNombre1.getText(), txtNombre2.getText(),
                    txtApellido1.getText(), txtApellido2.getText())) {
                JOptionPane.showMessageDialog(this, "Usuario insertado exitosamente.");
                dispose();
                parent.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al insertar usuario.");
            }
        });

        btnVolver.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        add(panel, BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.add(btnInsertar);
        southPanel.add(btnVolver);
        add(southPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}

// Ventana para Actualizar Usuario
class VentanaActualizar extends JFrame {
    private JTextField txtCedula, txtNombre1, txtNombre2, txtApellido1, txtApellido2;
    private UsuarioDAO usuarioDAO;

    public VentanaActualizar(UsuarioDAO usuarioDAO, JFrame parent) {
        this.usuarioDAO = usuarioDAO;
        setTitle("Actualizar Usuario");
        setSize(300, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2));
        txtCedula = new JTextField(10);
        txtNombre1 = new JTextField(10);
        txtNombre2 = new JTextField(10);
        txtApellido1 = new JTextField(10);
        txtApellido2 = new JTextField(10);

        panel.add(new JLabel("Cédula:"));
        panel.add(txtCedula);
        panel.add(new JLabel("Nombre1:"));
        panel.add(txtNombre1);
        panel.add(new JLabel("Nombre2:"));
        panel.add(txtNombre2);
        panel.add(new JLabel("Apellido1:"));
        panel.add(txtApellido1);
        panel.add(new JLabel("Apellido2:"));
        panel.add(txtApellido2);

        JButton btnActualizar = new JButton("Actualizar");
        JButton btnVolver = new JButton("Volver");

        btnActualizar.addActionListener(e -> {
            if (usuarioDAO.actualizarUsuario(txtCedula.getText(), txtNombre1.getText(), txtNombre2.getText(),
                    txtApellido1.getText(), txtApellido2.getText())) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.");
                dispose();
                parent.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar usuario.");
            }
        });

        btnVolver.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        add(panel, BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.add(btnActualizar);
        southPanel.add(btnVolver);
        add(southPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}

// Ventana para Mostrar Usuarios en una tabla
class VentanaMostrar extends JFrame {
    public VentanaMostrar(UsuarioDAO usuarioDAO, JFrame parent) {
        setTitle("Lista de Usuarios");
        setSize(500, 300);
        setLocationRelativeTo(null);

        List<String[]> usuarios = usuarioDAO.obtenerUsuarios();
        String[] columnNames = { "Cédula", "Nombre1", "Nombre2", "Apellido1", "Apellido2" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (String[] usuario : usuarios) {
            tableModel.addRow(usuario);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        add(scrollPane, BorderLayout.CENTER);
        add(btnVolver, BorderLayout.SOUTH);

        setVisible(true);
    }
}

// Ventana de Login
class VentanaLogin extends JFrame {
    public VentanaLogin() {
        setTitle("Login");
        setSize(500, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para la imagen de fondo
        JPanel panelImagen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibuja la imagen de fondo
                ImageIcon icon = new ImageIcon(
                        "C:\\Users\\david\\OneDrive\\Documentos\\VSC proyects\\ProyectoBD\\.vscode\\image\\fon.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelImagen.setLayout(new BorderLayout()); // Establecer un layout para el panel de imagen
        add(panelImagen, BorderLayout.CENTER);

        // Panel para los campos de usuario y contraseña
        JPanel panelCampos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCampos.setOpaque(false); // Hacer el panel transparente
        JLabel labelUsuario = new JLabel("Usuario:");
        labelUsuario.setForeground(Color.WHITE); // Cambiar el color del texto a blanco
        panelCampos.add(labelUsuario);
        JTextField usuarioField = new JTextField(10);
        panelCampos.add(usuarioField);
        JLabel labelContraseña = new JLabel("Contraseña:");
        labelContraseña.setForeground(Color.WHITE); // Cambiar el color del texto a blanco
        panelCampos.add(labelContraseña);
        JPasswordField passwordField = new JPasswordField(10);
        panelCampos.add(passwordField);

        // Panel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setOpaque(false); // Hacer el panel de botones transparente
        JButton btnEntrar = new JButton("Entrar");
        JButton btnSalir = new JButton("Salir");

        btnEntrar.addActionListener(e -> {
            String usuario = usuarioField.getText();
            String contraseña = new String(passwordField.getPassword());

            if (ConexionBD.autenticarUsuario(usuario, contraseña)) {
                new InterfazGrafica().setVisible(true);
                this.dispose(); // Cerrar la ventana de login
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
            }
        });

        btnSalir.addActionListener(e -> {
            System.exit(0); // Cierra completamente el programa
        });

        panelBotones.add(btnEntrar);
        panelBotones.add(btnSalir);

        // Añadir los paneles (campos de texto y botones) sobre el panel de imagen
        panelImagen.add(panelCampos, BorderLayout.CENTER);
        panelImagen.add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }
}

// Clase de FondoPanel para la imagen de fondo
class FondoPanel extends JPanel {
    private Image imagen;

    public FondoPanel(String imagePath) {
        try {
            imagen = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
