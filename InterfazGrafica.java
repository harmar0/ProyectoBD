import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InterfazGrafica extends JFrame {
    private UsuarioDAO usuarioDAO;
    private EnvioDAO envioDao;
    private FondoPanel fondoPanel;
    private JButton btnInsertar, btnActualizar, btnEliminar, btnMostrar, btnCerrarSesion;

    public InterfazGrafica() {
        usuarioDAO = new UsuarioDAO();
        envioDao = new EnvioDAO();

        // Configuración de la ventana principal
        setTitle("Gestión de Usuarios");
        setSize(400, 425);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana
        setLayout(null);

        // Imagen de fondo
        String imagePath = "images/fondo.jpg";
        fondoPanel = new FondoPanel(imagePath);
        fondoPanel.setLayout(null);

        // Icono para el botón de cerrar sesión
        ImageIcon iconCerrarSesion = new ImageIcon("images/sesion.png");
        iconCerrarSesion = new ImageIcon(iconCerrarSesion.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));  

        // Crear el botón con el icono
        btnCerrarSesion = new JButton();
        btnCerrarSesion.setIcon(iconCerrarSesion);  // Coloca el icono dentro del botón
        btnCerrarSesion.setBounds(10, 310, 50, 50);  // Ajusta el tamaño del botón para que se adapte al icono
        btnCerrarSesion.setContentAreaFilled(false);  // Evita que se dibuje el fondo del botón
        btnCerrarSesion.setBorderPainted(false);     // Elimina el borde del botón
        fondoPanel.add(btnCerrarSesion);

        btnCerrarSesion.addActionListener(e -> {
            dispose(); // Cierra la ventana actual
            new VentanaLogin().setVisible(true); // Abre la ventana de login
        });

        // Otros botones
        btnInsertar = new JButton("Insertar");
        btnInsertar.setBounds(100, 50, 200, 35);
        btnInsertar.setBackground(Color.WHITE);
        fondoPanel.add(btnInsertar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(100, 100, 200, 35);
        btnActualizar.setBackground(Color.WHITE);
        fondoPanel.add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(100, 150, 200, 35);
        btnEliminar.setBackground(Color.WHITE);
        fondoPanel.add(btnEliminar);

        btnMostrar = new JButton("Mostrar");
        btnMostrar.setBounds(100, 200, 200, 35);
        btnMostrar.setBackground(Color.WHITE);
        fondoPanel.add(btnMostrar);

        setContentPane(fondoPanel);

        // Acción para abrir la ventana de insertar usuario
        btnInsertar.addActionListener(e -> new VentanaInsertarOpciones(usuarioDAO, envioDao, this));

        // Acción para abrir la ventana de actualizar usuario
        btnActualizar.addActionListener(e -> new VentanaActualizar());

        btnEliminar.addActionListener(e -> {
            JFrame ventanaEliminar = new JFrame("Eliminar");
            ventanaEliminar.setSize(200, 150);
            ventanaEliminar.setLocationRelativeTo(null);

            FondoPanel fondoPanel = new FondoPanel("images/fondo.jpg");
            fondoPanel.setLayout(new FlowLayout());

            JButton btnEliminarCliente = new JButton("Eliminar Cliente");
            btnEliminarCliente.addActionListener(e1 -> {
                new VentanaEliminarCliente().setVisible(true);
                ventanaEliminar.dispose();
            });

            JButton btnEliminarEnvio = new JButton("Eliminar Envío");
            btnEliminarEnvio.addActionListener(e2 -> {
                new VentanaEliminarEnvio().setVisible(true);
                ventanaEliminar.dispose();
            });

            JButton btnVolver = new JButton("Volver");
            btnVolver.addActionListener(e3 -> ventanaEliminar.dispose());

            fondoPanel.add(btnEliminarCliente);
            fondoPanel.add(btnEliminarEnvio);
            fondoPanel.add(btnVolver);

            ventanaEliminar.setContentPane(fondoPanel);
            ventanaEliminar.setVisible(true);
        });

        // Acción para abrir la ventana de mostrar usuarios
        btnMostrar.addActionListener(e -> {
            this.setVisible(false); // Oculta la ventana principal
            new VentanaMostrar(this); // Pasa la referencia de la ventana principal
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaLogin::new);
    }
}

class VentanaInsertarOpciones extends JFrame {
    public VentanaInsertarOpciones(UsuarioDAO usuarioDAO, EnvioDAO envioDao, JFrame parent) {
        setTitle("Insertar");
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear panel de fondo
        String imagePath = "images/fondo.jpg";
        FondoPanel fondoPanel = new FondoPanel(imagePath);
        fondoPanel.setLayout(new GridBagLayout()); // Usar GridBagLayout para flexibilidad
        fondoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre botones
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Botones con un tamaño preferido menor
        JButton btnInsertarCliente = new JButton("Insertar Cliente");
        btnInsertarCliente.setPreferredSize(new Dimension(150, 30));
        JButton btnInsertarEnvio = new JButton("Insertar Envio");
        btnInsertarEnvio.setPreferredSize(new Dimension(150, 30));
        JButton btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(150, 30));

        // Agregar botones al panel de fondo
        fondoPanel.add(btnInsertarCliente, gbc);
        gbc.gridy++;
        fondoPanel.add(btnInsertarEnvio, gbc);
        gbc.gridy++;
        fondoPanel.add(btnVolver, gbc);

        // Agregar acción a los botones
        btnInsertarCliente.addActionListener(e -> new VentanaInsertarCliente(usuarioDAO, this));
        btnInsertarEnvio.addActionListener(e -> new VentanaInsertarEnvio(envioDao, this));
        btnVolver.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        // Establecer el panel de fondo como el ContentPane
        setContentPane(fondoPanel);
        setVisible(true);
    }
}


