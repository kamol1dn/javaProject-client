package utils;

import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user for a string input and ensures it is non-empty.
     *
     * @param prompt The message displayed to the user.
     * @return A non-empty string input from the user.
     */
    public static String getNonEmptyString(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    /**
     * Prompts the user for an integer input and validates it is within a specified range.
     *
     * @param prompt The message displayed to the user.
     * @param min    The minimum valid value.
     * @param max    The maximum valid value.
     * @return A valid integer input from the user.
     */
    public static int getIntInRange(String prompt, int min, int max) {
        int input;
        while (true) {
            try {
                System.out.print(prompt);
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

    /**
     * Prompts the user for a date input in the format `dd.mm.yyyy` and validates the format.
     *
     * @param prompt The message displayed to the user.
     * @return A valid date string in `dd.mm.yyyy` format.
     */
    public static String getDate(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                return input;
            } else {
                System.out.println("Invalid date format. Please use 'dd.mm.yyyy'.");
            }
        }
    }
}
