import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertFailsWith
import org.springframework.security.oauth2.jwt.Jwt
import com.example.booknook.UtilisateursService
import com.example.booknook.RessourceInexistanteException
import com.example.booknook.Utilisateurs
import com.example.booknook.OperationNonAutoriseeException
import com.example.booknook.ConflitAvecUneRessourceExistanteException
import com.example.booknook.UtilisateursDAOImpl

class UtilisateursServiceTest {

    private val utilisateursDAO = mock<UtilisateursDAOImpl>()
    private val service = UtilisateursService(utilisateursDAO)
    private val jeton = mock<Jwt>()

    @Test
    fun `Étant donné que des utilisateurs existent, quand on les obtient, on obtient une liste d'utilisateurs`() {
        utilisateursDAO.chercherTous()
        
        val utilisateurs = service.obtenirUtilisateurs()

        assertNotNull(utilisateurs)
        assertEquals(2, utilisateurs.size)
        assertEquals("user1", utilisateurs[0].nom)
    }

    @Test
    fun `Étant donné qu'un utilisateur avec l'ID 1 existe, quand on le demande par ID, on obtient l'utilisateur avec l'ID 1 et le nom 'user1'`() {
        jeton.claims["email"] = "login1"
        utilisateursDAO.chercherParId(1)

        val utilisateur = service.obtenirUtilisateurParId(1, jeton)

        assertNotNull(utilisateur)
        assertEquals(1, utilisateur.id)
        assertEquals("user1", utilisateur.nom)
    }

    @Test
    fun `Étant donné qu'aucun utilisateur n'existe avec l'ID 999, quand on demande l'utilisateur par ID 999, on obtient une exception RessourceInexistanteException`() {
        utilisateursDAO.chercherParId(999)

        assertFailsWith<RessourceInexistanteException> {
            service.obtenirUtilisateurParId(999, jeton)
        }
    }

    @Test
    fun `Étant donné qu'un utilisateur avec le nom 'Dupont' existe, quand on le demande par nom, on obtient l'utilisateur avec le nom 'Dupont'`() {
        utilisateursDAO.chercherParNom("Dupont")
        
        val utilisateur = service.obtenirUtilisateurParNom("Dupont")

        assertNotNull(utilisateur)
        assertEquals("Dupont", utilisateur.nom)
    }

    @Test
    fun `Étant donné qu'aucun utilisateur n'existe avec le nom 'Durand', quand on demande l'utilisateur par nom 'Durand', on obtient null`() {
        utilisateursDAO.chercherParNom("Durand")
        
        val utilisateur = service.obtenirUtilisateurParNom("Durand")

        assertNull(utilisateur)
    }

    @Test
    fun `Étant donné un jeton avec email 'login1', quand on demande un utilisateur avec un email différent, on obtient une exception OperationNonAutoriseeException`() {
        jeton.claims["email"] = "login2"
        utilisateursDAO.chercherParId(1)

        assertFailsWith<OperationNonAutoriseeException> {
            service.obtenirUtilisateurParId(1, jeton)
        }
    }

    @Test
    fun `Etant donne un jeton avec email admin@booknook-qc-ca quand on demande un utilisateur on obtient lutilisateur meme si les emails ne correspondent pas`() {
        jeton.claims["email"] = "admin@booknook.qc.ca"
        utilisateursDAO.chercherParId(1)

        val utilisateur = service.obtenirUtilisateurParId(1, jeton)

        assertNotNull(utilisateur)
        assertEquals(1, utilisateur.id)
        assertEquals("user1", utilisateur.nom)
    }
}