// Ventana para insertar cliente
class VentanaInsertarCliente extends JFrame {
    public VentanaInsertarCliente(UsuarioDAO usuarioDAO, JFrame parent) {
        setTitle("Insertar Cliente");
        setSize(300, 200);
        setLocationRelativeTo(parent);

        // Crear panel de fondo
        String imagePath = "images/fondo.jpg";
        FondoPanel fondoPanel = new FondoPanel(imagePath);
        fondoPanel.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.setOpaque(false); // Hacer transparente para que el fondo sea visible
        JTextField txtCedula = new JTextField(10);
        JTextField txtNombre1 = new JTextField(10);
        JTextField txtNombre2 = new JTextField(10);
        JTextField txtApellido1 = new JTextField(10);
        JTextField txtApellido2 = new JTextField(10);

        // Labels con color de texto negro
        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setForeground(Color.WHITE);
        JLabel lblNombre1 = new JLabel("Primer Nombre:");
        lblNombre1.setForeground(Color.WHITE);
        JLabel lblNombre2 = new JLabel("Segundo Nombre:");
        lblNombre2.setForeground(Color.WHITE);
        JLabel lblApellido1 = new JLabel("Primer Apellido:");
        lblApellido1.setForeground(Color.WHITE);
        JLabel lblApellido2 = new JLabel("Segundo Apellido:");
        lblApellido2.setForeground(Color.WHITE);

        panel.add(lblCedula);
        panel.add(txtCedula);
        panel.add(lblNombre1);
        panel.add(txtNombre1);
        panel.add(lblNombre2);
        panel.add(txtNombre2);
        panel.add(lblApellido1);
        panel.add(txtApellido1);
        panel.add(lblApellido2);
        panel.add(txtApellido2);

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnVolver = new JButton("Volver");

        btnAceptar.addActionListener(e -> {
            if (usuarioDAO.insertarUsuario(txtCedula.getText(), txtNombre1.getText(), txtNombre2.getText(), txtApellido1.getText(), txtApellido2.getText())) {
                JOptionPane.showMessageDialog(this, "Cliente insertado exitosamente.");
                dispose();
                parent.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al insertar cliente.");
            }
        });

        btnVolver.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        fondoPanel.add(panel, BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.add(btnAceptar);
        southPanel.add(btnVolver);
        fondoPanel.add(southPanel, BorderLayout.SOUTH);

        setContentPane(fondoPanel);
        setVisible(true);
    }
}

// Ventana para insertar envío
class VentanaInsertarEnvio extends JFrame {
    public VentanaInsertarEnvio(EnvioDAO envioDao, JFrame parent) {
        setTitle("Insertar Envio");
        setSize(300, 300);
        setLocationRelativeTo(parent);

        // Crear panel de fondo
        String imagePath = "images/fondo.jpg";
        FondoPanel fondoPanel = new FondoPanel(imagePath);
        fondoPanel.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(10, 2));
        panel.setOpaque(false);
        JTextField txtNumeroEnvio = new JTextField();
        JTextField txtCedulaCliente = new JTextField();
        JTextField txtCantonSucursal = new JTextField();
        JTextField txtFechaEnvio = new JTextField();
        JTextField txtCosto = new JTextField();
        JTextField txtEstadoActual = new JTextField();
        JTextField txtCedulaDestinatario = new JTextField();
        JTextField txtDetalle = new JTextField();

