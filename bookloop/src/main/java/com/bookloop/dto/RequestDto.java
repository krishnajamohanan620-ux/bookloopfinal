package com.bookloop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * RequestDto
 * Form object for student book requests ("Need a Book").
 */
public class RequestDto {

    @NotBlank(message = "Book title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @Size(max = 150, message = "Author name cannot exceed 150 characters")
    private String author;

    @NotBlank(message = "Course is required")
    private String course;

    @NotBlank(message = "Semester is required")
    private String semester;

    @NotBlank(message = "Contact information is required (Phone or Email)")
    private String contactInfo;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    public RequestDto() {
    }

    // Getters and Setters
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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
