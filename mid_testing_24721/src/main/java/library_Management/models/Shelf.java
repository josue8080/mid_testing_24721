package library_Management.models;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Shelf {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID shelfId;

    private int availableStock;
    private String bookCategory;
    private int borrowedNumber;

    @OneToMany(mappedBy = "shelf")
    private List<Book> books;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // Constructors
    public Shelf() {}

    public Shelf(int availableStock, String bookCategory, int borrowedNumber, List<Book> books, Room room) {
        this.availableStock = availableStock;
        this.bookCategory = bookCategory;
        this.borrowedNumber = borrowedNumber;
        this.books = books;
        this.room = room;
    }

    // Getters
    public UUID getShelfId() {
        return shelfId;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public int getBorrowedNumber() {
        return borrowedNumber;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Room getRoom() {
        return room;
    }

    // Setters
    public void setShelfId(UUID shelfId) {
        this.shelfId = shelfId;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public void setBorrowedNumber(int borrowedNumber) {
        this.borrowedNumber = borrowedNumber;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
