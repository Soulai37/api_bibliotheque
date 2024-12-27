package com.example.booknook

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.oauth2.jwt.Jwt


import com.example.booknook.*

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension::class)
class EmpruntServiceTests {

    @Mock
    lateinit var mockEmpruntDAO: EmpruntDAOImpl

    private val livre = Livres(
        isbn = "1234567890",
        nom = "Un Livre",
        auteur = "Auteur Test",
        resume = "Résumé du livre",
        edition = "Edition Test",
        genre = "Genre Test",
        quantite = 5,
        image = "image_url"
    )

    private val utilisateur = Utilisateurs(
        id = 1,
        nom = "Utilisateur Test",
        login = "user@test.com",
        type = false
    )

    private val emprunt = Emprunt(
        id = 1,
        livre = livre,
        utilisateur = utilisateur,
        date_emprunt = java.time.LocalDate.now(),
        date_retour = java.time.LocalDate.now().plusDays(7)
    )

    @Test
    fun `étant donné une liste d'emprunts existants lorsqu'on récupère tous les emprunts on obtient la liste complète`() {
        val emprunts = listOf(emprunt)
        Mockito.`when`(mockEmpruntDAO.chercherTous()).thenReturn(emprunts)

        val resultatObtenu = EmpruntService(mockEmpruntDAO).obtenirEmprunts()

        assertEquals(emprunts, resultatObtenu)
    }

    @Test
    fun `étant donné un emprunt existant lorsqu'on le récupère par ID on obtient l'emprunt`() {
        Mockito.`when`(mockEmpruntDAO.chercherParId(1)).thenReturn(emprunt)

        val resultatObtenu = EmpruntService(mockEmpruntDAO).obtenirEmpruntParId(1, createMockJwt(utilisateur.login))

        assertEquals(emprunt, resultatObtenu)
    }

    @Test
    fun `étant donné un emprunt inexistant lorsqu'on le récupère par ID on obtient une RessourceInexistanteException`() {
        Mockito.`when`(mockEmpruntDAO.chercherParId(99)).thenReturn(null)

        val exception = assertFailsWith<RessourceInexistanteException> {
            EmpruntService(mockEmpruntDAO).obtenirEmpruntParId(99, createMockJwt(utilisateur.login))
        }

        assertEquals("L'emprunt est inexistant dans le système", exception.message)
    }

    @Test
    fun `étant donné un emprunt valide lorsqu'on l'ajoute alors il est ajouté et la quantité du livre est décrémentée`() {
        Mockito.`when`(mockEmpruntDAO.ajouter(emprunt)).thenReturn(emprunt)

        val service = EmpruntService(mockEmpruntDAO)
        val resultatObtenu = service.ajouterEmprunt(emprunt, createMockJwt(utilisateur.login))

        assertEquals(emprunt, resultatObtenu)
        Mockito.verify(mockEmpruntDAO).decrementerQuantite(livre.isbn)
    }

    @Test
    fun `étant donné un emprunt avec une date de retour invalide lorsqu'on l'ajoute alors on obtient une RequeteMalFormuleeException`() {
        val empruntInvalide = emprunt.copy(date_retour = emprunt.date_emprunt.minusDays(1))

        val exception = assertFailsWith<RequeteMalFormuleeException> {
            EmpruntService(mockEmpruntDAO).ajouterEmprunt(empruntInvalide, createMockJwt(utilisateur.login))
        }

        assertEquals("Date de retour ne peut pas être inférieure à la date d'emprunt", exception.message)
    }

    @Test
    fun `étant donné un emprunt existant lorsqu'on le supprime alors il est supprimé`() {
        Mockito.`when`(mockEmpruntDAO.chercherParId(1)).thenReturn(emprunt)

        EmpruntService(mockEmpruntDAO).supprimerEmprunt(1, createMockJwt(utilisateur.login))

        Mockito.verify(mockEmpruntDAO).effacer(1)
    }

    @Test
    fun `étant donné un emprunt inexistant lorsqu'on le supprime alors on obtient une RessourceInexistanteException`() {
        Mockito.`when`(mockEmpruntDAO.chercherParId(99)).thenReturn(null)

        val exception = assertFailsWith<RessourceInexistanteException> {
            EmpruntService(mockEmpruntDAO).supprimerEmprunt(99, createMockJwt(utilisateur.login))
        }

        assertEquals("L'emprunt est inexistant dans le système", exception.message)
    }

    private fun createMockJwt(email: String): Jwt {
        return Mockito.mock(Jwt::class.java).apply {
            Mockito.`when`(claims).thenReturn(mapOf("email" to email))
        }
    }
}