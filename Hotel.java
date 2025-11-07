
import java.util.Scanner;

public class Hotel {
    private static final int FLOORS = 3;
    private static final int ROOMS_PER_FLOOR = 4;
    private boolean[][] rooms;  

    public Hotel() {
        rooms = new boolean[FLOORS][ROOMS_PER_FLOOR];
       
    }

    public boolean bookRoom(int floor, int room) {
        if (floor < 1 || floor > FLOORS || room < 1 || room > ROOMS_PER_FLOOR) {
            System.out.println("Invalid floor or room number.");
            return false;
        }
        if (rooms[floor - 1][room - 1]) {
            System.out.println("Room is already booked.");
            return false;
        } else {
            rooms[floor - 1][room - 1] = true;
            System.out.println("Room booked successfully.");
            return true;
        }
    }

    public void displayRooms() {
        System.out.println("Hotel Room Booking Status (true = booked, false = available):");
        for (int i = 0; i < FLOORS; i++) {
            System.out.print("Floor " + (i + 1) + ": ");
            for (int j = 0; j < ROOMS_PER_FLOOR; j++) {
                System.out.print("Room " + (j + 1) + "=" + rooms[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Book a room");
            System.out.println("2. Display room status");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter floor number (1-3): ");
                    int floor = scanner.nextInt();
                    System.out.print("Enter room number (1-4): ");
                    int room = scanner.nextInt();
                    hotel.bookRoom(floor, room);
                    break;
                case 2:
                    hotel.displayRooms();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}