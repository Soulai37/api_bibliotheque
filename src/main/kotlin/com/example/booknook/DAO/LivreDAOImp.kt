package com.example.booknook

import org.springframework.stereotype.Repository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import com.example.booknook.Livres

@Repository
class LivreDAOImp(private val bd: JdbcTemplate): LivreDAO {
    
    override fun chercherTous(): List<Livres> = bd.query("SELECT isbn, nom, auteur, resume, edition, quantite, genre FROM livre") { réponse, _ ->
        Livres(
            réponse.getString("isbn"),
            réponse.getString("nom"),
            réponse.getString("auteur"),
            réponse.getString("resume"),
            réponse.getString("edition"),
            réponse.getString("genre"),
            réponse.getInt("quantite")
        )
    }

    override fun chercherParIsbn(isbn: String): Livres? { var livres:Livres?=null  
        bd.query("SELECT * FROM livre WHERE isbn=?", isbn) { réponse, _ ->
        livres=Livres(
            réponse.getString("isbn"),
            réponse.getString("nom"),
            réponse.getString("auteur"),
            réponse.getString("resume"),
            réponse.getString("edition"),
            réponse.getString("genre"),
            réponse.getInt("quantite")
        )}
        return livres
    }
    override fun chercherParGenre(genre: String): Livres? { var livres:Livres?=null  
        bd.query("SELECT * FROM livre WHERE genre LIKE ?", genre) { réponse, _ ->
        livres=Livres(
            réponse.getString("isbn"),
            réponse.getString("nom"),
            réponse.getString("auteur"),
            réponse.getString("resume"),
            réponse.getString("edition"),
            réponse.getString("genre"),
            réponse.getInt("quantite"),
        )}
        return livres
    }
    override fun chercherParNom(nom: String): Livres? { var livres:Livres?=null  
        bd.query("SELECT * FROM livre WHERE nom LIKE ?", nom) { réponse, _ ->
        livres=Livres(
            réponse.getString("isbn"),
            réponse.getString("nom"),
            réponse.getString("auteur"),
            réponse.getString("resume"),
            réponse.getString("edition"),
            réponse.getString("genre"),
            réponse.getInt("quantite")
        )}
        return livres
    }

    override fun ajouter(livres: Livres): Livres? {  
        bd.query("INSERT INTO livre(isbn, nom, auteur, resume, edition, quantite, genre) VALUES (?, ?, ?, ? ,? ,?, ?)", livres.isbn, livres.nom, livres.auteur, livres.resume, livres.edition, livres.quantite, livres.genre) { réponse, _ ->
        Livres(
            réponse.getString("isbn"),
            réponse.getString("nom"),
            réponse.getString("auteur"),
            réponse.getString("resume"),
            réponse.getString("edition"),
            réponse.getString("genre"),
            réponse.getInt("quantite")
        )}
        return livres
    }

    override fun modifierL(isbn:String, livres: Livres): Livres? {
        val query = "UPDATE livre SET nom=?, auteur=?, resume=?, edition=?, quantite=?, genre=? WHERE isbn=?"
        bd.update(query, livres.nom, livres.auteur, livres.resume, livres.edition, livres.quantite, livres.genre, isbn)
       
        return livres
    }

    override fun effacerL(isbn:String) {  
        bd.query("DELETE FROM livre WHERE isbn=?", isbn){ réponse, _ ->
            Livres(
                réponse.getString("isbn"),
                réponse.getString("nom"),
                réponse.getString("auteur"),
                réponse.getString("resume"),
                réponse.getString("edition"),
                réponse.getString("genre"),
                réponse.getInt("quantite")
            )}
    }

    override fun chercherParId(id: Int): Livres? { return null}

    override fun modifier(id: Int, element: Livres): Livres? { return null}

    override fun effacer(id: Int) { }
       
}
