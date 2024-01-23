package edu.asu.oop2.finalprojects;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookParser {
    public static List<Book> parseBooks(BufferedReader reader) throws IOException {
        List<Book> books = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                String title = parts[0].trim();
                String author = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                books.add(new Book(title, author, price));
            }
        }
        return books;
    }
}
