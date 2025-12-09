package service;

import dto.LoanDTO;
import model.Loan;
import repository.LoanRepository;
import repository.LoanRepositoryImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Pattern : Service Layer
 * Logique métier liée aux emprunts
 *
 * Pattern : Observer (Subject)
 * Notifie les observateurs quand un emprunt est créé ou retourné
 * Lien : https://refactoring.guru/fr/design-patterns/observer/java/example
 */
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final UserService userService;

    // Pattern Observer : liste des observateurs à notifier
    private final List<LoanObserver> observers;

    private int loanCounter = 1; // Pour générer des IDs uniques

    public LoanService(BookService bookService, UserService userService) {
        this.loanRepository = new LoanRepositoryImpl();
        this.bookService = bookService;
        this.userService = userService;
        this.observers = new ArrayList<>();
    }

    /**
     * Pattern Observer : Ajoute un observateur
     */
    public void addObserver(LoanObserver observer) {
        observers.add(observer);
    }

    /**
     * Pattern Observer : Notifie tous les observateurs
     */
    private void notifyObservers(String userId, String isbn, String message) {
        for (LoanObserver observer : observers) {
            observer.onLoanEvent(userId, isbn, message);
        }
    }

    /**
     * Crée un nouvel emprunt
     */
    public void createLoan(String userId, String isbn) {
        // Validation métier
        if (!userService.userExists(userId)) {
            throw new IllegalArgumentException("Utilisateur introuvable : " + userId);
        }

        if (!bookService.isBookAvailable(isbn)) {
            throw new IllegalArgumentException("Le livre n'est pas disponible : " + isbn);
        }

        // Création de l'emprunt
        String loanId = "L" + String.format("%03d", loanCounter++);
        LocalDate loanDate = LocalDate.now();
        LocalDate dueDate = loanDate.plusDays(14); // 14 jours d'emprunt

        Loan loan = new Loan(loanId, userId, isbn, loanDate, dueDate);
        loanRepository.save(loan);

        // Marque le livre comme emprunté
        bookService.markAsUnavailable(isbn);

        // Pattern Observer : Notifie les observateurs
        String bookTitle = bookService.getBookByIsbn(isbn).getTitle();
        String userName = userService.getUserFullName(userId);
        String message = String.format(
                "📚 Emprunt créé avec succès !\n" +
                        "Livre : %s\n" +
                        "Emprunteur : %s\n" +
                        "Date de retour : %s",
                bookTitle, userName, dueDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );

        notifyObservers(userId, isbn, message);
    }

    /**
     * Retourne un livre emprunté
     */
    public void returnBook(String loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Emprunt introuvable : " + loanId));

        if (loan.isReturned()) {
            throw new IllegalArgumentException("Ce livre a déjà été retourné");
        }

        // Marque l'emprunt comme retourné
        loan.setReturnDate(LocalDate.now());
        loanRepository.update(loan);

        // Marque le livre comme disponible
        bookService.markAsAvailable(loan.getIsbn());

        // Notifie les observateurs
        String bookTitle = bookService.getBookByIsbn(loan.getIsbn()).getTitle();
        String userName = userService.getUserFullName(loan.getUserId());
        String message = String.format(
                "✅ Livre retourné !\n" +
                        "Livre : %s\n" +
                        "Emprunteur : %s",
                bookTitle, userName
        );

        notifyObservers(loan.getUserId(), loan.getIsbn(), message);
    }

    /**
     * Récupère tous les emprunts actifs
     */
    public List<LoanDTO> getActiveLoans() {
        return loanRepository.findActiveLoans().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère tous les emprunts
     */
    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les emprunts en retard
     */
    public List<LoanDTO> getOverdueLoans() {
        return loanRepository.findOverdueLoans().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les emprunts actifs d'un utilisateur spécifique
     * Pour la fonctionnalité "Voir mes emprunts"
     */
    public List<LoanDTO> getActiveLoansByUserId(String userId) {
        return loanRepository.findActiveLoans().stream()
                .filter(loan -> loan.getUserId().equals(userId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Conversion Loan → LoanDTO
     */
    private LoanDTO convertToDTO(Loan loan) {
        String userName = userService.getUserFullName(loan.getUserId());
        String bookTitle = bookService.getBookByIsbn(loan.getIsbn()).getTitle();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String loanDate = loan.getLoanDate().format(formatter);
        String dueDate = loan.getDueDate().format(formatter);
        String returnDate = loan.getReturnDate() != null ?
                loan.getReturnDate().format(formatter) : "Non retourné";

        String status;
        if (loan.isReturned()) {
            status = "Retourné";
        } else if (loan.isOverdue()) {
            status = "En retard";
        } else {
            status = "En cours";
        }

        return new LoanDTO(loan.getLoanId(), userName, bookTitle,
                loanDate, dueDate, returnDate, status);
    }

    /**
     * Interface Observer pour les événements d'emprunt
     */
    public interface LoanObserver {
        void onLoanEvent(String userId, String isbn, String message);
    }
}