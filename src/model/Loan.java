package model;

import java.time.LocalDate;

/**
 * Entité représentant un emprunt de livre
 */
public class Loan {
    private String loanId;
    private String userId;
    private String isbn;
    private LocalDate loanDate;
    private LocalDate returnDate; // null si pas encore retourné
    private LocalDate dueDate; // Date limite de retour

    public Loan(String loanId, String userId, String isbn, LocalDate loanDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.userId = userId;
        this.isbn = isbn;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }

    // Getters
    public String getLoanId() { return loanId; }
    public String getUserId() { return userId; }
    public String getIsbn() { return isbn; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public LocalDate getDueDate() { return dueDate; }

    // Setters
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public boolean isReturned() {
        return returnDate != null;
    }

    public boolean isOverdue() {
        return !isReturned() && LocalDate.now().isAfter(dueDate);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId='" + loanId + '\'' +
                ", userId='" + userId + '\'' +
                ", isbn='" + isbn + '\'' +
                ", loanDate=" + loanDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", returned=" + isReturned() +
                '}';
    }
}