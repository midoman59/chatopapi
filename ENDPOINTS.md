# Documentation des Endpoints API - Chatop

Cette documentation détaille tous les endpoints implémentés dans l'API Chatop, en conformité avec le modèle Mockoon fourni.

## 📊 Récapitulatif des endpoints

| # | Méthode | Route | Authentification | Description |
|---|---------|-------|------------------|-------------|
| 1 | POST | `/api/auth/register` | ❌ Non | Enregistrement d'un nouvel utilisateur |
| 2 | POST | `/api/auth/login` | ❌ Non | Connexion d'un utilisateur |
| 3 | GET | `/api/auth/me` | ✅ Oui (JWT) | Récupération de l'utilisateur authentifié |
| 4 | GET | `/api/rentals` | ✅ Oui (JWT) | Liste de toutes les locations |
| 5 | GET | `/api/rentals/{id}` | ✅ Oui (JWT) | Récupération d'une location par ID |
| 6 | POST | `/api/rentals` | ✅ Oui (JWT) | Création d'une nouvelle location |
| 7 | PUT | `/api/rentals/{id}` | ✅ Oui (JWT) | Modification d'une location |
| 8 | GET | `/api/user/{id}` | ✅ Oui (JWT) | Récupération des infos d'un utilisateur |
| 9 | POST | `/api/messages` | ✅ Oui (JWT) | Envoi d'un message |

---

## 🔐 Endpoint 1 : Enregistrement

### Requête
```http
POST /api/auth/register
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "email": "user@example.com",
  "name": "John Doe",
  "password": "secure_password"
}
```

### Réponse - Succès (200 OK)
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjgwMDAwMDAwLCJleHAiOjE2ODAwMDM2MDB9.SIGNATURE"
}
```

### Réponse - Erreur Email déjà utilisé (400 Bad Request)
```json
{
  "message": "Email already used"
}
```

### Réponse - Erreur Validation (400 Bad Request)
```json
{
  "message": "Validation error",
  "errors": {
    "email": "Email must be valid",
    "name": "Name is required",
    "password": "Password is required"
  }
}
```

### Codes HTTP
- `200` : Enregistrement réussi, token retourné
- `400` : Email déjà existant ou données invalides
- `500` : Erreur serveur

---

## 🔐 Endpoint 2 : Connexion

### Requête
```http
POST /api/auth/login
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "email": "user@example.com",
  "password": "secure_password"
}
```

### Réponse - Succès (200 OK)
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjgwMDAwMDAwLCJleHAiOjE2ODAwMDM2MDB9.SIGNATURE"
}
```

### Réponse - Erreur Utilisateur non trouvé (401 Unauthorized)
```json
{
  "message": "User not found"
}
```

### Réponse - Erreur Mot de passe incorrect (401 Unauthorized)
```json
{
  "message": "Invalid password"
}
```

### Codes HTTP
- `200` : Connexion réussie, token retourné
- `401` : Utilisateur non trouvé ou mot de passe incorrect
- `500` : Erreur serveur

---

## 👤 Endpoint 3 : Utilisateur authentifié (GET /api/auth/me)

### Requête
```http
GET /api/auth/me
Authorization: Bearer <jwt_token>
```

