
package controller;


import model.Usuarios;

/**
 *
 * @author alu2017454
 */
public class Manager {
    
    
    private Manager() {
     }
    
	private static final Manager instance = new Manager();
	public static Manager getInstance() { return instance; }
        
        public boolean checkLogin(String matricula, String password){
            
            return true;
        }
}
