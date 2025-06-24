package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import BLL.Administrador;
import BLL.Usuario;
import BLL.Rol;
import DLL.ControladorAdmin;
import java.awt.*;
import java.util.List;

public class AdminMenu extends JFrame {
    private JTable tablaUsuarios;
    private DefaultTableModel modelo;
    private ControladorAdmin controladorAdmin = new ControladorAdmin();
    private Administrador admin;

    public AdminMenu(Usuario usuario) {
        this.admin = new Administrador(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getContraseña());

        setTitle("Menú Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 520);
        setLayout(null);

        // Botón Logout
        ImageIcon iconoOriginal = new ImageIcon("src/IMG/logout.png");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        ImageIcon iconoLogout = new ImageIcon(imagenEscalada);

        JButton btnLogout = new JButton(iconoLogout);
        btnLogout.setBounds(10, 10, 32, 32);
        btnLogout.setToolTipText("Cerrar sesión");
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setBorderPainted(true);
        add(btnLogout);

        btnLogout.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        // Tabla
        String[] columnas = {"ID", "Nombre", "Apellido", "Roles"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaUsuarios = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        scrollPane.setBounds(20, 60, 800, 280);
        add(scrollPane);

        // Botones
        JButton btnAgregar = new JButton("Registrar Usuario");
        JButton btnEliminar = new JButton("Eliminar Usuario");
        JButton btnModificar = new JButton("Modificar Usuario");
        JButton btnAgregarRol = new JButton("Agregar Rol");
        JButton btnModificarRol = new JButton("Modificar Rol");

        btnAgregar.setBounds(20, 360, 180, 30);
        btnEliminar.setBounds(210, 360, 180, 30);
        btnModificar.setBounds(400, 360, 180, 30);
        btnAgregarRol.setBounds(20, 400, 180, 30);
        btnModificarRol.setBounds(210, 400, 180, 30);

        add(btnAgregar);
        add(btnEliminar);
        add(btnModificar);
        add(btnAgregarRol);
        add(btnModificarRol);

        cargarUsuariosEnTabla();

        btnAgregar.addActionListener(e -> registrarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnModificar.addActionListener(e -> modificarUsuario());
        btnAgregarRol.addActionListener(e -> agregarRol());
        btnModificarRol.addActionListener(e -> modificarRol());
    }

    private void registrarUsuario() {
        JTextField campoNombre = new JTextField();
        JTextField campoApellido = new JTextField();
        JPasswordField campoContraseña = new JPasswordField();

        Object[] campos = {
            "Nombre:", campoNombre,
            "Apellido:", campoApellido,
            "Contraseña:", campoContraseña
        };

        int opcion = JOptionPane.showConfirmDialog(null, campos, "Registrar nuevo usuario", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            String nombre = campoNombre.getText().trim();
            String apellido = campoApellido.getText().trim();
            String contraseña = new String(campoContraseña.getPassword()).trim();

            if (nombre.isEmpty() || apellido.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                return;
            }

            if (admin.registrarUsuario(nombre, apellido, contraseña)) {
                JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.");
                cargarUsuariosEnTabla();
            }
        }
    }

    private void eliminarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccioná un usuario");
            return;
        }

        String nombre = (String) modelo.getValueAt(fila, 1);
        Usuario u = new Usuario();
        u.setNombre(nombre);

        int confirmar = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar a " + nombre + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION && admin.eliminarUsuario(u)) {
            JOptionPane.showMessageDialog(null, "Usuario eliminado");
            cargarUsuariosEnTabla();
        }
    }

    private void modificarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccioná un usuario");
            return;
        }

        String nombre = (String) modelo.getValueAt(fila, 1);
        Usuario u = new Usuario();
        u.setNombre(nombre);

        admin.modificarUsuario(u);
        cargarUsuariosEnTabla();
    }

    private void agregarRol() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccioná un usuario");
            return;
        }

        String nombre = (String) modelo.getValueAt(fila, 1);
        String rolNuevo = JOptionPane.showInputDialog("Rol a agregar:");

        if (rolNuevo == null || rolNuevo.trim().isEmpty()) return;

        Usuario u = new Usuario();
        u.setNombre(nombre);
        Rol r = new Rol();
        r.setNombre(rolNuevo.trim());

        admin.agregarRol(u, r);
        cargarUsuariosEnTabla();
    }

    private void modificarRol() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccioná un usuario");
            return;
        }

        String nombre = (String) modelo.getValueAt(fila, 1);
        String rolActual = JOptionPane.showInputDialog("Rol actual:");
        String rolNuevo = JOptionPane.showInputDialog("Nuevo rol:");

        if (rolActual == null || rolNuevo == null || rolActual.trim().isEmpty() || rolNuevo.trim().isEmpty()) return;

        Usuario u = new Usuario();
        u.setNombre(nombre);
        Rol rActual = new Rol();
        rActual.setNombre(rolActual.trim());
        Rol rNuevo = new Rol();
        rNuevo.setNombre(rolNuevo.trim());

        admin.modificarRolUsuario(u, rActual, rNuevo);
        cargarUsuariosEnTabla();
    }

    private void cargarUsuariosEnTabla() {
        modelo.setRowCount(0);
        List<Usuario> listaUsuarios = controladorAdmin.obtenerTodosLosUsuariosConRoles();
        for (Usuario u : listaUsuarios) {
            String rolesStr = String.join(", ", u.getRoles());
            modelo.addRow(new Object[]{u.getId(), u.getNombre(), u.getApellido(), rolesStr});
        }
    }

    public void run() {
        setVisible(true);
    }
}
