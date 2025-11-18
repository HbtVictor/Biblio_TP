package pattern;

/**
 * Pattern : Strategy (implémentation concrète)
 * Simule l'envoi d'un email (en vrai, on utiliserait JavaMail API)
 */
public class EmailNotification implements NotificationStrategy {

    @Override
    public void send(String recipient, String message) {
        // Simulation d'envoi d'email
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║          📧 EMAIL ENVOYÉ                          ║");
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║ De      : bibliotheque@example.com");
        System.out.println("║ À       : " + recipient);
        System.out.println("║ Sujet   : Notification Bibliothèque");
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║ Message :");

        // Affiche le message ligne par ligne
        String[] lines = message.split("\n");
        for (String line : lines) {
            System.out.println("║   " + line);
        }

        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║ ✅ Email envoyé avec succès !                     ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");
    }
}