package com.kod.FirstHibernateProject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction tx = null;

        try {
            System.out.println("Enter name, email and marks to insert (press enter after each):");

            String name = in.nextLine().trim();
            String email = in.nextLine().trim();
            // read marks as int — handle if user types newline after email
            int marks;
            if (in.hasNextInt()) {
                marks = in.nextInt();
            } else {
                // if not immediate int, try reading next token
                String next = in.next();
                marks = Integer.parseInt(next);
            }

            // Load config from src/main/resources/hibernate.cfg.xml
            Configuration configuration = new Configuration().configure(); // looks for hibernate.cfg.xml in resources

            // Register annotated entity class here (so no mapping in xml required)
            configuration.addAnnotatedClass(Student.class);

            // Build SessionFactory
            sessionFactory = configuration.buildSessionFactory();

            // Open session and begin transaction
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Create Student object and persist
            Student s1 = new Student(name,email,marks);

            session.persist(s1); // persist the entity
            
            Student ref = session.get(Student.class, 1);
            
            System.out.println(ref);

            // commit
            tx.commit();

            System.out.println("Saved student: " + s1);

        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            System.err.println("Hibernate error: " + he.getMessage());
            he.printStackTrace();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
            if (sessionFactory != null) sessionFactory.close();
            in.close();
        }
    }
}
