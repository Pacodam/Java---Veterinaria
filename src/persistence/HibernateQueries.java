/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import controller.Manager;
import exceptions.VeterinariaException;
import java.util.ArrayList;
import java.util.List;
import model.Expedientes;
import model.Usuarios;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


/**
 *
 * @author alu2017454
 */
public class HibernateQueries {
    
    /**
     * Modificacion de expediente pasado por parametro
     * @param expediente Expedientes 
     */
    public static void updateExpediente(Expedientes expediente) {
        
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.update(expediente);
        tx.commit();
        sesion.close();
    }
    
    /**
     * Eliminación del expediente pasado por parámetro
     * @param expediente Expedientes
     */
    public static void borrarExpediente(Expedientes expediente){
        
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.delete(expediente);
        tx.commit();
        sesion.close();
    }
    
    /**
     * Devuelve al Usuario correspondiente al id pasado por parametro o null si no lo hay
     * @param id int
     * @return Usuarios
     */
    public static Usuarios getUsuarioById(int id){
        
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Criteria crit = sesion.createCriteria(Usuarios.class);
        crit.add(Restrictions.eq("id", id));
        List<Usuarios> lista = crit.list();
        sesion.close();
        if(lista.isEmpty()){
            return null;
        }
        return lista.get(0);
    }
    
    /**
     * Devuelve el Expedientes correspondiente al id pasado por parámetro, null si no lo hay
     * @param id int
     * @return Expedientes
     */
    public static Expedientes getExpediente(int id){
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Criteria crit = sesion.createCriteria(Expedientes.class);
         crit.add(Restrictions.eq("id", id));
         List<Expedientes> lista = crit.list();
         sesion.close();
         if(lista.isEmpty()){
             return null;
         }
         return lista.get(0);
    }
    
    /**
     * Devuelve una List String con la información básica de todos los expedientes.
     * Si la lista está vacía lanza una excepción
     * @return List String
     * @throws VeterinariaException 
     */
    public static List<String> getExpedientes() throws VeterinariaException {
        
        List<String> exp = new ArrayList<>();
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Criteria crit = sesion.createCriteria(Expedientes.class);
        //String sentencia = "FROM expedientes";
        //Query q = sesion.createQuery(sentencia);
        List<Expedientes> lista = crit.list();
        System.out.println(lista.size());
        if(lista.isEmpty()){
             throw new VeterinariaException(VeterinariaException.NO_EXPEDIENTS);
         }
        for(Expedientes e: lista){
           exp.add(Manager.getInstance().expedienteToString(e));     
        }
        sesion.close();
        return exp;
        }
    
    /**
     * Alta de un nuevo expediente
     * @param expediente Expedientes
     */
    public static void altaExpediente(Expedientes expediente)  {
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.save(expediente);//LO PREPARA PARA PERSISTENCIA, PERO AÚN NO GUARDA
        tx.commit();//CONFIRMA EN BBDD TODO LO QUE ESTÁ EN LA SESIÓN DESDE EL COMIENZO DE LA TRANSACCION
        sesion.close();

     }
    
    /**
     * Devuelve al Usuarios correspondiente a matricula y password pasados por parametro.
     * Lanza excepción si no se encuentra ninguna correspondiente a esos datos.
     * @param matricula String
     * @param password String
     * @return Usuarios
     * @throws VeterinariaException 
     */
     public static Usuarios getUser(String matricula, String password) throws VeterinariaException{
         
         Session sesion = HibernateUtil.createSessionFactory().openSession();
         Criteria crit = sesion.createCriteria(Usuarios.class);
         crit.add(Restrictions.eq("matricula", matricula));
         crit.add(Restrictions.eq("pass", password));
         List<Usuarios> lista = crit.list();
         sesion.close();
         if(lista.isEmpty()){
             throw new VeterinariaException(VeterinariaException.NO_SUCH_USER);
         }
         return lista.get(0);
     }
   
     /**
      * Alta de nuevo usuario
      * @param usuario Usuarios
      */
     public static void altaUsuario(Usuarios usuario)  {
         
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.save(usuario);//LO PREPARA PARA PERSISTENCIA, PERO AÚN NO GUARDA
        tx.commit();//CONFIRMA EN BBDD TODO LO QUE ESTÁ EN LA SESIÓN DESDE EL COMIENZO DE LA TRANSACCION
        sesion.close();

     }
     
     /**
      * Eliminación del usuario que se pasa por parametro
      * @param usuario Usuarios
      */
     public static void borrarUsuario(Usuarios usuario) {
        
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.delete(usuario);
        tx.commit();
        sesion.close();
    }
    
    /**
     * Modificación del usuario pasado por parámetro
     * @param usuario Usuarios
     */
    public static void updateUsuario(Usuarios usuario) {
        
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.update(usuario);
        tx.commit();
        sesion.close();
    }
    
    /**
     * Devuelve List Usuarios de los usuarios en el sistema
     * Si no hay ninguno lanza excepción
     * @return List Usuarios
     * @throws VeterinariaException 
     */
    public static List<Usuarios> getUsers() throws VeterinariaException{
         //hacerlo de la otra manera
         Session sesion = HibernateUtil.createSessionFactory().openSession();
         Criteria crit = sesion.createCriteria(Usuarios.class);
         List<Usuarios> lista = crit.list();
         sesion.close();
         if(lista.isEmpty()){
             throw new VeterinariaException(VeterinariaException.NO_USERS);
         }
         return lista;
     }
    
    public static boolean existsAdmin() {
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Criteria crit = sesion.createCriteria(Usuarios.class);
        crit.add(Restrictions.eq("matricula", "admin"));
        crit.add(Restrictions.eq("pass", "admin"));
        List<Usuarios> lista = crit.list();
        sesion.close();
        if(lista.isEmpty()){
            return false;
         }
        return true;
    }
    
    public static void checkMatriculaU(String matricula) throws VeterinariaException{
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Criteria crit = sesion.createCriteria(Usuarios.class);
        crit.add(Restrictions.eq("matricula", matricula));
        List<Usuarios> lista = crit.list();
        sesion.close();
        if(!lista.isEmpty()){
            throw new VeterinariaException(VeterinariaException.MATRICULA_U_EXISTS);
         }
        
    }

    
     
}
