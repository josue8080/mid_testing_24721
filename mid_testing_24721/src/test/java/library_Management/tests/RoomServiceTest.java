package library_Management.tests;

import library_Management.dao.BookDAO;
import library_Management.dao.RoomDAO;
import library_Management.dao.ShelfDAO;
import library_Management.models.Book;
import library_Management.models.Room;
import library_Management.models.Shelf;
import library_Management.services.RoomService;
import library_Management_enum.BookStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomServiceTest {

    private RoomDAO roomDAO;
    private ShelfDAO shelfDAO;
    private BookDAO bookDAO;
    private RoomService roomService;
    private UUID room1Id;
    private UUID room2Id;
    private UUID shelf1Id;
    private UUID shelf2Id;

    @BeforeEach
    public void setUp() {
        // Initialize DAOs and service
        roomDAO = new RoomDAO();
        shelfDAO = new ShelfDAO();
        bookDAO = new BookDAO();
        roomService = new RoomService(roomDAO, shelfDAO);

        // Create and save rooms
        room1Id = createRoom("R101");
        room2Id = createRoom("R102");

        // Create and save shelves
        shelf1Id = createShelf("Programming", 10);
        shelf2Id = createShelf("Fiction", 10);

        // Assign shelves to rooms
        roomService.assignShelfToRoom(shelfDAO.findById(shelf1Id), roomDAO.findById(room1Id));
        roomService.assignShelfToRoom(shelfDAO.findById(shelf2Id), roomDAO.findById(room2Id));

        // Create and assign books to shelves
        createAndAssignBookToShelf(shelf1Id, "best prigramming skills", "978013212345", "lil meech", 3, "2024-09-06");
        createAndAssignBookToShelf(shelf1Id, "filler Jeans", "9780132312345", "Prentice Hall", 1, "2025-09-08");
        createAndAssignBookToShelf(shelf2Id, "The reader", "9781234550884", "BIG mary", 1, "2024-10-20");
    }

    private UUID createRoom(String roomCode) {
        Room room = new Room();
        room.setRoomCode(roomCode);
        roomDAO.save(room);
        return room.getRoomId();
    }

    private UUID createShelf(String category, int availableStock) {
        Shelf shelf = new Shelf();
        shelf.setAvailableStock(availableStock);
        shelf.setBookCategory(category);
        shelf.setBorrowedNumber(0);
        shelfDAO.save(shelf);
        return shelf.getShelfId();
    }

    private void createAndAssignBookToShelf(UUID shelfId, String title, String isbn, String publisher, int edition, String publicationDate) {
        Book book = new Book();
        book.setTitle(title);
        book.setISBNCode(isbn);
        book.setPublisherName(publisher);
        book.setEdition(edition);
        book.setPublicationYear(LocalDate.parse(publicationDate, DateTimeFormatter.ISO_DATE));
        book.setStatus(BookStatus.AVAILABLE);
        bookDAO.save(book);

        Shelf shelf = shelfDAO.findById(shelfId);
        shelf.getBooks().add(book);
        shelfDAO.save(shelf);
    }

    @Test
    public void testGetRoomWithFewestBooks() {
        Room roomWithFewestBooks = roomService.getRoomWithFewestBooks();
        assertNotNull(roomWithFewestBooks, "Room should not be null");
        assertEquals("R102", roomWithFewestBooks.getRoomCode(), "Room with fewest books should be R102");
    }
}
