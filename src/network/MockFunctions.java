package network;
import java.util.Iterator;
import static network.MockData.*;


public class MockFunctions {


    // ========================== Handlers for Commands ==========================

    protected static String handleLogin(String[] parts) {
        if (parts.length != 3) {
            return "LOGIN|false"; // Invalid format
        }

        String userId = parts[1];
        String password = parts[2];

        if (MOCK_USER_DB.containsKey(userId) && MOCK_USER_DB.get(userId)[0].equals(password)) {

            String userName = MOCK_USER_DB.get(userId)[1];
            return "LOGIN|true|" + userName;
        } else {
            return "LOGIN|false";
        }
    }

     static String handleRegister(String[] parts) {
        if (parts.length != 4) {
            return "REGISTER|false"; // Invalid format
        }

        String userId = parts[1];
        String password = parts[2];
        String name = parts[3];

        if (MOCK_USER_DB.containsKey(userId)) {
            return "REGISTER|false"; // User already exists
        } else {
            MOCK_USER_DB.put(userId, new String[]{password, name});
            return "REGISTER|true";
        }
    }

    protected static String handleTaskEdit(String[] parts) {
        if (parts.length != 4) {
            return "TASKEDIT|false"; // Invalid format
        }

        String oldTaskName = parts[1];
        String newTaskName = parts[2];
        String newDeadline = parts[3];

        boolean taskFound = false;
        for (String[] task : MOCK_TASKLIST) {
            String studentId = task[0];
            String currentTaskName = task[1];
            String currentDeadline = task[2];

            if (currentTaskName.equals(oldTaskName)) {
                if (currentTaskName.equals(newTaskName) && currentDeadline.equals(newDeadline)) {
                    return "TASKEDIT|false"; // No changes made
                }

                task[1] = newTaskName;
                task[2] = newDeadline;

                taskFound = true;
                break;
            }
        }

        return taskFound ? "TASKEDIT|true" : "TASKEDIT|false";
    }

    protected static String handleUserBooks(String[] parts) {
        if (parts.length != 2) {
            return "USERBOOKS|false";
        }

        String userId = parts[1]; // Extract the user ID from the request
        StringBuilder response = new StringBuilder("USERBOOKS|true|"); // Start building the response

        boolean hasBooks = false;

        for (String[] entry : MOCK_BOOK_USER) {
            if (entry[0].equals(userId)) {
                hasBooks = true;
                String bookName = entry[1];

                for (String[] book : MOCK_BOOKLIST) {
                    if (book[0].equals(bookName)) {
                        response.append(bookName).append(",").append(book[1]).append("|");
                        break;
                    }
                }
            }
        }

        if (!hasBooks) {
            return "USERBOOKS|false";
        }

        return response.substring(0, response.length() - 1); // Remove the last "|"
    }

    protected static String handleTaskDeleteAll(String[] parts) {
        if (parts.length != 2) { // TASKALLDELETE requires 2 parts: command and user_id
            return "TASKALLDELETE|false"; // Invalid format
        }

        String userId = parts[1];
        boolean taskDeleted = false;

        Iterator<String[]> iterator = MOCK_TASKLIST.iterator();
        while (iterator.hasNext()) {
            String[] task = iterator.next();
            if (task[0].equals(userId)) {
                iterator.remove();
                taskDeleted = true;
            }
        }

        return taskDeleted ? "TASKALLDELETE|true" : "TASKALLDELETE|false";
    }

    protected static String handleTaskAddNew(String[] parts) {
        if (parts.length != 4) { // TASKADDNEW requires 4 parts: command, user_id, task_name, task_deadline
            return "TASKADDNEW|false"; // Invalid format
        }

        String userId = parts[1];
        String taskName = parts[2];
        String taskDeadline = parts[3];

        for (String[] task : MOCK_TASKLIST) {
            if (task[0].equals(userId) && task[1].equals(taskName)) {
                return "TASKADDNEW|false"; // Task with the same name already exists for the user
            }
        }

        MOCK_TASKLIST.add(new String[]{userId, taskName, taskDeadline});
        return "TASKADDNEW|true"; // Success
    }

