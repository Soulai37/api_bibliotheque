package com.example.booknook

interface UtilisateursDAO: DAO<Utilisateurs, Int>{
    override fun chercherTous():List<Utilisateurs>
    override fun chercherParId(id: Int): Utilisateurs?
    override fun chercherParNom(nom: String): Utilisateurs?
    override fun ajouter(utilisateur: Utilisateurs): Utilisateurs?
    override fun modifier(id: Int, utilisateur: Utilisateurs): Utilisateurs?
    override fun effacer(id: Int)
    fun livresRecommandations(nom: String): List<Livres>
}