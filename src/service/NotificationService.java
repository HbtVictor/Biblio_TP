package service;

import pattern.NotificationFactory;
import pattern.NotificationStrategy;

/**
 * Pattern : Observer (implémente LoanObserver)
 * Pourquoi : Écoute les événements d'emprunt et déclenche automatiquement
 * une notification quand un emprunt est créé ou retourné.
 *
 * Utilise aussi : Factory Pattern + Strategy Pattern
 */
public class NotificationService implements LoanService.LoanObserver {

    private NotificationStrategy notificationStrategy;
    private final UserService userService;

    public NotificationService(UserService userService) {
        this.userService = userService;
        // Par défaut, on utilise la notification console
        this.notificationStrategy = NotificationFactory.create("console");
    }

    /**
     * Change la stratégie de notification dynamiquement
     * Pattern : Strategy - On peut changer de stratégie à la volée
     */
    public void setNotificationStrategy(String type) {
        // Pattern : Factory - Utilise la factory pour créer la bonne stratégie
        this.notificationStrategy = NotificationFactory.create(type);
        System.out.println("✅ Mode de notification changé : " + type);
    }

    /**
     * Pattern : Observer - Méthode appelée automatiquement par LoanService
     * quand un événement d'emprunt se produit
     */
    @Override
    public void onLoanEvent(String userId, String isbn, String message) {
        // Récupère l'email ou l'ID de l'utilisateur comme destinataire
        String recipient = userService.getUserEmail(userId);

        if (recipient == null) {
            recipient = userId; // Fallback sur l'ID si pas d'email
        }

        // Envoie la notification avec la stratégie actuelle
        notificationStrategy.send(recipient, message);
    }

    /**
     * Envoie une notification manuelle (utile pour tester)
     */
    public void sendNotification(String recipient, String message) {
        notificationStrategy.send(recipient, message);
    }
}