### Réponse - Succès (200 OK)
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "user@example.com",
  "created_at": "2024-01-15",
  "updated_at": "2024-01-15"
}
```

### Réponse - Erreur Non authentifié (401 Unauthorized)
```json
{
  "message": "Pas d'authentification valide trouvée"
}
```

### Codes HTTP
- `200` : Utilisateur récupéré avec succès
- `401` : Token manquant, invalide ou expiré
- `500` : Erreur serveur

---

## 🏠 Endpoint 4 : Liste des locations

### Requête
```http
GET /api/rentals
Authorization: Bearer <jwt_token>
```

### Réponse - Succès (200 OK)
```json
{
  "rentals": [
    {
      "id": 1,
      "name": "Appartement luxe",
      "surface": 120.50,
      "price": 1500.00,
      "picture": "http://localhost:3001/uploads/1234567890_apartment.jpg",
      "description": "Bel appartement en centre-ville",
      "owner_id": 1,
      "created_at": "2024-01-15T10:00:00",
      "updated_at": "2024-01-15T10:00:00"
    },
    {
      "id": 2,
      "name": "Maison côtière",
      "surface": 250.00,
      "price": 2500.00,
      "picture": "http://localhost:3001/uploads/1234567891_house.jpg",
      "description": "Magnifique maison avec vue sur mer",
      "owner_id": 2,
      "created_at": "2024-01-16T14:30:00",
      "updated_at": "2024-01-16T14:30:00"
    }
  ]
}
```

### Réponse - Erreur Non authentifié (401 Unauthorized)
```json
{
  "message": "Pas d'authentification valide trouvée"
}
```

### Codes HTTP
- `200` : Liste réussie
- `401` : Token manquant, invalide ou expiré
- `500` : Erreur serveur

---

## 🏠 Endpoint 5 : Récupération d'une location par ID

### Requête
```http
GET /api/rentals/{id}
Authorization: Bearer <jwt_token>
```

**Paramètre URL:**
- `id` (integer, obligatoire) : Identifiant unique de la location

### Réponse - Succès (200 OK)
```json
{
  "id": 1,
  "name": "Appartement luxe",
  "surface": 120.50,
  "price": 1500.00,
  "picture": "http://localhost:3001/uploads/1234567890_apartment.jpg",
  "description": "Bel appartement en centre-ville",
  "owner_id": 1,
  "created_at": "2024-01-15T10:00:00",
  "updated_at": "2024-01-15T10:00:00"
}
```

### Réponse - Erreur Non trouvée (404 Not Found)
```json
{
  "message": "Rental introuvable"
}
```

### Réponse - Erreur Non authentifié (401 Unauthorized)
```json
{
  "message": "Pas d'authentification valide trouvée"
}
```

### Codes HTTP
- `200` : Location récupérée avec succès
- `401` : Token manquant, invalide ou expiré
- `404` : Location non trouvée
- `500` : Erreur serveur

---

## 🏠 Endpoint 6 : Création d'une location

### Requête
```http
POST /api/rentals
Content-Type: multipart/form-data
Authorization: Bearer <jwt_token>
```

**Body (Form-Data):**
- `name` (string, obligatoire) : Nom de la location
- `surface` (decimal, obligatoire) : Surface en m²
- `price` (decimal, obligatoire) : Prix en €
- `picture` (file, obligatoire) : Image de la location (PNG/JPEG)
- `description` (string, obligatoire) : Description de la location

**Exemple CURL:**
```bash
curl -X POST http://localhost:3001/api/rentals \
  -H "Authorization: Bearer <jwt_token>" \
  -F "name=Appartement luxe" \
  -F "surface=120.50" \
  -F "price=1500.00" \
  -F "picture=@apartment.jpg" \
  -F "description=Bel appartement en centre-ville"
```

### Réponse - Succès (200 OK)
```json
{
  "message": "Rental created !"
}
```

### Réponse - Erreur Données invalides (400 Bad Request)
```json
{
  "message": "Données manquantes ou invalides"
}
```

### Réponse - Erreur Non authentifié (401 Unauthorized)
```json
{
  "message": "Pas d'authentification valide trouvée"
}
```

### Codes HTTP
- `200` : Location créée avec succès
- `400` : Données invalides ou manquantes
- `401` : Token manquant, invalide ou expiré
- `500` : Erreur serveur

---

## 🏠 Endpoint 7 : Modification d'une location

### Requête
```http
PUT /api/rentals/{id}
Content-Type: multipart/form-data
Authorization: Bearer <jwt_token>
```

**Paramètre URL:**
- `id` (integer, obligatoire) : Identifiant de la location

**Body (Form-Data):** (identique à la création)
- `name` (string, obligatoire) : Nom de la location
- `surface` (decimal, obligatoire) : Surface en m²
- `price` (decimal, obligatoire) : Prix en €
- `picture` (file, optionnel) : Nouvelle image de la location
- `description` (string, obligatoire) : Description

### Réponse - Succès (200 OK)
```json
{
  "message": "Rental updated !"
}
```

### Réponse - Erreur Location non trouvée (404 Not Found)
```json
{
  "message": "Rental introuvable"
}
```

### Réponse - Erreur Non authentifié (401 Unauthorized)
```json
{
  "message": "Pas d'authentification valide trouvée"
}
```

### Codes HTTP
- `200` : Location mise à jour avec succès
- `400` : Données invalides
- `401` : Token manquant, invalide ou expiré
- `404` : Location non trouvée
- `500` : Erreur serveur

---

## 👤 Endpoint 8 : Récupération d'un utilisateur

### Requête
```http
GET /api/user/{id}
Authorization: Bearer <jwt_token>
```

**Paramètre URL:**
- `id` (integer, obligatoire) : Identifiant unique de l'utilisateur

### Réponse - Succès (200 OK)
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "user@example.com",
  "created_at": "2024-01-15",
  "updated_at": "2024-01-15"
}
```

