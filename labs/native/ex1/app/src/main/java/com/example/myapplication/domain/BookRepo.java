package com.example.myapplication.domain;

public interface BookRepo {

    public interface BookRepository {

        int getBook(Long id);

        boolean addBook(Book b);

        void deleteBook(Book b);

        boolean editBook(String name, String landed, String description, String state, String author);
    }
}
