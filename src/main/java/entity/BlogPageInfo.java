package entity;

import java.sql.Date;

public class BlogPageInfo {
    private int id;
    private String title;
    private String summary;
    private Date publishDate;

    public BlogPageInfo() {
        super();
    }

    public BlogPageInfo(int id, String title, String summary, Date publishDate) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.publishDate = publishDate;
    }

    public int getId() {
        return id;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
