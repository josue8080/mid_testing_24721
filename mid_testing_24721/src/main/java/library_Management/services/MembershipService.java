package library_Management.services;

import library_Management.dao.UserDAO;
import library_Management.dao.MembershipTypeDAO;
import library_Management.dao.MembershipDAO;
import library_Management.models.MembershipType;
import library_Management.models.User;
import library_Management.models.Membership;
import java.util.UUID;
import java.util.Date;

public class MembershipService {

    private final MembershipDAO membershipDAO;
    private final UserDAO userDAO;
    private final MembershipTypeDAO membershipTypeDAO;

    public MembershipService(MembershipDAO membershipDAO, UserDAO userDAO, MembershipTypeDAO membershipTypeDAO) {
        this.membershipDAO = membershipDAO;
        this.userDAO = userDAO;
        this.membershipTypeDAO = membershipTypeDAO;
    }

    public void registerMembership(User user, MembershipType membershipType) {
        Membership membership = new Membership();
        membership.setReader(user);
        membership.setMembershipType(membershipType);
        membership.setExpiringTime(new Date()); // Set expiration to current date for simplicity
        membership.setMembershipCode(UUID.randomUUID().toString());
        membership.setFine(0); // No fine for new memberships

        membershipDAO.save(membership);
        user.setMembership(membership);
        userDAO.save(user); // Save user with updated membership
    }

    public void registerMembership1(User user, MembershipType membershipType) {
        // TODO: Implement functionality
    }
}
