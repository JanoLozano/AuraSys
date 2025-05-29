package BLL;
import javax.swing.JOptionPane;

class Astrologo extends Usuario{

	public Astrologo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Astrologo(int id, String nombre, String apellido, String contraseña) {
		super(id, nombre, apellido, contraseña);
		// TODO Auto-generated constructor stub
	}
	public void menu() {
		JOptionPane.showMessageDialog(null, "Menu Astrologo");
	}
	
}
