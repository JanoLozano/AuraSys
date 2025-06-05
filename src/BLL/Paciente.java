package BLL;
import javax.swing.JOptionPane;

import DLL.ControladorPaciente;
import DLL.ControladorTurno;



public class Paciente extends Usuario {
	private ControladorPaciente ct = new ControladorPaciente();
	private ControladorTurno ctTurno = new ControladorTurno();

	public Paciente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Paciente(int id, String nombre, String apellido, String contraseña) {
		super(id, nombre, apellido, contraseña);
		// TODO Auto-generated constructor stub
	}
	
	public void menu() {
		JOptionPane.showMessageDialog(null, "Menu paciente");
	}
	
	public boolean reservarTurno(Paciente paciente, int idTurno, Turno turno) {
		if (paciente == null) {
		    JOptionPane.showMessageDialog(null, "Paciente no válido");
		    return false;
		}
		
		if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty() ||
			    paciente.getApellido() == null || paciente.getApellido().trim().isEmpty()) {
			    JOptionPane.showMessageDialog(null, "Nombre y apellido son obligatorios");
			    return false;
		}
		
		if (ct.existeTurnoPaciente(paciente.getId(), turno.getFechaTurno(), turno.getHoraTurno())) {
			JOptionPane.showMessageDialog(null, "Ya tenes un turno con esta fecha y hora");
			return false;
		}
		
		if (!ctTurno.existeTurno(idTurno)) {
			JOptionPane.showMessageDialog(null, "No existe un turno con esa id");
		}
		
		if (!ctTurno.estadoTurno(idTurno)) {
			JOptionPane.showMessageDialog(null, "Este turno no esta disponible");
			return false;
		}
	return ct.reservarTurno(paciente, idTurno);
	}
	
}
