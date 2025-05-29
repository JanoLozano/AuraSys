package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BLL.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {
	
	private Usuario u = new Usuario();
	private JPanel contentPane;
	private JTextField inputNombre;
	private JPasswordField inputContra;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 302, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel TituloImg = new JLabel("");
		TituloImg.setIcon(new ImageIcon("C:\\Users\\Jano\\Desktop\\EjemploSimple\\AuraSys\\src\\IMG\\AuraSys_icon_trimmed_128x128-removebg-preview.png"));
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
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Usuario logueado = u.login(inputNombre.getText(), inputContra.getText());
				
				if (logueado == null) {
					JOptionPane.showMessageDialog(null, "no se pudo loguear");
				}else {
					Home home = new Home(logueado);
					home.run(logueado);
				}
				
			}
		});
		btnLogin.setBounds(91, 191, 89, 23);
		contentPane.add(btnLogin);
	}

}
