package entity;

public class User {
    private int id;
    private String userName;
    private String password;
    private String name;
    private int authority;
    private int state;

    public User() {
        super();
    }

    public User(int id, String userName, String password, String name, int authority) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.authority = authority;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
