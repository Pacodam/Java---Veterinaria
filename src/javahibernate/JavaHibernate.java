/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahibernate;

//import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import controller.Manager;
import exceptions.VeterinariaException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import model.Usuarios;
import java.util.Date;
import java.util.HashSet;
import model.Expedientes;

/**
 *
 * @author alu2017454
 */
public class JavaHibernate {

    private static BufferedReader br;
    private static Manager manager;
    private static Usuarios usuarioLogueado;
    private static Usuarios admin;
  
    
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
                option = inputMethods.askInt("Opciones:");
                
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
    
    
    public static void editarExpediente() throws VeterinariaException, IOException{
        
         System.out.println("*** EDITAR EXPEDIENTE ***\n");
    }
    
    public static void consultaExpediente() throws VeterinariaException, IOException{
        
         System.out.println("*** CONSULTA EXPEDIENTES ***\n");
    }
    
    
    public static void bajaExpediente() throws VeterinariaException, IOException{
        
         System.out.println("*** BAJA EXPEDIENTE ***\n");
    }
    
    public static void altaExpediente() throws VeterinariaException, IOException{
        
        System.out.println("*** ALTA EXPEDIENTE ***\n");
        
        
        String nombre = inputMethods.askString("Nombre cliente");
        String apellidos = inputMethods.askString("Apellidos cliente:");
        String dni = manager.checkDNI(inputMethods.askString("DNI (sin letra, solo los numeros)"));
        String cp = inputMethods.askString("Codigo postal:");
        String matricula = inputMethods.askString("Matricula asignada (use tres cifras, ej: 298");
        String telefono = inputMethods.askString("Telefono:");
        int numeroMascotas = inputMethods.askInt("Numero de mascotas:");
        
        Expedientes nuevoExpediente = new Expedientes(usuarioLogueado, nombre, apellidos, dni, cp, new Date(), telefono, numeroMascotas);
        nuevoExpediente.setId(2); //generarlo sistematicamente
        manager.altaExpediente(nuevoExpediente);
        System.out.println("Nuevo expediente dado de alta en el sistema");
        
    }
    
   /**
    * Login: se pide un nº de matricula (el id del que se loguea) y su pass
    * Luego se evalua en base de datos si es correcto para permitir producir el logueo
     * @throws exceptions.VeterinariaException
    */
   public static void login() throws VeterinariaException {
       
       System.out.println("***  LOGIN ***\n");
       
       String matricula = inputMethods.askString("Introduce matricula:");
       String pass = inputMethods.askString("introduce password:");
       if(matricula.equals("admin") && pass.equals("admin")){
           usuarioLogueado = admin;
           menus.menu3();
       }
       usuarioLogueado = manager.checkLogin(matricula, pass); 
       manager.setUltimoAcceso(usuarioLogueado);
       System.out.println("Bienvenido, " + usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellidos());
       System.out.println("\n");
       
       switch(usuarioLogueado.getTipoUsuario()){
           case 1:
               menus.menu1();
               break;
           case 2:
               menus.menu2();
               break;
           case 3:
               menus.menu3();
               break;
           default:
               throw new VeterinariaException(VeterinariaException.WRONG_TYPE);
       }
    }
   
   public static void consultaUsuarios() throws VeterinariaException{
       
       System.out.println("***  USUARIOS EN EL SISTEMA ***\n");
       System.out.println(manager.consultaUsuarios());
       
   }
   
   public static void editarUsuario() throws VeterinariaException, IOException{
       
       System.out.println("***  EDICION USUARIO ***\n");
       
       String matricula = inputMethods.askString("Introduce matricula:");
       String password = inputMethods.askString("Introduce password:");
       Usuarios usuarioModificar = manager.checkLogin(matricula, password);
       boolean exit = false;
       int option;
       String nuevoNombre;
       String nuevoApellido;
       String nuevoPass;
       do{
         edicionOpcionesMenu(usuarioModificar);
            option = inputMethods.askInt("Elige opcion:");
            switch (option) {
                       case 1:
                           nuevoNombre = inputMethods.askString("Nuevo nombre:");
                           usuarioModificar.setNombre(nuevoNombre);
                           break;
                       case 2:
                           nuevoApellido = inputMethods.askString("Nuevo apellido");
                           usuarioModificar.setApellidos(nuevoApellido);
                       case 3:
                           nuevoPass = inputMethods.askString("Nuevo pass:");
                           String nuevoPassConf = inputMethods.askString("Confirma pass:");
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
       String matricula = inputMethods.askString("Introduce matricula:");
       String password = inputMethods.askString("Introduce password:");
       Usuarios usuarioBorrar = manager.checkLogin(matricula, password);
       manager.borrarUsuario(usuarioBorrar);
       System.out.println("Usuario eliminado");
   }
   
   
      
    public static void altaUsuario() throws VeterinariaException, IOException{
        System.out.println("*** ALTA NUEVO USUARIO ***\n");
        String nombre = inputMethods.askString("Nombre usuario");
        String apellidos = inputMethods.askString("Apellidos:");
        String dni = manager.checkDNI(inputMethods.askString("DNI (sin letra, solo los numeros)"));
        String matricula = inputMethods.askString("Matricula asignada (use tres cifras, ej: 298");
        String password = inputMethods.askString("Password asignado:");
        String passwordConf = inputMethods.askString("confirme password:");
        int tipoUsuario = inputMethods.askInt("Tipo usuario: 1: auxiliar/ 2: veterinario / 3: admin");
        
        Usuarios nuevoUsuario = new Usuarios(nombre, apellidos, dni, matricula, password, tipoUsuario, null, new HashSet());
        nuevoUsuario.setId(3); //generarlo sistematicamente
        manager.altaUsuario(nuevoUsuario);
        System.out.println("Nuevo usuario dado de alta en el sistema");
        /*
        chequear:
        Que no haya un usuario con esa matricula, hay que traerse todas las matriculas
        Que el dni sea correcto (y luego añadir la letra, claro)
        
        */
    }
   
    
    
    
}