        // Labels con color de texto negro
        JLabel lblNumeroEnvio = new JLabel("Número de Envio:");
        lblNumeroEnvio.setForeground(Color.WHITE);
        JLabel lblCedulaCliente = new JLabel("Cédula del cliente:");
        lblCedulaCliente.setForeground(Color.WHITE);
        JLabel lblCantonSucursal = new JLabel("Cantón de la sucursal:");
        lblCantonSucursal.setForeground(Color.WHITE);
        JLabel lblFechaEnvio = new JLabel("Fecha de Envio (AAAA-MM-DD):");
        lblFechaEnvio.setForeground(Color.WHITE);
        JLabel lblCosto = new JLabel("Costo:");
        lblCosto.setForeground(Color.WHITE);
        JLabel lblEstadoActual = new JLabel("Estado actual del envío:");
        lblEstadoActual.setForeground(Color.WHITE);
        JLabel lblCedulaDestinatario = new JLabel("Cédula del destinatario:");
        lblCedulaDestinatario.setForeground(Color.WHITE);
        JLabel lblDetalle = new JLabel("Detalle del envío:");
        lblDetalle.setForeground(Color.WHITE);

        panel.add(lblNumeroEnvio);
        panel.add(txtNumeroEnvio);
        panel.add(lblCedulaCliente);
        panel.add(txtCedulaCliente);
        panel.add(lblCantonSucursal);
        panel.add(txtCantonSucursal);
        panel.add(lblFechaEnvio);
        panel.add(txtFechaEnvio);
        panel.add(lblCosto);
        panel.add(txtCosto);
        panel.add(lblEstadoActual);
        panel.add(txtEstadoActual);
        panel.add(lblCedulaDestinatario);
        panel.add(txtCedulaDestinatario);
        panel.add(lblDetalle);
        panel.add(txtDetalle);

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnVolver = new JButton("Volver");

