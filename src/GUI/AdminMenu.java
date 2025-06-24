package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BLL.Administrador;
import BLL.Usuario;
import BLL.Rol;
import DLL.ControladorAdmin;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AdminMenu extends JFrame {

    private JTable tablaUsuarios;
    private DefaultTableModel modelo;
    private ControladorAdmin controladorAdmin = new ControladorAdmin();
    private Administrador admin;

    public AdminMenu(Usuario usuario) {
        this.admin = new Administrador(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getContraseña());

        setTitle("Menú Administrador con Tabla");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 450);
        getContentPane().setLayout(new BorderLayout());

        // ==== Botón de Cerrar Sesión (icono) ====
        ImageIcon iconoOriginal = new ImageIcon("src/IMG/logout.png");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon iconoLogout = new ImageIcon(imagenEscalada);

        JButton btnCerrarSesion = new JButton(iconoLogout);
        btnCerrarSesion.setToolTipText("Cerrar sesión");
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBorderPainted(true);
        btnCerrarSesion.setContentAreaFilled(false);

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        panelTop.add(btnCerrarSesion);
        getContentPane().add(panelTop, BorderLayout.NORTH);
        
        btnCerrarSesion.setBorder(BorderFactory.createCompoundBorder(
        	    BorderFactory.createLineBorder(Color.GRAY, 1),
        	    BorderFactory.createEmptyBorder(2, 2, 2, 2)
        	));

        btnCerrarSesion.addActionListener(e -> {
            dispose(); // cerrar ventana actual
            new Login().setVisible(true); // volver al login
        });

        
        String[] columnas = { "ID", "Nombre", "Apellido", "Roles" };
        modelo = new DefaultTableModel(columnas, 0);
        tablaUsuarios = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Panel de botones 
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        JButton btnAgregar = new JButton("Registrar Usuario");
        JButton btnEliminar = new JButton("Eliminar Usuario");
        JButton btnModificar = new JButton("Modificar Usuario");
        JButton btnAgregarRol = new JButton("Agregar Rol");
        JButton btnModificarRol = new JButton("Modificar Rol");
        JButton btnRecargar = new JButton("Recargar Tabla");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnAgregarRol);
        panelBotones.add(btnModificarRol);
        panelBotones.add(btnRecargar);

        
        cargarUsuariosEnTabla();

        

        // Registrar usuario
        btnAgregar.addActionListener(e -> {
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
        });

        // Eliminar usuario
        btnEliminar.addActionListener(e -> {
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
        });

        // Modificar usuario
        btnModificar.addActionListener(e -> {
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
        });

        // Agregar rol
        btnAgregarRol.addActionListener(e -> {
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
        });

        // Modificar rol
        btnModificarRol.addActionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccioná un usuario");
                return;
            }

            String nombre = (String) modelo.getValueAt(fila, 1);
            String rolActual = JOptionPane.showInputDialog("Rol actual:");
            String rolNuevo = JOptionPane.showInputDialog("Nuevo rol:");

            if (rolActual == null || rolNuevo == null || rolActual.trim().isEmpty() || rolNuevo.trim().isEmpty())
                return;

            Usuario u = new Usuario();
            u.setNombre(nombre);
            Rol rActual = new Rol();
            rActual.setNombre(rolActual.trim());
            Rol rNuevo = new Rol();
            rNuevo.setNombre(rolNuevo.trim());

            admin.modificarRolUsuario(u, rActual, rNuevo);
            cargarUsuariosEnTabla();
        });

        // Recargar tabla
        btnRecargar.addActionListener(e -> cargarUsuariosEnTabla());
    }

    private void cargarUsuariosEnTabla() {
        modelo.setRowCount(0); // limpiar tabla

        List<Usuario> listaUsuarios = controladorAdmin.obtenerTodosLosUsuariosConRoles();

        for (Usuario u : listaUsuarios) {
            String rolesStr = String.join(", ", u.getRoles());
            modelo.addRow(new Object[] { u.getId(), u.getNombre(), u.getApellido(), rolesStr });
        }
    }

    public void run() {
        setVisible(true);
    }
}
