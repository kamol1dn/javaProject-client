
///// this class remembers user id for the session and assigns it to user object


package utils;

public class Session {
    private static Session instance;
    private String userId;
    private String ipAddress = "192.168.16.59";
    private int status=0;

    private Session() {}  //abstract constructor to prevent another initialization

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }
// getters and setters
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) {this.ipAddress = ipAddress;}

    public int getStatus() {return status;}
    public void setStatus(int status) {this.status = status;}

}
