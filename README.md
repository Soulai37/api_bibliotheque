# API de bibliothèque

## Description
Règles de la Bibliothèque API
1. Chaque utilisateur a un profil : Chaque utilisateur doit avoir un profil unique qui contient ses informations personnelles, y compris son nom, prénom, adresse e-mail, et historique d'emprunt.

2. Nature des requêtes : Les requêtes GET permettent d'accéder aux informations des livres et des utilisateurs. Les requêtes POST permettent aux utilisateurs d'emprunter et de retourner des livres. Les requêtes PUT sont utilisées pour mettre à jour l'état d'emprunt d'un livre. Les requêtes DELETE permettent aux utilisateurs d'annuler un emprunt ou de supprimer un livre de leur liste de souhaits.

3. Ce que l'administrateur peut faire : Accéder à toutes les informations des livres, y compris les livres empruntés et réservés. Créer de nouveaux utilisateurs.
Modifier les détails des utilisateurs existants, y compris leur rôle (administrateur, membre, etc.). Désactiver ou activer des utilisateurs. Gérer les emprunts et les retours de livres pour le compte des utilisateurs.

4. Ce que l'utilisateur peut faire : Emprunter des livres. Retourner des livres empruntés. Annuler un emprunt. Supprimer des livres de sa liste de souhaits. Accéder à ses propres livres empruntés, ainsi qu'aux livres disponibles.

5. Gestion de l'accès aux livres : Les utilisateurs peuvent uniquement emprunter des livres qui sont disponibles. Les livres peuvent être réservés par des utilisateurs, et ceux-ci doivent être informés de la disponibilité des livres réservés. Les administrateurs peuvent voir tous les livres, y compris ceux qui sont empruntés ou réservés, et gérer leur disponibilité.

6. Historique des emprunts : Chaque emprunt doit être enregistré, avec des détails tels que la date d'emprunt et la date de retour prévue. Les utilisateurs peuvent consulter leur historique d'emprunt.

## Modèles
Modèle Miro: https://miro.com/app/board/uXjVLdJEr-s=/?share_link_id=626855140138
## Installation

## Usage

## Auteurs et remerciements
Abdelhamid Aziz et Soulaimane Manaf
Le binôme Abdelhamid Aziz et Soulaimane Manaf pour la collection utilisateurs
# License
 API Bibliothèque © 2024 by Abdelhamid Aziz et Soulaimane Manaf is licensed under CC BY-NC-SA 4.0 