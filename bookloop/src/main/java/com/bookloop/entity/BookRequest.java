package com.bookloop.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * BookRequest Entity
 * Represents a request created by a student who needs a specific book for their coursework.
 */
@Entity
@Table(name = "book_requests")
public class BookRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String author;

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private String semester;

    @Column(length = 1000)
    private String notes;

    @Column(name = "contact_info", nullable = false)
    private String contactInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User requester;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public BookRequest() {
    }

    public BookRequest(String title, String author, String course, String semester,
                       String notes, String contactInfo, User requester) {
        this.title = title;
        this.author = author;
        this.course = course;
        this.semester = semester;
        this.notes = notes;
        this.contactInfo = contactInfo;
        this.requester = requester;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
