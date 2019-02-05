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
    /*
     public static Usuarios getUser(String matricula, String password){
         
         Session sesion = HibernateUtil.createSessionFactory().openSession();
         String query = "FROM usuarios WHERE matricula = " + matricula + " and pass = "+ password;
         Query q = sesion.createQuery(query);
         List<Usuarios> listaUsuarios =  q.list();
         for(Usuarios u: listaUsuarios){
             Usuarios user = (Usuarios) u;
             System.out.println(user.getNombre());
         }
         return null;
     }
     */
}
