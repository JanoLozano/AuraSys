
package GUI;

import BLL.Paciente;
import BLL.Turno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class PacienteMenu extends JFrame {
    private Paciente paciente;

    public PacienteMenu(Paciente paciente) {
        this.paciente = paciente;

        setTitle("Menú Paciente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        setLayout(null);

        // Icono logout
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

        // TabbedPane
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBounds(10, 50, 760, 400);
        add(tabs);

        // Panel 1: Ver turnos disponibles
        JPanel panelDisponibles = new JPanel(null);
        JTable tablaDisponibles = new JTable();
        JScrollPane scrollDisponibles = new JScrollPane(tablaDisponibles);
        scrollDisponibles.setBounds(10, 10, 720, 300);
        panelDisponibles.add(scrollDisponibles);

        JButton btnReservar = new JButton("Reservar turno seleccionado");
        btnReservar.setBounds(10, 320, 250, 30);
        panelDisponibles.add(btnReservar);

        tabs.add("Ver Turnos Disponibles", panelDisponibles);

        // Panel 2: Ver mis turnos
        JPanel panelMisTurnos = new JPanel(null);
        JTable tablaMisTurnos = new JTable();
        JScrollPane scrollMisTurnos = new JScrollPane(tablaMisTurnos);
        scrollMisTurnos.setBounds(10, 10, 720, 360);
        panelMisTurnos.add(scrollMisTurnos);

        tabs.add("Mis Turnos", panelMisTurnos);

        //Lógica cargar datos
        cargarTurnosDisponibles(tablaDisponibles);
        cargarMisTurnos(tablaMisTurnos);

        //Acción: Reservar turno
        btnReservar.addActionListener((ActionEvent e) -> {
            int fila = tablaDisponibles.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccioná un turno disponible");
                return;
            }
            int idTurno = (int) tablaDisponibles.getValueAt(fila, 0);
            Date fecha = Date.valueOf(tablaDisponibles.getValueAt(fila, 1).toString());
            Time hora = Time.valueOf(tablaDisponibles.getValueAt(fila, 2).toString());

            Turno turno = new Turno();
            turno.setFechaTurno(fecha);
            turno.setHoraTurno(hora);

            boolean reservado = paciente.reservarTurno(paciente, idTurno, turno);
            if (reservado) {
                JOptionPane.showMessageDialog(null, "Turno reservado exitosamente.");
                cargarTurnosDisponibles(tablaDisponibles);
                cargarMisTurnos(tablaMisTurnos);
            }
        });
    }

    private void cargarTurnosDisponibles(JTable tabla) {
        List<Turno> turnos = new Turno().obtenerTurnosDisponibles(); // filtra con paciente = null
        String[] columnas = {"ID", "Fecha", "Hora", "Profesional", "Tipo", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Turno t : turnos) {
            if (t.getEstado().equals("activo") && t.getPaciente() == null) {
                modelo.addRow(new Object[]{
                    t.getId(),  // ✅ ID REAL DEL TURNO
                    t.getFechaTurno(),
                    t.getHoraTurno(),
                    t.getProfesional() != null ? t.getProfesional().getNombre() : "",
                    t.getTipoSesion(),
                    t.getEstado()
                });
            }
        }
        tabla.setModel(modelo);
    }

    private void cargarMisTurnos(JTable tabla) {
        List<Turno> turnos = new Turno().obtenerTurnosPorPaciente(paciente);
        String[] columnas = {"Fecha", "Hora", "Profesional", "Tipo", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Turno t : turnos) {
            modelo.addRow(new Object[]{
                t.getFechaTurno(),
                t.getHoraTurno(),
                t.getProfesional() != null ? t.getProfesional().getNombre() : "",
                t.getTipoSesion(),
                t.getEstado()
            });
        }
        tabla.setModel(modelo);
    }

    public void run() {
        this.setVisible(true);
    }
}
