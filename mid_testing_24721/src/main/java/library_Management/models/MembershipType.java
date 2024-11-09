package library_Management.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class MembershipType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID membershipTypeId;
    
    private String membershipName;
    private int maxBooks;    // Borrow limit
    private int price;
    private int dailyFee;

    // Constructor
    public MembershipType() {}

    // Getters
    public UUID getMembershipTypeId() {
        return membershipTypeId;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public int getMaxBooks() {
        return maxBooks;
    }

    public int getPrice() {
        return price;
    }

    public int getDailyFee() {
        return dailyFee;
    }

    // Setters
    public void setMembershipTypeId(UUID membershipTypeId) {
        this.membershipTypeId = membershipTypeId;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public void setMaxBooks(int maxBooks) {
        this.maxBooks = maxBooks;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDailyFee(int dailyFee) {
        this.dailyFee = dailyFee;
    }

    // Override toString (Optional for debugging purposes)
    @Override
    public String toString() {
        return "MembershipType{" +
                "membershipTypeId=" + membershipTypeId +
                ", membershipName='" + membershipName + '\'' +
                ", maxBooks=" + maxBooks +
                ", price=" + price +
                ", dailyFee=" + dailyFee +
                '}';
    }
}
