package library_Management.tests;

import library_Management_enum.LocationType;
import library_Management.dao.LocationDAO;
import library_Management.models.Location;
import library_Management.services.LocationService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class LocationServiceTest {

    private final LocationDAO locationDAO = new LocationDAO();
    private final LocationService locationService = new LocationService(locationDAO);
    private UUID villageId;

    @Before
    public void setUp() {
        // Set up locations hierarchy
        Location province = new Location("ProvinceName", LocationType.PROVINCE, null);
        Location district = new Location("DistrictName", LocationType.DISTRICT, province);
        Location sector = new Location("SectorName", LocationType.SECTOR, district);
        Location cell = new Location("CellName", LocationType.CELL, sector);
        Location village = new Location("VillageName", LocationType.VILLAGE, cell);

        // Save each location in the hierarchy
        locationService.createLocation(province);
        locationService.createLocation(district);
        locationService.createLocation(sector);
        locationService.createLocation(cell);
        locationService.createLocation(village);

        // Logging IDs for reference
        System.out.println("Province ID: " + province.getId());
        System.out.println("District ID: " + district.getId());
        System.out.println("Sector ID: " + sector.getId());
        System.out.println("Cell ID: " + cell.getId());
        System.out.println("Village ID: " + village.getId());

        // Store village ID for testing retrieval
        villageId = village.getId();
    }

    @Test
    public void testGetProvinceFromVillage() {
        // Verify that using a village ID retrieves the correct province
        Location retrievedProvince = locationService.getProvinceFromVillage(villageId);
        assertNotNull(retrievedProvince);
        assertEquals("ProvinceName", retrievedProvince.getName());
    }
}
