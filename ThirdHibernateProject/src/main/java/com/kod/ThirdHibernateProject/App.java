package com.kod.ThirdHibernateProject;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    { 
    	Scanner in=new Scanner(System.in);
    	SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory(); 
    	//above i have done in 1 step
    	
    	Session session= factory.openSession();
    	
    	Transaction tx = session.beginTransaction();
    	
        System.out.println( "Enter Student id to update");
        int id=in.nextInt();
        in.nextLine();
       Student obj =  session.get(Student.class, id);
       
       if(obj!=null) {
    	   System.out.println("Enter name and email to update");
    	   String name=in.nextLine();
    	   String email=in.nextLine();
    	   
    	   obj.setName(name);
    	   obj.setEmail(email);
    	   
    	   session.merge(obj);
    	   System.out.println("Student "+id+" Updated successfully");
       }
       else {
    	   System.out.println("Student with id "+id+" not found to update");
       }
       
       tx.commit();
       
       session.close();
       factory.close();
        
    }
}
