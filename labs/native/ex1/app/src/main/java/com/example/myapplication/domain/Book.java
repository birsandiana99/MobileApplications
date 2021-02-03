package com.example.myapplication.domain;

import java.io.Serializable;

public class Book implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String  landed;
    private String description;
    private String state;
    private String author;

    @Override
    public String toString() {
        return "[" +
                "name='" + name + '\'' +
                ", landed='" + landed + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", author='" + author + '\'' + "]";
    }

    public Book(String name, String landed, String description, String state, String author) {
        this.name = name;
        this.landed = landed;
        this.description = description;
        this.state = state;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanded() {
        return landed;
    }

    public void setLanded(String landed) {
        this.landed = landed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
