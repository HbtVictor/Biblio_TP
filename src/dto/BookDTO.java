package dto;

/**
 * Pattern : DTO (Data Transfer Object)
 * Pourquoi : Sépare les entités internes (model) des objets exposés à l'extérieur.
 * On ne renvoie jamais directement les objets du model.
 * Lien : https://blog.ippon.fr/2018/04/17/dto-data-transfer-object/
 */
public class BookDTO {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private String status; // "Disponible" ou "Emprunté"

    public BookDTO(String isbn, String title, String author, String publisher, int year, String status) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.status = status;
    }

    // Getters uniquement (un DTO est immuable)
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public int getYear() { return year; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("ISBN: %s | Titre: %s | Auteur: %s | Année: %d | Statut: %s",
                isbn, title, author, year, status);
    }

    /**
     * Affichage formaté pour le menu console
     */
    public String toFormattedString() {
        return String.format(
                "┌────────────────────────────────────────────────────┐\n" +
                        "│ 📖 %-47s│\n" +
                        "├────────────────────────────────────────────────────┤\n" +
                        "│ ISBN      : %-39s│\n" +
                        "│ Auteur    : %-39s│\n" +
                        "│ Éditeur   : %-39s│\n" +
                        "│ Année     : %-39d│\n" +
                        "│ Statut    : %-39s│\n" +
                        "└────────────────────────────────────────────────────┘",
                title, isbn, author, publisher, year, status
        );
    }
}