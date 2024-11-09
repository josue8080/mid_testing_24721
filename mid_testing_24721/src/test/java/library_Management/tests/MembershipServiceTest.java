package library_Management.tests;

import library_Management.dao.MembershipDAO;
import library_Management.dao.MembershipTypeDAO;
import library_Management.dao.LocationDAO;
import library_Management.dao.UserDAO;
import library_Management.models.Location;
import library_Management.models.MembershipType;
import library_Management.models.User;
import library_Management.services.MembershipService;
import library_Management.services.LocationService;
import library_Management.services.UserService;
import library_Management_enum.LocationType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MembershipServiceTest {

    private final LocationDAO locationDAO = new LocationDAO();
    private final UserDAO userDAO = new UserDAO();
    private final MembershipDAO membershipDAO = new MembershipDAO();
    private final MembershipTypeDAO membershipTypeDAO = new MembershipTypeDAO();
    private final LocationService locationService = new LocationService(locationDAO);
    private final UserService userService = new UserService(userDAO, locationService);
    private final MembershipService membershipService = new MembershipService(membershipDAO, userDAO, membershipTypeDAO);

    private UUID userId;

    @Before
    public void setUp() {
        // Create locations
        Location province = new Location("ProvinceName", LocationType.PROVINCE, null);
        Location district = new Location("DistrictName", LocationType.DISTRICT, province);
        Location sector = new Location("SectorName", LocationType.SECTOR, district);
        Location cell = new Location("CellName", LocationType.CELL, sector);
        Location village = new Location("VillageName", LocationType.VILLAGE, cell);

        // Save locations
        locationService.createLocation(province);
        locationService.createLocation(district);
        locationService.createLocation(sector);
        locationService.createLocation(cell);
        locationService.createLocation(village);

        // Create a user
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setLocation(village);
        user.setUsername("jdoe");
        user.setPassword("password123");

        // Create account (hash password and save user)
        userService.createAccount(user);

        // Logging IDs
        System.out.println("User ID: " + user.getPersonId());
        assertNotNull(user.getPersonId(), "User ID should not be null after saving");

        // Store the user ID for retrieval
        userId = user.getPersonId();
    }

    private void assertNotNull(UUID personId, String string) {
		// TODO Auto-generated method stub
		
	}

	@Test
    public void testRegisterMembership() {
        // Create a membership type
        MembershipType membershipType = new MembershipType();
        membershipType.setMembershipName("Gold");
        membershipType.setMaxBooks(10);
        membershipType.setPrice(100);

        // Save membership type
        membershipTypeDAO.save(membershipType);

        // Register membership for user
        User user = userDAO.findById(userId);
        membershipService.registerMembership(user, membershipType);

        // Verify membership registration
        Assert.assertNotSame(user.getMembership(), "User should have a membership assigned");
        assertEquals("Gold", user.getMembership().getMembershipType().getMembershipName(), "Membership type should be 'Gold'");
    }
}
