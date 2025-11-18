package util;

import model.Book;
import model.Loan;
import model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Pattern : Singleton
 * Pourquoi : Garantit une seule instance pour stocker toutes les données en mémoire
 * de l'application (livres, utilisateurs, emprunts)
 * Lien : https://refactoring.guru/fr/design-patterns/singleton/java/example
 */
public class DataStore {

    // L'unique instance (volatile pour thread-safety si besoin)
    private static DataStore instance;

    // Les données de l'application
    private List<Book> books;
    private List<User> users;
    private List<Loan> loans;

    // Constructeur privé : empêche la création d'instances depuis l'extérieur
    private DataStore() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.loans = new ArrayList<>();

        // Initialisation avec quelques données de test
        initializeTestData();
    }

    /**
     * Méthode pour obtenir l'unique instance du DataStore
     * Pattern : Lazy Initialization (l'instance est créée au premier appel)
     */
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // Getters pour accéder aux listes
    public List<Book> getBooks() {
        return books;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    /**
     * Initialise quelques données de test pour faciliter les tests
     */
    private void initializeTestData() {
        // Quelques livres de test
        books.add(new Book.Builder()
                .isbn("978-0-547-92822-7")
                .title("1984")
                .author("George Orwell")
                .publisher("Gallimard")
                .year(1949)
                .build());

        books.add(new Book.Builder()
                .isbn("978-2-07-036822-8")
                .title("Le Petit Prince")
                .author("Antoine de Saint-Exupéry")
                .publisher("Gallimard")
                .year(1943)
                .build());

        books.add(new Book.Builder()
                .isbn("978-2-253-00249-1")
                .title("Les Misérables")
                .author("Victor Hugo")
                .publisher("Le Livre de Poche")
                .year(1862)
                .build());

        // Quelques utilisateurs de test
        users.add(new User("U001", "Jean", "Dupont", "jean.dupont@email.com"));
        users.add(new User("U002", "Marie", "Martin", "marie.martin@email.com"));
        users.add(new User("U003", "Pierre", "Durand", "pierre.durand@email.com"));
    }

    /**
     * Méthode utilitaire pour réinitialiser les données (pratique pour les tests)
     */
    public void reset() {
        books.clear();
        users.clear();
        loans.clear();
        initializeTestData();
    }
}