package library_Management.dao;


import library_Management.models.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.UUID;
import library_Managenment.hibernate.HibernateUtil;

public class BookDAO {

    // Save or update a book entry in the database
    public void save(Book book) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            session.saveOrUpdate(book);
            session.flush();  // Ensure data is flushed to the database
            transaction.commit();
            
            System.out.println("Saved Book ID: " + book.getBookId());

        } catch (Exception e) {
            e.printStackTrace();
            handleTransactionRollback(transaction);
        }
    }

    // Find a book by its unique ID
    public Book findById(UUID id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Book.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Private helper to handle transaction rollback in case of exceptions
    private void handleTransactionRollback(Transaction transaction) {
        if (transaction != null) {
            try {
                transaction.rollback();
            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }
}
