
package controller;


import exceptions.VeterinariaException;
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
}
