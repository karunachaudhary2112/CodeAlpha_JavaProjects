import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Room {
    int roomNo;
    String category;
    int price;
    boolean available = true;

    Room(int roomNo, String category, int price) {
        this.roomNo = roomNo;
        this.category = category;
        this.price = price;
    }
}

class Booking {
    String id, guestName, category, status;
    int roomNo, nights, amount;

    Booking(String id, String guestName, int roomNo, String category,
            int nights, int amount, String status) {
        this.id = id;
        this.guestName = guestName;
        this.roomNo = roomNo;
        this.category = category;
        this.nights = nights;
        this.amount = amount;
        this.status = status;
    }

    String toFileString() {
        return id + "|" + guestName + "|" + roomNo + "|" + category + "|"
                + nights + "|" + amount + "|" + status;
    }
}

public class Task3_HotelReservationSystem {
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static int nextId = 1001;
    static final String FILE = "bookings.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Setup rooms
        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(301, "Suite", 4500));

        loadBookings();

        int choice;
        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1: viewRooms(); break;
                case 2: bookRoom(); break;
                case 3: cancelBooking(); break;
                case 4: viewBooking(); break;
                case 5: saveBookings();
                    System.out.println("Bookings saved to " + FILE + ". Goodbye!");
                    break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 5);

        sc.close();
    }

    // ---------- Menu actions ----------

    static void viewRooms() {
        System.out.println("\n------------ AVAILABLE ROOMS ------------");
        System.out.println("Room No   Category    Price/Night");
        boolean any = false;
        for (Room r : rooms) {
            if (r.available) {
                System.out.printf("%-9d %-11s Rs. %d%n", r.roomNo, r.category, r.price);
                any = true;
            }
        }
        if (!any) System.out.println("No rooms available.");
        System.out.println("-----------------------------------------");
    }

    static void bookRoom() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim().replace("|", "");
        System.out.print("Enter category (Standard/Deluxe/Suite): ");
        String category = sc.nextLine().trim();
        int nights = readInt("Enter number of nights: ");

        if (nights <= 0) {
            System.out.println("Nights must be at least 1.");
            return;
        }

        Room found = null;
        for (Room r : rooms) {
            if (r.available && r.category.equalsIgnoreCase(category)) {
                found = r;
                break;
            }
        }

        if (found == null) {
            System.out.println("Sorry, no " + category + " room is available.");
            return;
        }

        int total = found.price * nights;
        System.out.println("\nRoom " + found.roomNo + " (" + found.category + ") is available.");
        System.out.println("Total amount: Rs. " + total);
        System.out.print("Proceed to payment? (yes/no): ");
        String ans = sc.nextLine().trim();

        if (!ans.equalsIgnoreCase("yes")) {
            System.out.println("Booking cancelled.");
            return;
        }

        // Payment simulation
        System.out.println("Processing payment...");
        System.out.println("Payment successful!");

        found.available = false;
        String id = "B" + nextId++;
        bookings.add(new Booking(id, name, found.roomNo, found.category,
                nights, total, "CONFIRMED"));
        System.out.println("Booking confirmed! Your Booking ID: " + id);
    }

    static void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        String id = sc.nextLine().trim();
        Booking b = findBooking(id);

        if (b == null) {
            System.out.println("Booking not found.");
        } else if (b.status.equals("CANCELLED")) {
            System.out.println("Booking already cancelled.");
        } else {
            b.status = "CANCELLED";
            for (Room r : rooms) {
                if (r.roomNo == b.roomNo) r.available = true;
            }
            System.out.println("Booking " + b.id + " cancelled. Room "
                    + b.roomNo + " is available again.");
        }
    }

    static void viewBooking() {
        System.out.print("Enter Booking ID: ");
        String id = sc.nextLine().trim();
        Booking b = findBooking(id);

        if (b == null) {
            System.out.println("Booking not found.");
            return;
        }
        System.out.println("\n------------ BOOKING DETAILS ------------");
        System.out.println("Booking ID : " + b.id);
        System.out.println("Guest Name : " + b.guestName);
        System.out.println("Room No    : " + b.roomNo);
        System.out.println("Category   : " + b.category);
        System.out.println("Nights     : " + b.nights);
        System.out.println("Amount Paid: Rs. " + b.amount);
        System.out.println("Status     : " + b.status);
        System.out.println("-----------------------------------------");
    }

    // ---------- Helpers ----------

    static Booking findBooking(String id) {
        for (Booking b : bookings) {
            if (b.id.equalsIgnoreCase(id)) return b;
        }
        return null;
    }

    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // ---------- File I/O ----------

    static void saveBookings() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Booking b : bookings) {
                pw.println(b.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    static void loadBookings() {
        File f = new File(FILE);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length != 7) continue;

                Booking b = new Booking(p[0], p[1], Integer.parseInt(p[2]), p[3],
                        Integer.parseInt(p[4]), Integer.parseInt(p[5]), p[6]);
                bookings.add(b);

                // keep room status and ID counter in sync
                if (b.status.equals("CONFIRMED")) {
                    for (Room r : rooms) {
                        if (r.roomNo == b.roomNo) r.available = false;
                    }
                }
                int num = Integer.parseInt(b.id.substring(1));
                if (num >= nextId) nextId = num + 1;
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading bookings: " + e.getMessage());
        }
    }
}