        btnAceptar.addActionListener(e -> {
            try {
                double costo = Double.parseDouble(txtCosto.getText());
                if (envioDao.insertarEnvio(txtNumeroEnvio.getText(), txtCedulaCliente.getText(), txtCantonSucursal.getText(), txtFechaEnvio.getText(), costo, txtEstadoActual.getText(), txtCedulaDestinatario.getText(), txtDetalle.getText())) {
                    JOptionPane.showMessageDialog(this, "Envio insertado exitosamente.");
                    dispose();
                    parent.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al insertar envio.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Costo debe ser un valor numérico.");
            }
        });

        btnVolver.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        fondoPanel.add(panel, BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.add(btnAceptar);
        southPanel.add(btnVolver);
        fondoPanel.add(southPanel, BorderLayout.SOUTH);

        setContentPane(fondoPanel);
        setVisible(true);
    }
}


class VentanaActualizar extends JFrame {
    public VentanaActualizar() {
        setTitle("Actualizar Datos");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear el panel de fondo
        String imagePath = "images/fondo.jpg";
        FondoPanel fondoPanel = new FondoPanel(imagePath);
        fondoPanel.setLayout(new GridBagLayout()); // Usar GridBagLayout para centrar elementos

        // Configurar layout para centrar los botones
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacio entre botones
        gbc.gridx = 0; // Todos los botones en la misma columna
        gbc.anchor = GridBagConstraints.CENTER; // Centrar botones

        // Crear los botones con tamaño reducido
        JButton btnActualizarCliente = new JButton("Cliente");
        JButton btnActualizarEnvio = new JButton("Envío");
        JButton btnVolver = new JButton("Volver");

        // Ajustar tamaño de los botones
        Dimension buttonSize = new Dimension(120, 30); // Tamaño más pequeño
        btnActualizarCliente.setPreferredSize(buttonSize);
        btnActualizarEnvio.setPreferredSize(buttonSize);
        btnVolver.setPreferredSize(buttonSize);

        // Agregar acción a los botones
        btnActualizarCliente.addActionListener(e -> new VentanaActualizarCliente().setVisible(true));
        btnActualizarEnvio.addActionListener(e -> new VentanaActualizarEnvio().setVisible(true));
        btnVolver.addActionListener(e -> dispose());

        // Agregar los botones al panel con fondo
        gbc.gridy = 0; // Fila 0
        fondoPanel.add(btnActualizarCliente, gbc);
        gbc.gridy = 1; // Fila 1
        fondoPanel.add(btnActualizarEnvio, gbc);
        gbc.gridy = 2; // Fila 2
        fondoPanel.add(btnVolver, gbc);

        // Establecer el panel de fondo como ContentPane
        setContentPane(fondoPanel);
        setVisible(true);
    }
}


class VentanaActualizarCliente extends JFrame {
    public VentanaActualizarCliente() {
        setTitle("Actualizar Cliente");
        setSize(300, 300);
        setLocationRelativeTo(null);

        // Crear el panel de fondo
        String imagePath = "images/fondo.jpg";
        FondoPanel fondoPanel = new FondoPanel(imagePath);
        fondoPanel.setLayout(new BorderLayout());

        // Panel con campos de cliente
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.setOpaque(false); // Hacer transparente para mostrar el fondo

        JTextField txtCedula = new JTextField(10);
        JTextField txtNombre1 = new JTextField(10);
        JTextField txtNombre2 = new JTextField(10);
        JTextField txtApellido1 = new JTextField(10);
        JTextField txtApellido2 = new JTextField(10);

        // Crear y personalizar los JLabel
        JLabel lblCedula = new JLabel("Cédula:");
        JLabel lblNombre1 = new JLabel("Primer nombre:");
        JLabel lblNombre2 = new JLabel("Segundo nombre:");
        JLabel lblApellido1 = new JLabel("Primer apellido:");
        JLabel lblApellido2 = new JLabel("Segundo apellido:");

        // Establecer color blanco a los JLabel
        lblCedula.setForeground(Color.WHITE);
        lblNombre1.setForeground(Color.WHITE);
        lblNombre2.setForeground(Color.WHITE);
        lblApellido1.setForeground(Color.WHITE);
        lblApellido2.setForeground(Color.WHITE);

        // Agregar componentes al panel
        panel.add(lblCedula);
        panel.add(txtCedula);
        panel.add(lblNombre1);
        panel.add(txtNombre1);
        panel.add(lblNombre2);
        panel.add(txtNombre2);
        panel.add(lblApellido1);
        panel.add(txtApellido1);
        panel.add(lblApellido2);
        panel.add(txtApellido2);

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnVolver = new JButton("Volver");

        btnAceptar.addActionListener(e -> {
            String cedula = txtCedula.getText();
            String nombre1 = txtNombre1.getText();
            String nombre2 = txtNombre2.getText();
            String apellido1 = txtApellido1.getText();
            String apellido2 = txtApellido2.getText();

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean success = usuarioDAO.actualizarUsuario(cedula, nombre1, nombre2, apellido1, apellido2);

            if (success) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el cliente.");
            }
        });

        btnVolver.addActionListener(e -> dispose());

        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false); // Hacer transparente
        southPanel.add(btnAceptar);
        southPanel.add(btnVolver);

        fondoPanel.add(panel, BorderLayout.CENTER);
        fondoPanel.add(southPanel, BorderLayout.SOUTH);

        setContentPane(fondoPanel);
        setVisible(true);
    }
}


