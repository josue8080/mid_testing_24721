package library_Managenment.hibernate;

import library_Management.models.Location;
import library_Management.models.Book;
import library_Management.models.Person;
import library_Management.models.Room;
import library_Management.models.User;
import library_Management.models.MembershipType;
import library_Management.models.Shelf;
import library_Management.models.Membership;
import library_Management.models.Borrower;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MainApp {
    public static void main(String[] args) {
        // Create a configuration instance
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        // Add annotated classes
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(Borrower.class);
        configuration.addAnnotatedClass(Location.class);
        configuration.addAnnotatedClass(Membership.class);
        configuration.addAnnotatedClass(MembershipType.class);
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(Room.class);
        configuration.addAnnotatedClass(Shelf.class);
        configuration.addAnnotatedClass(User.class);

        // Create SessionFactory
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Open a session
        Session session = sessionFactory.openSession();

        // Begin transaction
        Transaction transaction = session.beginTransaction();

        // Your database operations here, e.g., save, update, delete, etc.

        // Commit transaction
        transaction.commit();

        // Close session
        session.close();

        // Shutdown SessionFactory
        sessionFactory.close();
    }
}
