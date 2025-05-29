package repository;


public interface ValidacionLogin {
    
	boolean validarNombre(String nombre);
    
    boolean validarContraseña(String contraseña);
}
