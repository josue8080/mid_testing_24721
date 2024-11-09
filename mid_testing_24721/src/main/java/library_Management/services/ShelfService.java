package library_Management.services;

import library_Management.models.Book;
import library_Management.models.Shelf;
import library_Management.models.Room;
import library_Management.dao.ShelfDAO;
import library_Management.dao.BookDAO;

public class ShelfService {
    
    private final ShelfDAO shelfDAO;
    private final BookDAO bookDAO;

    public ShelfService(ShelfDAO shelfDAO, BookDAO bookDAO) {
        this.shelfDAO = shelfDAO;
        this.bookDAO = bookDAO;
    }

    public void assignBookToShelf(Book book, Shelf shelf) {
        // Associate the book with the shelf
        book.setShelf(shelf);
        bookDAO.save(book);

        // Update shelf details and save
        shelf.getBooks().add(book);
        shelfDAO.save(shelf);
    }

    public int countBooksInRoom(Room room) {
        int bookCount = 0;
        for (Shelf shelf : room.getShelves()) {
            bookCount += shelf.getBooks().size();
        }
        return bookCount;
    }
}
