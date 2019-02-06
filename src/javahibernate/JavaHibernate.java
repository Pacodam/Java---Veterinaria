/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahibernate;

import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import controller.Manager;
import exceptions.VeterinariaException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.Usuarios;
import java.util.Date;

/**
 *
 * @author alu2017454
 */
public class JavaHibernate {

    private static BufferedReader br;
    private static Manager manager;
    private static Usuarios usuarioLogueado;
    private static Usuarios admin;
    private static Date date;
  
    
    public static void main(String[] args) {
       
        //tipos usuario: 
        //creación de un usuario administrador para empezar a usar la aplicación
        //cuando en la base de datos no hay administradores dados de alta
        Usuarios admin = new Usuarios();
        admin.setNombre("admin");
        admin.setMatricula("admin");
        admin.setPass("admin");
        admin.setTipoUsuario(3);
        
        manager = Manager.getInstance();
        try {
            int option = 0;
            do {
               try{
                System.out.println("*** Clinica Veterinaria STUCOM ***");
                System.out.println("1. Iniciar sesion (TEST USE: admin, admin)");
                System.out.println("0. Salir");
                option = askInt("Opciones:");
                
                switch (option) {
                    case 1:
                        login();
                        break;
                    case 0:
                        System.out.println("Hasta pronto...");
                        break;
                    default:
                        System.out.println("Opcion incorrecta!");
                }
               }catch(VeterinariaException e){
                   System.out.println(e);
               }
            } while (option != 0);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
   /**
    * Login: se pide un nº de matricula (el id del que se loguea) y su pass
    * Luego se evalua en base de datos si es correcto para permitir producir el logueo
    */
   public static void login() throws VeterinariaException {
     
       String matricula = askString("Introduce matricula:");
       String pass = askString("introduce password:");
       if(matricula.equals("admin") && pass.equals("admin")){
           usuarioLogueado = admin;
           menu3();
       }
       usuarioLogueado = manager.checkLogin(matricula, pass); 
       System.out.println("Bienvenido, " + usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellidos());
       System.out.println("\n");
       
       switch(usuarioLogueado.getTipoUsuario()){
           case 1:
               menu1();
               break;
           case 2:
               menu2();
               break;
           case 3:
               menu3();
               break;
           default:
               throw new VeterinariaException(VeterinariaException.WRONG_TYPE);
       }
    }
   
   public static void consultaUsuarios() throws VeterinariaException{
       
       System.out.println("***  USUARIOS EN EL SISTEMA ***\n");
       System.out.println(manager.consultaUsuarios);
       
   }
   
   public static void editarUsuario() throws VeterinariaException, IOException{
       
       System.out.println("***  EDICION USUARIO ***\n");
       
       String matricula = askString("Introduce matricula:");
       String password = askString("Introduce password:");
       Usuarios usuarioModificar = manager.checkLogin(matricula, password);
       boolean exit = false;
       int option;
       String nuevoNombre;
       String nuevoApellido;
       String nuevoPass;
       do{
         edicionOpcionesMenu(usuarioModificar);
            option = askInt("Elige opcion:");
            switch (option) {
                       case 1:
                           nuevoNombre = askString("Nuevo nombre:");
                           usuarioModificar.setNombre(nuevoNombre);
                           break;
                       case 2:
                           nuevoApellido = askString("Nuevo apellido");
                           usuarioModificar.setApellidos(nuevoApellido);
                       case 3:
                           nuevoPass = askString("Nuevo pass:");
                           String nuevoPassConf = askString("Confirma pass:");
                           usuarioModificar.setPass(nuevoPass);
                       case 0:
                           break;
                       default:
                           System.out.println("Opcion incorrecta!");
                   }   
       }while(option != 0);
       
       manager.updateUsuario(usuarioModificar);
       System.out.println("Usuario actualizado");
       
   }
   
   private static void edicionOpcionesMenu(Usuarios u){
        System.out.println("*** Selecciona que modificar: ***");
        System.out.println("1. Nombre ("+ u.getNombre()+")");
        System.out.println("2. Apellidos (" + u.getApellidos()+")");
        System.out.println("3. Password (" + u.getPass()+")");
        System.out.println("0. Salir");
    }
   
   public static void bajaUsuario() throws VeterinariaException{
       
       System.out.println("*** BAJA DE USUARIO ***\n");
       String matricula = askString("Introduce matricula:");
       String password = askString("Introduce password:");
       Usuarios usuarioBorrar = manager.checkLogin(matricula, password);
       manager.borrarUsuario(usuarioBorrar);
       System.out.println("Usuario eliminado");
   }
   
   
      
    public static void altaUsuario() throws VeterinariaException, IOException{
        System.out.println("*** ALTA NUEVO USUARIO ***\n");
        String nombre = askString("Nombre usuario");
        String apellidos = askString("Apellidos:");
        String dni = checkDNI(askString("DNI (sin letra, solo los numeros)"));
        String matricula = askString("Matricula asignada (use tres cifras, ej: 298");
        String password = askString("Password asignado:");
        String passwordConf = askString("confirme password:");
        int tipoUsuario = askInt("Tipo usuario: 1: auxiliar/ 2: veterinario / 3: admin");
        
        Usuarios nuevoUsuario = new Usuarios(nombre, apellidos, dni, matricula, password, tipoUsuario, null, null);
        manager.altaUsuario(nuevoUsuario);
        System.out.println("Nuevo usuario dado de alta en el sistema");
        /*
        chequear:
        Que no haya un usuario con esa matricula, hay que traerse todas las matriculas
        Que el dni sea correcto (y luego añadir la letra, claro)
        
        */
    }
   
    public static String checkDNI(String dn) throws VeterinariaException  {
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
    
     public static String getDNIfull(String dni) {
        String characters="TRWAGMYFPDXBNJZSQVHLCKE";
        int modulo= Integer.parseInt(dni) % 23;
        char letra = characters.charAt(modulo);
        return dni + letra; 
        } 
    
    private static void menu1(){
        try {
           
            int option;
            do {
                 showMenu1();
                option = askInt("Elige opcion:");
                switch (option) {
                    case 1:
                        //consultaExpedientes();
                        break;
                    case 0:
                        System.out.println("Hasta pronto...");
                        break;
                    default:
                        System.out.println("Opcion incorrecta!");
                }
            } while (option != 0);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    private static void showMenu1() {
        
        System.out.println("*** Auxiliares ***");
        System.out.println("1. Consulta expedientes");
        System.out.println("0. Cerrar sesion");
    }
    
     private static void menu2(){
         try {
            int option;
            do {
                showMenu2();
                option = askInt("Elige opcion:");
                switch (option) {
                    case 1:
                        //consultaExpedientes();
                        break;
                    case 2:
                        //altaExpedientes();
                        break;
                    case 3:
                        //editarExpediente();
                        break;
                    case 4:
                        //bajaExpediente();
                        break;
                    case 0:
                        System.out.println("Hasta pronto...");
                        break;
                    default:
                        System.out.println("Opcion incorrecta!");
                }
            } while (option != 0);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private static void showMenu2(){
        System.out.println("*** Veterinarios ***");
        System.out.println("1. Consulta expedientes");
        System.out.println("2. Alta expediente");
        System.out.println("3. Editar expediente");
        System.out.println("4. Baja expediente");
        System.out.println("0. Cerrar sesion");
    }
    
     private static void menu3() {
        try {
            int option;
            do {
                showMenu3();
                option = askInt("Elige opcion:");
                switch (option) {
                    case 1:
                        //consultaExpedientes();
                        break;
                    case 2:
                        //altaExpedientes();
                        break;
                    case 3:
                        //editarExpediente();
                        break;
                    case 4:
                        //bajaExpediente();
                        break;
                    case 5:
                        consultaUsuarios();
                        break;
                    case 6:
                        altaUsuario();
                        break;
                     case 7:
                        editarUsuario(); 
                        break;
                     case 8:
                        bajaUsuario();
                        break;
                    case 0:
                        System.out.println("Hasta pronto...");
                        break;
                    default:
                        System.out.println("Opcion incorrecta!");
                }
            } while (option != 0);
        } catch (IOException | VeterinariaException ex ) {
            System.out.println(ex.getMessage());
        }
    }
    private static void showMenu3() {
        System.out.println("*** Administradores ***");
        System.out.println("1. Consulta expedientes");
        System.out.println("2. Alta expediente");
        System.out.println("3. Editar expediente");
        System.out.println("4. Baja expediente");
        System.out.println("5. Consulta usuarios");
        System.out.println("6. Alta usuario");
        System.out.println("7. Editar usuario");
        System.out.println("8. Baja usuario");
        System.out.println("0. Cerrar sesion");
    }
    
    /**
     * Request int
     *
     * @param message
     * @return int
     * @throws java.io.IOException
     */
    public static int askInt(String message) throws IOException, NumberFormatException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int num = 0;
        boolean error;
        do {
            try {
                System.out.println(message);
                num = Integer.parseInt(br.readLine());
                error = false;
            } catch (IOException ex) {
                System.out.println("Error input / output.");
                error = true;
            } catch (NumberFormatException ex) {
                System.out.println("Please, write integer number.");
                error = true;
            }
        } while (error);
        return num;
    }
    
      /**
     * Request String
     *
     * @param message
     * @return String
     */
    public static String askString(String message) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String answer = "";
        do {
            try {
                System.out.println(message);
                answer = br.readLine();
                if (answer.equals("")) {
                    System.out.println("You must write something.");
                }
            } catch (IOException ex) {
                System.out.println("Error input / output.");
            }
        } while (answer.equals(""));
        return answer;
    }

   
    
}
