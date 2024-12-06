package network;
import java.io.*;
import java.net.Socket;

import static network.MockFunctions.*;

public class ClientConnection {

    private static final String SERVER_HOST = "192.168.19.203";
    private static final int SERVER_PORT = 8080;

    //... for debugging with mock server (0) or using actual server (1)
    private static final int testVar = 0;

    public static String sendRequest(String request) {
        if (testVar == 0) {
            return handleMockRequest(request);
        } else {
            return handleServerRequest(request);
        }
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

        String[] parts = request.split("\\|");
        String command = parts[0]; // First part is the command

        switch (command) {
            case "LOGIN" -> {
                return handleLogin(parts);
            }
            case "REGISTER" -> {
                return handleRegister(parts);
            }
            case "TASKLIST" -> {
                return handleTaskList(parts);
            }
            case "TASKEDIT" -> {
                return handleTaskEdit(parts);
            }
            case "TASKALLDELETE" -> {
                return handleTaskDeleteAll(parts);
            }
            case "TASKSADDNEW" -> {
                return handleTaskAddNew(parts);
            }
            case "ADDNEWBOOK" -> {
                return handleAddBook(parts);
            }
            case "ASSIGNBOOK" -> {
                return handleAssignBook(parts);
            }
            case "BOOKRETURN" -> {
                return handleReturnBook(parts);
            }
            case "STUDENTDETAIL" -> {
                return handleDetailStudent(parts);
            }
            case "VIEWALLBOOKS" -> {
                return handleViewAllBooks(parts);
            }
            case "USERBOOKS" -> {
                return handleUserBooks(parts);
            }
            case "TIMETABLEEDIT" -> {
                return handleTimetableEdit(parts);
            }
            case "VIEWTIMETABLE" -> {
                return handleTimetableView(parts);
            }
            case "USEREDITNAME" -> {
                return handleUserEditName(parts);
            }
            case "USEREDITPASSWORD" -> {
                return handleUserEditPassword(parts);
            }
            default -> {
                return command + "|false"; // Unknown command
            }
        }
    }
}

