package library_Management.dao;

import library_Managenment.hibernate.HibernateUtil;
import library_Management.models.Shelf;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.UUID;

public class ShelfDAO {

    // Save or update the given Shelf entity
    public void save(Shelf shelf) {
        Transaction transaction = null;

        // Try-with-resources for session management
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(shelf);
            session.flush();  // Ensure changes are flushed to the database

            transaction.commit();
            System.out.println("Saved Shelf ID: " + shelf.getShelfId());
        
        } catch (Exception e) {
            e.printStackTrace();

            if (transaction != null) {
                rollbackTransaction(transaction);
            }
        }
    }

    // Find and return a Shelf by its ID
    public Shelf findById(UUID id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Shelf.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to handle transaction rollback
    private void rollbackTransaction(Transaction transaction) {
        try {
            transaction.rollback();
        } catch (Exception rollbackException) {
            rollbackException.printStackTrace();
        }
    }
}
