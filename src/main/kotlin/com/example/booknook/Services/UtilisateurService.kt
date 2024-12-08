package com.example.booknook
import org.springframework.stereotype.Service

@Service
class UtilisateursService(private val utilisateursDAO: UtilisateursDAOImpl){
    fun obtenirUtilisateurs(): List<Utilisateurs> = utilisateursDAO.chercherTous()
    fun obtenirUtilisateurParId(id: Int): Utilisateurs? {
        if(id<=0){
            throw BadRequestException("L'id $id n'est pas dans un format valide.")
        }
        return utilisateursDAO.chercherParId(id) ?: throw RessourceInexistanteException("L'utilisateur  est inexistant dans le système")
    }
    fun obtenirUtilisateurParNom(nom: String): Utilisateurs?=utilisateursDAO.chercherParNom(nom)
    fun ajouterUtilisateur(utilisateur: Utilisateurs): Utilisateurs?=utilisateursDAO.ajouter(utilisateur)
    fun modifierUtilisateur(id: Int, utilisateur: Utilisateurs): Utilisateurs?{
        if(id<=0){
            throw BadRequestException("L'id $id n'est pas dans un format valide.")
        }
        return utilisateursDAO.modifier(id, utilisateur) ?: throw RessourceInexistanteException("L'utilisateur  est inexistant dans le système")
    }
    fun supprimerUtilisateur(id: Int){
        if(id<=0){
            throw BadRequestException("L'id $id n'est pas dans un format valide.")
        }
        utilisateursDAO.effacer(id) ?: throw RessourceInexistanteException("L'utilisateur  est inexistant dans le système")
    }
}