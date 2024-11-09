package library_Management.services;

import library_Management.dao.UserDAO;
import library_Management.models.Location;
import library_Management.models.Person;
import library_Management.models.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

public class UserService {
    
    private final UserDAO userDAO;
    private final LocationService locationService;

    // Constructor to initialize UserDAO and LocationService dependencies
    public UserService(UserDAO userDAO, LocationService locationService) {
        this.userDAO = userDAO;
        this.locationService = locationService;
    }

    // Retrieves the province name for a given person ID
    public String getPersonLocation(UUID personId) {
        Person person = userDAO.findById(personId);
        
        if (person == null) {
            throw new IllegalArgumentException("Person not found with ID: " + personId);
        }
        
        Location village = person.getLocation(); // Assuming Person has a getLocation() method
        Location province = locationService.getProvinceFromVillage(village.getId());
        
        return province.getName();
    }

    // Creates a user account with a hashed password
    public void createAccount(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userDAO.save(user);
    }

    // Authenticates a user by username and password
    public boolean authenticate(String username, String password) {
        User user = userDAO.findByUsername(username);
        
        if (user == null) {
            return false;
        }
        
        return BCrypt.checkpw(password, user.getPassword());
    }
}
