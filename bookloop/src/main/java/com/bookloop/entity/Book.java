package com.bookloop.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Book Entity
 * Represents an academic book listed for sale, rental, or exchange by a student.
 */
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private String semester;

    @Column(nullable = false)
    private String college;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String bookCondition; // e.g. "Brand New", "Like New", "Good", "Fair"

    @Column(name = "listing_type", nullable = false)
    private String listingType; // "Sale", "Rental", "Exchange"

    @Column(name = "seller_contact", nullable = false)
    private String sellerContact;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User seller;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Book() {
    }

    public Book(String title, String author, String course, String semester,
                String college, Double price, String bookCondition,
                String listingType, String sellerContact, String description, User seller) {
        this.title = title;
        this.author = author;
        this.course = course;
        this.semester = semester;
        this.college = college;
        this.price = price;
        this.bookCondition = bookCondition;
        this.listingType = listingType;
        this.sellerContact = sellerContact;
        this.description = description;
        this.seller = seller;
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

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
