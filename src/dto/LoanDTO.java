package dto;

/**
 * Pattern : DTO (Data Transfer Object)
 * Version simplifiée de Loan pour l'affichage
 */
public class LoanDTO {
    private String loanId;
    private String userName;
    private String bookTitle;
    private String loanDate;
    private String dueDate;
    private String returnDate;
    private String status; // "En cours", "Retourné", "En retard"

    public LoanDTO(String loanId, String userName, String bookTitle,
                   String loanDate, String dueDate, String returnDate, String status) {
        this.loanId = loanId;
        this.userName = userName;
        this.bookTitle = bookTitle;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    // Getters
    public String getLoanId() { return loanId; }
    public String getUserName() { return userName; }
    public String getBookTitle() { return bookTitle; }
    public String getLoanDate() { return loanDate; }
    public String getDueDate() { return dueDate; }
    public String getReturnDate() { return returnDate; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("Emprunt #%s | Livre: %s | Emprunteur: %s | Statut: %s",
                loanId, bookTitle, userName, status);
    }
}