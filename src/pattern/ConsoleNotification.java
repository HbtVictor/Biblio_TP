package pattern;

/**
 * Pattern : Strategy (implémentation concrète)
 * Envoie une notification sur la console
 */
public class ConsoleNotification implements NotificationStrategy {

    @Override
    public void send(String recipient, String message) {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║          📢 NOTIFICATION CONSOLE                  ║");
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║ Destinataire : " + recipient);
        System.out.println("╠════════════════════════════════════════════════════╣");

        // Affiche le message ligne par ligne
        String[] lines = message.split("\n");
        for (String line : lines) {
            System.out.println("║ " + line);
        }

        System.out.println("╚════════════════════════════════════════════════════╝\n");
    }
}