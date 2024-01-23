package edu.asu.oop2.finalprojects;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BookstoreManagementSystem extends InventorySubject {
  private List<Book> bookCatalog;
    private final FileManager fileManager;

    public BookstoreManagementSystem() {
        this.bookCatalog = new ArrayList<>();
        this.fileManager = FileManager.getInstance();
        loadBooks();
    }

    public void addBook(Book book) {
        bookCatalog.add(book);
        saveBooks();
        notifyObservers();
    }
    public List<Book> getBookCatalog() {
        return bookCatalog;
    }

    public void loadBooks() {
        try {
            List<Book> loadedBooks = fileManager.readBooksFromFile(fileManager.getDefaultFilePath());
            // Initialize if null
            bookCatalog = Objects.requireNonNullElseGet(loadedBooks, ArrayList::new);
        } catch (Exception e) {
            System.err.println("Error reading books from file.");
            e.printStackTrace();
        }
    }
    public void updateInventory(List<Book> updatedCatalog) {
        bookCatalog = updatedCatalog;
        saveBooks();
        notifyObservers();
    }

    public void deleteBook(Book book) {
        bookCatalog.remove(book);
        saveBooks();
        notifyObservers();
    }

    private void saveBooks() {
        fileManager.writeBooksToFile(fileManager.getDefaultFilePath(), bookCatalog);
    }
}