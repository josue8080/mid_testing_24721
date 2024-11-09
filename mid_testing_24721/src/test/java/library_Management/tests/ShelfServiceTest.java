package library_Management.tests;

import library_Management.dao.RoomDAO;
import library_Management.dao.ShelfDAO;
import library_Management.dao.BookDAO;
import library_Management.models.Book;
import library_Management.models.Room;
import library_Management.models.Shelf;
import library_Management.services.ShelfService;
import library_Management.services.RoomService;
import library_Management_enum.BookStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ShelfServiceTest {

    private final ShelfDAO shelfDAO = new ShelfDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final ShelfService shelfService = new ShelfService(shelfDAO, bookDAO);
    private final RoomService roomService = new RoomService(roomDAO, shelfDAO);
    private UUID roomId;
    private UUID shelfId;

    @BeforeEach
    public void setUp() {
        Room room = new Room();
        room.setRoomCode("R101");
        roomDAO.save(room);
        roomId = room.getRoomId();

        Shelf shelf = new Shelf();
        shelf.setAvailableStock(10);
        shelf.setBookCategory("life");
        shelf.setBorrowedNumber(0);
        shelfDAO.save(shelf);
        shelfId = shelf.getShelfId();
    }

    @Test
    public void testAssignShelfToRoom() {
        try {
            Shelf shelf = shelfDAO.findById(shelfId);
            Room room = roomDAO.findById(roomId);

            Assertions.assertNotNull(shelf, "Shelf should not be null");
            Assertions.assertNotNull(room, "Room should not be null");

            roomService.assignShelfToRoom(shelf, room);

            Assertions.assertEquals(room, shelf.getRoom(), "Shelf should be assigned to the specified room");
            Assertions.assertTrue(room.getShelves().contains(shelf), "Room should contain the assigned shelf");
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testCountBooksInRoom() {
        Shelf shelf = shelfDAO.findById(shelfId);
        Room room = roomDAO.findById(roomId);

        Assertions.assertNotNull(shelf, "Shelf should not be null");
        Assertions.assertNotNull(room, "Room should not be null");

        Book book1 = createBook("The reader", "9780134645678", "lil meech", 3, "2008-04-01");
        Book book2 = createBook("best prigramming skills", "9780132123454", "BIG mary", 1, "2024-07-01");

        shelfService.assignBookToShelf(book1, shelf);
        shelfService.assignBookToShelf(book2, shelf);

        roomService.assignShelfToRoom(shelf, room);

        int bookCount = shelfService.countBooksInRoom(room);
        Assertions.assertEquals(2, bookCount, "The room should contain 2 books");
    }

    private Book createBook(String title, String isbn, String publisher, int edition, String pubDate) {
        Book book = new Book();
        book.setTitle(title);
        book.setISBNCode(isbn);
        book.setPublisherName(publisher);
        book.setEdition(edition);
        book.setPublicationYear(LocalDate.parse(pubDate, DateTimeFormatter.ISO_DATE));
        book.setStatus(BookStatus.AVAILABLE);
        return book;
    }
}
