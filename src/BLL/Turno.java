package BLL;


import java.time.LocalDateTime;


public class Turno {
	private LocalDateTime fechaTurno;
	private Usuario paciente; // Obj Usuario con rol paciente
	private Usuario profesional; // Obj Usuario con rol profesional
	private String tipoSesion; // Tipo de sesion "psicologa", "tarot", etc
	private String estado; // "pendiente", "confirmado", "cancelado"
	
	public Turno() {}

	public Turno(LocalDateTime fechaTurno, Usuario paciente, Usuario profesional, String tipoSesion, String estado) {
		super();
		this.fechaTurno = fechaTurno;
		this.paciente = paciente;
		this.profesional = profesional;
		this.tipoSesion = tipoSesion;
		this.estado = estado;
	}

	public LocalDateTime getFechaTurno() {
		return fechaTurno;
	}

	public void setFechaTurno(LocalDateTime fechaTurno) {
		this.fechaTurno = fechaTurno;
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
		return "Turno [fechaTurno=" + fechaTurno + ", paciente=" + paciente + ", profesional=" + profesional
				+ ", tipoSesion=" + tipoSesion + ", estado=" + estado + "]";
	}
	
	
}
