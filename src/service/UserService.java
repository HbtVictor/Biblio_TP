package service;

import dto.UserDTO;
import model.User;
import repository.UserRepository;
import repository.UserRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Pattern : Service Layer
 * Logique métier liée aux utilisateurs
 */
public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepositoryImpl();
    }

    /**
     * Ajoute un nouvel utilisateur
     */
    public void addUser(String userId, String firstName, String lastName, String email) {
        // Validation métier
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("L'ID utilisateur ne peut pas être vide");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email invalide");
        }

        User user = new User(userId, firstName, lastName, email);
        userRepository.save(user);
    }

    /**
     * Récupère tous les utilisateurs sous forme de DTOs
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un utilisateur par son ID
     */
    public UserDTO getUserById(String userId) {
        return userRepository.findById(userId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    /**
     * Vérifie si un utilisateur existe
     */
    public boolean userExists(String userId) {
        return userRepository.findById(userId).isPresent();
    }

    /**
     * Récupère l'email d'un utilisateur (pour les notifications)
     */
    public String getUserEmail(String userId) {
        return userRepository.findById(userId)
                .map(User::getEmail)
                .orElse(null);
    }

    /**
     * Récupère le nom complet d'un utilisateur
     */
    public String getUserFullName(String userId) {
        return userRepository.findById(userId)
                .map(User::getFullName)
                .orElse("Utilisateur inconnu");
    }

    /**
     * Conversion User → UserDTO
     */
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getFullName(),
                user.getEmail()
        );
    }
}