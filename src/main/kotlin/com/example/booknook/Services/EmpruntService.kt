package com.example.booknook
import org.springframework.stereotype.Service

@Service
class EmpruntService(private val empruntDAO: EmpruntDAOImpl){
    fun obtenirEmprunts(): List<Emprunt> = empruntDAO.chercherTous()
    fun obtenirEmpruntParId(id: Int): Emprunt? {
        if(id<=0){
            throw BadRequestException("L'id $id n'est pas dans un format valide.")
        }
        return empruntDAO.chercherParId(id) ?: throw RessourceInexistanteException("L'emprunt est inexistant dans le système")
    }
    fun obtenirEmpruntParNomLivre(nom: String): Emprunt?=empruntDAO.chercherParNom(nom)
    fun obtenirEmpruntParNomUtilisateur(nom: String): List<Emprunt> =empruntDAO.chercherParNomUtilisateur(nom)
    fun ajouterEmprunt(emprunt: Emprunt): Emprunt?=empruntDAO.ajouter(emprunt)
    fun modifierEmprunt(id: Int, emprunt: Emprunt): Emprunt?{
        if(id<=0){
            throw BadRequestException("L'id $id n'est pas dans un format valide.")
        }
        return empruntDAO.modifier(id, emprunt) ?: throw RessourceInexistanteException("L'emprunt est inexistant dans le système")
    }
    fun supprimerEmprunt(id: Int) {
        if(id <= 0) {
            throw BadRequestException("L'id $id n'est pas dans un format valide.")
        }
           return empruntDAO.effacer(id) ?: throw RessourceInexistanteException("L'emprunt est inexistant dans le système")
    }
}