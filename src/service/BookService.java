package service;

import dto.BookDTO;
import model.Book;
import repository.BookRepository;
import repository.BookRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Pattern : Service Layer
 * Pourquoi : Contient la logique métier liée aux livres.
 * Orchestre les appels aux repositories et transforme les entités en DTOs.
 * Lien : https://www.baeldung.com/java-enterprise-design-patterns#service-layer
 */
public class BookService {

    private final BookRepository bookRepository;

    public BookService() {
        this.bookRepository = new BookRepositoryImpl();
    }

    /**
     * Ajoute un nouveau livre dans la bibliothèque
     */
    public void addBook(String isbn, String title, String author, String publisher, int year) {
        // Validation métier
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("L'ISBN ne peut pas être vide");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("L'auteur ne peut pas être vide");
        }

        // Création du livre avec le Builder Pattern
        Book book = new Book.Builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .publisher(publisher)
                .year(year)
                .build();

        bookRepository.save(book);
    }

    /**
     * Récupère tous les livres sous forme de DTOs
     */
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère uniquement les livres disponibles
     */
    public List<BookDTO> getAvailableBooks() {
        return bookRepository.findAllAvailable().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recherche des livres par titre
     */
    public List<BookDTO> searchBooksByTitle(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot-clé ne peut pas être vide");
        }
        return bookRepository.findByTitleContaining(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recherche des livres par auteur
     */
    public List<BookDTO> searchBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("L'auteur ne peut pas être vide");
        }
        return bookRepository.findByAuthor(author).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un livre par ISBN (retourne DTO)
     */
    public BookDTO getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(this::convertToDTO)
                .orElse(null);
    }

    /**
     * Vérifie si un livre est disponible
     */
    public boolean isBookAvailable(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(Book::isAvailable)
                .orElse(false);
    }

    /**
     * Marque un livre comme emprunté
     */
    public void markAsUnavailable(String isbn) {
        bookRepository.findByIsbn(isbn).ifPresent(book -> {
            book.setAvailable(false);
            bookRepository.update(book);
        });
    }

    /**
     * Marque un livre comme disponible
     */
    public void markAsAvailable(String isbn) {
        bookRepository.findByIsbn(isbn).ifPresent(book -> {
            book.setAvailable(true);
            bookRepository.update(book);
        });
    }

    /**
     * Conversion d'une entité Book en BookDTO
     * Pattern : DTO - On ne renvoie jamais l'entité directement
     */
    private BookDTO convertToDTO(Book book) {
        String status = book.isAvailable() ? "Disponible" : "Emprunté";
        return new BookDTO(
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getYear(),
                status
        );
    }
}