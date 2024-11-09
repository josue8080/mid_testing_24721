package library_Management.dao;

import library_Managenment.hibernate.HibernateUtil;
import library_Management.models.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

public class RoomDAO {

    // Save or update a Room in the database
    public void save(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(room);
            session.flush();  // Ensure data is flushed to the database
            transaction.commit();
            System.out.println("Saved Room ID: " + room.getRoomId());
        } catch (Exception e) {
            handleException(e, transaction);
        }
    }

    // Retrieve a Room by its ID
    public Room findById(UUID id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Room.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Retrieve all Room entities from the database
    public List<Room> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Room", Room.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Handle exceptions and transaction rollback
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
