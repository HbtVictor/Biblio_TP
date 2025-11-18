package repository;

import model.Book;
import util.DataStore;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Pattern : Repository (implémentation)
 * Utilise le Singleton DataStore pour accéder aux données
 */
public class BookRepositoryImpl implements BookRepository {

    private final DataStore dataStore;

    public BookRepositoryImpl() {
        // Récupère l'instance unique du DataStore (Pattern Singleton)
        this.dataStore = DataStore.getInstance();
    }

    @Override
    public void save(Book book) {
        // Vérifie que le livre n'existe pas déjà
        if (findByIsbn(book.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("Un livre avec cet ISBN existe déjà : " + book.getIsbn());
        }
        dataStore.getBooks().add(book);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return dataStore.getBooks().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();
    }

    @Override
    public List<Book> findAll() {
        return dataStore.getBooks();
    }

    @Override
    public List<Book> findByTitleContaining(String keyword) {
        return dataStore.getBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return dataStore.getBooks().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllAvailable() {
        return dataStore.getBooks().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteByIsbn(String isbn) {
        return dataStore.getBooks().removeIf(book -> book.getIsbn().equals(isbn));
    }

    @Override
    public void update(Book book) {
        Optional<Book> existingBook = findByIsbn(book.getIsbn());
        if (existingBook.isEmpty()) {
            throw new IllegalArgumentException("Livre introuvable : " + book.getIsbn());
        }
        // Supprime l'ancien et ajoute le nouveau
        deleteByIsbn(book.getIsbn());
        dataStore.getBooks().add(book);
    }
}