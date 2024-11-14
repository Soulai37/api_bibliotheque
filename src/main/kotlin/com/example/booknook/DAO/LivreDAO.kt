package com.example.booknook

import com.example.booknook.DAO

interface LivreDAO: DAO<Livres> {
    override fun chercherTous(): List<Livres>
    fun chercherParIsbn(isbn: String): Livres?
    fun chercherParGenre(genre: String): Livres?
    override fun chercherParNom(nom: String): Livres?
    override fun ajouter(livres: Livres): Livres?
    fun modifierL(isbn: String, livres: Livres): Livres?
    fun effacerL(isbn: String)
}
