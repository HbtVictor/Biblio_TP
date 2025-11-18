package repository;

import model.Loan;
import util.DataStore;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Pattern : Repository (implémentation)
 * Gère l'accès aux données des emprunts
 */
public class LoanRepositoryImpl implements LoanRepository {

    private final DataStore dataStore;

    public LoanRepositoryImpl() {
        this.dataStore = DataStore.getInstance();
    }

    @Override
    public void save(Loan loan) {
        if (findById(loan.getLoanId()).isPresent()) {
            throw new IllegalArgumentException("Un emprunt avec cet ID existe déjà : " + loan.getLoanId());
        }
        dataStore.getLoans().add(loan);
    }

    @Override
    public Optional<Loan> findById(String loanId) {
        return dataStore.getLoans().stream()
                .filter(loan -> loan.getLoanId().equals(loanId))
                .findFirst();
    }

    @Override
    public List<Loan> findAll() {
        return dataStore.getLoans();
    }

    @Override
    public List<Loan> findByUserId(String userId) {
        return dataStore.getLoans().stream()
                .filter(loan -> loan.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findByIsbn(String isbn) {
        return dataStore.getLoans().stream()
                .filter(loan -> loan.getIsbn().equals(isbn))
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findActiveLoans() {
        return dataStore.getLoans().stream()
                .filter(loan -> !loan.isReturned())
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findOverdueLoans() {
        return dataStore.getLoans().stream()
                .filter(Loan::isOverdue)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Loan loan) {
        Optional<Loan> existingLoan = findById(loan.getLoanId());
        if (existingLoan.isEmpty()) {
            throw new IllegalArgumentException("Emprunt introuvable : " + loan.getLoanId());
        }
        // Remplace l'ancien par le nouveau
        dataStore.getLoans().remove(existingLoan.get());
        dataStore.getLoans().add(loan);
    }
}