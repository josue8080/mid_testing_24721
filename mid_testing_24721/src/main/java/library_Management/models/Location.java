package library_Management.models;

import javax.persistence.*;
import library_Management_enum.LocationType;
import java.util.UUID;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_type")
    private LocationType locationType;

    @ManyToOne
    @JoinColumn(name = "parent_location_id")
    @Type(type = "uuid-char")
    private Location parentLocation;

    // Default Constructor
    public Location() {}

    // Parameterized Constructor
    public Location(String name, LocationType locationType, Location parentLocation) {
        this.name = name;
        this.locationType = locationType;
        this.parentLocation = parentLocation;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Location getParentLocation() {
        return parentLocation;
    }

    public void setParentLocation(Location parentLocation) {
        this.parentLocation = parentLocation;
    }
}
