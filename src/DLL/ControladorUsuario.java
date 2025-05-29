package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	        
	        if (rs.next()) {
	            Usuario u = new Usuario();
	            u.setNombre(rs.getString("nombre"));
	            u.setContraseña(rs.getString("contraseña"));
	            return u;
	        } else {
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