    protected static String handleAddBook(String[] parts) {
        if (parts.length != 4) { // ADDNEWBOOK requires 3 parts: command, bookname, author
            return "ADDNEWBOOK|false"; // Invalid format
        }

        String bookName = parts[2];
        String author = parts[3];

        for (String[] book : MOCK_BOOKLIST) {
            if (book[0].equalsIgnoreCase(bookName) && book[1].equalsIgnoreCase(author)) {
                return "ADDNEWBOOK|false";
            }
        }

        MOCK_BOOKLIST.add(new String[]{bookName, author});
        return "ADDNEWBOOK|true"; // Success
    }

    protected static String handleViewAllBooks(String[] parts) {
        if (parts.length != 2) { // VIEWALLBOOKS requires 1 part: command
            return "VIEWALLBOOKS|false"; // Invalid format
        }

        StringBuilder response = new StringBuilder("VIEWALLBOOKS|true");
        for (String[] book : MOCK_BOOKLIST) {
            response.append("|").append(book[0]).append(",").append(book[1]); // Format: title,author
        }
        return response.toString(); // Returns list of all books
    }

    protected static String handleAssignBook(String[] parts) {
        if (parts.length != 3) {
            return "ASSIGNBOOK|false"; // Invalid format
        }

        String userId = parts[1];
        String bookName = parts[2];

        for (String[] entry : MOCK_BOOK_USER) {
            if (entry[0].equals(userId) && entry[1].equals(bookName)) {
                return "ASSIGNBOOK|false"; // User already borrowed the book
            }
        }
        MOCK_BOOK_USER.add(new String[]{userId, bookName});
        return "ASSIGNBOOK|true"; // Success
    }

    protected static String handleReturnBook(String[] parts) {
        if (parts.length != 3) {
            return "BOOKRETURN|false"; // Invalid format
        }
        String userId = parts[1];
        String bookName = parts[2];

        boolean bookFound = false;
        Iterator<String[]> iterator = MOCK_BOOK_USER.iterator();
        while (iterator.hasNext()) {
            String[] entry = iterator.next();
            if (entry[0].equals(userId) && entry[1].equals(bookName)) {
                iterator.remove();
                bookFound = true;
                break;
            }
        }
        return bookFound ? "BOOKRETURN|true" : "BOOKRETURN|false";
    }

    protected static String handleDetailStudent(String[] parts) {
        if (parts.length != 2) {
            return "STUDENTDETAIL|false";
        }

        String userId = parts[1];

        if (!MOCK_USER_DB.containsKey(userId)) {
            return "STUDENTDETAIL|false"; // User not found
        }

        String[] userDetails = MOCK_USER_DB.get(userId);
        String password = userDetails[0];
        String name = userDetails[1];

        return String.format("STUDENTDETAIL|true|%s|%s|%s", name, userId, password);
    }

    protected static String handleTaskList(String[] parts) {
        if (parts.length != 2) {
            return "TASKLIST|false";
        }

        String userId = parts[1];
        StringBuilder response = new StringBuilder("TASKLIST|true|");
        for (String[] task : MOCK_TASKLIST) {
            if (task[0].equals(userId)) {
                response.append(task[1]).append(" (").append(task[2]).append("), ");
            }
        }
        return response.toString();
    }

    protected static String handleUserEditName(String[] parts) {
        if (parts.length != 4) {
            return "USEREDITNAME|false";
        }
        String userId = parts[2];
        String newName = parts[3];
        if (!MOCK_USER_DB.containsKey(userId)) {
            return "USEREDITNAME|false";
        }
        MOCK_USER_DB.get(userId)[1] = newName;
        return "USEREDITNAME|true";
    }

