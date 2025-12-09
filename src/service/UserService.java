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
     * Inscrit un nouvel utilisateur (S'inscrire)
     */
    public void register(String userId, String firstName, String lastName, String email, String password) {
        // Validation métier
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("L'ID utilisateur ne peut pas être vide");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email invalide");
        }
        if (password == null || password.length() < 3) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 3 caractères");
        }

        // Vérifier que l'userId n'existe pas déjà
        if (userRepository.findById(userId).isPresent()) {
            throw new IllegalArgumentException("Cet identifiant est déjà utilisé");
        }

        User user = new User(userId, firstName, lastName, email, password, false);
        userRepository.save(user);
    }

    /**
     * Connecte un utilisateur (Se connecter)
     * Retourne l'utilisateur si les identifiants sont corrects, null sinon
     */
    public User login(String userId, String password) {
        return userRepository.findById(userId)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
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