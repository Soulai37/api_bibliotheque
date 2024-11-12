package com.example.booknook
interface DAO<T> {
    fun chercherTous(): List<T>
    fun chercherParId(id: Int): T?
    fun chercherParNom(nom: String): T?
    fun ajouter(element: T): T?
    fun modifier(id: Int, element: T): T?
    fun effacer(id: Int)
}