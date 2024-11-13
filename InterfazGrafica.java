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
        btnInsertar.addActionListener(e -> new VentanaInsertarOpciones(usuarioDAO, envioDao, this));

        // Acción para abrir la ventana de actualizar usuario
        btnActualizar.addActionListener(e -> new VentanaActualizar());

        // Acción para eliminar usuario
        btnEliminar.addActionListener(e -> {
            JFrame ventanaEliminar = new JFrame("Eliminar");
            ventanaEliminar.setSize(250, 150);
            ventanaEliminar.setLayout(new FlowLayout());
            ventanaEliminar.setLocationRelativeTo(null);
        
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
        
            ventanaEliminar.add(btnEliminarCliente);
            ventanaEliminar.add(btnEliminarEnvio);
            ventanaEliminar.add(btnVolver);
        
            ventanaEliminar.setVisible(true);
        });
        // Acción para abrir la ventana de mostrar usuarios
        btnMostrar.addActionListener(e -> {
            this.setVisible(false);
            new VentanaMostrar();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaLogin::new);
    }
}

// Ventana para Insertar Usuario
class VentanaInsertarOpciones extends JFrame {
    public VentanaInsertarOpciones(UsuarioDAO usuarioDAO, EnvioDAO envioDao, JFrame parent) {
        setTitle("Insertar");
        setSize(300, 200);
        setLocationRelativeTo(parent);

        JButton btnInsertarCliente = new JButton("Insertar Cliente");
        JButton btnInsertarEnvio = new JButton("Insertar Envio");
        JButton btnVolver = new JButton("Volver");

        btnInsertarCliente.addActionListener(e -> new VentanaInsertarCliente(usuarioDAO, this));
        btnInsertarEnvio.addActionListener(e -> new VentanaInsertarEnvio(envioDao, this));
        btnVolver.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        setLayout(new GridLayout(5, 3));
        add(btnInsertarCliente);
        add(btnInsertarEnvio);
        add(btnVolver);

        setVisible(true);
    }
}

// Ventana para insertar cliente
class VentanaInsertarCliente extends JFrame {
    public VentanaInsertarCliente(UsuarioDAO usuarioDAO, JFrame parent) {
        setTitle("Insertar Cliente");
        setSize(300, 200);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(7, 2));
        JTextField txtCedula = new JTextField(10);
        JTextField txtNombre1 = new JTextField(10);
        JTextField txtNombre2 = new JTextField(10);
        JTextField txtApellido1 = new JTextField(10);
        JTextField txtApellido2 = new JTextField(10);
       
        panel.add(new JLabel("Cédula:"));
        panel.add(txtCedula);
        panel.add(new JLabel("Primer Nombre:"));
        panel.add(txtNombre1);
        panel.add(new JLabel("Segundo Nombre:"));
        panel.add(txtNombre2);
        panel.add(new JLabel("Primer Apellido:"));
        panel.add(txtApellido1);
        panel.add(new JLabel("Segundo Apellido:"));
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

