package entity;

public class Comment {
    private int id;
    private int blogId;
    private int userId;
    private String content;

    public Comment() {
    }

    public Comment(int id, int blogId, int userId, String content) {
        this.id = id;
        this.blogId = blogId;
        this.userId = userId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
