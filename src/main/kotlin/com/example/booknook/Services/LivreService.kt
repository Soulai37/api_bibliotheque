package com.example.booknook
import org.springframework.stereotype.Service
import com.example.booknook.LivreDAO
import org.springframework.security.access.prepost.PreAuthorize

@Service
class LivreService(private val livreDAO: LivreDAOImp){
    fun obtenirLivres(): List<Livres> = livreDAO.chercherTous()
    fun verificationISBN(isbn:String): Boolean{
        if(!isbn.startsWith("ISBN") || isbn.length != 8 || !isbn.substring(4).all { it.isDigit() }){
            throw RequeteMalFormuleeException("L'isbn $isbn n'est pas dans un format valide.")
        }
        return true
    }
    fun obtenirLivreParIsbn(isbn: String): Livres? {
        verificationISBN(isbn) 
        return livreDAO.chercherParId(isbn) ?: throw RessourceInexistanteException("Le livre  est inexistant dans le système")
    }
    fun obtenirLivreParNom(nom: String): Livres?=livreDAO.chercherParNom(nom)
    fun obtenirLivreParGenre(genre: String):  List<Livres>?=livreDAO.chercherParGenre(genre)
    @PreAuthorize("hasAuthority('ajouter:livres')")
    fun ajouterLivre(livres: Livres): Livres?{
        val isbn = livres.isbn
        verificationISBN(isbn)
        return  livreDAO.ajouter(livres) 
        
    }
    @PreAuthorize("hasAuthority('modifier:livres')")
    fun modifierLivres(isbn: String, livres: Livres): Livres?{
        verificationISBN(isbn)
        val isbn2 = livres.isbn
        verificationISBN(isbn2)
        obtenirLivreParIsbn(isbn)
        return livreDAO.modifier(isbn, livres) ?: throw RessourceInexistanteException("Le livre  est inexistant dans le système")
    }
    @PreAuthorize("hasAuthority('supprimer:livres')")
    fun supprimerLivres(isbn: String){
        verificationISBN(isbn)
        obtenirLivreParIsbn(isbn)
        return livreDAO.effacer(isbn) ?: throw RessourceInexistanteException("Le livre  est inexistant dans le système")
    }
}