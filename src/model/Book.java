package model;

/**
 * Entité représentant un livre dans la bibliothèque
 *
 * Pattern : Builder
 * Pourquoi : Permet de créer un objet Book de manière lisible et flexible
 * avec plusieurs attributs optionnels
 * Lien : https://refactoring.guru/fr/design-patterns/builder/java/example
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private boolean available;

    // Constructeur privé : on force l'utilisation du Builder
    private Book(Builder builder) {
        this.isbn = builder.isbn;
        this.title = builder.title;
        this.author = builder.author;
        this.publisher = builder.publisher;
        this.year = builder.year;
        this.available = builder.available;
    }

    // Getters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public int getYear() { return year; }
    public boolean isAvailable() { return available; }

    // Setters (uniquement pour available, car le reste est immuable après création)
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", available=" + available +
                '}';
    }

    /**
     * Pattern : Builder (classe interne statique)
     * Facilite la création d'objets Book complexes
     */
    public static class Builder {
        private String isbn;
        private String title;
        private String author;
        private String publisher = "Inconnu"; // Valeur par défaut
        private int year = 0;
        private boolean available = true; // Par défaut, un livre ajouté est disponible

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder available(boolean available) {
            this.available = available;
            return this;
        }

        // Méthode finale qui construit l'objet Book
        public Book build() {
            // Validation minimale
            if (isbn == null || isbn.isEmpty()) {
                throw new IllegalArgumentException("ISBN obligatoire");
            }
            if (title == null || title.isEmpty()) {
                throw new IllegalArgumentException("Titre obligatoire");
            }
            return new Book(this);
        }
    }
}