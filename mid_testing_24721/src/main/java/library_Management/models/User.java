package library_Management.models;

import javax.persistence.*;
import library_Management_enum.RoleType;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends Person {

    @ManyToOne
    @JoinColumn(name = "village_id")
    private Location village;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @OneToMany(mappedBy = "borrowedBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Book> borrowedBooks = new HashSet<>();

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Location getVillage() {
        return village;
    }

    public void setVillage(Location village) {
        this.village = village;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Set<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        this.borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        this.borrowedBooks.remove(book);
    }

    public Object getId() {
        // TODO Auto-generated method stub
        return null;
    }
}