class VentanaActualizarEnvio extends JFrame {
    public VentanaActualizarEnvio() {
        setTitle("Actualizar Envío");
        setSize(300, 250);
        setLocationRelativeTo(null);

        // Crear el panel de fondo
        String imagePath = "images/fondo.jpg";
        FondoPanel fondoPanel = new FondoPanel(imagePath);
        fondoPanel.setLayout(new BorderLayout());

        // Panel con campos de envío
        JPanel panel = new JPanel(new GridLayout(10, 2));
        panel.setOpaque(false); // Hacer transparente para mostrar el fondo

        JTextField txtNumeroEnvio = new JTextField(10);
        JTextField txtCedulaCliente = new JTextField(10);
        JTextField txtCantonSucursal = new JTextField(10);
        JTextField txtFechaEnvio = new JTextField(10);
        JTextField txtCosto = new JTextField(10);
        JTextField txtEstadoActual = new JTextField(10);
        JTextField txtCedulaDestinatario = new JTextField(10);
        JTextField txtDetalle = new JTextField(10);

        // Crear y personalizar los JLabel
        JLabel lblNumeroEnvio = new JLabel("Número de Envio:");
        JLabel lblCedulaCliente = new JLabel("Cédula del cliente:");
        JLabel lblCantonSucursal = new JLabel("Cantón de la sucursal:");
        JLabel lblFechaEnvio = new JLabel("Fecha de Envio (AAAA-MM-DD):");
        JLabel lblCosto = new JLabel("Costo:");
        JLabel lblEstadoActual = new JLabel("Estado actual del envío:");
        JLabel lblCedulaDestinatario = new JLabel("Cédula del destinatario:");
        JLabel lblDetalle = new JLabel("Detalle del envío:");

        // Establecer color blanco a los JLabel
        lblNumeroEnvio.setForeground(Color.WHITE);
        lblCedulaCliente.setForeground(Color.WHITE);
        lblCantonSucursal.setForeground(Color.WHITE);
        lblFechaEnvio.setForeground(Color.WHITE);
        lblCosto.setForeground(Color.WHITE);
        lblEstadoActual.setForeground(Color.WHITE);
        lblCedulaDestinatario.setForeground(Color.WHITE);
        lblDetalle.setForeground(Color.WHITE);

        // Agregar componentes al panel
        panel.add(lblNumeroEnvio);
        panel.add(txtNumeroEnvio);
        panel.add(lblCedulaCliente);
        panel.add(txtCedulaCliente);
        panel.add(lblCantonSucursal);
        panel.add(txtCantonSucursal);
        panel.add(lblFechaEnvio);
        panel.add(txtFechaEnvio);
        panel.add(lblCosto);
        panel.add(txtCosto);
        panel.add(lblEstadoActual);
        panel.add(txtEstadoActual);
        panel.add(lblCedulaDestinatario);
        panel.add(txtCedulaDestinatario);
        panel.add(lblDetalle);
        panel.add(txtDetalle);

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnVolver = new JButton("Volver");

        btnAceptar.addActionListener(e -> {
            try {
                String numeroEnvio = txtNumeroEnvio.getText();
                String cedulaCliente = txtCedulaCliente.getText();
                String cantonSucursal = txtCantonSucursal.getText();
                String fechaEnvio = txtFechaEnvio.getText();
                double costo = Double.parseDouble(txtCosto.getText());
                String estadoActual = txtEstadoActual.getText();
                String cedulaDestinatario = txtCedulaDestinatario.getText();
                String detalle = txtDetalle.getText();

                EnvioDAO envioDao = new EnvioDAO();
                boolean success = envioDao.actualizarEnvio(numeroEnvio, cedulaCliente, cantonSucursal, fechaEnvio, costo, estadoActual, cedulaDestinatario, detalle);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Envío actualizado exitosamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el envío.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un número válido en los campos de Número de Envío y Costo.");
            }
        });

        btnVolver.addActionListener(e -> dispose());

        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false); // Hacer transparente
        southPanel.add(btnAceptar);
        southPanel.add(btnVolver);

        fondoPanel.add(panel, BorderLayout.CENTER);
        fondoPanel.add(southPanel, BorderLayout.SOUTH);

        setContentPane(fondoPanel);
        setVisible(true);
    }
}

class VentanaEliminarCliente extends JFrame {
    private JComboBox<String> comboCedulas;
    private JButton btnEliminar, btnVolver;
    private UsuarioDAO usuarioDAO;

