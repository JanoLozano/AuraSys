package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import BLL.Rol;
import BLL.Usuario;

public class ControladorAdmin {
	
	private static Connection con = Conexion.getInstance().getConnection();
	
	
	public boolean registrarUsuario(String nombre, String apellido, String contraseña) {
	    // Verificar si el nombre ya existe
	    String verificarNombre = "SELECT * FROM usuario WHERE nombre = ?";
	    
	    try {
	        // Preparar la consulta para verificar si ya existe un usuario con ese nombre
	        PreparedStatement ps = con.prepareStatement(verificarNombre);
	        ps.setString(1, nombre);
	        ResultSet rs = ps.executeQuery();

	        // Si existe el nombre, mostrar mensaje y devolver false
	        if (rs.next()) {
	            JOptionPane.showMessageDialog(null, "Ya existe alguien con ese nombre");
	            return false;
	        } else {
	            // Validar que los campos no estén vacíos antes de insertar
	            if (nombre.isEmpty() || apellido.isEmpty() || contraseña.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Ningún campo puede estar vacío.");
	                return false;
	            }
	            
	            // Insertar el usuario si las validaciones son correctas
	            String insertDatos = "INSERT INTO usuario (nombre, apellido, contraseña) VALUES (?,?,?)";
	            PreparedStatement psI = con.prepareStatement(insertDatos);
	            psI.setString(1, nombre);
	            psI.setString(2, apellido);
	            psI.setString(3, contraseña);
	            
	            psI.executeUpdate();
	            JOptionPane.showMessageDialog(null, "Usuario agregado correctamente");
	            return true;
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
	    }
	    return false;
	}
	
	public boolean eliminarUsuario(Usuario nombreUsuario){
		String selectNombreId = "SELECT id FROM usuario WHERE nombre = ?";
		int idNombre = 0;
		
		try {
			PreparedStatement ps = con.prepareStatement(selectNombreId);
			
			ps.setString(1, nombreUsuario.getNombre());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				idNombre = rs.getInt("id");
				
			}else {
				JOptionPane.showMessageDialog(null, "No se encontro ningun ID con ese nombre");
				return false;
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: "+ e.getMessage());
			return false;
		}
		
		String deleteRolUsuario = "DELETE FROM rol_usuario WHERE usuario_id = ?";

		try {
		    PreparedStatement ps1 = con.prepareStatement(deleteRolUsuario);
		    ps1.setInt(1, idNombre);
		    ps1.executeUpdate();
		} catch (Exception e) {
		    JOptionPane.showMessageDialog(null, "ERROR al eliminar de rol_usuario: " + e.getMessage());
		    return false;
		}

		String delete = "DELETE FROM `usuario` WHERE id = ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(delete);
			
			ps.setInt(1, idNombre);
			
			ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Usuario borrado correctamente");
			
			return true;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: "+ e.getMessage());
			return false;
		}	
	}
	
	
	public boolean modificarUsuario(Usuario nombreUsuario) {
		String selectId = "SELECT id FROM usuario WHERE nombre = ?";
		int nombreId = 0;
		
		try {
			PreparedStatement ps = con.prepareStatement(selectId);
			
			ps.setString(1,nombreUsuario.getNombre());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				nombreId = rs.getInt("id");
			}else {
				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: "+ e.getMessage());
			return false;	
		}
		
		String[] opciones = {"Nombre", "Apellido", "Contraseña"};
		
		String seleccion = (String) JOptionPane.showInputDialog(
				null,
				"¿Que datos desea modificar?",
						"Modificar Usuario",
				JOptionPane.QUESTION_MESSAGE,
				null,
				opciones,
				opciones[0]);
		
		if (seleccion == null) {
			return false;
		}
		
		String nuevoValor = JOptionPane.showInputDialog("Ingrese nuevo valor para " + seleccion.toLowerCase());
		
		if (nuevoValor == null || nuevoValor.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No se ingreso ningun valor");
			return false;
		}
		
		String updateSQL ="";
		
		switch (seleccion) {
		case "Nombre":
			updateSQL = "UPDATE usuario SET nombre = ? WHERE id = ?";
			break;
		case "Apellido":
			updateSQL = "UPDATE usuario SET apellido = ? WHERE id = ?";
			break;
		case "Contraseña":
			updateSQL = "UPDATE usuario SET contraseña = ? WHERE id = ?";
			break;
		}
		
		try {
			PreparedStatement ps = con.prepareStatement(updateSQL);
			
			ps.setString(1, nuevoValor);
			ps.setInt(2, nombreId);
			
			ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "valor modificado correctamente");
			
			return true;
			
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: "+ e.getMessage());
			return false;
		}
	}
	
	public boolean agregarRol(Usuario nombreUsuario, Rol nombreRol) {
		
		String selectNombreId = "SELECT id FROM usuario WHERE nombre = ?"; //Consulta
		int idNombre = 0; //Inicializa la variable donde se va a guardar la id encontrada
		
		try {
			PreparedStatement ps = con.prepareStatement(selectNombreId); //Prepara la consulta
			ps.setString(1, nombreUsuario.getNombre());//Seteamos el ? por el nombreUsuario pasado por parametro 
														//getNombre traigo el nombre seteado en el objeto 
														//Usuario usuario = new Usuario(); 
														//usuario.setNombre(nombreUsuario);
														//esto tiene que ser asi por que por parametro le paso un tipo de dato Usuario
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				idNombre = rs.getInt("id");
			}
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: "+ e.getMessage());
		}
		
		String selectRolId = "SELECT id FROM rol WHERE rol = ?";
		int idRol = 0;
		
		try {
			PreparedStatement ps = con.prepareStatement(selectRolId);
			
			ps.setString(1,nombreRol.getNombre());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				idRol = rs.getInt("id");
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: "+ e.getMessage());
			return false;
		}
		
		if (idNombre == 0 || idRol == 0) {
		    JOptionPane.showMessageDialog(null, "No se encontró el usuario o el rol.");
		    return false;
		}
		
		String sqlVerifica = "SELECT * FROM rol_usuario WHERE usuario_id = ? AND rol_id = ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sqlVerifica);
			
			ps.setInt(1, idNombre);
			ps.setInt(2, idRol);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				JOptionPane.showMessageDialog(null, "ya existe un usuario con este rol");
				return false;
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: "+ e.getMessage());
			return false;
		}
		
		String sqlRolIds = "SELECT rol_id FROM rol_usuario WHERE usuario_id = ?";
		ArrayList<Integer> rolIds = new ArrayList<>();

		try {
		    PreparedStatement ps = con.prepareStatement(sqlRolIds);
		    ps.setInt(1, idNombre);
		    ResultSet rs = ps.executeQuery();

		    while (rs.next()) {
		        rolIds.add(rs.getInt("rol_id"));
		    }

		    //verificar los nombres de los roles
		    for (int rolId : rolIds) {
		        String sqlNombreRol = "SELECT rol FROM rol WHERE id = ?";
		        PreparedStatement ps2 = con.prepareStatement(sqlNombreRol);
		        ps2.setInt(1, rolId);
		        ResultSet rs2 = ps2.executeQuery();

		        if (rs2.next()) {
		            String rolExistente = rs2.getString("rol");

		            // el usuario ya es paciente y se intenta agregar otro rol
		            if (rolExistente.equals("paciente") && !nombreRol.getNombre().equals("paciente")) {
		                JOptionPane.showMessageDialog(null, "Este usuario ya es paciente y no puede tener otros roles.");
		                return false;
		            }

		            // el usuario ya tiene un rol distinto y se intenta agregar paciente
		            if (!rolExistente.equals("paciente") && nombreRol.getNombre().equals("paciente")) {
		                JOptionPane.showMessageDialog(null, "Este usuario ya tiene un rol y no puede convertirse en paciente.");
		                return false;
		            }
		        }
		    }
		} catch (Exception e) {
		    JOptionPane.showMessageDialog(null, "ERROR al verificar roles existentes: " + e.getMessage());
		    return false;
		}
		
		String sqlInsert = "INSERT INTO `rol_usuario`(`usuario_id`, `rol_id`) VALUES (?,?)";
		
		
		try {
			PreparedStatement ps = con.prepareStatement(sqlInsert);
			
			ps.setInt(1, idNombre);
			ps.setInt(2, idRol);
			
			ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Agregado correctamente");
			return true;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: "+ e.getMessage());
			return false;
		}	
	}
	
	public boolean modificarRol(Usuario nombreUsuario, Rol nombreRol, Rol nombreRolModificado) {
	    int idNombre = 0;
	    int idRol = 0;
	    int idRolModificado = 0;

	    // Obtener ID del usuario
	    try {
	        String selectIdNombre = "SELECT id FROM usuario WHERE nombre = ?";
	        PreparedStatement ps = con.prepareStatement(selectIdNombre);
	        ps.setString(1, nombreUsuario.getNombre());
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            idNombre = rs.getInt("id");
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "ERROR al buscar ID del usuario: " + e.getMessage());
	        return false;
	    }

	    // Obtener ID del rol actual
	    try {
	        String selectIdRol = "SELECT id FROM rol WHERE rol = ?";
	        PreparedStatement ps = con.prepareStatement(selectIdRol);
	        ps.setString(1, nombreRol.getNombre());
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            idRol = rs.getInt("id");
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "ERROR al buscar ID del rol actual: " + e.getMessage());
	        return false;
	    }

	    // Obtener ID del nuevo rol
	    try {
	        String selectIdRolModificado = "SELECT id FROM rol WHERE rol = ?";
	        PreparedStatement ps = con.prepareStatement(selectIdRolModificado);
	        ps.setString(1, nombreRolModificado.getNombre());
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            idRolModificado = rs.getInt("id");
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "ERROR al buscar ID del nuevo rol: " + e.getMessage());
	        return false;
	    }

	    // Verificar si ya existe ese rol para ese usuario
	    try {
	        String sqlVerifica = "SELECT * FROM rol_usuario WHERE usuario_id = ? AND rol_id = ?";
	        PreparedStatement ps = con.prepareStatement(sqlVerifica);
	        ps.setInt(1, idNombre);
	        ps.setInt(2, idRolModificado);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            JOptionPane.showMessageDialog(null, "Ya existe un usuario con este rol.");
	            return false;
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "ERROR al verificar existencia del nuevo rol: " + e.getMessage());
	        return false;
	    }

	    // Actualizar rol
	    try {
	        String updateIdRolUsuario = "UPDATE rol_usuario SET rol_id = ? WHERE usuario_id = ? AND rol_id = ?";
	        PreparedStatement ps = con.prepareStatement(updateIdRolUsuario);
	        ps.setInt(1, idRolModificado); // nuevo rol
	        ps.setInt(2, idNombre);        // id del usuario
	        ps.setInt(3, idRol);           // rol anterior que se desea reemplazar

	        int filasAfectadas = ps.executeUpdate();

	        if (filasAfectadas > 0) {
	            JOptionPane.showMessageDialog(null, "Rol modificado con éxito.");
	            return true;
	        } else {
	            JOptionPane.showMessageDialog(null, "No se encontró ese rol para ese usuario.");
	            return false;
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "ERROR al actualizar el rol: " + e.getMessage());
	        return false;
	    }
	}
	
	public boolean eliminarTurno(int idTurno) {
		String sql = "DELETE FROM turno WHERE id = ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idTurno);
			ps.executeUpdate();
			return true;
			
		} catch (Exception e) {
			 JOptionPane.showMessageDialog(null, e.getMessage());
			 return false;			 
		}
	}
	
}
