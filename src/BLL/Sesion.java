package BLL;

import java.time.LocalDateTime;

public class Sesion {
	private Turno turno; //Relacion con el turno
	private String resumen;
	private String diagnostico;
	private LocalDateTime fecha;
	public Sesion(Turno turno, String resumen, String diagnostico, LocalDateTime fecha) {
		super();
		this.turno = turno;
		this.resumen = resumen;
		this.diagnostico = diagnostico;
		this.fecha = fecha;
	}
	public Turno getTurno() {
		return turno;
	}
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	public String getDiagnostico() {
		return diagnostico;
	}
	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return "Sesion [turno=" + turno + ", resumen=" + resumen + ", diagnostico=" + diagnostico + ", fecha=" + fecha
				+ "]";
	}
	
	

}
