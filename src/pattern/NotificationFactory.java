package pattern;

/**
 * Pattern : Factory Method
 * Pourquoi : Crée des objets de notification sans exposer la logique de création.
 * Le client demande un type ("email" ou "console") et reçoit la bonne stratégie.
 * Lien : https://refactoring.guru/fr/design-patterns/factory-method/java/example
 */
public class NotificationFactory {

    /**
     * Crée une stratégie de notification selon le type demandé
     * @param type "email" ou "console"
     * @return Une instance de NotificationStrategy
     */
    public static NotificationStrategy create(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Le type de notification ne peut pas être vide");
        }

        return switch (type.toLowerCase()) {
            case "email" -> new EmailNotification();
            case "console" -> new ConsoleNotification();
            default -> throw new IllegalArgumentException(
                    "Type de notification inconnu : " + type +
                            ". Types disponibles : 'email', 'console'"
            );
        };
    }
}