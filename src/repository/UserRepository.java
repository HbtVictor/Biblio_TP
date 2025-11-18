package repository;

import model.User;
import java.util.List;
import java.util.Optional;

/**
 * Pattern : Repository
 * Interface pour l'accès aux données des utilisateurs
 */
public interface UserRepository {

    void save(User user);

    Optional<User> findById(String userId);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    boolean deleteById(String userId);
}