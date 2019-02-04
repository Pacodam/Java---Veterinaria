/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.util.List;
import model.Usuarios;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 *
 * @author alu2017454
 */
public class HibernateQueries {
    
     public static Usuarios getUser(String matricula, String password){
         
         Session sesion = HibernateUtil.createSessionFactory().openSession();
         String query = "FROM usuarios WHERE matricula = " + matricula + " and pass = "+ password;
         Query q = sesion.createQuery(query);
         List listaUsuarios =  q.list();
         for(Object u: listaUsuarios){
             Usuarios user = (Usuarios) u;
             System.out.println(user.getNombre());
         }
         return null;
     }
}
