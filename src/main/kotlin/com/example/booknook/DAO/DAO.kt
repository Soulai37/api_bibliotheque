package com.example.booknook
interface DAO<T, ID> {
    fun chercherTous(): List<T>
    fun chercherParId(id: ID): T?
    fun chercherParNom(nom: String): T?
    fun ajouter(element: T): T?
    fun modifier(id: ID, element: T): T?
    fun effacer(id: ID)

}