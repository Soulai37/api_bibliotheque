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
            val login=utilReponse.getString("login")
            val type=utilReponse.getBoolean("type")
            val listeLivres = ArrayList<Livres>()
            db.query("""SELECT livre.isbn, livre.nom, livre.auteur, livre.resume, livre.edition, livre.genre, livre.quantite, livre.image
                        FROM livre
                        INNER JOIN utilisateur_livresFavoris ON livre.isbn = utilisateur_livresFavoris.isbn_livre
                        WHERE utilisateur_livresFavoris.id_utilisateur = ?""", arrayOf(id)){ livreReponse, _ ->
                            listeLivres.add(Livres(livreReponse.getString("isbn"), livreReponse.getString("nom"), livreReponse.getString("auteur"), livreReponse.getString("resume"), livreReponse.getString("edition"), livreReponse.getString("genre"), livreReponse.getInt("quantite"), livreReponse.getString("image")))
                        }
            var utilisateur=Utilisateurs(id, nom, login, type, listeLivres)
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
            utilisateur=Utilisateurs(reponse.getInt("id"), reponse.getString("nom"), reponse.getString("login"), reponse.getBoolean("type"), listeLivres)
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
            utilisateur=Utilisateurs(reponse.getInt("id"), reponse.getString("nom"), reponse.getString("login"), reponse.getBoolean("type"), listeLivres)
        }
        return utilisateur
    }
    override fun chercherParLogin(login: String): Utilisateurs? {
        var utilisateur:Utilisateurs?=null
        val listeLivres = ArrayList<Livres>()
        db.query("SELECT * FROM utilisateur WHERE login LIKE ?", login){ reponse, _ ->
            val id=reponse.getInt("id")
            db.query("""SELECT livre.isbn, livre.nom, livre.auteur, livre.resume, livre.edition, livre.genre, livre.quantite, livre.image
                        FROM livre
                        INNER JOIN utilisateur_livresFavoris ON livre.isbn = utilisateur_livresFavoris.isbn_livre
                        WHERE utilisateur_livresFavoris.id_utilisateur = ?""", arrayOf(id)){ livreReponse, _ ->
                            listeLivres.add(Livres(livreReponse.getString("isbn"), livreReponse.getString("nom"), livreReponse.getString("auteur"), livreReponse.getString("resume"), livreReponse.getString("edition"), livreReponse.getString("genre"), livreReponse.getInt("quantite"), livreReponse.getString("image")))
                        }     
            utilisateur=Utilisateurs(reponse.getInt("id"), reponse.getString("nom"), reponse.getString("login"), reponse.getBoolean("type"), listeLivres)
        }
        return utilisateur
    }
    override fun ajouter(utilisateur: Utilisateurs): Utilisateurs? {
        db.query("INSERT INTO utilisateur(id, nom, login, type) VALUES (?, ?, ?, ?)", utilisateur.id, utilisateur.nom, utilisateur.login, utilisateur.type){ reponse, _ ->
            Utilisateurs(reponse.getInt("id"), reponse.getString("nom"), reponse.getString("login"), reponse.getBoolean("type"))
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

    /*override fun modifier(id: Int, utilisateur: Utilisateurs): Utilisateurs? {
        val queryUtilisateur = "UPDATE utilisateur SET nom=?, login=?, type=? WHERE id=?"
        db.update(queryUtilisateur, utilisateur.nom, utilisateur.login, utilisateur.type, id)
        return utilisateur
    }*/
    override fun effacer(id: Int) {
        db.query("DELETE FROM utilisateur WHERE id=?", id){ reponse, _ ->
            Utilisateurs(reponse.getInt("id"), reponse.getString("nom"), reponse.getString("login"), reponse.getBoolean("type"))
        }
    }
}