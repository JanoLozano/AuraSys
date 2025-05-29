package BLL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import DLL.Conexion;
import DLL.ControladorUsuario;
import repository.ValidacionLogin;

public class Usuario implements ValidacionLogin{
	private ControladorUsuario controlador = new ControladorUsuario();
	private int id;
	private String nombre;
	private String apellido;
	private String contraseña;
	
	public Usuario() {}
	
	public Usuario(int id, String nombre, String apellido, String contraseña) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contraseña = contraseña;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", contraseña=" + contraseña
				+ "]";
	}
	
	//Interface
	@Override
	public boolean validarNombre(String nombre) {
		if (nombre.isEmpty()) {
			 JOptionPane.showMessageDialog(null, "Por favor ingrese un nombre de usuario.");
	            return false;
		}
		return true;
	}
	//Interface
	@Override
	public boolean validarContraseña(String contraseña) {
		if (contraseña.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor ingrese una contraseña.");
			return false;
		}
		return true;
	}
	
	
	//Logica, la llamada a base de datos esta en dll-controladores
	public Usuario login(String nombre, String contraseña) {
	    if (!validarNombre(nombre)) {
	        return null;
	    }
	    if (!validarContraseña(contraseña)) {
	        return null;
	    }

	    Usuario usuarioLogueado = this.controlador.login(nombre, contraseña);
	    return usuarioLogueado;
	}

	
	public void agregarPacienteProfesional(Usuario paciente, Usuario profesional){
		if (paciente == null || profesional == null) {
			JOptionPane.showMessageDialog(null, "Paciente o profesional invalido");
			return;
		}
		if (paciente.getId() == profesional.getId()) {
			JOptionPane.showMessageDialog(null, "Un usuario no puede ser su propio profesional.");
			return;
		}		
		if (paciente.getId() <= 0 || profesional.getId() <= 0) {
		    JOptionPane.showMessageDialog(null, "ID inválido de paciente o profesional.");
		    return;
		}
		this.controlador.agregarPacienteProfesional(paciente, profesional);
	}
	
	public void menu() {
		JOptionPane.showMessageDialog(null, "Menu Usuario");
	}
}
