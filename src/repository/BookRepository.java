package repository;

import model.Book;
import java.util.List;
import java.util.Optional;

/**
 * Pattern : Repository
 * Pourquoi : Abstraction de la couche d'accès aux données.
 * Sépare la logique métier de la persistance des données.
 * Lien : https://javaetmoi.com/2013/03/17/le-pattern-repository/
 */
public interface BookRepository {

    /**
     * Ajoute un livre dans le repository
     */
    void save(Book book);

    /**
     * Trouve un livre par son ISBN
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Récupère tous les livres
     */
    List<Book> findAll();

    /**
     * Recherche des livres par titre (contient le mot-clé)
     */
    List<Book> findByTitleContaining(String keyword);

    /**
     * Recherche des livres par auteur
     */
    List<Book> findByAuthor(String author);

    /**
     * Récupère uniquement les livres disponibles
     */
    List<Book> findAllAvailable();

    /**
     * Supprime un livre par ISBN
     */
    boolean deleteByIsbn(String isbn);

    /**
     * Met à jour un livre existant
     */
    void update(Book book);
}