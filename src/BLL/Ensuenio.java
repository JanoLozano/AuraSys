package BLL;
import javax.swing.JOptionPane;

class Ensuenio extends Usuario{

	public Ensuenio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ensuenio(int id, String nombre, String apellido, String contraseña) {
		super(id, nombre, apellido, contraseña);
		// TODO Auto-generated constructor stub
	}
	public void menu() {
		JOptionPane.showMessageDialog(null, "Menu Ensueño");
	}
	
}
