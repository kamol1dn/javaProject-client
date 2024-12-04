package ui.student.Timetable;

import models.User;
import network.ClientConnection;
import utils.InputUtils;
import utils.ScreenUtils;

import java.util.Scanner;

public class TimetableEdit {

    public static void timetableEdit(int day){

        //confirmation message
        switch (day){
            case 1:
                ScreenUtils.printMessage("Day chosen: Monday", ScreenUtils.GREEN, true);
                break;
            case 2:
                ScreenUtils.printMessage("Day chosen: Tuesday", ScreenUtils.GREEN, true);
                break;
            case 3:
                ScreenUtils.printMessage("Day chosen: Wednesday", ScreenUtils.GREEN, true);
                break;
            case 4:
                ScreenUtils.printMessage("Day chosen: Thursday", ScreenUtils.GREEN, true);
                break;
            case 5:
                ScreenUtils.printMessage("Day chosen: Friday", ScreenUtils.GREEN, true);
                break;
            case 6:
                ScreenUtils.printMessage("Day chosen: Saturday", ScreenUtils.GREEN, true);
                break;
        }
        // User interaction for editing
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter slot number to edit (0 to go back): ");
            int choice;
            choice= InputUtils.getIntInRange(" ",0,4);

            if (choice == 0) {
                // Go back to the previous menu
                return;
            } else if (choice >= 1 && choice <= 4) {
                // Edit selected timetable slot
                System.out.print("Enter new subject: ");
                String newSubject = scanner.nextLine().trim();
                System.out.print("Enter new time (hh:mm): ");
                String newTime =InputUtils.getTime("hh:mm");

                // Validate inputs
                if (newSubject.isEmpty() || newTime.isEmpty()) {
                    System.out.println("Subject and time cannot be empty. Try again.");
                    continue;
                }

                // Send edit request
                String editRequest = String.format("TIMETABLEEDIT|true|%s|%s|slot_%d|%s;%s",
                        User.getInstance().getUserId(), day, choice, newSubject, newTime);
                String editResponse = ClientConnection.sendRequest(editRequest);

                if (editResponse.equals("TIMETABLEEDIT|true")) {
                    System.out.println("Timetable updated successfully.");
                    break; // Reload the timetable
                } else {
                    System.out.println("Failed to update timetable. Please try again.");
                }
            } else {
                System.out.println("Invalid choice. Please select a number between 0-4.");
            }
        }
    }
}
