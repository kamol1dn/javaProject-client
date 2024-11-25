package network;
import java.util.*;
import java.io.*;
import java.net.Socket;


public class ClientConnection {

    private static final String SERVER_HOST = "192.168.194.246"; // Replace with your Serveo public URL
    private static final int SERVER_PORT = 8080; // Serveo forwards traffic over port 80

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
        MOCK_TIMETABLE.put("student1", new String[]{"Math", "Physics", "Chemistry", "Biology"});
        MOCK_TIMETABLE.put("student2", new String[]{"History", "Economics", "Programming", "Art"});
    }

    /**
     * Simulates server communication.
     *
     * @param request The request command to be sent to the server.
     * @return The mocked response in the format COMMAND|true or COMMAND|false.
     */
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

        // Split the request into parts based on the '|' delimiter
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
            case "RETURNBOOK":
                return handleReturnBook(parts);
            case "STUDENTDETAIL":
                return handleDetailStudent(parts);
            case "VIEWALLBOOKS":
                return handleBookList(parts);
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

        // Check credentials
        if (MOCK_USER_DB.containsKey(userId) && MOCK_USER_DB.get(userId)[0].equals(password)) {
            // Retrieve the user's name
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

        String oldTaskName = parts[1]; // The existing task name to edit
        String newTaskName = parts[2]; // The new task description
        String newDeadline = parts[3]; // The new deadline

        // Locate the task in the list
        boolean taskFound = false;
        for (String[] task : MOCK_TASKLIST) {
            String studentId = task[0];
            String currentTaskName = task[1];
            String currentDeadline = task[2];

            // Check if this is the task to edit
            if (currentTaskName.equals(oldTaskName)) {
                // Validation checks
                if (currentTaskName.equals(newTaskName) && currentDeadline.equals(newDeadline)) {
                    return "TASKEDIT|false"; // No changes made
                }

                // Update task name and deadline
                task[1] = newTaskName;
                task[2] = newDeadline;

                taskFound = true;
                break; // Task found and updated, exit loop
            }
        }

        // If task was not found, return failure
        return taskFound ? "TASKEDIT|true" : "TASKEDIT|false";
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

        // Check if a task with the same name already exists for the user
        for (String[] task : MOCK_TASKLIST) {
            if (task[0].equals(userId) && task[1].equals(taskName)) {
                return "TASKADDNEW|false"; // Task with the same name already exists for the user
            }
        }

        // Add the new task to the task list
        MOCK_TASKLIST.add(new String[]{userId, taskName, taskDeadline});
        return "TASKADDNEW|true"; // Success
    }

    private static String handleAddBook(String[] parts) {
        if (parts.length != 3) { // ADDNEWBOOK requires 3 parts: command, bookname, author
            return "ADDNEWBOOK|false"; // Invalid format
        }

        String bookName = parts[1];
        String author = parts[2];

        // Check if the book already exists
        for (String[] book : MOCK_BOOKLIST) {
            if (book[0].equalsIgnoreCase(bookName) && book[1].equalsIgnoreCase(author)) {
                return "ADDNEWBOOK|false"; // Book already exists
            }
        }

        // Add the new book to the book list
        MOCK_BOOKLIST.add(new String[]{bookName, author});
        return "ADDNEWBOOK|true"; // Success
    }

    private static String handleBookList(String[] parts) {
        if (parts.length != 1) {
            return "BOOKLIST|false";
        }

        StringBuilder response = new StringBuilder("BOOKLIST");
        for (String[] book : MOCK_BOOKLIST) {
            response.append("|").append(book[0]).append(",").append(book[1]);
        }
        return response.toString();
    }

    private static String handleAssignBook(String[] parts) {
        if (parts.length != 3) {
            return "ASSIGN_BOOK|false"; // Invalid format
        }

        String userId = parts[1];
        String bookName = parts[2];

        MOCK_BOOK_USER.add(new String[]{userId, bookName});
        return "ASSIGN_BOOK|true";
    }

    private static String handleReturnBook(String[] parts) {
        if (parts.length != 3) {
            return "RETURN_BOOK|false"; // Invalid format
        }

        String userId = parts[1];
        String bookName = parts[2];

        MOCK_BOOK_USER.removeIf(entry -> entry[0].equals(userId) && entry[1].equals(bookName));
        return "RETURN_BOOK|true";
    }

    private static String handleDetailStudent(String[] parts) {
        if (parts.length != 2) { // STUDENTDETAIL requires 2 parts: command and userid
            return "STUDENTDETAIL|false"; // Invalid format
        }

        String userId = parts[1];

        // Check if the user exists in the mock database
        if (!MOCK_USER_DB.containsKey(userId)) {
            return "STUDENTDETAIL|false"; // User not found
        }

        // Retrieve the user details
        String[] userDetails = MOCK_USER_DB.get(userId);
        String password = userDetails[0];
        String name = userDetails[1];

        // Return the formatted response
        return String.format("STUDENTDETAIL|%s|%s|%s", name, userId, password);
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
        if (parts.length != 4) { // TIMETABLEEDIT requires 4 parts: command, userid, slot_N, newname
            return "TIMETABLEEDIT|false"; // Invalid format
        }

        String userId = parts[1];
        String slot = parts[2];
        String newName = parts[3];

        // Validate slot number (should be between slot_1 and slot_4)
        if (!slot.matches("slot_[1-4]")) {
            return "TIMETABLEEDIT|false"; // Invalid slot number
        }

        // Validate new name (ensure it's not empty)
        if (newName == null || newName.trim().isEmpty()) {
            return "TIMETABLEEDIT|false"; // Invalid new name
        }

        // Simulate a success response (no database changes)
        return "TIMETABLEEDIT|true";
    }

    private static String handleTimetableView(String[] parts) {
        if (parts.length != 2) {
            return "TIMETABLE|false";
        }

        String userId = parts[1];
        if (!MOCK_TIMETABLE.containsKey(userId)) {
            return "TIMETABLE|false";
        }

        StringBuilder response = new StringBuilder("TIMETABLE");
        for (String slot : MOCK_TIMETABLE.get(userId)) {
            response.append("|").append(slot);
        }
        return response.toString();


    }

    private static String handleUserEditName(String[] parts) {
        if (parts.length != 3) { // USEREDITNAME requires 3 parts: command, userid, newname
            return "USEREDITNAME|false"; // Invalid format
        }

        String userId = parts[1];
        String newName = parts[2];

        // Check if the user exists in the mock database
        if (!MOCK_USER_DB.containsKey(userId)) {
            return "USEREDITNAME|false"; // User not found
        }

        // Update the user's name
        MOCK_USER_DB.get(userId)[1] = newName;

        return "USEREDITNAME|true"; // Success
    }

    private static String handleUserEditPassword(String[] parts) {
        if (parts.length != 3) { // USEREDITPASSWORD requires 3 parts: command, userid, newpassword
            return "USEREDITPASSWORD|false"; // Invalid format
        }

        String userId = parts[1];
        String newPassword = parts[2];

        // Check if the user exists in the mock database
        if (!MOCK_USER_DB.containsKey(userId)) {
            return "USEREDITPASSWORD|false"; // User not found
        }

        // Update the user's password
        MOCK_USER_DB.get(userId)[0] = newPassword;

        return "USEREDITPASSWORD|true"; // Success
    }


}
