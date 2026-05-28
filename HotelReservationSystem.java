package hotelreservation;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

class Room {

    int roomNumber;
    String category;
    double price;
    boolean isBooked;

    Room(int roomNumber, String category, double price) {

        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isBooked = false;
    }

    @Override
    public String toString() {

        return "Room No: " + roomNumber +
                " | Category: " + category +
                " | Price: ₹" + price +
                " | Status: " + (isBooked ? "Booked" : "Available");
    }
}

class Booking {

    String customerName;
    String bookingDate;
    Room room;

    Booking(String customerName, String bookingDate, Room room) {

        this.customerName = customerName;
        this.bookingDate = bookingDate;
        this.room = room;
    }

    @Override
    public String toString() {

        return "Customer Name: " + customerName +
                "\nBooking Date: " + bookingDate +
                "\nRoom Number: " + room.roomNumber +
                "\nRoom Category: " + room.category +
                "\nRoom Price: ₹" + room.price;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // LOGIN SYSTEM
        String username = "admin";
        String password = "1234";

        System.out.println("========== LOGIN SYSTEM ==========");

        System.out.print("Enter Username: ");
        String user = sc.next();

        System.out.print("Enter Password: ");
        String pass = sc.next();

        if (!user.equals(username) || !pass.equals(password)) {

            System.out.println("Invalid Credentials!");
            return;
        }

        System.out.println("Login Successful!");

        // ADDING ROOMS
        rooms.add(new Room(101, "Standard", 2000));
        rooms.add(new Room(102, "Standard", 2000));

        rooms.add(new Room(201, "Deluxe", 4000));
        rooms.add(new Room(202, "Deluxe", 4000));

        rooms.add(new Room(301, "Suite", 7000));
        rooms.add(new Room(302, "Suite", 7000));

        int choice;

        do {

            System.out.println("\n========== HOTEL MANAGEMENT SYSTEM ==========");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Booking Details");
            System.out.println("5. View Total Bookings");
            System.out.println("6. Exit");

            System.out.print("Enter Your Choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    viewRooms();
                    break;

                case 2:
                    bookRoom(sc);
                    break;

                case 3:
                    cancelBooking(sc);
                    break;

                case 4:
                    viewBookings();
                    break;

                case 5:
                    System.out.println("Total Bookings: " + bookings.size());
                    break;

                case 6:
                    System.out.println("Thank You For Using The System!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 6);

        sc.close();
    }

    // VIEW AVAILABLE ROOMS
    static void viewRooms() {

        System.out.println("\n========== AVAILABLE ROOMS ==========");

        boolean found = false;

        for (Room room : rooms) {

            if (!room.isBooked) {

                System.out.println(room);
                found = true;
            }
        }

        if (!found) {

            System.out.println("No Rooms Available!");
        }
    }

    // BOOK ROOM
    static void bookRoom(Scanner sc) {

        sc.nextLine();

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Booking Date (DD/MM/YYYY): ");
        String date = sc.nextLine();

        viewRooms();

        System.out.print("Enter Room Number To Book: ");
        int roomNumber = sc.nextInt();

        for (Room room : rooms) {

            if (room.roomNumber == roomNumber && !room.isBooked) {

                System.out.println("Room Price: ₹" + room.price);

                System.out.print("Proceed To Payment? (yes/no): ");
                sc.nextLine();
                String payment = sc.nextLine();

                if (payment.equalsIgnoreCase("yes")) {

                    room.isBooked = true;

                    Booking booking = new Booking(name, date, room);

                    bookings.add(booking);

                    saveBookingToFile(booking);

                    System.out.println("\nBooking Successful!");
                    System.out.println("Room Booked Successfully!");

                } else {

                    System.out.println("Payment Cancelled!");
                }

                return;
            }
        }

        System.out.println("Room Not Available!");
    }

    // CANCEL BOOKING
    static void cancelBooking(Scanner sc) {

        System.out.print("Enter Room Number To Cancel Booking: ");
        int roomNumber = sc.nextInt();

        for (Booking booking : bookings) {

            if (booking.room.roomNumber == roomNumber) {

                booking.room.isBooked = false;

                bookings.remove(booking);

                System.out.println("Booking Cancelled Successfully!");

                return;
            }
        }

        System.out.println("Booking Not Found!");
    }

    // VIEW BOOKINGS
    static void viewBookings() {

        System.out.println("\n========== BOOKING DETAILS ==========");

        if (bookings.isEmpty()) {

            System.out.println("No Bookings Found!");
            return;
        }

        for (Booking booking : bookings) {

            System.out.println(booking);
            System.out.println("-----------------------------------");
        }
    }

    // SAVE BOOKINGS TO FILE
    static void saveBookingToFile(Booking booking) {

        try {

            FileWriter writer = new FileWriter("bookings.txt", true);

            writer.write(booking.toString());
            writer.write("\n-----------------------------------\n");

            writer.close();

        } catch (IOException e) {

            System.out.println("Error Saving Booking!");
        }
    }
}