package com.example.booknook

import org.springframework.stereotype.Repository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
@Repository
class EmpruntDAOImpl(private val db: JdbcTemplate): EmpruntDAO{
    
    override fun chercherTous(): List<Emprunt> = db.query("""
    SELECT e.id, e.isbn_livre, e.id_utilisateur, e.date_emprunt, e.date_retour,
            l.isbn, l.nom, l.auteur, l.resume, l.auteur, l.edition, l.quantite,
            u.id, u.nom, u.type 
            FROM emprunt e JOIN livre l on e.isbn_livre=l.isbn
            JOIN utilisateur u ON e.id_utilisateur=u.id
    """) { reponse, _ ->
        val livre=Livres(reponse.getString("isbn"), reponse.getString("nom"), reponse.getString("auteur"), reponse.getString("resume"), reponse.getString("edition"), reponse.getInt("quantite"))
        val utilisateur=Utilisateurs(reponse.getInt("id"), reponse.getString("u.nom"), reponse.getBoolean("type"))
        Emprunt(reponse.getInt("id"), livre, utilisateur, reponse.getDate("date_emprunt").toLocalDate(), reponse.getDate("date_retour").toLocalDate())
    }
    override fun chercherParId(id: Int): Emprunt? {
        var emprunt:Emprunt?=null
        db.query("""
            SELECT e.id, e.isbn_livre, e.id_utilisateur, e.date_emprunt, e.date_retour,
            l.isbn, l.nom, l.auteur, l.resume, l.auteur, l.edition, l.quantite,
            u.id, u.nom, u.type 
            FROM emprunt e JOIN livre l on e.isbn_livre=l.isbn
            JOIN utilisateur u ON e.id_utilisateur=u.id
            where e.id=?
            """, id) { reponse, _ ->
            val livre=Livres(reponse.getString("isbn"), reponse.getString("nom"), reponse.getString("auteur"), reponse.getString("resume"), reponse.getString("edition"), reponse.getInt("quantite"))
            val utilisateur=Utilisateurs(reponse.getInt("id"), reponse.getString("u.nom"), reponse.getBoolean("type"))
            emprunt=Emprunt(reponse.getInt("id"), livre, utilisateur, reponse.getDate("date_emprunt").toLocalDate(), reponse.getDate("date_retour").toLocalDate())
        }
        return emprunt
    } 
    override fun chercherParNom(nom: String): Emprunt? {
        var emprunt:Emprunt?=null
        db.query("""
            SELECT e.id, e.isbn_livre, e.id_utilisateur, e.date_emprunt, e.date_retour,
            l.isbn, l.nom, l.auteur, l.resume, l.auteur, l.edition, l.quantite,
            u.id, u.nom, u.type 
            FROM emprunt e JOIN livre l on e.isbn_livre=l.isbn
            JOIN utilisateur u ON e.id_utilisateur=u.id
            WHERE l.nom LIKE ?
            """, nom) { reponse, _ ->
            val livre=Livres(reponse.getString("isbn"), reponse.getString("nom"), reponse.getString("auteur"), reponse.getString("resume"), reponse.getString("edition"), reponse.getInt("quantite"))
            val utilisateur=Utilisateurs(reponse.getInt("id"), reponse.getString("u.nom"), reponse.getBoolean("type"))
            emprunt=Emprunt(reponse.getInt("id"), livre, utilisateur, reponse.getDate("date_emprunt").toLocalDate(), reponse.getDate("date_retour").toLocalDate())
        }
        return emprunt
    }
    override fun chercherParNomUtilisateur(nom: String): List<Emprunt> = db.query("""
        SELECT e.id, e.isbn_livre, e.id_utilisateur, e.date_emprunt, e.date_retour,
                l.isbn, l.nom, l.auteur, l.resume, l.auteur, l.edition, l.quantite,
                u.id, u.nom, u.type 
                FROM emprunt e JOIN livre l on e.isbn_livre=l.isbn
                JOIN utilisateur u ON e.id_utilisateur=u.id
                WHERE u.nom LIKE ?
        """, nom) { reponse, _ ->
        val livre=Livres(reponse.getString("isbn"), reponse.getString("nom"), reponse.getString("auteur"), reponse.getString("resume"), reponse.getString("edition"), reponse.getInt("quantite"))
        val utilisateur=Utilisateurs(reponse.getInt("id"), reponse.getString("u.nom"), reponse.getBoolean("type"))
        Emprunt(reponse.getInt("id"), livre, utilisateur, reponse.getDate("date_emprunt").toLocalDate(), reponse.getDate("date_retour").toLocalDate())
    }
    override fun ajouter(emprunt: Emprunt): Emprunt? {
        db.query("INSERT INTO emprunt(id, isbn_livre, id_utilisateur, date_emprunt, date_retour) VALUES (?, ?, ?, ?, ?)", emprunt.id, emprunt.livre.isbn, emprunt.utilisateur.id, emprunt.date_emprunt, emprunt.date_retour){ reponse, _ ->
            //Emprunt(reponse.getInt("id"), reponse.getString("isbn_livre"), reponse.getInt("id_utilisateur"), reponse.getDate("date_emprunt").toLocalDate(), reponse.getDate("date_retour").toLocalDate())
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