/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahibernate;

import exceptions.VeterinariaException;
import java.io.IOException;
import static javahibernate.JavaHibernate.*;


/**
 *
 * @author MSI
 */
public class menus {
    
    public static void menu1(){
        try {
           
            int option;
            do {
                 showMenu1();
                option = inputMethods.askInt("Elige opcion:");
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
    public static void showMenu1() {
        
        System.out.println("*** Auxiliares ***");
        System.out.println("1. Consulta expedientes");
        System.out.println("0. Cerrar sesion");
    }
    
     public static void menu2(){
         try {
            int option;
            do {
                showMenu2();
                option = inputMethods.askInt("Elige opcion:");
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
    
    public static void showMenu2(){
        System.out.println("*** Veterinarios ***");
        System.out.println("1. Consulta expedientes");
        System.out.println("2. Alta expediente");
        System.out.println("3. Editar expediente");
        System.out.println("4. Baja expediente");
        System.out.println("0. Cerrar sesion");
    }
    
     public static void menu3() {
        try {
            int option;
            do {
                showMenu3();
                option = inputMethods.askInt("Elige opcion:");
                switch (option) {
                    case 1:
                        consultaExpediente();
                        break;
                    case 2:
                        altaExpediente();
                        break;
                    case 3:
                        editarExpediente();
                        break;
                    case 4:
                        bajaExpediente();
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
    public static void showMenu3() {
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
    
   
    
}
