# 📚 Ma Petite Bibliothèque

**Projet d'Architecture Logicielle - M1 DEVFLSK**

Application console de gestion de bibliothèque démontrant l'utilisation de 8 design patterns classiques dans une architecture en couches (SOA).

---

## 🚀 Comment lancer l'application

### Prérequis
- Java 17 ou supérieur
- Un terminal

### Compilation
```bash
javac -encoding UTF-8 -d out src/**/*.java src/*.java
```

### Exécution
```bash
java -cp out App
```

---

## 🎯 Fonctionnalités

L'application propose 9 fonctionnalités accessibles via un menu interactif :

1. **S'inscrire** - Créer un nouveau compte utilisateur
2. **Se connecter** - Authentification avec identifiant et mot de passe
3. **Ajouter un livre** - Réservé aux administrateurs uniquement
4. **Voir tous les livres** - Afficher le catalogue complet
5. **Rechercher un livre par titre** - Recherche par mot-clé
6. **Emprunter un livre** - Créer un emprunt (nécessite une connexion)
7. **Retourner un livre** - Marquer un emprunt comme retourné
8. **Voir mes emprunts** - Afficher uniquement les emprunts de l'utilisateur connecté
9. **Quitter** - Fermer l'application

### Système d'authentification
- Les utilisateurs doivent se connecter pour emprunter, retourner des livres ou voir leurs emprunts
- Un système de rôles distingue les administrateurs des utilisateurs normaux
- Seuls les administrateurs peuvent ajouter des livres

### Comptes de test disponibles
| Identifiant | Mot de passe | Rôle |
|-------------|--------------|------|
| `admin` | `admin123` | Administrateur |
| `U001` | `password` | Utilisateur |
| `U002` | `password` | Utilisateur |

---

## 🏗️ Architecture du projet

Le projet suit une architecture en couches (SOA) avec séparation des préoccupations :

```
src/
├── App.java              # Point d'entrée avec le menu principal
├── model/                # Entités métier (Book, User, Loan)
│   ├── Book.java         # ← Pattern Builder
│   ├── User.java
│   └── Loan.java
├── dto/                  # Objets de transfert de données
│   ├── BookDTO.java      # ← Pattern DTO
│   ├── UserDTO.java
│   └── LoanDTO.java
├── repository/           # Couche d'accès aux données
│   ├── BookRepository.java          # ← Pattern Repository
│   ├── BookRepositoryImpl.java
│   ├── UserRepository.java
│   ├── UserRepositoryImpl.java
│   ├── LoanRepository.java
│   └── LoanRepositoryImpl.java
├── service/              # Logique métier
│   ├── BookService.java             # ← Pattern Service Layer
│   ├── UserService.java
│   ├── LoanService.java             # ← Pattern Observer (Subject)
│   └── NotificationService.java     # ← Pattern Observer (Observer)
├── pattern/              # Patterns de notification
│   ├── NotificationFactory.java     # ← Pattern Factory
│   ├── NotificationStrategy.java    # ← Pattern Strategy
│   ├── ConsoleNotification.java
│   └── EmailNotification.java
└── util/                 # Utilitaires
    └── DataStore.java                # ← Pattern Singleton
```

---

## 📋 Les 8 Design Patterns implémentés

Chaque pattern est commenté dans le code avec des explications et des liens de référence.

