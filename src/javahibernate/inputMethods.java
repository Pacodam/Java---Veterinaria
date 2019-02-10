/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahibernate;

import controller.Manager;
import exceptions.VeterinariaException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author MSI
 */
public class inputMethods {
    
    
    public static Manager manager = Manager.getInstance();
    
    
    public static String dni(){
        String dni = null;
        boolean exit = false;
        do{
          exit = false;
            try{
              dni = manager.checkDNI(inputMethods.askString("DNI (sin letra, solo los numeros)"));
              exit = true;
            }catch(VeterinariaException e){
                System.out.println(e.getMessage());
            }
        }while(!exit);
        return dni;
    }
    
    public static String matriculaUser(){
        String matricula = null;
        boolean exit = false;
        do{
           exit = false;
           try{
               matricula = askString("Matricula para el usuario (3 caracteres)");
               if(matricula.length() != 3){
                   throw new VeterinariaException(VeterinariaException.MATRICULA_SIZE);
               }
               manager.checkMatriculaU(matricula);
               exit = true;
           }catch(VeterinariaException e){
               System.out.println(e.getMessage());
           }
        }while(!exit);
       return matricula;
    }
    
    public static String password(){
        
        String password = null;
        boolean exit = false;
        do{
          exit = false;
          try{
            password = inputMethods.askString("Password:");
            String passwordConf = inputMethods.askString("Confirme password:");
            if(!password.equals(passwordConf)){
                throw new VeterinariaException(VeterinariaException.PASS_CONFIRM);
            }
            exit = true;
          }catch(VeterinariaException e){
              System.out.println(e.getMessage());
          }
        }while(!exit);
       return password;
    }
    
    public static int tipoUsuario() {
        
        int tu = 0;
        boolean exit = false;
        do{
          exit = false;
          try{
            tu = inputMethods.askInt("Tipo usuario: 1: auxiliar/ 2: veterinario / 3: admin");
            if(!(tu == 1 || tu == 2 || tu == 3)){
                throw new VeterinariaException(VeterinariaException.WRONG_TYPE);
            }
            exit = true;
          }catch(VeterinariaException |IOException e){
              System.out.println(e.getMessage());
          }
        }while(!exit);
       return tu;
    }
    
   
     /**
     * Request int
     *
     * @param message
     * @return int
     * @throws java.io.IOException
     */
    public static int askInt(String message) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
