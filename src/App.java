import dto.BookDTO;
import dto.LoanDTO;
import dto.UserDTO;
import service.BookService;
import service.LoanService;
import service.NotificationService;
import service.UserService;

import java.util.List;
import java.util.Scanner;

/**
 * Application principale - Point d'entrée
 * Orchestre tous les services et affiche le menu console
 */
public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static BookService bookService;
    private static UserService userService;
    private static LoanService loanService;
    private static NotificationService notificationService;

    public static void main(String[] args) {
        // Initialisation des services
        initializeServices();

        // Affichage du message de bienvenue
        displayWelcome();

        // Boucle principale du menu
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = readIntInput();

            try {
                running = handleMenuChoice(choice);
            } catch (Exception e) {
                System.err.println("❌ Erreur : " + e.getMessage());
            }
        }

        System.out.println("\n👋 Merci d'avoir utilisé Ma Petite Bibliothèque !");
        scanner.close();
    }

    /**
     * Initialise tous les services et configure le pattern Observer
     */
    private static void initializeServices() {
        bookService = new BookService();
        userService = new UserService();
        notificationService = new NotificationService(userService);
        loanService = new LoanService(bookService, userService);

        // Pattern Observer : Le NotificationService s'enregistre comme observateur
        loanService.addObserver(notificationService);
    }

    private static void displayWelcome() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                                                    ║");
        System.out.println("║        📚 MA PETITE BIBLIOTHÈQUE 📚               ║");
        System.out.println("║                                                    ║");
        System.out.println("║     Projet Architecture Logicielle - M1 DEVFLSK   ║");
        System.out.println("║                                                    ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");
    }

    private static void displayMenu() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                    MENU PRINCIPAL                  ║");
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║  1. Afficher tous les livres                       ║");
        System.out.println("║  2. Afficher les livres disponibles                ║");
        System.out.println("║  3. Rechercher un livre par titre                  ║");
        System.out.println("║  4. Rechercher un livre par auteur                 ║");
        System.out.println("║  5. Ajouter un nouveau livre                       ║");
        System.out.println("║  6. Emprunter un livre                             ║");
        System.out.println("║  7. Retourner un livre                             ║");
        System.out.println("║  8. Afficher les emprunts en cours                 ║");
        System.out.println("║  9. Afficher tous les emprunts                     ║");
        System.out.println("║ 10. Afficher les utilisateurs                      ║");
        System.out.println("║ 11. Changer le mode de notification                ║");
        System.out.println("║  0. Quitter                                        ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.print("Votre choix : ");
    }

    private static boolean handleMenuChoice(int choice) {
        System.out.println(); // Ligne vide pour la lisibilité

        switch (choice) {
            case 1 -> displayAllBooks();
            case 2 -> displayAvailableBooks();
            case 3 -> searchBooksByTitle();
            case 4 -> searchBooksByAuthor();
            case 5 -> addNewBook();
            case 6 -> borrowBook();
            case 7 -> returnBook();
            case 8 -> displayActiveLoans();
            case 9 -> displayAllLoans();
            case 10 -> displayUsers();
            case 11 -> changeNotificationMode();
            case 0 -> {
                return false; // Quitte l'application
            }
            default -> System.out.println("❌ Choix invalide. Veuillez réessayer.");
        }

        return true; // Continue la boucle
    }

    // ==================== GESTION DES LIVRES ====================

    private static void displayAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("📚 Aucun livre dans la bibliothèque.");
            return;
        }

        System.out.println("📚 LISTE DE TOUS LES LIVRES (" + books.size() + ")");
        System.out.println("═".repeat(70));
        for (BookDTO book : books) {
            System.out.println(book);
        }
    }

    private static void displayAvailableBooks() {
        List<BookDTO> books = bookService.getAvailableBooks();
        if (books.isEmpty()) {
            System.out.println("📚 Aucun livre disponible pour le moment.");
            return;
        }

        System.out.println("✅ LIVRES DISPONIBLES (" + books.size() + ")");
        System.out.println("═".repeat(70));
        for (BookDTO book : books) {
            System.out.println(book);
        }
    }

    private static void searchBooksByTitle() {
        System.out.print("Entrez le titre (ou une partie) : ");
        String keyword = scanner.nextLine();

        List<BookDTO> books = bookService.searchBooksByTitle(keyword);
        if (books.isEmpty()) {
            System.out.println("❌ Aucun livre trouvé avec le titre : " + keyword);
            return;
        }

        System.out.println("🔍 RÉSULTATS DE LA RECHERCHE (" + books.size() + ")");
        System.out.println("═".repeat(70));
        for (BookDTO book : books) {
            System.out.println(book);
        }
    }

    private static void searchBooksByAuthor() {
        System.out.print("Entrez l'auteur : ");
        String author = scanner.nextLine();

        List<BookDTO> books = bookService.searchBooksByAuthor(author);
        if (books.isEmpty()) {
            System.out.println("❌ Aucun livre trouvé pour l'auteur : " + author);
            return;
        }

        System.out.println("🔍 RÉSULTATS DE LA RECHERCHE (" + books.size() + ")");
        System.out.println("═".repeat(70));
        for (BookDTO book : books) {
            System.out.println(book);
        }
    }

    private static void addNewBook() {
        System.out.println("➕ AJOUTER UN NOUVEAU LIVRE");
        System.out.println("─".repeat(50));

        System.out.print("ISBN : ");
        String isbn = scanner.nextLine();

        System.out.print("Titre : ");
        String title = scanner.nextLine();

        System.out.print("Auteur : ");
        String author = scanner.nextLine();

        System.out.print("Éditeur : ");
        String publisher = scanner.nextLine();

        System.out.print("Année : ");
        int year = readIntInput();
        scanner.nextLine(); // Consomme le retour à la ligne

        bookService.addBook(isbn, title, author, publisher, year);
        System.out.println("✅ Livre ajouté avec succès !");
    }

    // ==================== GESTION DES EMPRUNTS ====================

    private static void borrowBook() {
        System.out.println("📤 EMPRUNTER UN LIVRE");
        System.out.println("─".repeat(50));

        System.out.print("ISBN du livre : ");
        String isbn = scanner.nextLine();

        System.out.print("ID de l'utilisateur : ");
        String userId = scanner.nextLine();

        loanService.createLoan(userId, isbn);
        System.out.println("✅ Emprunt enregistré !");
    }

    private static void returnBook() {
        System.out.println("📥 RETOURNER UN LIVRE");
        System.out.println("─".repeat(50));

        // Affiche les emprunts actifs
        List<LoanDTO> activeLoans = loanService.getActiveLoans();
        if (activeLoans.isEmpty()) {
            System.out.println("❌ Aucun emprunt en cours.");
            return;
        }

        System.out.println("Emprunts en cours :");
        for (LoanDTO loan : activeLoans) {
            System.out.println("  - " + loan);
        }

        System.out.print("\nID de l'emprunt à retourner : ");
        String loanId = scanner.nextLine();

        loanService.returnBook(loanId);
        System.out.println("✅ Livre retourné !");
    }

    private static void displayActiveLoans() {
        List<LoanDTO> loans = loanService.getActiveLoans();
        if (loans.isEmpty()) {
            System.out.println("📋 Aucun emprunt en cours.");
            return;
        }

        System.out.println("📋 EMPRUNTS EN COURS (" + loans.size() + ")");
        System.out.println("═".repeat(70));
        for (LoanDTO loan : loans) {
            System.out.println(loan);
        }
    }

    private static void displayAllLoans() {
        List<LoanDTO> loans = loanService.getAllLoans();
        if (loans.isEmpty()) {
            System.out.println("📋 Aucun emprunt enregistré.");
            return;
        }

        System.out.println("📋 TOUS LES EMPRUNTS (" + loans.size() + ")");
        System.out.println("═".repeat(70));
        for (LoanDTO loan : loans) {
            System.out.println(loan);
        }
    }

    // ==================== GESTION DES UTILISATEURS ====================

    private static void displayUsers() {
        List<UserDTO> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("👤 Aucun utilisateur enregistré.");
            return;
        }

        System.out.println("👥 LISTE DES UTILISATEURS (" + users.size() + ")");
        System.out.println("═".repeat(70));
        for (UserDTO user : users) {
            System.out.println(user);
        }
    }

    // ==================== GESTION DES NOTIFICATIONS ====================

    private static void changeNotificationMode() {
        System.out.println("🔔 CHANGER LE MODE DE NOTIFICATION");
        System.out.println("─".repeat(50));
        System.out.println("1. Console (affichage dans le terminal)");
        System.out.println("2. Email (simulation d'envoi d'email)");
        System.out.print("\nVotre choix : ");

        int choice = readIntInput();
        scanner.nextLine(); // Consomme le retour à la ligne

        String type = (choice == 2) ? "email" : "console";
        notificationService.setNotificationStrategy(type);
    }

    // ==================== UTILITAIRES ====================

    private static int readIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("❌ Veuillez entrer un nombre valide : ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}