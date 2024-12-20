package com.example.booknook
 
import org.springframework.stereotype.Repository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
@Repository
class EmpruntDAOImpl(private val db: JdbcTemplate): EmpruntDAO{
   
    override fun chercherTous(): List<Emprunt> = db.query("""
    SELECT e.id, e.isbn_livre, e.id_utilisateur, e.date_emprunt, e.date_retour,
            l.isbn, l.nom, l.auteur, l.resume, l.auteur, l.edition, l.genre, l.quantite, l.image,
            u.id, u.nom, u.login, u.type
            FROM emprunt e JOIN livre l on e.isbn_livre=l.isbn
            JOIN utilisateur u ON e.id_utilisateur=u.id
    """) { reponse, _ ->
        val livre=Livres(reponse.getString("isbn"), reponse.getString("nom"), reponse.getString("auteur"), reponse.getString("resume"), reponse.getString("edition"), reponse.getString("genre"), reponse.getInt("quantite"), reponse.getString("image"))
        val utilisateur=Utilisateurs(reponse.getInt("id"), reponse.getString("u.nom"), reponse.getString("u.login"), reponse.getBoolean("type"))
        Emprunt(reponse.getInt("id"), livre, utilisateur, reponse.getDate("date_emprunt").toLocalDate(), reponse.getDate("date_retour").toLocalDate())
    }
    override fun chercherParId(id: Int): Emprunt? {
        var emprunt:Emprunt?=null
        db.query("""
            SELECT e.id, e.isbn_livre, e.id_utilisateur, e.date_emprunt, e.date_retour,
            l.isbn, l.nom, l.auteur, l.resume, l.auteur, l.edition, l.genre, l.quantite, l.image,
            u.id, u.nom, u.login, u.type
            FROM emprunt e JOIN livre l on e.isbn_livre=l.isbn
            JOIN utilisateur u ON e.id_utilisateur=u.id
            where e.id=?
            """, id) { reponse, _ ->
            val livre=Livres(reponse.getString("isbn"), reponse.getString("nom"), reponse.getString("auteur"), reponse.getString("resume"), reponse.getString("edition"), reponse.getString("genre"), reponse.getInt("quantite"), reponse.getString("image"))
            val utilisateur=Utilisateurs(reponse.getInt("id_utilisateur"), reponse.getString("u.nom"), reponse.getString("u.login"), reponse.getBoolean("type"))
            emprunt=Emprunt(reponse.getInt("id"), livre, utilisateur, reponse.getDate("date_emprunt").toLocalDate(), reponse.getDate("date_retour").toLocalDate())
        }
        return emprunt
    }
    override fun chercherParNom(nom: String): Emprunt? {
        var emprunt:Emprunt?=null
        db.query("""
            SELECT e.id, e.isbn_livre, e.id_utilisateur, e.date_emprunt, e.date_retour,
            l.isbn, l.nom, l.auteur, l.resume, l.auteur, l.edition, l.genre, l.quantite, l.image,
            u.id, u.nom, u.login, u.type
            FROM emprunt e JOIN livre l on e.isbn_livre=l.isbn
            JOIN utilisateur u ON e.id_utilisateur=u.id
            WHERE l.nom LIKE ?
            """, nom) { reponse, _ ->
            val livre=Livres(reponse.getString("isbn"), reponse.getString("nom"), reponse.getString("auteur"), reponse.getString("resume"), reponse.getString("edition"), reponse.getString("edition"), reponse.getInt("quantite"), reponse.getString("image"))
            val utilisateur=Utilisateurs(reponse.getInt("id"), reponse.getString("u.nom"), reponse.getString("u.login"), reponse.getBoolean("type"))
            emprunt=Emprunt(reponse.getInt("id"), livre, utilisateur, reponse.getDate("date_emprunt").toLocalDate(), reponse.getDate("date_retour").toLocalDate())
        }
        return emprunt
    }
    fun decrementerQuantite(isbn: String) {
        db.update("UPDATE livre SET quantite = quantite - 1 WHERE isbn = ?", isbn)
    }
    override fun chercherParNomUtilisateur(nom: String): List<Emprunt> = db.query("""
        SELECT e.id, e.isbn_livre, e.id_utilisateur, e.date_emprunt, e.date_retour,
                l.isbn, l.nom, l.auteur, l.resume, l.auteur, l.edition, l.genre, l.quantite, l.image,
                u.id, u.nom, u.login, u.type
                FROM emprunt e JOIN livre l on e.isbn_livre=l.isbn
                JOIN utilisateur u ON e.id_utilisateur=u.id
                WHERE u.nom LIKE ?
        """, nom) { reponse, _ ->
        val livre=Livres(reponse.getString("isbn"), reponse.getString("nom"), reponse.getString("auteur"), reponse.getString("resume"), reponse.getString("edition"), reponse.getString("edition"), reponse.getInt("quantite"), reponse.getString("image"))
        val utilisateur=Utilisateurs(reponse.getInt("e.id_utilisateur"), reponse.getString("u.nom"), reponse.getString("u.login"), reponse.getBoolean("type"))
        Emprunt(reponse.getInt("id"), livre, utilisateur, reponse.getDate("date_emprunt").toLocalDate(), reponse.getDate("date_retour").toLocalDate())
    }
    override fun ajouter(emprunt: Emprunt): Emprunt? {
        db.query("INSERT INTO emprunt(isbn_livre, id_utilisateur, date_emprunt, date_retour) VALUES (?, ?, ?, ?)", emprunt.livre.isbn, emprunt.utilisateur.id, emprunt.date_emprunt, emprunt.date_retour){ reponse, _ ->
        }
        return emprunt
    }
    override fun modifier(id: Int, emprunt: Emprunt): Emprunt? {
        val query = "UPDATE emprunt SET isbn_livre=?, id_utilisateur=?, date_emprunt=?, date_retour=? WHERE id=?"
        db.update(query, emprunt.livre.isbn, emprunt.utilisateur.id, emprunt.date_emprunt, emprunt.date_retour, id)
        return emprunt
    }
    override fun effacer(id: Int) {
        db.update("DELETE FROM emprunt WHERE id=?", id)
    }
}