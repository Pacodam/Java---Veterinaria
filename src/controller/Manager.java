
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
        
        /**
         * Actualiza expediente
         * @param expediente Expedientes
         */
        public void updateExpediente(Expedientes expediente) {
            HibernateQueries.updateExpediente(expediente);
        }
        
        /**
         * Borra expediente
         * @param expediente Expedientes
         */
        public void borrarExpediente(Expedientes expediente){
            HibernateQueries.borrarExpediente(expediente);
        }
        
        /**
         * Construye un String a devolver con toda la información de un expediente
         * @param e Expedientes
         * @return String
         */
        public String detailExpediente(Expedientes e){
            System.out.println(e.getApellidos());
            StringBuilder sb = new StringBuilder();
            sb.append("        EXPEDIENTE " + e.getId() + "\n");
            sb.append("*******************************\n");
            sb.append("Nombre completo: " + e.getNombre()+ " " + e.getApellidos()+ "\n");
            sb.append("Dni: " + e.getDni()+ "\n");
            sb.append("CP: " + e.getCp()+ "\n");
            sb.append("Fecha alta: " + e.getFechaAlta()+ "\n");
            sb.append("Telefono: " + e.getTelefono()+ "\n");
            sb.append("Mascotas: " + e.getNMascotas()+ "\n");
            Usuarios usuario = HibernateQueries.getUsuarioById(e.getUsuarios().getId());
            sb.append("Usuario alta: \n");
            sb.append("         Nombre completo: " + usuario.getNombre()+ " " +usuario.getApellidos() + "\n");
            sb.append("         Categoria: " + tipoUsuario(usuario.getTipoUsuario()) + "\n" );
            sb.append("         ID: " + usuario.getId() + "\n");
            System.out.println(sb.toString());
            return sb.toString();
        }
        
        /**
         * Devuelve un expediente buscado por id
         * @param id int
         * @return Expedientes
         */
        public Expedientes getExpediente(int id) {
            
            return HibernateQueries.getExpediente(id);
            
        }
        
        /**
         * Muestra los expedientes printados, la finalidad es que el usuario escoja uno.
         * @return int
         * @throws VeterinariaException 
         */
        public int showExpedientes() throws VeterinariaException {
            
            List<String> exps = HibernateQueries.getExpedientes();
            System.out.println("*** EXPEDIENTES *** \n");
            for(String s: exps){
                System.out.println(s );
               
            }
            System.out.println("0: Salir");
            return  exps.size();
        }
        
        /**
         * Alta de un expediente nuevo
         * @param expediente Expedientes
         */
        public void altaExpediente(Expedientes expediente){
            HibernateQueries.altaExpediente(expediente);
        }
        
        /**
         * Graba el ultimo acceso de un usuario en el sistema
         * @param usuario Usuarios 
         */
        public void setUltimoAcceso(Usuarios usuario) {
          
            usuario.setUltimoAcceso(new Date());
            HibernateQueries.updateUsuario(usuario);
        }
        
        /**
         * Chequea si hay usuario con la matricula y el password y lo devuelve si existe.
         * @param matricula String
         * @param password String
         * @return Usuarios
         * @throws VeterinariaException 
         */
        public Usuarios checkLogin(String matricula, String password) throws VeterinariaException{
           Usuarios user = HibernateQueries.getUser(matricula, password);
           return user;
        }
        
        /**
         * Eliminación de usuario
         * @param usuario Usuarios
         */
        public void borrarUsuario(Usuarios usuario){
            HibernateQueries.borrarUsuario(usuario);
        }
        
        /**
         * Alta de usuario
         * @param usuario Usuarios 
         */
        public void altaUsuario(Usuarios usuario){
            HibernateQueries.altaUsuario(usuario);
        }
        
        /**
         * Actualiza usuario
         * @param usuario Usuarios 
         */
        public void updateUsuario(Usuarios usuario){
            HibernateQueries.updateUsuario(usuario);
        }
        
        /**
         * Consulta de usuarios. Devuelve un String con la información de todos ellos.
         * @return String
         * @throws VeterinariaException 
         */
        public String consultaUsuarios() throws VeterinariaException{
            
            List<Usuarios> usuarios = HibernateQueries.getUsers();
            StringBuilder sb = new StringBuilder();
            for(Usuarios u: usuarios){
                sb.append(usuarioToString(u)+ "\n");
            }
            return sb.toString();
        }
        
        /**
         * Devuelve un String con la información del expediente recibido por parámetro
         * @param e Expedientes
         * @return String
         */
        public String expedienteToString(Expedientes e){
            
            return("ID: " + e.getId() + " | Nombre: " + e.getNombre() + " | Apellidos: " + 
                    e.getApellidos() + " | DNI: "+ e.getDni() + " | Fecha alta: "+ e.getFechaAlta());
        }
        
        /**
         * Devuelve un String con la información del usuario pasado por parámetro
         * @param u
         * @return 
         */
        public static String usuarioToString(Usuarios u){
            
            String tipoUsuario = tipoUsuario(u.getTipoUsuario());
            return("Nombre: " + u.getNombre() + "  Apellidos: " +  u.getApellidos() + " DNI: "+ u.getDni() +
                    " Tipo: "+ tipoUsuario + " Matricula: " + u.getMatricula() + " Password: " + u.getPass());
        }
        
        /**
         * Devuelve el tipo de usuario correspondiente al int recibido
         * @param tipo int
         * @return String
         */
        public static String tipoUsuario(int tipo){
            String tipoUsuario;
            switch(tipo){
                case 1: tipoUsuario = "Auxiliar"; break;
                case 2: tipoUsuario = "Veterinario"; break;
                case 3: tipoUsuario = "Administrador"; break;
                default: tipoUsuario = "Tipo no valido. Chequear";    
            }
            return tipoUsuario;
        }
        
        /**
         * Controla que el formato de dni sea correcto. Lo devuelve con su letra correspondiente.
         * @param dn String
         * @return String
         * @throws VeterinariaException 
         */
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
    
     /**
      * Recibe un String dni sin la letra y lo devuelve con su letra correspondiente
      * @param dni String
      * @return String
      */
     public  String getDNIfull(String dni) {
        String characters="TRWAGMYFPDXBNJZSQVHLCKE";
        int modulo= Integer.parseInt(dni) % 23;
        char letra = characters.charAt(modulo);
        return dni + letra; 
        } 
        
}

