package pattern;

/**
 * Pattern : Strategy
 * Pourquoi : Permet de définir une famille d'algorithmes (différentes façons d'envoyer
 * une notification) et de les rendre interchangeables.
 * Lien : https://refactoring.guru/fr/design-patterns/strategy/java/example
 */
public interface NotificationStrategy {

    /**
     * Envoie une notification à un destinataire
     * @param recipient Le destinataire (email, user ID, etc.)
     * @param message Le message à envoyer
     */
    void send(String recipient, String message);
}