# SAE 2.03 - Serveur de Partage de Fichiers (Java & Docker)

## Membre équipe B3

FERRIER Mathys [C]

DOUILLARD Axel [A]

DUPONT Armand [A]

## Description
Ce projet est une application de partage de fichiers en réseau local basée sur une architecture Client/Serveur via Sockets. Il permet de transformer une machine (ou un conteneur) en serveur de stockage, accessible par des clients graphiques pour l'envoi et la récupération de documents.

L'objectif principal est la mise en place d'un service réseau robuste, isolé dans un environnement Docker et piloté par une interface Java Swing.

## Fonctionnalités

### Fonctionnalités implémentées
* Liste des fichiers : Consultation en temps réel des fichiers présents sur le serveur.
* Upload (Envoi) : Transfert de fichiers du PC client vers le serveur.
* Download (Réception) : Téléchargement de fichiers du serveur vers le dossier Downloads de l'utilisateur.
* Drag & Drop : Support du glisser-déposer directement dans l'interface pour l'envoi de fichiers.
* Multi-PC : Capacité de connexion entre deux machines physiques distinctes sur le même réseau local.

### Aspects Techniques
* Architecture Sockets : Communication via flux de données (DataInputStream / DataOutputStream).
* Multithreading : Gestion de plusieurs requêtes simultanées sur le serveur.
* Persistance : Utilisation de volumes Docker pour conserver les fichiers même après l'arrêt du conteneur.

## Architecture du projet

Le projet est décomposé en services clairs :
* Serveur (Backend) : Application Java tournant sous Debian Stable (Docker) gérant les entrées/sorties fichiers.
* Client (IHM) : Interface graphique Swing permettant de piloter les échanges.
* Stockage : Volume synchronisé entre l'hôte (Windows) et le conteneur (Linux) via le dossier partage/.

### Structure des dossiers
```text
.
├── backend/
├── IHM/
├── partage/
├── LanceurClient.java
├── Dockerfile
└── README.md
Installation et Utilisation
Prérequis
Docker Desktop (avec moteur WSL 2 activé)

Java JDK 17+ (pour le client local)

Déploiement avec Docker (Recommandé)
Lancer le serveur :
docker start -a mon-serveur

Lancer l'interface client (PC 2 ou local) :
javac IHM/*.java backend/*.java
java LanceurClient

Configuration Réseau
Port par défaut : 8080

Connexion distante : Pour connecter deux PC, remplacez localhost dans l'IHM par l'adresse IPv4 du PC hébergeant le serveur.

Développement et Git
Le projet a suivi une méthodologie de développement par branches :

main

gh-pages

ihm 

client 

storage-volume

java-serveur

docker

Objectifs Pédagogiques
Maîtrise des protocoles de communication TCP/IP (Sockets).

Apprentissage de la conteneurisation applicative avec Docker.

Développement d'interfaces événementielles en Java.

Gestion de flux de données binaires et persistance.

