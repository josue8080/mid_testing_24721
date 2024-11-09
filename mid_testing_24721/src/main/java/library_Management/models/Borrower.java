package library_Management.models;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID borrowerId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private Date dueDate;
    private int lateChargeFees;
    private UUID reservationId;
    private Date reservationDate;
    private Date returnDate;

    // Getters and Setters

    public UUID getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(UUID borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getLateChargeFees() {
        return lateChargeFees;
    }

    public void setLateChargeFees(int lateChargeFees) {
        this.lateChargeFees = lateChargeFees;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

}
