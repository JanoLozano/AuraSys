package BLL;
import java.util.List;

public class Rol {
	private int id;
	private String nombre;
	
	
	public Rol(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}


	public Rol() {
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	@Override
	public String toString() {
		return "Rol [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
	
	
}
