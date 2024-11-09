package library_Management.dao;

import library_Managenment.hibernate.HibernateUtil;
import library_Management.models.Membership;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.UUID;

public class MembershipDAO {

    public void save(Membership membership) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            session.save(membership);
            session.flush(); // Ensures data is written to the database
            transaction.commit();
            
            System.out.println("Saved Membership ID: " + membership.getMembershipId());
        } catch (Exception e) {
            handleException(e, transaction);
        }
    }

    public Membership findById(UUID id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Membership.class, id);
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
