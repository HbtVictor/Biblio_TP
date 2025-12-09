package model;

/**
 * Entité représentant un utilisateur de la bibliothèque
 */
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isAdmin;

    public User(String userId, String firstName, String lastName, String email, String password, boolean isAdmin) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return isAdmin; }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setAdmin(boolean admin) { isAdmin = admin; }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}