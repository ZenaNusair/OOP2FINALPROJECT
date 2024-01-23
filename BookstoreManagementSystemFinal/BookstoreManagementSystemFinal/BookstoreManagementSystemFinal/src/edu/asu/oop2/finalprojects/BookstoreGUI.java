
package edu.asu.oop2.finalprojects;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookstoreGUI extends JPanel implements InventoryObserver {

    private final DefaultListModel<Book> bookListModel;
    private final JList<Book> bookList;
    private final List<Book> books;
    private final JTextField searchField;

    public BookstoreGUI() {
        books = new ArrayList<>();

        setLayout(new BorderLayout());

        bookListModel = new DefaultListModel<>();
        bookList = new JList<>(bookListModel);
        JScrollPane scrollPane = new JScrollPane(bookList);

        JButton addButton1 = new JButton("Add Book");
        JButton viewButton = new JButton("View Catalog");
        JButton updateButton = new JButton("Update Inventory");
        JButton deleteButton = new JButton("Delete Book");

        searchField = new JTextField(10);
        JButton searchButton = new JButton("Search");


        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        searchPanel.add(new JLabel("Search by Title:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);


        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        buttonPanel.add(addButton1);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);


        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);
        add(searchPanel, BorderLayout.SOUTH);

        addButton1.addActionListener(e -> {
            JTextField titleField = new JTextField();
            JTextField authorField = new JTextField();
            JTextField priceField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(3, 2));
            inputPanel.add(new JLabel("Title:"));
            inputPanel.add(titleField);
            inputPanel.add(new JLabel("Author:"));
            inputPanel.add(authorField);
            inputPanel.add(new JLabel("Price:"));
            inputPanel.add(priceField);

            int result = JOptionPane.showConfirmDialog(
                    BookstoreGUI.this, inputPanel,
                    "Enter book details", JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                String title = titleField.getText();
                String author = authorField.getText();
                double price = Double.parseDouble(priceField.getText());

                if (!title.isEmpty() && !author.isEmpty()) {
                    Book newBook = new Book(title, author, price);
                    books.add(newBook);
                    notifyInventoryChanged();


                    BookstoreManagementSystem managementSystem = new BookstoreManagementSystem();
                    managementSystem.addBook(newBook);
                } else {
                    JOptionPane.showMessageDialog(BookstoreGUI.this, "Please enter valid title and author.");
                }
            }
        });

        viewButton.addActionListener(e -> {
            List<Book> catalog = loadBooksFromCatalogFile();
            if (catalog != null && !catalog.isEmpty()) {
                StringBuilder catalogMessage = new StringBuilder("Book Catalog:\n");
                for (Book book : catalog) {
                    catalogMessage.append(book).append("\n");
                }
                JOptionPane.showMessageDialog(BookstoreGUI.this, catalogMessage.toString());
            } else {
                JOptionPane.showMessageDialog(BookstoreGUI.this, "No books in the catalog.");
            }
        });


        updateButton.addActionListener(e -> {

            List<Book> catalog = loadBooksFromCatalogFile();
            if (catalog != null) {

                Book selectedBook = (Book) JOptionPane.showInputDialog(
                        BookstoreGUI.this,
                        "Select a book to update:",
                        "Update Inventory",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        catalog.toArray(),
                        null);

                if (selectedBook != null) {

                    JTextField titleField = new JTextField(selectedBook.getTitle());
                    JTextField authorField = new JTextField(selectedBook.getAuthor());
                    JTextField priceField = new JTextField(String.valueOf(selectedBook.getPrice()));

                    JPanel inputPanel = new JPanel(new GridLayout(3, 2));
                    inputPanel.add(new JLabel("Title:"));
                    inputPanel.add(titleField);
                    inputPanel.add(new JLabel("Author:"));
                    inputPanel.add(authorField);
                    inputPanel.add(new JLabel("Price:"));
                    inputPanel.add(priceField);

                    int result = JOptionPane.showConfirmDialog(
                            BookstoreGUI.this, inputPanel,
                            "Update book details", JOptionPane.OK_CANCEL_OPTION
                    );

                    if (result == JOptionPane.OK_OPTION) {

                        selectedBook.setTitle(titleField.getText());
                        selectedBook.setAuthor(authorField.getText());
                        selectedBook.setPrice(Double.parseDouble(priceField.getText()));


                        BookstoreManagementSystem managementSystem = new BookstoreManagementSystem();
                        managementSystem.updateInventory(catalog);


                        notifyInventoryChanged();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(BookstoreGUI.this, "Error loading books from file.");
            }
        });


        deleteButton.addActionListener(e -> {

            List<Book> catalog = loadBooksFromCatalogFile();
            if (catalog != null) {

                Book selectedBook = (Book) JOptionPane.showInputDialog(
                        BookstoreGUI.this,
                        "Select a book to delete:",
                        "Delete Book",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        catalog.toArray(),
                        null);

                if (selectedBook != null) {

                    int confirmResult = JOptionPane.showConfirmDialog(
                            BookstoreGUI.this,
                            "Are you sure you want to delete the selected book?",
                            "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmResult == JOptionPane.YES_OPTION) {
                        catalog.remove(selectedBook);
                        BookstoreManagementSystem managementSystem = new BookstoreManagementSystem();
                        managementSystem.updateInventory(catalog);

                        notifyInventoryChanged();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(BookstoreGUI.this, "Error loading books from file.");
            }
        });


        searchButton.addActionListener(e -> {
            String searchTitle = searchField.getText();
            if (searchTitle != null && !searchTitle.isEmpty()) {
                List<Book> searchResults = new ArrayList<>();
                for (Book book : books) {
                    if (book.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) {
                        searchResults.add(book);
                    }
                }
                displaySearchResults(searchResults);
            }
        });

        updateBookList();
    }


    private void displaySearchResults(List<Book> searchResults) {
        if (!searchResults.isEmpty()) {
            StringBuilder resultMessage = new StringBuilder("Search Results:\n");
            for (Book book : searchResults) {
                resultMessage.append(book).append("\n");
            }
            JOptionPane.showMessageDialog(BookstoreGUI.this, resultMessage.toString());
        } else {
            JOptionPane.showMessageDialog(BookstoreGUI.this, "No matching results found.");
        }
    }


    private void updateBookList() {
        bookListModel.clear();
        for (Book book : books) {
            bookListModel.addElement(book);
        }
    }

    private void notifyInventoryChanged() {
        update();
    }
    private List<Book> loadBooksFromCatalogFile() {
        try {
            return FileManager.getInstance().readBooksFromFile(FileManager.getInstance().getDefaultFilePath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void update() {
        updateBookList();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bookstore Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new BookstoreGUI());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
    }

}
