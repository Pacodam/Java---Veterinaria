
package controller;


import exceptions.VeterinariaException;
import java.util.ArrayList;
import java.util.List;
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
        
        public String usuarioToString(Usuarios u){
            
            return(u.getNombre() + " " +  u.getApellidos() + " "+ u.getDni() + " "+ u.getTipoUsuario());
        }
        
}

