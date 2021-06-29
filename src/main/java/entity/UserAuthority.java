package entity;

public class UserAuthority {
    private int id;
    private String name;

    public UserAuthority() {
        super();
    }

    public UserAuthority(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
