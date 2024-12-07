package utils;

import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);


    public static String getNonEmptyString(String prompt) {
        String input;
        do {
            System.out.print(prompt+ ": ");
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }


    public static int getIntInRange(String prompt, int min, int max) {
        int input;
        while (true) {
            try {
                System.out.print(prompt+ ": ");
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.printf("Input must be between %d and %d. Please try again.%n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static int getInt() {
        while (true) {
            System.out.print("Enter a number: ");
            try {
                return Integer.parseInt(scanner.nextLine().trim()); // Parse and return the integer
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static String getTime(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt+ ": ");
            input = scanner.nextLine().trim();
            if (input.matches("\\d{2}\\:\\d{2}")) {
                return input;
            } else {
                System.out.println("Invalid time format. Please use 'hh:mm '.");
            }
        }
    }

    public static String getDate(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt+ ": ");
            input = scanner.nextLine().trim();
            if (input.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                return input;
            } else {
                System.out.println("Invalid date format. Please use 'dd.mm.yyyy '.");
            }
        }
    }
}
