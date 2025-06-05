package BLL;


import java.security.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.swing.JOptionPane;

import DLL.ControladorTurno;



public class Turno {
	private ControladorTurno ct = new ControladorTurno();
	private Date fechaTurno;
	private Time horaTurno;
	private Usuario paciente; // Obj Usuario con rol paciente
	private Usuario profesional; // Obj Usuario con rol profesional
	private String tipoSesion; // Tipo de sesion "psicologa", "tarot", etc
	private String estado; // "activo", "cancelado", "completado"
	
	public Turno() {}

	public Turno(Date fechaTurno, Time horaTurno, Usuario paciente, Usuario profesional, String tipoSesion,
			String estado) {
		super();
		this.fechaTurno = fechaTurno;
		this.horaTurno = horaTurno;
		this.paciente = paciente;
		this.profesional = profesional;
		this.tipoSesion = tipoSesion;
		this.estado = estado;
	}
	
	
	
	public Date getFechaTurno() {
		return fechaTurno;
	}

	public void setFechaTurno(Date fechaTurno) {
		this.fechaTurno = fechaTurno;
	}

	public Time getHoraTurno() {
		return horaTurno;
	}

	public void setHoraTurno(Time horaTurno) {
		this.horaTurno = horaTurno;
	}

	public Usuario getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}

	public Usuario getProfesional() {
		return profesional;
	}

	public void setProfesional(Usuario profesional) {
		this.profesional = profesional;
	}

	public String getTipoSesion() {
		return tipoSesion;
	}

	public void setTipoSesion(String tipoSesion) {
		this.tipoSesion = tipoSesion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	
	@Override
	public String toString() {
		return "Turno [fechaTurno=" + fechaTurno + ", horaTurno=" + horaTurno + ", paciente=" + paciente
				+ ", profesional=" + profesional + ", tipoSesion=" + tipoSesion + ", estado=" + estado + "]";
	}
	//Crear Turno
	public boolean crearTurno(int profesionalId, Date fechaTurno, Time horaTurno, String tipoSesion) {
		LocalDateTime fechaHoraTurno = fechaTurno.toLocalDate().atTime(horaTurno.toLocalTime()); //guarda la fecha y hora en una sola variable
		LocalDateTime ahora = LocalDateTime.now(ZoneId.systemDefault()); //Fecha y hora actual para comparar
		
		if (fechaTurno == null || horaTurno == null || 
				tipoSesion == null || tipoSesion.trim().isEmpty()) { //verifica que no este vacio
				JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
				return false;
		}
		if (fechaHoraTurno.isBefore(ahora)) { // verifica que no sea anterior a la fecha actual
			JOptionPane.showMessageDialog(null, "No se puede agendar un turno con fecha anterior a la actual.");
			return false;
		}
		if (ct.existeTurno(profesionalId, fechaTurno, horaTurno)) { //verifica que exista un turno mediante la funcion existeTurno();
			JOptionPane.showMessageDialog(null, "Ya existe un turno para ese profesional en ese horario.");
	        return false;
		}
		return ct.crearTurno(profesionalId, fechaTurno, horaTurno, tipoSesion);	//en caso de que pase todas las validaciones retorna la consulta
	}
	//Traer Lista TURNOS
	public List<Turno> obtenerTurnosPorProfesional(Usuario profesional) {
	    return ct.obtenerTurnosPorProfesional(profesional);
	}

	public List<Turno> obtenerTurnosPorPaciente(Usuario paciente) {
	    return ct.obtenerTurnosPorPaciente(paciente);
	}
	
	
}
