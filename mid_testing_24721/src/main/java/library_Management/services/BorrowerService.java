package library_Management.services;

import library_Management.dao.UserDAO;
import library_Management.dao.BookDAO;
import library_Management.dao.BorrowerDAO;
import library_Management.models.Book;
import library_Management.models.User;
import library_Management.models.Membership;
import library_Management.models.Borrower;
import library_Management_enum.BookStatus;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BorrowerService {

    private final BorrowerDAO borrowerDAO;
    private final BookDAO bookDAO;
    private final UserDAO userDAO;

    public BorrowerService(BorrowerDAO borrowerDAO, BookDAO bookDAO, UserDAO userDAO) {
        this.borrowerDAO = borrowerDAO;
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
    }

    public void borrowBook(User user, Book book) throws Exception {
        if (validateBorrowLimit(user)) {
            throw new Exception("User has exceeded the borrow limit.");
        }

        // Create and save new Borrower record
        Borrower borrower = new Borrower();
        borrower.setBook(book);
        borrower.setReservationDate(new Date());
        borrower.setDueDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))); // 1 week due date
        borrower.setLateChargeFees(0); // No late fees for a new borrow
        borrowerDAO.save(borrower);

        // Update book status and associate borrower
        book.setStatus(BookStatus.BORROWED);
        book.setBorrowedBy(user);
        bookDAO.save(book);

        // Add book to user’s borrowed list and save
        user.borrowBook(book);
        userDAO.save(user);
    }

    public boolean validateBorrowLimit(User user) {
        int currentBorrowedBooks = user.getBorrowedBooks().size();
        int borrowLimit = user.getMembership().getMembershipType().getMaxBooks();
        return currentBorrowedBooks >= borrowLimit;
    }

    public int calculateLateFees(Borrower borrower) {
        if (borrower.getReturnDate() == null || borrower.getDueDate() == null) {
            return 0;
        }

        long diffInMillis = Math.abs(borrower.getReturnDate().getTime() - borrower.getDueDate().getTime());
        long lateDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

        if (lateDays <= 0) {
            return 0;
        }

        Membership membership = borrower.getBook().getBorrowedBy().getMembership();
        int dailyFee = membership.getMembershipType().getDailyFee();
        return (int) lateDays * dailyFee;
    }
}