    protected static String handleUserEditPassword (String[]parts){
        if (parts.length != 3) {
            return "USEREDITPASSWORD|false";
        }

        String userId = parts[1];
        String newPassword = parts[2];

        if (!MOCK_USER_DB.containsKey(userId)) {
            return "USEREDITPASSWORD|false";
        }
        MOCK_USER_DB.get(userId)[0] = newPassword;
        return "USEREDITPASSWORD|true";
    }

    protected static String handleTimetableView(String[] parts) {
        // Validate input length
        if (parts.length != 2) {
            return "VIEWTIMETABLE|false";
        }

        // Extract userId and trim whitespace
        String userId = parts[1].trim();

        // Check if user exists in the timetable maps
        boolean userExists = MOCK_TIMETABLE_MONDAY.containsKey(userId) &&
                MOCK_TIMETABLE_TUESDAY.containsKey(userId) &&
                MOCK_TIMETABLE_WEDNESDAY.containsKey(userId) &&
                MOCK_TIMETABLE_THURSDAY.containsKey(userId) &&
                MOCK_TIMETABLE_FRIDAY.containsKey(userId);

        if (!userExists) {
            return "VIEWTIMETABLE|false";
        }

        // Build the response string
        StringBuilder response = new StringBuilder("VIEWTIMETABLE|true|");

        // Append each day's timetable to the response
        response.append(String.join("|", MOCK_TIMETABLE_MONDAY.get(userId)));
        response.append(new String("|"));
        response.append(String.join("|", MOCK_TIMETABLE_TUESDAY.get(userId)));
        response.append(new String("|"));
        response.append(String.join("|", MOCK_TIMETABLE_WEDNESDAY.get(userId)));
        response.append(new String("|"));
        response.append(String.join("|", MOCK_TIMETABLE_THURSDAY.get(userId)));
        response.append(new String("|"));
        response.append(String.join("|", MOCK_TIMETABLE_FRIDAY.get(userId)));

        return response.toString();
    }

    protected static String handleTimetableEdit(String[] parts) {
        if (parts.length != 6) {
            return "TIMETABLEEDIT|false";
        }

        //  String userId = parts[2];  // we dont need it there yet
        int day = Integer.parseInt(parts[3]);
        String slot = parts[4];
        String newName = parts[5];

        if (!slot.matches("slot_[1-4]")) {
            return "TIMETABLEEDIT|false"; // Invalid slot number
        }

        if (newName == null || newName.trim().isEmpty()) {
            return "TIMETABLEEDIT|false";
        }
        switch (day){
            case 1:
                return handleTimetableEditMonday(parts);
            case 2:
                return handleTimetableEditTuesday(parts);
            case 3:
                return handleTimetableEditWednesday(parts);
            case 4:
                return  handleTimetableEditThursday(parts);
            case 5:
                return  handleTimetableEditFriday(parts);
            case 6:
                return  handleTimetableEditSaturday(parts);

            default: return "TIMETABLEEDIT|false";
        }

    }

    private static String handleTimetableEditMonday(String[] parts) {

        String userId = parts[2];
        // no need for day anymore
        String slot = parts[4];
        String newName = parts[5];


        if (!MOCK_TIMETABLE_MONDAY.containsKey(userId)) {
            return "TIMETABLEEDIT|false";
        }

        int slotIndex = Integer.parseInt(slot.split("_")[1]) - 1;

        String[] timetable = MOCK_TIMETABLE_MONDAY.get(userId);
        if (slotIndex < 0 || slotIndex >= timetable.length) {
            return "TIMETABLEEDIT|false";
        }
        timetable[slotIndex] = newName;
        MOCK_TIMETABLE_MONDAY.put(userId, timetable);
        return "TIMETABLEEDIT|true";
    }
   private static String handleTimetableEditTuesday(String[] parts) {
        String userId = parts[2];
        int day = Integer.parseInt(parts[3]);
        String slot = parts[4];
        String newName = parts[5];


        if (!MOCK_TIMETABLE_TUESDAY.containsKey(userId)) {
            return "TIMETABLEEDIT|false";
        }

        int slotIndex = Integer.parseInt(slot.split("_")[1]) - 1;

        String[] timetable = MOCK_TIMETABLE_TUESDAY.get(userId);
        if (slotIndex < 0 || slotIndex >= timetable.length) {
            return "TIMETABLEEDIT|false";
        }
        timetable[slotIndex] = newName;
        MOCK_TIMETABLE_TUESDAY.put(userId, timetable);

        return "TIMETABLEEDIT|true";
   }

