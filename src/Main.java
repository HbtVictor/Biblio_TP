import util.DataStore;
import model.Book;

public class Main {
    public static void main(String[] args) {
        DataStore store = DataStore.getInstance();

        System.out.println("=== Livres en mémoire ===");
        for (Book book : store.getBooks()) {
            System.out.println(book);
        }

        // Test du Builder
        Book newBook = new Book.Builder()
                .isbn("123456")
                .title("Test Builder")
                .author("Moi")
                .build();

        System.out.println("\n=== Nouveau livre créé ===");
        System.out.println(newBook);
    }
}