package repository;

import model.User;
import util.DataStore;

import java.util.List;
import java.util.Optional;

/**
 * Pattern : Repository (implémentation)
 * Gère l'accès aux données des utilisateurs
 */
public class UserRepositoryImpl implements UserRepository {

    private final DataStore dataStore;

    public UserRepositoryImpl() {
        this.dataStore = DataStore.getInstance();
    }

    @Override
    public void save(User user) {
        if (findById(user.getUserId()).isPresent()) {
            throw new IllegalArgumentException("Un utilisateur avec cet ID existe déjà : " + user.getUserId());
        }
        dataStore.getUsers().add(user);
    }

    @Override
    public Optional<User> findById(String userId) {
        return dataStore.getUsers().stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return dataStore.getUsers();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return dataStore.getUsers().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public boolean deleteById(String userId) {
        return dataStore.getUsers().removeIf(user -> user.getUserId().equals(userId));
    }
}