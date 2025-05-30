package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BLL.Usuario;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Home extends JFrame {

	private JPanel contentPane;

			public void run(Usuario u) {
				try {
					Home frame = new Home(u);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
	public Home(Usuario u) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel textoBienvenido = new JLabel("Bienvendio " + u.getNombre() + " a AuraSys");
		textoBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
		textoBienvenido.setBounds(88, 32, 238, 14);
		contentPane.add(textoBienvenido);
	}

}
