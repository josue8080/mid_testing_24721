package library_Management.services;

import library_Management.dao.LocationDAO;
import library_Management.models.Location;
import library_Management_enum.LocationType;
import java.util.UUID;

public class LocationService {
    private final LocationDAO locationDAO;

    public LocationService(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    public void createLocation(Location location) {
        locationDAO.save(location);
    }

    public Location getProvinceFromVillage(UUID villageId) {
        Location village = locationDAO.findById(villageId);
        
        if (village == null) {
            throw new IllegalArgumentException("Village not found with ID: " + villageId);
        }

        Location currentLocation = village;
        
        while (currentLocation.getLocationType() != LocationType.PROVINCE &&
               currentLocation.getParentLocation() != null) {
            currentLocation = currentLocation.getParentLocation();
        }

        if (currentLocation.getLocationType() == LocationType.PROVINCE) {
            return currentLocation;
        }
        
        throw new IllegalArgumentException("Province not found for the given village");
    }
}