### Réponse - Erreur Utilisateur non trouvé (401 Unauthorized)
```json
{
  "message": "Utilisateur introuvable"
}
```

### Réponse - Erreur Non authentifié (401 Unauthorized)
```json
{
  "message": "Pas d'authentification valide trouvée"
}
```

### Codes HTTP
- `200` : Utilisateur récupéré avec succès
- `401` : Token manquant, invalide ou expiré ou utilisateur non trouvé
- `500` : Erreur serveur

---

## 💬 Endpoint 9 : Envoi d'un message

### Requête
```http
POST /api/messages
Content-Type: application/json
Authorization: Bearer <jwt_token>
```

**Body (JSON):**
```json
{
  "user_id": 1,
  "rental_id": 2,
  "message": "Bonjour, je suis intéressé par cette location. Quand est-elle disponible ?"
}
```

**Paramètres:**
- `user_id` (integer, obligatoire) : ID de l'utilisateur qui envoie le message
- `rental_id` (integer, obligatoire) : ID de la location concernée
- `message` (string, obligatoire) : Contenu du message

### Réponse - Succès (200 OK)
```json
{
  "message": "Message send with success"
}
```

### Réponse - Erreur Utilisateur non trouvé (404 Not Found / 401 Unauthorized)
```json
{
  "message": "Utilisateur introuvable"
}
```

### Réponse - Erreur Location non trouvée (404 Not Found / 500 Internal Server Error)
```json
{
  "message": "Rental introuvable"
}
```

### Réponse - Erreur Non authentifié (401 Unauthorized)
```json
{
  "message": "Pas d'authentification valide trouvée"
}
```

### Codes HTTP
- `200` : Message envoyé avec succès
- `400` : Données manquantes ou invalides
- `401` : Token manquant, invalide ou expiré
- `404` : Utilisateur ou location non trouvé(e)
- `500` : Erreur serveur

---

## 🔑 Authentification JWT

### Format du token
```
Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjgwMDAwMDAwLCJleHAiOjE2ODAwMDM2MDB9.SIGNATURE
```

### Durée de vie
- **Expiration** : 1 heure (3600000 ms)

### Utilisation
Tous les endpoints protégés (marqués avec ✅) requièrent l'en-tête :
```http
Authorization: Bearer <token>
```

Si le token est absent, invalide ou expiré, le serveur retourne une erreur `401 Unauthorized`.

---

## 📸 Images et stockage

### Chemin de stockage
- Les images sont sauvegardées dans le répertoire `./uploads`
- Les fichiers sont renommés avec un timestamp pour éviter les collisions : `{timestamp}_{original_filename}`

### Format URL retourné
```
http://localhost:3001/uploads/1234567890_apartment.jpg
```

### Formats supportés
- PNG
- JPEG/JPG
- Autres formats image standard (configurables)

---

## 🔄 Codes d'erreur globaux

| Code | Signification | Exemple |
|------|---------------|---------|
| `200` | Succès | Opération réussie |
| `400` | Bad Request | Données invalides, champs manquants |
| `401` | Unauthorized | Token manquant, invalide, expiré ou authentification échouée |
| `404` | Not Found | Ressource (user, rental) non trouvée |
| `500` | Internal Server Error | Erreur serveur interne |

---

## 🧪 Tester avec Swagger UI

L'API dispose d'une interface Swagger interactive pour tester les endpoints :

**URL** : `http://localhost:3001/swagger-ui.html`

### Procédure de test
1. Accédez à Swagger UI
2. Appelez `POST /api/auth/login` pour obtenir un token
3. Cliquez sur le bouton **Authorize** et saisissez le token
4. Testez les endpoints protégés directement dans l'interface

---

## 📝 Notes importantes

- Tous les timestamps sont au format ISO 8601 (UTC)
- Les prix et surfaces sont des décimaux avec 2 décimales
- Les emails doivent être uniques en base de données
- Les mots de passe sont hashés avec BCrypt (jamais stockés en clair)
- Les variables d'environnement doivent être configurées avant le lancement
- Le port par défaut est `3001`

