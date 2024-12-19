package com.example.booknook
import org.springframework.stereotype.Service
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.oauth2.jwt.Jwt
import com.example.booknook.RequeteMalFormuleeException
import com.example.booknook.ConflitAvecUneRessourceExistanteException

@Service
class UtilisateursService(private val utilisateursDAO: UtilisateursDAOImpl){
    @PreAuthorize("hasAuthority('lister:utilisateurs')")
    fun obtenirUtilisateurs(): List<Utilisateurs> = utilisateursDAO.chercherTous()
    @PreAuthorize("hasAuthority('consulter:utilisateurs')")
    fun obtenirUtilisateurParId(id: Int, jeton: Jwt): Utilisateurs? {
        val utilisateur=utilisateursDAO.chercherParId(id) ?: throw RessourceInexistanteException("L'utilisateur  est inexistant dans le système")
        var loginAuthentification = jeton.claims["email"] as? String ?: throw OperationNonAutoriseeException("Votre jeton d'accès ne contient pas les éléments nécesssaires à la création d'un profil de joueur")
        if (utilisateur.login != loginAuthentification && loginAuthentification!="admin@booknook.qc.ca") {
            throw OperationNonAutoriseeException("Vous n'êtes pas autorisé à voir les profls des autres utilisateurs.")
        }
        return utilisateur
    }
    @PreAuthorize("hasAuthority('lister:utilisateurs')")
    fun obtenirUtilisateurParNom(nom: String): Utilisateurs?=utilisateursDAO.chercherParNom(nom)
    @PreAuthorize("hasAuthority('ajouter:utilisateurs')")
    fun ajouterUtilisateur(utilisateur: Utilisateurs, jeton: Jwt): Utilisateurs? {
        var loginAuthentification = jeton.claims["email"] as? String ?: throw OperationNonAutoriseeException("Votre jeton d'accès ne contient pas les éléments nécesssaires à la création d'un profil de joueur")
        val login=utilisateur.login
        if(utilisateursDAO.chercherParLogin(login) != null) throw ConflitAvecUneRessourceExistanteException("Un profil de joueur existe déjà pour l'adresse $login.")
        
        val nouvelUtlisateur = utilisateursDAO.ajouter(utilisateur)

        if(nouvelUtlisateur == null) throw RequeteMalFormuleeException("Le joueur ${utilisateur.nom} n'a pas pu être créé.")
        return nouvelUtlisateur
    }
    @PreAuthorize("hasAuthority('modifier:utilisateurs')")
    fun modifierUtilisateur(id: Int, utilisateur: Utilisateurs, jeton: Jwt): Utilisateurs? {
        val loginAuthentification = jeton.claims["email"] as? String 
            ?: throw OperationNonAutoriseeException("Votre jeton d'accès ne contient pas les éléments nécessaires à la modification d'un profil utilisateur.")
        val utilisateurExistant = utilisateursDAO.chercherParId(id)
            ?: throw ConflitAvecUneRessourceExistanteException("Aucun profil utilisateur trouvé avec l'identifiant $id.")
        if (utilisateurExistant.login != loginAuthentification && loginAuthentification!="admin@booknook.qc.ca") {
            throw OperationNonAutoriseeException("Vous n'êtes pas autorisé à modifier ce profil utilisateur.")
        }
        if (utilisateur.login != null && utilisateur.login != loginAuthentification && loginAuthentification!="admin@booknook.qc.ca") {
            throw RequeteMalFormuleeException("Le champ 'login' doit correspondre à l'adresse courriel utilisée pour l'authentification.")
        }
        val utilisateurModifie = utilisateursDAO.modifier(id, utilisateur)
            ?: throw RequeteMalFormuleeException("Le profil utilisateur n'a pas pu être modifié.")

        return utilisateurModifie
    }
    @PreAuthorize("hasAuthority('supprimer:utilisateurs')")
    fun supprimerUtilisateur(id: Int){
        if(id<=0){
            throw BadRequestException("L'id $id n'est pas dans un format valide.")
        }
        utilisateursDAO.effacer(id) ?: throw RessourceInexistanteException("L'utilisateur  est inexistant dans le système")
    }
}