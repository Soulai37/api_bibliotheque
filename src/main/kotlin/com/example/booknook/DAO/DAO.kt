
interface DAO<T> {
    fun chercherTous(): List<T>
    fun chercherParId(id: Int): T?
    //fun ajouter(element: T): T?
    //fun modifier(id: String, element: T): T?
    //fun effacer(id: String)
}