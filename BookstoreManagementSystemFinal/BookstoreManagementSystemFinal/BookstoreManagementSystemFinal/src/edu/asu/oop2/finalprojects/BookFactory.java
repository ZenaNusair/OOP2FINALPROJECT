package edu.asu.oop2.finalprojects;

public class BookFactory {
    public static Book createBook(String type, String title, String author, double price) {
        if ("fiction".equalsIgnoreCase(type)) {
            return new FictionBook(title, author, price);
        } else if ("non-fiction".equalsIgnoreCase(type)) {
            return new NonFictionBook(title, author, price);
        }
        return null;
    }
}