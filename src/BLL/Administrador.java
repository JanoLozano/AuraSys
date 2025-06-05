package BLL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import DLL.ControladorAdmin;
import DLL.ControladorTurno;

public class Administrador extends Usuario{
	private ControladorAdmin controlador = new ControladorAdmin();
	private ControladorTurno ctTurno = new ControladorTurno();
	public Administrador() {
		super();
		
	}

	public Administrador(int id, String nombre, String apellido, String contraseña) {
		super(id, nombre, apellido, contraseña);
		
	}
	
	public boolean registrarUsuario(String nombre, String apellido, String contraseña) { //Validaciones Registrar Usuario
		if (nombre == null || nombre.trim().isEmpty() ||
	         apellido == null || apellido.trim().isEmpty() ||
		     contraseña == null || contraseña.trim().isEmpty()){//si esta vacio, nulo o si son solo espacios en blanco
		            
	            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
	            return false;
		} 
		return this.controlador.registrarUsuario(nombre, apellido, contraseña); //Ejectua la consulta si paso todas las validaciones
		
	}
	
	//Validaciones Eliminar Usuario
	public boolean eliminarUsuario(Usuario nombreUsuario){
		if (nombreUsuario == null || nombreUsuario.getNombre() == null || nombreUsuario.getNombre().trim().isEmpty()) {
		    JOptionPane.showMessageDialog(null, "Debe proporcionar un usuario válido para modificar.");
		    return false;
		}
		
		return this.controlador.eliminarUsuario(nombreUsuario);
	}
	
	
	public boolean modificarUsuario(Usuario nombreUsuario) {
		if (nombreUsuario == null || nombreUsuario.getNombre() == null || nombreUsuario.getNombre().trim().isEmpty()) {
		    JOptionPane.showMessageDialog(null, "Debe proporcionar un usuario válido para modificar.");
		    return false;
		}else {
			this.controlador.modificarUsuario(nombreUsuario);
			return true;
		}
		
	}

	public boolean agregarRol(Usuario nombreUsuario, Rol nombreRol) {
		if (nombreUsuario == null) {
		    JOptionPane.showMessageDialog(null, "El usuario no puede ser nulo.");
		    return false;
		}
		if (nombreRol == null) {
		    JOptionPane.showMessageDialog(null, "El rol no puede ser nulo.");
		    return false;
		}
		if (nombreUsuario.getNombre() == null || nombreUsuario.getNombre().trim().isEmpty()) {
		    JOptionPane.showMessageDialog(null, "El nombre del usuario es obligatorio.");
		    return false;
		}

		if (nombreRol.getNombre() == null || nombreRol.getNombre().trim().isEmpty()) {
		    JOptionPane.showMessageDialog(null, "El nombre del rol es obligatorio.");
		    return false;
		}
		
		return this.controlador.agregarRol(nombreUsuario, nombreRol);
		
	}
	
	 public boolean modificarRolUsuario(Usuario usuario, Rol rolActual, Rol rolNuevo) {
	        if (usuario == null || rolActual == null || rolNuevo == null) {
	            JOptionPane.showMessageDialog(null, "Faltan datos obligatorios.");
	            return false;
	        }

	        if (usuario.getNombre().trim().isEmpty() || rolActual.getNombre().trim().isEmpty() 
	        	|| rolNuevo.getNombre().trim().isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Los nombres no pueden estar vacíos.");
	            return false;
	        }

	        if (rolActual.getNombre().equals(rolNuevo.getNombre())) {
	            JOptionPane.showMessageDialog(null, "El nuevo rol no puede ser igual al actual.");
	            return false;
	        }

	        return this.controlador.modificarRol(usuario, rolActual, rolNuevo);       
	    }
	 public boolean eliminarTurno(int idTurno) {
		 if (!ctTurno.existeTurno(idTurno)) {
			 JOptionPane.showMessageDialog(null, "El turno no existe.");
			    return false;
		}
		 
		 return controlador.eliminarTurno(idTurno);
	 }
	 
}
