package utils;

public class ScreenUtils {
    // ANSI Reset Code
    public static final String RESET = "\u001B[0m";

    // Text Colors
   public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // Text Styles
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";


    public static void clearScreen() {
        System.out.print("\033[H\033[2J" + RESET);
        System.out.flush();
    }



    public static void printDivider() {
        System.out.println("======================================");
    }
    public static void printDivider(String color) {
        System.out.println(color + "======================================" + RESET);
    }


    public static void printHeader(String title) {
        printDivider();
        System.out.println(BOLD + "  " + title + RESET);
        printDivider();
    }
    public static void printHeader(String title, String color) {
        printDivider(color);
        printCentered(BOLD + "  " + title, 40, color);

        printDivider(color);
    }


    public static void promptEnterKey() {
        System.out.println("\n" + CYAN + "Press Enter to continue..." + RESET);
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }


    public static void printMessage(String message, String color, boolean isBold) {
        if (isBold) {
            System.out.println(color + BOLD + message + RESET);
        } else {
            System.out.println(color + message + RESET);
        }
    }
    public static void printMessage(String message) {

            System.out.println(message + RESET);

    }


    public static void drawLine(int length, char character, String color) {
        for (int i = 0; i < length; i++) {
            System.out.print(color + character + RESET);
        }
        System.out.println();
    }




    public static void printCentered(String text, int width, String color) {
        int padding = (width - text.length()) / 2;
        System.out.print(color);
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.print(text);
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.println(RESET);
    }

    public static void printCentered(String text, int width) {
        int padding = (width - text.length()) / 2;
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.print(text);
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
    }

    public static void showLoading(String message, int duration) {
        System.out.print(message);
        for (int i = 0; i < duration; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        System.out.print(" *");
        }
        System.out.println();
    }
    public static void showLoading(Double duration) {

        for (int i = 0; i < duration; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
