package edu.asu.oop2.finalprojects;

import java.io.Serial;
import java.io.Serializable;

public class FictionBook extends Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public FictionBook() {
        super("", "", 0.0);
    }

    public FictionBook(String title, String author, double price) {
        super(title, author, price);
    }
}