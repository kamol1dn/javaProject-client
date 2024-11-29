import ui.MainMenu;
import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;
import java.util.Scanner;
import utils.Session;
import models.User;

public class Main {
    public static void main(String[] args) {
        // Clear the screen and show a welcome message
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Welcome to Student Companion App", ScreenUtils.CYAN);

        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        // Loop for Login or Register
        while (!loggedIn) {
            ScreenUtils.clearScreen();
            ScreenUtils.printHeader("Welcome to Student Companion App", ScreenUtils.CYAN);
            ScreenUtils.printMessage("Main Menu:", ScreenUtils.YELLOW, true);
            ScreenUtils.printMessage("1. Login", ScreenUtils.GREEN, false);
            ScreenUtils.printMessage("2. Register", ScreenUtils.BLUE, false);
            ScreenUtils.printMessage("3. Exit", ScreenUtils.RED, false);

            System.out.print(ScreenUtils.PURPLE + "Choose an option: " + ScreenUtils.RESET);
            int choice= InputUtils.getIntInRange("", 1, 3);


            switch (choice) {
                case 1 -> {
                    ScreenUtils.clearScreen();
                    if (handleLogin(scanner)) {
                        loggedIn = true;
                    }
                }
                case 2 -> {
                    ScreenUtils.clearScreen();
                    handleRegister(scanner);
                }
                case 3 -> {
                    ScreenUtils.printMessage("Thank you for using the app! Goodbye!", ScreenUtils.CYAN, true);
                    System.exit(0);
                }
                default -> ScreenUtils.printMessage("Invalid option. Please try again.", ScreenUtils.RED, false);
            }
        }


        ScreenUtils.clearScreen();


        MainMenu.show();


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

        if (response.startsWith("LOGIN|true")) {

            Session.getInstance().setUserId(userId);
            String[] answer = response.split("\\|");
            String User_name = answer[2];
            models.User.getInstance().setName(User_name);
            models.User.getInstance().setPassword(password);
            models.User.getInstance().setUserId(userId);


            ScreenUtils.printMessage("\nLogin successful! Welcome back, " + User_name  + "!", ScreenUtils.GREEN, true);
            ScreenUtils.showLoading("\nLoading...", 3);
            return true;
        } else {
            ScreenUtils.printMessage("\nLogin failed. Please check your credentials and try again.", ScreenUtils.RED, false);
            ScreenUtils.showLoading("\nGoing back", 3);
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


        String response = ClientConnection.sendRequest("REGISTER|" + userId + "|" + password + "|" + name);

        if ("REGISTER|true".equals(response)) {
            ScreenUtils.printMessage("\nRegistration successful! You can now log in.", ScreenUtils.GREEN, true);
            ScreenUtils.showLoading("\nGoing back..", 4);
        } else {
            ScreenUtils.printMessage("\nRegistration failed. The user ID may already exist.", ScreenUtils.RED, false);
            ScreenUtils.showLoading("\nGoing back..", 4);
        }
    }
}
