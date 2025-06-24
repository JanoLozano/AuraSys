package GUI;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import BLL.Turno;
import BLL.Usuario;

public class ProfesionalMenu extends JFrame {

    private Usuario profesional;
    private Turno gestorTurno = new Turno();

    public ProfesionalMenu(Usuario profesional) {
        this.profesional = profesional;

        setTitle("Menú Profesional");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(null);

        JLabel lblBienvenida = new JLabel("Bienvenido, " + profesional.getNombre());
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        lblBienvenida.setBounds(50, 10, 350, 25);
        getContentPane().add(lblBienvenida);

        JButton btnCrearTurno = new JButton("Crear Turno");
        btnCrearTurno.setBounds(125, 50, 200, 30);
        getContentPane().add(btnCrearTurno);

        JButton btnVerTurnos = new JButton("Ver Turnos");
        btnVerTurnos.setBounds(125, 90, 200, 30);
        getContentPane().add(btnVerTurnos);

        JButton btnModificarTurno = new JButton("Modificar Turno");
        btnModificarTurno.setBounds(125, 130, 200, 30);
        getContentPane().add(btnModificarTurno);

        JButton btnSalir = new JButton("Cerrar sesión");
        btnSalir.setBounds(125, 170, 200, 30);
        getContentPane().add(btnSalir);

        // Acción: Crear turno
        btnCrearTurno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String fechaStr = JOptionPane.showInputDialog("Fecha (YYYY-MM-DD):");
                    String horaStr = JOptionPane.showInputDialog("Hora (HH:MM):");
                    String tipoSesion = JOptionPane.showInputDialog("Tipo de sesión:");

                    if (fechaStr == null || horaStr == null || tipoSesion == null) return;

                    Date fecha = Date.valueOf(fechaStr);
                    Time hora = Time.valueOf(horaStr + ":00");

                    boolean exito = gestorTurno.crearTurno(profesional.getId(), fecha, hora, tipoSesion);

                    if (exito) {
                        JOptionPane.showMessageDialog(null, "Turno creado correctamente.");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al crear turno: " + ex.getMessage());
                }
            }
        });

        // Acción: Ver turnos
        btnVerTurnos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Turno> turnos = gestorTurno.obtenerTurnosPorProfesional(profesional);

                if (turnos.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No tenés turnos registrados.");
                    return;
                }

                StringBuilder sb = new StringBuilder();
                for (Turno t : turnos) {
                    sb.append("Fecha: ").append(t.getFechaTurno()).append("\n");
                    sb.append("Hora: ").append(t.getHoraTurno()).append("\n");
                    sb.append("Tipo: ").append(t.getTipoSesion()).append("\n");
                    sb.append("Estado: ").append(t.getEstado()).append("\n");
                    sb.append("------------------------\n");
                }

                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new java.awt.Dimension(400, 200));
                JOptionPane.showMessageDialog(null, scrollPane, "Turnos", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Acción: Modificar turno
        btnModificarTurno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String idStr = JOptionPane.showInputDialog("ID del turno a modificar:");
                    if (idStr == null || idStr.isEmpty()) return;

                    int idTurno = Integer.parseInt(idStr);

                    String fechaStr = JOptionPane.showInputDialog("Nueva fecha (YYYY-MM-DD):");
                    String horaStr = JOptionPane.showInputDialog("Nueva hora (HH:MM):");
                    String tipoSesion = JOptionPane.showInputDialog("Nuevo tipo de sesión:");

                    if (fechaStr == null || horaStr == null || tipoSesion == null) return;

                    Date nuevaFecha = Date.valueOf(fechaStr);
                    Time nuevaHora = Time.valueOf(horaStr + ":00");

                    boolean exito = gestorTurno.modificarTurno(idTurno, nuevaFecha, nuevaHora, tipoSesion);

                    if (exito) {
                        JOptionPane.showMessageDialog(null, "Turno modificado con éxito.");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar turno: " + ex.getMessage());
                }
            }
        });

        // Acción: Cerrar sesión
        btnSalir.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });
    }

    public void run() {
        this.setVisible(true);
    }
}
