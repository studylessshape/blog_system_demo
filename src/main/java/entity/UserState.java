package entity;

public class UserState {
    private int userId;
    private int state;

    public UserState() {
    }

    public UserState(int userId, int state) {
        this.userId = userId;
        this.state = state;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
