package network;
import java.util.*;
import java.io.*;
import java.net.Socket;


public class ClientConnection {

    private static final String SERVER_HOST = "192.168.17.116";
    private static final int SERVER_PORT = 8080;

    private static final int testVar = 0;

    public static String sendRequest(String request) {
        if (testVar == 0) {
            // Use mock database
            return handleMockRequest(request);
        } else {
            // Use actual server connection
            return handleServerRequest(request);
        }
    }

    // Mock tables
    private static final Map<String, String[]> MOCK_USER_DB = new HashMap<>(); // user_id -> {password, name}
    private static final List<String[]> MOCK_BOOKLIST = new ArrayList<>(); // {title, author}
    private static final List<String[]> MOCK_BOOK_USER = new ArrayList<>(); // {user_id, book_name}
    private static final List<String[]> MOCK_TASKLIST = new ArrayList<>(); // {user_id, task_name, task_deadline}
    private static final Map<String, String[]> MOCK_TIMETABLE = new HashMap<>(); // user_id -> {slot1, slot2, slot3, slot4}

    static {
        // Pre-fill the mock database with sample data

        // Users
        MOCK_USER_DB.put("U1", new String[]{"123", "John Doe"});
        MOCK_USER_DB.put("student2", new String[]{"mypassword", "Jane Smith"});

        // Books
        MOCK_BOOKLIST.add(new String[]{"1984", "George Orwell"});
        MOCK_BOOKLIST.add(new String[]{"To Kill a Mockingbird", "Harper Lee"});
        MOCK_BOOKLIST.add(new String[]{"Dune", "Frank Herbert"});

        // Book-User mapping
        MOCK_BOOK_USER.add(new String[]{"U1", "1984"});
        MOCK_BOOK_USER.add(new String[]{"student2", "Dune"});

        // Tasks
        MOCK_TASKLIST.add(new String[]{"U1", "Complete Assignment", "22.23.2311"});
        MOCK_TASKLIST.add(new String[]{"U1", "Dune", "22.23.2311"});
        MOCK_TASKLIST.add(new String[]{"student1", "Prepare for Exam", "22.23.2311"});
        MOCK_TASKLIST.add(new String[]{"student2", "Finish Project", "22.23.2311"});

        // Timetable
        MOCK_TIMETABLE.put("U1", new String[]{"Math;9:00", "Physics;12:00", "Chemistry;13:00",
                "Biology;16:00"});
        MOCK_TIMETABLE.put("student2", new String[]{"History", "Economics", "Programming", "Art"});
    }


    public static String handleServerRequest(String request) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(request); // Send the request to the server
            return in.readLine(); // Read the server's response
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR|Unable to connect to server";
        }
    }

    public static String handleMockRequest(String request) {
        System.out.println("Mock request sent: " + request);
        String[] parts = request.split("\\|");
        String command = parts[0]; // First part is the command

        switch (command) {
            case "LOGIN":
                return handleLogin(parts);
            case "REGISTER":
                return handleRegister(parts);
            case "TASKLIST":
                return handleTaskList(parts);
            case "TASKEDIT":
                return handleTaskEdit(parts);
            case "TASKALLDELETE":
                return handleTaskDeleteAll(parts);
            case "TASKSADDNEW":
                return handleTaskAddNew(parts);
            case "ADDNEWBOOK":
                return handleAddBook(parts);
            case "ASSIGNBOOK":
                return handleAssignBook(parts);
            case "BOOKRETURN":
                return handleReturnBook(parts);
            case "STUDENTDETAIL":
                return handleDetailStudent(parts);
            case "VIEWALLBOOKS": // view all books
                return handleViewAllBooks(parts);
            case "USERBOOKS": //viev user's all books
                return handleUserBooks(parts);
            case "TIMETABLEEDIT":
                return handleTimetableEdit(parts);
            case "VIEWTIMETABLE":
                return handleTimetableView(parts);
            case "USEREDITNAME":
                return handleUserEditName(parts);
            case "USEREDITPASSWORD":
                return handleUserEditPassword(parts);

            default:
                return command + "|false"; // Unknown command
        }
    }


    // ========================== Handlers for Commands ==========================

    private static String handleLogin(String[] parts) {
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

    private static String handleRegister(String[] parts) {
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

    private static String handleTaskEdit(String[] parts) {
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

    private static String handleUserBooks(String[] parts) {
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

    private static String handleTaskDeleteAll(String[] parts) {
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

    private static String handleTaskAddNew(String[] parts) {
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

    private static String handleAddBook(String[] parts) {
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

    private static String handleViewAllBooks(String[] parts) {
        if (parts.length != 2) { // VIEWALLBOOKS requires 1 part: command
            return "VIEWALLBOOKS|false"; // Invalid format
        }

        StringBuilder response = new StringBuilder("VIEWALLBOOKS|true");
        for (String[] book : MOCK_BOOKLIST) {
            response.append("|").append(book[0]).append(",").append(book[1]); // Format: title,author
        }
        return response.toString(); // Returns list of all books
    }

    private static String handleAssignBook(String[] parts) {
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

    private static String handleReturnBook(String[] parts) {
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

    private static String handleDetailStudent(String[] parts) {
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

    private static String handleTaskList(String[] parts) {
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

    private static String handleTimetableEdit(String[] parts) {
        if (parts.length != 4) {
            return "TIMETABLEEDIT|false";
        }

        String userId = parts[1];
        String slot = parts[2];
        String newName = parts[3];

        if (!slot.matches("slot_[1-4]")) {
            return "TIMETABLEEDIT|false"; // Invalid slot number
        }

        if (newName == null || newName.trim().isEmpty()) {
            return "TIMETABLEEDIT|false";
        }

        if (!MOCK_TIMETABLE.containsKey(userId)) {
            return "TIMETABLEEDIT|false";
        }

        int slotIndex = Integer.parseInt(slot.split("_")[1]) - 1;

        String[] timetable = MOCK_TIMETABLE.get(userId);
        if (slotIndex < 0 || slotIndex >= timetable.length) {
            return "TIMETABLEEDIT|false";
        }
        timetable[slotIndex] = newName;
        MOCK_TIMETABLE.put(userId, timetable);
        return "TIMETABLEEDIT|true";
    }

    private static String handleUserEditName(String[] parts) {
        if (parts.length != 3) {
            return "USEREDITNAME|false";
        }
        String userId = parts[1];
        String newName = parts[2];
        if (!MOCK_USER_DB.containsKey(userId)) {
            return "USEREDITNAME|false";
        }
        MOCK_USER_DB.get(userId)[1] = newName;
        return "USEREDITNAME|true";
    }

    private static String handleUserEditPassword (String[]parts){
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

    private static String handleTimetableView(String[] parts) {
        // Validate input length
        if (parts.length != 2) {
            return "TIMETABLE|false";
        }

        // Extract userId and trim whitespace
        String userId = parts[1].trim();

        // Check if user exists in MOCK_TIMETABLE
        if (!MOCK_TIMETABLE.containsKey(userId)) {
            return "TIMETABLE|false";
        }

        // Build the response string
        StringBuilder response = new StringBuilder("TIMETABLE|true");
        for (String slot : MOCK_TIMETABLE.get(userId)) {
            response.append("|").append(slot);
        }

        return response.toString();
    }


}