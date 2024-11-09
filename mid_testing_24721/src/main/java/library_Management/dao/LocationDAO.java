package library_Management.dao;


import library_Management.models.Location;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.UUID;
import library_Managenment.hibernate.HibernateUtil;

public class LocationDAO {

    
    public void save(Location location) {
        Transaction transaction = null;

        // Open a session and start a transaction
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Save the location entity and flush changes to the database
            session.save(location);
            session.flush();
            
            // Commit the transaction to finalize the save
            transaction.commit();
            
        } catch (Exception e) {
            // Rollback transaction on failure and print the exception stack trace
            handleException(transaction, e);
        }
    }

  
    public Location findById(UUID id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            
            // Retrieve Location by id and print the result
            Location location = session.get(Location.class, id);
            System.out.println("Retrieved Location: " + 
                (location != null ? location.getName() : "null"));
                
            return location;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Handles exceptions during a transaction by rolling back and printing the exception.
     * @param transaction the current transaction to roll back
     * @param e the exception that occurred
     */
    private void handleException(Transaction transaction, Exception e) {
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
