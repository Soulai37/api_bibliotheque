package com.example.booknook
import org.springframework.stereotype.Service
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.oauth2.jwt.Jwt
import com.example.booknook.RequeteMalFormuleeException
import com.example.booknook.ConflitAvecUneRessourceExistanteException

@Service
class EmpruntService(private val empruntDAO: EmpruntDAOImpl){
    @PreAuthorize("hasAuthority('lister:emprunts')")
    fun obtenirEmprunts(): List<Emprunt> = empruntDAO.chercherTous()
    @PreAuthorize("hasAuthority('consulter:emprunts')")
    fun obtenirEmpruntParId(id: Int, jeton: Jwt): Emprunt? {
        var loginAuthentification = jeton.claims["email"] as? String ?: throw OperationNonAutoriseeException("Votre jeton d'accès ne contient pas les éléments nécesssaires à la création d'un profil de joueur")
        val emprunt=empruntDAO.chercherParId(id)
        if (emprunt!!.utilisateur.login != loginAuthentification && loginAuthentification!="admin@booknook.qc.ca") {
            throw OperationNonAutoriseeException("Vous n'êtes pas autorisé à voir les emprunts des autres utilisateurs.")
        }
        return emprunt
    }
    @PreAuthorize("hasAuthority('consulter:emprunts')")
    fun obtenirEmpruntParNomLivre(nom: String, jeton: Jwt): Emprunt? {
        var loginAuthentification = jeton.claims["email"] as? String ?: throw OperationNonAutoriseeException("Votre jeton d'accès ne contient pas les éléments nécesssaires à la création d'un profil de joueur")
        val emprunt=empruntDAO.chercherParNom(nom)
        if (emprunt!!.utilisateur.login != loginAuthentification && loginAuthentification!="admin@booknook.qc.ca") {
            throw OperationNonAutoriseeException("Vous n'êtes pas autorisé à voir les emprunts des autres utilisateurs.")
        }
        return emprunt
    }
    @PreAuthorize("hasAuthority('consulter:emprunts')")
    fun obtenirEmpruntParNomUtilisateur(nom: String, jeton: Jwt): List<Emprunt> {
        var loginAuthentification = jeton.claims["email"] as? String ?: throw OperationNonAutoriseeException("Votre jeton d'accès ne contient pas les éléments nécesssaires à la création d'un profil de joueur")
        val emprunt=empruntDAO.chercherParNomUtilisateur(nom)
        if (emprunt[0].utilisateur.login != loginAuthentification && loginAuthentification!="admin@booknook.qc.ca") {
            throw OperationNonAutoriseeException("Vous n'êtes pas autorisé à voir les emprunts des autres utilisateurs.")
        }
        return emprunt
    }
    fun ajouterEmprunt(emprunt: Emprunt, jeton: Jwt): Emprunt? {
        var loginAuthentification = jeton.claims["email"] as? String ?: throw OperationNonAutoriseeException("Votre jeton d'accès ne contient pas les éléments nécesssaires à la création d'un profil de joueur")
        if (emprunt!!.utilisateur.login != loginAuthentification && loginAuthentification!="admin@booknook.qc.ca") {
            throw OperationNonAutoriseeException("Vous n'êtes pas autorisé à modifier les emprunts des autres utilisateurs.")
        }
        if(emprunt.date_retour<emprunt.date_emprunt){
            throw RequeteMalFormuleeException("Date de retour ne peut pas être inférieure à la date d'emprunt")
        }
        if (emprunt.livre.quantite <= 0) {
            throw RequeteMalFormuleeException("Le livre ${emprunt.livre.nom} n'est pas disponible, la quantité est épuisée.")
        }
        val addedEmprunt = empruntDAO.ajouter(emprunt)

        if (addedEmprunt != null) {
            empruntDAO.decrementerQuantite(emprunt.livre.isbn)
        }
        return addedEmprunt
    }

    /*fun ajouterEmprunt(emprunt: Emprunt, jeton: Jwt): Emprunt? {
        var loginAuthentification = jeton.claims["email"] as? String ?: throw OperationNonAutoriseeException("Votre jeton d'accès ne contient pas les éléments nécesssaires à la création d'un profil de joueur")
        if (emprunt!!.utilisateur.login != loginAuthentification && loginAuthentification!="admin@booknook.qc.ca") {
            throw OperationNonAutoriseeException("Vous n'êtes pas autorisé à modifier les emprunts des autres utilisateurs.")
        }
        if(emprunt.date_retour<emprunt.date_emprunt){
            throw RequeteMalFormuleeException("Date de retour ne peut pas être inférieure à la date d'emprunt")
        }
        return empruntDAO.ajouter(emprunt)
    } */
    @PreAuthorize("hasAuthority('modifier:emprunts')")
    fun modifierEmprunt(id: Int, emprunt: Emprunt, jeton: Jwt): Emprunt?{
        var loginAuthentification = jeton.claims["email"] as? String ?: throw OperationNonAutoriseeException("Votre jeton d'accès ne contient pas les éléments nécesssaires à la création d'un profil de joueur")
        if (emprunt!!.utilisateur.login != loginAuthentification && loginAuthentification!="admin@booknook.qc.ca") {
            throw OperationNonAutoriseeException("Vous n'êtes pas autorisé à modifier les emprunts des autres utilisateurs.")
        }
        if(emprunt.date_retour<emprunt.date_emprunt){
            throw RequeteMalFormuleeException("Date de retour ne peut pas être inférieure à la date d'emprunt")
        }
        return empruntDAO.modifier(id, emprunt)
    }
    @PreAuthorize("hasAuthority('supprimer:emprunts')")
    fun supprimerEmprunt(id: Int, jeton: Jwt) {
        val emprunt=empruntDAO.chercherParId(id)
        var loginAuthentification = jeton.claims["email"] as? String ?: throw OperationNonAutoriseeException("Votre jeton d'accès ne contient pas les éléments nécesssaires à la création d'un profil de joueur")
        if (emprunt!!.utilisateur.login != loginAuthentification && loginAuthentification!="admin@booknook.qc.ca") {
            throw OperationNonAutoriseeException("Vous n'êtes pas autorisé à modifier les emprunts des autres utilisateurs.")
        }
           return empruntDAO.effacer(id) ?: throw RessourceInexistanteException("L'emprunt est inexistant dans le système")
    }
}