| N° | Pattern | Classe/Ligne | Lien de référence |
|---|---|---|---|
| 1 | **Singleton** | `util/DataStore.java` lignes 18-45 | [Refactoring Guru](https://refactoring.guru/fr/design-patterns/singleton/java/example) |
| 2 | **Builder** | `model/Book.java` lignes 49-88 | [Refactoring Guru](https://refactoring.guru/fr/design-patterns/builder/java/example) |
| 3 | **Factory** | `pattern/NotificationFactory.java` lignes 15-28 | [Refactoring Guru](https://refactoring.guru/fr/design-patterns/factory-method/java/example) |
| 4 | **Strategy** | `pattern/NotificationStrategy.java` + `ConsoleNotification.java` + `EmailNotification.java` | [Refactoring Guru](https://refactoring.guru/fr/design-patterns/strategy/java/example) |
| 5 | **Repository** | `repository/BookRepository.java` + implémentations (`*RepositoryImpl.java`) | [Java et Moi](https://javaetmoi.com/2013/03/17/le-pattern-repository/) |
| 6 | **Service Layer** | `service/BookService.java`, `UserService.java`, `LoanService.java`, `NotificationService.java` | [Baeldung](https://www.baeldung.com/java-enterprise-design-patterns#service-layer) |
| 7 | **DTO (Data Transfer Object)** | `dto/BookDTO.java`, `dto/UserDTO.java`, `dto/LoanDTO.java` | [Ippon](https://blog.ippon.fr/2018/04/17/dto-data-transfer-object/) |
| 8 | **Observer** | `service/LoanService.java` lignes 28-54 (Subject) + `service/NotificationService.java` (Observer) | [Refactoring Guru](https://refactoring.guru/fr/design-patterns/observer/java/example) |

---

## 📸 Captures d'écran du programme en fonctionnement

### 1. Menu principal
```
=== Ma Petite Bibliothèque ===
1. S'inscrire
2. Se connecter
3. Ajouter un livre (admin seulement)
4. Voir tous les livres
5. Rechercher un livre par titre
6. Emprunter un livre
7. Retourner un livre
8. Voir mes emprunts
9. Quitter
Votre choix :
```

### 2. Inscription d'un nouvel utilisateur
```
=== INSCRIPTION ===
Identifiant : john
Prénom : John
Nom : Doe
Email : john.doe@email.com
Mot de passe : mypass
✅ Inscription réussie ! Vous pouvez maintenant vous connecter.
```

### 3. Connexion en tant qu'administrateur
```
=== CONNEXION ===
Identifiant : admin
Mot de passe : admin123
✅ Connexion réussie ! Bienvenue Admin Bibliothèque
🔑 Vous êtes connecté en tant qu'administrateur
```

### 4. Affichage de tous les livres (Pattern DTO)
```
=== TOUS LES LIVRES (3) ===
📖 1984 par George Orwell (1949) - Gallimard
   ISBN: 978-0-547-92822-7 | Statut: Disponible

📖 Le Petit Prince par Antoine de Saint-Exupéry (1943) - Gallimard
   ISBN: 978-2-07-036822-8 | Statut: Disponible

📖 Les Misérables par Victor Hugo (1862) - Le Livre de Poche
   ISBN: 978-2-253-00249-1 | Statut: Disponible
```

### 5. Ajout d'un livre (admin seulement)
```
=== AJOUTER UN NOUVEAU LIVRE ===
ISBN : 978-2-07-037000-0
Titre : L'Étranger
Auteur : Albert Camus
Éditeur : Gallimard
Année : 1942
✅ Livre ajouté avec succès !
```

### 6. Emprunt d'un livre avec notification (Pattern Observer)
```
=== EMPRUNTER UN LIVRE ===
ISBN du livre : 978-0-547-92822-7
✅ Emprunt enregistré !

╔════════════════════════════════════════════════════╗
║          📢 NOTIFICATION CONSOLE                  ║
╠════════════════════════════════════════════════════╣
║ Destinataire : john.doe@email.com
╠════════════════════════════════════════════════════╣
║ 📚 Emprunt créé avec succès !
║ Livre : 1984
║ Emprunteur : John Doe
║ Date de retour : 23/12/2024
╚════════════════════════════════════════════════════╝
```

### 7. Mes emprunts (filtré par utilisateur connecté)
```
=== MES EMPRUNTS EN COURS (1) ===
📚 Emprunt #L001
   Livre: 1984
   Emprunteur: John Doe
   Date d'emprunt: 09/12/2024
   Date de retour prévue: 23/12/2024
   Statut: En cours
```

---

## 🎓 Explications détaillées des patterns

### 1. Singleton - `DataStore`
Le `DataStore` est l'unique instance qui stocke toutes les données en mémoire (livres, utilisateurs, emprunts). Le constructeur est privé et l'accès se fait via `getInstance()`.

**Pourquoi ?** Garantit qu'il n'y a qu'une seule source de vérité pour les données de l'application.

### 2. Builder - `Book`
Permet de construire un objet `Book` de manière lisible et flexible sans constructeur surchargé.

```java
Book book = new Book.Builder()
    .isbn("978-2-07-037000-0")
    .title("L'Étranger")
    .author("Albert Camus")
    .publisher("Gallimard")
    .year(1942)
    .build();
```

### 3. Factory - `NotificationFactory`
Crée dynamiquement la bonne stratégie de notification (console ou email) selon le type demandé.

```java
NotificationStrategy strategy = NotificationFactory.create("email");
```

### 4. Strategy - `NotificationStrategy`
Permet de changer l'algorithme de notification à l'exécution sans modifier le code. Deux implémentations : `ConsoleNotification` et `EmailNotification`.

### 5. Repository
Abstrait la logique d'accès aux données. Les services utilisent les interfaces `BookRepository`, `UserRepository`, `LoanRepository` sans connaître l'implémentation.

**Avantage** : On peut facilement changer la source de données (fichier, base de données) sans modifier les services.

### 6. Service Layer
Toute la logique métier est centralisée dans les services (`BookService`, `UserService`, `LoanService`, `NotificationService`). L'application (`App.java`) ne fait qu'orchestrer les appels.

### 7. DTO (Data Transfer Object)
Les services ne renvoient jamais les entités métier directement, mais des DTOs simplifiés. Cela évite d'exposer des données sensibles (comme le mot de passe) et découple la représentation interne de la représentation externe.

### 8. Observer
Quand un emprunt est créé ou retourné dans `LoanService`, tous les observateurs enregistrés (dont `NotificationService`) sont automatiquement notifiés. Le `NotificationService` envoie alors une notification selon la stratégie active.

**Flux** : `LoanService.createLoan()` → notifie → `NotificationService` → envoie notification

---

## 🔄 Flux d'utilisation typique

1. L'utilisateur lance l'application
2. Il s'inscrit (option 1) ou se connecte (option 2)
3. Il peut consulter les livres disponibles (option 4)
4. Il emprunte un livre (option 6)
   - Le `LoanService` crée l'emprunt
   - Le pattern **Observer** déclenche une notification automatique
   - Le pattern **Strategy** détermine comment envoyer la notification
5. Il consulte ses emprunts (option 8)
6. Il retourne le livre (option 7)
7. Il quitte l'application (option 9)

---

## 👨‍💻 Auteur

**Victor Hubert** - M1 DEVFLSK
Projet réalisé dans le cadre du cours d'Architecture Logicielle

---

## 📚 Ressources utilisées

- [Refactoring Guru - Design Patterns](https://refactoring.guru/fr/design-patterns) - Référence principale pour les patterns
- [Baeldung - Enterprise Patterns](https://www.baeldung.com/java-enterprise-design-patterns) - Pattern Service Layer
- [Java et Moi - Pattern Repository](https://javaetmoi.com/2013/03/17/le-pattern-repository/) - Pattern Repository en français
- [Ippon - DTO](https://blog.ippon.fr/2018/04/17/dto-data-transfer-object/) - Pattern DTO en français

---

## ⚙️ Notes techniques

- **Encodage** : Le projet utilise UTF-8 pour supporter les caractères spéciaux
- **Version Java** : Compatible Java 17+
- **Stockage** : En mémoire (volatile, données perdues à la fermeture)
- **Architecture** : SOA simulée avec séparation en couches (model, repository, service, presentation)
- **Patterns commentés** : Chaque pattern est documenté dans le code source avec des commentaires explicatifs

---

## 📝 Conformité aux consignes

✅ 8 design patterns implémentés et commentés
✅ Architecture en couches (packages model, dto, repository, service, pattern, util)
✅ Menu console avec 9 options comme spécifié
✅ Système d'authentification (inscription/connexion)
✅ Gestion des rôles (admin/utilisateur)
✅ README complet avec tableau des patterns et captures d'écran
✅ Code commenté avec liens vers les ressources utilisées
