package edu.asu.oop2.finalprojects;

import java.io.*;
import java.util.List;

public class FileManager {
    private static final FileManager instance = new FileManager();
    private static final String DEFAULT_FILE_PATH = "C:\\Users\\User\\Desktop\\BOOK.txt";

    private FileManager() {
    }

    public String getDefaultFilePath() {
        return DEFAULT_FILE_PATH;
    }

    public static FileManager getInstance() {
        return instance;
    }

    public List<Book> readBooksFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            List<Book> books = BookParser.parseBooks(reader);
            return books;
        } catch (FileNotFoundException e) {
        }
        return null;
    }


    public void writeBooksToFile(String filename, List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books) {
                writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
