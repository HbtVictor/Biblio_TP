import dto.BookDTO;
import dto.LoanDTO;
import model.User;
import service.BookService;
import service.LoanService;
import service.NotificationService;
import service.UserService;

import java.util.List;
import java.util.Scanner;

/**
 * Application principale - Point d'entrée
 * Conforme aux consignes du Mini Projet Partie 1
 */
public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static BookService bookService;
    private static UserService userService;
    private static LoanService loanService;
    private static NotificationService notificationService;

    // Utilisateur actuellement connecté (null si personne n'est connecté)
    private static User currentUser = null;

    public static void main(String[] args) {
        // Initialisation des services
        initializeServices();

        // Boucle principale du menu
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = readIntInput();
            scanner.nextLine(); // Consomme le retour à la ligne

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

    /**
     * Affiche le menu conforme aux consignes
     */
    private static void displayMenu() {
        System.out.println("\n=== Ma Petite Bibliothèque ===");
        System.out.println("1. S'inscrire");
        System.out.println("2. Se connecter");
        System.out.println("3. Ajouter un livre (admin seulement)");
        System.out.println("4. Voir tous les livres");
        System.out.println("5. Rechercher un livre par titre");
        System.out.println("6. Emprunter un livre");
        System.out.println("7. Retourner un livre");
        System.out.println("8. Voir mes emprunts");
        System.out.println("9. Quitter");
        System.out.print("Votre choix : ");
    }

    private static boolean handleMenuChoice(int choice) {
        System.out.println(); // Ligne vide pour la lisibilité

        switch (choice) {
            case 1 -> register();
            case 2 -> login();
            case 3 -> addNewBook();
            case 4 -> displayAllBooks();
            case 5 -> searchBooksByTitle();
            case 6 -> borrowBook();
            case 7 -> returnBook();
            case 8 -> displayMyLoans();
            case 9 -> {
                return false; // Quitte l'application
            }
            default -> System.out.println("❌ Choix invalide. Veuillez réessayer.");
        }

        return true; // Continue la boucle
    }

    // ==================== 1. S'INSCRIRE ====================

    private static void register() {
        System.out.println("=== INSCRIPTION ===");

        System.out.print("Identifiant : ");
        String userId = scanner.nextLine();

        System.out.print("Prénom : ");
        String firstName = scanner.nextLine();

        System.out.print("Nom : ");
        String lastName = scanner.nextLine();

        System.out.print("Email : ");
        String email = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        userService.register(userId, firstName, lastName, email, password);
        System.out.println("✅ Inscription réussie ! Vous pouvez maintenant vous connecter.");
    }

    // ==================== 2. SE CONNECTER ====================

    private static void login() {
        System.out.println("=== CONNEXION ===");

        System.out.print("Identifiant : ");
        String userId = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        User user = userService.login(userId, password);
        if (user != null) {
            currentUser = user;
            System.out.println("✅ Connexion réussie ! Bienvenue " + user.getFullName());
            if (user.isAdmin()) {
                System.out.println("🔑 Vous êtes connecté en tant qu'administrateur");
            }
        } else {
            System.out.println("❌ Identifiant ou mot de passe incorrect");
        }
    }

    // ==================== 3. AJOUTER UN LIVRE (ADMIN SEULEMENT) ====================

    private static void addNewBook() {
        // Vérification : utilisateur connecté et admin
        if (currentUser == null) {
            System.out.println("❌ Vous devez être connecté pour ajouter un livre");
            return;
        }

        if (!currentUser.isAdmin()) {
            System.out.println("❌ Seuls les administrateurs peuvent ajouter des livres");
            return;
        }

        System.out.println("=== AJOUTER UN NOUVEAU LIVRE ===");

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

    // ==================== 4. VOIR TOUS LES LIVRES ====================

    private static void displayAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("📚 Aucun livre dans la bibliothèque.");
            return;
        }

        System.out.println("=== TOUS LES LIVRES (" + books.size() + ") ===");
        for (BookDTO book : books) {
            System.out.println(book);
        }
    }

    // ==================== 5. RECHERCHER UN LIVRE PAR TITRE ====================

    private static void searchBooksByTitle() {
        System.out.print("Entrez le titre (ou une partie) : ");
        String keyword = scanner.nextLine();

        List<BookDTO> books = bookService.searchBooksByTitle(keyword);
        if (books.isEmpty()) {
            System.out.println("❌ Aucun livre trouvé avec le titre : " + keyword);
            return;
        }

        System.out.println("=== RÉSULTATS DE LA RECHERCHE (" + books.size() + ") ===");
        for (BookDTO book : books) {
            System.out.println(book);
        }
    }

    // ==================== 6. EMPRUNTER UN LIVRE ====================

    private static void borrowBook() {
        // Vérification : utilisateur connecté
        if (currentUser == null) {
            System.out.println("❌ Vous devez être connecté pour emprunter un livre");
            return;
        }

        System.out.println("=== EMPRUNTER UN LIVRE ===");

        System.out.print("ISBN du livre : ");
        String isbn = scanner.nextLine();

        loanService.createLoan(currentUser.getUserId(), isbn);
        System.out.println("✅ Emprunt enregistré !");
    }

    // ==================== 7. RETOURNER UN LIVRE ====================

    private static void returnBook() {
        // Vérification : utilisateur connecté
        if (currentUser == null) {
            System.out.println("❌ Vous devez être connecté pour retourner un livre");
            return;
        }

        System.out.println("=== RETOURNER UN LIVRE ===");

        // Affiche les emprunts actifs de l'utilisateur
        List<LoanDTO> myLoans = loanService.getActiveLoansByUserId(currentUser.getUserId());
        if (myLoans.isEmpty()) {
            System.out.println("❌ Vous n'avez aucun emprunt en cours.");
            return;
        }

        System.out.println("Vos emprunts en cours :");
        for (LoanDTO loan : myLoans) {
            System.out.println("  - " + loan);
        }

        System.out.print("\nID de l'emprunt à retourner : ");
        String loanId = scanner.nextLine();

        loanService.returnBook(loanId);
        System.out.println("✅ Livre retourné !");
    }

    // ==================== 8. VOIR MES EMPRUNTS ====================

    private static void displayMyLoans() {
        // Vérification : utilisateur connecté
        if (currentUser == null) {
            System.out.println("❌ Vous devez être connecté pour voir vos emprunts");
            return;
        }

        List<LoanDTO> myLoans = loanService.getActiveLoansByUserId(currentUser.getUserId());
        if (myLoans.isEmpty()) {
            System.out.println("📋 Vous n'avez aucun emprunt en cours.");
            return;
        }

        System.out.println("=== MES EMPRUNTS EN COURS (" + myLoans.size() + ") ===");
        for (LoanDTO loan : myLoans) {
            System.out.println(loan);
        }
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
