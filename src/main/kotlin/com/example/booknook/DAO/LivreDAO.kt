package com.example.booknook

import com.example.booknook.DAO

interface LivreDAO: DAO<Livres, String> {
    override fun chercherTous(): List<Livres>
    override fun chercherParId(isbn: String): Livres?
    fun chercherParGenre(genre: String): List<Livres> ?
    override fun chercherParNom(nom: String): Livres?
    override fun ajouter(livres: Livres): Livres?
    override fun modifier(isbn: String, livres: Livres): Livres?
    override fun effacer(isbn: String)
}
