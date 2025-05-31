package DLL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

import javax.swing.JOptionPane;

import BLL.Paciente;
import BLL.Usuario;



public class ControladorTurno {
	//LocalDateTime fechaTurno;
	//Usuario paciente
	//Usuario profesional
	//String tipoSesion
	//String estado // "activo", "cancelado", "completado"
	
	
	private static Connection con = Conexion.getInstance().getConnection();
	
	public boolean crearTurno(int profesionalId, Date fechaTurno, Time horaTurno, String tipoSesion) {
		 String sql = "INSERT INTO turno (fecha_turno, hora_turno, profesional_id, tipo_sesion, estado) "
		 		+ "VALUES (?, ?, ?, ?, 'activo')"; //Inserta los datos en la tabla turno y como default deja estado en activo, tampoco se le pasa paciente ya que eso lo hace un paciente
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDate(1, new java.sql.Date(fechaTurno.getTime()));
			ps.setTime(2, horaTurno);
			ps.setInt(3, profesionalId);
			ps.setString(4, tipoSesion);

			ps.executeUpdate();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: "+ e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean existeTurno(int profesionalId, Date fechaTurno, Time horaTurno) {
	    String sql = "SELECT COUNT(*) FROM turno WHERE profesional_id = ? AND fecha_turno = ? AND hora_turno = ?"; //cuantas filas cumplen con la condicion where
	    
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, profesionalId);
	        ps.setDate(2, fechaTurno);
	        ps.setTime(3, horaTurno);

	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) { //si encontro algo
	        	if (rs.getInt(1) > 0) {
					return true; // devuelve true si el valor de la fila es mayor a 1, por que encontro algo
				}      	
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "ERROR al verificar turno duplicado: " + e.getMessage());
	        return false;
	    }
	    return false;
	}
	
	public boolean reservarTurno(Paciente paciente, int idTurno) {
		String select = "SELECT id FROM usuario WHERE nombre = ? AND apellido = ?";
		int idPacienteReserva = 0;
		try {
			PreparedStatement ps = con.prepareStatement(select);
			ps.setString(1, paciente.getNombre());
			ps.setString(2, paciente.getApellido());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				idPacienteReserva = rs.getInt("id");
			}else {
				JOptionPane.showMessageDialog(null, "No se encontro ningun usuario con ese nombre y apellido");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: "+ e.getMessage());
			return false;
		}
		
		String insertId = "UPDATE turno SET paciente_id = ? WHERE id = ?";	
		
		try {
			PreparedStatement ps = con.prepareStatement(insertId);
			
			ps.setInt(1, idPacienteReserva);
			ps.setInt(2, idTurno);
			ps.executeUpdate();
			
		} catch (Exception e) {
			 JOptionPane.showMessageDialog(null, "ERROR al verificar turno duplicado: " + e.getMessage());
			 return false;
		}
		
		return true;
	}
	

	
}
