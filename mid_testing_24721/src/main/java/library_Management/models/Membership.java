package library_Management.models;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID membershipId;

    private Date expiringTime;
    private int fine;
    private String membershipCode;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private User reader;

    @ManyToOne
    @JoinColumn(name = "membershipType_id")
    private MembershipType membershipType;

    // Getters and Setters
    public UUID getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(UUID membershipId) {
        this.membershipId = membershipId;
    }

    public Date getExpiringTime() {
        return expiringTime;
    }

    public void setExpiringTime(Date expiringTime) {
        this.expiringTime = expiringTime;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public String getMembershipCode() {
        return membershipCode;
    }

    public void setMembershipCode(String membershipCode) {
        this.membershipCode = membershipCode;
    }

    public User getReader() {
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    // Methods for specific behaviors
    public int getBorrowLimit() {
        return 0; // Placeholder, to be implemented as needed
    }

    public long getDailyFee() {
        return 0; // Placeholder, to be implemented as needed
    }

    public int getType() {
        return 0; // Placeholder, to be implemented as needed
    }
}
