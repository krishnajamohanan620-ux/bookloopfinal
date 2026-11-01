package com.bookloop.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * BookDto
 * Used for receiving and validating book listing data submitted via the "Sell a Book" form.
 */
public class BookDto {

    @NotBlank(message = "Book title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 150, message = "Author name cannot exceed 150 characters")
    private String author;

    @NotBlank(message = "Course/Department is required")
    private String course;

    @NotBlank(message = "Semester is required")
    private String semester;

    @NotBlank(message = "College name is required")
    private String college;

    @NotNull(message = "Price is required (enter 0 for free/exchange)")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
    private Double price;

    @NotBlank(message = "Book condition is required")
    private String bookCondition;

    @NotBlank(message = "Listing type is required")
    private String listingType;

    @NotBlank(message = "Contact info is required (Phone / WhatsApp / Email)")
    private String sellerContact;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    public BookDto() {
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

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public String getSellerContact() {
        return sellerContact;
    }

    public void setSellerContact(String sellerContact) {
        this.sellerContact = sellerContact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
