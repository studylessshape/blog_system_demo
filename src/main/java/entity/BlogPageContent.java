package entity;

import java.sql.Date;

public class BlogPageContent {
    private int blog_id;
    private String title;
    private String summary;
    private Date date;
    private String content;

    public BlogPageContent() {
        super();
    }

    public BlogPageContent(int blog_id, String title, String summary, Date date, String content) {
        this.blog_id = blog_id;
        this.title = title;
        this.summary = summary;
        this.date = date;
        this.content = content;
    }

    public int getBlog_id() {
        return blog_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
