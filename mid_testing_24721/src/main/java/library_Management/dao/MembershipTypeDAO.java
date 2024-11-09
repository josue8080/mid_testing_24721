package library_Management.dao;

import library_Management.models.MembershipType;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.UUID;
import library_Managenment.hibernate.HibernateUtil;

public class MembershipTypeDAO {

    
    public void save(MembershipType membershipType) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(membershipType);
            session.flush(); // Ensure data is flushed to the database

            transaction.commit();
            System.out.println("Saved MembershipType ID: " + membershipType.getMembershipTypeId());

        } catch (Exception e) {
            handleException(e, transaction);
        }
    }

    
    public MembershipType findById(UUID id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(MembershipType.class, id);
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
