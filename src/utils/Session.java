package utils;

public class Session {
    private static Session instance; // Singleton instance
    private String userId;           // Logged-in user ID

    // Private constructor to prevent instantiation from outside
    private Session() {}

    // Get the singleton instance
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // Getter and setter for userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
