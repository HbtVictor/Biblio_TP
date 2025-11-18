# 📚 Ma Petite Bibliothèque

**Projet d'Architecture Logicielle - M1 DEVFLSK**

Application console de gestion de bibliothèque démontrant l'utilisation de 8 design patterns classiques.

---

## 🚀 Comment lancer l'application

### Prérequis
- Java 17 ou supérieur
- Un terminal

### Compilation
```bash
javac -d bin src/**/*.java src/*.java
```

### Exécution
```bash
java -cp bin App
```

---

## 🎯 Fonctionnalités

- ✅ Gestion des livres (ajout, affichage, recherche)
- ✅ Gestion des emprunts (emprunter, retourner)
- ✅ Système de notifications (console / email)
- ✅ Suivi des utilisateurs
- ✅ Détection des retards

---

## 🏗️ Architecture du projet

```
src/
├── model/          # Entités métier (Book, User, Loan)
├── dto/            # Objets de transfert de données
├── repository/     # Couche d'accès aux données
├── service/        # Logique métier
├── pattern/        # Patterns de notification
├── util/           # Utilitaires (DataStore)
└── App.java        # Point d'entrée
```

---

## 📋 Les 8 Design Patterns implémentés

| N° | Pattern | Classe/Ligne | Lien de référence |
|---|---|---|---|
| 1 | **Singleton** | `util/DataStore.java` ligne 18-30 | [Refactoring Guru](https://refactoring.guru/fr/design-patterns/singleton/java/example) |
| 2 | **Builder** | `model/Book.java` ligne 49-88 | [Refactoring Guru](https://refactoring.guru/fr/design-patterns/builder/java/example) |
| 3 | **Factory** | `pattern/NotificationFactory.java` ligne 15-28 | [Refactoring Guru](https://refactoring.guru/fr/design-patterns/factory-method/java/example) |
| 4 | **Strategy** | `pattern/NotificationStrategy.java` + implémentations | [Refactoring Guru](https://refactoring.guru/fr/design-patterns/strategy/java/example) |
| 5 | **Repository** | `repository/BookRepository.java` + implémentations | [Java et Moi](https://javaetmoi.com/2013/03/17/le-pattern-repository/) |
| 6 | **Service Layer** | `service/BookService.java`, `LoanService.java`, `UserService.java` | [Baeldung](https://www.baeldung.com/java-enterprise-design-patterns#service-layer) |
| 7 | **DTO** | `dto/BookDTO.java`, `UserDTO.java`, `LoanDTO.java` | [Ippon](https://blog.ippon.fr/2018/04/17/dto-data-transfer-object/) |
| 8 | **Observer** | `service/LoanService.java` (Subject) + `service/NotificationService.java` (Observer) | [Refactoring Guru](https://refactoring.guru/fr/design-patterns/observer/java/example) |

---

## 📸 Captures d'écran

### Menu principal
```
╔════════════════════════════════════════════════════╗
║                    MENU PRINCIPAL                  ║
╠════════════════════════════════════════════════════╣
║  1. Afficher tous les livres                       ║
║  2. Afficher les livres disponibles                ║
║  3. Rechercher un livre par titre                  ║
║  ...                                               ║
╚════════════════════════════════════════════════════╝
```

### Notification (Pattern Observer)
```
╔════════════════════════════════════════════════════╗
║          📢 NOTIFICATION CONSOLE                  ║
╠════════════════════════════════════════════════════╣
║ Destinataire : jean.dupont@email.com
╠════════════════════════════════════════════════════╣
║ 📚 Emprunt créé avec succès !
║ Livre : 1984
║ Emprunteur : Jean Dupont
║ Date de retour : 02/12/2025
╚════════════════════════════════════════════════════╝
```

### Changement de stratégie de notification
```
🔔 CHANGER LE MODE DE NOTIFICATION
──────────────────────────────────────────────────
1. Console (affichage dans le terminal)
2. Email (simulation d'envoi d'email)

Votre choix : 2
✅ Mode de notification changé : email
```

---

## 🎓 Explications des patterns

### Pattern Observer en action
Lorsqu'un emprunt est créé (ligne 66 de `LoanService.java`), le service notifie automatiquement tous les observateurs enregistrés. Le `NotificationService` reçoit l'événement et déclenche une notification selon la stratégie active (console ou email).

### Pattern Strategy + Factory
Le `NotificationFactory` crée dynamiquement la bonne stratégie de notification. L'utilisateur peut changer de stratégie en temps réel sans modifier le code.

### Pattern Builder
La création d'un livre utilise le Builder pattern pour une syntaxe claire :
```java
Book book = new Book.Builder()
    .isbn("123")
    .title("Mon Livre")
    .author("Auteur")
    .build();
```

---

## 👨‍💻 Auteur

**Victor Hubert** - M1 DEVFLSK  
Projet réalisé dans le cadre du cours d'Architecture Logicielle

---

## 📚 Ressources

- [Refactoring Guru - Design Patterns](https://refactoring.guru/fr/design-patterns)
- [Baeldung - Enterprise Patterns](https://www.baeldung.com/java-enterprise-design-patterns)
- Documentation du projet disponible dans les commentaires du code