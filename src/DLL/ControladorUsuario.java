package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import BLL.Administrador;
import BLL.Paciente;
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
	            int id = rs.getInt("id");
	            String nom = rs.getString("nombre");
	            String ape = rs.getString("apellido");
	            String pass = rs.getString("contraseña");

	            // Obtener roles
	            String rolSql = "SELECT r.rol FROM rol_usuario ru JOIN rol r ON ru.rol_id = r.id WHERE ru.usuario_id = ?";
	            PreparedStatement psRol = con.prepareStatement(rolSql);
	            psRol.setInt(1, id);
	            ResultSet rsRoles = psRol.executeQuery();

	            List<String> roles = new ArrayList<>();
	            while (rsRoles.next()) {
	                roles.add(rsRoles.getString("rol"));
	            }

	            Usuario u;

	            if (roles.contains("admin")) {
	                u = new Administrador(id, nom, ape, pass);
	            } else if (roles.contains("paciente")) {
	                u = new Paciente(id, nom, ape, pass);
	            } else {
	                u = new Usuario(id, nom, ape, pass); // Profesional u otro
	            }

	            u.setRoles(new ArrayList<>(roles));
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
	public Usuario obtenerUsuarioPorId(int id) {
	    String sql = "SELECT * FROM usuario WHERE id = ?";
	    
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            Usuario u = new Usuario();
	            u.setId(rs.getInt("id"));
	            u.setNombre(rs.getString("nombre"));
	            u.setApellido(rs.getString("apellido"));
	            return u;
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "ERROR al obtener usuario: " + e.getMessage());
	    }
	    return null;
	}

}
