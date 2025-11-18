package repository;

import model.Loan;
import java.util.List;
import java.util.Optional;

/**
 * Pattern : Repository
 * Interface pour l'accès aux données des emprunts
 */
public interface LoanRepository {

    void save(Loan loan);

    Optional<Loan> findById(String loanId);

    List<Loan> findAll();

    List<Loan> findByUserId(String userId);

    List<Loan> findByIsbn(String isbn);

    List<Loan> findActiveLoans(); // Emprunts non retournés

    List<Loan> findOverdueLoans(); // Emprunts en retard

    void update(Loan loan);
}