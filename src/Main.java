import ui.MainMenu;
import utils.ScreenUtils;
import network.ClientConnection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Clear the screen and show a welcome message
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Welcome to Student Companion App", ScreenUtils.CYAN);

        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        // Loop for Login or Register
        while (!loggedIn) {
            ScreenUtils.printMessage("Main Menu:", ScreenUtils.YELLOW, true);
            ScreenUtils.printMessage("1. Login", ScreenUtils.GREEN, false);
            ScreenUtils.printMessage("2. Register", ScreenUtils.BLUE, false);
            ScreenUtils.printMessage("3. Exit", ScreenUtils.RED, false);

            System.out.print(ScreenUtils.PURPLE + "Choose an option: " + ScreenUtils.RESET);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    // Login process
                    if (handleLogin(scanner)) {
                        loggedIn = true;
                    }
                }
                case 2 -> handleRegister(scanner);
                case 3 -> {
                    ScreenUtils.printMessage("Thank you for using the app! Goodbye!", ScreenUtils.CYAN, true);
                    System.exit(0);
                }
                default -> ScreenUtils.printMessage("Invalid option. Please try again.", ScreenUtils.RED, false);
            }
        }

        // Clear screen before showing the main menu
        ScreenUtils.clearScreen();

        // Show the main menu
        MainMenu.show();

        // Exit the program with a goodbye message
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Thank you for using the app!", ScreenUtils.GREEN);
    }


    private static boolean handleLogin(Scanner scanner) {
        ScreenUtils.printHeader("Login", ScreenUtils.GREEN);

        System.out.print(ScreenUtils.CYAN + "Enter your user ID: " + ScreenUtils.RESET);
        String userId = scanner.nextLine();

        System.out.print(ScreenUtils.CYAN + "Enter your password: " + ScreenUtils.RESET);
        String password = scanner.nextLine();


        String response = ClientConnection.sendRequest("LOGIN|" + userId + "|" + password);

        if ("LOGIN|true".equals(response)) {
            ScreenUtils.printMessage("\nLogin successful! Welcome back, " + userId + "!", ScreenUtils.GREEN, true);
            return true;
        } else {
            ScreenUtils.printMessage("\nLogin failed. Please check your credentials and try again.", ScreenUtils.RED, false);
            return false;
        }
    }


    private static void handleRegister(Scanner scanner) {
        ScreenUtils.printHeader("Register", ScreenUtils.BLUE);

        System.out.print(ScreenUtils.CYAN + "Enter a user ID: " + ScreenUtils.RESET);
        String userId = scanner.nextLine();

        System.out.print(ScreenUtils.CYAN + "Enter a password: " + ScreenUtils.RESET);
        String password = scanner.nextLine();

        System.out.print(ScreenUtils.CYAN + "Enter your name: " + ScreenUtils.RESET);
        String name = scanner.nextLine();


        String response = ClientConnection.sendRequest("REGISTER |" + userId + "|" + password + "|" + name);

        if ("REGISTER|true".equals(response)) {
            ScreenUtils.printMessage("\nRegistration successful! You can now log in.", ScreenUtils.GREEN, true);
        } else {
            ScreenUtils.printMessage("\nRegistration failed. The user ID may already exist.", ScreenUtils.RED, false);
        }
    }
}
