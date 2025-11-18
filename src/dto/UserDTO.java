package dto;

/**
 * Pattern : DTO (Data Transfer Object)
 * Version simplifiée de User pour l'affichage
 */
public class UserDTO {
    private String userId;
    private String fullName;
    private String email;

    public UserDTO(String userId, String fullName, String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("ID: %s | Nom: %s | Email: %s", userId, fullName, email);
    }
}