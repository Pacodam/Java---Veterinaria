
package controller;


import exceptions.VeterinariaException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Expedientes;
import model.Usuarios;
import persistence.HibernateQueries;

/**
 *
 * @author alu2017454
 */
public class Manager {
    
    
    private Manager() {
     }
    
	private static final Manager instance = new Manager();
	public static Manager getInstance() { return instance; }
        
        
        public void altaExpediente(Expedientes expediente){
            HibernateQueries.altaExpediente(expediente);
        }
        
        public void setUltimoAcceso(Usuarios usuario) {
          
            usuario.setUltimoAcceso(new Date());
            HibernateQueries.updateUsuario(usuario);
        }
        
        public Usuarios checkLogin(String matricula, String password) throws VeterinariaException{
           Usuarios user = HibernateQueries.getUser(matricula, password);
           return user;
        }
        
        public void borrarUsuario(Usuarios usuario){
            HibernateQueries.borrarUsuario(usuario);
        }
        
        public void altaUsuario(Usuarios usuario){
            HibernateQueries.altaUsuario(usuario);
        }
        
        public void updateUsuario(Usuarios usuario){
            HibernateQueries.updateUsuario(usuario);
        }
        
        public String consultaUsuarios() throws VeterinariaException{
            
            List<Usuarios> usuarios = HibernateQueries.getUsers();
            StringBuilder sb = new StringBuilder();
            for(Usuarios u: usuarios){
                sb.append(usuarioToString(u)+ "\n");
            }
            return sb.toString();
        }
        
        public String expedienteToString(Expedientes e){
            
            return("ID Expediente: " + e.getId() + "Nombre: " + e.getNombre() + "  Apellidos: " + 
                    e.getApellidos() + " DNI: "+ e.getDni() + " Fecha alta: "+ e.getFechaAlta());
        }
        
        public static String usuarioToString(Usuarios u){
            
            String tipoUsuario;
            switch(u.getTipoUsuario()){
                case 1: tipoUsuario = "Auxiliar"; break;
                case 2: tipoUsuario = "Veterinario"; break;
                case 3: tipoUsuario = "Administrador"; break;
                default: tipoUsuario = "Tipo no valido. Chequear";    
            }
            
            return("Nombre: " + u.getNombre() + "  Apellidos: " +  u.getApellidos() + " DNI: "+ u.getDni() +
                    " Tipo: "+ tipoUsuario + " Matricula: " + u.getMatricula() + " Password: " + u.getPass());
        }
        
        
        public  String checkDNI(String dn) throws VeterinariaException  {
    	int dni;
    	try {
    		dni = Integer.parseInt(dn);
      	    if(dn.length() != 8) {
      		  throw new VeterinariaException(VeterinariaException.DNI_INCORRECT_SIZE);
      	    }
      	}catch(NumberFormatException e) {
      		throw new VeterinariaException(VeterinariaException.DNI_INCORRECT_NUM);
      	}
    	return getDNIfull(dn);	
    }
    
     public  String getDNIfull(String dni) {
        String characters="TRWAGMYFPDXBNJZSQVHLCKE";
        int modulo= Integer.parseInt(dni) % 23;
        char letra = characters.charAt(modulo);
        return dni + letra; 
        } 
        
}

