package com.example.booknook

import org.springframework.stereotype.Repository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import com.example.booknook.Utilisateurs

@Repository
class UtilisateursDAOImpl(private val db: JdbcTemplate): UtilisateursDAO{

    override fun chercherTous(): List<Utilisateurs> {
        var listeUtilisateurs =ArrayList<Utilisateurs> ()
        db.query("select * from utilisateur") { utilReponse, _ ->
            val id=utilReponse.getInt("id")
            val nom=utilReponse.getString("nom")
            val type=utilReponse.getBoolean("type")
            val listeLivres = ArrayList<Livres>()
            db.query("""SELECT livre.isbn, livre.nom, livre.auteur, livre.resume, livre.edition, livre.genre, livre.quantite, livre.image
                        FROM livre
                        INNER JOIN utilisateur_livresFavoris ON livre.isbn = utilisateur_livresFavoris.isbn_livre
                        WHERE utilisateur_livresFavoris.id_utilisateur = ?""", arrayOf(id)){ livreReponse, _ ->
                            listeLivres.add(Livres(livreReponse.getString("isbn"), livreReponse.getString("nom"), livreReponse.getString("auteur"), livreReponse.getString("resume"), livreReponse.getString("edition"), livreReponse.getString("genre"), livreReponse.getInt("quantite"), livreReponse.getString("image")))
                        }
            var utilisateur=Utilisateurs(id, nom, type, listeLivres)
            listeUtilisateurs.add(utilisateur)
        }
        return listeUtilisateurs
    }
    override fun chercherParId(id: Int): Utilisateurs? {
        var utilisateur:Utilisateurs?=null
        val listeLivres = ArrayList<Livres>()
        db.query("SELECT * FROM utilisateur WHERE id = ?", id){ reponse, _ ->
            db.query("""SELECT livre.isbn, livre.nom, livre.auteur, livre.resume, livre.edition, livre.genre, livre.quantite, livre.image
                        FROM livre
                        INNER JOIN utilisateur_livresFavoris ON livre.isbn = utilisateur_livresFavoris.isbn_livre
                        WHERE utilisateur_livresFavoris.id_utilisateur = ?""", arrayOf(id)){ livreReponse, _ ->
                            listeLivres.add(Livres(livreReponse.getString("isbn"), livreReponse.getString("nom"), livreReponse.getString("auteur"), livreReponse.getString("resume"), livreReponse.getString("edition"), livreReponse.getString("genre"), livreReponse.getInt("quantite"), livreReponse.getString("image")))
                        }   
            utilisateur=Utilisateurs(reponse.getInt("id"), reponse.getString("nom"), reponse.getBoolean("type"), listeLivres)
        }
        return utilisateur
    }
    override fun chercherParNom(nom: String): Utilisateurs? {
        var utilisateur:Utilisateurs?=null
        val listeLivres = ArrayList<Livres>()
        db.query("SELECT * FROM utilisateur WHERE nom LIKE ?", nom){ reponse, _ ->
            val id=reponse.getInt("id")
            db.query("""SELECT livre.isbn, livre.nom, livre.auteur, livre.resume, livre.edition, livre.genre, livre.quantite, livre.image
                        FROM livre
                        INNER JOIN utilisateur_livresFavoris ON livre.isbn = utilisateur_livresFavoris.isbn_livre
                        WHERE utilisateur_livresFavoris.id_utilisateur = ?""", arrayOf(id)){ livreReponse, _ ->
                            listeLivres.add(Livres(livreReponse.getString("isbn"), livreReponse.getString("nom"), livreReponse.getString("auteur"), livreReponse.getString("resume"), livreReponse.getString("edition"), livreReponse.getString("genre"), livreReponse.getInt("quantite"), livreReponse.getString("image")))
                        }     
            utilisateur=Utilisateurs(reponse.getInt("id"), reponse.getString("nom"), reponse.getBoolean("type"), listeLivres)
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
        val queryUtilisateur = "UPDATE utilisateur SET nom=?, type=? WHERE id=?"
        db.update(queryUtilisateur, utilisateur.nom, utilisateur.type, id)
            val deleteQuery = "DELETE FROM utilisateur_livresFavoris WHERE id_utilisateur=?"
        db.update(deleteQuery, id)
        val insertQuery = "INSERT INTO utilisateur_livresFavoris (id_utilisateur, isbn_livre) VALUES (?, ?)"
        for (livre in utilisateur.livresFavoris) {
            db.update(insertQuery, id, livre.isbn)
        }    
        return utilisateur
    }
    
    override fun effacer(id: Int) {
        db.query("DELETE FROM utilisateur WHERE id=?", id){ reponse, _ ->
            Utilisateurs(reponse.getInt("id"), reponse.getString("nom"), reponse.getBoolean("type"))
        }
    }
    override fun livresRecommandations(nom: String): List<Livres> {
        val livres=db.query("SELECT isbn, nom, auteur, resume, edition, quantite, genre, image FROM livre") { réponse, _ ->
            Livres(
                réponse.getString("isbn"),
                réponse.getString("nom"),
                réponse.getString("auteur"),
                réponse.getString("resume"),
                réponse.getString("edition"),
                réponse.getString("genre"),
                réponse.getInt("quantite"),
                réponse.getString("image")
            )
        }
        val utilisateur=chercherParNom(nom)
        val livresFavoris=utilisateur!!.livresFavoris
        val genres = livresFavoris.groupingBy { it.genre }.eachCount()
        val genresRecurrents = genres.entries.sortedByDescending { it.value }.take(2).map { it.key }
        val livreTriés = livres.filter { it.genre in genresRecurrents }
        return livreTriés.filter { it !in livresFavoris }.shuffled().take(3)
    }
}