    public VentanaEliminarCliente() {
        usuarioDAO = new UsuarioDAO();

        setTitle("Eliminar Cliente");
        setSize(600, 400);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        // Crear el panel de fondo
        String imagePath = "images/fondo.jpg";
        FondoPanel fondoPanel = new FondoPanel(imagePath);
        fondoPanel.setLayout(new FlowLayout());  // Aseguramos que el contenido se acomode correctamente

        // Combo box con lista de cédulas
        comboCedulas = new JComboBox<>();
        cargarCedulas();
        fondoPanel.add(new JLabel("Seleccione cédula a eliminar:"));
        fondoPanel.add(comboCedulas);

        // Botón Eliminar
        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedulaSeleccionada = (String) comboCedulas.getSelectedItem();
                if (cedulaSeleccionada != null) {
                    boolean success = usuarioDAO.eliminarCliente(cedulaSeleccionada);
                    if (success) {
                        JOptionPane.showMessageDialog(VentanaEliminarCliente.this, "Cliente eliminado exitosamente.");
                        cargarCedulas(); // Recargar cédulas después de eliminar
                    } else {
                        JOptionPane.showMessageDialog(VentanaEliminarCliente.this, "Error al eliminar el cliente.");
                    }
                }
            }
        });
        fondoPanel.add(btnEliminar);

        // Botón Volver
        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> dispose());
        fondoPanel.add(btnVolver);

        // Establecer el panel de fondo como ContentPane
        setContentPane(fondoPanel);
        setVisible(true);
    }

    // Método para cargar cédulas en el combo box
    private void cargarCedulas() {
        comboCedulas.removeAllItems();
        List<String> cedulas = usuarioDAO.obtenerCedulasClientes();
        for (String cedula : cedulas) {
            comboCedulas.addItem(cedula);
        }
    }
}

class VentanaEliminarEnvio extends JFrame {
    private JComboBox<Integer> comboEnvios;
    private JButton btnEliminar, btnVolver;
    private EnvioDAO envioDAO;

    public VentanaEliminarEnvio() {
        envioDAO = new EnvioDAO();

        setTitle("Eliminar Envío");
        setSize(300, 200);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        // Crear el panel de fondo
        String imagePath = "images/fondo.jpg";
        FondoPanel fondoPanel = new FondoPanel(imagePath);
        fondoPanel.setLayout(new FlowLayout());  // Aseguramos que el contenido se acomode correctamente

        // Combo box con lista de envíos
        comboEnvios = new JComboBox<>();
        cargarEnvios();
        fondoPanel.add(new JLabel("Seleccione envío a eliminar:"));
        fondoPanel.add(comboEnvios);

        // Botón Eliminar
        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer envioSeleccionado = (Integer) comboEnvios.getSelectedItem();
                if (envioSeleccionado != null) {
                    boolean success = envioDAO.eliminarEnvio(envioSeleccionado);
                    if (success) {
                        JOptionPane.showMessageDialog(VentanaEliminarEnvio.this, "Envío eliminado exitosamente.");
                        cargarEnvios(); // Recargar envíos después de eliminar
                    } else {
                        JOptionPane.showMessageDialog(VentanaEliminarEnvio.this, "Error al eliminar el envío.");
                    }
                }
            }
        });
        fondoPanel.add(btnEliminar);

        // Botón Volver
        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> dispose());
        fondoPanel.add(btnVolver);

        // Establecer el panel de fondo como ContentPane
        setContentPane(fondoPanel);
        setVisible(true);
    }

    // Método para cargar números de envíos en el combo box
    private void cargarEnvios() {
        comboEnvios.removeAllItems();
        List<Integer> envios = envioDAO.obtenerNumerosEnvios();
        for (Integer envio : envios) {
            comboEnvios.addItem(envio);
        }
    }
}



class VentanaMostrar extends JFrame {
    private UsuarioDAO usuarioDAO;
    private EnvioDAO envioDAO;
    private JTable tablaDatos;
    private DefaultTableModel modeloTabla;
    private JButton btnMostrarClientes, btnMostrarEnvios, btnMostrarClientesYEnvios, btnMostrarCantidadEnvios, btnVolver;
    private InterfazGrafica interfazPrincipal; // Agregar referencia a InterfazGrafica