        add(panel, BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.add(btnAceptar);
        southPanel.add(btnVolver);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}

// Ventana para insertar envío
class VentanaInsertarEnvio extends JFrame {
    public VentanaInsertarEnvio(EnvioDAO envioDao, JFrame parent) {
        setTitle("Insertar Envio");
        setSize(300, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(10, 2));
        JTextField txtNumeroEnvio = new JTextField();
        JTextField txtCedulaCliente = new JTextField();
        JTextField txtCantonSucursal = new JTextField();
        JTextField txtFechaEnvio = new JTextField();
        JTextField txtCosto = new JTextField();
        JTextField txtEstadoActual = new JTextField();
        JTextField txtCedulaDestinatario = new JTextField();
        JTextField txtDetalle = new JTextField();

        panel.add(new JLabel("Número de Envio:"));
        panel.add(txtNumeroEnvio);
        panel.add(new JLabel("Cédula del cliente:"));
        panel.add(txtCedulaCliente);
        panel.add(new JLabel("Cantón de la sucursal:"));
        panel.add(txtCantonSucursal);
        panel.add(new JLabel("Fecha de Envio (AAAA-MM-DD):"));
        panel.add(txtFechaEnvio);
        panel.add(new JLabel("Costo:"));
        panel.add(txtCosto);
        panel.add(new JLabel("Estado actual del envío:"));
        panel.add(txtEstadoActual);
        panel.add(new JLabel("Cédula del destinatario:"));
        panel.add(txtCedulaDestinatario);
        panel.add(new JLabel("Detalle del envío:"));
        panel.add(txtDetalle);

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnVolver = new JButton("Volver");

        btnAceptar.addActionListener(e -> {
            try {
                double costo = Double.parseDouble(txtCosto.getText());
                if (envioDao.insertarEnvio(txtNumeroEnvio.getText(), txtCedulaCliente.getText(), txtCantonSucursal.getText() ,txtFechaEnvio.getText(), costo, txtEstadoActual.getText(), txtCedulaDestinatario.getText(), txtDetalle.getText())) {
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

        add(panel, BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.add(btnAceptar);
        southPanel.add(btnVolver);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
// Ventana para Actualizar Usuario
class VentanaActualizar extends JFrame {

    public VentanaActualizar() {
        setTitle("Actualizar Datos");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal con opciones de actualización
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnActualizarCliente = new JButton("Actualizar Cliente");
        JButton btnActualizarEnvio = new JButton("Actualizar Envío");
        JButton btnVolver = new JButton("Volver");

        // Acción para abrir la ventana de actualización de cliente
        btnActualizarCliente.addActionListener(e -> new VentanaActualizarCliente().setVisible(true));

        // Acción para abrir la ventana de actualización de envío
        btnActualizarEnvio.addActionListener(e -> new VentanaActualizarEnvio().setVisible(true));

        // Acción para volver (cerrar la ventana actual)
        btnVolver.addActionListener(e -> dispose());

        mainPanel.add(btnActualizarCliente);
        mainPanel.add(btnActualizarEnvio);
        mainPanel.add(btnVolver);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}

// Ventana de actualización de cliente por cédula
class VentanaActualizarCliente extends JFrame {
    private JTextField txtCedula, txtNombre1, txtNombre2, txtApellido1, txtApellido2;

    public VentanaActualizarCliente() {
        setTitle("Actualizar Cliente");
        setSize(300, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        txtCedula = new JTextField(10);
        txtNombre1 = new JTextField(10);
        txtNombre2 = new JTextField(10);
        txtApellido1 = new JTextField(10);
        txtApellido2 = new JTextField(10);

        panel.add(new JLabel("Cédula:"));
        panel.add(txtCedula);
        panel.add(new JLabel("Primer nombre:"));
        panel.add(txtNombre1);
        panel.add(new JLabel("Segundo nombre:"));
        panel.add(txtNombre2);
        panel.add(new JLabel("Primer apellido:"));
        panel.add(txtApellido1);
        panel.add(new JLabel("Segundo apellido:"));
        panel.add(txtApellido2);

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnVolver = new JButton("Volver");

        // Acción para actualizar el cliente en la base de datos
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
        southPanel.add(btnAceptar);
        southPanel.add(btnVolver);

        add(panel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}

// Ventana de actualización de envío por número de envío
class VentanaActualizarEnvio extends JFrame {
    private JTextField txtNumeroEnvio, txtFechaEnvio, txtCosto;

    public VentanaActualizarEnvio() {
        setTitle("Actualizar Envío");
        setSize(300, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(10, 2));
        JTextField txtNumeroEnvio = new JTextField(10);
        JTextField txtCedulaCliente = new JTextField(10);
        JTextField txtCantonSucursal = new JTextField(10);
        JTextField txtFechaEnvio = new JTextField(10);
        JTextField txtCosto = new JTextField(10);
        JTextField txtEstadoActual = new JTextField(10);
        JTextField txtCedulaDestinatario = new JTextField(10);
        JTextField txtDetalle = new JTextField(10);

        panel.add(new JLabel("Número de Envio:"));
        panel.add(txtNumeroEnvio);
        panel.add(new JLabel("Cédula del cliente:"));
        panel.add(txtCedulaCliente);
        panel.add(new JLabel("Cantón de la sucursal:"));
        panel.add(txtCantonSucursal);
        panel.add(new JLabel("Fecha de Envio (AAAA-MM-DD):"));
        panel.add(txtFechaEnvio);
        panel.add(new JLabel("Costo:"));
        panel.add(txtCosto);
        panel.add(new JLabel("Estado actual del envío:"));
        panel.add(txtEstadoActual);
        panel.add(new JLabel("Cédula del destinatario:"));
        panel.add(txtCedulaDestinatario);
        panel.add(new JLabel("Detalle del envío:"));
        panel.add(txtDetalle);

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnVolver = new JButton("Volver");

        // Acción para actualizar el envío en la base de datos
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
        southPanel.add(btnAceptar);
        southPanel.add(btnVolver);

        add(panel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}

// Ventana para Ventana Eliminar Cliente
class VentanaEliminarCliente extends JFrame {
    private JComboBox<String> comboCedulas;
    private JButton btnEliminar, btnVolver;
    private UsuarioDAO usuarioDAO;

    public VentanaEliminarCliente() {
        usuarioDAO = new UsuarioDAO();

        setTitle("Eliminar Cliente");
        setSize(400, 200);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        // Combo box con lista de cédulas
        comboCedulas = new JComboBox<>();
        cargarCedulas();
        add(new JLabel("Seleccione cédula a eliminar:"));
        add(comboCedulas);

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
        add(btnEliminar);

        // Botón Volver
        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> dispose());
        add(btnVolver);
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

// Ventana para Eliminar Envio
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

        // Combo box con lista de envíos
        comboEnvios = new JComboBox<>();
        cargarEnvios();
        add(new JLabel("Seleccione envío a eliminar:"));
        add(comboEnvios);

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
        add(btnEliminar);

        // Botón Volver
        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> dispose());
        add(btnVolver);
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

// Ventana para Mostrar Usuarios en una tabla
class VentanaMostrar extends JFrame {
    private UsuarioDAO usuarioDAO;
    private EnvioDAO envioDAO;
    private JTable tablaDatos;
    private DefaultTableModel modeloTabla;
    private JButton btnMostrarClientes, btnMostrarEnvios, btnMostrarClientesYEnvios, btnMostrarCantidadEnvios, btnVolver;

    public VentanaMostrar() {
        usuarioDAO = new UsuarioDAO();
        envioDAO = new EnvioDAO();

        // Configuración de la ventana
        setTitle("Mostrar Información");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 1, 10, 10));

        btnMostrarClientes = new JButton("Mostrar Clientes");
        btnMostrarEnvios = new JButton("Mostrar Envios");
        btnMostrarClientesYEnvios = new JButton("Mostrar Clientes y Envios");
        btnMostrarCantidadEnvios = new JButton("Mostrar Cantidad de Envios por Cliente");
        btnVolver = new JButton("Volver");

        panelBotones.add(btnMostrarClientes);
        panelBotones.add(btnMostrarEnvios);
        panelBotones.add(btnMostrarClientesYEnvios);
        panelBotones.add(btnMostrarCantidadEnvios);
        panelBotones.add(btnVolver);

        // Tabla de datos
        modeloTabla = new DefaultTableModel();
        tablaDatos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaDatos);

        add(panelBotones, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // Acción para cada botón
        btnMostrarClientes.addActionListener(e -> mostrarClientes());
        btnMostrarEnvios.addActionListener(e -> mostrarEnvios());
        btnMostrarClientesYEnvios.addActionListener(e -> mostrarClientesYEnvios());
        btnMostrarCantidadEnvios.addActionListener(e -> mostrarCantidadEnviosPorCliente());
        btnVolver.addActionListener(e -> dispose()); // Cerrar ventana

        setVisible(true);
    }

// Llamadas a métodos de obtención y actualización de tablas
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

// Métodos para actualizar el modelo de la tabla con diferentes datos
private void actualizarTablaClientes(List<Cliente> clientes) {
    modeloTabla.setRowCount(0); // Limpiar la tabla
    modeloTabla.setColumnIdentifiers(new String[] { "Cedula", "Nombre", "Apellido1", "Apellido2" });
    for (Cliente cliente : clientes) {
        modeloTabla.addRow(new Object[] { cliente.getCedula(), cliente.getNombre1(), cliente.getApellido2(),  cliente.getApellido1(), cliente.getApellido2() });
    }
}

private void actualizarTablaEnvios(List<Envio> envios) {
    modeloTabla.setRowCount(0); // Limpiar la tabla
    modeloTabla.setColumnIdentifiers(new String[] { "ID Envio", "Fecha", "Costo", "Cliente" });
    for (Envio envio : envios) {
        modeloTabla.addRow(new Object[] { envio.getnumeroEnvio(), envio.getcedulaCliente(), envio.getcantonSucursal(),envio.getFechaEnvio(), envio.getCosto(), envio.getestadoActual(), envio.getcedulaDestinatario(), envio.getdetalle()  });
    }
}

private void actualizarTablaClientesYEnvios(List<Object[]> clientesYEnvios) {
    modeloTabla.setRowCount(0); // Limpiar la tabla
    modeloTabla.setColumnIdentifiers(new String[] { "Numero Envio", "Cedula Cliente", "Estado" });
    for (Object[] clienteEnvio : clientesYEnvios) {
        modeloTabla.addRow(clienteEnvio);
    }
}

private void actualizarTablaCantidadEnvios(List<Object[]> cantidadEnvios) {
    modeloTabla.setRowCount(0); // Limpiar la tabla
    modeloTabla.setColumnIdentifiers(new String[] { "Cedula Cliente", "Cantidad Envios" });
    for (Object[] cantidad : cantidadEnvios) {
        modeloTabla.addRow(cantidad);
    }
}


    private void actualizarTablaClientesYEnvios(List<Object[]> clientesYEnvios) {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        modeloTabla.setColumnIdentifiers(new String[] { "Cedula", "Nombre", "ID Envio", "Fecha", "Costo" });
        for (Object[] registro : clientesYEnvios) {
            modeloTabla.addRow(registro);
        }
    }

    private void actualizarTablaCantidadEnvios(List<Object[]> cantidadEnvios) {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        modeloTabla.setColumnIdentifiers(new String[] { "Cedula", "Nombre", "Cantidad Envios" });
        for (Object[] registro : cantidadEnvios) {
            modeloTabla.addRow(registro);
        }
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
                        "C:\\Users\\david\\OneDrive\\Documentos\\VSC proyects\\ProyectoBD\\.vscode\\image\\verdes.jpeg");
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
