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
import model.Usuarios;
import java.util.Date;
import java.util.HashSet;
import static javahibernate.inputMethods.dni;
import static javahibernate.inputMethods.matriculaUser;
import static javahibernate.inputMethods.password;
import static javahibernate.inputMethods.tipoUsuario;
import model.Expedientes;

/**
 *
 * @author alu2017454
 */
public class JavaHibernate {

    private static BufferedReader br;
    private static Manager manager;
    protected static Usuarios usuarioLogueado;
    
  
    
    public static void main(String[] args) {
       
        
        manager = Manager.getInstance();
        
        //se verifica si en base de datos hay un usuario admin admin, si no se crea.
        Usuarios admin = null;
        if(!manager.existsAdmin()){
            manager.createAdmin();
            System.out.println("Creado usuario para acceso: matricula 'admin' y pass 'admin'");
        }
        else{
            System.out.println("Usuario para acceso: matricula 'admin' y pass 'admin'");
        }
       
        try {
            int option = 0;
            do {
               try{
                System.out.println("*** Clinica Veterinaria STUCOM ***\n");
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
    
    
    /**
     * Muestras los expedientes en el sistema y pide selecionar uno introduciendo el id numerico del mismo
     * si ninguno corresponde a ese id introducido se lanzará una excepción
     * @param type String
     * @return Expedientes
     * @throws VeterinariaException
     * @throws IOException 
     */
    public static Expedientes selectExpediente(String type) throws VeterinariaException, IOException{

        int exp = 0;
         boolean exists = true;
         Expedientes expediente = null;
         do{
            exists = true;
            manager.showExpedientes(); 
            exp = inputMethods.askInt("Introduce ID del expediente a revisar");
            expediente = manager.getExpediente(exp);
            if(exp == 0){
                break;
            }
            if(expediente == null){
              System.out.println("Ese ID no corresponde a ningun expediente. Intente de nuevo.");
              exists = false;
            }
            else{
              return expediente;
            }
         }while(!exists);
       return null;  
    }
    
    /**
     * Edicion de expediente
     * @throws VeterinariaException
     * @throws IOException 
     */
    public static void editarExpediente() throws VeterinariaException, IOException{
        
         System.out.println("*** EDITAR EXPEDIENTE ***\n");
         
         Expedientes expediente = selectExpediente("Introduce ID del expediente a modificar");
         if(expediente != null){
         
            manager.detailExpediente(expediente);

            boolean exit = false;
            int option;
            String nuevoNombre;
            String nuevoApellido;
            String nuevoDni;
            int nuevoTelefono;
            int nuevoCp;
            int nuevoNumMascotas;
            do{
             edicionExpedienteOpcionesMenu(expediente);
               option = inputMethods.askInt("Elige opcion:");
               switch (option) {
                          case 1:
                              nuevoNombre = inputMethods.askString("Nuevo nombre:");
                              expediente.setNombre(nuevoNombre);
                              break;
                          case 2:
                              nuevoApellido = inputMethods.askString("Nuevo apellido");
                              expediente.setApellidos(nuevoApellido);
                              break;
                          case 3:
                              nuevoDni = dni();
                              expediente.setDni(nuevoDni);
                              break;
                          case 4:
                              nuevoTelefono = inputMethods.askInt("Nuevo telefono");
                              expediente.setTelefono(Integer.toString(nuevoTelefono));
                              break;
                          case 5:
                              nuevoCp = inputMethods.askInt("Nuevo codigo postal");
                              expediente.setCp(Integer.toString(nuevoCp));
                              break;
                          case 6:
                              nuevoNumMascotas = inputMethods.askInt("Nuevo numero mascotas");
                              expediente.setNMascotas(nuevoNumMascotas);
                              break;
                          case 0:
                              break;
                          default:
                              System.out.println("Opcion incorrecta!");
                      }   
          }while(option != 0);

          manager.updateExpediente(expediente);
          System.out.println("Expediente actualizado");
         }
    }
    
    /**
    * Seleccion del campo del expediente que se desea modificar
    * @param u Expedientes
    */
   private static void edicionExpedienteOpcionesMenu(Expedientes e){
       
        System.out.println("*** Selecciona que modificar del expediente: ***");
        System.out.println("\n");
        System.out.println("1. Nombre ("+ e.getNombre()+")");
        System.out.println("2. Apellidos ("+ e.getApellidos()+")");
        System.out.println("3. Dni (" + e.getDni()+ ")");
        System.out.println("4. Telefono (" + e.getTelefono()+ ")");
        System.out.println("5. CP (" + e.getCp()+ ")");
        System.out.println("6. Mascotas (" + e.getNMascotas()+ ")");
        System.out.println("0. He terminado");
    }
   
    /**
     * Consulta de expediente
     * @throws VeterinariaException
     * @throws IOException 
     */
    public static void consultaExpediente() throws VeterinariaException, IOException{
        
         System.out.println("*** CONSULTA EXPEDIENTES ***\n");
         
         Expedientes expediente = selectExpediente("Introduce matricula del expediente a revisar");
         if(expediente != null){
           System.out.println(manager.detailExpediente(expediente));
         }
    }
    
    
    /**
     * Baja de expediente
     * @throws VeterinariaException
     * @throws IOException 
     */
    public static void bajaExpediente() throws VeterinariaException, IOException{
        
         System.out.println("*** BAJA EXPEDIENTE ***\n");
         
         Expedientes expediente = selectExpediente("Introduce matricula del expediente a eliminar");
         if(expediente != null){
           manager.borrarExpediente(expediente);
           System.out.println("Expediente eliminado");
         }
    }
    
    /**
     * Nueva alta de expediente
     * @throws VeterinariaException
     * @throws IOException 
     */
    public static void altaExpediente() throws VeterinariaException, IOException{
        
        System.out.println("*** ALTA EXPEDIENTE ***\n");
        
        String nombre = inputMethods.askString("Nombre cliente");
        String apellidos = inputMethods.askString("Apellidos cliente:");
        String dni = dni();
        String cp = inputMethods.askString("Codigo postal:");
        String telefono = inputMethods.askString("Telefono:");
        int numeroMascotas = inputMethods.askInt("Numero de mascotas:");
        
        Expedientes nuevoExpediente = new Expedientes(usuarioLogueado, nombre, apellidos, dni, cp, new Date(), telefono, numeroMascotas);
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
   
   /**
    * Muestra los usuarios en el sistema
    * @throws VeterinariaException 
    */
   public static void consultaUsuarios() throws VeterinariaException{
       
       System.out.println("***  USUARIOS EN EL SISTEMA ***\n");
       System.out.println(manager.consultaUsuarios());
       
   }
   
   /**
    * Edicion de usuario
    * @throws VeterinariaException
    * @throws IOException 
    */
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
                           nuevoPass = password();
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
   
   /**
    * Seleccion del campo del usuario que se desea modificar
    * @param u Usuarios
    */
   private static void edicionOpcionesMenu(Usuarios u){
        System.out.println("*** Selecciona que modificar: ***");
        System.out.println("1. Nombre ("+ u.getNombre()+")");
        System.out.println("2. Apellidos (" + u.getApellidos()+")");
        System.out.println("3. Password (" + u.getPass()+")");
        System.out.println("0. He terminado");
    }
   
   /**
    * Baja de un usuario
    * @throws VeterinariaException 
    */
   public static void bajaUsuario() throws VeterinariaException{
       
       System.out.println("*** BAJA DE USUARIO ***\n");
       String matricula = inputMethods.askString("Introduce matricula:");
       String password = inputMethods.askString("Introduce password:");
       Usuarios usuarioBorrar = manager.checkLogin(matricula, password);
       manager.borrarUsuario(usuarioBorrar);
       System.out.println("Usuario eliminado");
   }
   
   
    /**
     * Alta de un nuevo usuario
     * @throws VeterinariaException
     * @throws IOException 
     */
    public static void altaUsuario() throws VeterinariaException, IOException{
        System.out.println("*** ALTA NUEVO USUARIO ***\n");
        String nombre = inputMethods.askString("Nombre usuario");
        String apellidos = inputMethods.askString("Apellidos:");
        String dni = dni();
        String matricula = matriculaUser();
        String password = password();
        int tipoUsuario = tipoUsuario();
        
        Usuarios nuevoUsuario = new Usuarios(nombre, apellidos, dni, matricula, password, tipoUsuario, null, new HashSet());
        //nuevoUsuario.setId(3); //generarlo sistematicamente
        manager.altaUsuario(nuevoUsuario);
        System.out.println("Nuevo usuario dado de alta en el sistema");
        /*
        chequear:
        Que no haya un usuario con esa matricula, hay que traerse todas las matriculas
        Que el dni sea correcto (y luego añadir la letra, claro)
        
        */
    }
   
    
    
    
}