    public VentanaMostrar(InterfazGrafica interfazPrincipal) {
        this.interfazPrincipal = interfazPrincipal; // Guardar la referencia
        usuarioDAO = new UsuarioDAO();
        envioDAO = new EnvioDAO();

        // Configuración de la ventana
        setTitle("Mostrar Información");
        setSize(1200, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el panel de fondo
        String imagePath = "images/fondo.jpg";
        FondoPanel fondoPanel = new FondoPanel(imagePath);
        fondoPanel.setLayout(new BorderLayout());

        // Panel para botones con posiciones absolutas
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(null);
        panelBotones.setPreferredSize(new Dimension(300, 400));
        panelBotones.setOpaque(false); // Hacer el fondo del panel transparente

        // Crear y configurar los botones
        btnMostrarClientes = new JButton("Mostrar Clientes");
        btnMostrarClientes.setBounds(20, 10, 200, 30);
        panelBotones.add(btnMostrarClientes);

        btnMostrarEnvios = new JButton("Mostrar Envios");
        btnMostrarEnvios.setBounds(20, 70, 200, 30);
        panelBotones.add(btnMostrarEnvios);

        btnMostrarClientesYEnvios = new JButton("Mostrar Clientes y Envios");
        btnMostrarClientesYEnvios.setBounds(20, 130, 200, 30);
        panelBotones.add(btnMostrarClientesYEnvios);

        btnMostrarCantidadEnvios = new JButton("Mostrar Cantidad de Envios por Cliente");
        btnMostrarCantidadEnvios.setBounds(20, 190, 200, 30);
        panelBotones.add(btnMostrarCantidadEnvios);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(20, 250, 200, 30);
        panelBotones.add(btnVolver);

        // Tabla de datos
        modeloTabla = new DefaultTableModel();
        tablaDatos = new JTable(modeloTabla);
        tablaDatos.setFillsViewportHeight(true); // Asegurar que se expanda al contenedor
        tablaDatos.setOpaque(true); // Asegura que el fondo sea opaco
        tablaDatos.setBackground(new Color(255, 255, 255, 200)); // Fondo semitransparente
        tablaDatos.setForeground(Color.BLACK); // Texto negroFF
        JScrollPane scrollPane = new JScrollPane(tablaDatos);

        // Configurar transparencia del scroll pane
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Agregar el panel de botones y la tabla al panel de fondo
        fondoPanel.add(panelBotones, BorderLayout.WEST);
        fondoPanel.add(scrollPane, BorderLayout.CENTER);

        // Establecer el panel de fondo como ContentPane
        setContentPane(fondoPanel);

        // Acciones para los botones
        btnMostrarClientes.addActionListener(e -> mostrarClientes());
        btnMostrarEnvios.addActionListener(e -> mostrarEnvios());
        btnMostrarClientesYEnvios.addActionListener(e -> mostrarClientesYEnvios());
        btnMostrarCantidadEnvios.addActionListener(e -> mostrarCantidadEnviosPorCliente());
        btnVolver.addActionListener(e -> {
            dispose(); // Cierra la ventana actual
            interfazPrincipal.setVisible(true); // Muestra la ventana principal
        });

        setVisible(true);
    }

    // Métodos para manejar las acciones (sin cambios)
    private void mostrarClientes() {
        List<Cliente> clientes = usuarioDAO.obtenerClientes();
        actualizarTablaClientes(clientes);
    }

    private void mostrarEnvios() {
        List<Envio> envios = envioDAO.obtenerEnvios();
        actualizarTablaEnvios(envios);
    }

    private void mostrarClientesYEnvios() {
        List<Object[]> clientesYEnvios = usuarioDAO.obtenerClientesYEnvios();
        actualizarTablaClientesYEnvios(clientesYEnvios);
    }

    private void mostrarCantidadEnviosPorCliente() {
        List<Object[]> cantidadEnvios = envioDAO.obtenerCantidadEnviosPorCliente();
        actualizarTablaCantidadEnvios(cantidadEnvios);
    }

    // Métodos para actualizar la tabla (sin cambios)
    private void actualizarTablaClientes(List<Cliente> clientes) {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{"Cedula", "Nombre1", "Nombre2", "Apellido1", "Apellido2"});
        for (Cliente cliente : clientes) {
            modeloTabla.addRow(new Object[]{cliente.getCedula(), cliente.getNombre1(), cliente.getNombre2(), cliente.getApellido1(), cliente.getApellido2()});
        }
        modeloTabla.fireTableDataChanged(); // Notifica cambios
        tablaDatos.revalidate(); // Revalida el componente
        tablaDatos.repaint(); // Repinta la tabla
    }

