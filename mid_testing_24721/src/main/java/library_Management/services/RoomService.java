package library_Management.services;

import library_Management.models.Room;
import library_Management.models.Shelf;
import library_Management.dao.RoomDAO;
import library_Management.dao.ShelfDAO;

import java.util.List;

public class RoomService {
    private final RoomDAO roomDAO;
    private final ShelfDAO shelfDAO;

    // Constructor to initialize DAOs
    public RoomService(RoomDAO roomDAO, ShelfDAO shelfDAO) {
        this.roomDAO = roomDAO;
        this.shelfDAO = shelfDAO;
    }

    // Method to assign a shelf to a room and update both shelf and room records
    public void assignShelfToRoom(Shelf shelf, Room room) {
        shelf.setRoom(room);
        shelfDAO.save(shelf);
        room.getShelves().add(shelf);
        roomDAO.save(room);
    }

    // Method to find the room with the fewest books
    public Room getRoomWithFewestBooks() {
        List<Room> rooms = roomDAO.findAll();
        Room roomWithFewestBooks = null;
        int fewestBooks = Integer.MAX_VALUE;

        for (Room room : rooms) {
            int bookCount = room.getShelves().stream()
                               .mapToInt(shelf -> shelf.getBooks().size())
                               .sum();
            if (bookCount < fewestBooks) {
                fewestBooks = bookCount;
                roomWithFewestBooks = room;
            }
        }
        return roomWithFewestBooks;
    }
}
