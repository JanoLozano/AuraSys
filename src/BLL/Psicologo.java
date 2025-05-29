package BLL;
import javax.swing.JOptionPane;

class Psicologo extends Usuario{

	public Psicologo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Psicologo(int id, String nombre, String apellido, String contraseña) {
		super(id, nombre, apellido, contraseña);
		// TODO Auto-generated constructor stub
	}
	
	public void menu() {
		JOptionPane.showMessageDialog(null, "Menu Psicologo");
	}
}
