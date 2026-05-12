# Chatop API

API de gestion de locations et de messages pour la plateforme Chatop.

## 📋 Table des matières

- [Installation](#installation)
- [Configuration](#configuration)
- [Lancement du projet](#lancement-du-projet)
- [Documentation API (Swagger)](#documentation-api-swagger)

---

## 🚀 Installation

### Prérequis

- **Java 17** ou supérieur
- **Maven 3.6** ou supérieur
- **MySQL 8** ou supérieur
- **Git** (optionnel)

### Étapes d'installation

1. **Cloner le repository** (si applicable)
   ```bash
   git clone <your-repository-url>
   cd chatopapi
   ```

2. **Installer les dépendances Maven**
   ```bash
   mvn clean install
   ```

   Ou sur Windows directement :
   ```bash
   mvnw clean install
   ```

---

## 🔧 Configuration

### 1. Configuration de la base de données

L'application utilise MySQL pour stocker les données. Vous devez configurer les variables d'environnement suivantes :

#### Variables d'environnement requises

Créez un fichier `.env` à la racine du projet ou configurez les variables système :

```
DB_URL=jdbc:mysql://localhost:3306/chatop
DB_USER=root
DB_PASS=votre_mot_de_passe
SECRET_KEY=votre_clé_secrète_jwt
```

#### Sur Windows (PowerShell)

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/chatop"
$env:DB_USER="root"
$env:DB_PASS="votre_mot_de_passe"
$env:SECRET_KEY="votre_clé_secrète_jwt"
```

#### Sur Windows (Command Prompt)

```cmd
set DB_URL=jdbc:mysql://localhost:3306/chatop
set DB_USER=root
set DB_PASS=votre_mot_de_passe
set SECRET_KEY=votre_clé_secrète_jwt
```

#### Sur Linux/Mac (Bash)

```bash
export DB_URL="jdbc:mysql://localhost:3306/chatop"
export DB_USER="root"
export DB_PASS="votre_mot_de_passe"
export SECRET_KEY="votre_clé_secrète_jwt"
```

### 2. Créer la base de données MySQL

Connectez-vous à MySQL et exécutez :

```Mysql
CREATE TABLE `users` (
                        `id` INT PRIMARY KEY AUTO_INCREMENT,
                        `email` VARCHAR(255) NOT NULL,
                        `name` VARCHAR(255) NOT NULL,
                        `password` VARCHAR(255) NOT NULL,
                        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `rentals` (
                          `id` INT PRIMARY KEY AUTO_INCREMENT,
                          `name` VARCHAR(255) NOT NULL,
                          `surface` DECIMAL(10,2),
                          `price` DECIMAL(10,2),
                          `picture` VARCHAR(255),
                          `description` VARCHAR(2000),
                          `owner_id` INT NOT NULL,
                          `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `messages` (
                           `id` INT PRIMARY KEY AUTO_INCREMENT,
                           `rental_id` INT,
                           `user_id` INT,
                           `message` VARCHAR(2000),
                           `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX `users_email_unique` ON `users` (`email`);

ALTER TABLE `rentals`
   ADD CONSTRAINT `fk_rentals_owner`
      FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
         ON DELETE CASCADE;

ALTER TABLE `messages`
   ADD CONSTRAINT `fk_messages_user`
      FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
         ON DELETE CASCADE;

ALTER TABLE `messages`
   ADD CONSTRAINT `fk_messages_rental`
      FOREIGN KEY (`rental_id`) REFERENCES `rentals` (`id`)
         ON DELETE CASCADE;
```

L'application crée automatiquement les tables via JPA Hibernate. Vérifiez que `spring.jpa.hibernate.ddl-auto` est configuré correctement dans `application.properties`.

### 2b. Créer un utilisateur MySQL avec permissions limitées (recommandé pour la sécurité)

⚠️ **Important** : Ne pas utiliser le compte root en production. Créez un utilisateur dédié avec permissions limitées.

#### Créer l'utilisateur et la base de données

Connectez-vous à MySQL en tant que root :

```bash
mysql -u root -p
```

Exécutez les commandes suivantes :

```sql
-- Créer la base de données
CREATE DATABASE IF NOT EXISTS chatop;

-- Créer un utilisateur dédié (remplacez `secure_password` par un mot de passe sécurisé)
CREATE USER 'chatop_user'@'localhost' IDENTIFIED BY 'secure_password';

-- Accorder les permissions nécessaires UNIQUEMENT sur la base chatop
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, DROP, INDEX ON chatop.* TO 'chatop_user'@'localhost';

-- Appliquer les modifications
FLUSH PRIVILEGES;

-- Vérifier la création
SHOW GRANTS FOR 'chatop_user'@'localhost';
```

#### Configurer les variables d'environnement avec cet utilisateur

Mettez à jour vos variables d'environnement :

```
DB_URL=jdbc:mysql://localhost:3306/chatop
DB_USER=chatop_user
DB_PASS=secure_password
SECRET_KEY=votre_clé_secrète_jwt
```

### 3. Configuration du dossier d'uploads

L'application stocke les images des locations dans le dossier `./uploads`. Assurez-vous que ce dossier existe :

```bash
mkdir uploads
```

---

## 🎯 Lancement du projet

### Avec Maven

```bash
mvn spring-boot:run
```

Ou sur Windows avec le wrapper Maven :

```bash
mvnw spring-boot:run
```

### Avec Java directement (après compilation)

```bash
java -jar target/chatopapi-0.0.1-SNAPSHOT.jar
```

### Dans un IDE (IntelliJ IDEA, Eclipse, VS Code)

1. Importez le projet Maven
2. Configurez les variables d'environnement
3. Exécutez `ChatopapiApplication.java` en tant que classe principale

---

## 📚 Documentation API (Swagger)

L'API utilise **Swagger/OpenAPI 3.0** pour la documentation interactive.

### Accéder à la documentation

Une fois l'application lancée :

- **Swagger UI** : http://localhost:3001/swagger-ui.html
- **OpenAPI JSON** : http://localhost:3001/v3/api-docs
- **OpenAPI YAML** : http://localhost:3001/v3/api-docs.yaml

### Routes principales

### 🔐 Authentication (`/api/auth`)

| Méthode | Route | Description |
|---------|-------|-------------|
| POST | `/api/auth/register` | Enregistrer un nouvel utilisateur |
| POST | `/api/auth/login` | Connecter un utilisateur |
| GET | `/api/auth/me` | Récupérer l'utilisateur connecté (authentification requise) |

### 🏠 Locations (`/api/rentals`)

| Méthode | Route | Description |
|---------|-------|-------------|
| GET | `/api/rentals` | Lister toutes les locations (authentification requise) |
| GET | `/api/rentals/{id}` | Récupérer une location par ID (authentification requise) |
| POST | `/api/rentals` | Créer une nouvelle location (authentification requise) |
| PUT | `/api/rentals/{id}` | Mettre à jour une location (authentification requise) |

### 👤 Utilisateurs (`/api/user`)

| Méthode | Route | Description |
|---------|-------|-------------|
| GET | `/api/user/{id}` | Récupérer les informations d'un utilisateur (authentification requise) |

### 💬 Messages (`/api/messages`)

| Méthode | Route | Description |
|---------|-------|-------------|
| POST | `/api/messages` | Envoyer un message (authentification requise) |

---

## 🔒 Authentification

L'API utilise des tokens **JWT (JSON Web Token)** pour l'authentification.

### Obtenir un token

1. **S'enregistrer** :
   ```bash
   POST http://localhost:3001/api/auth/register
   Content-Type: application/json

   {
     "email": "user@example.com",
     "name": "John Doe",
     "password": "secure_password"
   }
   ```

2. **Se connecter** :
   ```bash
   POST http://localhost:3001/api/auth/login
   Content-Type: application/json

   {
     "email": "user@example.com",
     "password": "secure_password"
   }
   ```

### Utiliser le token

Incluez le token dans l'en-tête `Authorization` pour toutes les requêtes protégées :

```bash
GET http://localhost:3001/api/rentals
Authorization: Bearer <votre_token_jwt>
```

---

## 📝 Notes importantes

- Le port par défaut est **3001**
- Les images sont stockées dans `./uploads`
- Les tokens JWT expirent après **1 heure** (3600000 ms)
- L'application nécessite une connexion MySQL valide pour fonctionner
- Les variables d'environnement doivent être définies avant le lancement

---

## 🐛 Dépannage

### Erreur : "Cannot connect to database"
- Vérifiez que MySQL est en cours d'exécution
- Vérifiez les valeurs de `DB_URL`, `DB_USER`, et `DB_PASS`
- Vérifiez que la base de données `chatop` existe

### Erreur : "JWT secret is not configured"
- Vérifiez que la variable `SECRET_KEY` est définie
- Assurez-vous qu'elle n'est pas vide

### Port 3001 déjà utilisé
- Modifiez `server.port` dans `application.properties` ou définissez une variable d'environnement

---

## 📄 Licence

[Ajouter les informations de licence si applicable]

## 👥 Support

Pour toute question ou problème, veuillez contacter : support@chatop.com

