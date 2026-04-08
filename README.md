# File Sharing Server

## Description

Ce projet est une application de **partage de fichiers via un serveur** permettant aux utilisateurs d’envoyer, stocker et télécharger des fichiers en ligne.

L’objectif est de mettre en place une architecture complète avec :

* un backend (API)
* un frontend (interface utilisateur)
* une base de données
* une conteneurisation avec Docker

---

## Fonctionnalités

### Fonctionnalités principales

* Upload de fichiers
* Téléchargement de fichiers
* Liste des fichiers disponibles

### Fonctionnalités avancées

* Authentification utilisateur (inscription / connexion)
* Fichiers publics / privés
* Suppression de fichiers
* Génération de liens de partage

### Bonus (optionnel)

* Drag & drop
* Barre de progression d’upload
* Expiration des fichiers
* Limitation de taille
* Historique des actions

---

## Architecture

Le projet est structuré en plusieurs services :

* **Frontend** : interface utilisateur (HTML/CSS/JS ou framework)
* **Backend** : API REST (Node.js / Python)
* **Base de données** : stockage des utilisateurs et métadonnées
* **Stockage fichiers** : volume Docker

---

## Structure du projet

```
/project
  /backend
  /frontend
  docker-compose.yml
  README.md
```

---

## Installation avec Docker

### Prérequis

* Docker
* Docker Compose

### Lancer le projet

```bash
docker-compose up --build
```

### Accès

* Frontend : http://localhost:8080
* Backend : http://localhost:3000

---

## Configuration

Les variables d’environnement peuvent être définies dans un fichier `.env` :

```
PORT=3000
DB_HOST=db
DB_USER=user
DB_PASSWORD=password
DB_NAME=filesharing
```

---

## Sécurité

* Vérification du type de fichier
* Limitation de taille des uploads
* Protection contre les noms de fichiers dangereux
* Authentification via token (JWT)

---

## Utilisation de Git

Bonnes pratiques utilisées :

* Branches par fonctionnalité (`feature/upload`, `feature/auth`)
* Commits clairs et structurés
* Historique propre

---

## Objectifs pédagogiques

Ce projet permet de travailler :

* Architecture client / serveur
* API REST
* Conteneurisation avec Docker
* Gestion des fichiers
* Sécurité des applications web

---

## Auteurs

Projet réalisé dans le cadre du BTS SIO.

---

## Aperçu (à compléter)

Ajouter ici des captures d’écran de l’application.

---

## Améliorations futures

* Interface plus moderne
* Système de dossiers
* Partage entre utilisateurs
* Déploiement en ligne

---
