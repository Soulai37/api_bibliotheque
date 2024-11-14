package com.example.booknook
import org.springframework.stereotype.Service
import com.example.booknook.LivreDAO

@Service
class LivreService(private val livreDAO: LivreDAOImp){
    fun obtenirLivres(): List<Livres> = livreDAO.chercherTous()
    fun obtenirLivreParIsbn(isbn: String): Livres? {
        if(!isbn.startsWith("ISBN") || isbn.length != 8 || !isbn.substring(4).all { it.isDigit() }){
            throw RequeteMalFormuleeException("L'isbn $isbn n'est pas dans un format valide.")
        }
        return livreDAO.chercherParIsbn(isbn)
    }
    fun obtenirLivreParNom(nom: String): Livres?=livreDAO.chercherParNom(nom)
    fun obtenirLivreParGenre(genre: String): Livres?=livreDAO.chercherParGenre(genre)
    fun ajouterLivre(isbn:String, livres: Livres): Livres?{
        if(!isbn.startsWith("ISBN") || isbn.length != 8 || !isbn.substring(4).all { it.isDigit() }){
        throw RequeteMalFormuleeException("L'isbn $isbn n'est pas dans un format valide.")
            }
        return  livreDAO.ajouter(livres)
    }

    fun modifierLivres(isbn: String, livres: Livres): Livres?{
        if(!isbn.startsWith("ISBN") || isbn.length != 8 || !isbn.substring(4).all { it.isDigit() }){
            throw RequeteMalFormuleeException("L'isbn $isbn n'est pas dans un format valide.")
        }
        return livreDAO.modifierL(isbn, livres)
    }

    fun supprimerLivres(isbn: String){
        if(!isbn.startsWith("ISBN") || isbn.length != 8 || !isbn.substring(4).all { it.isDigit() }){
            throw RequeteMalFormuleeException("L'isbn $isbn n'est pas dans un format valide.")
        }
        return livreDAO.effacerL(isbn)
    }
}