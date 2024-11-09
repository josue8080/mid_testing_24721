package library_Management.dao;

import library_Managenment.hibernate.HibernateUtil;
import library_Management.models.Borrower;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.UUID;

public class BorrowerDAO {

 public void save(Borrower borrower) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            session.save(borrower);
            session.flush();  
            transaction.commit();
            
            System.out.println("Saved Borrower ID: " + borrower.getBorrowerId());
        } catch (Exception e) {
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
