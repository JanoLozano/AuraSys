package GUI;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import BLL.Paciente;
import BLL.Usuario;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

    private JPanel contentPane;
    private JTextField inputNombre;
    private JPasswordField inputContra;
    private Usuario u = new Usuario();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Login frame = new Login();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 302, 302);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel TituloImg = new JLabel("");
        TituloImg.setIcon(new ImageIcon("src\\IMG\\AuraSys_icon_trimmed_128x128-removebg-preview.png"));
        TituloImg.setBounds(70, 0, 130, 78);
        contentPane.add(TituloImg);

        JLabel nombre = new JLabel("Nombre");
        nombre.setHorizontalAlignment(SwingConstants.CENTER);
        nombre.setBounds(80, 79, 111, 14);
        contentPane.add(nombre);

        inputNombre = new JTextField();
        inputNombre.setBounds(84, 104, 107, 20);
        contentPane.add(inputNombre);
        inputNombre.setColumns(10);

        JLabel contraseña = new JLabel("Contraseña");
        contraseña.setHorizontalAlignment(SwingConstants.CENTER);
        contraseña.setBounds(84, 135, 107, 14);
        contentPane.add(contraseña);

        inputContra = new JPasswordField();
        inputContra.setBounds(84, 160, 107, 20);
        contentPane.add(inputContra);

        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setBounds(91, 191, 89, 23);
        contentPane.add(btnLogin);
        getRootPane().setDefaultButton(btnLogin); // ENTER = botón ingresar

        // Acción al hacer clic en "Ingresar"
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = inputNombre.getText();
                String contraseña = new String(inputContra.getPassword());

                Usuario logueado = u.login(nombre, contraseña);

                if (logueado == null) {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                    return;
                }

                if (logueado.tieneRol("admin")) {
                    AdminMenu adminMenu = new AdminMenu(logueado);
                    adminMenu.run();
                } else if (logueado.tieneRol("paciente")) {
                	PacienteMenu pacienteMenu = new PacienteMenu((Paciente) logueado);
                	pacienteMenu.run();
                } else {
                    ProfesionalMenu profesionalMenu = new ProfesionalMenu(logueado);
                    profesionalMenu.run();
                }

                dispose(); // cerrar login
            }
        });

    }
}
