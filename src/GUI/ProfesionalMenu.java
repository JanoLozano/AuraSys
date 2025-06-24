package GUI;

import BLL.Turno;
import BLL.Usuario;
import DLL.ControladorTurno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Time;

import java.util.List;

public class ProfesionalMenu extends JFrame {
    private Usuario profesional;
    private JTable tablaTurnos;

    public ProfesionalMenu(Usuario profesional) {
        this.profesional = profesional;

        setTitle("Menú Profesional");
        setBounds(100, 100, 850, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Icono Logout
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
        add(btnLogout);

        // Tabla Turnos
        tablaTurnos = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaTurnos);
        scrollPane.setBounds(20, 60, 800, 250);
        add(scrollPane);

        // Botones de estado
        JButton btnActivo = new JButton("Marcar como Activo");
        JButton btnCompletado = new JButton("Marcar como Completado");
        JButton btnCancelado = new JButton("Marcar como Cancelado");

        btnActivo.setBounds(20, 320, 200, 30);
        btnCompletado.setBounds(240, 320, 200, 30);
        btnCancelado.setBounds(460, 320, 200, 30);

        add(btnActivo);
        add(btnCompletado);
        add(btnCancelado);

        // Crear nuevo turno
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(20, 370, 50, 20);
        add(lblFecha);

        JSpinner spinnerFecha = new JSpinner(new SpinnerDateModel());
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "yyyy-MM-dd"));
        spinnerFecha.setBounds(70, 370, 100, 25);
        add(spinnerFecha);

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setBounds(190, 370, 40, 20);
        add(lblHora);

        JSpinner spinnerHora = new JSpinner(new SpinnerDateModel());
        spinnerHora.setEditor(new JSpinner.DateEditor(spinnerHora, "HH:mm"));
        spinnerHora.setBounds(230, 370, 80, 25);
        add(spinnerHora);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(330, 370, 50, 20);
        add(lblTipo);

        JComboBox<String> comboTipo = new JComboBox<>(
                new String[]{"psicologo", "astrologo", "tarotista", "ensueñista", "paciente"}
        );
        comboTipo.setBounds(380, 370, 120, 25);
        add(comboTipo);

        JButton btnCrear = new JButton("Crear Turno");
        btnCrear.setBounds(520, 370, 150, 30);
        add(btnCrear);

        // Lógica botones de estado
        btnActivo.addActionListener(e -> cambiarEstado("activo"));
        btnCompletado.addActionListener(e -> cambiarEstado("completado"));
        btnCancelado.addActionListener(e -> cambiarEstado("cancelado"));

        // Crear turno
        btnCrear.addActionListener((ActionEvent e) -> {
            try {
                Date fecha = new Date(((java.util.Date) spinnerFecha.getValue()).getTime());
                Time hora = new Time(((java.util.Date) spinnerHora.getValue()).getTime());
                String tipo = (String) comboTipo.getSelectedItem();

                Turno nuevoTurno = new Turno();
                boolean creado = nuevoTurno.crearTurno(profesional.getId(), fecha, hora, tipo);

                if (creado) {
                    JOptionPane.showMessageDialog(null, "Turno creado exitosamente.");
                    cargarTurnos();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al crear turno: " + ex.getMessage());
            }
        });

        cargarTurnos();
    }

    private void cargarTurnos() {
        List<Turno> turnos = new Turno().obtenerTurnosPorProfesional(profesional);
        String[] columnas = {"ID", "Fecha", "Hora", "Paciente", "Tipo", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Turno t : turnos) {
            model.addRow(new Object[]{
                    t.getId(),
                    t.getFechaTurno(),
                    t.getHoraTurno(),
                    t.getPaciente() != null ? t.getPaciente().getNombre() : "-",
                    t.getTipoSesion(),
                    t.getEstado()
            });
        }
        tablaTurnos.setModel(model);
    }

    private void cambiarEstado(String nuevoEstado) {
        int fila = tablaTurnos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccioná un turno de la tabla");
            return;
        }
        int idTurno = (int) tablaTurnos.getValueAt(fila, 0);
        ControladorTurno controladorTurno = new ControladorTurno();
        boolean ok = controladorTurno.cambiarEstadoTurno(idTurno, nuevoEstado);

        if (ok) {
            JOptionPane.showMessageDialog(null, "Turno actualizado a: " + nuevoEstado);
            cargarTurnos();
        }
    }

    public void run() {
        this.setVisible(true);
    }
}