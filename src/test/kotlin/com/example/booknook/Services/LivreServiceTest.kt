import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import kotlin.test.assertEquals
import com.example.booknook.LivreService
import com.example.booknook.LivreDAOImp
import com.example.booknook.Livres
import com.example.booknook.RequeteMalFormuleeException

class LivreServiceTest {
    private val livreDAO: LivreDAOImp = mock()
    private val livreService = LivreService(livreDAO)

    @Test
    fun `Étant donné une liste de livres, quand on demande tous les livres, on obtient la liste des livres`() {
        val livres = listOf(Livres("ISBN12345678", "Le Livre Magique", "Jean Dupont", "Résumé", "Edition", "Fantastique", 10, "image.jpg"))
        livreDAO.chercherTous()
        assertEquals(livres, livreService.obtenirLivres())
    }

    @Test
    fun `Étant donné un ISBN valide, quand on vérifie l'ISBN, on obtient true`() {
        val result = livreService.verificationISBN("ISBN12345678")
        assertEquals(true, result)
    }

    @Test
    fun `Étant donné un ISBN invalide, quand on vérifie l'ISBN, on obtient une exception`() {
        try {
            livreService.verificationISBN("12345678")
        } catch (e: RequeteMalFormuleeException) {
            assertEquals("L'isbn 12345678 n'est pas dans un format valide.", e.message)
        }
    }

    @Test
    fun `Étant donné un ISBN, quand on demande un livre par ISBN, on obtient le livre correspondant`() {
        val livre = Livres("ISBN12345678", "Le Livre Magique", "Jean Dupont", "Résumé", "Edition", "Fantastique", 10, "image.jpg")
        livreDAO.chercherParId("ISBN12345678")
        assertEquals(livre, livreService.obtenirLivreParIsbn("ISBN12345678"))
    }

    @Test
    fun `Étant donné un livre, quand on l'ajoute, on obtient le livre ajouté`() {
        val livre = Livres("ISBN12345678", "Le Livre Magique", "Jean Dupont", "Résumé", "Edition", "Fantastique", 10, "image.jpg")
        livreDAO.ajouter(livre)
        assertEquals(livre, livreService.ajouterLivre(livre))
    }
}
