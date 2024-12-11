package com.example.booknook
import org.springframework.stereotype.Service
import org.springframework.security.access.prepost.PreAuthorize

@Service
class UtilisateursService(private val utilisateursDAO: UtilisateursDAOImpl){
    @PreAuthorize("hasAuthority('consulter:utilisateurs')")
    fun obtenirUtilisateurs(): List<Utilisateurs> = utilisateursDAO.chercherTous()
    @PreAuthorize("hasAuthority('consulter:utilisateurs')")
    fun obtenirUtilisateurParId(id: Int): Utilisateurs? {
        if(id<=0){
            throw BadRequestException("L'id $id n'est pas dans un format valide.")
        }
        return utilisateursDAO.chercherParId(id) ?: throw RessourceInexistanteException("L'utilisateur  est inexistant dans le système")
    }
    @PreAuthorize("hasAuthority('consulter:utilisateurs')")
    fun obtenirUtilisateurParNom(nom: String): Utilisateurs?=utilisateursDAO.chercherParNom(nom)
    @PreAuthorize("hasAuthority('ajouter:utilisateurs')")
    fun ajouterUtilisateur(utilisateur: Utilisateurs): Utilisateurs?=utilisateursDAO.ajouter(utilisateur)
    @PreAuthorize("hasAuthority('modifier:utilisateurs')")
    fun modifierUtilisateur(id: Int, utilisateur: Utilisateurs): Utilisateurs?{
        if(id<=0){
            throw BadRequestException("L'id $id n'est pas dans un format valide.")
        }
        return utilisateursDAO.modifier(id, utilisateur) ?: throw RessourceInexistanteException("L'utilisateur  est inexistant dans le système")
    }
    @PreAuthorize("hasAuthority('supprimer:utilisateurs')")
    fun supprimerUtilisateur(id: Int){
        if(id<=0){
            throw BadRequestException("L'id $id n'est pas dans un format valide.")
        }
        utilisateursDAO.effacer(id) ?: throw RessourceInexistanteException("L'utilisateur  est inexistant dans le système")
    }
}