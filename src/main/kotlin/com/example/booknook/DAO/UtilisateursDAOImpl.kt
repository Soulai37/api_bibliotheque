package com.example.booknook

import org.springframework.stereotype.Repository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import com.example.booknook.Utilisateurs

@Repository
class UtilisateursDAOImpl(private val db: JdbcTemplate): UtilisateursDAO{

    override fun chercherTous(): List<Utilisateurs> = db.query("SELECT * FROM utilisateur") { reponse, _ ->
        Utilisateurs(reponse.getInt("id"), reponse.getString("nom"), reponse.getBoolean("type"))
    }
    override fun chercherParId(id: Int): Utilisateurs? {
        var utilisateur:Utilisateurs?=null
        db.query("SELECT * FROM utilisateur WHERE id = ?", id){ reponse, _ ->
            utilisateur=Utilisateurs(reponse.getInt("id"), reponse.getString("nom"), reponse.getBoolean("type"))
        }
        return utilisateur
    }
    override fun chercherParNom(nom: String): Utilisateurs? {
        var utilisateur:Utilisateurs?=null
        db.query("SELECT * FROM utilisateur WHERE nom LIKE ?", nom){ reponse, _ ->
            utilisateur=Utilisateurs(reponse.getInt("id"), reponse.getString("nom"), reponse.getBoolean("type"))
        }
        return utilisateur
    }
    override fun ajouter(utilisateur: Utilisateurs): Utilisateurs? {
        db.query("INSERT INTO utilisateur(id, nom, type) VALUES (?, ?, ?)", utilisateur.id, utilisateur.nom, utilisateur.type){ reponse, _ ->
            Utilisateurs(reponse.getInt("id"), reponse.getString("nom"), reponse.getBoolean("type"))
        }
        return utilisateur
    }
    override fun modifier(id: Int, utilisateur: Utilisateurs): Utilisateurs? {
        val query = "UPDATE utilisateur SET nom=?, type=? WHERE id=?"
        db.update(query, utilisateur.nom, utilisateur.type, id)
        return utilisateur
    }
    override fun effacer(id: Int) {
        db.query("DELETE FROM utilisateur WHERE id=?", id){ reponse, _ ->
            Utilisateurs(reponse.getInt("id"), reponse.getString("nom"), reponse.getBoolean("type"))
        }
    }
}