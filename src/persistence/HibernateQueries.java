/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import exceptions.VeterinariaException;
import java.util.List;
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
   
     
     public static void altaUsuario(Usuarios usuario)  {
         
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.save(usuario);//LO PREPARA PARA PERSISTENCIA, PERO AÚN NO GUARDA
        tx.commit();//CONFIRMA EN BBDD TODO LO QUE ESTÁ EN LA SESIÓN DESDE EL COMIENZO DE LA TRANSACCION
        sesion.close();

     }
     
     public static void borrarUsuario(Usuarios usuario) {
        
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.delete(usuario);
        tx.commit();
        sesion.close();
    }
    
    public static void updateUsuario(Usuarios usuario) {
        
        Session sesion = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.update(usuario);
        tx.commit();
        sesion.close();
    }
    
    public static List<Usuarios> getUsers() throws VeterinariaException{
         
         Session sesion = HibernateUtil.createSessionFactory().openSession();
         Criteria crit = sesion.createCriteria(Usuarios.class);
         List<Usuarios> lista = crit.list();
         sesion.close();
         if(lista.isEmpty()){
             throw new VeterinariaException(VeterinariaException.NO_SUCH_USER);
         }
         return lista;
     }

     
}
