package network;
import java.util.HashMap;
import java.util.Map;

public class ClientConnection {
    // Mock database for testing
    private static final Map<String, String> MOCK_USER_DB = new HashMap<>();
    private static final Map<Integer, String> MOCK_TASKS = new HashMap<>();

    static {
        MOCK_USER_DB.put("student1", "password123");
        MOCK_USER_DB.put("student2", "mypassword");
        MOCK_TASKS.put(1, "Finish Assignment");
        MOCK_TASKS.put(2, "Prepare for Exam");
    }

    /**
     * Simulates server communication.
     *
     * @param request The request command to be sent to the server.
     * @return The mocked response.
     */
    public static String sendRequest(String request) {
        System.out.println("Mock request sent: " + request);

        String[] parts = request.split(" ", 2); // Split command and arguments
        String command = parts[0];
        String args = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case "LOGIN":
                String[] loginArgs = args.split(" ");
                return mockLogin(loginArgs[0], loginArgs[1]);

            case "REGISTER":
                String[] registerArgs = args.split(" ");
                return mockRegister(registerArgs[0], registerArgs[1]);

            case "TASKS_LIST":
                return mockTasksList();

            case "TASKS_CREATE":
                return mockTasksCreate(args);

            case "SETTINGS_EDIT_NAME":
                return "EDIT_NAME_SUCCESS";

            case "SETTINGS_EDIT_ID":
                return "EDIT_ID_SUCCESS";

            case "SETTINGS_EDIT_PASSWORD":
                return "EDIT_PASSWORD_SUCCESS";

            case "SETTINGS_EDIT_BIRTHDAY":
                return "EDIT_BIRTHDAY_SUCCESS";

            case "STUDENT_INFO":
                return mockStudentInfo();

            case "STUDENT_TIMETABLE":
                return mockStudentTimetable();

            case "LIBRARY_LIST":
                return mockLibraryList();

            case "LIBRARY_BORROW":
                return mockLibraryBorrow(args);

            case "LIBRARY_RETURN":
                return mockLibraryReturn(args);

            case "LIBRARY_ADD":
                return mockLibraryAdd(args);

            case "TASKS_DELETE_ALL":
                return mockTasksDeleteAll();

            default:
                return "UNKNOWN_COMMAND";
        }
    }

    private static String mockLogin(String userId, String password) {
        if (MOCK_USER_DB.containsKey(userId) && MOCK_USER_DB.get(userId).equals(password)) {
            return "LOGIN_SUCCESS";
        }
        return "LOGIN_FAILED";
    }

    private static String mockRegister(String userId, String password) {
        if (MOCK_USER_DB.containsKey(userId)) {
            return "REGISTER_FAILED: User already exists";
        }
        MOCK_USER_DB.put(userId, password);
        return "REGISTER_SUCCESS";
    }

    private static String mockTasksList() {
        StringBuilder response = new StringBuilder("Tasks:\n");
        MOCK_TASKS.forEach((id, task) -> response.append(id).append(": ").append(task).append("\n"));
        return response.toString();
    }

    private static String mockTasksCreate(String description) {
        int newId = MOCK_TASKS.size() + 1;
        MOCK_TASKS.put(newId, description);
        return "TASKS_CREATE_SUCCESS: Task ID " + newId;
    }

    private static String mockTasksDeleteAll() {
        MOCK_TASKS.clear();
        return "TASKS_DELETE_SUCCESS";
    }

    private static String mockStudentInfo() {
        return "Name: John Doe\nID: 12345\nFaculty: Computer Science\nBirthday: 01.01.2000";
    }

    private static String mockStudentTimetable() {
        return """
               Monday:
               9:00 AM - Data Structures
               11:00 AM - Algorithms

               Tuesday:
               10:00 AM - Operating Systems
               1:00 PM - Databases
               """;
    }

    private static String mockLibraryList() {
        return "1: The Catcher in the Rye\n2: 1984\n3: To Kill a Mockingbird";
    }

    private static String mockLibraryBorrow(String bookName) {
        return "BORROW_SUCCESS: " + bookName;
    }

    private static String mockLibraryReturn(String bookId) {
        return "RETURN_SUCCESS: Book ID " + bookId;
    }

    private static String mockLibraryAdd(String args) {
        return "ADD_SUCCESS: " + args;
    }
}
