package DLL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

import javax.swing.JOptionPane;

import BLL.Paciente;

public class ControladorPaciente {
	
	private static Connection con = Conexion.getInstance().getConnection();

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
				return false;
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
			 JOptionPane.showMessageDialog(null, e.getMessage());
			 return false;
		}
		return true;
	}
	
	public boolean existeTurnoPaciente(int pacienteId, Date fechaTurno, Time horaTurno) {
	    String sql = "SELECT COUNT(*) FROM turno WHERE paciente_id = ? AND fecha_turno = ? AND hora_turno = ?";
	    
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, pacienteId);
	        ps.setDate(2, fechaTurno);
	        ps.setTime(3, horaTurno);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "ERROR (paciente): " + e.getMessage());
	    }

	    return false;
	}
}
