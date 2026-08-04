package com.vit.campusconnect.dto.response;

public class CommentResponse {
    private Long id;
    private String author;
    private String role;
    private String text;
    private String time;

    public CommentResponse() {}

    public CommentResponse(Long id, String author, String role, String text, String time) {
        this.id = id;
        this.author = author;
        this.role = role;
        this.text = text;
        this.time = time;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}
