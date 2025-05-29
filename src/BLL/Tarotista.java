package BLL;
import javax.swing.JOptionPane;

class Tarotista extends Usuario{

	public Tarotista() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tarotista(int id, String nombre, String apellido, String contraseña) {
		super(id, nombre, apellido, contraseña);
		// TODO Auto-generated constructor stub
	}
	public void menu() {
		JOptionPane.showMessageDialog(null, "Menu Tarotista");
	}

}
