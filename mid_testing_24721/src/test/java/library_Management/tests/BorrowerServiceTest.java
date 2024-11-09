package library_Management.tests;

import library_Management.dao.UserDAO;
import library_Management.dao.BorrowerDAO;
import library_Management.dao.BookDAO;
import library_Management.models.Borrower;
import library_Management.models.Book;
import library_Management.models.MembershipType;
import library_Management.models.User;
import library_Management.models.Membership;
import library_Management.services.BorrowerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BorrowerServiceTest {

    private BorrowerDAO borrowerDAO;
    private BookDAO bookDAO;
    private UserDAO userDAO;
    private BorrowerService borrowerService;

    @BeforeEach
    public void setUp() {
        borrowerDAO = new BorrowerDAO();
        bookDAO = new BookDAO();
        userDAO = new UserDAO();
        borrowerService = new BorrowerService(borrowerDAO, bookDAO, userDAO);
    }

    @Test
    public void testCalculateLateFees() {
        // Setting up user, book, membership, and borrower for the test
        User user = createTestUserWithMembership(5);
        Book book = new Book();
        Borrower borrower = createTestBorrowerWithDates(book, -5, 0); // Due 5 days ago, returned today

        // Calculate late fees and assert expected value
        int lateFees = borrowerService.calculateLateFees(borrower);
        assertEquals(25, lateFees, "Late fee should be 25");

        // Test with no late fees scenario
        borrower.setDueDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(5))); // Due in 5 days
        lateFees = borrowerService.calculateLateFees(borrower);
        assertEquals(0, lateFees, "Late fee should be 0");
    }

    private User createTestUserWithMembership(int dailyFee) {
        MembershipType membershipType = new MembershipType();
        membershipType.setDailyFee(dailyFee);
        Membership membership = new Membership();
        membership.setMembershipType(membershipType);

        User user = new User();
        user.setMembership(membership);
        return user;
    }

    private Borrower createTestBorrowerWithDates(Book book, int daysDueOffset, int daysReturnOffset) {
        Borrower borrower = new Borrower();
        borrower.setBook(book);
        borrower.setDueDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(daysDueOffset)));
        borrower.setReturnDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(daysReturnOffset)));
        return borrower;
    }
}
