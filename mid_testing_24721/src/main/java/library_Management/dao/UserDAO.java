package library_Management.dao;

import library_Managenment.hibernate.HibernateUtil;
import library_Management.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.UUID;

public class UserDAO {

    public void save(User user) {
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            session.flush(); // Ensure data is flushed to the database
            transaction.commit();
            System.out.println("Saved User ID: " + user.getPersonId());
            
        } catch (Exception e) {
            handleException(e, transaction);
        }
    }

    public User findById(UUID id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            System.out.println("Retrieved User: " + (user != null ? user.getFirstName() + " " + user.getLastName() : "null"));
            return user;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    public User findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User WHERE username = :username", User.class)
                          .setParameter("username", username)
                          .uniqueResult();
                          
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   
    private void handleException(Exception e, Transaction transaction) {
        e.printStackTrace();
        
        if (transaction != null) {
            try {
                transaction.rollback();
            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }
}
