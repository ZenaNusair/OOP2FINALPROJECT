package edu.asu.oop2.finalprojects;

import java.io.Serial;
import java.io.Serializable;

public class NonFictionBook extends Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public NonFictionBook(String title, String author, double price) {
        super(title, author, price);
    }
}