    private void actualizarTablaEnvios(List<Envio> envios) {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{"Numero_envio", "Cedula_cliente", "Canton_sucursal", "Fecha_envio", "Costo", "Estado_actual", "Cedula_destinatario", "Detalle"});
        for (Envio envio : envios) {
            modeloTabla.addRow(new Object[]{envio.getnumeroEnvio(), envio.getcedulaCliente(), envio.getcantonSucursal(), envio.getFechaEnvio(), envio.getCosto(), envio.getestadoActual(), envio.getcedulaDestinatario(), envio.getdetalle()});
        }
        modeloTabla.fireTableDataChanged(); // Notifica cambios
        tablaDatos.revalidate(); // Revalida el componente
        tablaDatos.repaint(); // Repinta la tabla
    }

    private void actualizarTablaClientesYEnvios(List<Object[]> clientesYEnvios) {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{"Numero_envio", "Cedula_cliente", "Estado_actual"});
        for (Object[] registro : clientesYEnvios) {
            modeloTabla.addRow(registro);
        }
        modeloTabla.fireTableDataChanged(); // Notifica cambios
        tablaDatos.revalidate(); // Revalida el componente
        tablaDatos.repaint(); // Repinta la tabla
    }

    private void actualizarTablaCantidadEnvios(List<Object[]> cantidadEnvios) {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{"Cedula_cliente", "Nombre1", "cantidad_envios"});
        for (Object[] registro : cantidadEnvios) {
            modeloTabla.addRow(registro);
        }
        modeloTabla.fireTableDataChanged(); // Notifica cambios
        tablaDatos.revalidate(); // Revalida el componente
        tablaDatos.repaint(); // Repinta la tabla
    }
    
}


class VentanaLogin extends JFrame {
    public VentanaLogin() {
        setTitle("Login");
        setSize(500, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Usar el FondoPanel con la imagen de fondo
        String imagePath = "images/fondo.jpg"; // Ruta de la imagen de fondo
        FondoPanel fondoPanel = new FondoPanel(imagePath); 
        fondoPanel.setLayout(new BorderLayout()); // Usar BorderLayout para organizar los componentes
        add(fondoPanel, BorderLayout.CENTER);

        // Panel para los campos de usuario y contraseña
        JPanel panelCampos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCampos.setOpaque(false); // Hacer el panel transparente

        // Icono de usuario
        ImageIcon iconUsuario = new ImageIcon("images/Usuario.png");
        iconUsuario = new ImageIcon(iconUsuario.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));  
        JLabel labelUsuario = new JLabel();
        labelUsuario.setIcon(iconUsuario); 
        panelCampos.add(labelUsuario);

        JTextField usuarioField = new JTextField(10);
        panelCampos.add(usuarioField);

        // Icono de contraseña
        ImageIcon iconContraseña = new ImageIcon("images/password.png");
        iconContraseña = new ImageIcon(iconContraseña.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));  
        JLabel labelContraseña = new JLabel();
        labelContraseña.setIcon(iconContraseña);
        panelCampos.add(labelContraseña);

        JPasswordField passwordField = new JPasswordField(10);
        panelCampos.add(passwordField);

        // Panel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setOpaque(false); 
        JButton btnEntrar = new JButton("Entrar");
        JButton btnSalir = new JButton("Salir");

        btnEntrar.addActionListener(e -> {
            String usuario = usuarioField.getText();
            String contraseña = new String(passwordField.getPassword());

            if (ConexionBD.autenticarUsuario(usuario, contraseña)) {
                new InterfazGrafica().setVisible(true);
                this.dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
            }
        });

        btnSalir.addActionListener(e -> {
            System.exit(0); 
        });

        panelBotones.add(btnEntrar);
        panelBotones.add(btnSalir);

        // Añadir los paneles (campos de texto y botones) al panel de fondo
        fondoPanel.add(panelCampos, BorderLayout.CENTER);
        fondoPanel.add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }
}
// Clase FondoPanel para la imagen de fondo
class FondoPanel extends JPanel {
    private Image imagen;

    public FondoPanel(String imagePath) {
        setImagePath(imagePath);
    }

    public void setImagePath(String imagePath) {
        try {
            imagen = new ImageIcon(imagePath).getImage();
            repaint(); // Redibuja el panel para aplicar la nueva imagen
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
