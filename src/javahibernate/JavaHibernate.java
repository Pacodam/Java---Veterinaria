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
           showMenu3();
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
      
    public static void altaUsuario(){
        System.out.println("*** ALTA NUEVO USUARIO ***");
        System.out.println("Nombre usuario:");
        String nombre = askString("Nombre usuario");
        String apellidos = askString("Apellidos:");
        //int dni = askInt("DNI (sin letra, solo los numeros)");
        String matricula = askString("Matricula asignada (use tres cifras, ej: 298");
        String password = askString("Password asignado:");
        /*
        chequear:
        Que no haya un usuario con esa matricula, hay que traerse todas las matriculas
        Que el dni sea correcto (y luego añadir la letra, claro)
        
        */
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
    
     private static void menu3(){
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
                        //consultaUsuarios();
                        break;
                    case 6:
                        altaUsuario();
                        break;
                     case 7:
                        //editarUsuario(); //checkUsuario
                        break;
                     case 8:
                        //bajaUsuario();
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
