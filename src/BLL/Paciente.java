package BLL;
import javax.swing.JOptionPane;

public class Paciente extends Usuario {

	public Paciente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Paciente(int id, String nombre, String apellido, String contraseña) {
		super(id, nombre, apellido, contraseña);
		// TODO Auto-generated constructor stub
	}
	
	public void menu() {
		JOptionPane.showMessageDialog(null, "Menu paciente");
	}
	
}
