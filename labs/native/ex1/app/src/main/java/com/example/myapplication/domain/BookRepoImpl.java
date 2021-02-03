package com.example.myapplication.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookRepoImpl implements Serializable {
    public List<Book> getBooklist() {
        return booklist;
    }

    private List<Book> booklist;

    public BookRepoImpl() {
        booklist = new ArrayList<>();
    }

    public int getBook(Book book){
        int index;
        index = booklist.indexOf(book);
        return index;
    }

    public boolean addBook(Book book){
        if(this.getBook(book) ==-1)
        {
            booklist.add(book);
            return true;
        }
        else return false;
    }

    public int getBookByName(String book){
        int index = -1;
        for (int i = 0; i < booklist.size(); i++) {
            if (booklist.get(i).getName().equals(book)) {
                index = i;
            }
        }
        return index;
    }

    public boolean deleteBook(Book book){
        if(booklist.contains(book)){
            booklist.remove(book);
            return true;
        }
        else{
            return false;
        }

    }
    public boolean deteleBookByName(String book){
        for (int i = 0; i < booklist.size(); i++) {
            if(booklist.get(i).getName().equals(book)){
                booklist.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean editBook(Book book, String name, String landed, String description, String state, String author){
        int index = this.getBook(book);
        if(index != -1){
            this.booklist.get(index).setAuthor(author);
            this.booklist.get(index).setDescription(description);
            this.booklist.get(index).setLanded(landed);
            this.booklist.get(index).setState(state);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String s="";
        for (int i = 0; i < booklist.size(); i++) {
            s+=booklist.get(i).toString() + ",";
        }
        return s;
    }
}
