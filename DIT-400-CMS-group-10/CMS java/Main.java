import java.io.*;
import java.util.Scanner;

class Main {
    // Constants
    private static final int MAX_COURSES = 1000;
    private static final int MAX_USERS = 100;

    // Global arrays for courses
    private static String[] courseIds = new String[MAX_COURSES];
    private static String[] titles = new String[MAX_COURSES];
    private static int[] creditHours = new int[MAX_COURSES];
    private static int courseCount = 0;

    // Global arrays for users
    private static String[] usernames = new String[MAX_USERS];
    private static String[] passwords = new String[MAX_USERS];
    private static int userCount = 0;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadUsers();
        loadCourses();

        while (true) {
            System.out.println("\n=== Course Management System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    if (registerUser()) {
                        System.out.println("Registration successful!");
                    } else {
                        System.out.println("Registration failed. Username may already exist.");
                    }
                    break;
                case 2:
                    if (loginUser()) {
                        System.out.println("Login successful!");
                        mainMenu();
                    } else {
                        System.out.println("Login failed. Invalid credentials.");
                    }
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            userCount = 0;
            while ((line = reader.readLine()) != null && userCount < MAX_USERS) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    usernames[userCount] = parts[0];
                    passwords[userCount] = parts[1];
                    userCount++;
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet, that's okay
        }
    }

    private static void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users.csv"))) {
            for (int i = 0; i < userCount; i++) {
                writer.println(usernames[i] + "," + passwords[i]);
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private static boolean registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Check if username exists
        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username)) {
                return false;
            }
        }

        if (userCount < MAX_USERS) {
            usernames[userCount] = username;
            passwords[userCount] = password;
            userCount++;
            saveUsers();
            return true;
        }
        return false;
    }

    private static boolean loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username) && passwords[i].equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static void loadCourses() {
        try (BufferedReader reader = new BufferedReader(new FileReader("courses.csv"))) {
            String line;
            courseCount = 0;
            while ((line = reader.readLine()) != null && courseCount < MAX_COURSES) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    courseIds[courseCount] = parts[0];
                    titles[courseCount] = parts[1];
                    creditHours[courseCount] = Integer.parseInt(parts[2]);
                    courseCount++;
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet, that's okay
        } catch (NumberFormatException e) {
            System.out.println("Error parsing credit hours from file.");
        }
    }

    private static void saveCourses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("courses.csv"))) {
            for (int i = 0; i < courseCount; i++) {
                writer.println(courseIds[i] + "," + titles[i] + "," + creditHours[i]);
            }
        } catch (IOException e) {
            System.out.println("Error saving courses: " + e.getMessage());
        }
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Add Course");
            System.out.println("2. Delete Course");
            System.out.println("3. Search Course");
            System.out.println("4. Update Course");
            System.out.println("5. List All Courses");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1: addCourse(); break;
                case 2: deleteCourse(); break;
                case 3: searchCourse(); break;
                case 4: updateCourse(); break;
                case 5: listCourses(); break;
                case 6:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addCourse() {
        if (courseCount >= MAX_COURSES) {
            System.out.println("Maximum course limit reached!");
            return;
        }

        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine();

        // Check for duplicate
        for (int i = 0; i < courseCount; i++) {
            if (courseIds[i].equals(courseId)) {
                System.out.println("Course ID already exists!");
                return;
            }
        }

        System.out.print("Enter course title: ");
        String title = scanner.nextLine();

        System.out.print("Enter credit hours (1-6): ");
        int hours;
        try {
            hours = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        if (hours < 1 || hours > 6) {
            System.out.println("Credit hours must be between 1 and 6!");
            return;
        }

        courseIds[courseCount] = courseId;
        titles[courseCount] = title;
        creditHours[courseCount] = hours;
        courseCount++;

        saveCourses();
        System.out.println("Course added successfully!");
    }

    private static void deleteCourse() {
        System.out.print("Enter course ID to delete: ");
        String courseId = scanner.nextLine();

        for (int i = 0; i < courseCount; i++) {
            if (courseIds[i].equals(courseId)) {
                // Shift all elements after the found position
                for (int j = i; j < courseCount - 1; j++) {
                    courseIds[j] = courseIds[j + 1];
                    titles[j] = titles[j + 1];
                    creditHours[j] = creditHours[j + 1];
                }
                courseCount--;
                saveCourses();
                System.out.println("Course deleted successfully!");
                return;
            }
        }
        System.out.println("Course not found!");
    }

    private static void searchCourse() {
        System.out.print("Search by: 1. Course ID  2. Title substring: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        boolean found = false;

        if (choice == 1) {
            System.out.print("Enter course ID: ");
            String searchTerm = scanner.nextLine();

            for (int i = 0; i < courseCount; i++) {
                if (courseIds[i].equals(searchTerm)) {
                    System.out.println("Found: " + courseIds[i] + " - " + titles[i]
                            + " (" + creditHours[i] + " credits)");
                    found = true;
                    break;
                }
            }
        } else if (choice == 2) {
            System.out.print("Enter title substring: ");
            String searchTerm = scanner.nextLine();

            for (int i = 0; i < courseCount; i++) {
                if (titles[i].contains(searchTerm)) {
                    System.out.println("Found: " + courseIds[i] + " - " + titles[i]
                            + " (" + creditHours[i] + " credits)");
                    found = true;
                }
            }
        } else {
            System.out.println("Invalid choice!");
            return;
        }

        if (!found) {
            System.out.println("No courses found!");
        }
    }

    private static void updateCourse() {
        System.out.print("Enter course ID to update: ");
        String courseId = scanner.nextLine();

        for (int i = 0; i < courseCount; i++) {
            if (courseIds[i].equals(courseId)) {
                System.out.println("Current title: " + titles[i]);
                System.out.print("Enter new title (or press enter to keep current): ");
                String newTitle = scanner.nextLine();

                System.out.println("Current credit hours: " + creditHours[i]);
                System.out.print("Enter new credit hours (1-6, or 0 to keep current): ");
                int newHours;
                try {
                    newHours = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Keeping current credit hours.");
                    newHours = 0;
                }

                if (!newTitle.isEmpty()) {
                    titles[i] = newTitle;
                }

                if (newHours >= 1 && newHours <= 6) {
                    creditHours[i] = newHours;
                } else if (newHours != 0) {
                    System.out.println("Invalid credit hours. Keeping current value.");
                }

                saveCourses();
                System.out.println("Course updated successfully!");
                return;
            }
        }
        System.out.println("Course not found!");
    }

    private static void listCourses() {
        if (courseCount == 0) {
            System.out.println("No courses available!");
            return;
        }

        System.out.println("\n=== All Courses ===");
        for (int i = 0; i < courseCount; i++) {
            System.out.println((i + 1) + ". " + courseIds[i] + " - " + titles[i]
                    + " (" + creditHours[i] + " credits)");
        }
        System.out.println("Total courses: " + courseCount);
    }

    private static void logout() {
        System.out.println("Logged out successfully!");
    }
}