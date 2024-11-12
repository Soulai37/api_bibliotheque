package com.example.booknook
import org.springframework.stereotype.Service

@Service
class UtilisateursService(private val utilisateursDAO: UtilisateursDAOImpl){
    fun obtenirUtilisateurs(): List<Utilisateurs> = utilisateursDAO.chercherTous()
    fun obtenirUtilisateurParId(id: Int): Utilisateurs?=utilisateursDAO.chercherParId(id)
    fun obtenirUtilisateurParNom(nom: String): Utilisateurs?=utilisateursDAO.chercherParNom(nom)
    fun ajouterUtilisateur(utilisateur: Utilisateurs): Utilisateurs?=utilisateursDAO.ajouter(utilisateur)
    fun modifierUtilisateur(id: Int, utilisateur: Utilisateurs): Utilisateurs?=utilisateursDAO.modifier(id, utilisateur)
    fun supprimerUtilisateur(id: Int)=utilisateursDAO.effacer(id)
}