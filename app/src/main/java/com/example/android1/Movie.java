package com.example.android1;

public class Movie {
    private String id;
    private String title;
    private String posterUrl;
    private String status;

    // Firebase bắt buộc phải có constructor rỗng
    public Movie() {}

    public Movie(String id, String title, String posterUrl, String status) {
        this.id = id;
        this.title = title;
        this.posterUrl = posterUrl;
        this.status = status;
    }

    // --- Tạo Getter và Setter cho các biến ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