    private static String handleTimetableEditWednesday(String[] parts) {
        String userId = parts[2];
        int day = Integer.parseInt(parts[3]);
        String slot = parts[4];
        String newName = parts[5];


        if (!MOCK_TIMETABLE_WEDNESDAY.containsKey(userId)) {
            return "TIMETABLEEDIT|false";
        }

        int slotIndex = Integer.parseInt(slot.split("_")[1]) - 1;

        String[] timetable = MOCK_TIMETABLE_WEDNESDAY.get(userId);
        if (slotIndex < 0 || slotIndex >= timetable.length) {
            return "TIMETABLEEDIT|false";
        }
        timetable[slotIndex] = newName;
        MOCK_TIMETABLE_WEDNESDAY.put(userId, timetable);
        return "TIMETABLEEDIT|true";
    }
    private static String handleTimetableEditThursday(String[] parts) {
        String userId = parts[2];
        int day = Integer.parseInt(parts[3]);
        String slot = parts[4];
        String newName = parts[5];


        if (!MOCK_TIMETABLE_THURSDAY.containsKey(userId)) {
            return "TIMETABLEEDIT|false";
        }

        int slotIndex = Integer.parseInt(slot.split("_")[1]) - 1;

        String[] timetable = MOCK_TIMETABLE_THURSDAY.get(userId);
        if (slotIndex < 0 || slotIndex >= timetable.length) {
            return "TIMETABLEEDIT|false";
        }
        timetable[slotIndex] = newName;
        MOCK_TIMETABLE_THURSDAY.put(userId, timetable);
        return "TIMETABLEEDIT|true";
    }

    private static String handleTimetableEditFriday(String[] parts) {
        String userId = parts[2];
        int day = Integer.parseInt(parts[3]);
        String slot = parts[4];
        String newName = parts[5];


        if (!MOCK_TIMETABLE_FRIDAY.containsKey(userId)) {
            return "TIMETABLEEDIT|false";
        }

        int slotIndex = Integer.parseInt(slot.split("_")[1]) - 1;

        String[] timetable = MOCK_TIMETABLE_FRIDAY.get(userId);
        if (slotIndex < 0 || slotIndex >= timetable.length) {
            return "TIMETABLEEDIT|false";
        }
        timetable[slotIndex] = newName;
        MOCK_TIMETABLE_FRIDAY.put(userId, timetable);
        return "TIMETABLEEDIT|true";
    }

    private static String handleTimetableEditSaturday(String[] parts) {
        String userId = parts[2];
        int day = Integer.parseInt(parts[3]);
        String slot = parts[4];
        String newName = parts[5];


        if (!MOCK_TIMETABLE_SATURDAY.containsKey(userId)) {
            return "TIMETABLEEDIT|false";
        }

        int slotIndex = Integer.parseInt(slot.split("_")[1]) - 1;

        String[] timetable = MOCK_TIMETABLE_SATURDAY.get(userId);
        if (slotIndex < 0 || slotIndex >= timetable.length) {
            return "TIMETABLEEDIT|false";
        }
        timetable[slotIndex] = newName;
        MOCK_TIMETABLE_SATURDAY.put(userId, timetable);
        return "TIMETABLEEDIT|true";
    }
}
    
    
    
    
    

