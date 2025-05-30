package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import BLL.Usuario;

public class ControladorUsuario {
	
	
	private static Connection con = Conexion.getInstance().getConnection();
	
	//Funcion para Login/Ingresar
	public Usuario login(String nombre, String contraseña) {
	    String sql = "SELECT * FROM usuario WHERE nombre = ? AND contraseña = ?";
	    
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, nombre);
	        ps.setString(2, contraseña);    
	        
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) { //Revias que se haya encontrado algo
	            Usuario u = new Usuario(); //creo objeto Usuario y guardo todos los datos de la sesion logueada
	            u.setId(rs.getInt("id"));
	            u.setNombre(rs.getString("nombre"));
	            u.setContraseña(rs.getString("contraseña"));
	            u.setApellido(rs.getString("apellido"));
	            u.setRoles(new ArrayList<>());
	            
	            int idUsuario = u.getId();

	            //Busco los roles del usuario con JOIN para traer el nombre
	            String rolQuery = "SELECT r.rol FROM rol_usuario ru JOIN rol r ON ru.rol_id = r.id WHERE ru.usuario_id = ?";
	            PreparedStatement psRoles = con.prepareStatement(rolQuery);
	            psRoles.setInt(1, idUsuario);
	            ResultSet rsRoles = psRoles.executeQuery();

	            while (rsRoles.next()) {
	                u.agregarRol(rsRoles.getString("rol")); // Lo guardo en la list que luego se va a usar para saber que nombre tiene el rol y poder traerlo
	            }
	            return u;
	        } else {
	            JOptionPane.showMessageDialog(null, "Nombre o contraseña incorrecta");
	            return null;
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
	        return null;
	    }
	}



	
	
	//Funcion Para Agregar Paciente Profesional, esto le asigna un paciente a un profesional y asi estan conectados
	public void agregarPacienteProfesional(Usuario paciente, Usuario profesional){
		String sqlVerifica = "SELECT * FROM profesional_paciente WHERE paciente_id = ? AND profesional_id = ?";
		
		
		try {
			PreparedStatement psVerifica = con.prepareStatement(sqlVerifica);
		
		psVerifica.setInt(1, paciente.getId());
		psVerifica.setInt(2, profesional.getId());
		
		ResultSet rs = psVerifica.executeQuery();

		if (rs.next()) {
		    JOptionPane.showMessageDialog(null, "Esta relación ya existe.");
		    return;
		}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		
		String sql = "INSERT INTO profesional_paciente (paciente_id, profesional_id) VALUES (?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, paciente.getId());
			ps.setInt(2, profesional.getId());
			
			ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Paciente agregado con exito");
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
		}
	}
}
