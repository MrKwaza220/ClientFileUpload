package com.enviro.assessment.grad001.sakhumzikwaza.clientfileupload;

import jakarta.persistence.*;

import java.util.Date;

// Entity annotation to mark this class as a JPA entity
@Entity
// Table annotation to specify the name of the corresponding database table
@Table(name = "client_files")
public class Document {
    // Id annotation to specify the primary key of the entity
    @Id
    // GeneratedValue annotation to specify the strategy for generating the primary key value
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    // Column annotation to specify the mapping of the entity attribute to the database column
    @Column(length = 512, nullable = false, unique = true)
    private String name;

    // Attribute to store the size of the document
    private Long size;

    // Column annotation to specify the mapping of the entity attribute to the database column
    @Column(name = "upload_time")
    private Date uploadTime;

    // Attribute to store the content of the document as a byte array
    private byte[] content;

    // Constructor with parameters to initialize the id, name, and size of the document
    public Document(Long id, String name, long size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    // Default constructor
    public Document(){

    }

    // Getter and setter methods for the id attribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and setter methods for the name attribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter methods for the size attribute
    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    // Getter and setter methods for the uploadTime attribute
    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    // Getter and setter methods for the content attribute
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
