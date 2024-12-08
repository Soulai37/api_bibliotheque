package com.example.booknook

interface EmpruntDAO: DAO<Emprunt, Int>{
    override fun chercherTous():List<Emprunt>
    override fun chercherParId(id: Int): Emprunt?
    override fun chercherParNom(nom: String): Emprunt?
    override fun ajouter(emprunt: Emprunt): Emprunt?
    override fun modifier(id: Int, emprunt: Emprunt): Emprunt?
    override fun effacer(id: Int)
    fun chercherParNomUtilisateur(nom: String): List<Emprunt>
}