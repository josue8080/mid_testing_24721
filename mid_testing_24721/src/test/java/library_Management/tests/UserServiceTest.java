package library_Management.tests;

import library_Management.dao.LocationDAO;
import library_Management.dao.UserDAO;
import library_Management.models.Location;
import library_Management.models.User;
import library_Management.services.LocationService;
import library_Management.services.UserService;
import library_Management_enum.LocationType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {

    private final LocationDAO locationDAO = new LocationDAO();
    private final UserDAO userDAO = new UserDAO();
    private final LocationService locationService = new LocationService(locationDAO);
    private final UserService userService = new UserService(userDAO, locationService);
    private UUID userId;

    @Before
    public void setUp() {
        setUpLocations();
        createUser();
    }

    private void setUpLocations() {
        Location province = new Location("ProvinceName", LocationType.PROVINCE, null);
        Location district = new Location("DistrictName", LocationType.DISTRICT, province);
        Location sector = new Location("SectorName", LocationType.SECTOR, district);
        Location cell = new Location("CellName", LocationType.CELL, sector);
        Location village = new Location("VillageName", LocationType.VILLAGE, cell);

        locationService.createLocation(province);
        locationService.createLocation(district);
        locationService.createLocation(sector);
        locationService.createLocation(cell);
        locationService.createLocation(village);
    }

    private void createUser() {
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setLocation(new Location("VillageName", LocationType.VILLAGE, new Location("CellName", LocationType.CELL, null)));
        user.setUsername("jdoe");
        user.setPassword("password123");

        userService.createAccount(user);

        assertNotNull("User ID should not be null after saving", user.getPersonId());
        userId = user.getPersonId();
    }

    @Test
    public void testCreateAccount() {
        User user = userDAO.findById(userId);
        Assert.assertNotNull(user);
        assertTrue("Password should be hashed correctly", BCrypt.checkpw("password123", user.getPassword()));
    }

    @Test
    public void testAuthenticate() {
        // Test valid credentials
        assertTrue("Authentication should succeed with valid credentials", userService.authenticate("jdoe", "password123"));

        // Test invalid credentials
        assertFalse("Authentication should fail with invalid credentials", userService.authenticate("jdoe", "wrongpassword"));

        // Test non-existent username
        assertFalse("Authentication should fail with non-existent username", userService.authenticate("unknown", "password123"));
    }
}
