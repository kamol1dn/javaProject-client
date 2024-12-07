import ui.MainMenu;
import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;
import java.util.Scanner;
import utils.Session;

public class Main {
    public static void main(String[] args) {
        // Clear the screen and show a welcome message
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("StudyLink", ScreenUtils.CYAN);

        Scanner scanner = new Scanner(System.in);

        // login status if true it redirectes to main menu
        boolean loggedIn = false;

        // Loop for Login or Register
        while (!loggedIn) {
            ScreenUtils.clearScreen();

            //client project has 2 modes: test for debugging and connection to actual server
            String statusMessage;
            if (Session.getInstance().getStatus()==0){
                statusMessage = "Test mode is on, you wont connect to server";
            } else {
                statusMessage = "Test mode is off, you will be connected to server, IP address: " + Session.getInstance().getIpAddress();
            }


            ScreenUtils.printHeader("StudyLink", ScreenUtils.CYAN);
            ScreenUtils.printMessage("Main Menu:", ScreenUtils.YELLOW, true);
            ScreenUtils.printMessage("1. Login", ScreenUtils.GREEN, false);
            ScreenUtils.printMessage("2. Register", ScreenUtils.BLUE, false);
            ScreenUtils.printMessage("3. Exit", ScreenUtils.RED, false);
            ScreenUtils.printMessage("4. Switch to debug or main mode", ScreenUtils.BLUE, true);
            ScreenUtils.printMessage("Current Mode: "+ statusMessage, ScreenUtils.BLUE, false);
            if (Session.getInstance().getStatus()==1){
                ScreenUtils.printMessage("Server IP adress: "+ Session.getInstance().getIpAddress(), ScreenUtils.BLUE, false);
            }
            System.out.print(ScreenUtils.PURPLE + "Choose an option: " + ScreenUtils.RESET);

            int choice= InputUtils.getIntInRange("", 1, 4);


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

                case 4 -> {
                    // switching between modes
                    ScreenUtils.clearScreen();
                    handleModeSwitch(scanner);
                }


                default -> ScreenUtils.printMessage("Invalid option. Please try again.", ScreenUtils.RED, false);
            }
        }


        ScreenUtils.clearScreen();


        MainMenu.show();


        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Thank you for using the app!", ScreenUtils.GREEN);
    }


    // handling login, if successfull, user id will be stored in session and user objects for further use
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

    private static void handleModeSwitch(Scanner scanner) {
        ScreenUtils.printHeader("Mode", ScreenUtils.BLUE);

        ScreenUtils.printMessage("Current ip adress: " + Session.getInstance().getIpAddress(), ScreenUtils.CYAN, true);

        ScreenUtils.printMessage("\n\n1. Switch to main mode", ScreenUtils.RED, false);
        ScreenUtils.printMessage("2. Switch to test mode", ScreenUtils.RED, false);
        int mode = InputUtils.getIntInRange("Select mode", 1, 2);

        if (mode == 1) {
            Session.getInstance().setStatus(1);
            ScreenUtils.printMessage("\nMain mode selected", ScreenUtils.BLUE, true);


            ScreenUtils.showLoading("Trying to establish connection...", 5);
            String response = ClientConnection.sendRequest("PING|true");

            if (response.startsWith("PING|true")) {
                ScreenUtils.printMessage("Connection is successful, now you can log in", ScreenUtils.GREEN, true);

                ScreenUtils.showLoading("Going back..", 3);
            }


        } else {
            Session.getInstance().setStatus(0);
            ScreenUtils.printMessage("\nTest mode selected", ScreenUtils.BLUE, true);
            ScreenUtils.showLoading("Going back..", 3);
        }

    }
}
