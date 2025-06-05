package DLL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JOptionPane;

import BLL.Paciente;
import BLL.Turno;
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
		 		+ "VALUES (?, ?, ?, ?, 'activo')"; //Inserta los datos en la tabla turno y como default deja estado en activo, 
		 											//tampoco se le pasa paciente ya que eso lo hace un paciente
		
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
	
	public boolean cambiarEstadoTurno(int idTurno, String estadoTurno) {
		String sql = "UPDATE turno SET estado = ? WHERE id = ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, estadoTurno);
			ps.setInt(2, idTurno);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR al cambiar estado del turno: " + e.getMessage());
	        return false;
		}
		
		
	}
	
	public List<Turno> obtenerTurnosPorProfesional(Usuario profesional) {
		List<Turno> turnos = new ArrayList<>();
		
		String sql = "SELECT * FROM turno WHERE profesional_id = ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, profesional.getId());
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Turno turno = new Turno();
				turno.setFechaTurno(rs.getDate("fecha_turno"));
	            turno.setHoraTurno(rs.getTime("hora_turno"));
	            turno.setTipoSesion(rs.getString("tipo_sesion"));
	            turno.setEstado(rs.getString("estado"));
	            
	            turno.setProfesional(profesional);
	            turnos.add(turno);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR al obtener turnos del profesional: " + e.getMessage());
		}
		
		return turnos;
	}
	
	public List<Turno> obtenerTurnosPorPaciente(Usuario paciente) {
		
	    List<Turno> turnos = new ArrayList<>();
	    String sql = "SELECT * FROM turno WHERE paciente_id = ?";
	    
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, paciente.getId());
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Turno turno = new Turno();
	            turno.setFechaTurno(rs.getDate("fecha_turno"));
	            turno.setHoraTurno(rs.getTime("hora_turno"));
	            turno.setTipoSesion(rs.getString("tipo_sesion"));
	            turno.setEstado(rs.getString("estado"));

	            turno.setPaciente(paciente);
	            turnos.add(turno);
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "ERROR al obtener turnos del paciente: " + e.getMessage());
	    }

	    return turnos;
	}

	//validaciones para turnos
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
	        JOptionPane.showMessageDialog(null, e.getMessage());
	        return false;
	    }
	    return false;
	}
	public boolean estadoTurno(int idTurno) {
		String sql = "SELECT * FROM `turno` WHERE id = ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);	
			ps.setInt(1, idTurno);		
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				String estado = rs.getString("estado");
				
				if (estado.trim().equalsIgnoreCase("activo")) {
					return true;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
		
		return false;
	}
	public boolean existeTurno(int idTurno) {
	    String sql = "SELECT COUNT(*) FROM turno WHERE id = ?";
	    
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, idTurno);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            if (rs.getInt(1) > 0) {
					return true;
				}
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e.getMessage());
	        return false;
	    }
	    return false;
	}

	
}
